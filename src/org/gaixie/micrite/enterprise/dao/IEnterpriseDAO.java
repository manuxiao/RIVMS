package org.gaixie.micrite.enterprise.dao;

import java.util.Date;
import java.util.List;

import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.beans.Enterprise;
import org.gaixie.micrite.beans.IdName;
import org.gaixie.micrite.dao.IGenericDAO;
/**
 * 客户管理持久化接口
 */
public interface IEnterpriseDAO extends IGenericDAO<Enterprise, Integer> {
	public List<IdName> findEnterpriseByPy(String py);
    /**
     * 高级查询客户
     * @param telephone
     * @param start
     * @param limit
     * @return 客户集合
     */
    public List<Enterprise> advancedFindByPerPage(SearchBean[] queryBean, int start, int limit);

    /**
     * 高级查询客户总记录数
     * @param telephone
     * @return 客户数量
     */
    public int advancedFindCount(SearchBean[] queryBean);

    /**
     * 根据创建日期及EnterpriseSourceType普通查询客户总记录数
     * @param createDate
     * @return
     */
    public int findByCreateDateSpacingCount(Date startDate,Date endDatem,int EnterpriseSourceType);
    /**
     *根据创建日期及EnterpriseSourceType普通查询客户
     * @param createDate
     * @param start
     * @param limit
     * @return
     */
    public List<Enterprise> findByCreateDateSpacingPerPage(Date startDate,Date endDate, int start, int limit,int EnterpriseSourceType);
    /**
     * 高级查询并按用户来源进行分组
     * @param telphone
     * @return 分组数据集合
     */
    public List findCSGroupByTelVague(SearchBean[] queryBean);
    /**
     * 是否存在该license的维修企业
     * @param id 如果不为null 则不包含该id的记录
     * @param license 
     **/
    public boolean existEnterprise(Integer id,String license);
}
