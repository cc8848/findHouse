package com.jacky.feixin.bean;

import java.util.Map;
/**
 * ΢��ͨ���绰���ǳƲ�ѯ���
 * @author Jacky
 *
 */
public class SearchResultBean {
	// ��¼����
	private int total;
	// ÿһ��map��װ��һ����¼��json��Ϣ
	private Map<String, Object>[] contacts;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public Map<String, Object>[] getContacts() {
		return contacts;
	}
	public void setContacts(Map<String, Object>[] contacts) {
		this.contacts = contacts;
	}
}
