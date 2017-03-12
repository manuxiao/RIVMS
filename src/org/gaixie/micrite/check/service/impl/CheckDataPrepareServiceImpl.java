package org.gaixie.micrite.check.service.impl;

import java.util.Date;

import org.gaixie.micrite.beans.Check;
import org.gaixie.micrite.check.dao.ICheckDAO;
import org.gaixie.micrite.check.service.ICheckDataPrepareService;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * check数据初始化接口实现
 * @see org.gaixie.micrite.check.service.IcheckDataPrepareService
 */
public class CheckDataPrepareServiceImpl implements ICheckDataPrepareService { 



    @Autowired
    private ICheckDAO CheckDAO;
    
    public void initDataForRun() {
    	 return;
//        if(CheckSourceDAO.get(1)!=null) return;
//        
//        //insert  into Check_source(name) values('Unfamiliar Visit');
//        //insert  into Check_source(name) values('Familiar'); 
//        CheckSource cs = new CheckSource("Unfamiliar Visit");
//        CheckSourceDAO.save(cs);
//        cs = new CheckSource("Familiar");
//        CheckSourceDAO.save(cs);
//        
//        Check Check = new Check("Bob Yu","139000000",new Date(),cs);
//        CheckDAO.save(Check);
    }
}
