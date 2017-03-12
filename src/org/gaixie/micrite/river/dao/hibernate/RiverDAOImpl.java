package org.gaixie.micrite.river.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.gaixie.micrite.car.service.ICarfileService;
import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.common.search.SearchFactory;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.River;
import org.gaixie.micrite.beans.IdName;
import org.gaixie.micrite.river.dao.IRiverDAO;
import org.gaixie.micrite.river.service.IRiverService;
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
 * @see org.gaixie.micrite.river.dao.IRiverDAO
 */
public class RiverDAOImpl extends GenericDAOImpl<River, Integer> implements IRiverDAO {

    @Override
	public List<IdName> findRiverByPy(String py) {
		// TODO Auto-generated method stub
    	List<IdName> list;
        if(py==null){
    	   list = this.getHibernateTemplate().find("select new org.gaixie.micrite.beans.IdName(id,Rname) from River where State=?",IRiverService.STATUS_NORMAL);
        }else{
     	   list = this.getHibernateTemplate().find("select new org.gaixie.micrite.beans.IdName(id,Rname) from River where py like ? and State=? ",new Object[]{"%"+py+"%",IRiverService.STATUS_NORMAL});
        }
        return list;
	}

	@SuppressWarnings("unchecked")
    public List<River> advancedFindByPerPage(SearchBean[] queryBean, int start,int limit){
        DetachedCriteria criteria = SearchFactory.generateCriteria(River.class, queryBean);
        criteria.add(Restrictions.ne("id", SYS_RECORD_ID));
        criteria.add(Expression.eq("status", IRiverService.STATUS_NORMAL));
        criteria.addOrder(Order.desc("modifydate"));
		return getHibernateTemplate().findByCriteria(criteria,start,limit);
    }
    
    public int advancedFindCount(SearchBean[] queryBean) {
        DetachedCriteria criteria = SearchFactory.generateCriteria(River.class, queryBean);
        criteria.add(Restrictions.ne("id", SYS_RECORD_ID));
        criteria.add(Expression.eq("State", IRiverService.STATUS_NORMAL));
        criteria.setProjection(Projections.rowCount());
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }

    public int findByCreateDateSpacingCount(Date startDate,Date endDate,int RiverSourceType) {
        DetachedCriteria criteria = DetachedCriteria.forClass(River.class);
//        criteria.createAlias("riverSource8", "cs");
//        if(0!=RiverSourceType){
//            criteria.add(Expression.eq("cs.id", RiverSourceType));
//        }
        criteria.add(Expression.between("begindate", startDate, endDate));
        criteria.add(Restrictions.ne("id", 1));
        criteria.setProjection(Projections.rowCount());        
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);       
    }
    @SuppressWarnings("unchecked")
    public List<River> findByCreateDateSpacingPerPage(Date startDate,
            Date endDate, int start, int limit,int villagId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(River.class);
//        criteria.createAlias("riverSource8", "cs");
//        if(0!=RiverSourceType){
//            criteria.add(Expression.eq("cs.id", RiverSourceType));
//        }
        criteria.add(Expression.between("begindate", startDate, endDate));
        //criteria.add(Restrictions.ne("id", SYS_RECORD_ID));       
        return getHibernateTemplate().findByCriteria(criteria,start,limit);
    }

    @SuppressWarnings("unchecked")
    public List findCSGroupByTelVague(SearchBean[] queryBean) {
        DetachedCriteria criteria = SearchFactory.generateCriteria(River.class, queryBean);
        criteria.add(Restrictions.ne("id", SYS_RECORD_ID));
        criteria.createAlias("village", "cs");
        criteria.setProjection(Projections.projectionList()
                .add(Projections.count("cs.vname"))
                .add(Projections.groupProperty("cs.vname")));
        return getHibernateTemplate().findByCriteria(criteria);
    }
	@Override
	public boolean existRiver(Integer id,String license) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass(River.class);
		criteria.add(Expression.eq("State", IRiverService.STATUS_NORMAL));
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.ne("id", SYS_RECORD_ID));
		criteria.add(Expression.eq("rid", license));
		if(id!=null){
        	criteria.add(Expression.ne("id", id));
        }
		return ((Integer)getHibernateTemplate().findByCriteria(criteria).get(0))>0;
	} 
    
}
