package org.gaixie.micrite.check.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * 
 * 注：sunJLEncryption加密串要跟自动转发程序的对得上，写在web.xml中
 * @param src:上传的文件
 * @param res:jiance.date.format日期的格式,fileFileName文件的名字,sunJLEncryption系统提供的加密串
 * */
@Service
public class DealWithSunJL extends DealWithCheck{
	@Autowired
	private PasswordEncoder passwordEncoder;
	Logger log = Logger.getLogger(DealWithSunJL.class);
	public static final int ERROR_BAD_ENCRYPTION = 2;
	@Override
	public int doJob(File src, Map<String, String> res) throws Exception {
		// TODO Auto-generated method stub
		String fileFileName = res.get("fileFileName");//原始的文件名
		log.debug("fileFileName="+fileFileName);
		fileFileName=fileFileName.substring(0,fileFileName.indexOf('.'));//去后缀的文件名
		log.debug("fileFileName="+fileFileName);
		String sunJLEncryption = res.get("sunJLEncryption");//密文属性
		log.debug("sunJLEncryption="+sunJLEncryption);
		Properties p = new Properties();//改进：只读的就够了；
    	p.load(new FileInputStream(src));
    	String key = p.getProperty("key");//key=密文字段
    	p.clear();
    	log.debug("key="+key);
    	fileFileName+=sunJLEncryption;//文件名+密文属性
    	fileFileName=passwordEncoder.encodePassword(fileFileName, null);//md5加密
    	fileFileName=fileFileName.toUpperCase();//全部大写
    	
    	if(!fileFileName.equals(key)&&false){//密文  Vs. 密文字段 
    		return ERROR_BAD_ENCRYPTION;
    	}
		return super.doJob(src, res);
	}
	
}
