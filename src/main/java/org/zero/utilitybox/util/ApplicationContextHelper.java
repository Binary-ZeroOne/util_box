package org.zero.utilitybox.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @program: utilitybox
 * @description: 获取Spring上下文工具
 * @author: 01
 * @create: 2018-09-12 22:21
 **/
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    /**
     * 获取指定类型的Bean
     *
     * @param clazz clazz
     * @param <T>   <T>
     * @return Bean
     */
    public static <T> T popBean(Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }

        return applicationContext.getBean(clazz);
    }

    /**
     * 获取指定类型的Bean
     *
     * @param clazz clazz
     * @param <T>   <T>
     * @return Bean
     */
    public static <T> T popBean(String className, Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }

        return applicationContext.getBean(className, clazz);
    }
}
