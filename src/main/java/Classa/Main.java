package Classa;

import java.util.Map;

import Config.ComfigManage;
import spring_ioc.Bean;

public class Main {

	public static void main(String[] args) {
		Map<String, Bean> maps = ComfigManage.getConfig("/application.xml");
		System.out.println(maps);
	}

}
