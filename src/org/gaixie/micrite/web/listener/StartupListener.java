package org.gaixie.micrite.web.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.swing.text.LabelView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.car.service.ICarfileService;
import org.gaixie.micrite.dic.service.IDictionaryService;
import org.gaixie.micrite.util.DictionaryUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class StartupListener implements ServletContextListener {
    private static final Log log = LogFactory.getLog(StartupListener.class);
    
    @Override
    public void contextInitialized(ServletContextEvent event) {
        log.debug("Initializing context...");

        ServletContext context = event.getServletContext();

        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);

        IDictionaryService service = (IDictionaryService) ctx.getBean("dictionaryService");
        List<Dictionary> list = service.findALLDictionary(0);
        DictionaryUtil.setDefaultDictionary(list.get(0));
//        DictionaryTableManager dictionaryMgr = (DictionaryTableManager) ctx.getBean("dictionaryTableManager");
//        Map<String,List<LabelValue>> map = dictionaryMgr.getDictionary();
//        for(String key : map.keySet()){ 
//        	context.setAttribute(key, map.get(key));
//        }

        log.debug("Drop-down initialization complete [OK]");
    }
 
    
    /**
     * Shutdown servlet context (currently a no-op method).
     * @param servletContextEvent The servlet context event
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //LogFactory.release(Thread.currentThread().getContextClassLoader());
        //Commented out the above call to avoid warning when SLF4J in classpath.
        //WARN: The method class org.apache.commons.logging.impl.SLF4JLogFactory#release() was invoked.
        //WARN: Please see http://www.slf4j.org/codes.html for an explanation.
    }

}
