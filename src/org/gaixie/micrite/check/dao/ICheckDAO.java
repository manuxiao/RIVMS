package org.gaixie.micrite.check.dao;

import java.util.Date;
import java.util.List;

import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.Check;
import org.gaixie.micrite.dao.IGenericDAO;
/**
 * 检测记录持久化接口
 */
public interface ICheckDAO extends IGenericDAO<Check, Integer> {
	/**
	 * 该车子最近的检测数据、以及匹配不到车子的最近的检测记录
	 * */
	public List<Check> findRecentPostCheckOf(Carfile car,int start,
			int limit);
	/**
	 * 获取指定合格证和检测时间的传过来尚未被引用的数据
	 * @param heGe 合格证号
	 * @param jianTime 检测时间
	 * */
	List<Check>getOrigCheckOf(Integer heGe ,Date jianTime);
	/**
	 * 将检测站数据置为已用
	 * */
//	public boolean setUsed(Check check);
	/**
	 * 合格证编号是否用过了
	 * */
	public boolean isUsed(Integer testNo);
	/**
	 * 获得车辆的最后一次二级维护记录
	 * */
	public Check getCheckOf(Integer carfileId,Date jianTime);
    /**
     * 根据ID获得CheckSource对象
     * @param  id CheckSource对象id
     * @return CheckSource对象
     */
//    public CheckSource getCheckSource(int id);

    /**
     * 获得所有CheckSource
     * @return CheckSource对象集合
     */
//    public List<CheckSource> findAllCheckSource();


    /**
     * 高级查询并按用户来源进行分组
     * @param telphone
     * @return 分组数据集合
     */
//    public List findCSGroupByTelVague(SearchBean[] queryBean);
    /**
     * 高级查询客户
     * @param telephone
     * @param start
     * @param limit
     * @return 客户集合
     */
    public List<Check> advancedFindByPerPage(SearchBean[] queryBean, int start, int limit);
    
    
    public List<Check> advancedFindByPerPageByCar(Dictionary paiSe,String paiHao,int start,
            int limit);
    /**
     * 高级查询客户总记录数
     * @param telephone
     * @return 客户数量
     */
    public int advancedFindCount(SearchBean[] queryBean);
    /**
     *根据创建日期及CheckSourceType普通查询客户
     * @param createDate
     * @param start
     * @param limit
     * @return
     */
    public List<Check> findByCreateDateSpacingPerPage(Date startDate,Date endDate, int start, int limit);
    /**
     * 根据创建日期及CheckSourceType普通查询客户总记录数
     * @param createDate
     * @return
     */
    public int findByCreateDateSpacingCount(Date startDate,Date endDatem);
    
	public int findHistoryAboutCarfile(Date startDate,Date endDate,String paiHao,Integer paiSe,Integer createrId,Integer testKind,Integer postType);
	public List<Check> findHistoryAboutCarfilePerPage(Date startDate,Date endDate,String paiHao,int start, int limit,Integer paiSe,Integer createrId,Integer testKind,Integer postType);
	
	public int findHistoryEnterprise(Date startDate,Date endDate,Integer enterpriseId,Integer createrId,Integer testKind);
	public List<Check> findHistoryEnterprisePerPage(Date startDate,Date endDate,int start, int limit,Integer enterpriseId,Integer createrId,Integer testKind);

	public int findHistoryCarowner(Date startDate,Date endDate,Integer carownerId,Integer createrId,Integer testKind);
	public List<Check> findHistoryCarownerPerPage(Date startDate,Date endDate,int start, int limit,Integer carownerId,Integer createrId,Integer testKind);
	
	
	public int findHistoryStandard(Date startDate,Date endDate,Integer heGe,Integer createrId,Integer testKind);
	public List<Check> findHistoryStandardPerPage(Date startDate,Date endDate,int start, int limit,Integer heGe,Integer createrId,Integer testKind);

	
	public Check findByStandardNo(Integer standarNo);
}
