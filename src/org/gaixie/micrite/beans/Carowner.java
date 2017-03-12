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
 * 车主信息
 * 日志追踪6字段
 */
@Entity
@Table(name = "carowner")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Carowner extends AbstractSecureObject implements Serializable {
	/**本类字段们在Dictionary表里的type值*/
	public static final int COLUMNS_TYPE = 17;
	
	@Id
	@GeneratedValue
	private Integer id;
	/** 车牌所属业户名称 */
	private String name;
	public static final int NAME_NO = 1;
	
	/**业户联系电话*/
	private String telephone;  
	public static final int TELEPHONE_NO = 2;
	/**业户地址*/
	private String address; 
	public static final int ADDRESS_NO = 3;
	/**经营许可证号*/
	private String license;
	public static final int LICENSE_NO = 4;
	/**车辆档案户主手机、短信提醒用-要求增加*/
	private String mobile;
	public static final int MOBILE_NO = 16;	
//------------------------------ 	

	/** 记录产生时间 -设计需要新增*/
	private Date createDate; 
	/** 记录谁产生人 -设计需要新增*/
	private int createrId;

	/**临时笔记本-功能需要新增*/
	private String remark;
	public static final int REMARK_NO = 5;
	/** 车辆所属业户拼音 -功能需要增加*/
	private String py;

	/**记录状态，0正常 1删除-设计需要新增*/
	private int status;
	public static final int STATUS_NO = 6;
	
	private Date editDate;
	private int editorId;
	
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
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPy() {
		return py;
	}
	public void setPy(String py) {
		this.py = py;
	}
	@JSON(format = "yyyy-MM-dd")
	public Date getCreateDate() {
		return createDate;
	}
	public Integer getId() {
		return id;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	
}
//