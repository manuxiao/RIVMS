package org.gaixie.micrite.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * 车辆技术档案
 * 日志追踪18字段
 */
@Entity
@Table(name = "carfile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Carfile extends AbstractSecureObject implements Serializable {
	/**本类字段们在Dictionary表里的type值*/
	public static final int COLUMNS_TYPE = 15;	
	@Id
	@GeneratedValue
	private Integer id;
	/** 车主 */
	@ManyToOne(targetEntity = Carowner.class)
	@JoinColumn(name = "owner")
	private Carowner carowner;
	/** 车牌所属业户 */
//	public static final int OWNER_NO = 1;
	/**业户联系电话*/
//	public static final int TELEPHONE_NO = 2;
	/**业户地址*/
//	public static final int ADDRESS_NO = 3;
	/**业户手机号码*/
//	public static final int MOBILE_NO = 16;
	/**车牌号码*/
	private String licenseNumber; 
	public static final int LICENSENUMBER_NO = 4;
	/** 车辆类型 */
	@ManyToOne(targetEntity = Dictionary.class)
	@JoinColumn(name = "carType")
	private Dictionary carType;
	public static final int CARTYPE_NO = 5;
	/** 车辆类型 */
	public static final int CARTYPE_TYPE = 1;
	/** 车牌型号*/
	@ManyToOne(targetEntity = Dictionary.class)
	@JoinColumn(name = "licenseType")
	private Dictionary licenseType; 
	public static final int LICENSETYPE_NO = 6;
	public static final int LICENSETYPE_TYPE = 3;
	/** 核载吨位*/
	private String loadTon; 
	public static final int LOADTON_NO = 7;
	/** 品牌型号*/
	private String brandType; 
	public static final int BRANDTYPE_NO = 8;
	/** 车辆备注*/
	private String carRemark; 
	public static final int CARREMARK_NO = 9;
	/**  燃油型号*/
	@ManyToOne(targetEntity = Dictionary.class)
	@JoinColumn(name = "fuelRank")
	private Dictionary fuelRank; 
	public static final int FUELRANK_NO = 10;
	public static final int FUELRANK_TYPE = 4;
	/** 技术等级*/
	@ManyToOne(targetEntity = Dictionary.class)
	@JoinColumn(name = "skillRank")
	private Dictionary skillRank; 
	public static final int SKILLRANK_NO = 11;
	public static final int SKILLRANK_TYPE = 2;
	/** 技术等级评定有效期止*/
	private Date evaluateDate;
	public static final int EVALUATEDATE_NO = 18;
	/** 技术评定周期 */
	@ManyToOne(targetEntity = Dictionary.class)
	@JoinColumn(name = "evaluateCycle")
	private Dictionary evaluateCycle; 
	public static final int EVALUATECYCLE_NO = 12;
	public static final int EVALUATECYCLE_TYPE = 5;
	/** 二维操作日期*/
	private Date maintainDate; 
	public static final int MAINTAINDATE_NO = 16;
	/** 二维周期 */
	@ManyToOne(targetEntity = Dictionary.class)
	@JoinColumn(name = "maintainCycle")
	private Dictionary maintainCycle; 
	public static final int MAINTAINCYCLE_NO = 13;
	public static final int MAINTAINCYCLE_TYPE = 6;
	/** 营运证号 -要求增加*/
	private String yingyunNo; 
	public static final int YINGYUNNO_NO = 14;
	
	/** 车牌颜色 -需求中遗漏字段*/
	@ManyToOne(targetEntity = Dictionary.class)
	@JoinColumn(name = "paiSe")
	private Dictionary paiSe;
	public static final int PAISE_NO = 15;
	public static final int PAISE_TYPE = 7;

//------------------------------ 	
	/** 二维日期截止-设计需要新增*/
	private Date maintainDateEnd; 

	/** 记录产生时间 -设计需要新增*/
	private Date createDate; 
	/** 记录谁产生人 -设计需要新增*/
	private int createrId;

	/**记录状态，0正常 1删除-设计需要新增*/
	private int status;
	/**临时笔记本-功能需要新增*/
	private String notepad;
	/**0正常1二维超期2严重超期需被处罚*/
//	@Transient
	private int expired;
	/**营运状态 0营运1报废2转籍*//**过户不在此列，报废、转籍操作也不是通过这个位的设置，是单独的标示、单独流程；这个17应该没用到*/
	private int carStatus;
	public static final int CARSTATUS_NO = 17;
	public static final int CARSTATUS_TYPE = 14;
	/**审批免除超期处罚0未有审批批准 1表示审批批准免除，2交完罚款解除处罚状态*/
	private int approval;
//	public static final int APPROVAL_NO = 16;//跟过户、转籍、报废等类似，见ICheckService中定义
//	public static final int APPROVAL_NO = 100;
	/**技术等级打印指针-设计需要增加*/
	private int printIndex1;
	/**二次维护打印指针-设计需要增加*/
	private int printIndex2;
	private Date editDate;
	private int editorId;
	/**离超期还有几天*/
	private Integer daysToExpired;
	
	@JSON(format="yyyy-MM-dd")
	public Date getEditDate() {
		return editDate;
	}
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}
	public int getEditorId() {
		return editorId;
	}
	public void setEditorId(int editorId) {
		this.editorId = editorId;
	}
	public int getCreaterId() {
		return createrId;
	}
	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}

	public String getMobile() {
		return this.carowner.getMobile();
	}
	public void setMobile(String mobile) {
		if(carowner==null)carowner=new Carowner();
		carowner.setMobile(mobile);
	}
	
	public int getApproval() {
		return approval;
	}
	public void setApproval(int approval) {
		this.approval = approval;
	}
	public int getExpired() {
		return expired;
	}
	public void setExpired(int expired) {
		this.expired = expired;
	}
	public int getCarStatus() {
		return carStatus;
	}
	public void setCarStatus(int carStatus) {
		this.carStatus = carStatus;
	}
	public String getNotepad() {
		return notepad;
	}
	public void setNotepad(String notepad) {
		this.notepad = notepad;
	}

	/**技术等级评定打印指针**/
	public int getPrintIndex1() {
		return printIndex1;
	}
	public void setPrintIndex1(int printIndex1) {
		this.printIndex1 = printIndex1;
	}
	/**二次维护打印指针**/
	public int getPrintIndex2() {
		return printIndex2;
	}
	public void setPrintIndex2(int printIndex2) {
		this.printIndex2 = printIndex2;
	}
	
	public String getCarRemark() {
		return carRemark;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getCreateDate() {
		return createDate;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getEvaluateDate() {
		return evaluateDate;
	}

	public void setEvaluateDate(Date evaluateDate) {
		this.evaluateDate = evaluateDate;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Accessor Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public Integer getId() {
		return id;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public String getLoadTon() {
		return loadTon;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getMaintainDate() {
		return maintainDate;
	}

	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}
	public String getTelephone() {
		return this.carowner.getTelephone();
	}

	public void setAddress(String address) {
		if(this.carowner==null)this.carowner=new Carowner();
		this.carowner.setAddress(address);
	}

	public void setBrandType(String brandType) {
		this.brandType = brandType;
	}

	public void setCarRemark(String carRemark) {
		this.carRemark = carRemark;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public void setLoadTon(String loadTon) {
		this.loadTon = loadTon;
	}


	public void setTelephone(String telephone) {
		if(carowner==null)carowner=new Carowner();
		carowner.setTelephone(telephone);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getYingyunNo() {
		return yingyunNo;
	}

	public void setYingyunNo(String yingyunNo) {
		this.yingyunNo = yingyunNo;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getMaintainDateEnd() {
		return maintainDateEnd;
	}
	public void setMaintainDateEnd(Date maintainDateEnd) {
		this.maintainDateEnd = maintainDateEnd;
	}
	public Dictionary getSkillRank() {
		return skillRank;
	}
	public void setSkillRank(Dictionary skillRank) {
		this.skillRank = skillRank;
	}
	public Dictionary getLicenseType() {
		return licenseType;
	}
	public void setLicenseType(Dictionary licenseType) {
		this.licenseType = licenseType;
	}
	public Dictionary getFuelRank() {
		return fuelRank;
	}
	public void setFuelRank(Dictionary fuelRank) {
		this.fuelRank = fuelRank;
	}
	public Dictionary getEvaluateCycle() {
		return evaluateCycle;
	}
	public void setEvaluateCycle(Dictionary evaluateCycle) {
		this.evaluateCycle = evaluateCycle;
	}
	public Dictionary getMaintainCycle() {
		return maintainCycle;
	}
	public void setMaintainCycle(Dictionary maintainCycle) {
		this.maintainCycle = maintainCycle;
	}
	public Dictionary getPaiSe() {
		return paiSe;
	}
	public void setPaiSe(Dictionary paiSe) {
		this.paiSe = paiSe;
	}
	public Dictionary getCarType() {
		return carType;
	}
	public void setCarType(Dictionary carType) {
		this.carType = carType;
	}
	public String getAddress() {
		return carowner.getAddress();
	}
	public String getBrandType() {
		return brandType;
	}

	public Carowner getCarowner() {
		return carowner;
	}
	public void setCarowner(Carowner carowner) {
		this.carowner = carowner;
	}
	public String getOwner() {
		return carowner.getName();
	}
	public void setOwner(String owner) {
		if(carowner==null)carowner=new Carowner();
		carowner.setName(owner);
	}
	public String getOwnerLicense() {
		return carowner.getLicense();
	}
	public void setOwnerLicense(String ownerLicense) {
		if(carowner==null)carowner=new Carowner();
		carowner.setLicense(ownerLicense);
	}
	public Integer getDaysToExpired() {
		return daysToExpired;
	}
	public void setDaysToExpired(Integer daysToExpired) {
		this.daysToExpired = daysToExpired;
	}
	
}
//