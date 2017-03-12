
package org.gaixie.micrite.standard.service;

import java.util.Date;
import java.util.List;

import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.beans.Enterprise;
import org.gaixie.micrite.beans.Standard;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;


public interface IStandardService {
	/**领入的批次*/
	public static final Integer TYPE_CHEGUANSUO = 0;
	/**领走的区间*/
	public static final Integer TYPE_ENTERPRISE = 1;
	/**可用区间*/
	public static final Integer TYPE_FREE = 2;
	/**状态为正常*/
	public static final Integer STATUS_NORMAL = 0;
	/**状态为删除*/
	public static final Integer STATUS_DELETED = 1;
	/**统计企业当前剩余合格证张数
	 * @return [0]为记录条数，即领取次数；[1]为剩余;如不存在该维修企业领取记录，则记录条数0，剩余null
	 * */
	public Object[] remainStatistics(Enterprise ent);
	/**入库前查询是否重复*/
	public boolean existWhenAddIn(int sNo);
	/**
	 * 数量减一
	 * */
	public void minus(Integer testNo);
	/**
	 * 该合格证是否存在是否发给维修企业
	 * */
	public boolean isExist(Integer standarNo);
	public Standard findByStandardNo(Integer standarNo);
    /**新增一条记录**/
    public int add(Standard standard) throws Exception;
    
    //分页查找
    public List<Standard> findStandardByQueryPerPage(SearchBean[] queryBean, int start, int limit);    
    public int findStandardByQuery(SearchBean[] queryBean);  
    
    public List<Standard> advancedFindByPerPage(Date startDate,Date endDate,Integer enterpriseId, int start, int limit);    
    public int advancedFindCount(Date startDate,Date endDate,Integer enterpriseId);
    
    //删除选中的记录
    public void delete(int[] standardIds);
    
    public void edit(int[] standardIds);
    //根据type查找外键
    public List<Enterprise> findALLEnterprise(int type);
    //根据日期查找
    public int findByCreateDateSpacingCount(Date startDate,Date endDate,int standardState);
 
    public List<Standard> findByCreateDateSpacingPerPage(Date startDate,Date endDate, int start, int limit,int standardState);
   
    public CategoryDataset getEnterpriseBarDataset(SearchBean[] queryBean);
   
    public PieDataset getEnterprisePieDataset(SearchBean[] queryBean);
    
    public int update(Standard standard) throws Exception;
    
    public List<Standard> findByType(int type);
    //企业关键字查找
    public int findByShorthandCount(String key);
    
    public List<Standard> findByShorthandPerPage(String key, int start, int limit);
    //获取起始号码和结束号码
//    public int getTwoNumbers(String differ);
    
    /**distinct查找企业*/
    public List<Standard> findStandardSinglePerPage(SearchBean[] queryBean, int start, int limit);    
    public int findStandardSingle(SearchBean[] queryBean);
}
