package org.gaixie.micrite.check.data;

import org.gaixie.micrite.check.service.ICheckDataPrepareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.InitializingBean;

public class CheckDataPrepare implements InitializingBean {
    
    @Autowired
    private ICheckDataPrepareService checkDataPrepareService;
    
    public void afterPropertiesSet() throws Exception {
        checkDataPrepareService.initDataForRun();
    }

}