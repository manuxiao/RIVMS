package org.gaixie.micrite.dic.action;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.gaixie.micrite.action.GenericAction;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.dic.service.IDictionaryService;
import org.gaixie.micrite.dic.service.impl.DictionaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class DictionaryAction  extends GenericAction{
	Logger log = Logger.getLogger(DictionaryAction.class);
	
	@Autowired
	private IDictionaryService dictionaryService;
	private Integer days;
	public void setDays(Integer days) {
		this.days = days;
	}
	private Dictionary d;
	public Dictionary getD() {
		return this.d;
	}
	public void setD(Dictionary d) {
		this.d = d;
	}

	/**
	 * 设置二次维护允许超期天数
	 * */
	public String erweiExpirePermissionDays(){
		if(days!=null&&days>=0){//this is set
			Dictionary dic = dictionaryService.get(IDictionaryService.EXPIRED_PERMISSION_ID);
			if(dic==null){
				getResultMap().put("message", getText("cannot.found.dictionary"));
				getResultMap().put("success", false);
				return SUCCESS;
			}else{
				Dictionary dic0=new Dictionary();
				dic0.setValue(days);
				dic0.setId(dic.getId());
				dictionaryService.updateValue(dic0);
				getResultMap().put("data", days);
				getResultMap().put("message", getText("save.success"));
				getResultMap().put("success", true);
				return SUCCESS;
			}
		}else{//this is get
			Dictionary dic = dictionaryService.get(IDictionaryService.EXPIRED_PERMISSION_ID);
			if(dic==null){
				log.debug("has not this car.");
				getResultMap().put("message", getText("cannot.found.dictionary"));
				getResultMap().put("success", false);
				return SUCCESS;
			}else{
				log.debug("has is car.");
				getResultMap().put("data", dic.getValue());
				getResultMap().put("message", getText("operating.success"));
				getResultMap().put("success", true);
				return SUCCESS;
			}
		}
	}
    private int type;
    
    public void setType(int type) {
		this.type = type;
	}
    
	public int getType() {
		return type;
	}

	private List<Dictionary> dictionaries ;

	public List<Dictionary> getDictionaries() {
		return dictionaries;
	}
	/**
     * 获得用户来源
     * @return "success"
     */
    public String getPartner(){
    	dictionaries = dictionaryService.findALLDictionary(type,IDictionaryService.SHOW_YES);
        return SUCCESS;
    }
    /**
     * 获得用户(含ALL)
     * @return "success"
     */
//    public String getPartnerByAll(){
//    	dictionaries = dictionaryService.findALLDictionary(type,IDictionaryService.SHOW_YES);
//        //在结果集中添加ALL
//    	Dictionary tempSource = new Dictionary();
//        tempSource.setId(-1);
//        tempSource.setName("全部");
//        tempSource.setValue(-1);
//        dictionaries.add(0,tempSource);
//        //按ID排序，把ALL放到List的最前面，用于下拉框显示时ALL在最上面
//        try {
//            Collections.sort(dictionaries, new Comparator<Dictionary>(){
//            	public int compare(Dictionary arg0, Dictionary arg1){
//                    return arg0.getId().compareTo(arg1.getId());
//                }
//            });//未执行此函数
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}

//        return SUCCESS;
//    }
    final private boolean isParamsError(){
    	if(d==null||d.getName()==null||d.getName().trim().equals("")){
			return true;
		}
    	for (int i = 0; i < DictionaryServiceImpl.list.size(); i++) {
			if(DictionaryServiceImpl.list.get(i).getType()==d.getType())return false;
		}
    	return true;
    }
	/**修改下拉列表中的元素的名称或者是否显示标识*/
	public String edit(){
		if(d==null||d.getId()==null||dictionaryService.get(d.getId())==null){
			getResultMap().put("message", getText("param.error"));
			getResultMap().put("success", false);
			return SUCCESS;
		}
		Dictionary dic = dictionaryService.get(d.getId());
		if(dic==null){
			getResultMap().put("message", getText("param.error"));
			getResultMap().put("success", false);
			return SUCCESS;
		}
		d.setType(dic.getType());
		if (isParamsError()) {
			getResultMap().put("message", getText("param.error"));
			getResultMap().put("success", false);
			return SUCCESS;
		}
		
		try {
			dictionaryService.updateNameShow(d);
		} catch (Exception e) {
			// TODO: handle exception
			
			
			getResultMap().put("message", getText("param.error"));
			getResultMap().put("success", false);
			return SUCCESS;
		}
		
		getResultMap().put("message", getText("save.success"));
		getResultMap().put("success", true);
		return this.SUCCESS;
	}
	/**新增下拉列表中的元素，用户提供名称和是否显示标识，值为自动生成；不提供删除功能*/
	public String add(){
		
		if (isParamsError()) {
			getResultMap().put("message", getText("param.error"));
			getResultMap().put("success", false);
			return SUCCESS;
		}
		if(dictionaryService.get(d.getName(),d.getType())!=null){
			getResultMap().put("message", getText("dictionary.save.error.name.same"));
			getResultMap().put("success", false);
			return SUCCESS;
		}
		dictionaryService.addItem(d.getName(), d.getType(), d.getShow());
		getResultMap().put("message", getText("save.success"));
		getResultMap().put("success", true);
		return this.SUCCESS;
	}
	/**获取可修改的下拉列表元素*/
	public String getListItems(){
		System.out.println(d);
		if(d==null||d.getType()==0){
			dictionaries=DictionaryServiceImpl.list;
			return this.SUCCESS;
		}else{
			dictionaries=dictionaryService.findALLDictionary(d.getType());
			System.out.println(dictionaries);
			return this.SUCCESS;
		}
	}
}
