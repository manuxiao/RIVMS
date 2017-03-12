package org.gaixie.micrite.check.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.Check;
import org.gaixie.micrite.car.service.ICarfileService;
import org.gaixie.micrite.check.service.ICheckService;
import org.gaixie.micrite.dic.dao.IDictionaryDAO;
import org.gaixie.micrite.dic.service.IDictionaryService;
import org.gaixie.micrite.upload.IDealWith;
import org.gaixie.micrite.util.DictionaryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dealWithCheck")
public class DealWithCheck implements IDealWith{
	@Autowired
	private IDictionaryService dictionaryService;
	@Autowired
	private ICheckService checkService;
	@Autowired
	private ICarfileService carfileService;	

	@Override
	public int doJob(File src,Map<String,String> res) throws Exception {
		// TODO Auto-generated method stub
		Properties p = new Properties();
    	p.load(new FileInputStream(src));
    	
    	Check check = new Check();
    	String testNoString = p.getProperty("test_no");//检测编号
    	int testNo = 0;
    	try {
    		testNo=Integer.parseInt(testNoString);//该字段可以为unique，则根据此字段去掉重复
		} catch (Exception e) {}
    	check.setTestNo(testNo);
    	String jianceDateString=p.getProperty("jian_time");//检测日期
    	String jianceDataFormat = res.get("jiance.date.format");
    	DateFormat df = new SimpleDateFormat(jianceDataFormat);
    	Date jianTime = null;
    	try {
			jianTime=df.parse((String) jianceDateString);
		} catch (Exception e) {}
    	check.setJianTime(jianTime);
    	
    	String hegezhengNo = p.getProperty("he_ge");//合格证编号
    	int hege = 0;
    	try{
    		hege=Integer.parseInt(hegezhengNo);
    	}catch(Exception e){}
    	check.setHeGe(hege);
    	String testKindString = p.getProperty("test_kind");//检测类型
    	int testKind = 0;
    	try{
    		testKind=Integer.parseInt(testKindString);
    	}catch(Exception e){testKind=ICheckService.TESTKIND_MAIN;}
    	check.setTestKind(testKind);
    	if(check.getTestKind()==ICheckService.TESTKIND_EVAL){
    		String dengji = p.getProperty("che_dengji");//等级评定	
    		if(dengji!=null)
    			dengji=new String(dengji.getBytes("iso-8859-1"),"gbk");
        	List<Dictionary>dengjis=dictionaryService.findALLDictionary(2);
        	check.setCheDengji(DictionaryUtil.getValue(dengji,dengjis));
    	}else{
    		check.setCheDengji(0);//二级维护的等级没有用处但该字段同样要置零
    	}
    	String paihao = p.getProperty("pai_hao");//车牌号码
    	if(paihao!=null)
    		paihao=new String(paihao.trim().getBytes("iso-8859-1"),"gbk");
    	check.setPaiHao(paihao);
    	String paiSe = p.getProperty("pai_she");//车牌颜色
    	if(paiSe!=null)
    		paiSe=new String(paiSe.trim().getBytes("iso-8859-1"),"gbk");
    	List<Dictionary>paises=dictionaryService.findALLDictionary(7);
    	check.setPaiSe(DictionaryUtil.getDictionary(paiSe,paises));
    	check.setCreateDate(new Date());//设置创建时间   
    	String temp = null;
    	temp=p.getProperty("che_lei");
    	if(temp!=null)
    		temp=new String(temp.trim().getBytes("iso-8859-1"),"gbk");
    	check.setCheLei(temp);//车辆类型
    	temp=p.getProperty("che_xiu");
    	if(temp!=null)
    		temp=new String(temp.trim().getBytes("iso-8859-1"),"gbk");
    	check.setCheXiu(temp);//车辆维修企业
    	temp=p.getProperty("che_zhu");
    	if(temp!=null)
    		temp=new String(temp.trim().getBytes("iso-8859-1"),"gbk");
    	check.setCheZhu(temp);//车主
    	//查到是哪个车子车辆 车牌号码用like 颜色用= 获得车辆id
    	String paiHao = check.getPaiHao();
    	if(paiHao!=null){
//    		if(paiHao.length()>5)paiHao=paiHao.substring(paiHao.length()-5,paiHao.length());
    		if(paiHao.length()==6)paiHao=paiHao.substring(0,1)+"."+paiHao.substring(1,6);
	    	int carId = carfileService.getCarId(paiHao, check.getPaiSe());
	    	check.setCarId(carId);
    	}
    	
    	checkService.add(check);//存入数据库
    	
    	return IDealWith.OK;
	}
}
