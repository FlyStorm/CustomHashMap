package com.customhashmap.fi;

import java.util.ArrayList;
import java.util.List;

public class CustomHashmap<V, K> implements CustomMap<K, V> {
	
	/**
	 * 定义数组的默认长度为16(HashMap底层是数组)
	 */
	private static int defaultLength=16;
	
	/**
	 * 定义数组的默认负载因子为0.75
	 * 如果超过了数组长度的0.75,就要扩容
	 */
	private static double defaultLoader=0.75;
	
	/**
	 * 定义一个数组
	 */
	private Entry<K,V>[] table=null;
	
	/**
	 * 默认大小
	 */
	private int size=0;
	
	/**
	 * 两个参数的构造方法
	 * 数组的默认长度和负载因子可以自己定义
	 * @param defalutLength
	 * @param defaultLoader
	 */
	public CustomHashmap(int length,double loader){
		defaultLength=length;
		defaultLoader=loader;
		
		table=new Entry[defaultLength];
	}
	/**
	 * 空参构造方法
	 * 如果不指定，就采用默认值
	 */
	public CustomHashmap(){
		this(defaultLength,defaultLoader);
	}

	/**
	 * 实现接口里面的方法
	 * 存储数据，返回value的值
	 */
	@Override
	public V put(K k, V v) {
		
		//存储数据之前，要判断size是否达到了一个扩容的标准
		if(size>=defaultLength*defaultLoader){
			//把数组扩大到原来的2倍
			up2size();
		}
		
		//1.调用hash函数，根据key和hash函数算出数组下标
		int index=getIndex(k);
		//获取数组中的元素对象
		Entry<K, V> entry = table[index];
		
		if(entry==null){
			//如果entry为空，说明table的index索引位置上没有元素,直接把value存储在当前位置
			table[index] = newEntry(k, v, null);
			//说明一个元素只占用了一个位置
			size++;
		}else{
			//不为空，说明index位置有元素，那么要进行一个替换，然后next指向老数据
			table[index]=newEntry(k, v, entry);
		}
		
		return table[index].getValue();
	}
	
	/**
	 * 把数组扩大到原来的2倍
	 */
	private void up2size() {
		Entry<K,V>[] newTable=new Entry[2*defaultLength];
		
		againHash(newTable);
	}
	
	/**
	 * 新创建数组后，要对以前老数组里面的元素进行再散列（哈希算法）
	 * @param newTable
	 */
	private void againHash(Entry<K,V>[] newTable){
		//创建一个集合，里面放Entry对象
		List<Entry<K,V>> list=new ArrayList<Entry<K,V>>();
		
		for (int i = 0; i < table.length; i++) {
			//判断数组里面是否有元素,如果不为空，拿到的这个元素，可能是一个链表
			if(table[i]==null){
				continue;
			}
			findEntryByNext(table[i],list);
		}
		//size不0，拿到了老数组里面的所有元素
		if(list.size()>0){
			//要进行一个新数组的再散列
			//数组大小重置为0
			size=0;
			defaultLength=defaultLength*2;
			table=newTable;
			
			for (Entry<K, V> entry : list) {
				if(entry.next!=null){
					//next指针重新归0
					entry.next=null;
				}
				//递归调用
				put(entry.getKey(),entry.getValue());
			}
		}
	}
	
	/**
	 * 通过next指针找到Entry对象,找到了就把它放在集合中
	 */
	private void findEntryByNext(Entry<K,V> entry,List<Entry<K,V>> list) {
		//假如元素不为空，并且指向下一个的指针不为空
		if(entry!=null&&entry.next!=null){
			list.add(entry);
			//进行递归调用
			findEntryByNext(entry.next, list);
		}else{
			//当entry.next为空的时候
			list.add(entry);
		}
	}
	/**
	 * 创建一个Entry对象，里面有三个值
	 * @param k
	 * @param v
	 * @param next
	 * @return
	 */
	private Entry<K,V> newEntry(K k,V v,Entry<K,V> next){
		return new Entry(k,v,next);
	}
	
	/**
	 * 创建一个Hash函数，根据key和hash函数算出数组下标
	 * 在这里不用它默认长度的最大质数取模
	 * @param k
	 * @return
	 */
	private int getIndex(K k){
		int m=defaultLength;
		//调用hashCode方法，取模m得到索引。有可能是负数
		int index=k.hashCode()%m;
		return index>=0?index:-index;
	}

	/**
	 * 获取数据
	 */
	@Override
	public V get(K k) {
		//1.创建一个hash函数，根据key和hash函数算出数组下标
		int index=getIndex(k);
		
		if(table[index]==null){
			return null;
		}
		
		return findValueByEqualKey(k,table[index]);
	}

	/**
	 * 通过键找值
	 * 可能不同的key存取的位置是一样的
	 * @param k
	 * @return
	 */
	private V findValueByEqualKey(K k,Entry<K,V> entry) {
		//先拿到数组里面的元素
		if(k==entry.getKey()||k.equals(entry.getKey())){
			return entry.getValue();
		}else{
			if(entry.next!=null){
				//进行递归调用
				return findValueByEqualKey(k, entry.next);
			}
		}
		return null;
	}
	@Override
	public int size() {
		return size;
	}
	
	//定义一个内部类实现内部接口
	class Entry<K,V> implements CustomMap.Entry<K, V>{
		K k;
		V v;
		//next是一个Entry类型
		Entry<K, V> next;
		
		public Entry(K k,V v,Entry<K,V> next){
			this.k=k;
			this.v=v;
			this.next=next;
		}

		/**
		 * 封装key的值
		 */
		@Override
		public K getKey() {
			//返回k,把key保存下来
			return k;
		}

		/**
		 * 封装value的值
		 */
		@Override
		public V getValue() {
			//返回v,把value保存下来
			return v;
		}
		
	}
	
}
