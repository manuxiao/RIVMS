package org.gaixie.micrite.standard.dao;

import java.util.Date;
import java.util.List;

import org.gaixie.micrite.beans.Enterprise;
import org.gaixie.micrite.beans.Standard;
import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.dao.IGenericDAO;

public interface IStandardDAO extends IGenericDAO<Standard, Integer> {
	public Object[] remainStatistics(Enterprise ent);
	public List<Standard> findStandardByQueryPerPage(SearchBean[] queryBean, int start, int limit);
	public int findStandardByQuery(SearchBean[] queryBean);
	
	public List<Standard> advancedFindByPerPage(Date startDate,Date endDate,Integer enterpriseId, int start, int limit);
	public int advancedFindCount(Date startDate,Date endDate,Integer enterpriseId);
	public List<Enterprise> findAllEnterprise(int type);
	public int findByCreateDateSpacingCount(Date startDate,Date endDate,int state);
	public List<Standard> findByCreateDateSpacingPerPage(Date startDate,Date endDate, int start, int limit,int state);
	public List<Standard> findCSGroupByTelVague(SearchBean[] queryBean);
//	public Enterprise getEnterprise(int id);
	public List<Standard> findByType(int type);
	/**入库前查询是否重复*/
	public boolean existWhenAddIn(int sNo);
	/**需要预先知道的情况下，获取可用的id*/
	public int getFreeId();
	
	/**
	 * 合格证号对应的企业记录中
	 * */
	public Standard getByStandardNo(Integer standarNo);
	/**
	 * 合格证号是否存在于发出给企业的合格证范围内
	 * */
	public boolean isExist(Integer standarNo);
	public Standard findByStandardNo(Integer standarNo);
	public int findByShorthandCount(String key);
	public List<Standard> findByShorthandPerPage(String key, int start, int limit);
//	public int getTwoNumbers(String differ);
	
    /**distinct查找企业*/
    public List<Standard> findStandardSinglePerPage(SearchBean[] queryBean, int start, int limit);    
    public int findStandardSingle(SearchBean[] queryBean);
    public void save(Standard s);
}
