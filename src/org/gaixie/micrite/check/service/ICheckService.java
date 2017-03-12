package org.gaixie.micrite.check.service;

import java.util.Date;
import java.util.List;

import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.Check;
import org.gaixie.micrite.beans.Standard;

/**
 * 检测记录管理服务接口，封装检测记录服务业务模型
 * 
 * 所有受到的数据都将入库保存，假如出现重复的数据，可以设置为作废。
 * 
 * 其他历史状态的数据，包括审核数据也放在这里；为了简化决定重用这些标志； 可以看做
 * ISPOST为大类可对应对象，TESTKIND为小类，可对应字段；PRINTED就是类型了；STATUS就是对象id
 * 那么对于检测站的二维和评定记录，只能取ISPOST=0的记录， 对于车辆操作记录，取ISPOST=2的记录，
 * 对于维修企业操作记录，取ISPOST=3的记录，
 */
public interface ICheckService {
	/** 对于传过来的记录：这是没有被引用的记录 */
	public static final int STATUS_NORMAL = 0;
	/** 对于传过来的记录：这是被引用的记录 */
	public static final int STATUS_USED = 1;
	/** 对于确认的记录：手工添加、即无引用检测站数据的；否则status保存被应用的记录id */
	public static final int STATUS_NO_REF = STATUS_NORMAL;
	/** 对于确认的记录：未被打印过 */
	public static final int PRINTED_NOT = 0;
	/** 对于确认的记录：已被打印过 */
	public static final int PRINTED_YES = 1;
	/** 检测站传递过来的数据 */
	public static final int ISPOST_YES = 0;
	/** 确认过保存的数据 */
	public static final int ISPOST_NOT = 1;
	/** 数据类型：等级评定 */
	public static final int TESTKIND_EVAL = 1;
	/** 数据类型：二级维护 */
	public static final int TESTKIND_MAIN = 2;
	/**
	 * 下面是扩展之后的标志位解释
	 * */
	/**
	 * 记录类型(Record Type)：检测站的数据；ispost
	 * */
	public static final int RT_JCZ = ISPOST_YES;
	/**
	 * 记录类型(Record Type)：手工保存的数据；ispost
	 * */
	public static final int RT_SG = ISPOST_NOT;
	/**
	 * 记录类型(Record Type)：车辆的历史操作；复用ispost字段
	 * */
	public static final int RT_CL = 2;
	/**
	 * 记录类型(Record Type)：维修企业的历史操作；复用ispost字段
	 * */
	public static final int RT_QY = 3;
	/**
	 * 记录类型(Record Type)：车主信息的操作历史；复用ispost字段
	 * */
	public static final int RT_CZ = 4;
	/**
	 * 记录类型(Record Type)：系统参数的操作历史；复用ispost字段
	 * */
	public static final int RT_SYS = 5;
	/**
	 * 车辆操作：车辆处罚后的解除操作。复用testkind字段
	 * */
	public static final int CL_PUNISH = 101;
	/**
	 * 车辆操作：车辆行政豁免处罚操作。复用testkind字段
	 * */
	public static final int CL_EXEMPT = 102;
	/**
	 * 车辆操作：过户 
	 * */
	public static final int CL_GH = 91;
	/**车辆操作：转籍*/
	public static final int CL_ZJ = 92;
	/**车辆操作：报废*/
	public static final int CL_ZX = 93;
	/**记录操作：新增*/
	public static final int ADD_ACTION = 94;
	/**记录操作：删除*/
	public static final int DELETE_ACTION = 95;	
	/**
	 * 车辆技术档案字段类型为15
	 * */
	public static final int CAR_COLUMN_TYPE = 15;
	/**
	 * 维修企业字段类型为16
	 * */
	public static final int ENTERPRISE_COLUMN_TYPE = 16;
	/**
	 * 从新车增加二维和等级评定记录
	 * */
	public void addFromNewCar(Carfile car);
	/**
	 * 合格证编号是否用掉了
	 * */
	public boolean isUsed(Integer testNo);

	/**
	 * 状态设为正常
	 * **/
	public void setNormal(int[] CheckIds);

	/**
	 * 状态设为作废
	 * */
	public void setCancel(int[] CheckIds);

	/** 设置是否打印的状态，为已打印 */
	public void setPrinted(int[] CheckIds);

	/**
	 * 新增检测记录
	 * 
	 * @param check
	 *            检测记录实体
	 */
	public void add(Check check);

	/**
	 * testKind=1时需要一分为二的，同时需要更新carfile记录，被引用的检测站数据 还要置为已用；需要核销合格证，比如数量减一
	 * */
	public void addWithTestKind(Check check);

	public void addWithTestKindNotMinus(Check check);
	/**
	 * 获取最新车
	 * */
	public List<Check> findRecentPostCheckOf(Carfile car,int start,
			int limit);
	public List<Check> advancedFindByPerPage(SearchBean[] queryBean, int start,
			int limit);

	/**
	 * 高级查询检测记录总记录数
	 * 
	 * @param telephone
	 * @return 检测记录数量
	 */
	public int advancedFindCount(SearchBean[] queryBean);

	/**
	 * 根据车辆记录查询相应检测记录
	 * 
	 * @param telephone
	 * @return 检测记录数量
	 */
	public List<Check> advancedFindByPerPageByCar(Dictionary paiSe,
			String paiHao, int start, int limit);
	
	public int findHistoryCheck(Date startDate,Date endDate,String paiHao,Integer paiSe,Integer createrId,Integer testKind);
	public List<Check> findHistoryCheckPerPage(Date startDate,Date endDate,String paiHao,int start, int limit,Integer paiSe,Integer createrId,Integer testKind);
	
	public int findHistoryCarfile(Date startDate,Date endDate,String paiHao,Integer paiSe,Integer createrId,Integer testKind);
	public List<Check> findHistoryCarfilePerPage(Date startDate,Date endDate,String paiHao,int start, int limit,Integer paiSe,Integer createrId,Integer testKind);
	
	public int findHistoryEnterprise(Date startDate,Date endDate,Integer enterpriseId,Integer createrId,Integer testKind);
	public List<Check> findHistoryEnterprisePerPage(Date startDate,Date endDate,int start, int limit,Integer enterpriseId,Integer createrId,Integer testKind);

	public int findHistoryCarowner(Date startDate,Date endDate,Integer carownerId,Integer createrId,Integer testKind);
	public List<Check> findHistoryCarownerPerPage(Date startDate,Date endDate,int start, int limit,Integer carownerId,Integer createrId,Integer testKind);
	
	public int findHistoryStandard(Date startDate,Date endDate,Integer heGe,Integer createrId,Integer testKind);
	public List<Check> findHistoryStandardPerPage(Date startDate,Date endDate,int start, int limit,Integer heGe,Integer createrId,Integer testKind);

	
	public Check findByStandardNo(Integer standarNo);
	/**
	 * 删除检测记录
	 * 
	 * @param CheckIds
	 *            检测记录的id数组
	 */
	public void delete(int[] CheckIds);

	/**
	 *获取检测记录，根据时间排序
	 */
	public List<Check> getChecks(int[] CheckIds);

	/**
	 * 日期间隔查及CheckSourceType普通询检测记录
	 * 
	 * @param createDate
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Check> findByCreateDateSpacingPerPage(Date startDate,
			Date endDate, int start, int limit);

	/**
	 * 日期间隔及CheckSourceType普通查询检测记录总记录数
	 * 
	 * @param createDate
	 * @return
	 */
	public int findByCreateDateSpacingCount(Date startDate, Date endDate);
	/**
	 * 打印检测记录
	 * 
	 * @param checkId
	 *            检测记录的id数组
	 */
}
