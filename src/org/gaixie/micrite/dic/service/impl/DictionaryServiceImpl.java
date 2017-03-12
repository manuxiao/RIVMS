package org.gaixie.micrite.dic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Check;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.Enterprise;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.check.dao.ICheckDAO;
import org.gaixie.micrite.check.service.ICheckService;
import org.gaixie.micrite.dic.dao.IDictionaryDAO;
import org.gaixie.micrite.dic.service.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("dictionaryService")
public class DictionaryServiceImpl implements IDictionaryService {
	@Autowired
	private ICheckDAO checkDao;
	@Autowired
	private IDictionaryDAO dictionaryDAO; 
	@Override
	public List<Dictionary> findALLDictionary(int type) {
		List<Dictionary> carType = dictionaryDAO.findAllDictionary(type);
        return carType;
    }
	@Override
	public List<Dictionary> findALLDictionary(int type,int show) {
		List<Dictionary> carType = dictionaryDAO.findAllDictionary(type,show);
        return carType;
    }

	@Override
	public String getDictionaryName(int type, int value) {
		// TODO Auto-generated method stub
		List<Dictionary> list = findALLDictionary(type);
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getValue()==value)return list.get(i).getName();
		}
		return "未选";
	}

	@Override
	public Dictionary get(Integer id) {
		// TODO Auto-generated method stub
		return dictionaryDAO.get(id);
	}
	@Override
	public Dictionary get(String name,int type) {
		// TODO Auto-generated method stub
		return dictionaryDAO.get(name,type);
	}
	@Override
	public void updateValue(Dictionary d) {
		Integer id = d.getId();
		if(id==null)return ;
		Dictionary dic = dictionaryDAO.get(id);
		if(dic==null)return ;
		if(d.getValue()!=dic.getValue()){
			checkDao.save(__getSysOptHistory(id,Dictionary.VALUE_NO,dic.getName(),dic.getValue(),d.getValue()));
			dic.setValue(d.getValue());
			dictionaryDAO.update(dic);
		}
	}
	private Check __getSysOptHistory(int id,int col,String paramName,int origValue,int newValue){
		Check c = new Check();
		c.setCheZhu(paramName);
		c.setIsPost(ICheckService.RT_SYS);
		c.setTestKind(col);
		c.setStatus(id);
		c.setNotepad(origValue+"->"+newValue);
		c.setCreateDate(new Date());//这个是解除的时间
		User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		c.setCreaterId(currentUser.getId());
		return c;
	}
	@Override
	public void updateNameShow(Dictionary d) {
		boolean modified = false;
		Dictionary dic = dictionaryDAO.get(d.getId());
		if(dic==null)return ;
		if(d.getName()!=null&&!d.getName().trim().equals(dic.getName())){
			dic.setName(d.getName());
//			checkDao.save(__getCarOptHistory(carId, Dictionary.CARREMARK_NO, carfile.getCarRemark(),c.getCarRemark()));
			modified=true;
		}
		if(d.getShow()!=dic.getShow()){
			dic.setShow(d.getShow());
			modified=true;
		}
		if(modified)
			dictionaryDAO.update(dic);
	}
	@Override
	public void addItem(String name, int type, int show) {
		// TODO Auto-generated method stub
		dictionaryDAO.addItem(name, type, show);
	}
public static List<Dictionary> list = new ArrayList<Dictionary>();
static {
	list.add(new Dictionary("车辆类型",Carfile.CARTYPE_TYPE));
	list.add(new Dictionary("技术等级",Carfile.SKILLRANK_TYPE));
	list.add(new Dictionary("车牌类型",Carfile.LICENSETYPE_TYPE));
	list.add(new Dictionary("燃料类型",Carfile.FUELRANK_TYPE));
	list.add(new Dictionary("车牌颜色",Carfile.PAISE_TYPE));
	list.add(new Dictionary("经营资质",Enterprise.QUALIFICATION_TYPE));
	list.add(new Dictionary("经济性质",Enterprise.KIND_TYPE));
	list.add(new Dictionary("经营状态",Enterprise.WORKTYPE_TYPE));
	list.add(new Dictionary("管辖站",Enterprise.STATION_TYPE));
}
}
