
package org.gaixie.micrite.standard.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.enterprise.dao.IEnterpriseDAO;
import org.gaixie.micrite.enterprise.service.IEnterpriseService;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Enterprise;
import org.gaixie.micrite.beans.Standard;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.standard.action.StandardAction;
import org.gaixie.micrite.standard.dao.IStandardDAO;
import org.gaixie.micrite.standard.service.IStandardService;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;

public class StandardServiceImpl implements IStandardService { 
	
    @Override
	public Object[] remainStatistics(Enterprise ent) {
		// TODO Auto-generated method stub
		return standardDAO.remainStatistics(ent);
	}

	@Override
	public void minus(Integer standardNo) {
		// TODO Auto-generated method stub
		if(standardNo==null)return;
		Standard s = standardDAO.getByStandardNo(standardNo);
		if(s==null)return ;
        s.setRemain(s.getRemain()-1);
        s.setPay(s.getPay()+1);
        standardDAO.update(s);
	}
    
	@Override
	public boolean isExist(Integer standarNo) {
		// TODO Auto-generated method stub
		return standardDAO.isExist(standarNo);
	}
	public Standard findByStandardNo(Integer standarNo){
		return standardDAO.findByStandardNo(standarNo);
	}
	@Autowired
    private IStandardDAO standardDAO;
	@Autowired
	private IEnterpriseDAO enterpriseDAO;
	
    private List<Standard> standards;
    private Standard standardFst;
	@Deprecated
    public int add_(Standard standard) {
    	if(standard.getType() == 0) {
    		standards = standardDAO.findByType(0);
    		Iterator<Standard> iter = standards.iterator();
    		if(iter.hasNext()) {
    			standardFst = iter.next();
    			if(standardFst.getRemain()>0) {//入库新增号码必须大于库存中的所有号码
    				if(standardFst.getNumberEnd()-standard.getNumberStart()>0||standardFst.getNumberEnd()-standard.getNumberEnd()>0){
    	    			return 2;
    	    		}
    			}    			
    		}
    	}    	
    	if(standard.getType() != 0) {
    		standards = standardDAO.findByType(0);
    		Iterator<Standard> iter = standards.iterator();
    		if(iter.hasNext()) {
    			standardFst = iter.next();
    			if(standardFst.getRemain()>0) {
    				if(standardFst.getRemain()-standard.getSum()<0){
    					//发放单位库存无法满足发放要求
    	    			return 1;
    	    		}
    				standardFst.setPay(standardFst.getPay()+standard.getSum());
    				standardFst.setRemain(standardFst.getRemain()-standard.getSum());
    	    		standardDAO.update(standardFst);
    	    		standard.setRemain(standard.getSum());
    	    		standard.setPay(0);
    	    		standardDAO.save(standard);
    	    		return 0;
    			}
    		}
    		return 1;
    	}
    	standard.setRemain(standard.getSum());
		standard.setPay(0);
		standardDAO.save(standard);
		return 0;
    }
	@Deprecated
    public int update_(Standard s) {
    	Standard standard = standardDAO.get(s.getId());
    	if(standard.getType() != 0) {
        	standards = standardDAO.findByType(0);
    		standardFst = standards.get(0);
    		
    		Integer s1 = standardFst.getRemain()-s.getSum();
    		Integer s2 = standardFst.getNumberEnd()-s.getNumberEnd();
    		Integer s3 = s.getNumberStart()-(standard.getNumberEnd()-standard.getPay());
    		if(s1<0){//发放单位库存不够
    			return 1;
    		}   
    		if(s2<0){// 发放单位库存结束号码 小于 入库结束号码
    			return 2;
    		}  
    		if(s3<0){//入库起始号码小于   库存核销后的起始号码
    			return 3; 
    		}  
    		standardFst.setRemain(standardFst.getRemain()-s.getSum()+standard.getSum());
    		standardFst.setPay(standardFst.getSum()-standardFst.getRemain());
    		standardDAO.update(standardFst);
        }
    	standard.setEnterprise(s.getEnterprise());
    	standard.setNumberStart(s.getNumberStart());
    	standard.setNumberEnd(s.getNumberEnd());
    	standard.setSum(s.getSum());
    	standard.setPay(s.getPay());
    	standard.setRemain(s.getRemain());
        standardDAO.update(standard);
        return 0;
    }
    
    @Override
	public boolean existWhenAddIn(int sNo) {
		// TODO Auto-generated method stub
		return standardDAO.existWhenAddIn(sNo);
	}

	/**合格证入库*/
    private int _addIn(Standard s)throws Exception{
    	if(existWhenAddIn(s.getNumberStart())||existWhenAddIn(s.getNumberEnd())){
    		return 4;//跟以前的入库有重复
    	}
    	if(s.getNumberEnd()<s.getNumberStart()){
    		int sw = s.getNumberEnd();
    		s.setNumberEnd(s.getNumberStart());
    		s.setNumberStart(sw);
    	}
    	Integer freeId = standardDAO.getFreeId();
    	s.setType(this.TYPE_CHEGUANSUO);s.setState(this.STATUS_NORMAL);
    	s.setPreSet(null);s.setNextSet(null);s.setFather(null);
    	_setNumberFill(s);
    	s.setId(freeId);
    	s.setEnterpriseId(IEnterpriseService.GONGGUANSUO_ID);
    	standardDAO.save(s);
    	
    	Standard ss = new Standard();
		BeanUtils.copyProperties(ss, s);
	
		ss.setFather(s);
		ss.setType(this.TYPE_FREE);
    	ss.setId(s.getId()+1);
    	standardDAO.save(ss);
    	
    	return 0;
    }
    /**领取必须基于某个可用区间
     * */
    public int add(Standard s) throws Exception{
    	if(s.getType()==this.TYPE_CHEGUANSUO){//这是入库
    		s.setEnterpriseId(IEnterpriseService.SYS_ENT_ID);
    		return _addIn(s);
    	}
    	if(s.getEnterpriseId()==IEnterpriseService.SYS_ENT_ID){
    		return 3;//无该领用企业
    	}
    	Enterprise ent = enterpriseDAO.get(s.getEnterpriseId());
    	if(ent==null){
    		return 3;//无该领用企业
    	}
    	if(s.getFather()==null||s.getFather().getId()==null){
    		return 5;//引用不明
    	}
    	Standard by = standardDAO.get(s.getFather().getId());//father里临时放的是所引用的记录id;
    	if(by==null||by.getType()!=this.TYPE_FREE){//被引用的必须是存在且待分配的空余库存段
    		return 5;//引用不明
    	}
    	if(s.getNumberStart()<by.getNumberStart()){
    		return 1;//超出引用范围-开始号过小
    	}
    	if(s.getNumberEnd()>by.getNumberEnd()){
    		return 2;//超出引用范围-结束号过大
    	}
    	
    	//下面是发放给企业、即企业领取
    	if(by.getNumberEnd()==s.getNumberEnd()&&by.getNumberStart()==s.getNumberStart()){//全部领走
    		by.setType(this.TYPE_ENTERPRISE);//空闲的改为领用的
    		by.setEnterpriseId(s.getEnterpriseId());//谁领用
    		_setCreateInfo(by);//相当于新生成的领用记录
    		standardDAO.update(by);//实际上没有新生成记录而是更新
    		_setNumberPay(by.getFather(), by.getSum());//修改入库记录的剩余量等
    		standardDAO.update(by.getFather());
    	}else{
//    		s.setId(standardDAO.getFreeId());
    		s.setState(IStandardService.STATUS_NORMAL);
    		s.setType(this.TYPE_ENTERPRISE);    	
        	s.setFather(by.getFather());
        	s.setPay(0);
        	_setNumberFill(s);
        	standardDAO.save(s);//新增领走部分
        	
        	_aDispatch(by,s);
        	_bSave(by,s);
    	}
    	return 0;//成功
    }
    /**可以领取的三种情况
     * @param s
     * */
    private final void _aDispatch(Standard by, Standard s){

		if(by.getNumberEnd()!=s.getNumberEnd()&&by.getNumberStart()!=s.getNumberStart()){//剩两头取中间：by-s-endFree
    		Standard endFree = new Standard();
//    		endFree.setId(standardDAO.getFreeId());//领走和尾剩余都是新增记录
    		endFree.setState(IStandardService.STATUS_NORMAL);
    		endFree.setType(this.TYPE_FREE);    	
    		endFree.setFather(by.getFather());
    		endFree.setNumberStart(s.getNumberEnd()+1);
    		endFree.setNumberEnd(by.getNumberEnd());
    		endFree.setPay(0);
        	_setNumberFill(endFree);
        	endFree.setPreSet(s);
    		endFree.setNextSet(by.getNextSet());
        	standardDAO.save(endFree);//新增尾剩余部分
        	
        	if(by.getNextSet()!=null){
    			by.getNextSet().setPreSet(endFree);//by原来下一个元素的向前指针指向endFree
    			standardDAO.update(by.getNextSet());
    		}
        	
    		by.setNextSet(s);s.setPreSet(by);s.setNextSet(endFree);
    		by.setNumberEnd(s.getNumberStart()-1);//计算首尾两条剩余记录的区间值
    	}else if(by.getNumberStart()!=s.getNumberStart()&&by.getNumberEnd()==s.getNumberEnd()){//剩前取后：by-s
    		s.setNextSet(by.getNextSet());s.setPreSet(by);
    		if(by.getNextSet()!=null){
    			by.getNextSet().setPreSet(s);//by原来下一个元素的向前指针指向s
    			standardDAO.update(by.getNextSet());	
    		}
    		by.setNextSet(s);
    		by.setNumberEnd(s.getNumberStart()-1);
    		
    	}else if(by.getNumberStart()==s.getNumberStart()&&by.getNumberEnd()!=s.getNumberEnd()){//取前剩后：s-by
    		s.setPreSet(by.getPreSet());s.setNextSet(by);
    		if(by.getPreSet()!=null){
    			by.getPreSet().setNextSet(s);//by原来的前一个元素的向后指针指向s
    			standardDAO.update(by.getPreSet());
    		}
    		by.setPreSet(s);
    		by.setNumberStart(s.getNumberEnd()+1);
    		
    	}
    }
    /**正常领取之后的保存*/
    private final void _bSave(Standard by, Standard s){
    	_setNumberFill(by);
    	standardDAO.update(by);//所参照的空余库存片段-变为剩余的一头
		standardDAO.update(s);
    	_setNumberPay(by.getFather(), s.getSum());//修改入库记录的剩余量等
		standardDAO.update(by.getFather());
    }
    /**id不能无效，即不为空，且存在
     * 缺少：日志、如果有部分已经使用的还需检查、修改后已经使用几个合格证的信息被抹掉了；入库记录中有多少被领走的信息没有更新
     * */
    public int update(Standard s) throws Exception{//id必须有效、
    	Standard by = standardDAO.get(s.getId());
    	if(by.getType()==TYPE_CHEGUANSUO){//如果修改入库，仅仅简单修改开始结束两个编号；未做校验；
    		by.setNumberEnd(s.getNumberEnd());by.setNumberStart(s.getNumberStart());
    		_setNumberFill(by);
    		standardDAO.update(by);
    		return 0;
    	}else if(by.getType()!=TYPE_ENTERPRISE){//这就是空闲的片段，不允许用户直接修改
    		return 4;
    	}
    	if(s.getEnterpriseId()<=0){
    		return 5;
    	}
    	Enterprise ent = enterpriseDAO.get(s.getEnterpriseId());
    	if(ent==null){
    		return 5;
    	}
    	if(by.getEnterpriseId()!=s.getEnterpriseId()){//修改了领取企业
			by.setEnterpriseId(s.getEnterpriseId());
			standardDAO.update(by);
		}
    	
    	if(by.getNumberEnd()==s.getNumberEnd()&&by.getNumberStart()==s.getNumberStart()){//始末号有变化
    		return 0;
    	}

    		Standard pre = by.getPreSet(); 
    		Standard next = by.getNextSet();
    		if((by.getNumberStart()<s.getNumberStart()||by.getNumberEnd()>s.getNumberEnd())&&by.getRemain()==0){
    			return 1;//用完不能缩小
   		 	}
    		 if(by.getNumberStart()>s.getNumberStart()&&(pre==null||pre.getType()!=this.TYPE_FREE||pre.getNumberStart()>s.getNumberStart())){//开始号跑到前面去了，即开始号往前移动了
    			 return 2;//往前扩展但前一个区间不存在或已领走或不够 
    		 }
    		 
			 if(by.getNumberEnd()<s.getNumberEnd()&&(next==null||next.getType()!=this.TYPE_FREE||next.getNumberEnd()<s.getNumberEnd())){//结束号跑到后面去了，即结束号往后移动了
				 return 3;//往后扩展但后一个区间不存在或已领走或不够
			 }
			 
			 //下面总是可以修改的
//			 if(c.getBrandType()!=null&&!c.getBrandType().trim().equals(car.getBrandType())){
//	        	checkDao.save(__getCarOptHistory(car, Carfile.BRANDTYPE_NO, car.getBrandType(),c.getBrandType()));
//	        	car.setBrandType(c.getBrandType().trim());isEdited=true;
//	         }	 
			 //by即为原本领走的记录
			 _setNumberPay(by.getFather(), -by.getSum());//还回入库记录
			 standardDAO.update(by.getFather());
			 
			 s.setPay(by.getPay());//如果是用过的，用过几个要保留
			 _setNumberFill(s);//设置

			//如有需要则合并前后
			if(next!=null&&next.getType()==TYPE_FREE){
				 //仅后继未分配、需要与后继合并
				 by.setNextSet(next.getNextSet());
				 by.setNumberEnd(by.getNumberEnd()+next.getSum());
				 _setNumberSum(by, next.getSum());//还回
				 if(next.getNextSet()!=null){
					 next.getNextSet().setPreSet(by);
					 standardDAO.update(next.getNextSet());
				 }
//				 next.setState(this.STATUS_DELETED);
//				 standardDAO.update(next);
				 standardDAO.delete(next);
			 }
			if(pre!=null&&pre.getType()==TYPE_FREE){
				 //前段未分配、需要与前段合并
				 by.setPreSet(pre.getPreSet());
				 by.setNumberStart(by.getNumberStart()+pre.getSum());
				 _setNumberSum(by, pre.getSum());//还回
				 if(pre.getPreSet()!=null){
					 pre.getPreSet().setNextSet(by);
				 }
//				 pre.setState(this.STATUS_DELETED);
//				 standardDAO.update(pre);
				 standardDAO.delete(pre);
			 }
			
			 //重新变为未用状态
			 by.setPay(0);
			 by.setRemain(by.getSum());
			 by.setType(TYPE_FREE);
			 by.setEnterpriseId(IEnterpriseService.SYS_ENT_ID);
			 standardDAO.update(by);
			 
			 //重新分配
			 _aDispatch(by,s);
			 s.setId(null);//抹掉原来的id
			 s.setFather(by.getFather());
			 standardDAO.save(s);//获取一个新的id
			 _bSave(by,s);
			 
			 return 0;
    }
    
    private void _setCreateInfo(Standard by){
    	by.setCreateDate(new Date());
		by.setCreaterId(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }
    //根据起止号重新计算总数、剩余
    private void _setNumberFill(Standard s){
    	s.setSum(s.getNumberEnd()-s.getNumberStart()+1);
    	s.setRemain(s.getSum()-s.getPay());
    }
    //指定领走数、自动计算剩余数
    private void _setNumberPay(Standard s,int offset){//offset可以为负数，表示还回
    	s.setPay(s.getPay()+offset);
    	s.setRemain(s.getRemain()-offset);
    }
    //指定总数变化、自动计算剩余数
    private void _setNumberSum(Standard s,int offset){//offset可以为负数，表示还回
    	s.setSum(s.getSum()+offset);
    	s.setRemain(s.getRemain()+offset);
    }
    /**修改备份，更新企业时 有错，发放单位 合格证1张当10张更新，而企业没更新
    public void update(Standard s) {
    	Standard standard = standardDAO.get(s.getId());
    	if(standard.getType() != 0) {
        	standards = standardDAO.findByType(0);
    		standardFst = standards.get(0);
    		standardFst.setRemain(standardFst.getRemain()-s.getSum()+standard.getSum());
    		standardFst.setPay(standardFst.getSum()-standardFst.getRemain());
    		standardDAO.update(standardFst);
        }
    	standard.setEnterprise(s.getEnterprise());
    	standard.setNumberStart(s.getNumberStart());
    	standard.setNumberEnd(s.getNumberEnd());
    	standard.setSum(s.getSum());
    	standard.setEnterpriseId(s.getEnterpriseId());
        standardDAO.update(standard);
    } 
    */
    
    public List<Standard> findStandardByQueryPerPage(SearchBean[] queryBean, int start,
            int limit){
        List<Standard> list = standardDAO.findStandardByQueryPerPage(queryBean,start,limit);
        return list;
    }
    public int findStandardByQuery(SearchBean[] queryBean){
        return standardDAO.findStandardByQuery(queryBean); 
    }
    
    public List<Standard> advancedFindByPerPage(Date startDate,Date endDate,Integer enterpriseId, int start,
            int limit){
        List<Standard> list = standardDAO.advancedFindByPerPage(startDate,endDate,enterpriseId,start,limit);
        return list;
    }
    public int advancedFindCount(Date startDate,Date endDate,Integer enterpriseId){
        return standardDAO.advancedFindCount(startDate,endDate,enterpriseId); 
    }
    
    
    public void delete(int[] standardIds) {
        for (int i = 0; i < standardIds.length; i++) { 
            Standard standard = standardDAO.get(standardIds[i]);
            if(standard.getType() != 0) {
            	standards = standardDAO.findByType(0);
        		standardFst = standards.get(standards.size()-1);
        		standardFst.setRemain(standardFst.getRemain()+standard.getSum());
        		standardFst.setPay(standardFst.getSum()-standardFst.getRemain());
        		standardDAO.update(standardFst);
            }
            standard.setState(1);
            standardDAO.update(standard);
        }
    }
    
    public void edit(int[] standardIds) {
        for (int i = 0; i < standardIds.length; i++) { 
        	Standard standard = standardDAO.get(standardIds[i]);
            standardDAO.save(standard);
        }
    }

    public List<Enterprise> findALLEnterprise(int type) {
        List<Enterprise> enterprise = standardDAO.findAllEnterprise(type);
        return enterprise;
    }

    public int findByCreateDateSpacingCount(Date startDate, Date endDate,int standardState) {
    	return standardDAO.findByCreateDateSpacingCount(startDate, endDate,standardState);
    }

    public List<Standard> findByCreateDateSpacingPerPage(Date startDate,
            Date endDate, int start, int limit,int standardState) {
        List<Standard> list = standardDAO.findByCreateDateSpacingPerPage(startDate, endDate, start, limit,standardState);
        return list;
    }
    public CategoryDataset getEnterpriseBarDataset(SearchBean[] queryBean){
        List list = standardDAO.findCSGroupByTelVague(queryBean);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                Object[] obj = (Object[]) list.get(i);
                dataset.setValue(Integer.parseInt(obj[0].toString()),obj[1].toString(),"");
            }
        } else {
            return null;
        }
        return dataset;
    }
    public PieDataset getEnterprisePieDataset(SearchBean[] queryBean){
        List list = standardDAO.findCSGroupByTelVague(queryBean);
        DefaultPieDataset dataset = new DefaultPieDataset();
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                Object[] obj = (Object[]) list.get(i);
                dataset.setValue( obj[1].toString(),Integer.parseInt(obj[0].toString()));
            }
        } else {
            return null;
        }
        return dataset;
        
    }
    
    public List<Standard> findByType(int type) {
    	return standardDAO.findByType(type);
    }

	@Override
	public int findByShorthandCount(String key) {
		return standardDAO.findByShorthandCount(key);
	}

	@Override
	public List<Standard> findByShorthandPerPage(String key, int start, int limit) {
		return standardDAO.findByShorthandPerPage(key, start, limit);
	}

//	@Override
//	public int getTwoNumbers(String differ) {
//		return standardDAO.getTwoNumbers(differ);
//	}
	
	
    public List<Standard> findStandardSinglePerPage(SearchBean[] queryBean, int start,
            int limit){
        List<Standard> list = standardDAO.findStandardSinglePerPage(queryBean,start,limit);
        return list;
    }
    public int findStandardSingle(SearchBean[] queryBean){
        return standardDAO.findStandardSingle(queryBean); 
    }
}
