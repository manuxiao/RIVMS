package org.gaixie.micrite.check.action;

import java.text.ParseException;
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
import org.gaixie.micrite.standard.service.IStandardService;
import org.gaixie.micrite.util.CalendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;

/**
 * CheckAction用来响应用户对Check基本信息维护时的操作，并调用相关的Service。
 * <p>
 * 通过调用相关的Service类，完成对Check基本信息的增加，删除，查询。(修改待定)
 */
public class CheckAction extends GenericAction {
	private static final long serialVersionUID = 3072131320220662398L;

	@Autowired
	private ICheckService checkService;
	@Autowired
	private IStandardService standardService;
	@Autowired
	private ICarfileService carfileService;
	@Autowired
	private IDictionaryService dictionaryService;
	// 获取的页面参数
	private Check check;

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	private String licenseNumber;
	// ------------------------
	private int checkId;

	public int getCheckId() {
		return checkId;
	}

	public void setCheckId(int checkId) {
		this.checkId = checkId;
	}

	private int[] checkIds;
//	private Date startDate;
//	private Date endDate;
	// ~~~~~~页面check的参数~~~~~~~~~~//
	private int id;
	private int carId;

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/** 状态 是否被设置成作废 */
	private int status;
	/** 是否被打印过 */
	private int printed;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPrinted() {
		return printed;
	}

	public void setPrinted(int printed) {
		this.printed = printed;
	}

	/** 检测日期 第3行jian_time=2003/02/28 */
	private Date jianTime;
	// 检测类型
	private Integer testKind;

	public void setTestKind(Integer testKind) {
		this.testKind = testKind;
	}

	public Date getJianTime() {
		return jianTime;
	}

	public void setJianTime(Date jianTime) {
		this.jianTime = jianTime;
	}

	public Integer getTestNo() {
		return testNo;
	}

	public void setTestNo(Integer testNo) {
		this.testNo = testNo;
	}

	public String getCheZhu() {
		return cheZhu;
	}

	public void setCheZhu(String cheZhu) {
		this.cheZhu = cheZhu;
	}

	public String getCheXiu() {
		return cheXiu;
	}

	public void setCheXiu(String cheXiu) {
		this.cheXiu = cheXiu;
	}

	public String getPaiHao() {
		return paiHao;
	}

	public void setPaiHao(String paiHao) {
		this.paiHao = paiHao;
	}

	public String getCheLei() {
		return cheLei;
	}

	public void setCheLei(String cheLei) {
		this.cheLei = cheLei;
	}

	public Integer getHeGe() {
		return heGe;
	}

	public void setHeGe(Integer heGe) {
		this.heGe = heGe;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getNotepad() {
		return notepad;
	}

	public void setNotepad(String notepad) {
		this.notepad = notepad;
	}

	public Dictionary getPaiSe() {
		return paiSe;
	}

	public void setPaiSe(Dictionary paiSe) {
		this.paiSe = paiSe;
	}

	/** 检测编号 第4行 test_no=-26141 可为空 */
	private Integer testNo;

	/** 车主 第7行 che_zhu=新城建 筑公司 可为空 */
	private String cheZhu;

	/** 维修企业 第8行che_xiu=众鑫 -即合格证 所属企业 */
	private String cheXiu;

	/** 车辆牌号 第9行 pai_hao=BB4344 */
	private String paiHao;

	/** 车辆类型 第11行 che_lei=大货 可为空 */
	private String cheLei;

	/** 合格证 编号 第23行 he_ge=283224 */
	private Integer heGe;

	/** 车牌颜色 第94行 pai_she */
	private Dictionary paiSe; // 车牌颜色
	/** 记录产生时间 */
	private Date createDate;
	/** 技术等级 */
	private int cheDengji;

	/** 记事本 */
	private String notepad;

	public int getCheDengji() {
		return cheDengji;
	}

	public void setCheDengji(int cheDengji) {
		this.cheDengji = cheDengji;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~ Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//
	/**
	 * 打印检测记录
	 * 
	 * @return "success"
	 */
	public String print() {
		// checkService.print(checkId);
		/** 打印处理函数 */

		this.getResultMap().put("message", getText("print.preview"));
		this.getResultMap().put("success", true);
		return SUCCESS;
	}

	/**
	 * 根据合格证获取维修单位
	 * */
	public String findEnterpriseByHege() {
		if (check.getHeGe() == null) {//
			this.getResultMap().put(
					"message",
					getText("add.check.testNo.notexist", new String[] { ""
							+ check.getHeGe() }));
			this.getResultMap().put("success", false);
			return INPUT;
		}
		Standard s = standardService.findByStandardNo(check.getHeGe());
		if (s == null) {
			this.getResultMap().put(
					"message",
					getText("add.check.testNo.notexist", new String[] { ""
							+ check.getHeGe() }));
			this.getResultMap().put("success", false);
			return INPUT;
		} else {
			this.getResultMap().put("message", s.getEnterprise().getUnitName());
			this.getResultMap().put("success", true);
			return SUCCESS;
		}
	}

	/**
	 * 单纯增加一个记录
	 * 
	 * @return "success"
	 */
	public String saveCheck() {
		check.setCreateDate(new Date());
		check.setCarId(carId);
		check.setPaiHao(licenseNumber);
		check.setPaiSe(paiSe);
		checkService.add(check);
		this.getResultMap().put("message", getText("save.success"));
		this.getResultMap().put("success", true);
		return SUCCESS;
	}

	/** 合格证找不到的时候需要提示是否强制保存 1表示强制保存 */
	public static final int FORCE_SAVE_CHECK = 1;
	private int forceSubmit;

	public void setForceSubmit(int forceSubmit) {
		this.forceSubmit = forceSubmit;
	}

	/**
	 * 未超期、超期但行政免除处罚
	 * */
	final static int NEEDPUNISH_NOT = 0;
	final static int NEEDPUNISH_YES = 100;
	final static int NEEDPUNISH_ERROR_NO_CHECK_DATE = 11;
	final static int NEEDPUNISH_ERROR_NO_THIS_CAR = 12;
	final static int NEEDPUNISH_ERROR_NO_MAINTAIN_ENDDATE = 13;
	final static int NEEDPUNISH_ERROR_NO_PERMISSION_DAYS = 14;

	private int needPunish(int carId, Date checkDate) {
		if (checkDate == null) {
			return NEEDPUNISH_ERROR_NO_CHECK_DATE;
		}
		Carfile car = carfileService.get(carId);
		if (car == null) {
			return NEEDPUNISH_ERROR_NO_THIS_CAR;
		}
		Date maintainEndDate = car.getMaintainDateEnd();
		if (maintainEndDate == null) {
			return NEEDPUNISH_ERROR_NO_MAINTAIN_ENDDATE;
		}
		Dictionary dic = dictionaryService
				.get(IDictionaryService.EXPIRED_PERMISSION_ID);
		if (dic == null) {
			return NEEDPUNISH_ERROR_NO_PERMISSION_DAYS;
		}

		boolean isNotNeedPunish = checkDate.before(CalendarUtil.afterDay(
				maintainEndDate, dic.getValue()));// 不含最后一天
		if (isNotNeedPunish)
			return NEEDPUNISH_NOT;
		if (car.getApproval() == ICarfileService.APPROVAL_NOT)
			return NEEDPUNISH_YES;
		return NEEDPUNISH_NOT;
	}

	private boolean _jishu_erwei_date_invalid(Date jianTime) {
		Date now = new Date();
		if (jianTime.after(now))
			return true;
		return false;
	}
	final private boolean _carUneditable_(Integer carId){
    	if(carId==null){
            this.getResultMap().put("message", getText("car.id.null"));
            this.getResultMap().put("success", false);
            return true;
    	}
    	Carfile car = carfileService.get(carId);
    	if(car==null){
            this.getResultMap().put("message", getText("car.not.found.by.id",new String[]{""+carId}));
            this.getResultMap().put("success", false);
            return true;
    	}
    	if(car.getCarStatus()!=ICarfileService.CARSTATUS_NORMAL){
            this.getResultMap().put("message", getText("car.status.not.normal",new String[]{car.getLicenseNumber()}));
            this.getResultMap().put("success", false);
    		return true;
    	}
    	return false;
    }
	/**
	 * 从检测站导入数据 生成 新增检测确认记录 现在两个保存都用它！！！
	 * 
	 * @return "success"
	 */
	public String addCheck() {
		
		if(_carUneditable_(carId))return SUCCESS;
		
		int i = needPunish(carId, jianTime);
		if (i != NEEDPUNISH_NOT) {// 处罚状态下不能操作
			this.getResultMap().put("message",
					getText("carfile.punishment" + i));
			this.getResultMap().put("success", false);
			this.getResultMap().put("type", "100");
			return INPUT;
		}
		if (_jishu_erwei_date_invalid(jianTime)) {// 二维日期不能晚于当天日期
			this.getResultMap().put("message",
					getText("jishu.erwei.date.future"));
			this.getResultMap().put("success", false);
			this.getResultMap().put("type", "100");
			return INPUT;
		}
		if (heGe == null) {// 
			if (FORCE_SAVE_CHECK != forceSubmit) {
				this.getResultMap().put(
						"message",
						getText("add.check.testNo.notexist", new String[] { ""
								+ heGe }));
				this.getResultMap().put("success", false);
				this.getResultMap().put("type", "0");
				return INPUT;
			}
		} else {
			boolean isUsed = checkService.isUsed(heGe);
			if (isUsed) {// 合格证已注销
				if (FORCE_SAVE_CHECK != forceSubmit) {
					this.getResultMap().put(
							"message",
							getText("add.check.testNo.occupied",
									new String[] { "" + heGe }));
					this.getResultMap().put("type", "1");
					this.getResultMap().put("success", false);
					return INPUT;
				}
			}
			boolean isExist = standardService.isExist(heGe);
			if (!isExist) {// 合格证不存在
				if (FORCE_SAVE_CHECK != forceSubmit) {
					this.getResultMap().put(
							"message",
							getText("add.check.testNo.notexist",
									new String[] { "" + heGe }));
					this.getResultMap().put("type", "2");
					this.getResultMap().put("success", false);
					return INPUT;
				}
			}
		}
		Check check = new Check();
		check.setJianTime(jianTime);
		check.setCarId(carId);
		check.setTestKind(testKind);
		check.setHeGe(heGe);
		check.setPaiHao(licenseNumber);
		check.setPaiSe(paiSe);
		check.setCheXiu(cheXiu);
		check.setIsPost(checkService.ISPOST_NOT);
		check.setCheDengji(cheDengji);
		Date endTime = CalendarUtil.afterMonth(check.getJianTime(),
				carfileService.get(carId).getMaintainCycle().getValue());

		check.setEndTime(endTime);
		check.setCreateDate(new Date());
		check.setCreaterId(((User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal()).getId());
		// 对于不存在合格证的保存和已注销合格证的保存
		if (forceSubmit == FORCE_SAVE_CHECK) {
			checkService.addWithTestKindNotMinus(check);
		} else {
			checkService.addWithTestKind(check);
		}
		this.getResultMap().put("message", getText("save.success"));
		this.getResultMap().put("success", true);
		return SUCCESS;

	}
	/**
	 * 高级查询检测记录信息
	 * 
	 * @return "success"
	 */
	public String findRecentPostCheck() {
		Carfile car = carfileService.get(carId);
		if(car==null){
			this.getResultMap().put("message", getText("carfile.not.found"));
			this.getResultMap().put("success", false);
			return SUCCESS;
		}
		// 得到分页查询结果
		List<Check> checks = checkService.findRecentPostCheckOf(car,
				this.getStart(), this.getLimit());
		this.putResultList(checks);
		return SUCCESS;
	}
	/**
	 * 高级查询检测记录信息
	 * 
	 * @return "success"
	 */
	public String advancedFind() {
		if (isFirstSearch()) {
			// 初次查询时，要从数据库中读取总记录数
			Integer count = checkService.advancedFindCount(getQueryBean());
			setTotalCount(count);
		}
		// 得到分页查询结果
		List<Check> checks = checkService.advancedFindByPerPage(getQueryBean(),
				this.getStart(), this.getLimit());
		this.putResultList(checks);
		return SUCCESS;
	}

	/**
	 * 日期间隔及customerSourceType普通查询检测记录
	 * 
	 * @return
	 
	public String findByDateSpacing() {
		if (isFirstSearch()) {
			// 初次查询时，要从数据库中读取总记录数
			Integer count = checkService.findByCreateDateSpacingCount(
					startDate, endDate);
			setTotalCount(count);
		}
		// 得到分页查询结果
		List<Check> checks = checkService.findByCreateDateSpacingPerPage(
				startDate, endDate, this.getStart(), this.getLimit());
		this.putResultList(checks);
		return SUCCESS;
	}
*/
	/**
	 * 删除检测记录
	 * 
	 * @return "success"
	 */
	public String delete() {
		checkService.delete(checkIds);
		this.getResultMap().put("message", getText("delete.success"));
		this.getResultMap().put("success", true);
		return SUCCESS;

	}

	// ~~~~~~~~~~~~~~~~~~~~~~~ Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//
	/**
	 * @return the customer
	 */
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
/*
	public void setStartDate(String startDate) throws ParseException {
		this.startDate = DateUtils.parseDate(startDate + ":00",
				new String[] { "yyyy-MM-dd hh:mm:ss" });
	}

	public void setEndDate(String endDate) throws ParseException {
		this.endDate = DateUtils.parseDate(endDate + ":00",
				new String[] { "yyyy-MM-dd hh:mm:ss" });
	}
*/
	/**
	 * Convenience method to get the request
	 * 
	 * @return current request
	 */
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * Convenience method to get the response
	 * 
	 * @return current response
	 */
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
}
