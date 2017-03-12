package org.gaixie.micrite.car.action;

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
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Carowner;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.Check;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.car.dao.ICarfileDAO;
import org.gaixie.micrite.car.service.ICarfileService;
import org.gaixie.micrite.check.dao.ICheckDAO;
import org.gaixie.micrite.check.service.ICheckService;
import org.gaixie.micrite.crm.dao.ICarownerDAO;
import org.gaixie.micrite.dic.service.IDictionaryService;
import org.gaixie.micrite.download.ExcelUtils;
import org.gaixie.micrite.security.dao.IUserDAO;
import org.gaixie.micrite.security.service.IUserService;
import org.gaixie.micrite.util.CalendarUtil;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;

import sun.util.calendar.CalendarUtils;

import com.googlecode.jsonplugin.annotations.JSON;
import com.hansheng.njj.PinyinConv;

/**
 * CarfileAction用来响应用户对Carfile基本信息维护时的操作，并调用相关的Service。
 * <p>
 * 通过调用相关的Service类，完成对Carfile基本信息的增加，删除，修改，查询。
 */
public class CarfileAction extends GenericAction{
	Logger log = Logger.getLogger(CarfileAction.class);
	private static final long serialVersionUID = 3072131320220662398L;

	@Autowired
	private ICarfileService carfileService;
	@Autowired
	private ICheckService checkService;
	@Autowired
	private IDictionaryService dictionaryService;
	@Autowired
	private ICarownerDAO carownerDAO;

    //获取的页面参数
    private Carfile carfile;
    private int[] CarfileIds;

    private Date startDate;
    private Date endDate;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~获得修改页面的参数~~~~~~~~~~~~~~~~~~~~~//
    private Integer id; 
    private Integer carownerId; 
    
    public Integer getCarownerId() {
		return carownerId;
	}
	public void setCarownerId(Integer carownerId) {
		this.carownerId = carownerId;
	}
	/**只在车辆列表中类型和技术等级组合查询用到*/
    private Integer carTypeId; 
    public Integer getCarTypeId() {
		return carTypeId;
	}
	public void setCarTypeId(Integer carTypeId) {
		this.carTypeId = carTypeId;
	}
	public Integer getSkillRankId() {
		return skillRankId;
	}
	public void setSkillRankId(Integer skillRankId) {
		this.skillRankId = skillRankId;
	}
	private Integer skillRankId; 
    
    private String notepad;
    
    public String getNotepad() {
		return notepad;
	}
	public void setNotepad(String notepad) {
		this.notepad = notepad;
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
	
    private String licenseNumber; //车牌号码  
    private Dictionary paiSe; //车牌颜色  
	
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	public void setPaiSe(Dictionary paiSe) {
		this.paiSe = paiSe;
	}
	// ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//
	/**距离某日将要到期的车辆*/
	public String getWillExpired(){
		if(startDate==null||endDate==null){
            this.getResultMap().put("message", getText("param.error"));
            this.getResultMap().put("success", false);
            return SUCCESS;
    	}
		Integer count = carfileService.findCountByMaintainDateWillExpired(carownerId,startDate, endDate);
        setTotalCount(count);
		List<Carfile> list = carfileService.findByMaintainDateWillExpired(carownerId,startDate, endDate,this.getStart(), this.getLimit());
		this.putResultList(list);
		if(DOWNLOADALL.equals(getRequest().getParameter("download"))){
        	return DOWNLOADALL;
        }
        return SUCCESS;
        
    }
	/**获取打印指针*/
	public String getPrintIndex(){
		
		int type = Integer.parseInt(getRequest().getParameter("type"));
		int carId = Integer.parseInt(getRequest().getParameter("carId"));
		
        Carfile car = carfileService.get(carId);
        if(type==2)type=car.getPrintIndex2();
        else type=car.getPrintIndex1();
        this.getResultMap().put("message", ""+type);
        this.getResultMap().put("success", true);
        return SUCCESS;
        
    }
	private Integer ghee;//过户到的车主
	
	public void setGhee(Integer ghee) {
		this.ghee = ghee;
	}
	/**过户操作*/
    public String updateGH(){
    	if(_carUneditable_(id))return SUCCESS;
    	
    	Carfile car = carfileService.get(id);
    	if(_carNeedPunishment(car)){
    	    this.getResultMap().put("message", getText("car.donot.permission"));
            this.getResultMap().put("success", false);
            return SUCCESS;
    	}
    	if(ghee==null){
            this.getResultMap().put("message", getText("param.error"));
            this.getResultMap().put("success", false);
            return SUCCESS;
    	}
    	if(ghee.intValue()==car.getCarowner().getId().intValue()){
    		this.getResultMap().put("message", getText("gh.error.same.owner"));
            this.getResultMap().put("success", false);
            return SUCCESS; 
    	}
    	Carowner newOwner = carownerDAO.get(ghee);
    	if(newOwner==null){
    		 this.getResultMap().put("message", getText("find.owner.no"));
             this.getResultMap().put("success", false);
             return SUCCESS;
    	}
   	
    	carfileService.updateGH(car,licenseNumber, newOwner, notepad);

        this.getResultMap().put("message", getText("gh.save.success"));
        this.getResultMap().put("success", true);
    	return SUCCESS;
    }
    /**转籍操作*/
    public String updateZJ(){
    	
    	if(_carUneditable_(id))return SUCCESS;
   	
    	Carfile car = carfileService.get(id);
    	if(_carNeedPunishment(car)){
    	    this.getResultMap().put("message", getText("car.donot.permission"));
            this.getResultMap().put("success", false);
            return SUCCESS;
    	}
    	carfileService.updateZJ(car, notepad);

        this.getResultMap().put("message", getText("zj.save.success",new String[]{car.getLicenseNumber()}));
        this.getResultMap().put("success", true);
    	return SUCCESS;
    }
    /**报废操作*/
    public String updateZX(){
    	
    	if(_carUneditable_(id))return SUCCESS;
   	
    	Carfile car = carfileService.get(id);
    	if(_carNeedPunishment(car)){
    	    this.getResultMap().put("message", getText("car.donot.permission"));
            this.getResultMap().put("success", false);
            return SUCCESS;
    	}
    	carfileService.updateZX(car, notepad);

        this.getResultMap().put("message", getText("zx.save.success",new String[]{car.getLicenseNumber()}));
        this.getResultMap().put("success", true);
    	return SUCCESS;
    }
    final boolean _carNeedPunishment(Carfile car){
    	if(car.getExpired()==ICarfileService.EXPIRED_SO&&car.getApproval()==ICarfileService.APPROVAL_NOT){
    		return true;
    	}else{
    		return false;
    	}
    }
    final private boolean _carUneditable_(Integer carId){
    	if(carId==null){
            this.getResultMap().put("message", getText("car.id.null"));
            this.getResultMap().put("success", false);
            return true;
    	}
    	Carfile car = carfileService.get(carId);
    	if(car==null){
            this.getResultMap().put("message", getText("car.not.found.by.id",new String[]{""+carId}));
            this.getResultMap().put("success", false);
            return true;
    	}
    	if(car.getCarStatus()!=ICarfileService.CARSTATUS_NORMAL){
    		String status = dictionaryService.getDictionaryName(Carfile.CARSTATUS_TYPE, car.getCarStatus());
    		if(status==null)status="";
            this.getResultMap().put("message", getText("car.status.not.normal",new String[]{car.getLicenseNumber(),status}));
            this.getResultMap().put("success", false);
    		return true;
    	}
    	return false;
    }
    /**
     * 编辑车辆记录
     * 获取修改的车辆档案记录信息
     * 要做审核
     * @return "success"
     */
    public String update(){
    	
    	if(_carUneditable_(carfile.getId()))return SUCCESS;
    	
    	if(carfileService.existCar(carfile)){
    		this.getResultMap().put("message", getText("check.car.exists.yes"));
	        this.getResultMap().put("success", false);
	        return SUCCESS;
    	}
    	carfileService.update(carfile);
        this.getResultMap().put("message", getText("save.success"));
        this.getResultMap().put("success", true);
        return SUCCESS;
    }
    /**输入的时候输入完毕牌色和牌号即来查询是否存在*/
    public String checkCarExists(){
    	try {
			carfile.setLicenseNumber(new String(carfile.getLicenseNumber().trim().getBytes("ISO8859-1"), "gbk"));			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(carfileService.existCar(carfile)){
    		this.getResultMap().put("message", getText("check.car.exists.yes"));
            this.getResultMap().put("success", false);//这是查获
    	}else{
    		this.getResultMap().put("message", getText("check.car.exists.no"));
            this.getResultMap().put("success", true);//新车
    	}
    	return SUCCESS;
    }
    //检查二维日期不能大于当前日期、技术等级有效期止不能大于今天往后推一个技评周期后的日期
    private boolean _jishu_erwei_date_invalid(Carfile car){
    	Date now = new Date();
    	if(car.getMaintainDate().after(now))return true;
//    	Dictionary d = dictionaryService.get(car.getEvaluateCycle().getId());
//    	if(CalendarUtil.afterMonth(car.getEvaluateDate(), -(d.getValue())).after(now))return true;
    	return false;
    }

//    public void validateAdd() {
//    }
    /**
     * 新增车辆档案
     * @return "success"
     */		
    public String saveCar() {
    	if(carfile.getId()!=null){//认为是更新
            this.getResultMap().put("message", getText("save.success"));
            this.getResultMap().put("success", true);
    		return update();    		
    	}
    	if(_jishu_erwei_date_invalid(carfile)){//update地方不修改技评和二维日期
    		this.getResultMap().put("message", getText("jishu.erwei.date.invalid"));
            this.getResultMap().put("success", false);
            return SUCCESS;
    	}
    	//检查车辆是否存在
    	if(carfileService.existCar(carfile)){
    		this.getResultMap().put("message", getText("check.car.exists.yes"));
            this.getResultMap().put("success", false);
            return SUCCESS;
    	}else{
    		
    		//保存车辆信息
	        carfileService.addFromFront(carfile);
	        //保存随之而来的等级评定和二级维护
	        checkService.addFromNewCar(carfile);	
			
	        this.getResultMap().put("message", getText("save.success"));
	        this.getResultMap().put("carId", ""+carfile.getId());
	        this.getResultMap().put("success", true);
	        return SUCCESS;
    	}
    }
    /**
     * 高级查询车辆档案记录信息
     * @return "success"
     */
    
    public String advancedFind(){
        if (isFirstSearch()) {
            //  初次查询时，要从数据库中读取总记录数
        	log.debug("totle size.1");
            Integer count = carfileService.advancedFindCount(getQueryBean());
            setTotalCount(count);
        }         
        //  得到分页查询结果
        log.debug("find resultset:"+this.getLimit());
        List<Carfile> Carfiles = carfileService.advancedFindByPerPage(getQueryBean(), this.getStart(), this.getLimit());
        this.putResultList(Carfiles);
        if(DOWNLOADALL.equals(getRequest().getParameter("download"))){
        	return DOWNLOADALL;
        }
        return SUCCESS;
    }
    /**
     * 删除车辆档案记录
     * @return "success"
     */
    public String delete(){
        carfileService.delete(CarfileIds);
        this.getResultMap().put("message", getText("delete.success"));
        this.getResultMap().put("success", true);
        return SUCCESS;
    }
    
    /**
     * 填写处罚回单（扩展为填写其他）
     * @return "success"
     */
    public String saveNotepad(){
    	
		if(_carUneditable_(id))return SUCCESS;
    	
    	carfileService.updateNotepad(id,notepad);
    	this.getResultMap().put("message", getText("save.success"));
        this.getResultMap().put("success", true);
        return SUCCESS;        
    }   
    
    /**
     * skillRank普通查询车辆档案记录 
     * @return
     */
    public String findByDateSpacing(){
        if (isFirstSearch()) {
            //  初次查询时，要从数据库中读取总记录数
        	log.debug("totle size.2");
            Integer count = carfileService.findByCreateDateSpacingCount(carfile.getSkillRank().getId());
            setTotalCount(count);
        }
        log.debug("find resultset.2");
        //  得到分页查询结果
        List<Carfile> Carfiles = carfileService.findByCreateDateSpacingPerPage(this.getStart(), this.getLimit(),carfile.getSkillRank().getId());
        this.putResultList(Carfiles);
        return SUCCESS;
    }
    /**
     * skillRank及CarType普通查询车辆档案记录 
     * @return
     */
    public String  findCarfileBySkill(){
    	if(carTypeId==null){
    		carTypeId=0;
    	}if(skillRankId==null){
    		skillRankId=0;
    	}
        if (isFirstSearch()) {
        	log.debug("totle size.3");
            //  初次查询时，要从数据库中读取总记录数
            Integer count = carfileService.findCarfileBySkill(carTypeId,skillRankId);
            setTotalCount(count);
        }   
        log.debug("find resultset.3");
        //  得到分页查询结果
        List<Carfile> Carfiles = carfileService.findCarfileBySkillPerPage(this.getStart(), this.getLimit(),carTypeId,skillRankId);
        this.putResultList(Carfiles);
        if(DOWNLOADALL.equals(getRequest().getParameter("download"))){
        	return DOWNLOADALL;
        }
        return SUCCESS;
    }
    
//    public String findByDateSpacing(){
//        if (isFirstSearch()) {
//            //  初次查询时，要从数据库中读取总记录数
//            Integer count = carfileService.findByCreateDateSpacingCount(startDate, endDate,carfile.getSkillRank().getId());
//            setTotalCount(count);
//        }         
//        //  得到分页查询结果
//        List<Carfile> Carfiles = carfileService.findByCreateDateSpacingPerPage(startDate, endDate, this.getStart(), this.getLimit(),carfile.getSkillRank().getId());
//        this.putResultList(Carfiles);
//        return SUCCESS;
//    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    /**
     * @return the Carfile
     */
    public Carfile getCarfile() {
        return carfile;
    }
    public int[] getCarfileIds() {
        return CarfileIds;
    }

    /**
     * @param Carfile the Carfile to set
     */
    public void setCarfile(Carfile carfile) {
        this.carfile = carfile;
    }    
    public void setCarfileIds(int[] CarfileIds) {
        this.CarfileIds = CarfileIds;
    }

    /**
     * 查询车辆档案相应的检测记录信息
     * @return "success"
     */
    public String getCheckByCar(){
//        if (isFirstSearch()) {
//            //  初次查询时，要从数据库中读取总记录数
//            Integer count = checkService.advancedFindCount(getQueryBean());
//            setTotalCount(count);
//        }         
        	//得到分页查询结果    	
    	Log.debug(licenseNumber); //得不到 licenseNumber
        List<Check> Checks = checkService.advancedFindByPerPageByCar(paiSe,licenseNumber,this.getStart(), this.getLimit());
        this.putResultList(Checks);
        return SUCCESS;
    }
    public String getNotepadOfCar(){
    	Carfile car = carfileService.get(this.id);
    	if(car==null){
            this.getResultMap().put("message", getText("carfile.not.found",new String[]{""+this.id}));
            this.getResultMap().put("success", false);
    	}else{
    		String notepad = car.getNotepad();
    		if(notepad==null)notepad="";
            this.getResultMap().put("message", notepad);
            this.getResultMap().put("success", true);
    	}
    	return SUCCESS;
    }
    /**拥有该权限的操作员以回单编号等为依据完成处罚过程*/
    public String ridofPunishment(){//需要核对是否拥有该权限

    	Carfile car= carfileService.get(this.id);
    	if(car.getExpired()!=ICarfileService.EXPIRED_SO){//这里改为实时计算
    		this.getResultMap().put("message", getText("reidofExpirePunishment.neednot"));
            this.getResultMap().put("success", false);
        	return SUCCESS;	
    	}
    	if(car.getApproval()!=ICarfileService.APPROVAL_NOT){
    		this.getResultMap().put("message", getText("reidofExpirePunishment.neednot.approval"));
            this.getResultMap().put("success", false);
        	return SUCCESS;
    	}
    	carfileService.ridofPunishment(this.id,notepad);
        this.getResultMap().put("message", getText("reidofExpirePunishment.success"));
        this.getResultMap().put("success", true);
    	return SUCCESS;
    }
    /**行政审批免除处罚的操作*/
    public String exemptPunishment(){//行政审批免除罚款//是否还能撤销行政决定的免除？谁能撤销谁的？是否还有行政决定给予某种处罚的？
    	
    	Carfile car= carfileService.get(this.id);
    	if(car.getExpired()!=ICarfileService.EXPIRED_SO){
    		this.getResultMap().put("message", getText("reidofExpirePunishment.neednot"));
            this.getResultMap().put("success", false);
        	return SUCCESS;	
    	}else if(car.getApproval()!=ICarfileService.APPROVAL_NOT){
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
    	carfileService.exemptPunishment(this.id,notepad);
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
    	carfile=carfileService.get(this.id);
    	if(carfile==null){
    		this.getResultMap().put("message", getText("carfile.not.found",new String[]{""+this.id}));
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
		List<Carfile> list = (List<Carfile>)getResultMap().get("data");
		Carfile car = null;
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
	}
}
