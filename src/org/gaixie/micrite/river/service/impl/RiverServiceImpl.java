package org.gaixie.micrite.river.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.gaixie.micrite.check.dao.ICheckDAO;
import org.gaixie.micrite.check.dao.hibernate.CheckDAOImpl;
import org.gaixie.micrite.check.service.ICheckService;
import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.dic.dao.IDictionaryDAO;
import org.gaixie.micrite.dic.service.IDictionaryService;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Check;
import org.gaixie.micrite.beans.River;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.IdName;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.river.dao.IRiverDAO;
import org.gaixie.micrite.river.service.IRiverService;
import org.gaixie.micrite.util.CalendarUtil;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;

import com.hansheng.njj.PinyinConv;
/**
 * 维修企业管理功能实现
 * @see org.gaixie.micrite.river.service.IRiverService
 */
public class RiverServiceImpl implements IRiverService { 

    @Override
	public List<IdName> findRiverByPy(String py) {
		// TODO Auto-generated method stub
		return riverDAO.findRiverByPy(py);
	}

	@Autowired
    private IRiverDAO riverDAO;
    @Autowired
    private ICheckDAO checkDao;
    @Autowired
    private IDictionaryDAO dictionaryDAO;
    public void add(River river) {
    	river.setPy(PinyinConv.cn2py(river.getRname()));
		river.setBegindate(new Date());
//		if(river.getEditDate()==null)
		river.setModifydate(river.getBegindate());
		river.setCreaterId(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
		river.setEditorId(river.getCreaterId());
    	try {
    		riverDAO.save(river);	
    		checkDao.save(__getAddHistory(river));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
    
    public List<River> advancedFindByPerPage(SearchBean[] queryBean, int start,
            int limit){
        List<River> list = riverDAO.advancedFindByPerPage(queryBean,start,limit);
        return list;
    }

    public int advancedFindCount(SearchBean[] queryBean){
        return riverDAO.advancedFindCount(queryBean); 
    }

    
    public void delete(int[] entIds) {
        for (int i = 0; i < entIds.length; i++) { 
            River e = riverDAO.get(entIds[i]);
            checkDao.save(__getDeleteHistory(e));
            riverDAO.delete(e);
        }
    }
    public int findByCreateDateSpacingCount(Date startDate, Date endDate,int RiverSourceType) {
    	return riverDAO.findByCreateDateSpacingCount(startDate, endDate,RiverSourceType);
    }

    public List<River> findByCreateDateSpacingPerPage(Date startDate,
            Date endDate, int start, int limit,int villageId) {
        List<River> list = riverDAO.findByCreateDateSpacingPerPage(startDate, endDate, start, limit,villageId);
        return list;
    }

    public CategoryDataset getRiverSourceBarDataset(SearchBean[] queryBean){
        List list = riverDAO.findCSGroupByTelVague(queryBean);
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

    public PieDataset getRiverSourcePieDataset(SearchBean[] queryBean){
        List list = riverDAO.findCSGroupByTelVague(queryBean);
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
	 * 企业被更新时需要留下修改日志
	 * */
    public void update(River c) {
    	DateFormat df = CalendarUtil.df;
    	boolean isEdited=false;
    	int enId=c.getId();
        River river = riverDAO.get(c.getId());
        
//        if(c.getAddress()!=null&&!c.getAddress().trim().equals(river.getAddress())){
//        	checkDao.save(__getOptHistory(river, River.ADDRESS_NO, river.getAddress(),c.getAddress()));
//        	river.setAddress(c.getAddress().trim());isEdited=true;
//        }
//        if(c.getCommission()!=null&&!c.getCommission().trim().equals(river.getCommission())){
//        	checkDao.save(__getOptHistory(river, River.COMMISSION_NO, river.getCommission(),c.getCommission()));
//        	river.setCommission(c.getCommission().trim());isEdited=true;
//        }
//        if(c.getDateBegin()!=null&&(river.getDateBegin()==null||!df.format(c.getDateBegin()).equals(df.format(river.getDateBegin())))){
//        	checkDao.save(__getOptHistory(river, River.DATEBEGIN_NO, (river.getDateBegin()==null?"null":df.format(river.getDateBegin())),df.format(c.getDateBegin())));
//        	river.setDateBegin(c.getDateBegin());isEdited=true;
//        }
//        if(c.getDateEnd()!=null&&(river.getDateEnd()==null||!df.format(c.getDateEnd()).equals(df.format(river.getDateEnd())))){
//        	checkDao.save(__getOptHistory(river, River.DATEEND_NO, (river.getDateEnd()==null?"null":df.format(river.getDateEnd())),df.format(c.getDateEnd())));
//        	river.setDateEnd(c.getDateEnd());isEdited=true;
//        }
//        if(c.getHandleMan()!=null&&!c.getHandleMan().trim().equals(river.getHandleMan())){
//        	checkDao.save(__getOptHistory(river, River.HANDLEMAN_NO, river.getHandleMan(),c.getHandleMan()));
//        	river.setHandleMan(c.getHandleMan().trim());isEdited=true;
//        }
//        if(c.getKind()!=null&&c.getKind().getId()!=null&&c.getKind().getId().intValue()!=river.getKind().getId().intValue()){
//        	checkDao.save(__getOptHistory(river, River.KIND_NO,river.getKind(),c.getKind()));
//        	river.setKind(c.getKind());isEdited=true;
//        }
//        if(c.getLegalPerson()!=null&&!c.getLegalPerson().trim().equals(river.getLegalPerson())){
//        	checkDao.save(__getOptHistory(river, River.LEGALPERSON_NO, river.getLegalPerson(),c.getLegalPerson()));
//        	river.setLegalPerson(c.getLegalPerson().trim());isEdited=true;
//        }
//        if(c.getLicense()!=null&&!c.getLicense().trim().equals(river.getLicense())){
//        	checkDao.save(__getOptHistory(river, River.LICENSE_NO, river.getLicense(),c.getLicense()));
//        	river.setLicense(c.getLicense().trim());isEdited=true;
//        }
//        if(c.getLicenseDate()!=null&&(river.getLicenseDate()==null||!df.format(c.getLicenseDate()).equals(df.format(river.getLicenseDate())))){
//        	checkDao.save(__getOptHistory(river, River.LICENSEDATE_NO, (river.getLicenseDate()==null?"null":df.format(river.getLicenseDate())),df.format(c.getLicenseDate())));
//        	river.setLicenseDate(c.getLicenseDate());isEdited=true;
//        }
//        if(c.getQualification()!=null&&c.getQualification().getId()!=null&&c.getQualification().getId().intValue()!=river.getQualification().getId().intValue()){
//        	checkDao.save(__getOptHistory(river, River.QUALIFICATION_NO,river.getQualification(),c.getQualification()));
//        	river.setQualification(c.getQualification());isEdited=true;
//        }
//        if(c.getStation()!=null&&c.getStation().getId()!=null&&c.getStation().getId().intValue()!=river.getStation().getId().intValue()){
//        	checkDao.save(__getOptHistory(river, River.STATION_NO,river.getStation(),c.getStation()));
//        	river.setStation(c.getStation());isEdited=true;
//        }
//        if(c.getTelephone1()!=null&&!c.getTelephone1().trim().equals(river.getTelephone1())){
//        	checkDao.save(__getOptHistory(river, River.TELEPHONE1_NO, river.getTelephone1(),c.getTelephone1()));
//        	river.setTelephone1(c.getTelephone1().trim());isEdited=true;
//        }
//        if(c.getTelephone2()!=null&&!c.getTelephone2().trim().equals(river.getTelephone2())){
//        	checkDao.save(__getOptHistory(river, River.TELEPHONE2_NO, river.getTelephone2(),c.getTelephone2()));
//        	river.setTelephone2(c.getTelephone2().trim());isEdited=true;
//        }
//        if(c.getTelephone3()!=null&&!c.getTelephone3().trim().equals(river.getTelephone3())){
//        	checkDao.save(__getOptHistory(river, River.TELEPHONE3_NO, river.getTelephone3(),c.getTelephone3()));
//        	river.setTelephone3(c.getTelephone3().trim());isEdited=true;
//        }
//        if(c.getTelephone4()!=null&&!c.getTelephone4().trim().equals(river.getTelephone4())){
//        	checkDao.save(__getOptHistory(river, River.TELEPHONE4_NO, river.getTelephone4(),c.getTelephone4()));
//        	river.setTelephone4(c.getTelephone4().trim());isEdited=true;
//        }
//        if(c.getUnitName()!=null&&!c.getUnitName().trim().equals(river.getUnitName())){
//        	checkDao.save(__getOptHistory(river, River.UNITNAME_NO, river.getUnitName(),c.getUnitName()));
//        	river.setUnitName(c.getUnitName().trim());isEdited=true;
//        	river.setPy(PinyinConv.cn2py(river.getUnitName()));
//        }
//        if(c.getWorkArea()!=null&&!c.getWorkArea().trim().equals(river.getWorkArea())){
//        	checkDao.save(__getOptHistory(river, River.WORKAREA_NO, river.getWorkArea(),c.getWorkArea()));
//        	river.setWorkArea(c.getWorkArea().trim());isEdited=true;
//        }
//        if(c.getWorkRemark()!=null&&!c.getWorkRemark().trim().equals(river.getWorkRemark())){
//        	checkDao.save(__getOptHistory(river, River.WORKREMARK_NO, river.getWorkRemark(),c.getWorkRemark()));
//        	river.setWorkRemark(c.getWorkRemark().trim());isEdited=true;
//        }
//        if(c.getWorkType()!=null&&c.getWorkType().getId()!=null&&c.getWorkType().getId().intValue()!=river.getWorkType().getId().intValue()){
//        	checkDao.save(__getOptHistory(river, River.WORKTYPE_NO,river.getWorkType(),c.getWorkType()));
//        	river.setWorkType(c.getWorkType());isEdited=true;
//        }
        if(isEdited){
        	river.setModifydate(new Date());
        	river.setEditorId(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        	riverDAO.update(river);
        }
    }
    final private Check __getOptHistory(River e,int col,Dictionary orig,Dictionary curr){
    	return __getOptHistory(e,col,orig.getName(),dictionaryDAO.getName(curr.getId()));
    }
    final private Check __getDeleteHistory(River e){
    	return __getOptHistory(e,ICheckService.DELETE_ACTION,"");
    }
    final private Check __getAddHistory(River e){
    	return __getOptHistory(e,ICheckService.ADD_ACTION,"");
    }
    final private Check __getOptHistory(River e,int col,String notepad){
    	Check c = new Check();
//		c.setIsPost(ICheckService.RT_QY);
		c.setCheZhu(e.getRname());
//		c.setTestKind(col);
		c.setStatus(e.getId());
		c.setNotepad(notepad);
		c.setCreateDate(new Date());//这个是解除的时间
		User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		c.setCreaterId(currentUser.getId());
		return c;
    }
    final private Check __getOptHistory(River e,int col,String origStr,String currStr){
		return __getOptHistory(e,col,origStr+"->"+currStr);
	}

    @Override
	public boolean existRiver(River river) {
		// TODO Auto-generated method stub
		return riverDAO.existRiver(river.getId(),river.getRid());
	}
    @Override
	public boolean existRiver(Integer id,String license) {
		// TODO Auto-generated method stub
		return riverDAO.existRiver(id,license);
	}

	@Override
	public River get(Integer id) {
		// TODO Auto-generated method stub
		return riverDAO.get(id);
	}

}
