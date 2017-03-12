package org.gaixie.micrite.check.action;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.ServletActionContext;

import org.gaixie.micrite.action.GenericAction;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.Check;
import org.gaixie.micrite.beans.Enterprise;
import org.gaixie.micrite.beans.Standard;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.car.service.ICarfileService;
import org.gaixie.micrite.check.service.ICheckService;
import org.gaixie.micrite.dic.service.IDictionaryService;
import org.gaixie.micrite.dic.service.impl.DictionaryServiceImpl;
import org.gaixie.micrite.standard.service.IStandardService;
import org.gaixie.micrite.util.CalendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;

/**
 * CheckAction用来响应用户对Check基本信息维护时的操作，并调用相关的Service。
 * <p>
 * 通过调用相关的Service类，完成对Check基本信息的增加，删除，查询。(修改待定)
 */
public class HistoryAction extends GenericAction {
	private static final long serialVersionUID = 3072131320220662398L;

	@Autowired
	private ICheckService checkService;
	// 获取的页面参数
	private Check check;	
	
	@Autowired
	private IStandardService standardService;
//	public IStandardService getStandardService() {
//		return standardService;
//	}

//	public void setStandardService(IStandardService standardService) {
//		this.standardService = standardService;
//	}
	public Standard getStandard() {
		return standard;
	}
	public void setStandard(Standard standard) {
		this.standard = standard;
	}
	// 获取的页面参数
	private Standard standard;
	
	// ------------------------
	private int checkId;

	public int getCheckId() {
		return checkId;
	}

	public void setCheckId(int checkId) {
		this.checkId = checkId;
	}

	private int[] checkIds;
	private Date startDate;
	private Date endDate;
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	
	private Integer createrId;
	public Integer getCarownerId() {
		return carownerId;
	}

	public void setCarownerId(Integer carownerId) {
		this.carownerId = carownerId;
	}
	private Integer enterpriseId;
	private Integer carownerId;
	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
//		if(enterpriseId!=null&&enterpriseId<0)
//			this.enterpriseId = null;
//		else
			this.enterpriseId = enterpriseId;
	}

	public Integer getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Integer createrId) {
//		if(createrId!=null&&createrId<0)
//			this.createrId = null;
//		else
			this.createrId = createrId;
	}

	// ~~~~~~页面check的参数~~~~~~~~~~//
	private int id;
	private int carId;
	

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/** 检测日期 第3行jian_time=2003/02/28 */
	private Date jianTime;
	// 检测类型
	private Integer testKind;

	public Integer getTestKind() {
		return testKind;
	}
	public void setTestKind(Integer testKind) {
//		if(testKind!=null&&testKind<0)
//			this.testKind = null;
//		else
			this.testKind = testKind;
	}

	public Date getJianTime() {
		return jianTime;
	}

	public void setJianTime(Date jianTime) {
		this.jianTime = jianTime;
	}

	public String getPaiHao() {
		return paiHao;
	}

	public void setPaiHao(String paiHao) {
		this.paiHao = paiHao;
	}

	public Integer getHeGe() {
		return heGe;
	}

	public void setHeGe(Integer heGe) {
//		if(heGe!=null&&heGe<0)
//			this.heGe = null;
//		else
			this.heGe = heGe;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	private String notepad;
	public String getNotepad() {
		return notepad;
	}

	public void setNotepad(String notepad) {
		this.notepad = notepad;
	}

	public Integer getPaiSeId() {
		return paiSeId;
	}

	public void setPaiSeId(Integer paiSeId) {
//		if(paiSeId!=null&&paiSeId<0)
//			this.paiSeId = null;
//		else
			this.paiSeId = paiSeId;
	}
	/** 车辆牌号 第9行 pai_hao=BB4344 */
	private String paiHao;

	/** 合格证 编号 第23行 he_ge=283224 */
	private Integer heGe;

	/** 车牌颜色 第94行 pai_she */
	private Integer paiSeId; // 车牌颜色
	/** 记录产生时间 */
	private Date createDate;
	/**
	 * 查询车辆二维等级历史记录
	 * 
	 * @return "success"
	 */
	public String findHistoryCheck() {
		if (isFirstSearch()) {
			Integer count = checkService.findHistoryCarfile(startDate, endDate,paiHao,paiSeId,createrId,testKind);
			setTotalCount(count);
		}
		List<Check> checks = checkService.findHistoryCheckPerPage(startDate, endDate,paiHao,this.getStart(), this.getLimit(),paiSeId,createrId,testKind);
		this.putResultList(checks);
		return SUCCESS;
	}
	/**
	 * 查询车辆历史记录
	 * 
	 * @return "success"
	 */
	public String findHistoryCarfile() {
//		if(enterpriseId!=null&&enterpriseId==0){
//			enterpriseId=null;
//		}
//		if(createrId!=null&&createrId==0){
//			createrId=null;
//		}
		if (isFirstSearch()) {
			// 初次查询时，要从数据库中读取总记录数
			Integer count = checkService.findHistoryCarfile(startDate, endDate,paiHao,paiSeId,createrId,testKind);
			setTotalCount(count);
		}
		// 得到分页查询结果
		List<Check> checks = checkService.findHistoryCarfilePerPage(startDate, endDate,paiHao,this.getStart(), this.getLimit(),paiSeId,createrId,testKind);
		this.putResultList(checks);
		return SUCCESS;
	}
	/**
	 * 查询企业历史记录
	 * @return "success"
	 */
	public String findHistoryEnterprise() {
//		if(enterpriseId!=null&&enterpriseId==0){
//			enterpriseId=null;
//		}
//		if(createrId!=null&&createrId==0){
//			createrId=null;
//		}
		if (isFirstSearch()) {
			// 初次查询时，要从数据库中读取总记录数
			Integer count = checkService.findHistoryEnterprise(startDate, endDate,enterpriseId,createrId,testKind);
			setTotalCount(count);
		}
		// 得到分页查询结果
		List<Check> checks = checkService.findHistoryEnterprisePerPage(startDate, endDate,this.getStart(), this.getLimit(),enterpriseId,createrId,testKind);
		this.putResultList(checks);
		return SUCCESS;
	}	

	
	/**
	 * 查询车主历史记录
	 * @return "success"
	 */
	public String findHistoryCarowner() {
//		if(carownerId!=null&&carownerId==0){
//			carownerId=null;
//		}
//		if(createrId!=null&&createrId==0){
//			createrId=null;
//		}
		if (isFirstSearch()) {
			// 初次查询时，要从数据库中读取总记录数
			Integer count = checkService.findHistoryCarowner(startDate, endDate,carownerId,createrId,testKind);
			setTotalCount(count);
		}
		// 得到分页查询结果
		List<Check> checks = checkService.findHistoryCarownerPerPage(startDate, endDate,this.getStart(), this.getLimit(),carownerId,createrId,testKind);
		this.putResultList(checks);
		return SUCCESS;
	}
	/**
	 * 查询合格证明细/历史记录  还没用到
	 * @return "success"
	 */
	public String findHistoryStandard() {
//		if(enterpriseId!=null&&enterpriseId==0){
//			enterpriseId=null;
//		}
//		if(createrId!=null&&createrId==0){
//			createrId=null;
//		}
		if (isFirstSearch()) {
			// 初次查询时，要从数据库中读取总记录数
			Integer count = checkService.findHistoryStandard(startDate, endDate,heGe,createrId,testKind);
			setTotalCount(count);
		}
		// 得到分页查询结果
		List<Check> checks = checkService.findHistoryStandardPerPage(startDate, endDate,this.getStart(), this.getLimit(),heGe,createrId,testKind);
		this.putResultList(checks);
		return SUCCESS;
	}	
	
	/**
	 * 按事主统计领用次数、总计、剩余 
	 * */
	public String findStandardSingle() {
		if (isFirstSearch()) {
			Integer count = standardService.findStandardSingle(getQueryBean());
			setTotalCount(count);
		}
		List<Standard> standards = standardService.findStandardSinglePerPage(getQueryBean(), this.getStart(), this.getLimit());
		this.putResultList(standards);
		return SUCCESS;
	}	
	// ~~~~~~~~~~~~~~~~~~~~~~~ Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public Check getCheck() {
		return check;
	}

	public void setCheck(Check check) {
		this.check = check;
	}
	public int[] getCheckIds() {
		return checkIds;
	}

	public void setCheckIds(int[] checkIds) {
		this.checkIds = checkIds;
	}

	public void setStartDate(String startDate) throws ParseException {
		this.startDate = DateUtils.parseDate(startDate + " 00:00:00",
				new String[] { "yyyy-MM-dd hh:mm:ss" });
	}

	public void setEndDate(String endDate) throws ParseException {
		this.endDate = DateUtils.parseDate(endDate + " 23:59:59",
				new String[] { "yyyy-MM-dd hh:mm:ss" });
	}
	public void setStartDate(Date startDate) throws ParseException {
		this.startDate =DateUtils.parseDate(CalendarUtil.df.format(startDate) + " 00:00:00",
				new String[] { "yyyy-MM-dd hh:mm:ss" });
	}
	public void setEndDate(Date endDate)throws ParseException {
		this.endDate = DateUtils.parseDate(CalendarUtil.df.format(endDate) + " 23:59:59",
				new String[] { "yyyy-MM-dd hh:mm:ss" });
	}
	public static void main(String[] args) throws ParseException {
		System.out.println(DateUtils.parseDate("2011-01-30" + " 23:59:59",
				new String[] { "yyyy-MM-dd hh:mm:ss" }));
	}
	
//	/**
//	 * Convenience method to get the request
//	 * 
//	 * @return current request
//	 */
//	protected HttpServletRequest getRequest() {
//		return ServletActionContext.getRequest();
//	}
//
//	/**
//	 * Convenience method to get the response
//	 * 
//	 * @return current response
//	 */
//	protected HttpServletResponse getResponse() {
//		return ServletActionContext.getResponse();
//	}
}
