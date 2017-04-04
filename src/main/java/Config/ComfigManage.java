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
	//读取配置文件=>并返回读取结果
	public static Map<String, Bean> getConfig(String path){
		//创建一个用于返回的map对象
		Map<String, Bean> maps = new HashMap<String, Bean>();
		Document doc = null;
		//dome4j实现
			//1 创建解析器
		SAXReader reader = new SAXReader();
			//2 加载配置文件 => document 对象
		InputStream is = ComfigManage.class.getResourceAsStream(path);
		try {
			doc = reader.read(is);
		} catch (Exception e) {
			throw new RuntimeException("xml配置错误");
		}
			//3 定义xpath表达式，取出Bean元素
		String xpath = "//bean";
			//4 对Bean元素进行遍历
		List<Element> list = doc.selectNodes(xpath);
		if(list != null){
			for(Element beanEle : list){
				Bean bean = new Bean();
				//将bean元素的name /class 属性封装到Bean对象中
				String id = beanEle.attributeValue("id");
				String className = beanEle.attributeValue("class");
				
				bean.setId(id);
				bean.setClassName(className);
				//获取Bean元素下的所有Property子元素将属性name/value/ref封装到Property中
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
				//id是不应重复的
                if(maps.containsKey(id)){
                    throw new RuntimeException("bean节点ID重复：" + id);
                }
				maps.put(id, bean);
			}
		}
		
			return maps;	
	}
}
