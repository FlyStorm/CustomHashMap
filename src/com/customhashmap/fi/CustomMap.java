package com.customhashmap.fi;

/**
 * �Զ���һ��Map�ӿ�
 * @author Administrator
 *
 * @param <K>
 * @param <V>
 */
public interface CustomMap<K,V> {
	/**
	 * �洢���ݵķ���
	 * @param k ��
	 * @param v	ֵ
	 * @return
	 */
	public V put(K k,V v);
	/**
	 * ��ȡ���ݵķ���
	 * @param k ��
	 * @return
	 */
	public V get(K k);
	public int size();
	
	/**
	 * �ڲ��ӿ�
	 * @param <K>
	 * @param <V>
	 */
	public interface Entry<K,V>{
		public K getKey();
		public V getValue();
	}
}
