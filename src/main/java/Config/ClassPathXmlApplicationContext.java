package Config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import spring_ioc.Bean;
import spring_ioc.Property;

public class ClassPathXmlApplicationContext implements BeanFactory {
//该类一创建就初始化容器
	
	public Object getBean(String beanName) {
		// TODO Auto-generated method stub
		return null;
	}
	//配置信息
	private Map<String, Bean> config;
	//类似spring的容器
	private Map<String, Object> context = new HashMap<String, Object>();
	public ClassPathXmlApplicationContext(String path) {
		//1读取配置文件获得需要初始化的Bean信息
		config = ComfigManage.getConfig(path);
		//2遍历配置初始化Bean
		if(config != null){
			for(Entry<String, Bean> entry : config.entrySet()){
				String beanName = entry.getKey();
				Bean bean = entry.getValue();
				//根据bean配置创建bean对象
				Object beanObj = createBean(bean);
				//3将初始化好的Bean放入容器中
				context.put(beanName, beanObj);
			}
		}
	}
	//根据Bean配置创建Bean对象
	private Object createBean(Bean bean) {
		Class clazz = null;
		Object beanObj = null;
		//1获得要创建的Bean的class
		String className = bean.getClassName();
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("您的Bean的class配置错误"+className);
		}
		//获得class => 将class对象创建出来
		try {
			beanObj = clazz.newInstance();//调用空参构造器
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("您的Bean没有一个空惨的构造器"+className);
		}
		//2需要获得Bean的属性将其注入
		if(bean.getProperties() != null){
			for(Property pro : bean.getProperties()){
				//注入分两种情况
				//1简单属性注入
				if(pro.getValue() != null){
					String value = pro.getValue();
					String name = pro.getName();
					//根据属性名称获得注入属性对应的set方法
					Method setMethod = BeanUtils.GetWriteMethod(beanObj,name);
					try {
						setMethod.invoke(beanObj, value);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new RuntimeException("Bean的属性"+name+"没有对应的set方法或方法参数不正确"+className);
					}
				}
				//2其他bean注入
				if(pro.getRef() != null){}
			}
		}
			
		return null;
	}

}
