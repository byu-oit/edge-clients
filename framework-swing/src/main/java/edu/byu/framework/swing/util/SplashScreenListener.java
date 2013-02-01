/**
 * Name: SplashScreenListener.java
 * Date Created: Feb 23, 2009
 */
package edu.byu.framework.swing.util;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tylers2
 */
@Repository
public class SplashScreenListener implements BeanPostProcessor, ApplicationContextAware {

    private final static Logger LOG = Logger.getLogger(SplashScreenListener.class);
    private int total = 0;
    private int count;
    private boolean listen = true;

    public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
        count += 1;
        if (listen) {
            int percent = (int) (((double) count) / ((double) total) * 100);
            LOG.debug("Loading " + name + " (" + count + " of " + total + ")");
            LOG.info("Loading Libraries... " + percent + "%");
	    SplashScreenForm.setPercent(percent);
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String name) throws BeansException {
        return bean;
    }

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        total = context.getBeanDefinitionCount();
        LOG.debug("Loading " + total + " beans");
    }

    public void setListen(boolean listen) {
        this.listen = listen;
    }
}
