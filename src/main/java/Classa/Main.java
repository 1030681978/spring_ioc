package Classa;

import java.util.Map;

import Config.BeanFactory;
import Config.ClassPathXmlApplicationContext;
import Config.ComfigManage;
import spring_ioc.Bean;

public class Main {

	public static void main(String[] args) {
//		Map<String, Bean> maps = ComfigManage.getConfig("/application.xml");
//		System.out.println(maps);
		BeanFactory factory = new ClassPathXmlApplicationContext("/application.xml");
		A a = (A) factory.getBean("A");
		B b = (B) factory.getBean("B");
		System.out.println(a.getName());
		System.out.println(b.getName());
		System.out.println(b.getA());
		//String name = b.getA().getName();
	}

}
