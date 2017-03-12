package org.gaixie.micrite.river.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.gaixie.micrite.action.GenericAction;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.River;
import org.gaixie.micrite.beans.IdName;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.dic.service.IDictionaryService;
import org.gaixie.micrite.download.ExcelUtils;
import org.gaixie.micrite.river.service.IRiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;

import com.hansheng.njj.PinyinConv;

/**
 * RiverAction用来响应用户对River基本信息维护时的操作，并调用相关的Service。
 * <p>
 * 通过调用相关的Service类，完成对River基本信息的增加，删除，修改，查询。
 */
public class RiverAction extends GenericAction {
	private static final long serialVersionUID = 3072131320220662398L;

	@Autowired
	private IRiverService riverService;
	@Autowired
	private IDictionaryService dictionaryService;


	// 获取的页面参数
	private River river;
	private int[] riverIds;
	private Date startDate;
	private Date endDate;

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private Integer id;
 
	// ~~~~~~~~~~~~~~~~~~~~~~~ Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//
	/**输入的时候输入完毕牌色和牌号即来查询是否存在*/
    public String checkRiverExists(){
    	
    	if(riverService.existRiver(river)){
    		this.getResultMap().put("message", getText("check.enterprise.exists.yes"));
            this.getResultMap().put("success", false);//这是查获
    	}else{
    		this.getResultMap().put("message", getText("check.enterprise.exists.no"));
            this.getResultMap().put("success", true);//不存在
    	}
    	return SUCCESS;
    }
	/**
	 * 保存河道信息		
	 * 
	 * @return "success"
	 */
	public String add() {
		if(river.getId()!=null){//认为是更新
    		return update();
    	}
		User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		river.setCreaterId(currentUser.getId());
		river.setBegindate (new Date());
		river.setEditorId(river.getCreaterId());
		river.setModifydate(river.getBegindate());
		river.setPy(PinyinConv.cn2py(river.getRname()));
		river.setStatus(IRiverService.STATUS_NORMAL);
		
		riverService.add(river);
		this.getResultMap().put("message", getText("save.success"));
		this.getResultMap().put("success", true);
		return SUCCESS;
	}

	/**
	 * 高级查询河道信息
	 * 
	 * @return "success"
	 */
	public String advancedFind() {
		if (isFirstSearch()) {
			// 初次查询时，要从数据库中读取总记录数
			Integer count = riverService.advancedFindCount(getQueryBean());
			setTotalCount(count);
		}
		List<River> rivers = riverService.advancedFindByPerPage(
				getQueryBean(), this.getStart(), this.getLimit());
		this.putResultList(rivers);
		if(DOWNLOADALL.equals(getRequest().getParameter("download"))){
        	return DOWNLOADALL;
        }
		return SUCCESS;
	}
	
	/**
	 * 高级查询河道信息
	 * 
	 * @return "success"
	 */
	public String findRiverByPy(){
		String py = getRequest().getParameter("py");
		List<IdName> rivers = riverService.findRiverByPy(py);
		this.putResultList(rivers);
		return SUCCESS;
	}
	/**
	 * 删除河道
	 * 
	 * @return "success"
	 */
	public String delete() {
		riverService.delete(riverIds);
		this.getResultMap().put("message", getText("delete.success"));
		this.getResultMap().put("success", true);
		return SUCCESS;
	}
	/**
	 * 日期间隔及RiverSourceType普通查询河道
	 * 
	 * @return
	 */
	public String findByDateSpacing() {
		if (isFirstSearch()) {
			// 初次查询时，要从数据库中读取总记录数
			Integer count = riverService.findByCreateDateSpacingCount(
					startDate, endDate, river.getVillage().getId());
			setTotalCount(count);
		}
		// 得到分页查询结果
		List<River> rivers = riverService
				.findByCreateDateSpacingPerPage(startDate, endDate, this
						.getStart(), this.getLimit(), river.getVillage().getId());
		this.putResultList(rivers);
		return SUCCESS;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~ Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//
	/**
	 * @return the river
	 */
	public River getRiver() {
		return river;
	}

	public int[] getRiverIds() {
		return riverIds;
	}

//	public void setEndDate(String endDate) throws ParseException {
//		this.endDate = DateUtils.parseDate(endDate + ":00",
//				new String[] { "yyyy-MM-dd hh:mm:ss" });
//	}

	/**
	 * @param river
	 *            the river to set
	 */
	public void setRiver(River river) {
		this.river = river;
	}

	public void setRiverIds(int[] riverIds) {
		this.riverIds = riverIds;
	}

//	public void setStartDate(String startDate) throws ParseException {
//		this.startDate = DateUtils.parseDate(startDate + ":00",
//				new String[] { "yyyy-MM-dd hh:mm:ss" });
//	}

	/**
	 * 获取修改的河道信息 需要自动生成审核信息
	 * 
	 * @return "success"
	 */
	public String update() {
		
		if(riverService.existRiver(river)){
    		this.getResultMap().put("message", getText("check.river.exists.yes"));
	        this.getResultMap().put("success", false);
	        return SUCCESS;
    	}
		riverService.update(river);
		this.getResultMap().put("message", getText("save.success"));
		this.getResultMap().put("success", true);
		return SUCCESS;

	}
	public String getEntity(){
    	river=riverService.get(id);
    	return SUCCESS;
    }
	
	final static String DOWNLOADALL="downloadall";
	private final static int EXPIREDCARS_COLNUM = 20;
	final private String fileName="rivers";
	
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
		cell.setCellValue("河道编码");
		cell = row.getCell(2);
		cell.setCellValue("河道名称");
		cell = row.getCell(3);
		cell.setCellValue("河道长度");
		cell = row.getCell(4);
		cell.setCellValue("河道起点");
		cell = row.getCell(5);
		cell.setCellValue("河道终点");
		cell = row.getCell(6);
		cell.setCellValue("是否马路沿线");
		cell = row.getCell(7);
		cell.setCellValue("是否美丽河道");
		cell = row.getCell(8);
		cell.setCellValue("河道状态");
		cell = row.getCell(9);
		cell.setCellValue("河长");
		cell = row.getCell(10);
		cell.setCellValue("公示牌编号");
		cell = row.getCell(11);
		cell.setCellValue("公示牌位置");
		cell = row.getCell(12);
  
//		int permissionDays = dictionaryDao.get(EXPIRED_PERMISSION_ID).getValue();
		List<River> list = (List<River>)getResultMap().get("data");
		River obj = null;
		for (int j = 0; j < list.size(); ++j) {
			ExcelUtils.insertRow(sheet, j + 1, EXPIREDCARS_COLNUM);
			obj = list.get(j);
			row = sheet.getRow(j+1);
			cell = row.getCell(0);//序号
			cell.setCellValue(j+1);
			cell = row.getCell(1);//河道编码
			cell.setCellValue(obj.getRid());
			cell = row.getCell(2);//河道名称
			cell.setCellValue(obj.getRname());
//			if(obj.getMobile()!=null&&obj.getMobile().trim().length()=="13811223344".length()){
//				cell.setCellValue(obj.getMobile());	
//			}else if(obj.getTelephone()!=null&&obj.getTelephone().trim().length()=="13811223344".length()){
//				cell.setCellValue(obj.getTelephone());
//			}
			cell = row.getCell(3);//河道所属村
			cell.setCellValue(obj.getVillage().getVname());
			cell = row.getCell(4);//河道长度
			cell.setCellValue(obj.getRlen());
			cell = row.getCell(5);//河道起点
			cell.setCellValue(obj.getStart());
			cell = row.getCell(6);//河道终点
			cell.setCellValue(obj.getEnd());
			cell = row.getCell(7);//是否马路沿线
			cell.setCellValue(obj.getOutload());
			cell = row.getCell(8);//是否美丽河道
			cell.setCellValue(obj.getBeautiful());
			cell = row.getCell(9);//河道状态(PH,富营养化)
			cell.setCellValue(obj.getStatus());
			cell = row.getCell(10);//河长
			cell.setCellValue(obj.getCreaterId());
			cell = row.getCell(11);//公示牌编号
			cell.setCellValue(obj.getBoard());
			cell = row.getCell(12);//公示牌位置
			cell.setCellValue(obj.getBoardlocation());
		 
//	    	try {
//	    		cell.setCellValue(df.format(obj.getLicenseDate()));
//			} catch (Exception e) {}
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
