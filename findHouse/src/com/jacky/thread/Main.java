package com.jacky.thread;

import java.util.ArrayList;
import java.util.List;

import com.jacky.feixin.service.FeixinService;
import com.jacky.ganji.bean.ItemBean;
import com.jacky.ganji.service.GanjiService;
import com.jacky.util.PropertiesUtil;

public class Main extends Thread {
	// ����ʷpuid��¼����һ��ֱ�ӷ�������
	private static List<String> puidList = new ArrayList<String>();
	public static void main(String[] args) {
		Main m = new Main();
		m.start();
	}
	
	@Override
	public void run(){

		try {
			// �߳̿�ʼ����һ�����ŵ��ֻ�
			String receivers = PropertiesUtil.receivers;
			// ������
			System.out.println("�����ߣ�"+receivers);
			String[] receiverArr = receivers.split(",");
			for(int i=0; i<receiverArr.length; i++){
				System.out.println(i);
				FeixinService.sendMsg(PropertiesUtil.feixinPhone, PropertiesUtil.feixinPwd, receiverArr[i], "ʵʱ�������ѿ�����");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ����ѯ����	
		int num = 0;
		while(true){
			try {
				System.out.println("--------------�� "+(++num)+" ��------------");
				ItemBean tempItemBean = null;
				List<ItemBean> list = GanjiService.getMsgGanjiList();
				System.out.println("��ѯ������"+list.size());
				if(list != null && list.size()>0){
					// ����ǵ�һ��puidListû��ֵ
					if(puidList.size()>0){
						for(int i=0; i<list.size(); i++){
							ItemBean itemBean = list.get(i);
							if(!puidList.contains(itemBean.getPuid())){
								puidList.add(itemBean.getPuid());
								// �������һ��   ˵�����Ǿ���Ϣ
								if(i<list.size()-1){
									tempItemBean = itemBean;
								}
							}
						}
					}else{
						for(ItemBean itemBean: list){
							puidList.add(itemBean.getPuid());
						}
					}
				}
				if(tempItemBean!=null){
					// ���Ͷ��Žӿ�
					String content = tempItemBean.getMsg();
					content += "����: http://sz.ganji.com/fang1/"+tempItemBean.getPuid().split("-")[1]+"x.htm";

					System.out.println("-----------------------------");
					String receivers = PropertiesUtil.receivers;
					String[] receiverArr = receivers.split(",");
					for(int i=0; i<receiverArr.length; i++){
						FeixinService.sendMsg(PropertiesUtil.feixinPhone, PropertiesUtil.feixinPwd, receiverArr[i], content);
					}
					System.out.println("���Ͷ������ݣ�"+content);
					System.out.println("-----------------------------");
					
				}
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("-----------------------------");
				String content = "�������쳣��"+e.getMessage();
				// �߳̿�ʼ����һ�����ŵ��ֻ�
				String receivers = PropertiesUtil.receivers;
				String[] receiverArr = receivers.split(",");
				for(int i=0; i<receiverArr.length; i++){
					try {
						FeixinService.sendMsg(PropertiesUtil.feixinPhone, PropertiesUtil.feixinPwd, receiverArr[i], content);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				System.out.println("���Ͷ������ݣ�"+content);
				System.out.println("-----------------------------");
			}
		}
	}
}
