package Config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import spring_ioc.Bean;
import spring_ioc.Property;

public class ClassPathXmlApplicationContext implements BeanFactory {
//����һ�����ͳ�ʼ������
	
	public Object getBean(String beanName) {
		// TODO Auto-generated method stub
		return null;
	}
	//������Ϣ
	private Map<String, Bean> config;
	//����spring������
	private Map<String, Object> context = new HashMap<String, Object>();
	public ClassPathXmlApplicationContext(String path) {
		//1��ȡ�����ļ������Ҫ��ʼ����Bean��Ϣ
		config = ComfigManage.getConfig(path);
		//2�������ó�ʼ��Bean
		if(config != null){
			for(Entry<String, Bean> entry : config.entrySet()){
				String beanName = entry.getKey();
				Bean bean = entry.getValue();
				//����bean���ô���bean����
				Object beanObj = createBean(bean);
				//3����ʼ���õ�Bean����������
				context.put(beanName, beanObj);
			}
		}
	}
	//����Bean���ô���Bean����
	private Object createBean(Bean bean) {
		Class clazz = null;
		Object beanObj = null;
		//1���Ҫ������Bean��class
		String className = bean.getClassName();
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("����Bean��class���ô���"+className);
		}
		//���class => ��class���󴴽�����
		try {
			beanObj = clazz.newInstance();//���ÿղι�����
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("����Beanû��һ���ղҵĹ�����"+className);
		}
		//2��Ҫ���Bean�����Խ���ע��
		if(bean.getProperties() != null){
			for(Property pro : bean.getProperties()){
				//ע����������
				//1������ע��
				if(pro.getValue() != null){
					String value = pro.getValue();
					String name = pro.getName();
					//�����������ƻ��ע�����Զ�Ӧ��set����
					Method setMethod = BeanUtils.GetWriteMethod(beanObj,name);
					try {
						setMethod.invoke(beanObj, value);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new RuntimeException("Bean������"+name+"û�ж�Ӧ��set�����򷽷���������ȷ"+className);
					}
				}
				//2����beanע��
				if(pro.getRef() != null){}
			}
		}
			
		return null;
	}

}
