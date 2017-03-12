/* ===========================================================
 * $Id: CustomerSourceDAOImpl.java 520 2009-08-27 05:59:23Z bitorb $
 * This file is part of Micrite
 * ===========================================================
 *
 * (C) Copyright 2009, by Gaixie.org and Contributors.
 * 
 * Project Info:  http://micrite.gaixie.org/
 *
 * Micrite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Micrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Micrite.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.gaixie.micrite.crm.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Carowner;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.IdName;
import org.gaixie.micrite.car.service.ICarfileService;
import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.common.search.SearchFactory;
import org.gaixie.micrite.crm.dao.ICarownerDAO;
import org.gaixie.micrite.crm.service.ICarownerService;
import org.gaixie.micrite.dao.hibernate.GenericDAOImpl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * 客户来源持久化实现，基于hibernate
 * @see org.gaixie.micrite.crm.dao.ICarownerDAO
 */
public class CarownerDAOImpl extends GenericDAOImpl<Carowner, Integer> implements ICarownerDAO {

	@Override
	public List<IdName> findCarownerByPy(String py) {
//	   DetachedCriteria criteria = DetachedCriteria.forClass(Carowner.class);
//       criteria.add(Restrictions.gt("id", 0));
//       criteria.add(Restrictions.like("py",py.trim()));
//       criteria.add(Restrictions.eq("status",ICarownerService.STATUS_NORMAL));
//       List<Carowner> list = getHibernateTemplate().findByCriteria(criteria);
	
       List<IdName> list;
       if(py==null){
    	   list = this.getHibernateTemplate().find("select new org.gaixie.micrite.beans.IdName(id,name) from Carowner where status=?",ICarownerService.STATUS_NORMAL);
       }else{
    	   list = this.getHibernateTemplate().find("select new org.gaixie.micrite.beans.IdName(id,name) from Carowner where py like ? and status=? ",new Object[]{"%"+py+"%",ICarownerService.STATUS_NORMAL});
       }
       return list;
	}

	//	@Override
//	public Carowner getByName(String name) {
//		if(name==null)return null;
//		// TODO Auto-generated method stub
//        DetachedCriteria criteria = DetachedCriteria.forClass(Carowner.class);
//        criteria.add(Restrictions.gt("id", 0));
//        criteria.add(Restrictions.eq("name",name.trim()));
//        criteria.add(Restrictions.eq("status",ICarownerService.STATUS_NORMAL));
//        List<Carowner> list = getHibernateTemplate().findByCriteria(criteria);
//        if(list.size()==0)return null;
//        return list.get(0);
//	}
	@Override
	public Carowner getByLicense(String license) {
		if(license==null)return null;
		// TODO Auto-generated method stub
        DetachedCriteria criteria = DetachedCriteria.forClass(Carowner.class);
        criteria.add(Restrictions.gt("id", 0));
        criteria.add(Restrictions.eq("license",license.trim()));
        criteria.add(Restrictions.eq("status",ICarownerService.STATUS_NORMAL));
        List<Carowner> list = getHibernateTemplate().findByCriteria(criteria);
        if(list.size()==0)return null;
        return list.get(0);
	}

    @SuppressWarnings("unchecked")
    public List<Carowner> advancedFindByPerPage(SearchBean[] queryBean, int start,int limit){
        DetachedCriteria criteria = SearchFactory.generateCriteria(Carowner.class, queryBean);
        criteria.addOrder(Order.desc("editDate"));
        criteria.add(Restrictions.gt("id", 0));
		return getHibernateTemplate().findByCriteria(criteria,start,limit);
    }
//    @SuppressWarnings("unchecked")
//    public List<Carowner> advancedFindByPerPageALL(SearchBean[] queryBean, int start,int limit){
//        DetachedCriteria criteria = SearchFactory.generateCriteria(Carowner.class, queryBean);
//        criteria.addOrder(Order.desc("createDate"));
//        criteria.add(Restrictions.gt("id", 0));
//		return getHibernateTemplate().findByCriteria(criteria,start,limit);
//    }    
    public int advancedFindCount(SearchBean[] queryBean) {
        DetachedCriteria criteria = SearchFactory.generateCriteria(Carowner.class, queryBean);
        criteria.add(Restrictions.gt("id", 0));
        criteria.setProjection(Projections.rowCount());
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }

//    @SuppressWarnings("unchecked")
//	public List<CustomerSource> findAllCustomerSource() {
//    	DetachedCriteria criteria = DetachedCriteria.forClass(CustomerSource.class);
//    	return getHibernateTemplate().findByCriteria(criteria);
//    }
    public int findByCreateDateSpacingCount(Date startDate,Date endDate,int customerSourceType) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Carowner.class);
        criteria.add(Restrictions.gt("id", 0));
//        criteria.createAlias("customerSource", "cs");
//        if(0!=customerSourceType){
//            criteria.add(Expression.eq("cs.id", customerSourceType));
//        }
        criteria.add(Expression.between("createDate", startDate, endDate));
        criteria.setProjection(Projections.rowCount());        
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);       
    }
    @SuppressWarnings("unchecked")
    public List<Carowner> findByCreateDateSpacingPerPage(Date startDate,
            Date endDate, int start, int limit,int customerSourceType) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Carowner.class);
        criteria.add(Restrictions.gt("id", 0));
//        criteria.createAlias("customerSource", "cs");
//        if(0!=customerSourceType){
//            criteria.add(Expression.eq("cs.id", customerSourceType));
//        }
        criteria.add(Expression.between("createDate", startDate, endDate));       
        return getHibernateTemplate().findByCriteria(criteria,start,limit);
    }

    @SuppressWarnings("unchecked")
    public List findCSGroupByTelVague(SearchBean[] queryBean) {
        DetachedCriteria criteria = SearchFactory.generateCriteria(Carowner.class, queryBean);
        criteria.add(Restrictions.gt("id", 0));
//        criteria.createAlias("customerSource", "cs");
//        criteria.setProjection(Projections.projectionList()
//                .add(Projections.count("cs.name"))
//                .add(Projections.groupProperty("cs.name")));
        return getHibernateTemplate().findByCriteria(criteria);
    }  
	@Override
	public boolean existCarowner(Integer id,String license,String name) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Carowner.class);
//		criteria.add(Expression.eq("status", ICarfileService.STATUS_NORMAL));
        criteria.setProjection(Projections.rowCount());
        criteria.add(Expression.eq("name", name));
        criteria.add(Expression.eq("license", license));
        if(id!=null){
        	criteria.add(Expression.ne("id", id));
        }
        return ((Integer)getHibernateTemplate().findByCriteria(criteria).get(0))>0;
	}    
    
}
