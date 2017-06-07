package com.stream.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service("springUtil")
public class SpringUtil implements ApplicationContextAware, InitializingBean {

	
	/**
	 * 当前IOC
	 */
	private static ApplicationContext applicationContext;
	
	public static void setAppContext(ApplicationContext appContext) {
		applicationContext = appContext;
	}
	
	/**
	 * 设置当前上下文环境，此方法由spring自动装配
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		setAppContext(arg0);
	}
	
	/**
	 * 从当前IOC获取bean
	 * 
	 * @param id bean的id
	 * @return 35
	 */
	public static Object getObject(String id) {
		Object object;
		object = applicationContext.getBean(id);
		return object;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}
}
