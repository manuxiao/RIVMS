package org.gaixie.micrite.standard.data;

import org.gaixie.micrite.standard.service.IStandardDataPrepareService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class StandardDataPrepare implements InitializingBean {
	@Autowired
	private IStandardDataPrepareService standardDataPrepareService;
	public void afterPropertiesSet() throws Exception {
		System.out.println(standardDataPrepareService);
//		standardDataPrepareService = new StandardDataPrepareServiceImpl();
		standardDataPrepareService.initDataForRun();
	}

}
