package org.gaixie.micrite.river.service.impl;

import java.util.Date;

import org.gaixie.micrite.beans.River;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.dic.dao.IDictionaryDAO;
import org.gaixie.micrite.river.dao.IRiverDAO;
import org.gaixie.micrite.river.service.IRiverDataPrepareService;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * river数据初始化接口实现
 * @see org.gaixie.micrite.river.service.IriverDataPrepareService
 */
public class RiverDataPrepareServiceImpl implements IRiverDataPrepareService { 

//    @Autowired
//    private IDictionaryDAO dictionaryDAO;
//
    @Autowired
    private IRiverDAO RiverDAO;
    
    public void initDataForRun() {
//        if(RiverDAO(1)!=null) return;
  
        River River = new River("FB04001","无名河1", "wmh", 200.00, "星华路", "顾良观屋后", false, 0, true,new Date(),1, 1, new Date(), "村级050404000101","", 0);
        RiverDAO.save(River);
    }
}
