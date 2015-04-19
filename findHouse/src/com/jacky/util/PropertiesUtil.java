package com.jacky.util;

import java.util.Properties;

public class PropertiesUtil {
	// �ϼ�����url
	public static String ganjiSearchUrl;
	// �����ʱ��
	public static String jianceTime;
	
	// ��½����ŵ�½���˺�
	public static String feixinPhone;
	// ��½����ŵ�½������֤����
	public static String feixinPwd;
	
	// ���շ�������ǳ�
	public static String receivers;
	
	
	static{
		try {
			Properties p = new Properties();
			p.load(PropertiesUtil.class.getResourceAsStream("/config.properties"));
			ganjiSearchUrl = p.getProperty("ganjiSearchUrl");
			jianceTime = p.getProperty("jianceTime");
			feixinPhone = p.getProperty("feixinPhone");
			feixinPwd = p.getProperty("feixinPwd");
			receivers = p.getProperty("receivers");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
