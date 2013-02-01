package edu.byu.swing.utilities;

import java.awt.Component;
import java.awt.EventQueue;
import java.util.Arrays;
import java.util.Iterator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author jmooreoa
 */
public class BeanHelper implements ApplicationContextAware {

    private static ApplicationContext appContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <U> U getBean(final String beanId) {
        if (appContext == null) {
            return null;
        }
        Class type = appContext.getType(beanId);
        return (U) getBean(beanId, type);
    }

    /**
     * Fetches a Spring bean
     * @param <U> bean type
     * @param klass bean class
     * @return bean
     */
    @SuppressWarnings("unchecked")
    public static <U> U getBean(final Class<U> klass) {
        if (appContext == null) {
            throw new IllegalStateException("Application is not yet initialized");
        }

        String[] names = appContext.getBeanNamesForType(klass);

        if (names.length == 0) {
            throw new IllegalStateException("No beans found with interface: " + klass);
        }

        if (names.length > 1) {
            StringBuffer sb = new StringBuffer();
            Iterator<String> i = Arrays.asList(names).iterator();
            while (i.hasNext()) {
                String name = i.next();
                sb.append(name);
                if (i.hasNext()) {
                    sb.append(", ");
                }
            }
            throw new IllegalStateException(names.length + " beans found with interface: " + klass + " [" + sb + "]");
        }

        //Get on EDT if it's a component
        String beanId = names[0];
        return (U) getBean(beanId, klass);
    }

    /**
     * Fetches a Spring bean
     * @param <U> bean type
     * @param beanName bean name
     * @param klass bean class
     * @return bean
     */
    @SuppressWarnings("unchecked")
    public static <U> U getBean(final String beanName, final Class<U> klass) {
        if (!Component.class.isAssignableFrom(klass) || EventQueue.isDispatchThread()) {
            return (U) appContext.getBean(beanName);
        }
        final BeanWrapper<U> wrapper = new BeanWrapper<U>();
        Runnable r = new Runnable() {

            public void run() {
                wrapper.setObj((U) appContext.getBean(beanName));
            }
        };
        try {
            EventQueue.invokeAndWait(r);
        } catch (Exception ex) {
            throw new IllegalStateException("Error creating bean: " + beanName, ex);
        }

        return wrapper.getObj();
    }

    // Used by all getBean implementations
    private static class BeanWrapper<U> {

        private U obj;

        public U getObj() {
            return obj;
        }

        public void setObj(U obj) {
            this.obj = obj;
        }
    }

}
