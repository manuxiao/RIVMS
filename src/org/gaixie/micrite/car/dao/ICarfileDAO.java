/* ===========================================================
 * $Id: ICarfileDAO.java 516 2009-08-23 16:33:31Z bitorb $
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

package org.gaixie.micrite.car.dao;

import java.util.Date;
import java.util.List;

import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.Check;
import org.gaixie.micrite.dao.IGenericDAO;
/**
 * 车辆管理持久化接口
 */
public interface ICarfileDAO extends IGenericDAO<Carfile, Integer> {
	/**
	 * 覆盖父类的方法，需要做一些修改
	 * */
	public Carfile get(Integer id);
	/**
	 * 实时计算是否超期、是否需要被处罚
	 * */
	public void desidedExpiredFlag(Carfile car);
	/**
	 * 这个时间间隔内超期的车辆名单
	 * */
	public List<Carfile> findByMaintainDateWillExpired(Integer carownerId,int fromDays,
			int toDays,int start,int limit);
	public int findCountByMaintainDateWillExpired(Integer carownerId,int fromDays,int toDays);
	/**获取超期未检的车辆名单
	 * @return List<Carfile>  
	 * */
	public List<Carfile>getExpiredCars();
	/**获得需要进行二级维护的车辆们
	 * */
	public List<Carfile>getActiveCars();
    /**
     * 高级查询车辆
     * @param telephone
     * @param start
     * @param limit
     * @return 车辆集合
     */
    public List<Carfile> advancedFindByPerPage(SearchBean[] queryBean, int start, int limit);

    /**
     * 高级查询车辆总记录数
     * @param telephone
     * @return 车辆数量
     */
    public int advancedFindCount(SearchBean[] queryBean);

    /**
     * 根据创建日期及carType普通查询车辆总记录数
     * @param createDate
     * @return
     */
    public int findByCreateDateSpacingCount(int carType);
    public int findCarfileBySkill(int carType,int skill);
//    public int findCarfileBySkill(String licenseNumber,int carType,int skill);
//    public int findByCreateDateSpacingCount(Date startDate,Date endDate,int carType);
    /**
     *根据创建日期及carType普通查询车辆
     * @param createDate
     * @param start
     * @param limit
     * @return
     */
    public List<Carfile> findByCreateDateSpacingPerPage(int start, int limit,int carType);
    public List<Carfile> findCarfileBySkillPerPage(int start, int limit,int carType,int skill);
//    public List<Carfile> findCarfileBySkillPerPage(int start, int limit,String licenseNumber,int carType,int skill);
    
//    public List<Carfile> findByCreateDateSpacingPerPage(Date startDate,Date endDate, int start, int limit,int carType);
    /**
     * 高级查询并按用户来源进行分组
     * @param telphone
     * @return 分组数据集合
     */
    public List findCSGroupByTelVague(SearchBean[] queryBean);

    /**
     * 车辆是否存在，根据车色、车牌号码来找
     * 如果存在id则该id的车子除外
     * */
    public boolean existCar(Integer id,String license,Dictionary paiSe);

//	public void save(Check check);
    public int getCarId(String carNo, Dictionary carColor);
}
