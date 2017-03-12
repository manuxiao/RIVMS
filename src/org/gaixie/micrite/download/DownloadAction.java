package org.gaixie.micrite.download;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.gaixie.micrite.action.GenericAction;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.car.service.ICarfileService;
import org.gaixie.micrite.dic.dao.IDictionaryDAO;
import org.springframework.beans.factory.annotation.Autowired;
/***
 * 数据下载类
 * 
 * */
public class DownloadAction extends GenericAction {
	 
	private Logger log = Logger.getLogger(DownloadAction.class);
	private String fileName;

	public String getFileName() {
		return fileName;
	}
	@Autowired
	private IDictionaryDAO dictionaryDao;
	@Autowired
	private ICarfileService carService;

	public void setCarService(ICarfileService carService) {
		this.carService = carService;
	}

	/**
	 * 下载超期未检的车辆列表
	 * **/
	public String expiredCars() throws Exception {
		String currentDate = null;
		try {
			DateFormat df = new SimpleDateFormat(getText("jiance.date.format"));
			currentDate = df.format(new Date());
		} catch (Exception e) {
			log.debug(e);
		}

		fileName = "超时未检车辆列表" + currentDate + ".xls";
		try {
			fileName = new String(fileName.getBytes("gbk"), "ISO8859-1");
		} catch (Exception e) {
			// TODO: handle exception
			log.debug(e);
		}
		return SUCCESS;
	}

	private final static int EXPIREDCARS_COLNUM = 20;

	public InputStream getDownloadExpiredCars() throws Exception, IOException {
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
		List<Carfile> list = carService.getExpiredCars();
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
			if(car.getMobile()!=null&&car.getMobile().trim().length()=="13811223344".length()){
				cell.setCellValue(car.getMobile());	
			}else if(car.getTelephone()!=null&&car.getTelephone().trim().length()=="13811223344".length()){
				cell.setCellValue(car.getTelephone());
			}
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
