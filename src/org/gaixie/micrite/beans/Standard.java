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
//import netscape.javascript.JSObject ;
//import com.ms.com.Dispatch;

import com.googlecode.jsonplugin.annotations.JSON;

@Entity
@Table(name = "standard")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Standard extends AbstractSecureObject implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(targetEntity = Enterprise.class)
    @JoinColumn(name = "enterprise",insertable=false,nullable=false,updatable=false)
    /**合格证 领走企业名称，不合适外键？复选框选择type=0时下拉框默认显示"车管所"不可改,车管所此项为null
     * 选择企业时，type=1，下拉框显示*/ 
    private Enterprise enterprise;
    /**合格证企业外键引用*/
    @Column(name = "enterprise")
    private Integer enterpriseId;
    /**合格证状态，0表示普通，1表示已删除*/
    private int state;
    /**合格证 编号起始*/    
    private int numberStart;  
    /**合格证 编号结束*/     
    private int numberEnd;    
    /**合格证 领取的总数*/     
    private int sum;     
    /**合格证 剩余 数量*/     
    private int remain;    
    /**合格证 用去的 数量*/     
    private int pay;   

	/**
     * type合格证 领取单位标识 。复选框，默认为企业，也可以理解为   进/出
     * 0为每次检测站(车管所)领走，一般每次输入后，接下来都是1，即是输入维修企业的记录
     * 直到它需要再次领取，那么是用完了再领还是可能会中途领？
     * 软件设计为只能到 remain为0(即全部用完后)才能进行输入，否则提示还没用完 不能输入；
     * 0表示入库的批次,1表示为维修企业领走,2表示可用的区间
     */
    private int type;
    
    /**记录产生时间，和领走时间该区分*/    
    private Date createDate;  
//    /**合格证 领走时间*/     
//    private Date takeDate; 
    private int createrId;
    @ManyToOne
    @JoinColumn(name="preSet",nullable=true)
    private Standard preSet;
    @ManyToOne
    @JoinColumn(name="nextSet",nullable=true)
    private Standard nextSet;
    
    @ManyToOne
    @JoinColumn(name="father")
    private Standard father;
    /**引用的是哪个入库批次,type==1或者==2的不允许为空；type==0的允许为空*/
    @JSON(serialize=false)
	public Standard getFather() {
		return father;
	}
	public void setFather(Standard father) {
		this.father = father;
	}
	@JSON(serialize=false)
	public Standard getPreSet() {
		return preSet;
	}
	public void setPreSet(Standard preSet) {
		this.preSet = preSet;
	}
	@JSON(serialize=false)
	public Standard getNextSet() {
		return nextSet;
	}
	public void setNextSet(Standard nextSet) {
		this.nextSet = nextSet;
	}
	public int getCreaterId() {
		return createrId;
	}
	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}

    /**
     * No-arg constructor for JavaBean tools
     */
	public Standard() {}
	/**one constructor*/
	public Standard(Enterprise enterprise) {	
		this.enterprise = enterprise ;
	}
	/**one constructor*/
	public Standard(Enterprise enterprise,int numberStart) {
		this.enterprise = enterprise ;
		this.numberStart = numberStart ;
	}
 // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~// 
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	public Integer getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public Enterprise getEnterprise() {
		return enterprise;
	}
	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@JSON(format="yyyy-MM-dd")
    public Date getCreateDate() {
		return createDate;
	}

	public int getNumberEnd() {
		return numberEnd;
	}

	public int getNumberStart() {
		return numberStart;
	}
	/**合格证 用去的 数量*/
	public int getPay() {
		return pay;
	}

	public int getRemain() {
		return remain;
	}

	public int getSum() {
		return sum;
	}
//	@JSON(format="yyyy-MM-dd")
//	public Date getTakeDate() {
//		return takeDate;
//	}

	public int getType() {
		return type;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setNumberEnd(int numberEnd) {
		this.numberEnd = numberEnd;
	}

	public void setNumberStart(int numberStart) {
		this.numberStart = numberStart;
	}

	public void setPay(int pay) {
		this.pay = pay;
	}

	public void setRemain(int remain) {
		this.remain = remain;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

//	public void setTakeDate(Date takeDate) {
//		this.takeDate = takeDate;
//	}

	public void setType(int type) {
		this.type = type;
	}

}
