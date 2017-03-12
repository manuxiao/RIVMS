package org.gaixie.micrite.patrolRiverSummary.service.impl;

import java.util.Date;
 
import org.gaixie.micrite.beans.PatrolRiverSummary;
import org.gaixie.micrite.patrolRiverSummary.dao.IPatrolRiverSummaryDAO;
import org.gaixie.micrite.patrolRiverSummary.service.IRiverDataPrepareService;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * River数据初始化接口实现
 * @see org.gaixie.micrite.car.service.IRiverDataPrepareService
 */
public class RiverDataPrepareServiceImpl implements IRiverDataPrepareService { 

//    @Autowired
//    private IDictionaryDAO dictionaryDAO;

    @Autowired
    private IPatrolRiverSummaryDAO patrolRiverSummaryDAO;
    
    public void initDataForRun() {
        if(patrolRiverSummaryDAO.get(1)!=null) return;
        
        //insert  into Carfile_source(name) values('Unfamiliar Visit');
        //insert  into Carfile_source(name) values('Familiar'); 
//        CarDictionary cs = new CarDictionary("非厢式货车");
//        patrolRiverSummaryDAO.save(cs);
//        cs = new CarDictionary("厢式货车");
//        CarDictionaryDAO.save(cs);        
//        Carfile carfile = new Carfile("慈溪市华夏电器实业有限公司","63741320","慈溪市掌起镇贴水村","浙B.B0708",1,"0.79","依维柯5037XXY5","测试记录",1,1,"1",1,"1",1,"test","test",1,new Date(),cs);              

        PatrolRiverSummary patrolRiverSummary = new PatrolRiverSummary(1,1,1,1,1,1,1,1,1,1,0,0,0,0,2,2,3,"测试待审批",0,new Date(),new Date(),new Date(),1,1,1,"测试小马");              
         patrolRiverSummaryDAO.save(patrolRiverSummary);
    }
}
