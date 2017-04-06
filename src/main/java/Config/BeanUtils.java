package Config;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BeanUtils {

	public static Method GetWriteMethod(Object beanObj, String name) {
		//得到属性的set方法用于注入
		Method m = null;
		Field ff = null;
		String methodName = "set" + name.substring(0, 1).toUpperCase()
				+ name.substring(1);
		try {
			//获取该类的字段
			ff = beanObj.getClass().getDeclaredField(name);
			ff.setAccessible(true);
		} catch (Exception e1) {
			throw new RuntimeException(beanObj.getClass()+"没有"+name+"这个属性");
		} 
		try {
			m = beanObj.getClass().getMethod(methodName,ff.getType());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(beanObj.getClass()+"没有"+methodName+"这个方法");
		} 
		return m;
	}

}
