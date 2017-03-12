package org.gaixie.micrite.dic.service;

import java.util.ArrayList;
import java.util.List;

import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Check;
import org.gaixie.micrite.beans.Dictionary;


public interface IDictionaryService {
	
	/**二次维护允许超时天数的id*/
	public static final int EXPIRED_PERMISSION_ID = 111;
//	/**系统用户id
//	 * 用于数据导入、定时计划执行时
//	 * */
//	public static final int SYS_USER_ID = 0;
	/**显示*/
	public static final int SHOW_YES = 0;
	/**不显示*/
	public static final int SHOW_NOT = 1;
    /**
     * 获取字典列表
     * @return 字典列表
     */
	public List<Dictionary> findALLDictionary(int type);
	public List<Dictionary> findALLDictionary(int type,int show);
    /**
     * 根据类型和值获取记录名称
     * */
    public String getDictionaryName(int type,int value);
	/**
	 * 根据id获取对象
	 * */
    public Dictionary get(Integer id);
    public Dictionary get(String name,int type);
    /**
     * 修改对象之后保存对象
     * */
    public void updateValue(Dictionary d);
    public void updateNameShow(Dictionary d);
    public void addItem(String name,int type,int show);
}
