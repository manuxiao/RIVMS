package org.gaixie.micrite.car.service.impl;

import java.util.Date;

import org.gaixie.micrite.car.dao.ICarfileDAO;
import org.gaixie.micrite.car.service.ICARDataPrepareService;
import org.gaixie.micrite.dic.dao.IDictionaryDAO;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * Carfile数据初始化接口实现
 * @see org.gaixie.micrite.car.service.ICARDataPrepareService
 */
public class CARDataPrepareServiceImpl implements ICARDataPrepareService { 

//    @Autowired
//    private IDictionaryDAO dictionaryDAO;
//
//    @Autowired
//    private ICarfileDAO carfileDAO;
    
    public void initDataForRun() {
//        if(CarDictionaryDAO.get(1)!=null) return;
//        
//        //insert  into Carfile_source(name) values('Unfamiliar Visit');
//        //insert  into Carfile_source(name) values('Familiar'); 
//        CarDictionary cs = new CarDictionary("非厢式货车");
//        CarDictionaryDAO.save(cs);
//        cs = new CarDictionary("厢式货车");
//        CarDictionaryDAO.save(cs);        
//        Carfile carfile = new Carfile("慈溪市华夏电器实业有限公司","63741320","慈溪市掌起镇贴水村","浙B.B0708",1,"0.79","依维柯5037XXY5","测试记录",1,1,"1",1,"1",1,"test","test",1,new Date(),cs);              
//        CarfileDAO.save(carfile);
    }
}
