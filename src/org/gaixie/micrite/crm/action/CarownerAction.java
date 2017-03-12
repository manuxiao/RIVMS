/* ===========================================================
 * $Id: CustomerAction.java 498 2009-08-13 09:41:54Z yebo2009@gmail.com $
 * This file is part of Micrite
 * ===========================================================
 *
 * (C) Copyright 2009, by Gaixie.org and Contributors.
 * 
 * Project Info:  http://micrite.gaixie.org/
 *
 * Micrite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Micrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Micrite.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.gaixie.micrite.crm.action;

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
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.gaixie.micrite.action.GenericAction;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Carowner;
import org.gaixie.micrite.beans.Enterprise;
import org.gaixie.micrite.beans.IdName;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.crm.service.ICarownerService;
import org.gaixie.micrite.download.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;

/**
 * CustomerAction用来响应用户对Customer基本信息维护时的操作，并调用相关的Service。
 * <p>
 * 通过调用相关的Service类，完成对Customer基本信息的增加，删除，修改，查询。
 */
public class CarownerAction extends GenericAction{ 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7995129929273200363L;

	@Autowired
	private ICarownerService carownerService;

//    private List<CustomerSource> carownerSource;
    //获取的页面参数
    private Carowner carowner;
    private int[] carownerIds;
    private Date startDate;
    private Date endDate;
    
    private Integer id;
    private String name;
    private String address;
    private String license;   
    private String mobile;
    private String telephone;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	// ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    public String getCarownerByName() {
    	if(carowner.getName()==null||carowner.getName().trim().equals("")){
    		this.getResultMap().put("message", getText("find.owner.error.name"));
	      this.getResultMap().put("success", false);
	      return SUCCESS;
    	}   	
    	try {
//			carowner = carownerService.getByName(new String(carowner.getName().trim().getBytes("ISO8859-1"), "gbk"));
    		carowner.setName(new String(carowner.getName().trim().getBytes("ISO8859-1"), "gbk"));
    	} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      if(carowner==null){
    	  this.getResultMap().put("message", getText("find.owner.no"));
	      this.getResultMap().put("success", false);
	      return SUCCESS;
      }
      this.getResultMap().put("owner", carowner);
      this.getResultMap().put("message", getText("find.owner.yes"));
      this.getResultMap().put("success", true);
      return SUCCESS;
  }
 // ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    public String getCarownerByLicense() {
    	if(carowner.getLicense()==null||carowner.getLicense().trim().equals("")){
    		this.getResultMap().put("message", getText("find.owner.error.license"));
    		this.getResultMap().put("success", false);
    		return SUCCESS;
    	}
    	
		carowner = carownerService.getByLicense(carowner.getLicense());
		
      if(carowner==null){
    	  this.getResultMap().put("message", getText("find.owner.no"));
	      this.getResultMap().put("success", false);
	      return SUCCESS;
      }
      this.getResultMap().put("owner", carowner);
      this.getResultMap().put("message", getText("find.owner.yes"));
      this.getResultMap().put("success", true);
      return SUCCESS;
  }
    /**
     * 保存客户信息
     * @return "success"
     */
    public String add() {
    	carowner.setId(null);
    	if(carowner.getId()!=null){//认为是更新
    		return edit();
    	}
    	//检查车辆是否存在
    	if(carownerService.existCarowner(carowner)){
    		this.getResultMap().put("message", getText("check.carowner.exists.yes"));
            this.getResultMap().put("success", false);
            return SUCCESS;
    	}   	
    	carownerService.save(carowner);
    	this.getResultMap().put("id", carowner.getId());
        this.getResultMap().put("message", getText("save.success"));
        this.getResultMap().put("success", true);
        return SUCCESS;
    }
    /**
     * 高级查询客户信息
     * @return "success"
     */
    public String advancedFind(){
        if (isFirstSearch()) {
            //  初次查询时，要从数据库中读取总记录数
            Integer count = carownerService.advancedFindCount(getQueryBean());
            setTotalCount(count);
        }         
        //  得到分页查询结果 
        List<Carowner> carowners = carownerService.advancedFindByPerPage(getQueryBean(), this.getStart(), this.getLimit());
        this.putResultList(carowners);
        if(DOWNLOADALL.equals(getRequest().getParameter("download"))){
        	return DOWNLOADALL;
        }
        return SUCCESS;
    }
    /**
     * 删除客户
     * @return "success"
     */
    public String delete(){
        carownerService.delete(carownerIds);
        this.getResultMap().put("message", getText("delete.success"));
        this.getResultMap().put("success", true);
        return SUCCESS;
        
    }
    /**
     * 编辑//没有用了
     * @return "success"
     */
    public String update(){
    	if(carowner==null||carowner.getId()==null){
            this.getResultMap().put("message", getText("param.error"));
            this.getResultMap().put("success", false);
            return SUCCESS;
    	}
        carownerService.update(carowner);
        this.getResultMap().put("message", getText("edit.success"));
        this.getResultMap().put("success", true);
        return SUCCESS;
        
    }
    
    /**
     * 编辑车主
     * @return "success"
     */
    public String edit(){
    	if(id==null){
            this.getResultMap().put("message", getText("param.error"));
            this.getResultMap().put("success", false);
            return SUCCESS;
    	}
    	Carowner carowner = new Carowner();
    	carowner.setId(id);
    	carowner.setName(name);
    	carowner.setAddress(address);
    	carowner.setLicense(license);  
    	carowner.setMobile(mobile);
    	carowner.setTelephone(telephone);

    	if(carownerService.existCarowner(carowner)){
    		this.getResultMap().put("message", getText("check.carowner.exists.yes"));
	        this.getResultMap().put("success", false);
	        return SUCCESS;   
    	}
        carownerService.update(carowner);
        this.getResultMap().put("message", getText("edit.success"));
        this.getResultMap().put("success", true);
        return SUCCESS;        
    }
 
	/**
	 * 加ALL查询车主信息
	 * 
	 * @return "success"
	 */
//	public String findCarownerALL() {
//		List<Carowner> carowners = carownerService.advancedFindByPerPageALL(
//				getQueryBean(), this.getStart(), this.getLimit());
//		Carowner tempSource = new Carowner();
//        tempSource.setId(-1);
//        tempSource.setName("全部");
//        carowners.add(0,tempSource);
        
//		this.putResultList(carowners);
//		return SUCCESS;
//	}
	public String findCarownerByPy(){
		String py = getRequest().getParameter("py");
		List<IdName> carowners = carownerService.findCarownerByPy(py);
		this.putResultList(carowners);
		return SUCCESS;
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~  Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    public Carowner getCarowner() {
		return carowner;
	}
	public void setCarowner(Carowner carowner) {
		this.carowner = carowner;
	}
	public int[] getCarownerIds() {
		return carownerIds;
	}
	public void setCarownerIds(int[] carownerIds) {
		this.carownerIds = carownerIds;
	}
    public void setEndDate(String endDate) throws ParseException {
        this.endDate = DateUtils.parseDate(endDate + ":00", new String[]{"yyyy-MM-dd hh:mm:ss"});
    }
    public void setStartDate(String startDate) throws ParseException {
        this.startDate = DateUtils.parseDate(startDate + ":00", new String[]{"yyyy-MM-dd hh:mm:ss"});
    }
    

	final static String DOWNLOADALL="downloadall";
	private final static int EXPIREDCARS_COLNUM = 20;
	final private String fileName="carowners";
	
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
		cell.setCellValue("业户名称");
		cell = row.getCell(2);
		cell.setCellValue("联系电话");
		cell = row.getCell(3);
		cell.setCellValue("手机号码");
		cell = row.getCell(4);
		cell.setCellValue("业户地址");
		cell = row.getCell(5);
		cell.setCellValue("经营许可证号");
		
//		int permissionDays = dictionaryDao.get(EXPIRED_PERMISSION_ID).getValue();
		List<Carowner> list = (List<Carowner>)getResultMap().get("data");
		Carowner obj = null;
		for (int j = 0; j < list.size(); ++j) {
			ExcelUtils.insertRow(sheet, j + 1, EXPIREDCARS_COLNUM);
			obj = list.get(j);
			row = sheet.getRow(j+1);
			cell = row.getCell(0);//序号
			cell.setCellValue(j+1);
			cell = row.getCell(1);//业户 名称
			cell.setCellValue(obj.getName());
			cell = row.getCell(2);//联系电话
			cell.setCellValue(obj.getTelephone());
			cell = row.getCell(3);//手机号码
			cell.setCellValue(obj.getMobile());
			cell = row.getCell(4);//业户地址
			cell.setCellValue(obj.getAddress());
			cell = row.getCell(5);//经营许可证号
			cell.setCellValue(obj.getLicense());
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
