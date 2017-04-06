package Config;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BeanUtils {

	public static Method GetWriteMethod(Object beanObj, String name) {
		//�õ����Ե�set��������ע��
		Method m = null;
		Field ff = null;
		String methodName = "set" + name.substring(0, 1).toUpperCase()
				+ name.substring(1);
		try {
			//��ȡ������ֶ�
			ff = beanObj.getClass().getDeclaredField(name);
			ff.setAccessible(true);
		} catch (Exception e1) {
			throw new RuntimeException(beanObj.getClass()+"û��"+name+"�������");
		} 
		try {
			m = beanObj.getClass().getMethod(methodName,ff.getType());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(beanObj.getClass()+"û��"+methodName+"�������");
		} 
		return m;
	}

}
