/* ===========================================================
 * $Id: ICarfileService.java 516 2009-08-23 16:33:31Z bitorb $
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

package org.gaixie.micrite.patrolRiverSummary.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Carowner;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.PatrolRiverSummary;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

/**
 * 车辆管理服务接口，封装车辆服务业务模型
 */
public interface IPatrolRiverSummaryService {
	
	/**记录状态：已删除*/
	public final static int STATUS_DELETED = 1;
	/**记录状态：正常*/
	public final static int STATUS_NORMAL = 0;
 
	/**审批未通过的状态*/
	public final static int APPROVAL_NOT = 2;
	/**行政审批通过*/ //行政审批这里将方式拥有行者审批权的用户id,审批时也可以留言放在notepad中？//简化了
 	public final static int APPROVAL_YES = 1;
	/**未行政审批*/
	public final static int APPROVAL_TOBE = 0;
	/**获取单个车辆记录**/
	public PatrolRiverSummary get(Integer id);
	/**过期未检的车辆
	 * **/
	public List<PatrolRiverSummary>getExpiredCars();
 
    /**
     * 新增车辆;
     * 1，已经存在的车主能够自动填满
     * 2，保存后根据二维检测日期和技术等级评定截止日期生成二维和技术等级记录
     * @param Carfile 车辆实体
     */
	public void addFromFront(PatrolRiverSummary patrolRiverSummary);
	/**
	 * 文件导入表格
	 * */
	public void addFromUpload(PatrolRiverSummary patrolRiverSummary);
    
    /**
     * 高级查询车辆
     * @param telephone
     * @param start
     * @param limit
     * @return 车辆集合
     */
    public List<PatrolRiverSummary> advancedFindByPerPage(SearchBean[] queryBean, int start, int limit);

    /**
     * 高级查询车辆总记录数
     * @param telephone
     * @return 车辆数量
     */
    public int advancedFindCount(SearchBean[] queryBean);
    
   /**
 * 删除车辆
 * @param CarfileIds 车辆的id数组
 */
public void delete(int[] CarfileIds);

    /**
     * 日期间隔及CarType普通查询车辆总记录数
     * @param createDate
     * @return
     */
    public int findByCreateDateSpacingCount(int carType);
 
    /**
     * 日期间隔查及CarType普通询车辆 
     * @param createDate
     * @param start
     * @param limit
     * @return
     */
    public List<PatrolRiverSummary> findByCreateDateSpacingPerPage(int start, int limit,int carType);
    /**
     * 高级查询获取用户来源的2D柱图对象
     * @return CategoryDataset
     */
    public CategoryDataset getCarDictionaryBarDataset(SearchBean[] queryBean);
    /**
	    * 高级查询获取用户来源的饼图对象
	    * @param telephone
	    * @return PieDataset
	    */
	    public PieDataset getCarDictionaryPieDataset(SearchBean[] queryBean);
    /**
     * 修改河道汇总信息
     * 要做审核
     * @param PatrolRiverSummary 河道汇总表实体
     */
    public void update(PatrolRiverSummary patrolRiverSummary);
    public boolean existPatrolRiverSummary(Integer userid);
    /**
     * 行政审批免除超期罚款
     * 权限2
     * */
    public void exemptPunishment(Integer carId,String message);
  
    /**更新打印指针
     * */
    public void updatePrintIndex1(int carId,int index);
    public void ridofPunishment(Integer carId, String message);
 
     
}
