package com.appkefu.appkehu_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.util.StringUtils;

import com.appkefu.lib.interfaces.KFInterfaces;
import com.appkefu.lib.service.KFMainService;
import com.appkefu.lib.service.KFSettingsManager;
import com.appkefu.lib.service.KFXmppManager;
import com.appkefu.lib.utils.KFSLog;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HospitalOnlineActivity extends Activity {
	
	/*
	 ��ʾ������Ѿ����й��ɰ��Demo���������ֻ���ɾ��ԭ�ȵ�App���������д˹���
	 ����ʹ�ð����μ���http://appkefu.com/AppKeFu/tutorial-android.html
	
	 ��Ҫʹ��˵����
	 ��1������http://appkefu.com/AppKeFu/admin/��ע��/����Ӧ��/����ͷ���������ȡ��appkey����AnroidManifest.xml
	 		�е�com.appkefu.lib.appkey
	 ��2��������ʵ�Ŀͷ�����ʼ��mKefuUsername
	 ��3�������� KFInterfaces.visitorLogin(this); ������¼
	 ��4��������chatWithKeFu(mKefuUsername);��ͷ��Ự������mKefuUsername��Ҫ�滻Ϊ��ʵ�ͷ���
	 ��5����(��ѡ)
     	//�����ǳƣ������ڿͷ��ͻ��� �����Ļ���һ���ַ���(�����ڵ�¼�ɹ�֮����ܵ��ã�����Ч)
     	KFInterfaces.setVisitorNickname("�ÿ�1", this);
	 */
	
	int[] image = {
			R.drawable.hos_case, R.drawable.hos_chat, R.drawable.hos_eye, R.drawable.hos_eye_2,
			R.drawable.hos_instro, R.drawable.hos_nav, R.drawable.hos_other, R.drawable.hos_pro,
			R.drawable.hos_promotion 
		};
	
	private MyAdapter adapter = null;
	private ArrayList<Map<String, Object>> array;
	
	//�ͷ��û�������Ҫ��дΪ��ʵ�Ŀͷ��û�������Ҫ�������̨(http://appkefu.com/AppKeFu/admin/),����
	private String 			  mKefuUsername;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//��admin�Ự,ʵ��Ӧ������Ҫ��admin�滻Ϊ��ʵ�Ŀͷ��û���			
		mKefuUsername = "admin";
				
		GridViewInterceptor gv = (GridViewInterceptor) findViewById(R.id.gride);
		array = getData();
		adapter = new MyAdapter();
		gv.setDropListener(onDrop);
		gv.setAdapter(adapter);
		gv.setOnItemClickListener(new ItemClickEvent());
		
		//���ÿ����ߵ���ģʽ��Ĭ��Ϊtrue����Ҫ�رտ�����ģʽ��������Ϊfalse
		KFSettingsManager.getSettingsManager(this).setDebugMode(true);
		//��һ������¼
		KFInterfaces.visitorLogin(this);
		
	}

	private GridViewInterceptor.DropListener onDrop = new GridViewInterceptor.DropListener() {
		public void drop(int from, int to) {
			Map item = adapter.getItem(from);

			adapter.remove(item);
			adapter.insert(item, to);
		}
	};

	public class ImageList extends BaseAdapter {
		Activity activity;

		// construct
		public ImageList(Activity a) {
			activity = a;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return image.length;
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return image[position];
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ImageView iv = new ImageView(activity);
			iv.setImageResource(image[position]);
			return iv;
		}
	}

	class MyAdapter extends ArrayAdapter<Map<String, Object>> {

		MyAdapter() {
			super(HospitalOnlineActivity.this, R.layout.gridview_item, array);
		}

		public ArrayList<Map<String, Object>> getList() {
			return array;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.gridview_item, parent, false);
			}
			ImageView imageView = (ImageView) row.findViewById(R.id.img);
			imageView.setImageResource(Integer.valueOf(array.get(position).get("img").toString()));
			TextView textView = (TextView) row.findViewById(R.id.text);

			switch(position)
			{
			case 0:
				textView.setText(R.string.hos_intro);
				break;
			case 1:
				textView.setText(R.string.hos_pro);
				break;
			case 2:
				textView.setText(R.string.hos_subject);
				break;
			case 3:
				textView.setText(R.string.hos_nav);
				break;
			case 4:
				textView.setText(R.string.hos_yuyue);
				break;
			case 5:
				textView.setText(R.string.hos_chat);
				break;
			case 6:
				textView.setText(R.string.hos_shuang);
				break;
			case 7:
				textView.setText(R.string.hos_kaiyanjiao);
				break;
			case 8:
				textView.setText(R.string.hos_case);
				break;
			}
			//ҽԺ��顢ר���Ŷӡ��ר�⡢΢���ΰ�������������������ԤԼ��������ѯ
			//˫��Ƥ�����۽ǡ�����������Ŀ
			
			return (row);
		}
	}

	private ArrayList<Map<String, Object>> getData() {
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < image.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("img", image[i]);
			list.add(map);

		}
		return list;
	}

	class ItemClickEvent implements AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {
			
			TextView textView =  (TextView)view.findViewById(R.id.text);
			
			Toast.makeText(HospitalOnlineActivity.this, textView.getText(), Toast.LENGTH_SHORT).show();
			view.setPressed(false);
			view.setSelected(false);
			
			if(textView.getText().equals("ҽԺ���"))
			{
				Intent intent = new Intent(HospitalOnlineActivity.this, HosIntroActivity.class);
				startActivity(intent);
			}
			else if(textView.getText().equals("ר���Ŷ�"))
			{
				Intent intent = new Intent(HospitalOnlineActivity.this, HosProActivity.class);
				startActivity(intent);
			}
			else if(textView.getText().equals("�ר��"))
			{
				Intent intent = new Intent(HospitalOnlineActivity.this, HosSubjectActivity.class);
				startActivity(intent);
			}
			else if(textView.getText().equals("��������"))
			{
				Intent intent = new Intent(HospitalOnlineActivity.this, HosNavActivity.class);
				startActivity(intent);
			}
			else if(textView.getText().equals("����ԤԼ"))
			{
				Intent intent = new Intent(HospitalOnlineActivity.this, HosYuyueActivity.class);
				startActivity(intent);
			}
			else if(textView.getText().equals("������ѯ"))
			{
				
				chatWithKeFu(mKefuUsername);
			}
			else if(textView.getText().equals("˫��Ƥ"))
			{
				Intent intent = new Intent(HospitalOnlineActivity.this, HosShuangActivity.class);
				startActivity(intent);
			}
			else if(textView.getText().equals("���۽�"))
			{
				Intent intent = new Intent(HospitalOnlineActivity.this, HosKaiyanjiaoActivity.class);
				startActivity(intent);
			}
			else if(textView.getText().equals("΢���ΰ���"))
			{
				Intent intent = new Intent(HospitalOnlineActivity.this, HosCaseActivity.class);
				startActivity(intent);
			}
			
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		KFSLog.d("onStart");
		
		IntentFilter intentFilter = new IntentFilter();
		//�����������ӱ仯���
        intentFilter.addAction(KFMainService.ACTION_XMPP_CONNECTION_CHANGED);
        //������Ϣ
        intentFilter.addAction(KFMainService.ACTION_XMPP_MESSAGE_RECEIVED);

        registerReceiver(mXmppreceiver, intentFilter); 
	}


	@Override
	protected void onStop() {
		super.onStop();

		KFSLog.d("onStop");
		
        unregisterReceiver(mXmppreceiver);
	}
	

	//������ѯ�Ի���
	private void chatWithKeFu(String kefuUsername)
	{
		KFInterfaces.startChatWithKeFu(this,
				kefuUsername, //�ͷ��û���
				"���ã�����΢�ͷ�С���飬������ʲô���԰�����?",  //�ʺ���
				"��ѯ�ͷ�");//�Ự���ڱ���
	}
	
	//����������״̬����ʱͨѶ��Ϣ���ͷ�����״̬
	private BroadcastReceiver mXmppreceiver = new BroadcastReceiver() 
	{
        public void onReceive(Context context, Intent intent) 
        {
            String action = intent.getAction();
            //����������״̬
            if (action.equals(KFMainService.ACTION_XMPP_CONNECTION_CHANGED))//��������״̬
            {
                updateStatus(intent.getIntExtra("new_state", 0));        
            }
            //��������ʱͨѶ��Ϣ
            else if(action.equals(KFMainService.ACTION_XMPP_MESSAGE_RECEIVED))//������Ϣ
            {
            	String body = intent.getStringExtra("body");
            	String from = StringUtils.parseName(intent.getStringExtra("from"));
            	
            	KFSLog.d("body:"+body+" from:"+from);
            }

        }
    };


  //���ݼ����������ӱ仯������½�����ʾ
    private void updateStatus(int status) {

    	switch (status) {
            case KFXmppManager.CONNECTED:
            	KFSLog.d("connected");
            	//mTitle.setText("΢�ͷ�(�ͷ�Demo)");

        		//�����ǳƣ������ڿͷ��ͻ��� �����Ļ���һ���ַ���(�����ڵ�¼�ɹ�֮����ܵ��ã�����Ч)
        		//KFInterfaces.setVisitorNickname("�ÿ�1", this);

                break;
            case KFXmppManager.DISCONNECTED:
            	KFSLog.d("disconnected");
            	//mTitle.setText("΢�ͷ�(�ͷ�Demo)(δ����)");
                break;
            case KFXmppManager.CONNECTING:
            	KFSLog.d("connecting");
            	//mTitle.setText("΢�ͷ�(�ͷ�Demo)(��¼��...)");
            	break;
            case KFXmppManager.DISCONNECTING:
            	KFSLog.d("connecting");
            	//mTitle.setText("΢�ͷ�(�ͷ�Demo)(�ǳ���...)");
                break;
            case KFXmppManager.WAITING_TO_CONNECT:
            case KFXmppManager.WAITING_FOR_NETWORK:
            	KFSLog.d("waiting to connect");
            	//mTitle.setText("΢�ͷ�(�ͷ�Demo)(�ȴ���)");
                break;
            default:
                throw new IllegalStateException();
        }
    }
    
}
















