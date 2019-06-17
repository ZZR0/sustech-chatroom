package sim.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * The type Spring util.
 *
 * @Description: 提供手动获取被spring管理的bean对象
 */
public class SpringUtil implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringUtil.applicationContext == null) {
			SpringUtil.applicationContext = applicationContext;
		}
	}

    /**
     * Gets application context.
     *
     * @return the application context
     */
// 获取applicationContext
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

    /**
     * Gets bean.
     *
     * @param name the name
     * @return the bean
     */
// 通过name获取 Bean.
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}


}
