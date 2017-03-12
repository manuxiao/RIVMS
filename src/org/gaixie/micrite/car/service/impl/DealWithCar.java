package org.gaixie.micrite.car.service.impl;

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
import org.gaixie.micrite.beans.Carowner;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.car.service.ICarfileService;
import org.gaixie.micrite.crm.service.ICarownerService;
import org.gaixie.micrite.dic.service.IDictionaryService;
import org.gaixie.micrite.upload.IDealWith;
import org.gaixie.micrite.util.CalendarUtil;
import org.gaixie.micrite.util.DictionaryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hansheng.njj.PinyinConv;

@Service("dealWithCar")
public class DealWithCar implements IDealWith {
	final static String PAISE_TITLE="车牌颜色";
	/**对于货车和客车，Excel里AH列*/
	public final static int PAISE_COL_1_2 = 33;
	/**对于货车和客车，如果不曾手工干预，只有35列；如果CtrlA+CtrlC，则列数多得去了...*/
	public final static int COLS_1_2 = 35;
	/**对于出租车，这一列是最后一列之后手工添加的，Excel里AN列*/
	public final static int PAISE_COL_3 = 39;
	/**对于出租车，如果不曾手工干预，只有39列；如果CtrlA+CtrlC，则列数多得去了...*/
	public final static int COLS_3 = 39;
	Logger log = Logger.getLogger(DealWithCar.class);
	@Autowired
	private ICarfileService carfileService;
	@Autowired
	private IDictionaryService dictionaryService;
	@Autowired
	private ICarownerService carownerService;
	private List<Dictionary> carType;
	private List<Dictionary> skillRank;
	private List<Dictionary> licenseType;
	private List<Dictionary> fuelRank;
	private List<Dictionary> evaluateCycle;
	private List<Dictionary> maintainCycle;
	private List<Dictionary> paiSe;
	static DateFormat df = CalendarUtil.df;
	private Dictionary paiSeTmp = null;
	private String licenseNumber = null;

	// private Carfile carfile=new Carfile();
	@Override
	public int doJob(File src, Map<String, String> res) throws Exception {
		getPartner1();
		getPartner2();
		getPartner3();
		getPartner4();
		getPartner5();
		getPartner6();
		getPartner7();

		// TODO Auto-generated method stub
		if(log.isInfoEnabled()){
			if (src == null)
				log.info("upload file is null.");
		}
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(src));

		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(sheet.getFirstRowNum());
		int colSize = row.getLastCellNum();
		int rowSize = sheet.getLastRowNum();
		if (log.isInfoEnabled())
			log.info("this is colSize=" + rowSize);
		
		try {

			if (colSize == COLS_1_2) {// 货车等其他
				for (int i = sheet.getFirstRowNum() + 1; i <= rowSize; i++) {
					row = sheet.getRow(i);
					save1_2(row);
				}
			} else if (colSize == COLS_3) {// 出租车，无车牌颜色
				paiSeTmp = DictionaryUtil.getDictionaryWhenNotGiveLast("其他",
						paiSe);
				for (int i = sheet.getFirstRowNum() + 1; i <= rowSize; i++) {
					row = sheet.getRow(i);
					save3(row);
				}
			} else if (colSize > COLS_3) {// 出租车，手工添加车牌颜色
				if(sheet.getRow(sheet.getFirstRowNum())!=null&&sheet.getRow(sheet.getFirstRowNum()).getCell(PAISE_COL_1_2)!=null
						&&sheet.getRow(sheet.getFirstRowNum()).getCell(PAISE_COL_1_2).getStringCellValue()!=null&&PAISE_TITLE.equals(sheet.getRow(sheet.getFirstRowNum()).getCell(PAISE_COL_1_2).getStringCellValue().trim())){
					for (int i = sheet.getFirstRowNum() + 1; i <= rowSize; i++) {
						row = sheet.getRow(i);
						save1_2(row);
					}
				}else{
					for (int i = sheet.getFirstRowNum() + 1; i <= rowSize; i++) {
						row = sheet.getRow(i);
						if(row.getCell(PAISE_COL_3)!=null&&row.getCell(PAISE_COL_3).getStringCellValue()!=null){
							paiSeTmp = DictionaryUtil.getDictionaryWhenNotGiveLast(row.getCell(PAISE_COL_3).getStringCellValue(),
									paiSe);	
						}else{
							paiSeTmp = DictionaryUtil.getDictionaryWhenNotGiveLast("其他",
									paiSe);
						}
						save3(row);
					}
				}
			}else {
				log.info("does not deal with colum size=" + colSize);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info(e);
		}
		return IDealWith.OK;
	}

	public void setCarfileService(ICarfileService carfileService) {
		this.carfileService = carfileService;
	}

	/****/
	public void getPartner1() {
		carType = dictionaryService.findALLDictionary(1);
	}

	public void getPartner2() {
		skillRank = dictionaryService.findALLDictionary(2);
	}

	public void getPartner3() {
		licenseType = dictionaryService.findALLDictionary(3);
	}

	public void getPartner4() {
		fuelRank = dictionaryService.findALLDictionary(4);
	}

	public void getPartner5() {
		evaluateCycle = dictionaryService.findALLDictionary(5);
	}

	public void getPartner6() {
		maintainCycle = dictionaryService.findALLDictionary(6);
	}

	public void getPartner7() {
		paiSe = dictionaryService.findALLDictionary(7);
	}

	final private void save3(HSSFRow row) {
		HSSFCell cell = null;
		Carfile carfile = new Carfile();
		
		if (makeCarowner(row, carfile))
			return;

		cell = row.getCell(5);// 牌号
		licenseNumber = cell.getStringCellValue();

		if (carfileService.existCar(licenseNumber, paiSeTmp)) {
			if(log.isInfoEnabled()){
				log.info("return for existCar:"+licenseNumber+","+paiSeTmp.getName());
			}
			return;
		}

		carfile.setPaiSe(paiSeTmp);// 牌色
		carfile.setLicenseNumber(licenseNumber);// 牌号
		cell = row.getCell(6);
		carfile.setCarType(DictionaryUtil.getDictionary(cell
				.getStringCellValue(), carType));
		cell = row.getCell(7);
		carfile.setLicenseType(DictionaryUtil.getDictionary(cell
				.getStringCellValue(), licenseType));
		cell = row.getCell(8);
		carfile.setLoadTon(cell.getStringCellValue());
		cell = row.getCell(13);
		carfile.setBrandType(cell.getStringCellValue());
		cell = row.getCell(14);
		carfile.setYingyunNo(cell.getStringCellValue());
		cell = row.getCell(25);
		carfile.setCarRemark(cell.getStringCellValue());
		cell = row.getCell(27);
		carfile.setFuelRank(DictionaryUtil.getDictionary(cell
				.getStringCellValue(), fuelRank));
		cell = row.getCell(28);
		carfile.setSkillRank(DictionaryUtil.getDictionary(cell
				.getStringCellValue(), skillRank));
		cell = row.getCell(29);
		if (cell.getStringCellValue() != null
				&& !cell.getStringCellValue().equals("")) {
			try {
				carfile.setEvaluateDate(df.parse(cell.getStringCellValue()));
			} catch (Exception e) {
			}// {log.info(e+":cell("+i+",29).getStringCellValue()="+cell.getStringCellValue());}
		}// else{log.info(":cell("+i+",29).getStringCellValue()==null");}
		cell = row.getCell(30);
		carfile.setEvaluateCycle(DictionaryUtil.getDictionary(cell
				.getStringCellValue(), evaluateCycle));
		cell = row.getCell(31);
		if (cell.getStringCellValue() != null
				&& !cell.getStringCellValue().equals("")) {
			try {
				carfile.setMaintainDate(df.parse(cell.getStringCellValue()));// new
																				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S")
			} catch (Exception e) {
			}// {log.info(e+":cell("+i+",31).getStringCellValue()="+cell.getStringCellValue());}
		}// else{log.info(":cell("+i+",31).getStringCellValue()==null");}
		cell = row.getCell(32);
		carfile.setMaintainCycle(DictionaryUtil.getDictionary(cell
				.getStringCellValue(), maintainCycle));
try{
		carfileService.addFromUpload(carfile);
	}catch(Exception e){
		log.info("when save yunyingNo="+carfile.getYingyunNo()+","+carfile.getLicenseNumber()+","+carfile.getPaiSe().getName()+":"+e);
}
	}

	final private void save1_2(HSSFRow row) {

		HSSFCell cell = null;
		Carfile carfile = new Carfile();

		if (makeCarowner(row, carfile)){
			return;
		}
			

		cell = row.getCell(5);// 牌号
		licenseNumber = cell.getStringCellValue();
		cell = row.getCell(PAISE_COL_1_2);// 牌色
		paiSeTmp = DictionaryUtil.getDictionaryWhenNotGiveLast(cell
				.getStringCellValue(), paiSe);

		if (carfileService.existCar(licenseNumber, paiSeTmp)) {
			if(log.isInfoEnabled()){
					log.info("return for existCar:"+licenseNumber+","+paiSeTmp.getName());
			}
			return;
		}
		carfile.setPaiSe(paiSeTmp);// 牌色
		carfile.setLicenseNumber(licenseNumber);// 牌号
		cell = row.getCell(6);
		carfile.setCarType(DictionaryUtil.getDictionary(cell
				.getStringCellValue(), carType));
		cell = row.getCell(7);
		carfile.setLicenseType(DictionaryUtil.getDictionary(cell
				.getStringCellValue(), licenseType));
		cell = row.getCell(8);
		carfile.setLoadTon(cell.getStringCellValue());
		cell = row.getCell(13);
		carfile.setBrandType(cell.getStringCellValue());
		cell = row.getCell(14);
		carfile.setYingyunNo(cell.getStringCellValue());
		cell = row.getCell(22);
		carfile.setCarRemark(cell.getStringCellValue());
		cell = row.getCell(24);
		carfile.setFuelRank(DictionaryUtil.getDictionary(cell
				.getStringCellValue(), fuelRank));
		cell = row.getCell(25);
		carfile.setSkillRank(DictionaryUtil.getDictionary(cell
				.getStringCellValue(), skillRank));
		cell = row.getCell(26);
		if (cell.getStringCellValue() != null
				&& !cell.getStringCellValue().equals("")) {
			try {
				carfile.setEvaluateDate(df.parse(cell.getStringCellValue()));
			} catch (Exception e) {
			}// {log.info(e+":cell("+i+",26).getStringCellValue()="+cell.getStringCellValue());}
		}// else{log.info(":cell("+i+",26).getStringCellValue()==null");}
		cell = row.getCell(27);
		carfile.setEvaluateCycle(DictionaryUtil.getDictionary(cell
				.getStringCellValue(), evaluateCycle));
		cell = row.getCell(28);
		if (cell.getStringCellValue() != null
				&& !cell.getStringCellValue().equals("")) {
			try {
				carfile.setMaintainDate(df.parse(cell.getStringCellValue()));
			} catch (Exception e) {
			}// {log.info(e+":cell("+i+",28).getStringCellValue()="+cell.getStringCellValue());}
		}// else{log.info(":cell("+i+",28).getStringCellValue()==null");}
		cell = row.getCell(29);
		carfile.setMaintainCycle(DictionaryUtil.getDictionary(cell
				.getStringCellValue(), maintainCycle));
try{
		carfileService.addFromUpload(carfile);
}catch(Exception e){
		log.info("when save yunyingNo="+carfile.getYingyunNo()+","+carfile.getLicenseNumber()+","+carfile.getPaiSe().getName()+":"+e);
}
	}

	final boolean makeCarowner(HSSFRow row, Carfile carfile) {
		HSSFCell cell = null;
		cell = row.getCell(2);// 业主经营许可证号
		String ownerLicense = cell.getStringCellValue().trim();
		if (ownerLicense.equals("")){
			if(log.isInfoEnabled()){
				log.info("return for makeCarowner==true:yingyunNo="+ownerLicense+",column0:"+row.getCell(0));
			}
			return true;
		}
		Carowner carowner = carownerService.getByLicense(ownerLicense);
		if (carowner != null) {
			carfile.setCarowner(carowner);
		} else {
			cell = row.getCell(1);// 名称
			carfile.setOwner(cell.getStringCellValue().trim());
			carfile.setOwnerLicense(ownerLicense);
			cell = row.getCell(3);// 电话
			carfile.setTelephone(cell.getStringCellValue());
			cell = row.getCell(4);// 地址
			carfile.setAddress(cell.getStringCellValue().trim());
			try {
				carownerService.save(carfile.getCarowner());	
			} catch (Exception e) {
				// TODO: handle exception
				log.info("save carowner:"+e);
			}
			
		}
		return false;
	}

	public static void main(String[] args) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(df.parseObject("2010-08-06 00:00:00.0"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
}
