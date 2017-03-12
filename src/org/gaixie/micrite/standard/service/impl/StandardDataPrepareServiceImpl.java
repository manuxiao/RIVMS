package org.gaixie.micrite.standard.service.impl;

import java.util.Date;

import org.gaixie.micrite.beans.Enterprise;
import org.gaixie.micrite.beans.Standard;
import org.gaixie.micrite.crm.dao.ICarownerDAO;
import org.gaixie.micrite.enterprise.dao.IEnterpriseDAO;
import org.gaixie.micrite.standard.dao.IStandardDAO;
import org.gaixie.micrite.standard.service.IStandardDataPrepareService;
import org.springframework.beans.factory.annotation.Autowired;

public class StandardDataPrepareServiceImpl implements IStandardDataPrepareService{
	@Autowired
	private IStandardDAO standardDAO;
	
	@Autowired
    private IEnterpriseDAO enterpriseDAO;
	public void initDataForRun() {
//		
//		if(standardDAO.get(2) == null) {
//			Enterprise enterprise = enterpriseDAO.get(4);
//		//	Enterprise enterprise = new Enterprise();
//		//	enterpriseDAO.save(enterprise);
//			Standard standard = new Standard(enterprise);
//			standard.setId(2);
//			standard.setWaijian1(enterprise.getId());
//			standardDAO.save(standard);
//		}
	}
}
