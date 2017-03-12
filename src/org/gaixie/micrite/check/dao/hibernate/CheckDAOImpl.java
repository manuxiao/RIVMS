package org.gaixie.micrite.check.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.derby.tools.sysinfo;
import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.common.search.SearchFactory;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Check;
import org.gaixie.micrite.beans.Standard;
import org.gaixie.micrite.car.service.ICarfileService;
import org.gaixie.micrite.check.dao.ICheckDAO;
import org.gaixie.micrite.check.service.ICheckService;
import org.gaixie.micrite.dao.hibernate.GenericDAOImpl;
import org.gaixie.micrite.standard.service.IStandardService;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * 客户管理持久化实现，基于hibernate
 * @see org.gaixie.micrite.check.dao.ICheckDAO
 */
public class CheckDAOImpl extends GenericDAOImpl<Check, Integer> implements ICheckDAO { 
    
    @Override
	public List<Check> getOrigCheckOf(Integer heGe, Date jianTime) {
		// TODO Auto-generated method stub

    	DetachedCriteria criteria = DetachedCriteria.forClass(Check.class);
    	criteria.add(Restrictions.eq("heGe", heGe));
    	criteria.add(Restrictions.eq("jianTime", jianTime));
    	criteria.add(Restrictions.eq("isPost", ICheckService.ISPOST_YES));
    	criteria.add(Restrictions.eq("status", ICheckService.STATUS_NORMAL));
        return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public Check getCheckOf(Integer carfileId,Date jianTime) {
		// TODO Auto-generated method stub
    	DetachedCriteria criteria = DetachedCriteria.forClass(Check.class);
    	criteria.add(Restrictions.eq("carId", carfileId));
    	criteria.add(Restrictions.eq("testKind", ICheckService.TESTKIND_MAIN));
        criteria.add(Restrictions.eq("isPost", ICheckService.ISPOST_NOT));
        if(jianTime==null){
        	criteria.setProjection(Projections.id());
        	criteria.setProjection(Projections.max("jianTime"));
        	List<Check> list=getHibernateTemplate().findByCriteria(criteria);
        	System.out.println(list.get(0));
//        	if(list.size()>0)return get();
            return null;
        }else{
        	criteria.add(Restrictions.eq("jianTime", jianTime));
            List<Check> list=getHibernateTemplate().findByCriteria(criteria);
            if(list.size()>0)return list.get(0);
            return null;
        }
	}

	@Override
	public boolean isUsed(Integer testNo) {
		// TODO Auto-generated method stub
    	DetachedCriteria criteria = DetachedCriteria.forClass(Check.class);
        criteria.setProjection(Projections.rowCount());
        criteria.add(Restrictions.eq("heGe", testNo));
        criteria.add(Restrictions.eq("isPost", ICheckService.ISPOST_NOT));
        return ((Integer)getHibernateTemplate().findByCriteria(criteria).get(0))>0;
	}

	@SuppressWarnings("unchecked")
    public List<Check> advancedFindByPerPage(SearchBean[] queryBean, int start,int limit){
        DetachedCriteria criteria = SearchFactory.generateCriteria(Check.class, queryBean);
        criteria.addOrder(Order.desc("createDate"));
		return getHibernateTemplate().findByCriteria(criteria,start,limit);
    }
    
    public int advancedFindCount(SearchBean[] queryBean) {
        DetachedCriteria criteria = SearchFactory.generateCriteria(Check.class, queryBean);
        criteria.setProjection(Projections.rowCount());
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }

    @SuppressWarnings("unchecked")
    public List<Check> findByCreateDateSpacingPerPage(Date startDate,
            Date endDate, int start, int limit) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Check.class);
        criteria.addOrder(Order.desc("createDate"));
        criteria.add(Expression.between("jianTime", startDate, endDate));       
        return getHibernateTemplate().findByCriteria(criteria,start,limit);
    }

    public int findByCreateDateSpacingCount(Date startDate,Date endDate) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Check.class);
        criteria.add(Expression.between("jianTime", startDate, endDate));
        criteria.setProjection(Projections.rowCount());        
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);       
    } 
    
    
    @SuppressWarnings("unchecked")
	public List<Check> advancedFindByPerPageByCar(Dictionary paiSe,String paiHao,int start,
            int limit){
		DetachedCriteria criteria = DetachedCriteria.forClass(Check.class);
		criteria.addOrder(Order.desc("createDate"));
		criteria.add(Restrictions.eq("paiSe", paiSe));
		criteria.add(Restrictions.eq("paiHao", paiHao));
	    return getHibernateTemplate().findByCriteria(criteria,start,limit); 
    }   
    
	@SuppressWarnings("unchecked")
    public List<Check> findHistoryAboutCarfilePerPage(Date startDate,Date endDate,String paiHao,int start,int limit,Integer paiSe,Integer createrId,Integer testKind,Integer postType){
        DetachedCriteria criteria = DetachedCriteria.forClass(Check.class);           
        criteria.add(Expression.eq("isPost", postType));
        criteria.add(Expression.like("paiHao","%"+paiHao+"%"));  
        if(null!=paiSe)
        {
		criteria.add(Restrictions.eq("paiSe.id", paiSe));
        }
        if(null!=createrId){
		criteria.add(Restrictions.eq("createrId", createrId));
        }
        if(null!=testKind)
        {
		criteria.add(Restrictions.eq("testKind", testKind));	
        }
        criteria.add(Expression.between("createDate", startDate, endDate));
        criteria.addOrder(Order.desc("createDate"));
        return getHibernateTemplate().findByCriteria(criteria,start,limit);
    }  
    public int findHistoryAboutCarfile(Date startDate,Date endDate,String paiHao,Integer paiSe,Integer createrId,Integer testKind,Integer postType) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Check.class);         
        criteria.add(Expression.eq("isPost", postType));
        criteria.add(Expression.like("paiHao","%"+paiHao+"%"));
        if(null!=paiSe)
		criteria.add(Restrictions.eq("paiSe.id", paiSe));
        if(null!=createrId)
		criteria.add(Restrictions.eq("createrId", createrId));
        if(null!=testKind)
		criteria.add(Restrictions.eq("testKind", testKind));
        criteria.add(Expression.between("createDate", startDate, endDate));
        criteria.setProjection(Projections.rowCount());
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }  
    
	@SuppressWarnings("unchecked")
    public List<Check> findHistoryEnterprisePerPage(Date startDate,Date endDate,int start,int limit,Integer enterpriseId,Integer createrId,Integer testKind){
        DetachedCriteria criteria = DetachedCriteria.forClass(Check.class);;
        criteria.add(Expression.eq("isPost", ICheckService.RT_QY));
        if(enterpriseId!=null)
//        if(enterpriseId!=0)
        	criteria.add(Restrictions.eq("status", enterpriseId));
        if(createrId!=null)
    		criteria.add(Restrictions.eq("createrId", createrId));
        if(testKind!=null)
    		criteria.add(Restrictions.eq("testKind", testKind));	
        criteria.add(Expression.between("createDate", startDate, endDate));
        criteria.addOrder(Order.desc("createDate"));
		return getHibernateTemplate().findByCriteria(criteria,start,limit);
    }  
    public int findHistoryEnterprise(Date startDate,Date endDate,Integer enterpriseId,Integer createrId,Integer testKind) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Check.class); 
        criteria.add(Expression.eq("isPost", ICheckService.RT_QY));
        if(enterpriseId!=null)
        	criteria.add(Restrictions.eq("status", enterpriseId));
        if(createrId!=null)
    		criteria.add(Restrictions.eq("createrId", createrId));
        if(testKind!=null)
    		criteria.add(Restrictions.eq("testKind", testKind));	
        criteria.add(Expression.between("createDate", startDate, endDate));
        criteria.setProjection(Projections.rowCount());
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }  

	@SuppressWarnings("unchecked")
    public List<Check> findHistoryCarownerPerPage(Date startDate,Date endDate,int start,int limit,Integer carownerId,Integer createrId,Integer testKind){
        DetachedCriteria criteria = DetachedCriteria.forClass(Check.class);;
        criteria.add(Expression.eq("isPost", ICheckService.RT_CZ));
        if(carownerId!=null)
        	criteria.add(Restrictions.eq("status", carownerId));
        if(createrId!=null)
    		criteria.add(Restrictions.eq("createrId", createrId));
        if(testKind!=null)
    		criteria.add(Restrictions.eq("testKind", testKind));	
        criteria.add(Expression.between("createDate", startDate, endDate));
        criteria.addOrder(Order.desc("createDate"));
		return getHibernateTemplate().findByCriteria(criteria,start,limit);
    }  
    public int findHistoryCarowner(Date startDate,Date endDate,Integer carownerId,Integer createrId,Integer testKind) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Check.class); 
        criteria.add(Expression.eq("isPost", ICheckService.RT_CZ));
        if(carownerId!=null)
        	criteria.add(Restrictions.eq("status", carownerId));
        if(createrId!=null)
    		criteria.add(Restrictions.eq("createrId", createrId));
        if(testKind!=null)
    		criteria.add(Restrictions.eq("testKind", testKind));	
            criteria.add(Expression.between("createDate", startDate, endDate));
        criteria.setProjection(Projections.rowCount());
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }     
    
	@SuppressWarnings("unchecked")
    public List<Check> findHistoryStandardPerPage(Date startDate,Date endDate,int start,int limit,Integer heGe,Integer createrId,Integer testKind){
        DetachedCriteria criteria = DetachedCriteria.forClass(Check.class);;
        criteria.add(Expression.eq("isPost", ICheckService.ISPOST_NOT));
        if(heGe!=null)
        	criteria.add(Restrictions.eq("heGe", heGe));
        if(createrId!=null)
        	criteria.add(Restrictions.eq("createrId", createrId));
        if(testKind!=null)
        	criteria.add(Restrictions.eq("testKind", testKind));	
        criteria.add(Expression.between("createDate", startDate, endDate));
        criteria.addOrder(Order.desc("createDate"));
		return getHibernateTemplate().findByCriteria(criteria,start,limit);
    }  
    public int findHistoryStandard(Date startDate,Date endDate,Integer heGe,Integer createrId,Integer testKind) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Check.class); 
        criteria.add(Expression.eq("isPost", ICheckService.ISPOST_NOT));
        if(heGe!=null)
        criteria.add(Restrictions.eq("heGe", heGe));
        if(createrId!=null)
		criteria.add(Restrictions.eq("createrId", createrId));
        if(testKind!=null)
		criteria.add(Restrictions.eq("testKind", testKind));
        criteria.add(Expression.between("createDate", startDate, endDate));
        criteria.setProjection(Projections.rowCount());
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }
    
	@Override
	public Check findByStandardNo(Integer standarNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Check.class);
		criteria.add(Expression.eq("isPost", ICheckService.ISPOST_NOT));
        criteria.add(Restrictions.ge("numberEnd",standarNo));
        criteria.add(Restrictions.le("numberStart", standarNo));
//        criteria.add(Restrictions.eq("type", IStandardService.TYPE_ENTERPRISE));
//        criteria.add(Restrictions.eq("state", IStandardService.STATUS_NORMAL));
        List<Check> list = getHibernateTemplate().findByCriteria(criteria);
        if(list.size()>0)return list.get(0);
        return null;
	}

	@Override
	public List<Check> findRecentPostCheckOf(Carfile car, int start, int limit) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass(Check.class);
		criteria.add(Expression.eq("isPost", ICheckService.ISPOST_YES));
        criteria.add(Expression.eq("status",ICheckService.STATUS_NORMAL));
        if(car.getMaintainDate()!=null)
        	criteria.add(Expression.ge("jianTime", car.getMaintainDate()));
        criteria.add(Expression.or(Expression.eq("carId", car.getId()),Expression.eq("carId", 0)));
        criteria.addOrder(Order.desc("carId"));
        criteria.addOrder(Order.desc("jianTime"));
        return  getHibernateTemplate().findByCriteria(criteria);
	}
}
