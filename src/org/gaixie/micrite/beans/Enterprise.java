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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * 本应用一个维修企业;
 * 允许修改的有19个字段，可以做到这19个字段的修改保存
 * 对外键的，保存外键的name字段值；
 */
@Entity
@Table(name = "enterprises")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Enterprise extends AbstractSecureObject implements Serializable {
	/**本类字段们在Dictionary表里的type值*/
	public static final int COLUMNS_TYPE = 16;
	@Id
	@GeneratedValue
	private Integer id;
	/** 单位名称 */
	private String unitName;
	public static final int UNITNAME_NO = 1;
	/** 法人代表 */
	private String legalPerson;
	public static final int LEGALPERSON_NO = 2;
	/** 法人手机 */
	private String telephone1;
	public static final int TELEPHONE1_NO = 3;
	/** 许可证号 */
	@Column(unique=true)
	private String license;
	public static final int LICENSE_NO = 4;
	/** 经营资质 */
	@ManyToOne(targetEntity = Dictionary.class)
	@JoinColumn(name = "qualification")
	private Dictionary qualification;
	public static final int QUALIFICATION_TYPE = 8;
	public static final int QUALIFICATION_NO=5;
	/** 经办人 */
	private String handleMan;
	public static final int HANDLEMAN_NO=6;
	/** （经办人）联系电话 */
	private String telephone2;
	public static final int TELEPHONE2_NO=7;
	/** 负责人手机 */
	private String telephone3;
	public static final int TELEPHONE3_NO=8;
	/** 委托人 */
	private String commission;
	public static final int COMMISSION_NO=9;
	/** 委托人电话 */
	private String telephone4;
	public static final int TELEPHONE4_NO=10;
	/** 经济性质 */
	@ManyToOne(targetEntity = Dictionary.class)
	@JoinColumn(name = "kind")
	private Dictionary kind;
	public static final int KIND_TYPE = 9;
	public static final int KIND_NO=11;
	/** 许可证发放日期 */
	private Date licenseDate;
	public static final int LICENSEDATE_NO=12;
	/** 有效日期开始 */
	private Date dateBegin;
	public static final int DATEBEGIN_NO=13;
	/** 有效日期结束 */
	private Date dateEnd;
	public static final int DATEEND_NO=14;
	/** 注册地址 */
	private String address;
	public static final int ADDRESS_NO=15;
	/** 修改日期 */
	private Date editDate;

	/** 经营范围 */
	private String workArea;
	public static final int WORKAREA_NO=16;
	/** 经营范围备注 */
	private String workRemark;
	public static final int WORKREMARK_NO=17;
	/** 经营状态 */
	@ManyToOne(targetEntity = Dictionary.class)
	@JoinColumn(name = "workType")
	private Dictionary workType;
	public static final int WORKTYPE_TYPE = 10;
	public static final int WORKTYPE_NO=18;
	/** 所属管辖站-要求增加 */
	@ManyToOne(targetEntity = Dictionary.class)
	@JoinColumn(name = "station")
	private Dictionary station;
	public static final int STATION_TYPE = 11;
	public static final int STATION_NO=19;
	/** 记录产生时间-设计需要增加 */
	private Date createDate;
	/**记录产生人-设计需要增加*/
	private int createrId;
	/**最后修改人-设计需要增加*/
	private int editorId;
	
	/** 企业名称拼音-设计需要增加 */
	private String py;
	/**记录状态0正常1删除-设计需要增加*/
	private int status;
	/**
	 * No-arg constructor for JavaBean tools
	 */
	public Enterprise() {
	}

	public int getCreaterId() {
		return createrId;
	}

	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}

	public int getEditorId() {
		return editorId;
	}

	public void setEditorId(int editorId) {
		this.editorId = editorId;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getAddress() {
		return address;
	}

	public String getCommission() {
		return commission;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getCreateDate() {
		return createDate;
	}

	public String getHandleMan() {
		return handleMan;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Accessor Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public Integer getId() {
		return id;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public String getLicense() {
		return license;
	}

	public String getTelephone1() {
		return telephone1;
	}

	public String getTelephone2() {
		return telephone2;
	}

	public String getTelephone3() {
		return telephone3;
	}

	public String getTelephone4() {
		return telephone4;
	}

	public String getUnitName() {
		return unitName;
	}

	public String getWorkArea() {
		return workArea;
	}

	public String getWorkRemark() {
		return workRemark;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setHandleMan(String handleMan) {
		this.handleMan = handleMan;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}

	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}

	public void setTelephone3(String telephone3) {
		this.telephone3 = telephone3;
	}

	public void setTelephone4(String telephone4) {
		this.telephone4 = telephone4;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public void setWorkArea(String workArea) {
		this.workArea = workArea;
	}

	public void setWorkRemark(String workRemark) {
		this.workRemark = workRemark;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getLicenseDate() {
		return licenseDate;
	}

	public void setLicenseDate(Date licenseDate) {
		this.licenseDate = licenseDate;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public Dictionary getStation() {
		return station;
	}

	public void setStation(Dictionary station) {
		this.station = station;
	}

	public Dictionary getQualification() {
		return qualification;
	}

	public void setQualification(Dictionary qualification) {
		this.qualification = qualification;
	}

	public Dictionary getKind() {
		return kind;
	}

	public void setKind(Dictionary kind) {
		this.kind = kind;
	}

	public Dictionary getWorkType() {
		return workType;
	}

	public void setWorkType(Dictionary workType) {
		this.workType = workType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}