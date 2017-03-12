/* ===========================================================
 * $Id: DictionaryDAOImpl.java 520 2009-08-27 05:59:23Z bitorb $
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

package org.gaixie.micrite.dic.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.dao.hibernate.GenericDAOImpl;
import org.gaixie.micrite.dic.dao.IDictionaryDAO;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * 客户来源持久化实现，基于hibernate
 * @see org.gaixie.micrite.dic.dao.IDictionaryDAO
 */
public class DictionaryDAOImpl extends GenericDAOImpl<Dictionary, Integer> implements IDictionaryDAO {
	
	@Override
	public Dictionary get(String name, int type) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass(Dictionary.class);  	
		criteria.add(Restrictions.eq("type", type));
		criteria.add(Restrictions.eq("name", name));
//		criteria.setProjection(Projections.rowCount());
//        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
        
		List<Dictionary> list = getHibernateTemplate().findByCriteria(criteria);
		if(list.size()==0)return null;
		return list.get(0);
	}
	@Override
	public void addItem(String name,int type,int show){
		int maxValue = (Integer)getHibernateTemplate().find("select max(value) from Dictionary d where d.type=?",type).get(0);
		Dictionary d = new Dictionary();
		d.setName(name);
		d.setValue(maxValue+1);
		d.setType(type);
		d.setShow(show);
		save(d);
	}
	@Override
	public List<Dictionary> findAllDictionary(int type) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Dictionary.class);  	
		criteria.add(Restrictions.eq("type", type));   
		return getHibernateTemplate().findByCriteria(criteria);
    }
	@Override
	public List<Dictionary> findAllDictionary(int type,int show) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Dictionary.class);  	
		criteria.add(Restrictions.eq("type", type));	   	
		criteria.add(Restrictions.eq("show", show));	   	
		return getHibernateTemplate().findByCriteria(criteria);
    }
	@Override
	public String getName(int type, int value) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass(Dictionary.class);  	
		criteria.add(Restrictions.eq("type", type));
		criteria.add(Restrictions.eq("value", value));
		List<Dictionary> list = getHibernateTemplate().findByCriteria(criteria);
		if(list.size()==0)return "";
		return list.get(0).getName();
	}

	@Override
	public String getName(int id) {
		Dictionary d = get(id);
		if(d==null)return "(no dictId:"+id+")";
		return d.getName();
	}
	
}
