package org.gaixie.micrite.enterprise.service;

import java.util.Date;
import java.util.List;

import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.Enterprise;
import org.gaixie.micrite.beans.IdName;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

/**
 * 企业管理服务接口，封装企业服务业务模型
 */
public interface IEnterpriseService {
	/**
	 * 企业中的元数据
	 * */
	public final static int SYS_ENT_ID=0;
	/**维修企业表中表示公管所的记录id*/
	public final static int GONGGUANSUO_ID = 0;

	/**记录状态：正常*/
	public final static int STATUS_NORMAL = 0;
	/**记录状态：已删除*/
	public final static int STATUS_DELETED = 1;
//	/**维修企业状态：营业*/
//	public final static int ENTERPRISESTATUS_NORMAL = 0;
//	/**维修企业状态：临时营运*/
//	public final static int ENTERPRISESTATUS_TEMP = 1;
//	/**维修企业状态：停业*/
//	public final static int ENTERPRISESTATUS_TY = 2;
//	/**维修企业状态：整改*/
//	public final static int ENTERPRISESTATUS_ZG = 3;
//	/**维修企业状态：停业整顿*/
//	public final static int ENTERPRISESTATUS_TYZD = 4;
//	/**维修企业状态：歇业*/
//	public final static int ENTERPRISESTATUS_XY = 5;
//	/**维修企业状态：注销*/
//	public final static int ENTERPRISESTATUS_DIED = 6;
//	/**维修企业档案事件：新增/营业*/
//	public final static int ENTPRISE_EVENT_NORMAL = 0;
//	/**维修企业档案事件：临时营运*/
//	public final static int ENTPRISE_EVENT_TEMP = 1;
//	/**维修企业档案事件：停业*/
//	public final static int ENTPRISE_EVENT_TY = 2;
//	/**维修企业档案事件：整改*/
//	public final static int ENTPRISE_EVENT_ZG = 3;
//	/**维修企业档案事件：停业整顿*/
//	public final static int ENTPRISE_EVENT_TYZD = 4;
//	/**维修企业档案事件：歇业*/
//	public final static int ENTPRISE_EVENT_XY = 5;
//	/**维修企业档案事件：注销*/
//	public final static int ENTPRISE_EVENT_DIED = 6;
	/**
	 * 是否存在该经营许可证号的维修企业记录
	 * id不为空的话，不包含这个id
	 * 包含所有状态、即停业、整顿、取缔的企业；
	 * */
	public boolean existEnterprise(Enterprise enterprise);
	public boolean existEnterprise(Integer id,String license);
    /**
     * 新增企业
     * @param Enterprise 企业实体
     */
    public void add(Enterprise enterprise);
    
    /**
     * 高级查询企业
     * @param telephone
     * @param start
     * @param limit
     * @return 企业集合
     */
    public List<Enterprise> advancedFindByPerPage(SearchBean[] queryBean, int start, int limit);
    public List<IdName> findEnterpriseByPy(String py);
    /**
     * 高级查询企业总记录数
     * @param telephone
     * @return 企业数量
     */
    public int advancedFindCount(SearchBean[] queryBean);
    
   /**
 * 删除企业
 * @param EnterpriseIds 企业的id数组
 */
public void delete(int[] EnterpriseIds);
    
    /**
     * 日期间隔及EnterpriseSourceType普通查询企业总记录数
     * @param createDate
     * @return
     */
    public int findByCreateDateSpacingCount(Date startDate,Date endDate,int EnterpriseSourceType);
    /**
     * 日期间隔查及EnterpriseSourceType普通询企业 
     * @param createDate
     * @param start
     * @param limit
     * @return
     */
    public List<Enterprise> findByCreateDateSpacingPerPage(Date startDate,Date endDate, int start, int limit,int EnterpriseSourceType);
    /**
     * 高级查询获取用户来源的2D柱图对象
     * @return CategoryDataset
     */
    public CategoryDataset getEnterpriseSourceBarDataset(SearchBean[] queryBean);
    /**
	    * 高级查询获取用户来源的饼图对象
	    * @param telephone
	    * @return PieDataset
	    */
	    public PieDataset getEnterpriseSourcePieDataset(SearchBean[] queryBean);
    /**
     * 修改企业信息
     * @param Enterprise 企业实体
     */
    public void update(Enterprise enterprise);
    public Enterprise get(Integer id);
}
