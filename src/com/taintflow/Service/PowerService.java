package com.taintflow.Service;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.demo.taintsee.R;
import com.taintflow.infos.Infos;

public class PowerService {
	static ArrayList<Infos> localArrayList = null;
	public static String[] titles = { "����λ����Ϣ", "ͨѶ¼", "��˷�������Ϣ", "�ֻ�����",
			"GPS", "�����ַ", "����ĵ���λ����Ϣ", "�����������Ϣ", "��������Ϣ", "����", "�������ʷ��¼",
			"�û��˻���Ϣ", "ICCID", "�ֻ��豸���к�", "IMEI", "IMSI" };
	public static String[] taint = { "Location",
			"Address Book", "Microphone Input",
			"Phone Number", "GPS Location", "NET-based Location",
			"Last known Location", "camera", "accelerometer", "SMS",
			"browser history", "User account information",
			"ICCID", "Device serial number", "IMEI",
			"IMSI" };
	public static String[] decsrib = { 
		"���ױ�����Ӧ�����ã�������λ����Ϣ���͸�����̣�ıȡ���档",
		"����Ӧ�á�ľ�������ܻ��ȡͨѶ¼����ϵ����Ϣ����������Ϣ���ӣ�Ϊ�������������á�"
		, "���⿪���߻�ڿͻ�������˷��������û�ͨ�����ֻ��������������ﵽ��ȡ�û�ͨ����¼�����������û���Ŀ�ġ�",
		"����Ӧ�ú�ľ�������ܻ��ȡ�ֻ����룬���е����ֻ����룬����ɧ�ŵ绰����Ϊ��", 
		"���ڴ���LBS(����λ�õķ���)Ӧ�ó����ʹ���û���GPS����λ����Ϣ�����ܽ��û���GPS����λ����Ϣй©����������̡�",
		"����������ȡ�û��������ַ��Ϣ�������û��ĵ���λ�ã�����û��������ַ��Ϣ�͵���λ����Ϣ����˽й©��",
		"�ڿͿ���ͨ��ϵͳ©����ȡ�û�����ĵ���λ����Ϣ���ﵽ�����û���Ŀ�ġ�", 
		"����Ӧ����ľ�������ܻ���������ͷ�豸��ʵʱ����û��ճ����͵���û���˽���ַ��������˽Ȩ��",
		"����Ӧ�ÿ���ͨ���ж������������Ͼ��ܱ�������������һ���ֻ�����ʹ�û���������Լ���λ�á���������������Ϣ��",
		"���ڲ��ֶ���Ӧ�ú�ľ������ȡ�û��Ķ��ţ������ݷ��͵�Զ�̷��������������Զ����Ͷ��ţ����ŵȣ������û���ʧ�������ѡ�",
		"��������������ȡ�û������������ʷ��¼��Ϣ��ת��������̣�ͨ�������û��������¼�����ƹ��û�����Ȥ����Ӧ��档", 
		"������Ϣ����������Ӧ����ľ��������ȡ��ͨ����¼�û����˻�������ȡ�û���˽���ݣ�������ص���˽����й©���⡣",
		"���ɵ�·��ʶ���룺ľ����������ICCID���ж�Android�ֻ��û�����Ӫ�����ĸ������ݲ�ͬ��Ӫ������ȡ��ͬ�Ĺ�����ʽ��",
		"��һ��Ҳ���ڿ����������кţ�����ʵAndroid�ֻ���������Ϣ����Բ�ͬ�����ֻ���ȡ��ͬ�Ĺ�����ʽ��", 
		"�ƶ��豸����ʶ���룺���ڴ�����Ӧ�ó�����ȡIMEI��Ψһ��ʶһ���û��˻����ֻ���",
		"�����ƶ��û�ʶ���룺����Ӧ�õ�ȡIMSI��ʶ���ƶ��û�����ݡ�" };

	public static int[] icon_power = { R.drawable.location, R.drawable.contact,
			R.drawable.audio, R.drawable.phone_number, R.drawable.gps,
			R.drawable.internet, R.drawable.last_location, R.drawable.camera,
			R.drawable.sensor, R.drawable.sms, R.drawable.history_bookmarks,
			R.drawable.account, R.drawable.iccid, R.drawable.device_sn,
			R.drawable.imei, R.drawable.imsi, };

	public static ArrayList<Infos> get_Chart_Info_List(Context context) {
		Resources resources = context.getResources();
		Drawable drawable;
		ArrayList<Infos> localArrayList = new ArrayList<Infos>();
		Infos infos;
		for (int i = 0; i < titles.length; i++) {
			drawable = resources.getDrawable(icon_power[i]);
			infos = new Infos(drawable, titles[i]);
			
				infos.setEnable(Main_activityService.isColEnable(i));
		
			infos.setNumber(TaintInfoService.getNumber_Title(context, titles[i]));
			localArrayList.add(infos);

		}

		return localArrayList;
	}
	public static int get_index_taint(String taint){
		for (int i = 0; i < PowerService.taint.length; i++) {
			if (taint .equals(PowerService.taint[i])) {
				return i;
			}
		}
		return -1;
	}
	public static ArrayList<Infos> get_Chart_Info_List_old(Context context) {
		Resources resources = context.getResources();
		Drawable drawable;
		if (localArrayList != null) {
			return localArrayList;
		}
		localArrayList = new ArrayList<Infos>();
		Infos infos;
		for (int i = 0; i < titles.length; i++) {
			drawable = resources.getDrawable(icon_power[i]);
			infos = new Infos(drawable, titles[i]);
			localArrayList.add(infos);
		}

		return localArrayList;
	}

	public static ArrayList<String> get_Taint_List(Context context) {
		ArrayList<String> localArrayList = new ArrayList<String>();
		for (int i = 0;; i++) {
			if (i >= taint.length)
				return localArrayList;
			
				localArrayList.add(taint[i]);

		}
	}

	public static ArrayList<String> get_title_List(Context context) {
		ArrayList<String> localArrayList = new ArrayList<String>();
		for (int i = 0;; i++) {
			if (i >= titles.length)
				return localArrayList;
				localArrayList.add(titles[i]);

		}
	}

	public static int get_index_title(String title) {
		int index;
		for (index = 0; index < titles.length; index++) {
			if (title.equals(PowerService.titles[index])) {
				return index;
			}
		}
		return -1;
	}
}
