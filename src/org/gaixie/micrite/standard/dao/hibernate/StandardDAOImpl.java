package org.gaixie.micrite.standard.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.gaixie.micrite.beans.Enterprise;
import org.gaixie.micrite.beans.Standard;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.common.search.SearchFactory;
import org.gaixie.micrite.dao.hibernate.GenericDAOImpl;
import org.gaixie.micrite.standard.dao.IStandardDAO;
import org.gaixie.micrite.standard.service.IStandardService;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.context.SecurityContextHolder;


public class StandardDAOImpl extends GenericDAOImpl<Standard,Integer> implements IStandardDAO {
	
	@Override
	public Object[] remainStatistics(Enterprise ent) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass(Standard.class);
//       criteria.add(Expression.between("createDate", startDate, endDate));
       ProjectionList projectionList = Projections.projectionList();
       projectionList.add(Projections.count("id"));
       projectionList.add(Projections.sum("remain"));
//       projectionList.add(Projections.sum("sum"));
       criteria.setProjection(projectionList);// Criteria投影distinct查询
       criteria.add(Expression.eq("enterpriseId", ent.getId()));
       criteria.add(Expression.ne("type", IStandardService.TYPE_FREE));
      	List list = getHibernateTemplate().findByCriteria(criteria);
      	return ((Object[])list.get(0));
	}
	@Override
	public void save(Standard s){
		s.setCreateDate(new Date());
		s.setCreaterId(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    	super.save(s);
	}
	@Override
	public int getFreeId() {
		// TODO Auto-generated method stub
		List<Object> obj = getHibernateTemplate().find("select max(s.id) from Standard s");
		if(obj.get(0)==null)return 1;
		return ((Integer)obj.get(0))+1;
	}

	@Override
	public boolean isExist(Integer standarNo) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass(Standard.class);
        criteria.setProjection(Projections.rowCount());
        criteria.add(Restrictions.ge("numberEnd",standarNo));
        criteria.add(Restrictions.le("numberStart", standarNo));
        criteria.add(Restrictions.eq("type", IStandardService.TYPE_ENTERPRISE));     
        criteria.add(Restrictions.eq("state", IStandardService.STATUS_NORMAL));
        return ((Integer)getHibernateTemplate().findByCriteria(criteria).get(0))>0;
	}

	@Override
	public Standard findByStandardNo(Integer standarNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Standard.class);
        criteria.add(Restrictions.ge("numberEnd",standarNo));
        criteria.add(Restrictions.le("numberStart", standarNo));
        criteria.add(Restrictions.eq("type", IStandardService.TYPE_ENTERPRISE));
        criteria.add(Restrictions.eq("state", IStandardService.STATUS_NORMAL));
        List<Standard> list = getHibernateTemplate().findByCriteria(criteria);
        if(list.size()>0)return list.get(0);
        return null;
	}

	@SuppressWarnings("unchecked")
	public Standard getByStandardNo(Integer standarNo) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass(Standard.class);
        criteria.add(Restrictions.ge("numberEnd",standarNo));
        criteria.add(Restrictions.le("numberStart", standarNo));
        criteria.add(Restrictions.eq("type", IStandardService.TYPE_ENTERPRISE));
        criteria.add(Restrictions.eq("state", IStandardService.STATUS_NORMAL));
        List<Standard> list = getHibernateTemplate().findByCriteria(criteria);
        if(list.size()<1)return null;
        return list.get(0);
	}

	@SuppressWarnings("unchecked")
    public List<Standard> findStandardByQueryPerPage(SearchBean[] queryBean, int start,int limit){
        DetachedCriteria criteria = SearchFactory.generateCriteria(Standard.class, queryBean);
        criteria.addOrder(Order.desc("createDate"));
        return getHibernateTemplate().findByCriteria(criteria,start,limit); 
    }
	public int findStandardByQuery(SearchBean[] queryBean) {
		DetachedCriteria criteria = SearchFactory.generateCriteria(Standard.class, queryBean);
        criteria.setProjection(Projections.rowCount());
	    return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }
	
	@SuppressWarnings("unchecked")
    public List<Standard> advancedFindByPerPage(Date startDate,Date endDate,Integer enterpriseId, int start,int limit){
        DetachedCriteria criteria = DetachedCriteria.forClass(Standard.class);
        if(enterpriseId!=null)
        	criteria.add(Restrictions.eq("enterprise.id", enterpriseId));
        criteria.add(Expression.between("createDate", startDate, endDate));
        criteria.addOrder(Order.desc("createDate"));
        return getHibernateTemplate().findByCriteria(criteria,start,limit); 
    }
	public int advancedFindCount(Date startDate,Date endDate,Integer enterpriseId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Standard.class);
        if(enterpriseId!=null)
        	criteria.add(Restrictions.eq("enterprise.id", enterpriseId));
		criteria.add(Expression.between("createDate", startDate, endDate));
        criteria.setProjection(Projections.rowCount());
	    return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }

	@SuppressWarnings("unchecked")
	public List<Enterprise> findAllEnterprise(int type) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Enterprise.class);  	
		criteria.add(Restrictions.eq("type", type));	   	
		return getHibernateTemplate().findByCriteria(criteria);
    }

	public int findByCreateDateSpacingCount(Date startDate, Date endDate,
			int state) {
		 DetachedCriteria criteria = DetachedCriteria.forClass(Standard.class);
	
	        criteria.add(Expression.eq("state", state));
	        
	        criteria.add(Expression.between("createDate", startDate, endDate));
	        criteria.setProjection(Projections.rowCount());
	        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Standard> findByCreateDateSpacingPerPage(Date startDate,
			Date endDate, int start, int limit, int state) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Standard.class);
		criteria.add(Expression.eq("state", state));
        criteria.add(Expression.between("createDate", startDate, endDate));
        return getHibernateTemplate().findByCriteria(criteria,start,limit);
	}

	@SuppressWarnings("unchecked")
	public List<Standard> findCSGroupByTelVague(SearchBean[] queryBean) {
		DetachedCriteria criteria = SearchFactory.generateCriteria(Standard.class, queryBean);
        criteria.createAlias("enterprise", "cs");
        criteria.setProjection(Projections.projectionList()
                .add(Projections.count("cs.name"))
                .add(Projections.groupProperty("cs.name")));
        return getHibernateTemplate().findByCriteria(criteria);
	}

//	@Override
//	public Enterprise getEnterprise(int id) {
//		return (Enterprise) getHibernateTemplate().get(Enterprise.class, id);
//	}
	@SuppressWarnings("unchecked")
	public List<Standard> findByType(int type) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Standard.class);
		Integer typeC = Integer.valueOf(type);
		criteria.add(Expression.eq("type", typeC));
		criteria.add(Expression.gt("remain", 0));
		criteria.add(Expression.eq("state", IStandardService.STATUS_NORMAL));
		criteria.addOrder(Order.asc("createDate"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public int findByShorthandCount(String key) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Standard.class);
		String keyLike = "%"+key+"%";
        criteria.add(Expression.eq("state", IStandardService.STATUS_NORMAL));
        criteria.createAlias("enterprise", "e");
        criteria.add(Expression.ne("e.id", SYS_RECORD_ID));
        criteria.add(Expression.or(Expression.like("e.py", keyLike), Expression.like("e.unitName", keyLike)));
        criteria.setProjection(Projections.rowCount());
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Standard> findByShorthandPerPage(String key, int start,
			int limit) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Standard.class);
		String keyLike = "%"+key+"%";
		criteria.add(Expression.eq("state", IStandardService.STATUS_NORMAL));
		criteria.createAlias("enterprise", "e");
		criteria.add(Expression.ne("e.id", SYS_RECORD_ID));
		criteria.add(Expression.or(Expression.like("e.py", keyLike), Expression.like("e.unitName", keyLike)));
		return getHibernateTemplate().findByCriteria(criteria,start,limit);
	}

//	@Override
//	public int getTwoNumbers(String differ) {
//		DetachedCriteria criteria = DetachedCriteria.forClass(Standard.class);
//		criteria.add(Expression.eq("state", IStandardService.STATUS_NORMAL));
//		criteria.add(Restrictions.eq("type", IStandardService.TYPE_CHEGUANSUO));
//		criteria.add(Expression.gt("remain", 0));
//		criteria.addOrder(Order.asc("createDate"));
//		List<Standard> standards = getHibernateTemplate().findByCriteria(criteria);
//		if(standards.size()!=0) {
//			Standard s = standards.get(0);
//			if(differ.equals("start")) return s.getNumberStart()+s.getPay();
//			else if(differ.equals("end")) return s.getNumberEnd();
//		}
//		return 0;
//	}
	
	/***每个企业一条*/
	@SuppressWarnings("unchecked")
    public List<Standard> findStandardSinglePerPage(SearchBean[] queryBean, int start,int limit){
        DetachedCriteria criteria = SearchFactory.generateCriteria(Standard.class, queryBean);
//        criteria.add(Expression.between("createDate", startDate, endDate));
        criteria.addOrder(Order.desc("createDate"));
        ProjectionList projectionList = Projections.projectionList();  
        projectionList.add(Projections.groupProperty("enterpriseId")); 
        criteria.add(Expression.ne("type", IStandardService.TYPE_FREE));
        projectionList.add(Projections.id());
        projectionList.add(Projections.count("id"));  
        projectionList.add(Projections.sum("remain"));
        projectionList.add(Projections.sum("sum"));
        criteria.setProjection(Projections.distinct(projectionList));// Criteria投影distinct查询         
       	List list = getHibernateTemplate().findByCriteria(criteria,start,limit);
       
       List<Standard> listRet = new ArrayList<Standard>();
		try {
			for (int i = 0; i < list.size(); i++) {

				Object[] values = (Object[]) list.get(i);
//				System.out.println(values[1]);
				Standard sorg = get((Integer)values[1]);
				Standard s = (Standard)BeanUtils.cloneBean(sorg);
				s.setPay((Integer)values[2]);
				s.setRemain((Integer)values[3]);
				s.setSum((Integer)values[4]);
				listRet.add(s);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}			
       return listRet;
//		 List<Standard> list= getHibernateTemplate().find("select * from Standard s where not exists (select 1 from Standard where enterpriseId=s.enterpriseId and id<s.id)",new Object[]{queryBean,start,limit});
//		 return list; 
    }

//	public int findStandardSingle(Date startDate,Date endDate,SearchBean[] queryBean) {
	public int findStandardSingle(SearchBean[] queryBean) {
		DetachedCriteria criteria = SearchFactory.generateCriteria(Standard.class, queryBean);		        
        criteria.setProjection(Projections.countDistinct("enterpriseId"));// select count(distcint enterpriseId) from ..            
//        criteria.setProjection(Projections.rowCount());
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//        criteria.add(Expression.between("createDate", startDate, endDate));
	    return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }
	@Override
	public boolean existWhenAddIn(int sNo){
		Criteria criteria = this.getSession().createCriteria(Standard.class);
		criteria.add(Restrictions.eq("type", IStandardService.TYPE_CHEGUANSUO));
		criteria.add(Expression.le("numberStart", sNo));
		criteria.add(Expression.ge("numberEnd", sNo));
        criteria.setProjection(Projections.rowCount());
        
        return ((Integer)criteria.list().get(0))>0;
	}
}
