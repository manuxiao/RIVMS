/* ===========================================================
 * $Id: SearchFactory.java 522 2009-08-27 09:05:05Z maven.yu $
 * This file is part of Micrite
 * ===========================================================
 *
 * (C) Copyright 2009, by Gaixie.org and Contributors.
 * 
 * Project Info:  http://micrite.gaixie.org/
 *
 * Micrite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Micrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Micrite.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.gaixie.micrite.common.search;

import java.awt.image.RasterFormatException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import ognl.OgnlOps;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * 将自定义查询的值封装为SearchBean数组，并动态生成DetachedCriteria对象实例。
 * <p>
 *
 */
public class SearchFactory {

    /**
	 * 检验输入是否为正确的日期格式(不含秒的任何情况),严格要求日期正确性,格式:yyyy-MM-dd HH:mm
	 * 
	 * @param sourceDate
	 * @return
	 */
	private static Date checkDate(String sourceDate) {
		if (sourceDate == null) {
			return null;
		}
		try {
			Date date = DateUtils.parseDate(sourceDate, new String[] {
					"yyyy-MM-dd hh:mm:ss", "yyyy-MM-dd hh:mm", "yyyy-MM-dd" });
			return date;
		} catch (ParseException e) {
		}
		return null;
	}
    
    @SuppressWarnings("unchecked")
    private static Object convertValue(Class type, String value, String relation){
        Object object;
        if(relation.equals("between")){
            List list = new ArrayList();
            String[] betValue = StringUtils.split(value, ';');
            if(type == java.util.Date.class){
                if(betValue[0].length() == 10)
                    betValue[0] += " 00:00:00";
                else
                    betValue[0] += ":00";
                if(betValue[1].length() == 10)
                    betValue[1] += " 23:59:59";
                else
                    betValue[1] += ":59";
                try {
                    list.add(DateUtils.parseDate(betValue[0],
                            new String[] { "yyyy-MM-dd hh:mm:ss" }));
                    list.add(DateUtils.parseDate(betValue[1],
                            new String[] { "yyyy-MM-dd hh:mm:ss" }));
                } catch (Exception e) {
                    throw new RasterFormatException("无法解析此日期！");
                }
            }
            else{
                list.add(OgnlOps.convertValue(betValue[0], type));
                list.add(OgnlOps.convertValue(betValue[1], type));
            }
            object = list;
        }
        else if(relation.equals("in")){
            List list = new ArrayList();
            for(String s : StringUtils.split(value, ';')){
                list.add(OgnlOps.convertValue(s, type));
            }
            object = list;
        }
        else if(relation.equals("like")){
            object = OgnlOps.convertValue("%" + value + "%", type);
        }
        else{
            if(type == java.util.Date.class){
                if(relation.equals("<") || relation.equals("<=")){
                    if(value.length() == 10)
                        value += " 23:59:59";
                    else
                        value += ":59";
                }
                else{
                    if(value.length() == 10)
                        value += " 00:00:00";
                    else
                        value += ":00";
                }
                try {
                    object = DateUtils.parseDate(value, new String[] { "yyyy-MM-dd hh:mm:ss" });
                } catch (ParseException e) {
                    throw new RasterFormatException("无法解析此日期！");
                }
            }
            else
                object = OgnlOps.convertValue(value, type);
        }
        return object;
    }
    
    private static String[] detach(String str, char left, char right){
        String string = str;
        if(string == null || string.equals(""))
            return null;
        List<String> list = new ArrayList<String>();
        if(StringUtils.indexOf(string, left) == -1 || StringUtils.indexOf(string, right) == -1)
            throw new RasterFormatException("无法解析此字符！: " + string);
        while(StringUtils.indexOf(string, left) >= 0 && StringUtils.indexOf(string, right) >= 0){
            int il = StringUtils.indexOf(string, left);
            int ir = StringUtils.indexOf(string, right);
            if(il > ir){
                string = StringUtils.substring(string, right + 1);
                continue;
            }
            list.add(StringUtils.substring(string, il + 1, ir));
            string = StringUtils.substring(string, ir + 1);
        }
        return list.toArray(new String[list.size()]);
    }
    
    @SuppressWarnings("unchecked")
    public static DetachedCriteria generateCriteria(Class entity, SearchBean[] searchBean) {
        DetachedCriteria criteria = DetachedCriteria.forClass(entity);
        if(searchBean == null || searchBean.length == 0)
            return criteria;
        Object value = null;
        for(int i = 0; i < searchBean.length; i++){
        	if(searchBean[i]==null)continue;
        	Field field;
            try {
                field = entity.getDeclaredField(searchBean[i].getName());
            } catch (NoSuchFieldException e) {
            	
            	String name = searchBean[i].getName();
        		String[] names = name.split("\\.");
        		if(names.length==2){
        			try {
                        field = entity.getDeclaredField(names[0]);
//                        System.out.println(field.getType().getName());
                        field=field.getType().getDeclaredField(names[1]);
                    } catch (NoSuchFieldException e2) {
    	                throw new NoSuchElementException("没有这样的元素:"+searchBean[i].getName());
                    }
        		}else{
	            	try {
						field = entity.getSuperclass().getDeclaredField(searchBean[i].getName());
					} catch (NoSuchFieldException e1) {
		                throw new NoSuchElementException("没有这样的元素:"+searchBean[i].getName());
					}
        		}
            }
            Class type = field.getType();
            value = convertValue(type, searchBean[i].getValue(), searchBean[i].getRelation());
            if(searchBean[i].getRelation().equals("="))
                criteria.add(Expression.eq(searchBean[i].getName(), value));
            else if(searchBean[i].getRelation().equals("<>"))
                criteria.add(Expression.ne(searchBean[i].getName(), value));
            else if(searchBean[i].getRelation().equals("<"))
                criteria.add(Expression.lt(searchBean[i].getName(), value));
            else if(searchBean[i].getRelation().equals("<="))
                criteria.add(Expression.le(searchBean[i].getName(), value));
            else if(searchBean[i].getRelation().equals(">"))
                criteria.add(Expression.gt(searchBean[i].getName(), value));
            else if(searchBean[i].getRelation().equals(">="))
                criteria.add(Expression.ge(searchBean[i].getName(), value));
            else if(searchBean[i].getRelation().equals("like"))
                criteria.add(Expression.like(searchBean[i].getName(),value));
            else if(searchBean[i].getRelation().equals("in"))
                criteria.add(Expression.in(searchBean[i].getName(), (List)value));
            else if(searchBean[i].getRelation().equals("between"))
                criteria.add(Expression.between(searchBean[i].getName(),
                        ((List)value).get(0), ((List)value).get(1)));
        }
        return criteria;
    }
    
    /**
     * 取得查询的月份
     * @param searchBean
     * @return 0～12，0表示格式错误或没有日期条件
     */
    public static int getSearchMonth(SearchBean[] searchBean){
        if(searchBean == null || searchBean.length == 0)
            return 0;
        Date date;
        for(int i = 0; i < searchBean.length; i++){
        	date = checkDate(StringUtils.split(searchBean[i].getValue(), ";")[0]);
        	if(date == null)
        		continue;
        	return date.getMonth() + 1;
        }
    	return 0;
    }
    
	/**
     * @param scarchBunch 格式：
     * [name,value,relation],[name,value,relation],[name,value,relation]
     * name:    字段名称
     * value:   字段值
     * relation:条件关系，包含：=, <, >, <=, >=, like, in
     * type:    字段类型，包含：numeric, string, date, boolean
     * @return
     */
    public static SearchBean[] getSearchTeam(String scarchBunch){
        String[] team = detach(scarchBunch, '[', ']');
        if(team== null)
            return null;
        SearchBean[] search = new SearchBean[team.length];
        for(int i = 0; i < team.length; i++){
            String[] element = StringUtils.split(team[i], ',');
            if(element == null || element.length != 3)
            	continue;
//                throw new RasterFormatException("Unable to parse the string: " + scarchBunch);
            search[i] = new SearchBean(element[0], element[1], element[2]);
        }
        return search;
    }
    
    /**
     * @param args
     * @throws ParseException 
     */
    public static void main(String[] args) {
        String str = "[name,yubo,like],[telephone,13810770810,=],[createDate,2009-8-02 00:00,>=],[interface_,1;2;3,in]";
        SearchBean[] search = getSearchTeam(str);
        for(SearchBean s: search)
            System.out.println("search =" + s.toString());
        System.out.println(getSearchMonth(search));
    }

}
