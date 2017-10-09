package com.customhashmap.fi;

/**
 * 自定义一个Map接口
 * @author Administrator
 *
 * @param <K>
 * @param <V>
 */
public interface CustomMap<K,V> {
	/**
	 * 存储数据的方法
	 * @param k 键
	 * @param v	值
	 * @return
	 */
	public V put(K k,V v);
	/**
	 * 获取数据的方法
	 * @param k 键
	 * @return
	 */
	public V get(K k);
	public int size();
	
	/**
	 * 内部接口
	 * @param <K>
	 * @param <V>
	 */
	public interface Entry<K,V>{
		public K getKey();
		public V getValue();
	}
}
