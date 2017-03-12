package org.gaixie.micrite.enterprise.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.gaixie.micrite.car.service.ICarfileService;
import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.common.search.SearchFactory;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Enterprise;
import org.gaixie.micrite.beans.IdName;
import org.gaixie.micrite.enterprise.dao.IEnterpriseDAO;
import org.gaixie.micrite.enterprise.service.IEnterpriseService;
import org.gaixie.micrite.dao.hibernate.GenericDAOImpl;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

/**
 * 客户管理持久化实现，基于hibernate
 * @see org.gaixie.micrite.enterprise.dao.IEnterpriseDAO
 */
public class EnterpriseDAOImpl extends GenericDAOImpl<Enterprise, Integer> implements IEnterpriseDAO {

    @Override
	public List<IdName> findEnterpriseByPy(String py) {
		// TODO Auto-generated method stub
    	List<IdName> list;
        if(py==null){
    	   list = this.getHibernateTemplate().find("select new org.gaixie.micrite.beans.IdName(id,unitName) from Enterprise where status=?",IEnterpriseService.STATUS_NORMAL);
        }else{
     	   list = this.getHibernateTemplate().find("select new org.gaixie.micrite.beans.IdName(id,unitName) from Enterprise where py like ? and status=? ",new Object[]{"%"+py+"%",IEnterpriseService.STATUS_NORMAL});
        }
        return list;
	}

	@SuppressWarnings("unchecked")
    public List<Enterprise> advancedFindByPerPage(SearchBean[] queryBean, int start,int limit){
        DetachedCriteria criteria = SearchFactory.generateCriteria(Enterprise.class, queryBean);
        criteria.add(Restrictions.ne("id", SYS_RECORD_ID));
        criteria.add(Expression.eq("status", IEnterpriseService.STATUS_NORMAL));
        criteria.addOrder(Order.desc("editDate"));
		return getHibernateTemplate().findByCriteria(criteria,start,limit);
    }
    
    public int advancedFindCount(SearchBean[] queryBean) {
        DetachedCriteria criteria = SearchFactory.generateCriteria(Enterprise.class, queryBean);
        criteria.add(Restrictions.ne("id", SYS_RECORD_ID));
        criteria.add(Expression.eq("status", IEnterpriseService.STATUS_NORMAL));
        criteria.setProjection(Projections.rowCount());
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }

    public int findByCreateDateSpacingCount(Date startDate,Date endDate,int EnterpriseSourceType) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Enterprise.class);
        criteria.createAlias("enterpriseSource8", "cs");
        if(0!=EnterpriseSourceType){
            criteria.add(Expression.eq("cs.id", EnterpriseSourceType));
        }
        criteria.add(Expression.between("createDate", startDate, endDate));
        criteria.add(Restrictions.ne("id", 1));
        criteria.setProjection(Projections.rowCount());        
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);       
    }
    @SuppressWarnings("unchecked")
    public List<Enterprise> findByCreateDateSpacingPerPage(Date startDate,
            Date endDate, int start, int limit,int EnterpriseSourceType) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Enterprise.class);
        criteria.createAlias("enterpriseSource8", "cs");
        if(0!=EnterpriseSourceType){
            criteria.add(Expression.eq("cs.id", EnterpriseSourceType));
        }
        criteria.add(Expression.between("createDate", startDate, endDate));
        criteria.add(Restrictions.ne("id", SYS_RECORD_ID));       
        return getHibernateTemplate().findByCriteria(criteria,start,limit);
    }

    @SuppressWarnings("unchecked")
    public List findCSGroupByTelVague(SearchBean[] queryBean) {
        DetachedCriteria criteria = SearchFactory.generateCriteria(Enterprise.class, queryBean);
        criteria.add(Restrictions.ne("id", SYS_RECORD_ID));
        criteria.createAlias("qualification", "cs");
        criteria.setProjection(Projections.projectionList()
                .add(Projections.count("cs.name"))
                .add(Projections.groupProperty("cs.name")));
        return getHibernateTemplate().findByCriteria(criteria);
    }
	@Override
	public boolean existEnterprise(Integer id,String license) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass(Enterprise.class);
		criteria.add(Expression.eq("status", IEnterpriseService.STATUS_NORMAL));
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.ne("id", SYS_RECORD_ID));
		criteria.add(Expression.eq("license", license));
		if(id!=null){
        	criteria.add(Expression.ne("id", id));
        }
		return ((Integer)getHibernateTemplate().findByCriteria(criteria).get(0))>0;
	} 
    
}
