package edu.byu.swing.renderers;

import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.BeansException;
import org.apache.log4j.Logger;

/**
 * Author: Tyler Southwick (tyler_southwick@byu.edu)
 * Date: Aug 18, 2009
 */
public class DefaultPersonRendererBean implements InitializingBean, ApplicationContextAware {
    private static String rendererBeanName;
    private final static String DEFAULT_RENDERER_BEAN = "personNameRenderer";
    private PersonRenderer renderer;
    private ApplicationContext applicationContext;
    private final static Logger LOG = Logger.getLogger(DefaultPersonRendererBean.class);

    public PersonRenderer getRenderer() {
        return renderer;
    }

    public void setRendererBeanName(String rendererBeanName) {
        this.rendererBeanName = rendererBeanName;
    }

    public void afterPropertiesSet() throws Exception {
        if (rendererBeanName == null) {
            throw new IllegalStateException("A renderer bean name must be specified");
        }
        
        LOG.debug("looking for renderer bean with name: " + rendererBeanName);

        try {
            renderer = (PersonRenderer) applicationContext.getBean(rendererBeanName, PersonRenderer.class);
            LOG.debug("Using " + rendererBeanName);
        } catch (NoSuchBeanDefinitionException e) {
            LOG.warn("Using the default name renderer: " + DEFAULT_RENDERER_BEAN);
            renderer = (PersonRenderer) applicationContext.getBean(DEFAULT_RENDERER_BEAN, PersonRenderer.class);
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
