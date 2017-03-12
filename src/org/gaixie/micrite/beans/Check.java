package org.gaixie.micrite.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * Micrite应用的一个检测记录管理。
 */
@Entity
@Table(name = "checknote")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Check extends AbstractSecureObject implements Serializable {

	@Id
	@GeneratedValue
	private Integer id;
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name = "createrId",insertable=false,nullable=false,updatable=false)
//	private User creater;
//	/**
//	 *  获取记录的操作员
//	 * */
//	public User getCreater() {
//		return creater;
//	}
	private int createrId;
	/**方便创建者用户的设置*/
	public int getCreaterId() {
		return createrId;
	}

	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}

//	public void setCreater(User creater) {
//		this.creater = creater;
//	}

	/**技术等级*/
	private int cheDengji;

	/** 车辆外键 0表示没有匹配到车子 **/
//	@ManyToOne(targetEntity = Carfile.class)
//	@JoinColumn(name = "carId")
	private int carId;
	/** 检测类型 */
	private int testKind;
	/** 判断是否是检测站post */
	private int isPost;

	/** 检测日期 第3行jian_time=2003/02/28 */
	private Date jianTime;
	private Date endTime;

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

	/** 车牌颜色 第94行 pai_she 外键7(id 64-68) */

	/** 车辆颜色 */
	@ManyToOne(targetEntity = Dictionary.class)
	@JoinColumn(name = "paiSe")
	private Dictionary paiSe;

	/** 检测站数据中 0为未用，1为用过 ;手工添加情况下 0为无引用、x为引用的记录id*/
	private int status;

	/** 是否被打印过 */
	private int printed;

	/** 记录产生时间 */
	private Date createDate;

	/** 注解 */
	private String notepad;

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

	public String getNotepad() {
		return notepad;
	}

	public void setNotepad(String notepad) {
		this.notepad = notepad;
	}

	/**
	 * No-arg constructor for JavaBean tools
	 */
	public Check() {
	}

	

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Accessor Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getJianTime() {
		return jianTime;
	}

	public void setJianTime(Date jianTime) {
		this.jianTime = jianTime;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	@JSON(format="yyyy-MM-dd")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Dictionary getPaiSe() {
		return paiSe;
	}

	public void setPaiSe(Dictionary paiSe) {
		this.paiSe = paiSe;
	}

	public int getTestKind() {
		return testKind;
	}

	public void setTestKind(int testKind) {
		this.testKind = testKind;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public int getIsPost() {
		return isPost;
	}

	public void setIsPost(int isPost) {
		this.isPost = isPost;
	}

	public int getCheDengji() {
		return cheDengji;
	}

	public void setCheDengji(int cheDengji) {
		this.cheDengji = cheDengji;
	}
}
