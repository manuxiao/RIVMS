
package org.gaixie.micrite.patrolRiverSummary.data;

import org.gaixie.micrite.patrolRiverSummary.service.IRiverDataPrepareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.InitializingBean;

public class RiverDataPrepare implements InitializingBean {
    
    @Autowired
    private IRiverDataPrepareService riverDataPrepareService;
    
    public void afterPropertiesSet() throws Exception {
    	riverDataPrepareService.initDataForRun();
    }

}