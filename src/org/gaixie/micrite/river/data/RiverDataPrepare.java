package org.gaixie.micrite.river.data;

import org.gaixie.micrite.river.service.IRiverDataPrepareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.InitializingBean;

public class RiverDataPrepare implements InitializingBean {
    
    @Autowired
    private IRiverDataPrepareService riverDataPrepareService;
    
    public void afterPropertiesSet() throws Exception {
    	System.out.println(riverDataPrepareService);
        riverDataPrepareService.initDataForRun();
    }

}