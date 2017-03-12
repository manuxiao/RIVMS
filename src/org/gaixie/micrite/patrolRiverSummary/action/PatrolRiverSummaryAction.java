package org.gaixie.micrite.patrolRiverSummary.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.time.DateUtils;
import org.apache.derby.tools.sysinfo;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.components.ActionError;
import org.apache.struts2.components.ActionMessage;
import org.apache.struts2.components.FieldError;
import org.gaixie.micrite.action.GenericAction;
import org.gaixie.micrite.beans.PatrolRiverSummary;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.patrolRiverSummary.dao.IPatrolRiverSummaryDAO;
import org.gaixie.micrite.patrolRiverSummary.service.IPatrolRiverSummaryService;
 
import org.gaixie.micrite.download.ExcelUtils;
 
import org.gaixie.micrite.util.CalendarUtil;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;

import sun.util.calendar.CalendarUtils;

import com.googlecode.jsonplugin.annotations.JSON;
import com.hansheng.njj.PinyinConv;

/**
 * PatrolRiverSummaryAction用来响应用户对PatrolRiverSummary基本信息维护时的操作，并调用相关的Service。
 * <p>
 * 通过调用相关的Service类，完成对PatrolRiverSummary基本信息的增加，删除，修改，查询。
 */
public class PatrolRiverSummaryAction extends GenericAction{
	Logger log = Logger.getLogger(PatrolRiverSummaryAction.class);
	private static final long serialVersionUID = 3072131320220662111L;

	@Autowired
	private IPatrolRiverSummaryService patrolRiverSummaryService;

    //获取的页面参数
    private PatrolRiverSummary patrolRiverSummary;
    private int[] PatrolRiverSummaryIds;

    private Date startDate;
    private Date endDate;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~获得修改页面的参数~~~~~~~~~~~~~~~~~~~~~//
    private Integer id; 
    private Integer carownerId; 
    private String notepad;
    
    public String getNotepad() {
		return notepad;
	}
	public void setNotepad(String notepad) {
		this.notepad = notepad;
	}
    public Integer getCarownerId() {
		return carownerId;
	}
	public void setCarownerId(Integer carownerId) {
		this.carownerId = carownerId;
	}
 
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
 
    final boolean _patrolRiverSummaryNeedPunishment(PatrolRiverSummary patrolRiverSummary){
    	if(patrolRiverSummary.getApproveStatus()==IPatrolRiverSummaryService.APPROVAL_TOBE){
    		return true;
    	}else{
    		return false;
    	}
    }
    final private boolean _carUneditable_(Integer userId){
    	if(userId==null){
            this.getResultMap().put("message", getText("car.id.null"));
            this.getResultMap().put("success", false);
            return true;
    	}
    	PatrolRiverSummary car = patrolRiverSummaryService.get(userId);
    	if(car==null){
            this.getResultMap().put("message", getText("car.not.found.by.id",new String[]{""+userId}));
            this.getResultMap().put("success", false);
            return true;
    	}
    	if(IPatrolRiverSummaryService.APPROVAL_YES==car.getApproveStatus()){
            this.getResultMap().put("message", getText("car.status.not.normal",new String[]{""}));
            this.getResultMap().put("success", false);
    		return true;
    	}
    	return false;
    }
    /**
     * 编辑河道汇总表记录 
     * 获取修改的河道汇总表记录信息
     * 要做审核
     * @return "success"
     */
    public String update(){
    	
    	if(_carUneditable_(patrolRiverSummary.getId()))return SUCCESS;
    	 
    	patrolRiverSummaryService.update(patrolRiverSummary);
        this.getResultMap().put("message", getText("save.success"));
        this.getResultMap().put("success", true);
        return SUCCESS;
    }
     
    /**
     * 新增河道汇总表档案
     * @return "success"
     */		
    public String savePatrolRiverSummary() {
    	if(patrolRiverSummary.getId()!=null){//认为是更新
            this.getResultMap().put("message", getText("save.success"));
            this.getResultMap().put("success", true);
    		return update();    		
    	}
    	//检查河道汇总表是否存在
    	if(patrolRiverSummaryService.existPatrolRiverSummary(patrolRiverSummary.getId())){
    		this.getResultMap().put("message", getText("check.car.exists.yes"));
            this.getResultMap().put("success", false);
            return SUCCESS;
    	}else{
    		
    		//保存河道汇总表信息
	        patrolRiverSummaryService.addFromFront(patrolRiverSummary);
	   	
	        this.getResultMap().put("message", getText("save.success"));
	        this.getResultMap().put("河道汇总表Id", ""+patrolRiverSummary.getId());
	        this.getResultMap().put("success", true);
	        return SUCCESS;
    	}
    }
    /**
     * 高级查询河道汇总表档案记录信息
     * @return "success"
     */  
    public String advancedFind(){
        if (isFirstSearch()) {
            //  初次查询时，要从数据库中读取总记录数
        	log.debug("totle size.1");
            Integer count = patrolRiverSummaryService.advancedFindCount(getQueryBean());
            setTotalCount(count);
        }         
        //  得到分页查询结果
        log.debug("find resultset:"+this.getLimit());
        List<PatrolRiverSummary> PatrolRiverSummarys = patrolRiverSummaryService.advancedFindByPerPage(getQueryBean(), this.getStart(), this.getLimit());
        this.putResultList(PatrolRiverSummarys);
        if(DOWNLOADALL.equals(getRequest().getParameter("download"))){
        	return DOWNLOADALL;
        }
        return SUCCESS;
    }
    /**
     * 删除河道汇总表档案记录
     * @return "success"
     */
    public String delete(){
        patrolRiverSummaryService.delete(PatrolRiverSummaryIds);
        this.getResultMap().put("message", getText("delete.success"));
        this.getResultMap().put("success", true);
        return SUCCESS;
    }
    
    /**
     * 普通查询河道汇总表档案记录 
     * @return
     */
    public String findByDateSpacing(){
        if (isFirstSearch()) {
            //  初次查询时，要从数据库中读取总记录数
        	log.debug("totle size.2");
            Integer count = patrolRiverSummaryService.findByCreateDateSpacingCount(IPatrolRiverSummaryService.STATUS_NORMAL);
            setTotalCount(count);
        }
        log.debug("find resultset.2");
        //  得到分页查询结果
        List<PatrolRiverSummary> PatrolRiverSummarys = patrolRiverSummaryService.findByCreateDateSpacingPerPage(this.getStart(), this.getLimit(),IPatrolRiverSummaryService.STATUS_NORMAL);
        this.putResultList(PatrolRiverSummarys);
        return SUCCESS;
    }
  
    
//    public String findByDateSpacing(){
//        if (isFirstSearch()) {
//            //  初次查询时，要从数据库中读取总记录数
//            Integer count = patrolRiverSummaryService.findByCreateDateSpacingCount(startDate, endDate,patrolRiverSummary.getSkillRank().getId());
//            setTotalCount(count);
//        }         
//        //  得到分页查询结果
//        List<PatrolRiverSummary> PatrolRiverSummarys = patrolRiverSummaryService.findByCreateDateSpacingPerPage(startDate, endDate, this.getStart(), this.getLimit(),patrolRiverSummary.getSkillRank().getId());
//        this.putResultList(PatrolRiverSummarys);
//        return SUCCESS;
//    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    /**
     * @return the PatrolRiverSummary
     */
    public PatrolRiverSummary getPatrolRiverSummary() {
        return patrolRiverSummary;
    }
    public int[] getPatrolRiverSummaryIds() {
        return PatrolRiverSummaryIds;
    }

    /**
     * @param PatrolRiverSummary the PatrolRiverSummary to set
     */
    public void setPatrolRiverSummary(PatrolRiverSummary patrolRiverSummary) {
        this.patrolRiverSummary = patrolRiverSummary;
    }    
    public void setPatrolRiverSummaryIds(int[] PatrolRiverSummaryIds) {
        this.PatrolRiverSummaryIds = PatrolRiverSummaryIds;
    }

    /**拥有该权限的操作员行政审批不通过*/
    public String ridofPunishment(){//需要核对是否拥有该权限

    	PatrolRiverSummary car= patrolRiverSummaryService.get(this.id);
    	
    	if(car.getApproveStatus()!=IPatrolRiverSummaryService.APPROVAL_NOT){
    		this.getResultMap().put("message", getText("reidofExpirePunishment.neednot.approval"));
            this.getResultMap().put("success", false);
        	return SUCCESS;
    	}
    	patrolRiverSummaryService.ridofPunishment(this.id,notepad);
        this.getResultMap().put("message", getText("reidofExpirePunishment.success"));
        this.getResultMap().put("success", true);
    	return SUCCESS;
    }
    /**行政审批通过操作*/
    public String exemptPunishment(){//行政审批免除罚款//是否还能撤销行政决定的免除？谁能撤销谁的？是否还有行政决定给予某种处罚的？

    	PatrolRiverSummary car= patrolRiverSummaryService.get(this.id);
    	
    	if(car.getApproveStatus()!=IPatrolRiverSummaryService.APPROVAL_TOBE){
//    		if(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId().intValue()==car.getApproval()){
//    			this.getResultMap().put("message", getText("has.exempted.by.yourself"));
//    		}else{
//    			User user = userDAO.get(car.getApproval()) ;
//    			String userName = user==null?(""+car.getApproval()):user.getFullname();
//    			this.getResultMap().put("message", getText("has.exempted.by",new String[]{userName}));
//    		}
    		this.getResultMap().put("message", getText("exemptPunishment.has.exempted"));
            this.getResultMap().put("success", false);
        	return SUCCESS;	
    	} 
    	patrolRiverSummaryService.exemptPunishment(this.id,notepad);
        this.getResultMap().put("message", getText("exemptPunishment.success"));
        this.getResultMap().put("success", true);
    	return SUCCESS;
    }
    public String getCar(){
    	if(this.id==null){
            this.getResultMap().put("message", getText("car.id.null"));
            this.getResultMap().put("success", false);
            return SUCCESS;
    	}
    	patrolRiverSummary=patrolRiverSummaryService.get(this.id);
    	if(patrolRiverSummary==null){
    		this.getResultMap().put("message", getText("patrolRiverSummary.not.found",new String[]{""+this.id}));
            this.getResultMap().put("success", false);
            return SUCCESS;
    	}
    	return SUCCESS;
    }

    final static String DOWNLOADALL="downloadall";
	private final static int EXPIREDCARS_COLNUM = 20;
	final private String fileName="cars";
	
	public String getFileName() {
		return fileName;
	}
	/*
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
		cell.setCellValue("车牌所属业户");
		cell = row.getCell(2);
		cell.setCellValue("业户手机");
		cell = row.getCell(3);
		cell.setCellValue("业户地址");
		cell = row.getCell(4);
		cell.setCellValue("车牌号码");
		cell = row.getCell(5);
		cell.setCellValue("车牌类型");
		cell = row.getCell(6);
		cell.setCellValue("车辆类型");
		cell = row.getCell(7);
		cell.setCellValue("车辆颜色");
		cell = row.getCell(8);
		cell.setCellValue("核载吨位或人数");
		cell = row.getCell(9);
		cell.setCellValue("品牌型号");
		cell = row.getCell(10);
		cell.setCellValue("营运证号");
		cell = row.getCell(11);
		cell.setCellValue("车辆备注");
		cell = row.getCell(12);
		cell.setCellValue("燃料类型");
		cell = row.getCell(13);
		cell.setCellValue("技术等级");
		cell = row.getCell(14);
		cell.setCellValue("技术等级评定有效期止");
		cell = row.getCell(15);
		cell.setCellValue("技术等级评定周期");
		cell = row.getCell(16);
		cell.setCellValue("二维操作日期");
		cell = row.getCell(17);
		cell.setCellValue("二维周期");
//		int permissionDays = dictionaryDao.get(EXPIRED_PERMISSION_ID).getValue();
		List<PatrolRiverSummary> list = (List<PatrolRiverSummary>)getResultMap().get("data");
		PatrolRiverSummary car = null;
		for (int j = 0; j < list.size(); ++j) {
			ExcelUtils.insertRow(sheet, j + 1, EXPIREDCARS_COLNUM);
			car = list.get(j);
			row = sheet.getRow(j+1);
			cell = row.getCell(0);//序号
			cell.setCellValue(j+1);
			cell = row.getCell(1);//车牌所属业户
			cell.setCellValue(car.getOwner());
			cell = row.getCell(2);//业户手机号码
			cell.setCellValue(car.getMobile());	
//			if(car.getMobile()!=null&&car.getMobile().trim().length()=="13811223344".length()){
//				cell.setCellValue(car.getMobile());	
//			}else if(car.getTelephone()!=null&&car.getTelephone().trim().length()=="13811223344".length()){
//				cell.setCellValue(car.getTelephone());
//			}
			cell = row.getCell(3);//业户地址
			cell.setCellValue(car.getAddress());
			cell = row.getCell(4);//车牌号码
			cell.setCellValue(car.getLicenseNumber());
			cell = row.getCell(5);//车牌类型
			cell.setCellValue(car.getLicenseType().getName());
			cell = row.getCell(6);//车辆类型
			cell.setCellValue(car.getCarType().getName());
			cell = row.getCell(7);//车辆颜色
			cell.setCellValue(car.getPaiSe().getName());
			cell = row.getCell(8);//核载吨位或人数
			cell.setCellValue(car.getLoadTon());
			cell = row.getCell(9);//品牌型号
			cell.setCellValue(car.getBrandType());
			cell = row.getCell(10);//营运证号
			cell.setCellValue(car.getYingyunNo());
			cell = row.getCell(11);//车辆备注
			cell.setCellValue(car.getCarRemark());
			cell = row.getCell(12);//燃料类型
			cell.setCellValue(car.getFuelRank().getName());
			cell = row.getCell(13);//技术等级
			cell.setCellValue(car.getSkillRank().getName());
			cell = row.getCell(14);//技术等级评定有效期止
	    	try {
	    		cell.setCellValue(df.format(car.getEvaluateDate()));
			} catch (Exception e) {}
			cell = row.getCell(15);//技术等级评定周期
			cell.setCellValue(car.getEvaluateCycle().getName());
			cell = row.getCell(16);//二维操作日期
			try {
	    		cell.setCellValue(df.format(car.getMaintainDate()));
			} catch (Exception e) {}
			cell = row.getCell(17);//二维周期
			cell.setCellValue(car.getMaintainCycle().getName());
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
	}*/
	 
	/**获取打印指针*/
	public String getPrintIndex(){
		
		int type = Integer.parseInt(getRequest().getParameter("type"));
		int carId = Integer.parseInt(getRequest().getParameter("carId"));
		
        PatrolRiverSummary car = patrolRiverSummaryService.get(carId);
      type=car.getPrintIndex1();
        this.getResultMap().put("message", ""+type);
        this.getResultMap().put("success", true);
        return SUCCESS;
        
    }
}
