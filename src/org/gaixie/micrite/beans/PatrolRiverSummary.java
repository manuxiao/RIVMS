package org.gaixie.micrite.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Columns;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * 巡河情况汇总表。
 */
@Entity
@Table(name = "patrolRiverSummary",catalog="rivmsdb")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PatrolRiverSummary extends AbstractSecureObject implements Serializable {

	private static final long serialVersionUID = -2982330126232868017L;

	@Id
	@GeneratedValue
	private Integer id;
//	private int userId;
//	/**方便创建者用户的设置*/
//	public int getUserId() {
//		return userId;
//	}
//	public void setUserId(int userId) {
//		this.userId = userId;
//	} 

//		/** 填报人 */
//		@ManyToOne(targetEntity = User.class)
//		@JoinColumn(name = "user")
//		private User user;
		 
		/**镇级河长巡河人数*/
		private Integer townRiverChief;
		
		/**村（社区）河长巡河人数*/
		private Integer villageRiverChief;
		
		/**参加巡河河长单位数*/
		private Integer RiverChiefUnit;
		
		/**河长单位参加巡河人数*/
		private Integer RiverChief;	
	 		
		/**河长公示牌无损，后端会显示为无*/
		private Integer publicSignDamage;	
	
		/**河面有漂浮垃 圾*/
		private Integer FloatGarbage;
		
		/**岸坡有垃圾杂物堆放*/
		private Integer  ZaWuDuiFang;
		 
		/**沿河有乱搭建*/
		private Integer  LuanDaJian;
			 
		/**沿河有简易旱厕*/
		private Integer  JianYiHanCe;
		
		/**入河排污（水）口标志未全*/
		private Integer PaiWuBZWeiQuan;	
		
		/**沿河有工业污水直排*/
		private Integer	 GongYeZhiPai;
		
		/**沿河有农业污水直排*/
		private Integer	 NongYeZhiPai;
		
		/**清理垃圾（处，吨）*/
		private Integer	 QingLiLaJi;
	 
		/**清洁公示牌（块）*/
		private Integer	 QingJieGongShiPai;
		
		/**清理乱堆放（处）*/
		private Integer	 QingLiLuanDuiFang;
		 
		/**上门宣传（户）*/
		private Integer	 ShangMenXC;
			 
		/**发放宣传资料（份）*/
		private Integer	 FaFangZiLiao;
 
		/**审批备注*/
		private String approveDemo;		
		/**审批状态0未审批， 1表示审核批准，2表示审批不通过*/
		private int approveStatus;		
		/** 记录产生时间*/
		private Date approveDate; 
		/** 记录谁产生人ID*/
		private int approveId;
		
		/** 记录产生时间*/
		private Date createDate; 
		/** 记录产生人ID*/
		private int createrId;
 
		/** 记录修改时间*/
		private Date editDate;
		/** 记录修改人ID*/
		private int editorId;
		/**记录状态，0正常 1删除*/
		private int State;
		/**	其他活动开展情况，活动1*/
		private String	 Activity1;
		private String	 Activity2;
		private String	 Activity3;
		private String	 Activity4;
		private String	 Activity5;
		/**技术等级打印指针-设计需要增加*/
		private int printIndex1;

	/**
	 * No-arg constructor for JavaBean tools
	 */
	public PatrolRiverSummary() {
	}
// ~~~~~~~~~ Accessor Methods~~~~~~~~~~~~~~~~~~~~
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
  // @Column(name="townRiverChief", nullable=true, length=45)
	public Integer getTownRiverChief() {
		return townRiverChief;
	}

	public void setTownRiverChief(Integer townRiverChief) {
		this.townRiverChief = townRiverChief;
	}

	public Integer getVillageRiverChief() {
		return villageRiverChief;
	}

	public void setVillageRiverChief(Integer villageRiverChief) {
		this.villageRiverChief = villageRiverChief;
	}

	public Integer getRiverChiefUnit() {
		return RiverChiefUnit;
	}

	public void setRiverChiefUnit(Integer riverChiefUnit) {
		RiverChiefUnit = riverChiefUnit;
	}

	public Integer getRiverChief() {
		return RiverChief;
	}

	public void setRiverChief(Integer riverChief) {
		RiverChief = riverChief;
	}

	public Integer getPublicSignDamage() {
		return publicSignDamage;
	}

	public void setPublicSignDamage(Integer publicSignDamage) {
		this.publicSignDamage = publicSignDamage;
	}

	public Integer getFloatGarbage() {
		return FloatGarbage;
	}

	public void setFloatGarbage(Integer floatGarbage) {
		FloatGarbage = floatGarbage;
	}

	public Integer getZaWuDuiFang() {
		return ZaWuDuiFang;
	}

	public void setZaWuDuiFang(Integer zaWuDuiFang) {
		ZaWuDuiFang = zaWuDuiFang;
	}

	public Integer getLuanDaJian() {
		return LuanDaJian;
	}

	public void setLuanDaJian(Integer luanDaJian) {
		LuanDaJian = luanDaJian;
	}

	public Integer getJianYiHanCe() {
		return JianYiHanCe;
	}

	public void setJianYiHanCe(Integer jianYiHanCe) {
		JianYiHanCe = jianYiHanCe;
	}

	public Integer getPaiWuBZWeiQuan() {
		return PaiWuBZWeiQuan;
	}

	public void setPaiWuBZWeiQuan(Integer paiWuBZWeiQuan) {
		PaiWuBZWeiQuan = paiWuBZWeiQuan;
	}

	public Integer getGongYeZhiPai() {
		return GongYeZhiPai;
	}

	public void setGongYeZhiPai(Integer gongYeZhiPai) {
		GongYeZhiPai = gongYeZhiPai;
	}

	public Integer getNongYeZhiPai() {
		return NongYeZhiPai;
	}

	public void setNongYeZhiPai(Integer nongYeZhiPai) {
		NongYeZhiPai = nongYeZhiPai;
	}

	public Integer getQingLiLaJi() {
		return QingLiLaJi;
	}

	public void setQingLiLaJi(Integer qingLiLaJi) {
		QingLiLaJi = qingLiLaJi;
	}

	public Integer getQingJieGongShiPai() {
		return QingJieGongShiPai;
	}

	public void setQingJieGongShiPai(Integer qingJieGongShiPai) {
		QingJieGongShiPai = qingJieGongShiPai;
	}

	public Integer getQingLiLuanDuiFang() {
		return QingLiLuanDuiFang;
	}

	public void setQingLiLuanDuiFang(Integer qingLiLuanDuiFang) {
		QingLiLuanDuiFang = qingLiLuanDuiFang;
	}

	public Integer getShangMenXC() {
		return ShangMenXC;
	}

	public void setShangMenXC(Integer shangMenXC) {
		ShangMenXC = shangMenXC;
	}

	public Integer getFaFangZiLiao() {
		return FaFangZiLiao;
	}

	public void setFaFangZiLiao(Integer faFangZiLiao) {
		FaFangZiLiao = faFangZiLiao;
	}

	public int getApproveStatus() {
		return approveStatus;
	}

	public void setApproveDemo(String approveDemo) {
		this.approveDemo = approveDemo;
	}

	public String getApproveDemo() {
		return approveDemo;
	}
	
	public void setApproveStatus(int approveStatus) {
		this.approveStatus = approveStatus;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public int getApproveId() {
		return approveId;
	}

	public void setApproveId(int approveId) {
		this.approveId = approveId;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getCreaterId() {
		return createrId;
	}

	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}
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

	public int getState() {
		return State;
	}

	public void setState(int state) {
		State = state;
	}

	public String getActivity1() {
		return Activity1;
	}

	public void setActivity1(String activity1) {
		Activity1 = activity1;
	}

	public String getActivity2() {
		return Activity2;
	}

	public void setActivity2(String activity2) {
		Activity2 = activity2;
	}

	public String getActivity3() {
		return Activity3;
	}

	public void setActivity3(String activity3) {
		Activity3 = activity3;
	}

	public String getActivity4() {
		return Activity4;
	}

	public void setActivity4(String activity4) {
		Activity4 = activity4;
	}

	public String getActivity5() {
		return Activity5;
	}

	public void setActivity5(String activity5) {
		Activity5 = activity5;
	}

	public int getPrintIndex1() {
		return printIndex1;
	}

	public void setPrintIndex1(int printIndex1) {
		this.printIndex1 = printIndex1;
	}

    /**
     * Full constructor
     */
    public PatrolRiverSummary(Integer townRiverChief,Integer villageRiverChief,Integer RiverChiefUnit,
    		Integer RiverChief,Integer publicSignDamage,Integer FloatGarbage,Integer ZaWuDuiFang,
    		Integer LuanDaJian,Integer JianYiHanCe,Integer PaiWuBZWeiQuan,Integer GongYeZhiPai,Integer NongYeZhiPai,
    	Integer QingLiLaJi,Integer QingJieGongShiPai,Integer QingLiLuanDuiFang,Integer ShangMenXC,
    	Integer FaFangZiLiao,String approveDemo,Integer approveStatus,
    	Date approveDate,Date editDate, Date createDate,
    	Integer approveId,Integer createrId,Integer editorId,String Activity1) {
    	
    	this.townRiverChief = townRiverChief;
    	this.villageRiverChief=villageRiverChief;
    	this.RiverChiefUnit=RiverChiefUnit;   	
    	this.RiverChief = RiverChief;
    	this.publicSignDamage=publicSignDamage;
    	this.FloatGarbage=FloatGarbage;	   	
    	this.ZaWuDuiFang = ZaWuDuiFang;
    	this.LuanDaJian=LuanDaJian;
    	this.JianYiHanCe=JianYiHanCe;	
    	this.PaiWuBZWeiQuan = PaiWuBZWeiQuan;
    	this.GongYeZhiPai=GongYeZhiPai;
    	this.NongYeZhiPai=NongYeZhiPai;   	  	
    	this.QingLiLaJi = QingLiLaJi;
    	this.QingJieGongShiPai=QingJieGongShiPai;
    	this.QingLiLuanDuiFang=QingLiLuanDuiFang;  	
    	this.ShangMenXC = ShangMenXC;
    	this.FaFangZiLiao=FaFangZiLiao;
    	this.approveDemo=approveDemo;	   	
    	this.approveStatus = approveStatus;
    	this.approveDate=approveDate;
    	this.editDate=editDate;	
    	this.createDate = createDate;
    	this.approveId=approveId;
    	this.createrId=createrId;   	
    	this.editorId = editorId;
    	this.Activity1=Activity1;
    	this.createrId=createrId; 
    }  
    public String toString() {
        return  "PatrolRiverSummary ('" + getId() + "'), " +
                "User: '" + getEditorId() + "'" +
                "CreateDate: '" + getCreateDate() + "'";
    }
}
