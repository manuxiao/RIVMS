/* ===========================================================
 * $Id: CarownerServiceImpl.java 516 2009-08-23 16:33:31Z bitorb $
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

package org.gaixie.micrite.crm.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.gaixie.micrite.check.dao.ICheckDAO;
import org.gaixie.micrite.check.service.ICheckService;
import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Carowner;
import org.gaixie.micrite.beans.Check;
import org.gaixie.micrite.beans.IdName;
import org.gaixie.micrite.beans.Standard;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.crm.dao.ICarownerDAO;
import org.gaixie.micrite.crm.service.ICarownerService;
import org.gaixie.micrite.dic.service.IDictionaryService;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hansheng.njj.PinyinConv;
/**
 * 客户管理功能实现
 * @see org.gaixie.micrite.crm.service.ICarownerService
 */
@Service("carownerService")
public class CarownerServiceImpl implements ICarownerService { 
	private Logger log = Logger.getLogger(CarownerServiceImpl.class);
    @Autowired
    private ICarownerDAO carownerDAO;

//    @Override
//	public Carowner getByName(String name) {
//    	return null;  	
////    	return carownerDAO.getByName(name);
//		// TODO Auto-generated method stub
//	}
    
    @Override
	public Carowner getByLicense(String license) {
    	
    	return carownerDAO.getByLicense(license);
		// TODO Auto-generated method stub
	}
    @Override
	public List<IdName> findCarownerByPy(String py) {
		// TODO Auto-generated method stub
		return carownerDAO.findCarownerByPy(py);
	}
	public void save(Carowner carowner) {
		
    	carowner.setPy(PinyinConv.cn2py(carowner.getName()));
		carowner.setCreateDate(new Date());
		carowner.setEditDate(carowner.getCreateDate());
		carowner.setCreaterId(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
		carowner.setEditorId(carowner.getCreaterId());
        carownerDAO.save(carowner);
        
        checkDao.save(__getCarownerOptHistory(carowner,ICheckService.ADD_ACTION));
    }
//    public void addFromUpload(Carowner carowner) {
//		carowner.setPy(PinyinConv.cn2py(carowner.getName()));
//		carowner.setCreateDate(new Date());
//		carowner.setEditDate(carowner.getCreateDate());
//		carowner.setCreaterId(IDictionaryService.SYS_USER_ID);
//		carowner.setEditorId(carowner.getCreaterId());
//        carownerDAO.save(carowner);
//        if(log.isDebugEnabled())
//        	log.debug(carowner.getName());
//    }
    
//    public List<Carowner> advancedFindByPerPageALL(SearchBean[] queryBean, int start,
//            int limit){
//        List<Carowner> list = carownerDAO.advancedFindByPerPageALL(queryBean,start,limit);
//		
//        return list;
//    }
    public List<Carowner> advancedFindByPerPage(SearchBean[] queryBean, int start,
            int limit){
        List<Carowner> list = carownerDAO.advancedFindByPerPage(queryBean,start,limit);
        return list;
    }
    public int advancedFindCount(SearchBean[] queryBean){
        return carownerDAO.advancedFindCount(queryBean); 
    }
    
//    @Override
//	public void save(Carowner carowner) {
//		// TODO Auto-generated method stub
//    	carowner.setCreateDate(new Date());
//    	carowner.setCreaterId(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
//    	carowner.setEditDate(carowner.getCreateDate());
//    	carowner.setEditorId(carowner.getEditorId());
//    	carownerDAO.save(carowner);
//	}

	@Override
	public Carowner get(Integer ownerId) {
		// TODO Auto-generated method stub
		return carownerDAO.get(ownerId);
	}
	public void delete(int[] carownerIds) {//实际删除、留操作日志；假如是修改状态、那么其车子怎么处理？太容易删除了没意义；
        for (int i = 0; i < carownerIds.length; i++) { 
            Carowner carowner = carownerDAO.get(carownerIds[i]);
            checkDao.save(__getCarownerOptHistory(carowner,ICheckService.DELETE_ACTION));
            carownerDAO.delete(carowner);
        }
    }
//    public List<CarownerSource> findALLCarownerSource() {
//        List<CarownerSource> carownerSource = carownerDAO.findAllCarownerSource();
//        return carownerSource;
//    }

    public int findByCreateDateSpacingCount(Date startDate, Date endDate,int carownerSourceType) {
    	return carownerDAO.findByCreateDateSpacingCount(startDate, endDate,carownerSourceType);
    }
@Autowired
private ICheckDAO checkDao;
    public boolean update(Carowner c) {
    	boolean carownerEdited=false;
    	Integer ownerId=c.getId();
        Carowner carowner = carownerDAO.get(ownerId);
      //下面是关于车主信息的修改
        if(c.getName()!=null&&!c.getName().trim().equals(carowner.getName())){
        	checkDao.save(__getCarownerOptHistory(carowner, Carowner.NAME_NO, carowner.getName(),c.getName()));
        	carowner.setName(c.getName().trim());carownerEdited=true;
        	carowner.setPy(PinyinConv.cn2py(carowner.getName()));
        }
        if(c.getAddress()!=null&&!c.getAddress().trim().equals(carowner.getAddress())){
        	checkDao.save(__getCarownerOptHistory(carowner, Carowner.ADDRESS_NO, carowner.getAddress(),c.getAddress()));
        	carowner.setAddress(c.getAddress().trim());carownerEdited=true;
        }
        if(c.getTelephone()!=null&&!c.getTelephone().trim().equals(carowner.getTelephone())){
        	checkDao.save(__getCarownerOptHistory(carowner, Carowner.TELEPHONE_NO, carowner.getTelephone(),c.getTelephone()));
        	carowner.setTelephone(c.getTelephone().trim());carownerEdited=true;
        }
        if(c.getMobile()!=null&&!c.getMobile().trim().equals(carowner.getMobile())){
        	checkDao.save(__getCarownerOptHistory(carowner, Carowner.MOBILE_NO, carowner.getMobile(),c.getMobile()));
        	carowner.setMobile(c.getMobile().trim());carownerEdited=true;
        }
        if(c.getLicense()!=null&&!c.getLicense().trim().equals(carowner.getLicense())){
        	checkDao.save(__getCarownerOptHistory(carowner, Carowner.LICENSE_NO, carowner.getLicense(),c.getLicense()));
        	carowner.setLicense(c.getLicense().trim());carownerEdited=true;
        }
        if(carownerEdited){
        	carowner.setEditDate(new Date());
        	carowner.setEditorId(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        	carownerDAO.update(carowner);
        }
        return carownerEdited;
    }

    private final Check __getCarownerOptHistory(Carowner carowner,int col,String origStr,String currStr){
    	return __makeCarownerOptHistory__(carowner,col,origStr+"->"+currStr);
	}
    private final Check __getCarownerOptHistory(Carowner carowner,int col){
		return __makeCarownerOptHistory__(carowner,col,"");
	}
	private final Check __makeCarownerOptHistory__(Carowner carowner,int col,String notepad){
		Check c = new Check();
		c.setCheZhu(carowner.getName());
		c.setCheXiu(carowner.getAddress());
		c.setIsPost(ICheckService.RT_CZ);
		c.setTestKind(col);
		c.setStatus(carowner.getId());
		c.setNotepad(notepad);
		c.setCreateDate(new Date());
		User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		c.setCreaterId(currentUser.getId());
		return c;
	}
    @Override
	public boolean existCarowner(Carowner carowner) {
		return carownerDAO.existCarowner(carowner.getId(),carowner.getLicense(),carowner.getName());
	}
}
