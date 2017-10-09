package com.customhashmap.fi;

import java.util.HashMap;
import java.util.Map;

//���Է���
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//�����Զ���HashMap����,��ʵ����
		CustomHashmap<String, String> customHashmap=new CustomHashmap<>();
		//���Զ���HashMap�����������
		for (int i = 0; i < 100; i++) {
			customHashmap.put("key"+i, "value"+i);
		}
		//��ȡ����
		for (int i = 0; i < 100; i++) {
			System.out.println("key:"+"key"+i+"����value:"+customHashmap.get("key"+i));
		}
		
		System.out.println("-----------HashMap------------------");
		Map<String, String> hashmap=new HashMap<String, String>();
		for (int i = 0; i < 100; i++) {
			hashmap.put("key"+i, "value"+i);
		}
		//��ȡ����
		for (int i = 0; i < 100; i++) {
			System.out.println("key:"+"key"+i+"����value:"+hashmap.get("key"+i));
		}
	}

}
