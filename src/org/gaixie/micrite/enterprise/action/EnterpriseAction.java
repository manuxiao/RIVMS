package org.gaixie.micrite.enterprise.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.gaixie.micrite.action.GenericAction;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.Enterprise;
import org.gaixie.micrite.beans.IdName;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.dic.service.IDictionaryService;
import org.gaixie.micrite.download.ExcelUtils;
import org.gaixie.micrite.enterprise.service.IEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;

import com.hansheng.njj.PinyinConv;

/**
 * EnterpriseAction用来响应用户对Enterprise基本信息维护时的操作，并调用相关的Service。
 * <p>
 * 通过调用相关的Service类，完成对Enterprise基本信息的增加，删除，修改，查询。
 */
public class EnterpriseAction extends GenericAction {
	private static final long serialVersionUID = 3072131320220662398L;

	@Autowired
	private IEnterpriseService enterpriseService;
	@Autowired
	private IDictionaryService dictionaryService;


	// 获取的页面参数
	private Enterprise enterprise;
	private int[] enterpriseIds;
	private Date startDate;
	private Date endDate;

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private Integer id;

//	// ~~~~~~~~~~~~~~~~~~~~~~~取得修改页面的参数~~~~~~~~~~~~~~~~~~~~~~~//
//	public Date getEndDate() {
//		return endDate;
//	}
//
//	public String getLicense() {
//		return license;
//	}
//
//	public void setLicense(String license) {
//		this.license = license;
//	}
//
//	public Date getLicenseDate() {
//		return licenseDate;
//	}
//
//	public void setLicenseDate(Date licenseDate) {
//		this.licenseDate = licenseDate;
//	}
//
//	public Dictionary getStation() {
//		return station;
//	}
//
//	public void setStation(Dictionary station) {
//		this.station = station;
//	}
//
//	public String getUnitName() {
//		return unitName;
//	}
//
//	public void setUnitName(String unitName) {
//		this.unitName = unitName;
//	}
//
//	public String getLegalPerson() {
//		return legalPerson;
//	}
//
//	public void setLegalPerson(String legalPerson) {
//		this.legalPerson = legalPerson;
//	}
//
//	public String getTelephone1() {
//		return telephone1;
//	}
//
//	public void setTelephone1(String telephone1) {
//		this.telephone1 = telephone1;
//	}
//
//
//	public String getHandleMan() {
//		return handleMan;
//	}
//
//	public void setHandleMan(String handleMan) {
//		this.handleMan = handleMan;
//	}
//
//	public String getTelephone2() {
//		return telephone2;
//	}
//
//	public void setTelephone2(String telephone2) {
//		this.telephone2 = telephone2;
//	}
//
//	public String getTelephone3() {
//		return telephone3;
//	}
//
//	public void setTelephone3(String telephone3) {
//		this.telephone3 = telephone3;
//	}
//
//	public String getCommission() {
//		return commission;
//	}
//
//	public void setCommission(String commission) {
//		this.commission = commission;
//	}
//
//	public String getTelephone4() {
//		return telephone4;
//	}
//
//	public void setTelephone4(String telephone4) {
//		this.telephone4 = telephone4;
//	}
//
//	public String getAddress() {
//		return address;
//	}
//
//	public void setAddress(String address) {
//		this.address = address;
//	}
//
//	public String getWorkArea() {
//		return workArea;
//	}
//
//	public void setWorkArea(String workArea) {
//		this.workArea = workArea;
//	}
//
//	public String getWorkRemark() {
//		return workRemark;
//	}
//
//	public void setWorkRemark(String workRemark) {
//		this.workRemark = workRemark;
//	}
//
//	public Date getStartDate() {
//		return startDate;
//	}
//
//
//	public Dictionary getQualification() {
//		return qualification;
//	}
//
//	public void setQualification(Dictionary qualification) {
//		this.qualification = qualification;
//	}
//
//	public Dictionary getKind() {
//		return kind;
//	}
//
//	public void setKind(Dictionary kind) {
//		this.kind = kind;
//	}
//
//	public Dictionary getWorkType() {
//		return workType;
//	}
//
//	public void setWorkType(Dictionary workType) {
//		this.workType = workType;
//	}
//
//
//	public Date getDateBegin() {
//		return dateBegin;
//	}
//
//	public void setDateBegin(Date dateBegin) {
//		this.dateBegin = dateBegin;
//	}
//
//	public Date getDateEnd() {
//		return dateEnd;
//	}
//
//	public void setDateEnd(Date dateEnd) {
//		this.dateEnd = dateEnd;
//	}

//	private String unitName; // 单位名称
//	/** 维修企业范围 */
//	private String legalPerson; // 法人代表
//	private String telephone1; // 法人手机
//	private String license; // 许可证号
//	private String handleMan; // 经办人
//	private String telephone2; // 联系电话
//	private String telephone3; // 负责人手机
//	private String commission; // 委托人
//	private String telephone4; // 委托人手机
//	private Date licenseDate; // 许可证发放日期
//	private Date dateBegin;// 有效日期开始
//	private Date dateEnd;// 有效日期结束
//	private String address; // 注册地址
//	private String workArea; // 经营范围
//	private String workRemark; // 经营范围备注
//	private Dictionary qualification; // 经营资质
//	private Dictionary kind; // 经济性质
//	private Dictionary workType; // 经营状态
//	private Dictionary station; // 所属管辖站

	// ~~~~~~~~~~~~~~~~~~~~~~~ Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//
	
	
	/**输入的时候输入完毕牌色和牌号即来查询是否存在*/
    public String checkEnterpriseExists(){
    	
    	if(enterpriseService.existEnterprise(enterprise)){
    		this.getResultMap().put("message", getText("check.enterprise.exists.yes"));
            this.getResultMap().put("success", false);//这是查获
    	}else{
    		this.getResultMap().put("message", getText("check.enterprise.exists.no"));
            this.getResultMap().put("success", true);//不存在
    	}
    	return SUCCESS;
    }
	/**
	 * 保存企业信息		
	 * 
	 * @return "success"
	 */
	public String add() {
		if(enterprise.getId()!=null){//认为是更新
    		return update();
    	}
		User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		enterprise.setCreaterId(currentUser.getId());
		enterprise.setCreateDate(new Date());
		enterprise.setEditorId(enterprise.getCreaterId());
		enterprise.setEditDate(enterprise.getCreateDate());
		enterprise.setPy(PinyinConv.cn2py(enterprise.getUnitName()));
		enterprise.setStatus(IEnterpriseService.STATUS_NORMAL);
		
		enterpriseService.add(enterprise);
		this.getResultMap().put("message", getText("save.success"));
		this.getResultMap().put("success", true);
		return SUCCESS;
	}

	/**
	 * 高级查询企业信息
	 * 
	 * @return "success"
	 */
	public String advancedFind() {
		if (isFirstSearch()) {
			// 初次查询时，要从数据库中读取总记录数
			Integer count = enterpriseService.advancedFindCount(getQueryBean());
			setTotalCount(count);
		}
		List<Enterprise> enterprises = enterpriseService.advancedFindByPerPage(
				getQueryBean(), this.getStart(), this.getLimit());
		this.putResultList(enterprises);
		if(DOWNLOADALL.equals(getRequest().getParameter("download"))){
        	return DOWNLOADALL;
        }
		return SUCCESS;
	}
	
	/**
	 * 高级查询企业信息
	 * 
	 * @return "success"
	 */
//	public String findEnterpriseALL() {
//		List<Enterprise> enterprises = enterpriseService.advancedFindByPerPage(
//				getQueryBean(), this.getStart(), this.getLimit());		
//		Enterprise tempSource = new Enterprise();
//        tempSource.setId(-1);
//        tempSource.setUnitName("全部");
//        enterprises.add(0,tempSource);      
//		this.putResultList(enterprises);
//		return SUCCESS;
//	}
	public String findEnterpriseByPy(){
		String py = getRequest().getParameter("py");
		List<IdName> enterprises = enterpriseService.findEnterpriseByPy(py);
		this.putResultList(enterprises);
		return SUCCESS;
	}
	/**
	 * 删除企业
	 * 
	 * @return "success"
	 */
	public String delete() {
		enterpriseService.delete(enterpriseIds);
		this.getResultMap().put("message", getText("delete.success"));
		this.getResultMap().put("success", true);
		return SUCCESS;
	}
	/**
	 * 日期间隔及EnterpriseSourceType普通查询企业
	 * 
	 * @return
	 */
	public String findByDateSpacing() {
		if (isFirstSearch()) {
			// 初次查询时，要从数据库中读取总记录数
			Integer count = enterpriseService.findByCreateDateSpacingCount(
					startDate, endDate, enterprise.getQualification()
							.getId());
			setTotalCount(count);
		}
		// 得到分页查询结果
		List<Enterprise> enterprises = enterpriseService
				.findByCreateDateSpacingPerPage(startDate, endDate, this
						.getStart(), this.getLimit(), enterprise
						.getQualification().getId());
		this.putResultList(enterprises);
		return SUCCESS;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~ Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//
	/**
	 * @return the enterprise
	 */
	public Enterprise getEnterprise() {
		return enterprise;
	}

	public int[] getEnterpriseIds() {
		return enterpriseIds;
	}

//	public void setEndDate(String endDate) throws ParseException {
//		this.endDate = DateUtils.parseDate(endDate + ":00",
//				new String[] { "yyyy-MM-dd hh:mm:ss" });
//	}

	/**
	 * @param enterprise
	 *            the enterprise to set
	 */
	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public void setEnterpriseIds(int[] enterpriseIds) {
		this.enterpriseIds = enterpriseIds;
	}

//	public void setStartDate(String startDate) throws ParseException {
//		this.startDate = DateUtils.parseDate(startDate + ":00",
//				new String[] { "yyyy-MM-dd hh:mm:ss" });
//	}

	/**
	 * 获取修改的企业信息 需要自动生成审核信息
	 * 
	 * @return "success"
	 */
	public String update() {
		
		if(enterpriseService.existEnterprise(enterprise)){
    		this.getResultMap().put("message", getText("check.enterprise.exists.yes"));
	        this.getResultMap().put("success", false);
	        return SUCCESS;
    	}
		enterpriseService.update(enterprise);
		this.getResultMap().put("message", getText("save.success"));
		this.getResultMap().put("success", true);
		return SUCCESS;

	}
	public String getEntity(){
    	enterprise=enterpriseService.get(id);
    	return SUCCESS;
    }
	
	final static String DOWNLOADALL="downloadall";
	private final static int EXPIREDCARS_COLNUM = 20;
	final private String fileName="enterprises";
	
	public String getFileName() {
		return fileName;
	}
	public InputStream getDownloadStream() throws Exception, IOException {
		String dateFormat=getText("zhouqi.date.format","yyyy-MM-dd");
		DateFormat df = new SimpleDateFormat(dateFormat);
    	
		HSSFWorkbook wb = null;
		HSSFRow row = null;
		HSSFCell cell = null;

		wb = new HSSFWorkbook();
		HSSFSheet sheet = null;
		// 新建sheet并设置sheet名字
//		sheet = wb.createSheet(getText("expired.cars", "未检车辆(截止" + new Date() + ")"));
		sheet = wb.createSheet();
		// 设置表头
		ExcelUtils.insertRow(sheet, 0, EXPIREDCARS_COLNUM);
		row = sheet.getRow(0);
		cell = row.getCell(0);
		cell.setCellValue("序号");
		cell = row.getCell(1);
		cell.setCellValue("企业名称");
		cell = row.getCell(2);
		cell.setCellValue("许可证号");
		cell = row.getCell(3);
		cell.setCellValue("经营资质");
		cell = row.getCell(4);
		cell.setCellValue("法人代表");
		cell = row.getCell(5);
		cell.setCellValue("法人手机");
		cell = row.getCell(6);
		cell.setCellValue("经营状态");
		cell = row.getCell(7);
		cell.setCellValue("所属辖站");
		cell = row.getCell(8);
		cell.setCellValue("经办人");
		cell = row.getCell(9);
		cell.setCellValue("联系电话");
		cell = row.getCell(10);
		cell.setCellValue("委托人");
		cell = row.getCell(11);
		cell.setCellValue("负责人手机");
		cell = row.getCell(12);
		cell.setCellValue("委托人手机");
		cell = row.getCell(13);
		cell.setCellValue("经济性质");
		cell = row.getCell(14);
		cell.setCellValue("许可证发放日期");
		cell = row.getCell(15);
		cell.setCellValue("有效日期开始");
		cell = row.getCell(16);
		cell.setCellValue("有效日期结束");
		cell = row.getCell(17);
		cell.setCellValue("注册地址");
		cell = row.getCell(18);
		cell.setCellValue("经营范围");
		cell = row.getCell(19);
//		cell.setCellValue("经营范围备注");
//		int permissionDays = dictionaryDao.get(EXPIRED_PERMISSION_ID).getValue();
		List<Enterprise> list = (List<Enterprise>)getResultMap().get("data");
		Enterprise obj = null;
		for (int j = 0; j < list.size(); ++j) {
			ExcelUtils.insertRow(sheet, j + 1, EXPIREDCARS_COLNUM);
			obj = list.get(j);
			row = sheet.getRow(j+1);
			cell = row.getCell(0);//序号
			cell.setCellValue(j+1);
			cell = row.getCell(1);//企业名称
			cell.setCellValue(obj.getUnitName());
			cell = row.getCell(2);//经营许可证号
			cell.setCellValue(obj.getLicense());
//			if(obj.getMobile()!=null&&obj.getMobile().trim().length()=="13811223344".length()){
//				cell.setCellValue(obj.getMobile());	
//			}else if(obj.getTelephone()!=null&&obj.getTelephone().trim().length()=="13811223344".length()){
//				cell.setCellValue(obj.getTelephone());
//			}
			cell = row.getCell(3);//经营资质
			cell.setCellValue(obj.getQualification().getName());
			cell = row.getCell(4);//
			cell.setCellValue(obj.getLegalPerson());
			cell = row.getCell(5);//
			cell.setCellValue(obj.getTelephone1());
			cell = row.getCell(6);//
			cell.setCellValue(obj.getWorkType().getName());
			cell = row.getCell(7);//
			cell.setCellValue(obj.getStation().getName());
			cell = row.getCell(8);//
			cell.setCellValue(obj.getHandleMan());
			cell = row.getCell(9);//
			cell.setCellValue(obj.getTelephone2());
			cell = row.getCell(10);//
			cell.setCellValue(obj.getCommission());
			cell = row.getCell(11);//
			cell.setCellValue(obj.getTelephone3());
			cell = row.getCell(12);//
			cell.setCellValue(obj.getTelephone4());
			cell = row.getCell(13);//
			cell.setCellValue(obj.getKind().getName());
			cell = row.getCell(14);//
	    	try {
	    		cell.setCellValue(df.format(obj.getLicenseDate()));
			} catch (Exception e) {}
			cell = row.getCell(15);//
			try {
	    		cell.setCellValue(df.format(obj.getDateBegin()));
			} catch (Exception e) {}
			cell = row.getCell(16);//
			try {
	    		cell.setCellValue(df.format(obj.getDateEnd()));
			} catch (Exception e) {}
			cell = row.getCell(17);//
			cell.setCellValue(obj.getAddress());
			cell = row.getCell(18);//
			cell.setCellValue(obj.getWorkArea());
			cell = row.getCell(19);//
			cell.setCellValue(obj.getWorkRemark());
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		return is;
	}
}
