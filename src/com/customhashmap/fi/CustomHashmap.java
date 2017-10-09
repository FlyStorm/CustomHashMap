package com.customhashmap.fi;

import java.util.ArrayList;
import java.util.List;

public class CustomHashmap<V, K> implements CustomMap<K, V> {
	
	/**
	 * ���������Ĭ�ϳ���Ϊ16(HashMap�ײ�������)
	 */
	private static int defaultLength=16;
	
	/**
	 * ���������Ĭ�ϸ�������Ϊ0.75
	 * ������������鳤�ȵ�0.75,��Ҫ����
	 */
	private static double defaultLoader=0.75;
	
	/**
	 * ����һ������
	 */
	private Entry<K,V>[] table=null;
	
	/**
	 * Ĭ�ϴ�С
	 */
	private int size=0;
	
	/**
	 * ���������Ĺ��췽��
	 * �����Ĭ�ϳ��Ⱥ͸������ӿ����Լ�����
	 * @param defalutLength
	 * @param defaultLoader
	 */
	public CustomHashmap(int length,double loader){
		defaultLength=length;
		defaultLoader=loader;
		
		table=new Entry[defaultLength];
	}
	/**
	 * �ղι��췽��
	 * �����ָ�����Ͳ���Ĭ��ֵ
	 */
	public CustomHashmap(){
		this(defaultLength,defaultLoader);
	}

	/**
	 * ʵ�ֽӿ�����ķ���
	 * �洢���ݣ�����value��ֵ
	 */
	@Override
	public V put(K k, V v) {
		
		//�洢����֮ǰ��Ҫ�ж�size�Ƿ�ﵽ��һ�����ݵı�׼
		if(size>=defaultLength*defaultLoader){
			//����������ԭ����2��
			up2size();
		}
		
		//1.����hash����������key��hash������������±�
		int index=getIndex(k);
		//��ȡ�����е�Ԫ�ض���
		Entry<K, V> entry = table[index];
		
		if(entry==null){
			//���entryΪ�գ�˵��table��index����λ����û��Ԫ��,ֱ�Ӱ�value�洢�ڵ�ǰλ��
			table[index] = newEntry(k, v, null);
			//˵��һ��Ԫ��ֻռ����һ��λ��
			size++;
		}else{
			//��Ϊ�գ�˵��indexλ����Ԫ�أ���ôҪ����һ���滻��Ȼ��nextָ��������
			table[index]=newEntry(k, v, entry);
		}
		
		return table[index].getValue();
	}
	
	/**
	 * ����������ԭ����2��
	 */
	private void up2size() {
		Entry<K,V>[] newTable=new Entry[2*defaultLength];
		
		againHash(newTable);
	}
	
	/**
	 * �´��������Ҫ����ǰ�����������Ԫ�ؽ�����ɢ�У���ϣ�㷨��
	 * @param newTable
	 */
	private void againHash(Entry<K,V>[] newTable){
		//����һ�����ϣ������Entry����
		List<Entry<K,V>> list=new ArrayList<Entry<K,V>>();
		
		for (int i = 0; i < table.length; i++) {
			//�ж����������Ƿ���Ԫ��,�����Ϊ�գ��õ������Ԫ�أ�������һ������
			if(table[i]==null){
				continue;
			}
			findEntryByNext(table[i],list);
		}
		//size��0���õ������������������Ԫ��
		if(list.size()>0){
			//Ҫ����һ�����������ɢ��
			//�����С����Ϊ0
			size=0;
			defaultLength=defaultLength*2;
			table=newTable;
			
			for (Entry<K, V> entry : list) {
				if(entry.next!=null){
					//nextָ�����¹�0
					entry.next=null;
				}
				//�ݹ����
				put(entry.getKey(),entry.getValue());
			}
		}
	}
	
	/**
	 * ͨ��nextָ���ҵ�Entry����,�ҵ��˾Ͱ������ڼ�����
	 */
	private void findEntryByNext(Entry<K,V> entry,List<Entry<K,V>> list) {
		//����Ԫ�ز�Ϊ�գ�����ָ����һ����ָ�벻Ϊ��
		if(entry!=null&&entry.next!=null){
			list.add(entry);
			//���еݹ����
			findEntryByNext(entry.next, list);
		}else{
			//��entry.nextΪ�յ�ʱ��
			list.add(entry);
		}
	}
	/**
	 * ����һ��Entry��������������ֵ
	 * @param k
	 * @param v
	 * @param next
	 * @return
	 */
	private Entry<K,V> newEntry(K k,V v,Entry<K,V> next){
		return new Entry(k,v,next);
	}
	
	/**
	 * ����һ��Hash����������key��hash������������±�
	 * �����ﲻ����Ĭ�ϳ��ȵ��������ȡģ
	 * @param k
	 * @return
	 */
	private int getIndex(K k){
		int m=defaultLength;
		//����hashCode������ȡģm�õ��������п����Ǹ���
		int index=k.hashCode()%m;
		return index>=0?index:-index;
	}

	/**
	 * ��ȡ����
	 */
	@Override
	public V get(K k) {
		//1.����һ��hash����������key��hash������������±�
		int index=getIndex(k);
		
		if(table[index]==null){
			return null;
		}
		
		return findValueByEqualKey(k,table[index]);
	}

	/**
	 * ͨ������ֵ
	 * ���ܲ�ͬ��key��ȡ��λ����һ����
	 * @param k
	 * @return
	 */
	private V findValueByEqualKey(K k,Entry<K,V> entry) {
		//���õ����������Ԫ��
		if(k==entry.getKey()||k.equals(entry.getKey())){
			return entry.getValue();
		}else{
			if(entry.next!=null){
				//���еݹ����
				return findValueByEqualKey(k, entry.next);
			}
		}
		return null;
	}
	@Override
	public int size() {
		return size;
	}
	
	//����һ���ڲ���ʵ���ڲ��ӿ�
	class Entry<K,V> implements CustomMap.Entry<K, V>{
		K k;
		V v;
		//next��һ��Entry����
		Entry<K, V> next;
		
		public Entry(K k,V v,Entry<K,V> next){
			this.k=k;
			this.v=v;
			this.next=next;
		}

		/**
		 * ��װkey��ֵ
		 */
		@Override
		public K getKey() {
			//����k,��key��������
			return k;
		}

		/**
		 * ��װvalue��ֵ
		 */
		@Override
		public V getValue() {
			//����v,��value��������
			return v;
		}
		
	}
	
}
