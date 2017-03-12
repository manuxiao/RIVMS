 
package org.gaixie.micrite.patrolRiverSummary.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
 
import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.dic.dao.IDictionaryDAO;
import org.gaixie.micrite.beans.PatrolRiverSummary;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.patrolRiverSummary.dao.IPatrolRiverSummaryDAO;
import org.gaixie.micrite.patrolRiverSummary.service.IPatrolRiverSummaryService;
import org.gaixie.micrite.patrolRiverSummary.dao.IPatrolRiverSummaryDAO;
import org.gaixie.micrite.patrolRiverSummary.service.IPatrolRiverSummaryService;
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
 * @see org.gaixie.micrite.car.service.IPatrolRiverSummaryService
 */
public class PatrolRiverSummaryServiceImpl implements IPatrolRiverSummaryService { 
	private Logger log=Logger.getLogger(PatrolRiverSummaryServiceImpl.class);
	@Override
	public PatrolRiverSummary get(Integer id) {
		// TODO Auto-generated method stub
		return patrolRiverSummaryDAO.get(id);
	}
    @Override
	public List<PatrolRiverSummary> getExpiredCars() {
		// TODO Auto-generated method stub
		return patrolRiverSummaryDAO.getExpiredCars();
	}

	@Autowired
    private IPatrolRiverSummaryDAO patrolRiverSummaryDAO;
 
	@Autowired
	private IDictionaryDAO dictionaryDAO;
	
    public List<PatrolRiverSummary> advancedFindByPerPage(SearchBean[] queryBean, int start,
            int limit){
        List<PatrolRiverSummary> list = patrolRiverSummaryDAO.advancedFindByPerPage(queryBean,start,limit);
        return list;
    }

    public int advancedFindCount(SearchBean[] queryBean){
        return patrolRiverSummaryDAO.advancedFindCount(queryBean); 
    }

    
    public void delete(int[] PatrolRiverSummaryIds) {
    	Date deleteDate = new Date();
    	int deletedById = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId(); 
        for (int i = 0; i < PatrolRiverSummaryIds.length; i++) { 
            PatrolRiverSummary car = patrolRiverSummaryDAO.get(PatrolRiverSummaryIds[i]);
            if(car!=null){
            	//checkDao.save(__getCarSetDeleteHistory(car));
            	car.setState(IPatrolRiverSummaryService.STATUS_DELETED);
            	car.setEditDate(deleteDate);
            	car.setEditorId(deletedById);
            	patrolRiverSummaryDAO.update(car);
            }
//            PatrolRiverSummaryDAO.delete(PatrolRiverSummary);
        }
    }
     
    public int findByCreateDateSpacingCount(int carType) {
        return patrolRiverSummaryDAO.findByCreateDateSpacingCount(carType);
    }   
    public List<PatrolRiverSummary> findByCreateDateSpacingPerPage(int start, int limit,int carType) {
        List<PatrolRiverSummary> list = patrolRiverSummaryDAO.findByCreateDateSpacingPerPage(start, limit,carType);
        return list;
    }
 
    public CategoryDataset getCarDictionaryBarDataset(SearchBean[] queryBean){
        List list = patrolRiverSummaryDAO.findCSGroupByTelVague(queryBean);
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
        List list = patrolRiverSummaryDAO.findCSGroupByTelVague(queryBean);
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
 * 不带审核
 * */
    public void update(PatrolRiverSummary c) {
    	boolean isEdited = false;
    	int carId = c.getId();
        PatrolRiverSummary patrol = patrolRiverSummaryDAO.get(carId);
      
        if(c.getTownRiverChief()!=null){
        	//checkDao.save(__getCarOptHistory(car, PatrolRiverSummary.PAISE_NO,car.getPaiSe(),c.getPaiSe()));
        	patrol.setTownRiverChief(c.getTownRiverChief());isEdited=true;
        }
        if(c.getActivity1()!=null){
        	patrol.setActivity1(c.getActivity1());isEdited=true;
        }
        if(c.getFaFangZiLiao()!=null){
        	patrol.setFaFangZiLiao(c.getFaFangZiLiao());isEdited=true;
        }     
        if(c.getFloatGarbage()!=null){
        	patrol.setFloatGarbage(c.getFloatGarbage());isEdited=true;
        }
        if(c.getGongYeZhiPai()!=null){
        	patrol.setGongYeZhiPai(c.getGongYeZhiPai());isEdited=true;
        }     
        if(c.getJianYiHanCe()!=null){
        	patrol.setJianYiHanCe(c.getJianYiHanCe());isEdited=true;
        }
        if(c.getLuanDaJian()!=null){
        	patrol.setLuanDaJian(c.getLuanDaJian());isEdited=true;
        }
        if(c.getNongYeZhiPai()!=null){
        	patrol.setNongYeZhiPai(c.getNongYeZhiPai());isEdited=true;
        }      
        if(c.getPaiWuBZWeiQuan()!=null){
        	patrol.setPaiWuBZWeiQuan(c.getPaiWuBZWeiQuan());isEdited=true;
        }
        if(c.getPublicSignDamage()!=null){
        	patrol.setPublicSignDamage(c.getPublicSignDamage());isEdited=true;
        }
        
        if(c.getQingJieGongShiPai()!=null){
        	patrol.setQingJieGongShiPai(c.getQingJieGongShiPai());isEdited=true;
        }
        if(c.getQingLiLaJi()!=null){
        	patrol.setQingLiLaJi(c.getQingLiLaJi());isEdited=true;
        }
        if(c.getQingLiLuanDuiFang()!=null){
        	patrol.setQingLiLuanDuiFang(c.getQingLiLuanDuiFang());isEdited=true;
        }      
        if(c.getRiverChief()!=null){
        	patrol.setRiverChief(c.getRiverChief());isEdited=true;
        }
        if(c.getRiverChiefUnit()!=null){
        	patrol.setRiverChiefUnit(c.getRiverChiefUnit());isEdited=true;
        }
        if(c.getShangMenXC()!=null){
        	patrol.setShangMenXC(c.getShangMenXC());isEdited=true;
        }
        if(c.getTownRiverChief()!=null){
        	patrol.setTownRiverChief(c.getTownRiverChief());isEdited=true;
        }
        if(c.getVillageRiverChief()!=null){
        	patrol.setVillageRiverChief(c.getVillageRiverChief());isEdited=true;
        }
        if(c.getZaWuDuiFang()!=null){
        	patrol.setZaWuDuiFang(c.getZaWuDuiFang());isEdited=true;
        }
//        //下面是关于车主信息的修改
//        c.getUser().setId(car.getUser().getId());//该方法不更新车主
//        if(carownerService.update(c.getUser())){
//        	isEdited=true;
//        }     
        if(isEdited){
        	_update(patrol);
        }
    }
    private void _update(PatrolRiverSummary patrolRiverSummary){
    	patrolRiverSummary.setEditDate(new Date());
    	patrolRiverSummary.setEditorId(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    	patrolRiverSummaryDAO.update(patrolRiverSummary);
    }

	@Override
	public boolean existPatrolRiverSummary(Integer userid) {
		return patrolRiverSummaryDAO.existPatrolRiverSummary(userid);
	}
    /*
     * 审批不通过，需填写不通过原因
     */
	@Override
	public void ridofPunishment(Integer carId, String message) {
		// TODO Auto-generated method stub
		PatrolRiverSummary patrolRiverSummary = patrolRiverSummaryDAO.get(carId);
		if(patrolRiverSummary!=null&&patrolRiverSummary.getApproveStatus()==APPROVAL_TOBE){
			patrolRiverSummary.setApproveStatus(this.APPROVAL_NOT);
			patrolRiverSummary.setApproveDemo(message);
			patrolRiverSummary.setApproveDate(new Date());
		   	patrolRiverSummary.setApproveId(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());    
			_update(patrolRiverSummary);
		}
	}
	
	/*
     * 审批通过，不需填写原因
     */
	@Override
	public void exemptPunishment(Integer carId, String message) {
		// TODO Auto-generated method stub
		PatrolRiverSummary patrolRiverSummary = patrolRiverSummaryDAO.get(carId);
		if(patrolRiverSummary!=null&&patrolRiverSummary.getApproveStatus()==APPROVAL_NOT){
			patrolRiverSummary.setApproveStatus(this.APPROVAL_YES);
			//patrolRiverSummary.setApproveDemo(message);
			patrolRiverSummary.setApproveDate(new Date());
 		   	patrolRiverSummary.setApproveId(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());		
			_update(patrolRiverSummary);
 		}
	}

	@Override
	public void updatePrintIndex1(int carId, int index) {
		// TODO Auto-generated method stub
		PatrolRiverSummary car =patrolRiverSummaryDAO.get(carId);
		if(car!=null){
			car.setPrintIndex1(index);
			patrolRiverSummaryDAO.update(car);
		}
	}
	@Override
	public void addFromFront(PatrolRiverSummary patrolRiverSummary) {
		// TODO Auto-generated method stub
		
	}

    /**
     * 车辆档案文件中
     * */
    @Override
    public void addFromUpload(PatrolRiverSummary PatrolRiverSummary) {
    	
    	//setsomdfsdgagdfg(PatrolRiverSummary);
    	try {
    		patrolRiverSummaryDAO.save(PatrolRiverSummary);	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        
    }
 	
}

