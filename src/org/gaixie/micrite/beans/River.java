package org.gaixie.micrite.beans;

import com.googlecode.jsonplugin.annotations.JSON;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 巡河情况汇总表。
 */
@Entity
@Table(name = "River",catalog="rivmsdb")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class River extends AbstractSecureObject implements Serializable {

	private static final long serialVersionUID = -2982330111111168017L;

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
		@Column(unique=true)
		/**河道编码*/
		private String rid;

		/**河道名称*/
		private String rname;

		/**河道名称拼音*/
		private String py;
	
		/**河道长度*/
		private Double rlen;

		/**起点*/
		private String start;

		/**终点*/
		private String end;

		/**是否马路沿线*/
		private Boolean isOutload;

		/**河道状态(PH,富营养化)*/
		private Integer Status;

		/**是否美丽河道*/ 
		private Boolean  isBeautiful;

		/** 创建时间*/
		private Date begindate;
		/** 创建人ID*/
		private int createrId;

		/** 修改时间*/
		private Date modifydate;
		/** 修改人ID*/
		private int editorId;
		/**记录状态，0正常 1删除*/
		private int State;

		/**公示牌编号*/
		private String board;
		/**公示牌位置*/
		private String boardlocation;

		/**村ID*/
		@ManyToOne(targetEntity = Village.class)
		@JoinColumn(name = "vid")
		private Village village;
		//private Integer vid;

//	public Integer getVid() {
//		return vid;
//	}
//
//	public void setVid(Integer vid) {
//		this.vid = vid;
//	}
 

	public Village getVillage() {
			return village;
		}

		public void setVillage(Village village) {
			this.village = village;
		}

	public Boolean getOutload() {
		return isOutload;
	}

	public void setOutload(Boolean outload) {
		isOutload = outload;
	}

	public Boolean getBeautiful() {
		return isBeautiful;
	}

	public void setBeautiful(Boolean beautiful) {
		isBeautiful = beautiful;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public String getBoardlocation() {
		return boardlocation;
	}

	public void setBoardlocation(String boardlocation) {
		this.boardlocation = boardlocation;
	}
	
	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public Double getRlen() {
		return rlen;
	}

	public void setRlen(Double rlen) {
		this.rlen = rlen;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}

	@JSON(format="yyyy-MM-dd")
	public Date getBegindate() {
		return begindate;
	}

	public void setBegindate(Date begindate) {
		this.begindate = begindate;
	}

	public int getCreaterId() {
		return createrId;
	}

	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getModifydate() {
		return modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
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
	public River() {
	}
// ~~~~~~~~~ Accessor Methods~~~~~~~~~~~~~~~~~~~~


    /**
     * Full constructor
     */
    public River(String rid, String rname,String py, Double rlen,
                 String start, String end, Boolean isOutload, Integer Status,
				 Boolean isBeautiful, Date begindate,
                Integer createrId, Integer editorId, Date modifydate,String board, String boardlocation,Integer vid) {

    	this.rid = rid;
    	this.rname=rname;
    	this.rlen=rlen;
    	this.start = start;
    	this.end=end;
    	this.isOutload =isOutload;
		this.Status=Status;
		this.isBeautiful =isBeautiful;
    	this.begindate = begindate;
    	this.modifydate=modifydate;
    	this.createrId=createrId;
    	this.editorId = editorId;
		this.board=board;
		this.boardlocation = boardlocation;
		this.py = py;
		this.village.setId(vid);
    }
//    public String toString() {
//        return  "PatrolRiverSummary ('" + getId() + "'), " +
//                "User: '" + getEditorId() + "'" +
//                "CreateDate: '" + getCreateDate() + "'";
//    }
}
