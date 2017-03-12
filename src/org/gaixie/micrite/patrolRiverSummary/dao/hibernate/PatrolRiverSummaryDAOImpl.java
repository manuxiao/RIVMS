/* ===========================================================
 * $Id: PatrolRiverSummaryDAOImpl.java 516 2009-08-23 16:33:31Z bitorb $
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

package org.gaixie.micrite.patrolRiverSummary.dao.hibernate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
 import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.common.search.SearchFactory;
import org.gaixie.micrite.beans.PatrolRiverSummary; 
import org.gaixie.micrite.dao.hibernate.GenericDAOImpl;
import org.gaixie.micrite.patrolRiverSummary.dao.IPatrolRiverSummaryDAO;
import org.gaixie.micrite.patrolRiverSummary.service.IPatrolRiverSummaryService;
 import org.gaixie.micrite.util.CalendarUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
/**
 * 车辆档案管理持久化实现，基于hibernate
 * @see org.gaixie.micrite.car.dao.IPatrolRiverSummaryDAO
 */
public class PatrolRiverSummaryDAOImpl extends GenericDAOImpl<PatrolRiverSummary, Integer> implements IPatrolRiverSummaryDAO {
 
	public PatrolRiverSummary get(Integer id){
		PatrolRiverSummary car = super.get(id);
		return car;
	}
	     
	@Override
	public List<PatrolRiverSummary> getExpiredCars() {
 		//List<PatrolRiverSummary> list = getHibernateTemplate().find("from PatrolRiverSummary where expired=? and carStatus=? and status=?",new Object[]{IPatrolRiverSummaryService.EXPIRED_YES,IPatrolRiverSummaryService.CARSTATUS_NORMAL,IPatrolRiverSummaryService.STATUS_NORMAL});
		List<PatrolRiverSummary> list = getHibernateTemplate().find("from PatrolRiverSummary where approveStatus=? and status=?",new Object[]{0,0});	
		return list;
	}

	@SuppressWarnings("unchecked")
    public List<PatrolRiverSummary> advancedFindByPerPage(SearchBean[] queryBean, int start,int limit){
        DetachedCriteria criteria = SearchFactory.generateCriteria(PatrolRiverSummary.class, queryBean);
        criteria.add(Expression.eq("State", IPatrolRiverSummaryService.STATUS_NORMAL));
//        for (int i = 0; i < queryBean.length; i++) {
//			if(queryBean[i]!=null&&queryBean[i].getName()!=null&&queryBean[i].getName().startsWith("carowner.id")){
//				criteria.addOrder(Order.desc("licenseNumber"));
//				break;	
//			}
//		}
        criteria.addOrder(Order.desc("editDate"));
        List<PatrolRiverSummary> list = getHibernateTemplate().findByCriteria(criteria,start,limit);
      
        return list;
    }
    
    public int advancedFindCount(SearchBean[] queryBean) {
        DetachedCriteria criteria = SearchFactory.generateCriteria(PatrolRiverSummary.class, queryBean);
        criteria.add(Expression.eq("State", IPatrolRiverSummaryService.STATUS_NORMAL));
        criteria.setProjection(Projections.rowCount());
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }

    public int findByCreateDateSpacingCount(int carType) {
        DetachedCriteria criteria = DetachedCriteria.forClass(PatrolRiverSummary.class);
        criteria.add(Expression.eq("State", IPatrolRiverSummaryService.STATUS_NORMAL));
//        criteria.createAlias("skillRank", "cs");
//        if(0!=carType){
//            criteria.add(Expression.eq("cs.id", carType));
//        }
        criteria.setProjection(Projections.rowCount());
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }    
    @SuppressWarnings("unchecked")
    public List<PatrolRiverSummary> findByCreateDateSpacingPerPage(int start, int limit,int carType) {
        DetachedCriteria criteria = DetachedCriteria.forClass(PatrolRiverSummary.class);
        criteria.add(Expression.eq("status", IPatrolRiverSummaryService.STATUS_NORMAL));
        criteria.addOrder(Order.desc("editDate"));
        criteria.createAlias("skillRank", "cs");
        if(0!=carType){
            criteria.add(Expression.eq("cs.id", carType));
        }
        List<PatrolRiverSummary> list = getHibernateTemplate().findByCriteria(criteria,start,limit);
        
        return list;
    }
  
    @SuppressWarnings("unchecked")
    public List findCSGroupByTelVague(SearchBean[] queryBean) {
        DetachedCriteria criteria = SearchFactory.generateCriteria(PatrolRiverSummary.class, queryBean);
        criteria.add(Expression.eq("approveStatus", IPatrolRiverSummaryService.STATUS_NORMAL));
        criteria.addOrder(Order.desc("editDate"));
        criteria.createAlias("carType", "cs");
        criteria.setProjection(Projections.projectionList()
                .add(Projections.count("cs.name"))
                .add(Projections.groupProperty("cs.name")));
        List<PatrolRiverSummary> list = getHibernateTemplate().findByCriteria(criteria);
//        desidedExpiredFlag(list);
        return list;
    }

	@Override
	public boolean existPatrolRiverSummary(Integer userid) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PatrolRiverSummary.class);
		criteria.add(Expression.eq("status", IPatrolRiverSummaryService.STATUS_NORMAL));
        criteria.setProjection(Projections.rowCount());
        if(userid!=null){
        	criteria.add(Expression.ne("id", userid));
        }
        return ((Integer)getHibernateTemplate().findByCriteria(criteria).get(0))>0;
	}
 

//	@Override
//	public int getCarId(String carNo, Dictionary carColor) {
//		 List<PatrolRiverSummary> list= getHibernateTemplate().find("from PatrolRiverSummary c where c.licenseNumber like ? and c.paiSe=? and c.status=?",new Object[]{"%"+carNo,carColor,IPatrolRiverSummaryService.STATUS_NORMAL});
//		 if(list.size()!=1)return 0;
//		 return list.get(0).getId();
//	}

}
