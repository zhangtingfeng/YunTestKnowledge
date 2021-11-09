package cn.tedu.sp0ag4studio.core.id.loader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.tedu.sp0ag4studio.core.id.IDException;

/**
 * LoadResourcesListener
 * 此代码源于开源项目E3,原作者：黄云辉
 *
 * @author OSWorks-XC
 * @see IDException
 * @since 2010-03-17
 */
public class LoadResourcesListener implements ServletContextListener {


    public void contextInitialized(ServletContextEvent pServletContextEvent) {
        final String WEB_HOME = pServletContextEvent.getServletContext().getRealPath("/");
        ResourcesLoader.load(WEB_HOME);
    }

    public void contextDestroyed(ServletContextEvent pServletContextEvent) {
    }

}
