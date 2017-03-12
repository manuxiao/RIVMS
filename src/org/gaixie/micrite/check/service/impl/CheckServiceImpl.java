package org.gaixie.micrite.check.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Check;
import org.gaixie.micrite.beans.Standard;
import org.gaixie.micrite.car.dao.ICarfileDAO;
import org.gaixie.micrite.car.service.ICarfileService;
import org.gaixie.micrite.check.dao.ICheckDAO;
import org.gaixie.micrite.check.service.ICheckService;
import org.gaixie.micrite.dic.dao.IDictionaryDAO;
import org.gaixie.micrite.standard.dao.IStandardDAO;
import org.gaixie.micrite.standard.service.IStandardService;
import org.gaixie.micrite.util.CalendarUtil;
import org.gaixie.micrite.util.DictionaryUtil;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 检测记录管理功能实现
 * 
 * @see org.gaixie.micrite.check.service.ICheckService
 */
public class CheckServiceImpl implements ICheckService {
	private static Logger log = Logger.getLogger(CheckServiceImpl.class);
	@Autowired
	private IStandardService standardService;
	@Autowired
	private ICarfileDAO carDao;
	@Autowired
	private ICheckDAO checkDAO;
	@Autowired
	private IDictionaryDAO dictionaryDAO;
	@Override
	public List<Check> findRecentPostCheckOf(Carfile car, int start, int limit) {
		// TODO Auto-generated method stub
		return checkDAO.findRecentPostCheckOf(car, start, limit);
	}

	@Override
	public boolean isUsed(Integer testNo) {
		// TODO Auto-generated method stub
		return checkDAO.isUsed(testNo);
	}

	@Override
	public void setPrinted(int[] CheckIds) {
		// TODO Auto-generated method stub
		for (int i = 0; i < CheckIds.length; i++) {
			Check check = checkDAO.get(CheckIds[i]);
			if (check == null)
				continue;
			check.setPrinted(PRINTED_YES);
			checkDAO.update(check);
		}
	}

	@Override
	public List<Check> getChecks(int[] CheckIds) {
		// TODO Auto-generated method stub
		List<Check> list = new ArrayList<Check>();
		for (int i = CheckIds.length - 1; i > -1; i--) {
			list.add(checkDAO.get(CheckIds[i]));
		}
		return list;
	}

	public void add(Check check) {
		checkDAO.save(check);
	}

	public void addWithTestKind(Check check) {
		addWithTestKindNotMinus(check);
		standardService.minus(check.getHeGe());// 库存减减
	}

	public void addWithTestKindNotMinus(Check check) {
		int carId = check.getCarId();
		Carfile car = carDao.get(carId);
		if(car.getNotepad()!=null){//临时记事本如果有内容就清一下
			car.setNotepad(null);
		}
		check.setIsPost(ICheckService.ISPOST_NOT);
		check.setPrinted(ICheckService.PRINTED_NOT);
		//自动查找如果能匹配上则认为是来自检测站，否则认为手工添加，匹配的条件为heGe\jianTime
		List<Check> list=checkDAO.getOrigCheckOf(check.getHeGe(), check.getJianTime());
		if(list==null||list.size()==0){
			check.setStatus(ICheckService.STATUS_NO_REF);
		}else{
			check.setStatus(list.get(0).getId());
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setStatus(ICheckService.STATUS_USED);//被引用的记录标识为已引用
				checkDAO.update(list.get(i));
			}
		}
		if (check.getTestKind() == ICheckService.TESTKIND_EVAL) {// 若testKind为等级评定，则再增加一条等级评定记录
			Check djCheck = new Check();//技术等级
			try {
				BeanUtils.copyProperties(djCheck, check);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
			//实际的技术等级评定有效截止日期为实际检测日期步进一个技术等级评定周期
			djCheck.setEndTime(CalendarUtil.afterMonth(check.getJianTime(), car.getEvaluateCycle().getValue()));
			
			//车辆表里的技术等级有效截止日期为原来的有效截止日期步进一个技术等级评定周期
			car.setEvaluateDate(CalendarUtil.afterMonth(car.getEvaluateDate(), car.getEvaluateCycle().getValue()));
			
			checkDAO.save(djCheck);
			
			check.setTestKind(ICheckService.TESTKIND_MAIN);//原来的数据需要变为二级维护
			check.setCheDengji(0);
		}
		car.setMaintainDate(check.getJianTime());
		car.setMaintainDateEnd(check.getEndTime());// 更新二级维护时间
		car.setDaysToExpired(CalendarUtil.getDaysBetween(new Date(), car.getMaintainDateEnd()));
//		car.setExpired(ICarfileService.EXPIRED_NOT);
		car.setApproval(ICarfileService.APPROVAL_NOT);
		checkDAO.save(check);
		carDao.save(car);
	}
	
	public void addFromNewCar(Carfile car){

        Check check = new Check();
        check.setCarId(car.getId());
        check.setIsPost(ICheckService.ISPOST_NOT);
        check.setTestKind(ICheckService.TESTKIND_EVAL);
        check.setCheDengji(dictionaryDAO.get(car.getSkillRank().getId()).getValue());
        check.setJianTime(car.getMaintainDate());
        Date evalEndDate=CalendarUtil.afterMonth(car.getMaintainDate(), dictionaryDAO.get(car.getEvaluateCycle().getId()).getValue());
        check.setEndTime(evalEndDate);
        car.setEvaluateDate(evalEndDate);
        check.setPaiSe(car.getPaiSe());
        check.setPaiHao(car.getLicenseNumber());
        check.setCreateDate(car.getCreateDate());
        check.setCreaterId(car.getCreaterId());
        checkDAO.save(check);
        check = new Check();
        check.setCarId(car.getId());
        check.setIsPost(ICheckService.ISPOST_NOT);
        check.setTestKind(ICheckService.TESTKIND_MAIN);
        check.setPaiSe(car.getPaiSe());
        check.setPaiHao(car.getLicenseNumber());
        check.setJianTime(car.getMaintainDate());
        check.setEndTime(car.getMaintainDateEnd());
        check.setCreateDate(car.getCreateDate());
        check.setCreaterId(car.getCreaterId());
        checkDAO.save(check);
	}
	public List<Check> advancedFindByPerPage(SearchBean[] queryBean, int start,
			int limit) {
		List<Check> list = checkDAO.advancedFindByPerPage(queryBean, start,
				limit);
		return list;
	}

	public List<Check> advancedFindByPerPageByCar(Dictionary paiSe,
			String paiHao, int start, int limit) {
		List<Check> list = checkDAO.advancedFindByPerPageByCar(paiSe, paiHao,
				start, limit);
		return list;
	}

	public int advancedFindCount(SearchBean[] queryBean) {
		return checkDAO.advancedFindCount(queryBean);
	}

	public void delete(int[] CheckIds) {
		for (int i = 0; i < CheckIds.length; i++) {
			Check check = checkDAO.get(CheckIds[i]);
			checkDAO.delete(check);
		}
	}

	public void setNormal(int[] CheckIds) {
		setStatus(CheckIds, STATUS_NORMAL);
	}

	public void setCancel(int[] CheckIds) {
		setStatus(CheckIds, STATUS_USED);
	}

	private void setStatus(int[] CheckIds, Integer status) {
		for (int i = 0; i < CheckIds.length; i++) {
			Check check = checkDAO.get(CheckIds[i]);
			if (check == null)
				continue;
			check.setStatus(status);
			checkDAO.save(check);
		}
	}

	public int findByCreateDateSpacingCount(Date startDate, Date endDate) {
		return checkDAO.findByCreateDateSpacingCount(startDate, endDate);
	}

	public List<Check> findByCreateDateSpacingPerPage(Date startDate,
			Date endDate, int start, int limit) {
		List<Check> list = checkDAO.findByCreateDateSpacingPerPage(startDate,
				endDate, start, limit);
		return list;
	}
	
	public int findHistoryCarfile(Date startDate,Date endDate,String paiHao,Integer paiSe,Integer createrId,Integer testKind) {
		return checkDAO.findHistoryAboutCarfile(startDate,endDate,paiHao,paiSe,createrId,testKind,ICheckService.RT_CL);
	}
	public List<Check> findHistoryCarfilePerPage(Date startDate,Date endDate,String paiHao,int start, int limit,Integer paiSe,Integer createrId,Integer testKind) {
		List<Check> list = checkDAO.findHistoryAboutCarfilePerPage(startDate,endDate,paiHao,start,limit,paiSe,createrId,testKind,ICheckService.RT_CL);
		return list;
	}

	@Override
	public int findHistoryCheck(Date startDate, Date endDate, String paiHao,
			Integer paiSe, Integer createrId, Integer testKind) {
		return checkDAO.findHistoryAboutCarfile(startDate,endDate,paiHao,paiSe,createrId,testKind,ICheckService.RT_SG);
	}

	@Override
	public List<Check> findHistoryCheckPerPage(Date startDate, Date endDate,
			String paiHao, int start, int limit, Integer paiSe,
			Integer createrId, Integer testKind) {
		// TODO Auto-generated method stub
		List<Check> list = checkDAO.findHistoryAboutCarfilePerPage(startDate,endDate,paiHao,start,limit,paiSe,createrId,testKind,ICheckService.RT_SG);
		return list;
	}

	public int findHistoryEnterprise(Date startDate,Date endDate,Integer enterpriseId,Integer createrId,Integer testKind) {
		return checkDAO.findHistoryEnterprise(startDate,endDate,enterpriseId,createrId,testKind);
	}
	public List<Check> findHistoryEnterprisePerPage(Date startDate,Date endDate,int start, int limit,Integer enterpriseId,Integer createrId,Integer testKind) {
		List<Check> list = checkDAO.findHistoryEnterprisePerPage(startDate,endDate,start,limit,enterpriseId,createrId,testKind);
		return list;
	}
	
	public int findHistoryCarowner(Date startDate,Date endDate,Integer carownerId,Integer createrId,Integer testKind) {
		return checkDAO.findHistoryCarowner(startDate,endDate,carownerId,createrId,testKind);
	}
	public List<Check> findHistoryCarownerPerPage(Date startDate,Date endDate,int start, int limit,Integer carownerId,Integer createrId,Integer testKind) {
		List<Check> list = checkDAO.findHistoryCarownerPerPage(startDate,endDate,start,limit,carownerId,createrId,testKind);
		return list;
	}
	
	public int findHistoryStandard(Date startDate,Date endDate,Integer heGe,Integer createrId,Integer testKind) {
		return checkDAO.findHistoryStandard(startDate,endDate,heGe,createrId,testKind);
	}
	public List<Check> findHistoryStandardPerPage(Date startDate,Date endDate,int start, int limit,Integer heGe,Integer createrId,Integer testKind) {
		List<Check> list = checkDAO.findHistoryStandardPerPage(startDate,endDate,start,limit,heGe,createrId,testKind);
		return list;
	}
	
	public Check findByStandardNo(Integer standarNo){
		return checkDAO.findByStandardNo(standarNo);
	}
}
