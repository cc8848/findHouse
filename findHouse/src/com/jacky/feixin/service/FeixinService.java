package com.jacky.feixin.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jacky.feixin.bean.SearchResultBean;
import com.jacky.util.HttpClientUtils;

public class FeixinService {
	private static HttpClient httpClient = new DefaultHttpClient();
	static{
		//����ʱ
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000); 
		//��ȡ��ʱ
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
		httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; rv:8.0.1) Gecko/20100101 Firefox/8.0.1");
		
	}
	
	// �Ƿ��½��״̬ Ĭ����δ��½
	private static boolean loginFlag = false;
	// �ҵķ���ID
	private static String myFeixinId = "";
	
	/**
	 * ���ͷ��Ŷ���
	 * @param sender ���ͺ���
	 * @param pwd ���ͺ�������
	 * @param receiver ���պ�����ǳ�
	 * @param content ��������
	 * @return
	 */
	public static boolean sendMsg(String sender, String pwd, String receiver, String content) throws Exception{
		if(!loginFlag){
			// ��½
			String retMsg = login(sender, pwd);
			System.out.println(retMsg);
			Map<String, Object> map = new Gson().fromJson(retMsg, new TypeToken<Map<String, Object>>(){}.getType());
			myFeixinId = (String)map.get("idUser");
			System.out.println(myFeixinId);
			if(myFeixinId!=null && myFeixinId.length()>0){
				//���ŵ�½�ɹ�
				System.out.println("�Ѿ��ɹ���½���ţ��Լ��ķ���ID�ǣ�"+myFeixinId);
				loginFlag = true;
				// ��¼֮���һ���ӷ���һ�·��ŷ���������ֹsession��ʱ
				Runnable t = new Runnable(){
					@Override
					public void run() {
						while(true){
							try {
								Thread.sleep(60000);
								System.out.println("������"+xintiao());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				};
				new Thread(t).start();
			}else{
				System.out.println("��½ʧ��"+(String)map.get("tip"));
			}
		}
		// ֻ���Ѿ���½�ſ��Է��ͷ��Ŷ���
		if(loginFlag){
			// �жϽ��պ����ǲ��Ƿ��ͺ��룬��������Ƿ����Լ�
			if(sender.equals(receiver)){
				sendMy(myFeixinId, content);
			}
			
			// ��������򷢸�����
			else{
				// ��ѯ���˵ķ���id
				String findResult = findFriend(receiver);
				SearchResultBean findBean = new Gson().fromJson(findResult, SearchResultBean.class);
				int count = findBean.getTotal();
				if(count>0){
					DecimalFormat decimalFormat = new DecimalFormat("#");//��ʽ������ 
					String touserid = decimalFormat.format(findBean.getContacts()[0].get("idContact"));
					System.out.println("���շ�ID��"+touserid);
					send(touserid, content);
				}else{
					// ʧ��
					System.out.println("���շ�"+receiver+"�Ҳ���");
				}
			}
			
		}
		return true;
	}
	
	// ����
	private static String findFriend(String phone){
		String url = "http://f.10086.cn/im5/index/searchFriendsByQueryKey.action?queryKey="+phone;
		String content = HttpClientUtils.methodGet(httpClient, url);
		return content;
	}
	
	// ��½
	private static String login(String phone, String pwd){
		Map<String, String> params = new HashMap<String, String>();
		params.put("m", phone);
		params.put("pass", pwd);
		params.put("captchaCode", "");
		params.put("checkCodeKey", "null");
		params.put("t", new Date().getTime()+"");
		String content = HttpClientUtils.methodPost(httpClient, "http://f.10086.cn/im5/login/loginHtml5.action", params);
		return content;
	}
	
	
	
	// ������Ϣ
	private static String send(String touserid, String msg){
		Map<String, String> params = new HashMap<String, String>();
		params.put("touserid", touserid);
		params.put("msg", msg);
		params.put("csrfToken", "");
		String content = HttpClientUtils.methodPost(httpClient, "http://f.10086.cn/im5/chat/sendNewMsg.action", params);
		return content;
	}
	// �����Լ���Ϣ
	private static String sendMy(String myId, String msg){
		Map<String, String> params = new HashMap<String, String>();
		params.put("touserid", myId);
		params.put("msg", msg);
		String content = HttpClientUtils.methodPost(httpClient, "http://f.10086.cn/im5/chat/sendNewGroupShortMsg.action", params);
		return content;
	}
	
	// ����
	private static String xintiao(){
		// ����������Ϊ�վ��� ��Ҫ��Ϊ����session��Ҫ����
		return findFriend("15914489532");
	}
}
