package org.gaixie.micrite.print;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.gaixie.micrite.action.GenericAction;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Check;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.car.service.ICarfileService;
import org.gaixie.micrite.check.service.ICheckService;
import org.gaixie.micrite.dic.service.IDictionaryService;
import org.gaixie.micrite.util.DictionaryUtil;
import org.springframework.beans.factory.annotation.Autowired;
/***配合打印*/
public class PrintAction extends GenericAction {
	/**等级评定打印最多条数*/
	public static final int DENGJI_ITEMS = 2;
	/**二级维护打印最多条数*/
	public static final int ERWEI_ITEMS = 8;
	private Logger log = Logger.getLogger(PrintAction.class);
	@Autowired
	private ICarfileService carService;
	@Autowired
	private IDictionaryService dictionaryService;
	@Autowired
	private ICheckService checkService;
	private int printIndex;
	
	public int getPrintIndex() {
		return printIndex;
	}
	public void setPrintIndex(int printIndex) {
		this.printIndex = printIndex;
	}
	private String action;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	private Integer id;
	private Carfile car;
	private int[] checkIds;
	private List<Check> checks;

	public List<Check> getChecks() {
		return checks;
	}
	public void setCheckIds(int[] checkIds) {
        this.checkIds = checkIds;
    }
	public String getIds() {
		if(checkIds==null)return "[]";
		String str = Arrays.toString(checkIds);
		return str;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public Carfile getCar() {
		return car;
	}
	private int withCarInfo;
	/**0不带car信息,1带car信息打印*/
	public static final int PRINT_CAR_INFO=1;
	public static final int NOT_CAR_INFO=0;
	public int getWithCarInfo() {
	return withCarInfo;
	}
	public void setWithCarInfo(int withCarInfo) {
		this.withCarInfo = withCarInfo;
	}
	/**第一面，基础数据以及技术评定数据**/
	public String print1() {
		if(action!=null){//认为是打印的回馈
			return doBackSetPrintIndex1();
		}
		
		if(withCarInfo==PRINT_CAR_INFO&&id==null)return INPUT;
		if(checkIds==null)return INPUT;
		
		checks=checkService.getChecks(checkIds);
		if(withCarInfo==PRINT_CAR_INFO)
			car=carService.get(id);
		
		return SUCCESS;
	}
	public static final String CONFIRMOK = "confirm";
	Map<String, Object> resultMap = new HashMap<String,Object>();
	public Map<String, Object> getResultMap() {
        return resultMap;
    }
	/**打印完毕之后
	 * 1,设置已打印的状态
	 * 2,打印指针要修改
	 * */
	private String setPrinted(){
		if(checkIds==null){
			this.getResultMap().put("message",getText("check.print.confirm.error.nocheckids"));
	        this.getResultMap().put("success", false);
		}else{
			checkService.setPrinted(checkIds);
			this.getResultMap().put("message","OK");
	        this.getResultMap().put("success", true);
		}
		return CONFIRMOK;
	}
	private String doBackSetPrintIndex1(){
		if(checkIds!=null){
			printIndex+=checkIds.length;
			printIndex%=DENGJI_ITEMS;
			carService.updatePrintIndex1(id,printIndex);
		}
		return setPrinted();
	}
	private String doBackSetPrintIndex2(){
		if(checkIds!=null){
			printIndex+=checkIds.length;
			printIndex%=ERWEI_ITEMS;
			carService.updatePrintIndex2(id,printIndex);
		}
		return setPrinted();
	}
	/**第二面，二级维护数据*/
	public String print2() {
		if(action!=null){//认为是打印的回馈
			return doBackSetPrintIndex2();
		}
		if(checkIds==null)return INPUT;
		checks=checkService.getChecks(checkIds);
		
		return SUCCESS;
	}
	
	public String getDengji(int id){
		return dictionaryService.getDictionaryName(DictionaryUtil.TYPE_CHEDENGJI, id);
	}
	public Date getCurrentDate(){
		return new Date(); 
	}
	/**处罚单*/
	public String printPaper() {
		if(id==null)return INPUT;
		car=carService.get(id);
		
		return SUCCESS;
	}
	/**
     * Convenience method to get the request
     * @return current request
     */
    protected HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    /**
     * Convenience method to get the response
     * @return current response
     */
    protected HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }
}
