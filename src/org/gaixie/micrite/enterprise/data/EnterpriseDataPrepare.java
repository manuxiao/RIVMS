package org.gaixie.micrite.enterprise.data;

import org.gaixie.micrite.enterprise.service.IEnterpriseDataPrepareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.InitializingBean;

public class EnterpriseDataPrepare implements InitializingBean {
    
    @Autowired
    private IEnterpriseDataPrepareService enterpriseDataPrepareService;
    
    public void afterPropertiesSet() throws Exception {
    	System.out.println(enterpriseDataPrepareService);
        enterpriseDataPrepareService.initDataForRun();
    }

}