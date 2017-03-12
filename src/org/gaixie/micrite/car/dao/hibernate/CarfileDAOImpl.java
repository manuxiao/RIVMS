/* ===========================================================
 * $Id: CarfileDAOImpl.java 516 2009-08-23 16:33:31Z bitorb $
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

package org.gaixie.micrite.car.dao.hibernate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.gaixie.micrite.check.service.ICheckService;
import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.common.search.SearchFactory;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.Check;
import org.gaixie.micrite.beans.Standard;
import org.gaixie.micrite.car.dao.ICarfileDAO;
import org.gaixie.micrite.car.service.ICarfileService;
import org.gaixie.micrite.dao.hibernate.GenericDAOImpl;
import org.gaixie.micrite.dic.dao.IDictionaryDAO;
import org.gaixie.micrite.dic.service.IDictionaryService;
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
 * @see org.gaixie.micrite.car.dao.ICarfileDAO
 */
public class CarfileDAOImpl extends GenericDAOImpl<Carfile, Integer> implements ICarfileDAO {
	@Autowired
	private IDictionaryDAO dictionaryDAO;
	
	public Carfile get(Integer id){
		Carfile car = super.get(id);
		desidedExpiredFlag(car);
		return car;
	}
	private void desidedExpiredFlag(List<Carfile> list){
		int days = dictionaryDAO.get(IDictionaryService.EXPIRED_PERMISSION_ID).getValue();
		
		Date now = null;
		try {
			now=CalendarUtil.df.parse(CalendarUtil.df.format(new Date()));	
		} catch (Exception e) {
			// TODO: handle exception
			Log.error(e);
		}
		for (int i = 0; i < list.size(); i++) {
			_setExpiredFlag(list.get(i),now,days);
		}
	}
	@Override
	public void desidedExpiredFlag(Carfile car){
		int days = dictionaryDAO.get(IDictionaryService.EXPIRED_PERMISSION_ID).getValue();
		
		Date now = null;
		try {
			now=CalendarUtil.df.parse(CalendarUtil.df.format(new Date()));	
		} catch (Exception e) {
			// TODO: handle exception
			Log.error(e);
		}
		_setExpiredFlag(car,now,days);
	}
	final static void _setExpiredFlag(Carfile car,Date now,int days){
		if(car.getMaintainDateEnd()==null){
			car.setExpired(ICarfileService.EXPIRED_NOT);
			return;
		}
		if(now.after(car.getMaintainDateEnd())){
			car.setExpired(ICarfileService.EXPIRED_YES);
			if(now.after(CalendarUtil.afterDay(car.getMaintainDateEnd(), days))){
				car.setExpired(ICarfileService.EXPIRED_SO);
			}
		}else{
			car.setExpired(ICarfileService.EXPIRED_NOT);
		}
	}
	@Override
	public int findCountByMaintainDateWillExpired(Integer carownerId,int fromDays,int toDays) {
		// TODO Auto-generated method stub
    	DetachedCriteria criteria = DetachedCriteria.forClass(Carfile.class);
    	if(carownerId!=null)
    		criteria.add(Expression.eq("carowner.id", carownerId));
    	criteria.add(Expression.eq("carStatus", ICarfileService.CARSTATUS_NORMAL));
    	criteria.add(Expression.eq("status", ICarfileService.STATUS_NORMAL));
        criteria.add(Expression.between("daysToExpired", fromDays, toDays));
        criteria.setProjection(Projections.rowCount());
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
	}
	@Override
	public List<Carfile> findByMaintainDateWillExpired(Integer carownerId,int fromDays,
			int toDays,int start,int limit) {
		// TODO Auto-generated method stub
    	DetachedCriteria criteria = DetachedCriteria.forClass(Carfile.class);
    	if(carownerId!=null)
    		criteria.add(Expression.eq("carowner.id", carownerId));
    	criteria.add(Expression.eq("carStatus", ICarfileService.CARSTATUS_NORMAL));
    	criteria.add(Expression.eq("status", ICarfileService.STATUS_NORMAL));
    	criteria.addOrder(Order.asc("daysToExpired"));
        criteria.add(Expression.between("daysToExpired", fromDays, toDays));
        List<Carfile> list = getHibernateTemplate().findByCriteria(criteria,start,limit);
        desidedExpiredFlag(list);
        return list;
	}
    
	@Override
	public List<Carfile> getExpiredCars() {
		// TODO Auto-generated method stub
		List<Carfile> list = getHibernateTemplate().find("from Carfile where expired=? and carStatus=? and status=?",new Object[]{ICarfileService.EXPIRED_YES,ICarfileService.CARSTATUS_NORMAL,ICarfileService.STATUS_NORMAL});
		desidedExpiredFlag(list);
        return list;
	}
    @Override
	public List<Carfile> getActiveCars() {
		// TODO Auto-generated method stub
		List<Carfile> list = getHibernateTemplate().find("from Carfile where carStatus=? and status=?",new Object[]{ICarfileService.CARSTATUS_NORMAL,ICarfileService.STATUS_NORMAL});
		desidedExpiredFlag(list);
        return list;
	}

	@SuppressWarnings("unchecked")
    public List<Carfile> advancedFindByPerPage(SearchBean[] queryBean, int start,int limit){
        DetachedCriteria criteria = SearchFactory.generateCriteria(Carfile.class, queryBean);
        criteria.add(Expression.eq("status", ICarfileService.STATUS_NORMAL));
        for (int i = 0; i < queryBean.length; i++) {
			if(queryBean[i]!=null&&queryBean[i].getName()!=null&&queryBean[i].getName().startsWith("carowner.id")){
				criteria.addOrder(Order.desc("licenseNumber"));
				break;	
			}
		}
        criteria.addOrder(Order.desc("editDate"));
        List<Carfile> list = getHibernateTemplate().findByCriteria(criteria,start,limit);
        desidedExpiredFlag(list);
        return list;
    }
    
    public int advancedFindCount(SearchBean[] queryBean) {
        DetachedCriteria criteria = SearchFactory.generateCriteria(Carfile.class, queryBean);
        criteria.add(Expression.eq("status", ICarfileService.STATUS_NORMAL));
        criteria.setProjection(Projections.rowCount());
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }

    public int findByCreateDateSpacingCount(int carType) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Carfile.class);
        criteria.add(Expression.eq("status", ICarfileService.STATUS_NORMAL));
        criteria.createAlias("skillRank", "cs");
        if(0!=carType){
            criteria.add(Expression.eq("cs.id", carType));
        }
        criteria.setProjection(Projections.rowCount());
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }    
    @SuppressWarnings("unchecked")
    public List<Carfile> findByCreateDateSpacingPerPage(int start, int limit,int carType) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Carfile.class);
        criteria.add(Expression.eq("status", ICarfileService.STATUS_NORMAL));
        criteria.addOrder(Order.desc("editDate"));
        criteria.createAlias("skillRank", "cs");
        if(0!=carType){
            criteria.add(Expression.eq("cs.id", carType));
        }
        List<Carfile> list = getHibernateTemplate().findByCriteria(criteria,start,limit);
        desidedExpiredFlag(list);
        return list;
    }
    
    /**根据技术等级和车辆类型

     */
    public int findCarfileBySkill(int carType,int skill) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Carfile.class);
        criteria.add(Expression.eq("status", ICarfileService.STATUS_NORMAL));
        
//        criteria.add(Expression.like("licenseNumber","%"+licenseNumber+"%"));
        if(0!=carType)
            criteria.add(Restrictions.eq("carType.id", carType));
        if(0!=skill)
            criteria.add(Restrictions.eq("skillRank.id", skill));	
        
        criteria.setProjection(Projections.rowCount());
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
//		 List<Carfile> list= getHibernateTemplate().find("from Carfile c where c.carType.id like ? and c.skillRank.id=? ",new Object[]{carType,skill});
//		 if(list.size()!=1)return 0;
//		 return list.get(0).getId();          
    }    
    @SuppressWarnings("unchecked")
    public List<Carfile> findCarfileBySkillPerPage(int start, int limit,int carType,int skill) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Carfile.class);
        criteria.add(Expression.eq("status", ICarfileService.STATUS_NORMAL));
        criteria.addOrder(Order.desc("editDate"));
//        criteria.add(Expression.like("licenseNumber","%"+licenseNumber+"%"));  
        if(0!=carType)
            criteria.add(Restrictions.eq("carType.id", carType));
        if(0!=skill)
            criteria.add(Restrictions.eq("skillRank.id", skill));	      
        List<Carfile> list = getHibernateTemplate().findByCriteria(criteria,start,limit);
        desidedExpiredFlag(list);
        return list;
//    	List<Carfile> list= getHibernateTemplate().find("from Carfile c where c.carType.id like ? and c.skillRank.id=? ",new Object[]{carType,skill});
//		 return list; 
    }   
    
//    public int findByCreateDateSpacingCount(Date startDate,Date endDate,int carType) {
//        DetachedCriteria criteria = DetachedCriteria.forClass(Carfile.class);
//        criteria.createAlias("skillRank", "cs");
//        if(0!=carType){
//            criteria.add(Expression.eq("cs.id", carType));
//        }
//        criteria.add(Expression.between("maintainDate", startDate, endDate));
//        criteria.setProjection(Projections.rowCount());
//        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
//    }
//    @SuppressWarnings("unchecked")
//    public List<Carfile> findByCreateDateSpacingPerPage(Date startDate,
//            Date endDate, int start, int limit,int carType) {
//        DetachedCriteria criteria = DetachedCriteria.forClass(Carfile.class);
//        criteria.createAlias("skillRank", "cs");
//        if(0!=carType){
//            criteria.add(Expression.eq("cs.id", carType));
//        }
//        criteria.add(Expression.between("maintainDate", startDate, endDate));
//        return getHibernateTemplate().findByCriteria(criteria,start,limit);
//    }

    @SuppressWarnings("unchecked")
    public List findCSGroupByTelVague(SearchBean[] queryBean) {
        DetachedCriteria criteria = SearchFactory.generateCriteria(Carfile.class, queryBean);
        criteria.add(Expression.eq("status", ICarfileService.STATUS_NORMAL));
        criteria.addOrder(Order.desc("editDate"));
        criteria.createAlias("carType", "cs");
        criteria.setProjection(Projections.projectionList()
                .add(Projections.count("cs.name"))
                .add(Projections.groupProperty("cs.name")));
        List<Carfile> list = getHibernateTemplate().findByCriteria(criteria);
//        desidedExpiredFlag(list);
        return list;
    }

	@Override
	public boolean existCar(Integer id,String license,Dictionary paiSe) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Carfile.class);
		criteria.add(Expression.eq("status", ICarfileService.STATUS_NORMAL));
        criteria.setProjection(Projections.rowCount());
        criteria.add(Expression.eq("paiSe", paiSe));
        criteria.add(Expression.eq("licenseNumber", license));
        if(id!=null){
        	criteria.add(Expression.ne("id", id));
        }
        return ((Integer)getHibernateTemplate().findByCriteria(criteria).get(0))>0;
	}

	@Override
	public int getCarId(String carNo, Dictionary carColor) {
		 List<Carfile> list= getHibernateTemplate().find("from Carfile c where c.licenseNumber like ? and c.paiSe=? and c.status=?",new Object[]{"%"+carNo,carColor,ICarfileService.STATUS_NORMAL});
		 if(list.size()!=1)return 0;
		 return list.get(0).getId();
	}

}
