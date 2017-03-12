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

package org.gaixie.micrite.patrolRiverSummary.dao;

import java.util.Date;
import java.util.List;

import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.PatrolRiverSummary;
import org.gaixie.micrite.dao.IGenericDAO;
/**
 * 车辆管理持久化接口
 */
public interface IPatrolRiverSummaryDAO extends IGenericDAO<PatrolRiverSummary, Integer> {
	/**
	 * 覆盖父类的方法，需要做一些修改
	 * */
	public PatrolRiverSummary get(Integer id);
 	/**获取超期未检的车辆名单
	 * @return List<Carfile>  
	 * */
	public List<PatrolRiverSummary> getExpiredCars();
	 
    /**
     * 高级查询车辆
     * @param telephone
     * @param start
     * @param limit
     * @return 车辆集合
     */
    public List<PatrolRiverSummary> advancedFindByPerPage(SearchBean[] queryBean, int start, int limit);

    /**
     * 高级查询巡河汇总表总记录数
     * @param telephone
     * @return 汇总表数量
     */
    public int advancedFindCount(SearchBean[] queryBean);

    /**
     * 根据创建日期及填报单位普通查询汇总表总记录数
     * @param createDate
     * @return
     */
    public int findByCreateDateSpacingCount(int carType);
 
    /**
     *根据创建日期及carType普通查询车辆
     * @param createDate
     * @param start
     * @param limit
     * @return
     */
    public List<PatrolRiverSummary> findByCreateDateSpacingPerPage(int start, int limit,int carType);
     /**
     * 高级查询并按用户来源进行分组
     * @param telphone
     * @return 分组数据集合
     */
    @SuppressWarnings("unchecked")
	public List findCSGroupByTelVague(SearchBean[] queryBean);
	public boolean existPatrolRiverSummary(Integer userid);
 
}
