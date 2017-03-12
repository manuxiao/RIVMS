package org.gaixie.micrite.enterprise.service.impl;

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
import org.gaixie.micrite.beans.Enterprise;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.IdName;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.enterprise.dao.IEnterpriseDAO;
import org.gaixie.micrite.enterprise.service.IEnterpriseService;
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
 * @see org.gaixie.micrite.enterprise.service.IEnterpriseService
 */
public class EnterpriseServiceImpl implements IEnterpriseService { 

    @Override
	public List<IdName> findEnterpriseByPy(String py) {
		// TODO Auto-generated method stub
		return enterpriseDAO.findEnterpriseByPy(py);
	}

	@Autowired
    private IEnterpriseDAO enterpriseDAO;
    @Autowired
    private ICheckDAO checkDao;
    @Autowired
    private IDictionaryDAO dictionaryDAO;
    public void add(Enterprise enterprise) {
    	enterprise.setPy(PinyinConv.cn2py(enterprise.getUnitName()));
		enterprise.setCreateDate(new Date());
//		if(enterprise.getEditDate()==null)
		enterprise.setEditDate(enterprise.getCreateDate());
		enterprise.setCreaterId(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
		enterprise.setEditorId(enterprise.getCreaterId());
    	try {
    		enterpriseDAO.save(enterprise);	
    		checkDao.save(__getAddHistory(enterprise));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
    
    public List<Enterprise> advancedFindByPerPage(SearchBean[] queryBean, int start,
            int limit){
        List<Enterprise> list = enterpriseDAO.advancedFindByPerPage(queryBean,start,limit);
        return list;
    }

    public int advancedFindCount(SearchBean[] queryBean){
        return enterpriseDAO.advancedFindCount(queryBean); 
    }

    
    public void delete(int[] entIds) {
        for (int i = 0; i < entIds.length; i++) { 
            Enterprise e = enterpriseDAO.get(entIds[i]);
            checkDao.save(__getDeleteHistory(e));
            enterpriseDAO.delete(e);
        }
    }
    public int findByCreateDateSpacingCount(Date startDate, Date endDate,int EnterpriseSourceType) {
    	return enterpriseDAO.findByCreateDateSpacingCount(startDate, endDate,EnterpriseSourceType);
    }

    public List<Enterprise> findByCreateDateSpacingPerPage(Date startDate,
            Date endDate, int start, int limit,int EnterpriseSourceType) {
        List<Enterprise> list = enterpriseDAO.findByCreateDateSpacingPerPage(startDate, endDate, start, limit,EnterpriseSourceType);
        return list;
    }

    public CategoryDataset getEnterpriseSourceBarDataset(SearchBean[] queryBean){
        List list = enterpriseDAO.findCSGroupByTelVague(queryBean);
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

    public PieDataset getEnterpriseSourcePieDataset(SearchBean[] queryBean){
        List list = enterpriseDAO.findCSGroupByTelVague(queryBean);
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
    public void update(Enterprise c) {
    	DateFormat df = CalendarUtil.df;
    	boolean isEdited=false;
    	int enId=c.getId();
        Enterprise enterprise = enterpriseDAO.get(c.getId());
        
        if(c.getAddress()!=null&&!c.getAddress().trim().equals(enterprise.getAddress())){
        	checkDao.save(__getOptHistory(enterprise, Enterprise.ADDRESS_NO, enterprise.getAddress(),c.getAddress()));
        	enterprise.setAddress(c.getAddress().trim());isEdited=true;
        }
        if(c.getCommission()!=null&&!c.getCommission().trim().equals(enterprise.getCommission())){
        	checkDao.save(__getOptHistory(enterprise, Enterprise.COMMISSION_NO, enterprise.getCommission(),c.getCommission()));
        	enterprise.setCommission(c.getCommission().trim());isEdited=true;
        }
        if(c.getDateBegin()!=null&&(enterprise.getDateBegin()==null||!df.format(c.getDateBegin()).equals(df.format(enterprise.getDateBegin())))){
        	checkDao.save(__getOptHistory(enterprise, Enterprise.DATEBEGIN_NO, (enterprise.getDateBegin()==null?"null":df.format(enterprise.getDateBegin())),df.format(c.getDateBegin())));
        	enterprise.setDateBegin(c.getDateBegin());isEdited=true;
        }
        if(c.getDateEnd()!=null&&(enterprise.getDateEnd()==null||!df.format(c.getDateEnd()).equals(df.format(enterprise.getDateEnd())))){
        	checkDao.save(__getOptHistory(enterprise, Enterprise.DATEEND_NO, (enterprise.getDateEnd()==null?"null":df.format(enterprise.getDateEnd())),df.format(c.getDateEnd())));
        	enterprise.setDateEnd(c.getDateEnd());isEdited=true;
        }
        if(c.getHandleMan()!=null&&!c.getHandleMan().trim().equals(enterprise.getHandleMan())){
        	checkDao.save(__getOptHistory(enterprise, Enterprise.HANDLEMAN_NO, enterprise.getHandleMan(),c.getHandleMan()));
        	enterprise.setHandleMan(c.getHandleMan().trim());isEdited=true;
        }
        if(c.getKind()!=null&&c.getKind().getId()!=null&&c.getKind().getId().intValue()!=enterprise.getKind().getId().intValue()){
        	checkDao.save(__getOptHistory(enterprise, Enterprise.KIND_NO,enterprise.getKind(),c.getKind()));
        	enterprise.setKind(c.getKind());isEdited=true;
        }
        if(c.getLegalPerson()!=null&&!c.getLegalPerson().trim().equals(enterprise.getLegalPerson())){
        	checkDao.save(__getOptHistory(enterprise, Enterprise.LEGALPERSON_NO, enterprise.getLegalPerson(),c.getLegalPerson()));
        	enterprise.setLegalPerson(c.getLegalPerson().trim());isEdited=true;
        }
        if(c.getLicense()!=null&&!c.getLicense().trim().equals(enterprise.getLicense())){
        	checkDao.save(__getOptHistory(enterprise, Enterprise.LICENSE_NO, enterprise.getLicense(),c.getLicense()));
        	enterprise.setLicense(c.getLicense().trim());isEdited=true;
        }
        if(c.getLicenseDate()!=null&&(enterprise.getLicenseDate()==null||!df.format(c.getLicenseDate()).equals(df.format(enterprise.getLicenseDate())))){
        	checkDao.save(__getOptHistory(enterprise, Enterprise.LICENSEDATE_NO, (enterprise.getLicenseDate()==null?"null":df.format(enterprise.getLicenseDate())),df.format(c.getLicenseDate())));
        	enterprise.setLicenseDate(c.getLicenseDate());isEdited=true;
        }
        if(c.getQualification()!=null&&c.getQualification().getId()!=null&&c.getQualification().getId().intValue()!=enterprise.getQualification().getId().intValue()){
        	checkDao.save(__getOptHistory(enterprise, Enterprise.QUALIFICATION_NO,enterprise.getQualification(),c.getQualification()));
        	enterprise.setQualification(c.getQualification());isEdited=true;
        }
        if(c.getStation()!=null&&c.getStation().getId()!=null&&c.getStation().getId().intValue()!=enterprise.getStation().getId().intValue()){
        	checkDao.save(__getOptHistory(enterprise, Enterprise.STATION_NO,enterprise.getStation(),c.getStation()));
        	enterprise.setStation(c.getStation());isEdited=true;
        }
        if(c.getTelephone1()!=null&&!c.getTelephone1().trim().equals(enterprise.getTelephone1())){
        	checkDao.save(__getOptHistory(enterprise, Enterprise.TELEPHONE1_NO, enterprise.getTelephone1(),c.getTelephone1()));
        	enterprise.setTelephone1(c.getTelephone1().trim());isEdited=true;
        }
        if(c.getTelephone2()!=null&&!c.getTelephone2().trim().equals(enterprise.getTelephone2())){
        	checkDao.save(__getOptHistory(enterprise, Enterprise.TELEPHONE2_NO, enterprise.getTelephone2(),c.getTelephone2()));
        	enterprise.setTelephone2(c.getTelephone2().trim());isEdited=true;
        }
        if(c.getTelephone3()!=null&&!c.getTelephone3().trim().equals(enterprise.getTelephone3())){
        	checkDao.save(__getOptHistory(enterprise, Enterprise.TELEPHONE3_NO, enterprise.getTelephone3(),c.getTelephone3()));
        	enterprise.setTelephone3(c.getTelephone3().trim());isEdited=true;
        }
        if(c.getTelephone4()!=null&&!c.getTelephone4().trim().equals(enterprise.getTelephone4())){
        	checkDao.save(__getOptHistory(enterprise, Enterprise.TELEPHONE4_NO, enterprise.getTelephone4(),c.getTelephone4()));
        	enterprise.setTelephone4(c.getTelephone4().trim());isEdited=true;
        }
        if(c.getUnitName()!=null&&!c.getUnitName().trim().equals(enterprise.getUnitName())){
        	checkDao.save(__getOptHistory(enterprise, Enterprise.UNITNAME_NO, enterprise.getUnitName(),c.getUnitName()));
        	enterprise.setUnitName(c.getUnitName().trim());isEdited=true;
        	enterprise.setPy(PinyinConv.cn2py(enterprise.getUnitName()));
        }
        if(c.getWorkArea()!=null&&!c.getWorkArea().trim().equals(enterprise.getWorkArea())){
        	checkDao.save(__getOptHistory(enterprise, Enterprise.WORKAREA_NO, enterprise.getWorkArea(),c.getWorkArea()));
        	enterprise.setWorkArea(c.getWorkArea().trim());isEdited=true;
        }
        if(c.getWorkRemark()!=null&&!c.getWorkRemark().trim().equals(enterprise.getWorkRemark())){
        	checkDao.save(__getOptHistory(enterprise, Enterprise.WORKREMARK_NO, enterprise.getWorkRemark(),c.getWorkRemark()));
        	enterprise.setWorkRemark(c.getWorkRemark().trim());isEdited=true;
        }
        if(c.getWorkType()!=null&&c.getWorkType().getId()!=null&&c.getWorkType().getId().intValue()!=enterprise.getWorkType().getId().intValue()){
        	checkDao.save(__getOptHistory(enterprise, Enterprise.WORKTYPE_NO,enterprise.getWorkType(),c.getWorkType()));
        	enterprise.setWorkType(c.getWorkType());isEdited=true;
        }
        if(isEdited){
        	enterprise.setEditDate(new Date());
        	enterprise.setEditorId(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        	enterpriseDAO.update(enterprise);
        }
    }
    final private Check __getOptHistory(Enterprise e,int col,Dictionary orig,Dictionary curr){
    	return __getOptHistory(e,col,orig.getName(),dictionaryDAO.getName(curr.getId()));
    }
    final private Check __getDeleteHistory(Enterprise e){
    	return __getOptHistory(e,ICheckService.DELETE_ACTION,"");
    }
    final private Check __getAddHistory(Enterprise e){
    	return __getOptHistory(e,ICheckService.ADD_ACTION,"");
    }
    final private Check __getOptHistory(Enterprise e,int col,String notepad){
    	Check c = new Check();
		c.setIsPost(ICheckService.RT_QY);
		c.setCheZhu(e.getUnitName());
		c.setTestKind(col);
		c.setStatus(e.getId());
		c.setNotepad(notepad);
		c.setCreateDate(new Date());//这个是解除的时间
		User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		c.setCreaterId(currentUser.getId());
		return c;
    }
    final private Check __getOptHistory(Enterprise e,int col,String origStr,String currStr){
		return __getOptHistory(e,col,origStr+"->"+currStr);
	}

    @Override
	public boolean existEnterprise(Enterprise enterprise) {
		// TODO Auto-generated method stub
		return enterpriseDAO.existEnterprise(enterprise.getId(),enterprise.getLicense());
	}
    @Override
	public boolean existEnterprise(Integer id,String license) {
		// TODO Auto-generated method stub
		return enterpriseDAO.existEnterprise(id,license);
	}

	@Override
	public Enterprise get(Integer id) {
		// TODO Auto-generated method stub
		return enterpriseDAO.get(id);
	}

}
