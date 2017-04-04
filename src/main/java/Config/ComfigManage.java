package Config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import spring_ioc.Bean;
import spring_ioc.Property;

public class ComfigManage {
	//��ȡ�����ļ�=>�����ض�ȡ���
	public static Map<String, Bean> getConfig(String path){
		//����һ�����ڷ��ص�map����
		Map<String, Bean> maps = new HashMap<String, Bean>();
		Document doc = null;
		//dome4jʵ��
			//1 ����������
		SAXReader reader = new SAXReader();
			//2 ���������ļ� => document ����
		InputStream is = ComfigManage.class.getResourceAsStream(path);
		try {
			doc = reader.read(is);
		} catch (Exception e) {
			throw new RuntimeException("xml���ô���");
		}
			//3 ����xpath���ʽ��ȡ��BeanԪ��
		String xpath = "//bean";
			//4 ��BeanԪ�ؽ��б���
		List<Element> list = doc.selectNodes(xpath);
		if(list != null){
			for(Element beanEle : list){
				Bean bean = new Bean();
				//��beanԪ�ص�name /class ���Է�װ��Bean������
				String id = beanEle.attributeValue("id");
				String className = beanEle.attributeValue("class");
				
				bean.setId(id);
				bean.setClassName(className);
				//��ȡBeanԪ���µ�����Property��Ԫ�ؽ�����name/value/ref��װ��Property��
				List<Element> children = beanEle.elements("property");
				if(children != null){
					for(Element childrenEle : children){
						Property property = new Property();
						String pName = childrenEle.attributeValue("name");
						String pValue = childrenEle.attributeValue("value");
						String pRef = childrenEle.attributeValue("ref");
						property.setName(pName);
						property.setValue(pValue);
						property.setRef(pRef);
						
						bean.getProperties().add(property);
					}
				}
				//id�ǲ�Ӧ�ظ���
                if(maps.containsKey(id)){
                    throw new RuntimeException("bean�ڵ�ID�ظ���" + id);
                }
				maps.put(id, bean);
			}
		}
		
			return maps;	
	}
}
