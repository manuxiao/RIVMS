package org.gaixie.micrite.enterprise.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.Enterprise;
import org.gaixie.micrite.dic.service.IDictionaryService;
import org.gaixie.micrite.enterprise.service.IEnterpriseService;
import org.gaixie.micrite.upload.IDealWith;
import org.gaixie.micrite.util.CalendarUtil;
import org.gaixie.micrite.util.DictionaryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hansheng.njj.PinyinConv;
@Service("dealWithEnterprise") 
public class DealWithEnterprise implements IDealWith{
	Logger log = Logger.getLogger(DealWithEnterprise.class);
	@Autowired
	private IEnterpriseService enterpriseService;
	@Autowired
	private IDictionaryService dictionaryService;

	static DateFormat df = CalendarUtil.df;
	
	@Override
	public int doJob(File src,Map<String, String>res)throws Exception {
		List<Dictionary> qualifications=dictionaryService.findALLDictionary(Enterprise.QUALIFICATION_TYPE);
		List<Dictionary> kinds=dictionaryService.findALLDictionary(Enterprise.KIND_TYPE);
		List<Dictionary> workTypes=dictionaryService.findALLDictionary(Enterprise.WORKTYPE_TYPE);
//		List<Dictionary> ranges=dictionaryService.findALLDictionary(11);
		// TODO Auto-generated method stub
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(src));
		Enterprise enterprise = new Enterprise();
    	HSSFSheet sheet = wb.getSheetAt(0);
    	String temp = null;
    	for(int i=sheet.getFirstRowNum()+1;i<=sheet.getLastRowNum();i++) {
    		HSSFRow row = sheet.getRow(i);
    		HSSFCell cell = row.getCell(4);
    		try {
    			temp = cell.getStringCellValue();	
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
    		 
    		if(enterpriseService.existEnterprise(null,temp)) 
    			continue;
    		cell = row.getCell(1);
    		enterprise.setUnitName(cell.getStringCellValue());
    		cell = row.getCell(2);
    		enterprise.setLegalPerson(cell.getStringCellValue());
    		cell = row.getCell(3);
    		enterprise.setTelephone1(cell.getStringCellValue());
    		cell = row.getCell(4);
    		enterprise.setLicense(cell.getStringCellValue());
    		cell = row.getCell(5);
    		enterprise.setQualification(DictionaryUtil.getDictionary(cell.getStringCellValue(),qualifications));
    		cell = row.getCell(6);
    		enterprise.setHandleMan(cell.getStringCellValue());
    		cell = row.getCell(7);
    		enterprise.setTelephone2(cell.getStringCellValue());
    		cell = row.getCell(8);
    		enterprise.setTelephone3(cell.getStringCellValue());
    		cell = row.getCell(9);
    		enterprise.setCommission(cell.getStringCellValue());
    		cell = row.getCell(10);
    		enterprise.setTelephone4(cell.getStringCellValue());
    		cell = row.getCell(11);
    		enterprise.setKind(DictionaryUtil.getDictionary(cell.getStringCellValue(), kinds));
    		cell = row.getCell(12);
    		if(cell.getStringCellValue()!=null) {
				try {
					enterprise.setLicenseDate(df.parse(cell.getStringCellValue()));	
				} catch (Exception e) {}
			}
    		cell = row.getCell(13);
    		if(cell.getStringCellValue()!=null) {
				try {
					enterprise.setDateBegin(df.parse(cell.getStringCellValue()));// HH:mm:ss.S	
				} catch (Exception e) {}
			}
    		cell = row.getCell(14);
    		if(cell.getStringCellValue()!=null) {
				try {
					enterprise.setDateEnd(df.parse(cell.getStringCellValue()));	
				} catch (Exception e) {}
			}
    		cell = row.getCell(15);
    		enterprise.setAddress(cell.getStringCellValue());
    		cell = row.getCell(16);
    		if(cell.getStringCellValue()!=null) {
				try {
					enterprise.setEditDate(df.parse(cell.getStringCellValue()));	
				} catch (Exception e) {}
			}
    		cell = row.getCell(17);
    		enterprise.setWorkArea(cell.getStringCellValue());
    		cell = row.getCell(18);
    		enterprise.setWorkRemark(cell.getStringCellValue());
    		cell = row.getCell(19);
    		enterprise.setWorkType(DictionaryUtil.getDictionary(cell.getStringCellValue(), workTypes));
    		//车辆数量是cell20 不要了
    		//---------------------------------    		
    		//导入文件中没有该信息
    		enterprise.setStation(DictionaryUtil.getDefaultDictionary());
    		enterprise.setStatus(IEnterpriseService.STATUS_NORMAL);
    		enterpriseService.add(enterprise);
    	}	
    	return IDealWith.OK;
	}
}
