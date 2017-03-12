/* ===========================================================
 * $Id: CarfileServiceImpl.java 516 2009-08-23 16:33:31Z bitorb $
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

package org.gaixie.micrite.car.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.gaixie.micrite.check.dao.ICheckDAO;
import org.gaixie.micrite.check.service.ICheckService;
import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.crm.dao.ICarownerDAO;
import org.gaixie.micrite.crm.service.ICarownerService;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Carowner;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.Check;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.car.dao.ICarfileDAO;
import org.gaixie.micrite.car.service.ICarfileService;
import org.gaixie.micrite.dic.dao.IDictionaryDAO;
import org.gaixie.micrite.dic.service.IDictionaryService;
import org.gaixie.micrite.util.CalendarUtil;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hansheng.njj.PinyinConv;
/**
 * 车辆档案管理功能实现
 * @see org.gaixie.micrite.car.service.ICarfileService
 */
public class CarfileServiceImpl implements ICarfileService { 
	private Logger log=Logger.getLogger(CarfileServiceImpl.class);
	@Override
	public Carfile get(Integer id) {
		// TODO Auto-generated method stub
		return carfileDAO.get(id);
	}
    @Override
	public List<Carfile> getExpiredCars() {
		// TODO Auto-generated method stub
		return carfileDAO.getExpiredCars();
	}

	@Autowired
    private ICarfileDAO carfileDAO;
	@Autowired
	private ICheckDAO checkDao;
	@Autowired
	private IDictionaryDAO dictionaryDAO;
//	@Autowired
//	private ICheckService checkService;
//	@Autowired
//	private ICarownerDAO carownerDAO;
	@Autowired
	private ICarownerService carownerService;
    public List<Carfile> advancedFindByPerPage(SearchBean[] queryBean, int start,
            int limit){
        List<Carfile> list = carfileDAO.advancedFindByPerPage(queryBean,start,limit);
        return list;
    }

    public int advancedFindCount(SearchBean[] queryBean){
        return carfileDAO.advancedFindCount(queryBean); 
    }

    
    public void delete(int[] CarfileIds) {
    	Date deleteDate = new Date();
    	int deletedById = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId(); 
        for (int i = 0; i < CarfileIds.length; i++) { 
            Carfile car = carfileDAO.get(CarfileIds[i]);
            if(car!=null){
            	checkDao.save(__getCarSetDeleteHistory(car));
            	car.setStatus(ICarfileService.STATUS_DELETED);
            	car.setEditDate(deleteDate);
            	car.setEditorId(deletedById);
            	carfileDAO.update(car);
            }
//            carfileDAO.delete(Carfile);
        }
    }

    public int findCarfileBySkill(int carType,int skill) {
        return carfileDAO.findCarfileBySkill(carType,skill);
    }   
    public List<Carfile> findCarfileBySkillPerPage(int start, int limit,int carType,int skill) {
        List<Carfile> list = carfileDAO.findCarfileBySkillPerPage(start, limit,carType,skill);
        return list;    
    }

    
    public int findByCreateDateSpacingCount(int carType) {
        return carfileDAO.findByCreateDateSpacingCount(carType);
    }   
    public List<Carfile> findByCreateDateSpacingPerPage(int start, int limit,int carType) {
        List<Carfile> list = carfileDAO.findByCreateDateSpacingPerPage(start, limit,carType);
        return list;
    }
//    public int findByCreateDateSpacingCount(Date startDate, Date endDate,int carType) {
//        return carfileDAO.findByCreateDateSpacingCount(startDate, endDate,carType);
//    }
//    public List<Carfile> findByCreateDateSpacingPerPage(Date startDate,
//            Date endDate, int start, int limit,int carType) {
//        List<Carfile> list = carfileDAO.findByCreateDateSpacingPerPage(startDate, endDate, start, limit,carType);
//        return list;
//    }
//
//	carfile.setCreateDate(new Date());
//	carfile.setCreaterId(IDictionaryService.SYS_USER_ID);
//	carfile.setEditDate(carfile.getCreateDate());
//	carfile.setEditorId(IDictionaryService.SYS_USER_ID);
    public CategoryDataset getCarDictionaryBarDataset(SearchBean[] queryBean){
        List list = carfileDAO.findCSGroupByTelVague(queryBean);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                Object[] obj = (Object[]) list.get(i);
                dataset.setValue(Integer.parseInt(obj[0].toString()),obj[1].toString(),"");
            }
        } else {
            return null;
        }
        return dataset;
    }

    public PieDataset getCarDictionaryPieDataset(SearchBean[] queryBean){
        List list = carfileDAO.findCSGroupByTelVague(queryBean);
        DefaultPieDataset dataset = new DefaultPieDataset();
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                Object[] obj = (Object[]) list.get(i);
                dataset.setValue( obj[1].toString(),Integer.parseInt(obj[0].toString()));
            }
        } else {
            return null;
        }
        return dataset;
        
    }
    
/**
 * 已带审核
 * */
    public void update(Carfile c) {
    	boolean isEdited = false;
    	int carId = c.getId();
        Carfile car = carfileDAO.get(carId);
    	
        if(c.getBrandType()!=null&&!c.getBrandType().trim().equals(car.getBrandType())){
        	checkDao.save(__getCarOptHistory(car, Carfile.BRANDTYPE_NO, car.getBrandType(),c.getBrandType()));
        	car.setBrandType(c.getBrandType().trim());isEdited=true;
        }
        if(c.getCarRemark()!=null&&!c.getCarRemark().trim().equals(car.getCarRemark())){
        	checkDao.save(__getCarOptHistory(car, Carfile.CARREMARK_NO, car.getCarRemark(),c.getCarRemark()));
        	car.setCarRemark(c.getCarRemark().trim());isEdited=true;
        }
        if(c.getCarStatus()!=car.getCarStatus()){//这里应该不会用用到；但实际上应该通过这里保存，实现复用；
        	checkDao.save(__getCarOptHistory(car, Carfile.CARSTATUS_NO
        			,dictionaryDAO.getName(Carfile.CARSTATUS_TYPE, car.getCarStatus())
        			,dictionaryDAO.getName(Carfile.CARSTATUS_TYPE, c.getCarStatus())));
        	car.setCarStatus(c.getCarStatus());isEdited=true;
        }
        if(c.getCarType()!=null&&c.getCarType().getId()!=null&&c.getCarType().getId().intValue()!=car.getCarType().getId().intValue()){
        	checkDao.save(__getCarOptHistory(car, Carfile.CARTYPE_NO,car.getCarType(),c.getCarType()));
        	car.setCarType(c.getCarType());isEdited=true;
        }
        if(c.getEvaluateCycle()!=null&&c.getEvaluateCycle().getId()!=null&&c.getEvaluateCycle().getId().intValue()!=car.getEvaluateCycle().getId().intValue()){
        	checkDao.save(__getCarOptHistory(car, Carfile.EVALUATECYCLE_NO,car.getEvaluateCycle(),c.getEvaluateCycle()));
        	car.setEvaluateCycle(c.getEvaluateCycle());isEdited=true;
        }
        if(c.getEvaluateDate()!=null&&(car.getEvaluateDate()==null||!c.getEvaluateDate().equals(car.getEvaluateDate()))){//技术等级有效期止的修改
        	checkDao.save(__getCarOptHistory(car, Carfile.EVALUATEDATE_NO, CalendarUtil.df.format(car.getEvaluateDate()),CalendarUtil.df.format(c.getEvaluateDate())));
        	car.setEvaluateDate(c.getEvaluateDate());isEdited=true;
        }
        if(c.getFuelRank()!=null&&c.getFuelRank().getId()!=null&&c.getFuelRank().getId().intValue()!=car.getFuelRank().getId().intValue()){
        	checkDao.save(__getCarOptHistory(car, Carfile.FUELRANK_NO,car.getFuelRank(),c.getFuelRank()));
        	car.setFuelRank(c.getFuelRank());isEdited=true;
        }
        if(c.getLicenseNumber()!=null&&!c.getLicenseNumber().trim().equals(car.getLicenseNumber())){
        	checkDao.save(__getCarOptHistory(car, Carfile.LICENSENUMBER_NO, car.getLicenseNumber(),c.getLicenseNumber()));
        	car.setLicenseNumber(c.getLicenseNumber().trim());isEdited=true;
        }
        if(c.getLicenseType()!=null&&c.getLicenseType().getId()!=null&&c.getLicenseType().getId().intValue()!=car.getLicenseType().getId().intValue()){
        	checkDao.save(__getCarOptHistory(car, Carfile.LICENSETYPE_NO,car.getLicenseType(),c.getLicenseType()));
        	car.setLicenseType(c.getLicenseType());isEdited=true;
        }
        if(c.getLoadTon()!=null&&!c.getLoadTon().trim().equals(car.getLoadTon())){
        	checkDao.save(__getCarOptHistory(car, Carfile.LOADTON_NO, car.getLoadTon(),c.getLoadTon()));
        	car.setLoadTon(c.getLoadTon().trim());isEdited=true;
        }
        if(c.getMaintainCycle()!=null&&c.getMaintainCycle().getId()!=null&&c.getMaintainCycle().getId().intValue()!=car.getMaintainCycle().getId().intValue()){
        	checkDao.save(__getCarOptHistory(car, Carfile.MAINTAINCYCLE_NO,car.getMaintainCycle(),c.getMaintainCycle()));
        	car.setMaintainCycle(c.getMaintainCycle());isEdited=true;
        	car.setMaintainDateEnd(CalendarUtil.afterMonth(car.getMaintainDate(), car.getMaintainCycle().getValue()));
        }
        if(c.getMaintainDate()!=null&&(car.getMaintainDate()==null||!c.getMaintainDate().equals(car.getMaintainDate()))){//二级维护日期的修改
        	checkDao.save(__getCarOptHistory(car, Carfile.MAINTAINDATE_NO, CalendarUtil.df.format(car.getMaintainDate()),CalendarUtil.df.format(c.getMaintainDate())));
        	car.setMaintainDate(c.getMaintainDate());isEdited=true;
        	car.setMaintainDateEnd(CalendarUtil.afterMonth(car.getMaintainDate(), car.getMaintainCycle().getValue()));
        }
        if(c.getPaiSe()!=null&&c.getPaiSe().getId()!=null&&c.getPaiSe().getId().intValue()!=car.getPaiSe().getId().intValue()){
        	checkDao.save(__getCarOptHistory(car, Carfile.PAISE_NO,car.getPaiSe(),c.getPaiSe()));
        	car.setPaiSe(c.getPaiSe());isEdited=true;
        }
        if(c.getSkillRank()!=null&&c.getSkillRank().getId()!=null&&c.getSkillRank().getId().intValue()!=car.getSkillRank().getId().intValue()){
        	checkDao.save(__getCarOptHistory(car, Carfile.SKILLRANK_NO,car.getSkillRank(),c.getSkillRank()));
        	car.setSkillRank(c.getSkillRank());isEdited=true;
        }
        
        if(c.getYingyunNo()!=null&&!c.getYingyunNo().trim().equals(car.getYingyunNo())){
        	checkDao.save(__getCarOptHistory(car, Carfile.YINGYUNNO_NO, car.getYingyunNo(),c.getYingyunNo()));
        	car.setYingyunNo(c.getYingyunNo().trim());isEdited=true;
        }
        //下面是关于车主信息的修改
        c.getCarowner().setId(car.getCarowner().getId());//该方法不更新车主
        if(carownerService.update(c.getCarowner())){
        	isEdited=true;
        }
        
        if(isEdited){
        	_update(car);
        }

    }
    private void _update(Carfile carfile){
    	carfile.setEditDate(new Date());
    	carfile.setEditorId(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    	carfileDAO.update(carfile);
    }
    private void setsomdfsdgagdfg(Carfile car){
//        carfile.setPy(PinyinConv.cn2py(carfile.getOwner()));
//      carfile.setMaintainDateEnd(CalendarUtil.afterMonth(carfile.getMaintainDate(), carfile.getMaintainCycle().getValue()));//如果是从网页上提交上来的carfile对象，则其外键对象们的其他字段是不可用的
    	car.setCreateDate(new Date());
    	car.setEditDate(car.getCreateDate());
		car.setCreaterId(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
		car.setEditorId(car.getCreaterId());
        int monthsCount = dictionaryDAO.get(car.getMaintainCycle().getId()).getValue();
        car.setMaintainDateEnd(CalendarUtil.afterMonth(car.getMaintainDate(), monthsCount));
        car.setDaysToExpired(CalendarUtil.getDaysBetween(new Date(), car.getMaintainDateEnd()));
    }
    /**
	 * 页面上新增一条
	 * 1,还要根据两个时间来生成两个记录
	 * */
    public void addFromFront(Carfile carfile) {
    	
    	Carowner owner=null;
    	carfile.setOwner(carfile.getOwner().trim());
    	if(carfile.getCarowner()!=null&&carfile.getCarowner().getId()!=null){//提交上来的carowner.id不空的
    		owner = carownerService.get(carfile.getCarowner().getId());
    		if(owner==null){//结果该id的业户还不存在
    			owner = carownerService.getByLicense(carfile.getOwnerLicense());
    			if(owner==null){//新增
    				carownerService.save(carfile.getCarowner());
    			}else{//尝试更新业户信息
    				carfile.getCarowner().setId(owner.getId());//
    				carownerService.update(carfile.getCarowner());
    			}
    		}else{//尝试更新业户信息
    			carownerService.update(carfile.getCarowner());
    		}
    	}else{//提交上来的carowner.id是空的
    		owner = carownerService.getByLicense(carfile.getOwnerLicense());
    		if(owner==null){
				carownerService.save(carfile.getCarowner());
			}else{//尝试更新业户信息
				carfile.getCarowner().setId(owner.getId());
				carownerService.update(carfile.getCarowner());
			}
    	}
    	carfile.setLicenseNumber(carfile.getLicenseNumber().toUpperCase());
    	carfile.setCarStatus(ICarfileService.CARSTATUS_NORMAL);
    	carfile.setStatus(ICarfileService.STATUS_NORMAL);
    	int monthsCount = dictionaryDAO.get(carfile.getEvaluateCycle().getId()).getValue();
        carfile.setEvaluateDate(CalendarUtil.afterMonth(carfile.getMaintainDate(), monthsCount));//新车的技术等级评定日期跟二级维护日期相同，计算截止日期
    	try {
    		addFromUpload(carfile);
    		checkDao.save(__getCarAddHistory(carfile));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
        if(log.isDebugEnabled())
        	log.debug(carfile.getYingyunNo());
    }
    /**
     * 车辆档案文件中
     * */
    @Override
    public void addFromUpload(Carfile carfile) {
    	
    	setsomdfsdgagdfg(carfile);
    	try {
    		carfileDAO.save(carfile);	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        
    }

    @Override
	public boolean existCar(Carfile car) {
		return carfileDAO.existCar(car.getId(),car.getLicenseNumber(),car.getPaiSe());
	}
    @Override
	public boolean existCar(String license,Dictionary paiSe) {
		return carfileDAO.existCar(null,license,paiSe);
	}
	@Override
	public int getCarId(String carNo, Dictionary carColor) {
		return carfileDAO.getCarId(carNo, carColor);
	}
//	@Override
//	public int renewErweiDateEnd() {
//		// TODO Auto-generated method stub
//		int count = 0;
//		
//		return count;
//	}
	
	/**设置一条解除处罚状态的历史记录
	 * 此类记录的查询最好也能在这里；因为状态都是这里设置的，勿多处；
	 * */
	final private Check __getCarExemptHistory(Carfile car,Date erweiEndDate,String message){
		return __getCarSomeHistory1(car, erweiEndDate, message, ICheckService.CL_EXEMPT);
	}
	final private Check __getCarPunishHistory(Carfile car,Date erweiEndDate,String message){
		return __getCarSomeHistory1(car, erweiEndDate, message, ICheckService.CL_PUNISH);
	}

	final private Check __getCarGHHistory(Carfile car,Carowner from,Carowner to,String message){//过户
		String tmp=car.getCarowner().getName()+"->"+to.getName();
		if(message!=null&&!message.trim().equals(""))tmp+=":"+message;
		return __getCarSomeHistory2(car,ICheckService.CL_GH,from.getId(),to.getId(),tmp);
	}
	final private Check __getCarZJHistory(Carfile car,String message){//转籍
		if(message==null){
			message="";
		}
		return __getCarSomeHistory2(car,ICheckService.CL_ZJ,0,0,message);
	}
	final private Check __getCarZXHistory(Carfile car,String message){//报废
		if(message==null){
			message="";
		}
		return __getCarSomeHistory2(car,ICheckService.CL_ZX,0,0,message);
	}
	final private Check __getCarAddHistory(Carfile car){//新增
		return __getCarSomeHistory2(car,ICheckService.ADD_ACTION,0,0,"");
	}
	final private Check __getCarSetDeleteHistory(Carfile car){//删除
		return __getCarSomeHistory2(car,ICheckService.DELETE_ACTION,0,0,"");
	}
	final private Check __getCarOptHistory(Carfile car,int col,Dictionary orig,Dictionary curr){
		return __getCarOptHistory(car,col,orig.getName(),dictionaryDAO.getName(curr.getId()));
	}
	final private Check __getCarOptHistory(Carfile car,int col,String origStr,String currStr){
		Check c = __fillWithInfo(car,col);
		c.setNotepad(origStr+"->"+currStr);
		return c;
	}
	final private Check __getCarSomeHistory1(Carfile car,Date erweiEndDate,String message,int action){
		Check c = __fillWithInfo(car,action);
		c.setEndTime(erweiEndDate);//原本到期时间(未追加准超级几天)
		c.setNotepad(message);
		return c;
	}
	final private Check __getCarSomeHistory2(Carfile car,int action,int from,int to,String message){
		Check c = __fillWithInfo(car,action);
		c.setTestNo(from);//从这里
		c.setPrinted(to);//过户到这里
		c.setNotepad(message);
		return c;
	}
	final private Check __fillWithInfo(Carfile car,int action){
		Check c = new Check();
		c.setTestKind(action);
		c.setStatus(car.getId());
		c.setCreateDate(new Date());
		User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		c.setCreaterId(currentUser.getId());
		c.setIsPost(ICheckService.RT_CL);
//		Carfile car = carfileDAO.get(c.getStatus());
//		if(car==null)return c;
		c.setPaiHao(car.getLicenseNumber());
		c.setPaiSe(car.getPaiSe());
		c.setCheZhu(car.getOwner());
		return c;
	}
	
	@Override
	public void ridofPunishment(Integer carId, String message) {
		// TODO Auto-generated method stub
		Carfile car = carfileDAO.get(carId);
		if(car!=null&&car.getApproval()==APPROVAL_NOT){
			car.setApproval(this.APPROVAL_BUY);
			_update(car);
			checkDao.save(__getCarPunishHistory(car,car.getMaintainDateEnd(),message));//留一条历史记录
		}
	}
	
	@Override
	public void exemptPunishment(Integer carId, String message) {
		// TODO Auto-generated method stub
		Carfile car = carfileDAO.get(carId);
		if(car!=null&&car.getApproval()==APPROVAL_NOT){
			car.setApproval(this.APPROVAL_YES);
			_update(car);
			checkDao.save(__getCarExemptHistory(car,car.getMaintainDateEnd(),message));//留一条历史记录
		}
	}
	
	@Override
	public void updateGH(Carfile car,String licenseNumber, Carowner ghee,String message) {
		// TODO Auto-generated method stub
		
		checkDao.save(__getCarGHHistory(car,car.getCarowner(),ghee,message));
		if(licenseNumber!=null&&!licenseNumber.trim().equals("")&&!licenseNumber.equals(car.getLicenseNumber())){
			checkDao.save(__getCarOptHistory(car, Carfile.LICENSENUMBER_NO, car.getLicenseNumber(),licenseNumber));
			car.setLicenseNumber(licenseNumber);
		}
		car.setCarowner(ghee);
		_update(car);
	}
	@Override
	public void updateZJ(Carfile car,String message) {
		// TODO Auto-generated method stub
		checkDao.save(__getCarZJHistory(car,message));
		
		car.setCarStatus(ICarfileService.CARSTATUS_GONE);
		_update(car);
	}
	@Override
	public void updateZX(Carfile car,String message) {
		// TODO Auto-generated method stub
		checkDao.save(__getCarZXHistory(car,message));
		
		car.setCarStatus(ICarfileService.CARSTATUS_DIED);
		_update(car);
	}
	
	@Override
	public int renewErweiExpiredPunishment() {
		// TODO Auto-generated method stub
		int days = dictionaryDAO.get(IDictionaryService.EXPIRED_PERMISSION_ID).getValue();
		Date now = null;
		try {
			now=CalendarUtil.df.parse(CalendarUtil.df.format(new Date()));	
		} catch (Exception e) {
			// TODO: handle exception
			Log.error(e);
		}
		
		int count = 0;
		List<Carfile> list = carfileDAO.getActiveCars();
		for (int i = 0; i < list.size(); i++) {
			try {
				Carfile car = list.get(i);
				if(car.getMaintainDateEnd()==null)continue;
				car.setDaysToExpired(CalendarUtil.getDaysBetween(new Date(), car.getMaintainDateEnd()));
//				if(now.after(car.getMaintainDateEnd())){
//					car.setExpired(EXPIRED_YES);
//					if(now.after(CalendarUtil.afterDay(car.getMaintainDateEnd(), days))){
//						car.setExpired(EXPIRED_SO);
//						count++;
//					}
//				}
				carfileDAO.update(car);					
			} catch (Exception e) {
				// TODO: handle exception
				Log.error(e);
			}
			
		}
		return count;
	}
	@Override
	public void updatePrintIndex1(int carId, int index) {
		// TODO Auto-generated method stub
		Carfile car = carfileDAO.get(carId);
		if(car!=null){
			car.setPrintIndex1(index);
			carfileDAO.update(car);
		}
	}
	@Override
	public void updatePrintIndex2(int carId, int index) {
		// TODO Auto-generated method stub
		Carfile car = carfileDAO.get(carId);
		if(car!=null){
			car.setPrintIndex2(index);
			carfileDAO.update(car);
		}
	}
	@Override
	public void updateNotepad(int carId, String notepad) {
		// TODO Auto-generated method stub
		Carfile car = carfileDAO.get(carId);
    	if(car!=null){
        	car.setNotepad(notepad);
        	carfileDAO.update(car);
    	}
	}
	@Override
	public List<Carfile> findByMaintainDateWillExpired(Integer carownerId,Date beginDate,
			Date endDate,int start,int limit) {
		Date now = new Date();
		int fromDays = CalendarUtil.getDaysBetween(now, beginDate);
		int toDays = CalendarUtil.getDaysBetween(now, endDate);
		// TODO Auto-generated method stub
		return carfileDAO.findByMaintainDateWillExpired(carownerId,fromDays, toDays,start, limit);
	}
	@Override
	public int findCountByMaintainDateWillExpired(Integer carownerId,Date beginDate, Date endDate) {
		Date now = new Date();
		int fromDays = CalendarUtil.getDaysBetween(now, beginDate);
		int toDays = CalendarUtil.getDaysBetween(now, endDate);
		// TODO Auto-generated method stub
		return carfileDAO.findCountByMaintainDateWillExpired(carownerId,fromDays, toDays);
	}
	
}

