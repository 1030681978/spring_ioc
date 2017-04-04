package Config;

public interface BeanFactory {
	//根据bean的name获得Bean对象的方法
	Object getBean(String beanName);
}
