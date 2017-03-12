package org.gaixie.micrite.beans;

import com.googlecode.jsonplugin.annotations.JSON;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 巡河情况汇总表。
 */
@Entity
@Table(name = "Village",catalog="rivmsdb")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Village extends AbstractSecureObject implements Serializable {

	private static final long serialVersionUID = -298233022222268017L;

	@Id
	@GeneratedValue
	private Integer id;

	     /**村名称*/
		private String vname;

		/**所属镇*/
	 	private Integer tid;

		/** 创建时间*/
		private Date createdate;
		/** 创建人ID*/
		private int createrId;

		/** 修改时间*/
		private Date editdate;
		/** 修改人ID*/
		private int editorId;
		/**记录状态，0正常 1删除*/
		private int State;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getVname() {
		return vname;
	}

	public void setVname(String vname) {
		this.vname = vname;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public int getCreaterId() {
		return createrId;
	}

	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getEditdate() {
		return editdate;
	}

	public void setEditdate(Date editdate) {
		this.editdate = editdate;
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

	/**
	 * No-arg constructor for JavaBean tools
	 */
	public Village() {
	}
// ~~~~~~~~~ Accessor Methods~~~~~~~~~~~~~~~~~~~~


    /**
     * Full constructor
     */
    public Village(String vname, Integer tid, Date createdate,
                   Integer createrId, Integer editorId, Date editdate) {


    	this.vname=vname;
    	this.tid=tid;
    	this.createdate = createdate;
    	this.editdate=editdate;
    	this.createrId=createrId;
    	this.editorId = editorId;
    }
//    public String toString() {
//        return  "PatrolRiverSummary ('" + getId() + "'), " +
//                "User: '" + getEditorId() + "'" +
//                "CreateDate: '" + getCreateDate() + "'";
//    }
}
