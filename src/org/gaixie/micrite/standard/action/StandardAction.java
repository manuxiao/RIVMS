package org.gaixie.micrite.standard.action;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.gaixie.micrite.action.GenericAction;
import org.gaixie.micrite.beans.Enterprise;
import org.gaixie.micrite.beans.Standard;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.enterprise.service.IEnterpriseService;
import org.gaixie.micrite.enterprise.service.impl.EnterpriseServiceImpl;
import org.gaixie.micrite.standard.service.IStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;

public class StandardAction extends GenericAction {
	private static final long serialVersionUID = 3072132220220662398L;

	@Autowired
	private IStandardService standardService;
	@Autowired
	private IEnterpriseService enterpriseService;

    private List<Enterprise> enterprises;
    //获取的页面参数
    private Standard standard;
    private int[] standardIds;
    private Date startDate;
	private Date endDate;
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
//    private int type;//0为入库，1为出库
    private String key;//企业搜索关键字，可以是拼音首字母，也可以是文字
    
//    private int numberStart;
//    private int numberEnd;    
//    private int sum;
//    private int remain;
//    private int pay;
//    private Date createDate;
//    private int state;
//    private int createrId;
    private Integer enterpriseId;
    
//    public int getPay() {
//		return pay;
//	}
//	public void setPay(int pay) {
//		this.pay = pay;
//	}
//	public Date getCreateDate() {
//		return createDate;
//	}
//	public void setCreateDate(Date createDate) {
//		this.createDate = createDate;
//	}
//	public int getState() {
//		return state;
//	}
//	public void setState(int state) {
//		this.state = state;
//	}
//	public int getCreaterId() {
//		return createrId;
//	}
//	public void setCreaterId(int createrId) {
//		this.createrId = createrId;
//	}
//	public Integer getEnterpriseId() {
//		return enterpriseId;
//	}
//	public void setEnterpriseId(Integer enterpriseId) {
//		this.enterpriseId = enterpriseId;
//	}
//	public int getSum() {
//		return sum;
//	}
//	public void setSum(int sum) {
//		this.sum = sum;
//	}
//	public int getRemain() {
//		return remain;
//	}
//	public void setRemain(int remain) {
//		this.remain = remain;
//	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	private Integer id;

    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	// ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~// 
	public String remainStatistics(){
		if(id==null){
			this.getResultMap().put("message", getText("param.error"));
	        this.getResultMap().put("success", false);
	        return SUCCESS;
		}
		Enterprise ent = enterpriseService.get(id);
		if(ent==null){
			this.getResultMap().put("message", getText("param.error"));
	        this.getResultMap().put("success", false);
	        return SUCCESS;
		}
		Object[] stat = standardService.remainStatistics(ent);
		this.getResultMap().put("message", getText("operating.success"));
        this.getResultMap().put("success", true);
        this.getResultMap().put("stat", stat);
        this.getResultMap().put("times", stat[0]);
        this.getResultMap().put("remain", stat[1]);
		return SUCCESS;
	}
    /**
     * 保存合格证信息
     * @return "success"
     */
    public String add() {
    	if(standard==null){
    		this.getResultMap().put("message", getText("param.error"));
	        this.getResultMap().put("success", false);
	        return SUCCESS;
    	}else if(standard.getNumberEnd()<standard.getNumberStart()){
    		this.getResultMap().put("message", getText("standard.add.not.success.star.bigger.than.end"));
	        this.getResultMap().put("success", false);
	        return SUCCESS;
    	}
    	
        int ret = -1;
        try {
			ret=standardService.add(standard);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.getResultMap().put("message", getText("some thing wrong"));
	        this.getResultMap().put("success", false);
	        return SUCCESS;
		}
        if(ret==0){
        	this.getResultMap().put("message", getText("save.success"));
	        this.getResultMap().put("success", true);
        }else{
        	this.getResultMap().put("message", getText("standard.add.not.success."+ret));
	        this.getResultMap().put("success", false);
        }
        return SUCCESS;
    }
    /**
     * 获取修改的合格证信息
     * @return "success"
     */
    public String update() {
    	if(standard==null){
    		this.getResultMap().put("message", getText("param.error"));
	        this.getResultMap().put("success", false);
	        return SUCCESS;
    	}else if(standard.getNumberEnd()<standard.getNumberStart()){
    		this.getResultMap().put("message", getText("standard.add.not.success.star.bigger.than.end"));
	        this.getResultMap().put("success", false);
	        return SUCCESS;
    	}
    	
//    	Standard standard = new Standard();
//    	standard.setId(id);
//    	standard.setNumberStart(numberStart);
//    	standard.setNumberEnd(numberEnd);
//    	standard.setEnterpriseId(enterpriseId);
        int ret = 0;
        	
        	try {
				ret = standardService.update(standard);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.getResultMap().put("message", getText("some thing wrong"));
		        this.getResultMap().put("success", false);
		        return SUCCESS;
			}
        if(ret==0){
        	this.getResultMap().put("message", getText("save.success"));
	        this.getResultMap().put("success", true);
        }else{
        	this.getResultMap().put("message", getText("standard.update.not.success."+ret));
	        this.getResultMap().put("success", false);
        }

        return SUCCESS;
        
    }
    /**
     * 高级查询合格证信息
     * @return "success"
     */
    public String advancedFind(){
        if (isFirstSearch()) {
            //  初次查询时，要从数据库中读取总记录数
//            Integer count = standardService.advancedFindCount(startDate,endDate,getQueryBean());
        	Integer count = standardService.advancedFindCount(startDate,endDate,enterpriseId);
            setTotalCount(count);
        }         
        //  得到分页查询结果 
        List<Standard> standards = standardService.advancedFindByPerPage(startDate,endDate,enterpriseId,this.getStart(), this.getLimit());
        this.putResultList(standards);
        return SUCCESS;
    }
    
    public String findStandardByQuery(){
        if (isFirstSearch()) {
            //  初次查询时，要从数据库中读取总记录数
        	Integer count = standardService.findStandardByQuery(getQueryBean());
            setTotalCount(count);
        }         
        //  得到分页查询结果 
        List<Standard> standards = standardService.findStandardByQueryPerPage(getQueryBean(),this.getStart(), this.getLimit());
        this.putResultList(standards);
        return SUCCESS;
    }
        
    
    /**
     * 删除合格证
     * @return "success"
     */
    public String delete(){
        standardService.delete(standardIds);
        this.getResultMap().put("message", getText("delete.success"));
        this.getResultMap().put("success", true);
        return SUCCESS;
        
    }
    /**
     * 编辑合格证
     * @return "success"
     */
    public String edit(){
        standardService.edit(standardIds);
        this.getResultMap().put("message", getText("edit.success"));
        this.getResultMap().put("success", true);
        return SUCCESS;
        
    }
    /**
     * 日期间隔及customerSourceType普通查询合格证 
     * @return "success"
     */
    public String findByDateSpacing(){
        if (isFirstSearch()) {
            //  初次查询时，要从数据库中读取总记录数
            Integer count = standardService.findByCreateDateSpacingCount(startDate, endDate,standard.getState());
            setTotalCount(count);
        }         
        //  得到分页查询结果
        List<Standard> standards = standardService.findByCreateDateSpacingPerPage(startDate, endDate, this.getStart(), this.getLimit(),standard.getState());
        this.putResultList(standards);
        return SUCCESS;
    }
    
    public String findByShorthand() {
    	if (isFirstSearch()) {
            //  初次查询时，要从数据库中读取总记录数
            Integer count = standardService.findByShorthandCount(key);
            setTotalCount(count);
        }
    	List<Standard> standards = standardService.findByShorthandPerPage(key, this.getStart(), this.getLimit());
        this.putResultList(standards);
        return SUCCESS;
    }

    /**
     * 获取库存的起始号码和结束号码
     * @return "success"
     */
//    public String getTwoNumbers() {
//    	numberStart = standardService.getTwoNumbers("start");
//    	numberEnd = standardService.getTwoNumbers("end");
//    	this.getResultMap().put("start", numberStart);
//    	this.getResultMap().put("end", numberEnd);
//    	return SUCCESS;
//    }
    // ~~~~~~~~~~~~~~~~~~~~~~~  Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
 
    public Standard getstandard() {
        return standard;
    }
    
    public int[] getStandardIds() {
        return standardIds;
    }
//    public int getType() {
//		return type;
//	}
//	public void setType(int type) {
//		this.type = type;
//	}
   
    public List<Enterprise> getEnterprise() {
        return enterprises;
    }
 
//    public String getPartner(){
//    	enterprises = standardService.findALLEnterprise(type);
//        return SUCCESS;
//    }
    
	public String getKey() {
		return key;
	}
	
//	public int getNumberStart() {
//		return numberStart;
//	}
	
//	public int getNumberEnd() {
//		return numberEnd;
//	}
//  
    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public void setStandardIds(int[] standardIds) {
        this.standardIds = standardIds;
    }
    
    public void setEnterprise(List<Enterprise> enterprises) {
        this.enterprises = enterprises;
    }
//    public void setEndDate(String endDate) throws ParseException {
//        this.endDate = DateUtils.parseDate(endDate + ":00", new String[]{"yyyy-MM-dd hh:mm:ss"});
//    }
//    public void setStartDate(String startDate) throws ParseException {
//        this.startDate = DateUtils.parseDate(startDate + ":00", new String[]{"yyyy-MM-dd hh:mm:ss"});
//    }
  public static void main(String[] args)throws Exception {
	  Date startDate = DateUtils.parseDate("2011-01-01" + "  23:59:59", new String[]{"yyyy-MM-dd hh:mm:ss"});
//	  Date startDate = DateUtils.parseDate("2011-01-01" + ":00",new String[] { "yyyy-MM-dd hh:mm:ss" });
	  System.out.println(startDate);
}
	public void setKey(String key) {
		this.key = key;
	}
	
//	public void setNumberStart(int numberStart) {
//		this.numberStart = numberStart;
//	}
//	
//	public void setNumberEnd(int numberEnd) {
//		this.numberEnd = numberEnd;
//	}
}
