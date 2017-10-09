package com.customhashmap.fi;

import java.util.HashMap;
import java.util.Map;

//测试方法
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//创建自定义HashMap对象,并实例化
		CustomHashmap<String, String> customHashmap=new CustomHashmap<>();
		//向自定义HashMap里面添加数据
		for (int i = 0; i < 100; i++) {
			customHashmap.put("key"+i, "value"+i);
		}
		//获取数据
		for (int i = 0; i < 100; i++) {
			System.out.println("key:"+"key"+i+"　　value:"+customHashmap.get("key"+i));
		}
		
		System.out.println("-----------HashMap------------------");
		Map<String, String> hashmap=new HashMap<String, String>();
		for (int i = 0; i < 100; i++) {
			hashmap.put("key"+i, "value"+i);
		}
		//获取数据
		for (int i = 0; i < 100; i++) {
			System.out.println("key:"+"key"+i+"　　value:"+hashmap.get("key"+i));
		}
	}

}
