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

package org.gaixie.micrite.car.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Carowner;
import org.gaixie.micrite.beans.Dictionary;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

/**
 * 车辆管理服务接口，封装车辆服务业务模型
 */
public interface ICarfileService {
	
	/**记录状态：已删除*/
	public final static int STATUS_DELETED = 1;
	/**记录状态：正常*/
	public final static int STATUS_NORMAL = 0;
	/**二维超期标识：未超期*/
	public final static int EXPIRED_NOT = 0;
	/**二维超期标识：超期*/
	public final static int EXPIRED_YES = 1;
	/**二维超期标识：超期严重要被处罚*/
	public final static int EXPIRED_SO = 2;
	/**无行政审批同意免除超期处罚*/
	public final static int APPROVAL_NOT = 0;
	/**行政审批同意免除超期处罚,*///行政审批这里将方式拥有行者审批权的用户id,审批时也可以留言放在notepad中？//简化了
	public final static int APPROVAL_YES = 1;
	/**这里是交了罚款之后的状态*/
	public final static int APPROVAL_BUY = 2;
	/**运营状态：营运*/
	public final static int CARSTATUS_NORMAL = 0;
	/**运营状态：报废*/
	public final static int CARSTATUS_DIED = 1;
	/**运营状态：转籍*/
	public final static int CARSTATUS_GONE = 2;
	/**运营状态：过户--不应该是单独的状态，而应该是营运的状态*/
//	public final static int CARSTATUS_GH = 3;
//	/**车辆事件：新增/营运*/
//	public final static int CAR_EVENT_XZ = 0;
//	/**车辆事件：报废*/
//	public final static int CAR_EVENT_BF = CARSTATUS_DIED;
//	/**车辆事件：转籍*/
//	public final static int CAR_EVENT_ZJ = CARSTATUS_GONE;
//	/**车辆事件：过户*/
//	public final static int CAR_EVENT_GH = 3;
	/**获取单个车辆记录**/
	public Carfile get(Integer id);
	/**过期未检的车辆
	 * **/
	public List<Carfile>getExpiredCars();
	/**过户操作;允许同时修改车牌
	 * */
	public void updateGH(Carfile car,String licenseNumber,Carowner ghee,String msg);
	/**转籍*/
	public void updateZJ(Carfile car,String message);
	/**报废*/
	public void updateZX(Carfile car,String message);
    /**
     * 新增车辆;
     * 1，已经存在的车主能够自动填满
     * 2，保存后根据二维检测日期和技术等级评定截止日期生成二维和技术等级记录
     * @param Carfile 车辆实体
     */
	public void addFromFront(Carfile carfile);
	/**
	 * 文件导入表格
	 * */
	public void addFromUpload(Carfile carfile);
    
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
    public int findCarfileBySkill(int carType,int skill);
//    public int findByCreateDateSpacingCount(Date startDate,Date endDate,int carType);
    
    /**
     * 日期间隔查及CarType普通询车辆 
     * @param createDate
     * @param start
     * @param limit
     * @return
     */
    
    public List<Carfile> findByCreateDateSpacingPerPage(int start, int limit,int carType);
    public List<Carfile> findCarfileBySkillPerPage(int start, int limit,int carType,int skill);
//    public List<Carfile> findByCreateDateSpacingPerPage(Date startDate,Date endDate, int start, int limit,int CarDictionaryType);
    /**指定到某日将要超期的车辆
     * 第一个元素[0]是数量
     * */
    public int findCountByMaintainDateWillExpired(Integer carownerId,Date beginDate, Date endDate);
    public List<Carfile> findByMaintainDateWillExpired(Integer carownerId,Date beginDate, Date endDate,int start,int limit);
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
     * 修改车辆信息
     * 要做审核
     * @param Carfile 车辆实体
     */
    public void update(Carfile carfile);
    /**
     * 查看当前牌号牌色的车子是否存在;
     * 如果存在car.id则把这个车子除外
     * */
    public boolean existCar(Carfile car);
    public boolean existCar(String license,Dictionary paiSe);
    /**
     * 根据车牌号码和车色来找到车辆id
     * */
    public int getCarId(String carNo,Dictionary paiSe);
    /**
     * 重新评估是否超期；包含允许超期的天数
     * */
    public int renewErweiExpiredPunishment();
    /**
     * 解除二维超期处罚状态。可能是交完罚款，也可能是免除
     * 权限1
     * */
    public void ridofPunishment(Integer carId,String message);
    /**
     * 行政审批免除超期罚款
     * 权限2
     * */
    public void exemptPunishment(Integer carId,String message);
//    /**
//     * 重新计算二维有效期止的时间
//     * @deprecated
//     * */
//    public int renewErweiDateEnd();
    /**更新打印指针
     * */
    public void updatePrintIndex1(int carId,int index);
    /**更新打印指针
     * */
    public void updatePrintIndex2(int carId,int index);
    /**更新记事本
     * */
    public void updateNotepad(int carId, String notepad);
     
}
