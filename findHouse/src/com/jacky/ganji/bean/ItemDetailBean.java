package com.jacky.ganji.bean;
public class ItemDetailBean {
	private String phone;
	private String name;
	private String basicInfo;
	private String msg;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getBasicInfo() {
		return basicInfo;
	}
	public void setBasicInfo(String basicInfo) {
		this.basicInfo = basicInfo;
	}
	
	@Override
	public String toString(){
		return "������Ϣ��"+basicInfo+"\n�����绰:"+name+"("+phone+")\n��ϸ����:"+msg;
	}
	
}