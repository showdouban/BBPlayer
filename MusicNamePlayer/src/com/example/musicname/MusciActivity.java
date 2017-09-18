/**
 * 
 */
package com.example.musicname;

import java.util.ArrayList;
import java.util.List;

import com.base.Player;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Title: MusciActivity.java Description: Copyright:Copyright(c)2015 Company:
 * 四川久聚科技股份有限公司 CreateTime:2015-8-31 下午9:00:38
 * 
 * @author liuwenxing
 * @version 1.0
 */
public class MusciActivity extends FragmentActivity implements OnClickListener {
	private ViewPager pager;
	private MyFragmentPagerAdapter adapter;
	static SeekBar seekBar;
	static TextView tv_Time;
	static TextView tv_Name;
	static TextView tv_Music_Time;
	static String musicName = "";
	static boolean btnChange = false;
	static Button bt_Pause;
	static SharedPreferences sPreferences;
	private Editor editor;
	private long exitTime = 0;
	MotionEvent event = null;
	NotificationBroadcastReceiver mReceiver;
	private static final String BTN = "btn";
	private static final int BTN_PREVIOUS = 1;
	private static final int BTN_NEXT = 2;
	private static final int BTN_START = 3;
	private static final int BTN_PAUSE = 4;
	private static final int BTN_BACK = 5;
	private static final int BTN_HOME = 6;
	static RemoteViews remoteViews;
	private int count = 0;
	static Notification notification;
	static NotificationManager notificationManager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_music);
		seekBar = (SeekBar)findViewById(R.id.seekBar1);
		tv_Time = (TextView) findViewById(R.id.tv_time);
		tv_Name = (TextView) findViewById(R.id.tv_name);
		tv_Music_Time = (TextView) findViewById(R.id.tv_music_time);
		bt_Pause = (Button) findViewById(R.id.bt_pause);
		seekBar.setOnSeekBarChangeListener(sChangeListener);
		
		bt_Pause.setOnClickListener(this);
		findViewById(R.id.bt_next).setOnClickListener(this);
		findViewById(R.id.bt_stop).setOnClickListener(this);
		findViewById(R.id.bt_previous).setOnClickListener(this);

		sPreferences = getSharedPreferences("music",
				Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
		Musicserver.musicNum = MusciActivity.sPreferences.getInt("position", 0);
		MusciActivity.seekBar.setMax(sPreferences.getInt("time", 0));

		Log.d("ddasda", sPreferences.getInt("time", 0) + "");
		editor = sPreferences.edit();// 获取编辑器
		editor.putBoolean("token", false);
		editor.commit();
		if (!btnChange) {
			bt_Pause.setText("播放");
		}

		String name = sPreferences.getString("filename", "歌曲名");
		// String time = sPreferences.getString("musictime", "00:00");
		ConstUtil.musicName = name;
		tv_Name.setText(name);
		tv_Time.setText("00:00");

		initView();
		showNotification();
	}

	private void initView() {
		pager = (ViewPager) findViewById(R.id.pager);

		ArrayList<Fragment> views = new ArrayList<Fragment>();

		views.add(new MusicFragment());
		views.add(new PlayFragment());
		views.add(new DownFragment());

		adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), views);

		pager.setAdapter(adapter);
		pager.setOffscreenPageLimit(3);//
	}

	OnSeekBarChangeListener sChangeListener = new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			// 当拖动停止后，控制mediaPlayer播放指定位置的音乐
			if (MusciActivity.sPreferences.getBoolean("token", false)) {
				Musicserver.mediaPlayer.seekTo(seekBar.getProgress());
				Musicserver.isChanging = false;
			} else {
				Musicserver.isChanging = false;
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			Musicserver.isChanging = true;
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			String musicTime = Musicserver.timeChange(progress);
			MusciActivity.tv_Time.setText(musicTime);
			

		}
	};

	class MyFragmentPagerAdapter extends FragmentPagerAdapter {
		ArrayList<Fragment> list;

		public MyFragmentPagerAdapter(FragmentManager fm,
				ArrayList<Fragment> list) {
			super(fm);
			this.list = list;

		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Fragment getItem(int arg0) {
			return list.get(arg0);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.bt_previous:

			Intent intent_previous = new Intent();
			intent_previous.setAction(ConstUtil.MUSICSERVICE_ACTION);
			intent_previous.putExtra("control", ConstUtil.STATE_PREVIOUS);
			sendBroadcast(intent_previous);

			// int position1 = MusciActivity.sPreferences.getInt("position",0);
			// MusicInformation mm1 =
			// (MusicInformation)MusicAdapter.list.get(position1-1);
			// Intent intentPrevious = new Intent(this,Musicserver.class);
			// intentPrevious.putExtra("path", mm1.getMusicPath());
			// startService(intentPrevious);

			break;
		case R.id.bt_next:
			Intent intent_next = new Intent();
			intent_next.setAction(ConstUtil.MUSICSERVICE_ACTION);
			intent_next.putExtra("control", ConstUtil.STATE_NEXT);
			sendBroadcast(intent_next);

			// int position2 = MusciActivity.sPreferences.getInt("position",0);
			// MusicInformation mm2 =
			// (MusicInformation)MusicAdapter.list.get(position2+1);
			// Intent intentNext = new Intent(this,Musicserver.class);
			// intentNext.putExtra("path", mm2.getMusicPath());
			// startService(intentNext);

			break;
		// case R.id.bt_play:
		//
		// break;
		case R.id.bt_stop:
			Intent intent = new Intent();
			intent.setAction(ConstUtil.MUSICSERVICE_ACTION);
			intent.putExtra("control", ConstUtil.STATE_STOP);
			// 向后台Service发送播放控制的广播
			sendBroadcast(intent);
			break;
		case R.id.bt_pause:
			if (btnChange) {
				Intent intent_pause = new Intent();
				intent_pause.setAction(ConstUtil.MUSICSERVICE_ACTION);
				intent_pause.putExtra("control", ConstUtil.STATE_PAUSE);
				Log.d("暂停", "启动");
				bt_Pause.setText("播放");
				remoteViews.setTextViewText(R.id.bt_pause, "播放");
				btnChange = false;
				sendBroadcast(intent_pause);
			} else {
				if (MusciActivity.sPreferences.getBoolean("token", false)) {
					Intent intent_play = new Intent();
					intent_play.setAction(ConstUtil.MUSICSERVICE_ACTION);
					intent_play.putExtra("control", ConstUtil.STATE_PLAY);
					sendBroadcast(intent_play);
					Log.d("启动过", "sasdasd");
				} else {
					String path = MusciActivity.sPreferences.getString("path",
							"");
					Log.d("刚开启", path);
					if (path != "") {
						Intent intentStart = new Intent(this, Musicserver.class);
						intentStart.putExtra("path", path);
						editor.putBoolean("token", true);
						editor.commit();
						startService(intentStart);
					}
				}
				remoteViews.setTextViewText(R.id.bt_pause, "暂停");
				bt_Pause.setText("暂停");
				btnChange = true;
			}
			break;

		default:
			break;
		}
		notificationManager.notify(0, notification);
	}

	// public static boolean isServiceRunning(Context mContext, String
	// className) {
	// boolean isRunning = false;
	// ActivityManager activityManager = (ActivityManager) mContext
	// .getSystemService(Context.ACTIVITY_SERVICE);
	// List<ActivityManager.RunningServiceInfo> serviceList = activityManager
	// .getRunningServices(30);
	// if (!(serviceList.size() > 0)) {
	// return false;
	// }
	// for (int i = 0; i < serviceList.size(); i++) {
	// if (serviceList.get(i).service.getClassName().equals(className) == true)
	// {
	// isRunning = true;
	// break;
	// }
	// }
	// return isRunning;
	// }


	private void showNotification() {
		// 创建一个NotificationManager的引用
		intiReceiver();
		loadBtn();
		notificationManager = (NotificationManager) this
				.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		//
		// NotificationCompat.Builder builder = new
		// NotificationCompat.Builder(MusciActivity.this);
		// builder.setOngoing(false);
		// builder.setAutoCancel(false);
		// builder.setTicker("我的音乐");
		// // builder.setContent(remoteViews);
		// // 定义Notification的各种属性
		// Notification notification = builder.build();
		// //FLAG_AUTO_CANCEL 该通知能被状态栏的清除按钮给清除掉
		// //FLAG_NO_CLEAR 该通知不能被状态栏的清除按钮给清除掉
		// //FLAG_ONGOING_EVENT 通知放置在正在运行
		// //FLAG_INSISTENT 是否一直进行，比如音乐一直播放，知道用户响应
		// notification.flags |= Notification.FLAG_ONGOING_EVENT; //
		// 将此通知放到通知栏的"Ongoing"即"正在运行"组中
		// notification.flags |= Notification.FLAG_NO_CLEAR; //
		// 表明在点击了通知栏中的"清除通知"后，此通知不清除，经常与FLAG_ONGOING_EVENT一起使用
		// notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		// //DEFAULT_ALL 使用所有默认值，比如声音，震动，闪屏等等
		// //DEFAULT_LIGHTS 使用默认闪光提示
		// //DEFAULT_SOUNDS 使用默认提示声音
		// //DEFAULT_VIBRATE 使用默认手机震动，需加上<uses-permission
		// android:name="android.permission.VIBRATE" />权限
		// //notification.defaults = Notification.DEFAULT_LIGHTS;
		// //叠加效果常量
		// //notification.defaults=Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND;
		// // notification.ledARGB = Color.BLUE;
		// // notification.ledOnMS =5000; //闪光时间，毫秒
		//
		//
		// Intent notificationIntent =new Intent(MusciActivity.this,
		// Musicserver.class); // 点击该通知后要跳转的Activity
		// PendingIntent contentItent = PendingIntent.getActivity(this, 0,
		// notificationIntent, 0);
		//
		//
		// // 设置通知的事件消息
		// // CharSequence contentTitle ="我的音乐标题"; // 通知栏标题
		// // CharSequence contentText ="我的音乐内容"; // 通知栏内容
		//
		// notification.setLatestEventInfo(this, "679","789", contentItent);
		//
		// // 把Notification传递给NotificationManager
		// notificationManager.notify(0, notification);

		notification = new Notification();

		notification.icon = R.drawable.ic_launcher;
		notification.tickerText = "我的音乐";

		notification.contentView = remoteViews;
		notification.flags |= Notification.FLAG_ONGOING_EVENT; // 将此通知放到通知栏的"Ongoing"即"正在运行"组中
//		notification.flags |= Notification.FLAG_NO_CLEAR; // 表明在点击了通知栏中的"清除通知"后，此通知不清除，经常与FLAG_ONGOING_EVENT一起使用
//		notification.flags |= Notification.FLAG_SHOW_LIGHTS;

		// 使用自定义下拉视图时，不需要再调用setLatestEventInfo()方法

		Intent intent = new Intent(MusciActivity.this,MusciActivity.class);
		// 但是必须定义 contentIntent
		PendingIntent pd = PendingIntent.getActivity(MusciActivity.this, 6,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.contentIntent = pd;
		notificationManager.notify(0, notification);
	}

	class NotificationBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			remoteViews = notification.contentView;
			if (action.equals(ConstUtil.ACTION_BTN)) {
				int btn_id = intent.getIntExtra(BTN, 0);

				// remoteViews.setTextViewText(R.id.bt_pause, ""+count);
				// AppWidgetManager manager =
				// AppWidgetManager.getInstance(context);
				// manager.updateAppWidget(new
				// ComponentName(MusciActivity.this.getPackageName(),
				// MusciActivity.class.getName()), remoteViews);
				switch (btn_id) {
				case BTN_PREVIOUS:

					Intent intent_previous = new Intent();
					intent_previous.setAction(ConstUtil.MUSICSERVICE_ACTION);
					intent_previous.putExtra("control",
							ConstUtil.STATE_PREVIOUS);
					sendBroadcast(intent_previous);
					break;
				case BTN_NEXT:

					Intent intent_next = new Intent();
					intent_next.setAction(ConstUtil.MUSICSERVICE_ACTION);
					intent_next.putExtra("control", ConstUtil.STATE_NEXT);
					sendBroadcast(intent_next);
					break;
				case BTN_PAUSE:

					if (btnChange) {
						Intent intent_pause = new Intent();
						intent_pause.setAction(ConstUtil.MUSICSERVICE_ACTION);
						intent_pause.putExtra("control", ConstUtil.STATE_PAUSE);
						Log.d("暂停", "启动");
						bt_Pause.setText("播放");
						 remoteViews.setTextViewText(R.id.bt_pause, "播放");
						 notificationManager.notify(0, notification);
						btnChange = false;
						sendBroadcast(intent_pause);
					} else {
						if (MusciActivity.sPreferences.getBoolean("token",
								false)) {
							Intent intent_play = new Intent();
							intent_play
									.setAction(ConstUtil.MUSICSERVICE_ACTION);
							intent_play.putExtra("control",
									ConstUtil.STATE_PLAY);
							sendBroadcast(intent_play);
							Log.d("启动过", "sasdasd");
						} else {
							String path = MusciActivity.sPreferences.getString(
									"path", "");
							Log.d("刚开启", path);
							if (path != "") {
								Intent intentStart = new Intent(
										MusciActivity.this, Musicserver.class);
								intentStart.putExtra("path", path);
								startService(intentStart);
							}
						}
						bt_Pause.setText("暂停");
						remoteViews.setTextViewText(R.id.bt_pause, "暂停");
						notificationManager.notify(0, notification);
						btnChange = true;
					}
					break;
				case BTN_BACK:
					clearNotification();
					MusciActivity.this.finish();
					stopService(new Intent(MusciActivity.this,
							Musicserver.class));
					unregeisterReceiver();
					
					break;
				default:
					break;
				}

			}
		}
	}

	private void loadBtn() {
		remoteViews = new RemoteViews(getPackageName(),
				R.layout.notification_item);
		Intent intentPrevious = new Intent(ConstUtil.ACTION_BTN);
		intentPrevious.putExtra(BTN, BTN_PREVIOUS);
		PendingIntent intentpi1 = PendingIntent.getBroadcast(this, 0,
				intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.bt_previous, intentpi1);

		Intent intentNext = new Intent(ConstUtil.ACTION_BTN);
		intentNext.putExtra(BTN, BTN_NEXT);
		PendingIntent intentpi2 = PendingIntent.getBroadcast(this, 1,
				intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.bt_next, intentpi2);

		Intent intentBack = new Intent(ConstUtil.ACTION_BTN);
		intentBack.putExtra(BTN, BTN_BACK);
		PendingIntent intentpi4 = PendingIntent.getBroadcast(this, 2,
				intentBack, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.back, intentpi4);

		Intent intentPause = new Intent(ConstUtil.ACTION_BTN);
		intentPause.putExtra(BTN, BTN_PAUSE);
		PendingIntent intentpi3 = PendingIntent.getBroadcast(this, 3,
				intentPause, PendingIntent.FLAG_UPDATE_CURRENT); // 0\1\2\3
																	// 区分跳转的编号，否则将只得到一个pendingIntent,即intentpi3
		remoteViews.setOnClickPendingIntent(R.id.bt_pause, intentpi3);
		remoteViews.setTextViewText(R.id.tv_music, ConstUtil.musicName);
		remoteViews.setTextViewText(R.id.bt_pause, "播放");

	}

	private void intiReceiver() {
		mReceiver = new NotificationBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ConstUtil.ACTION_BTN);
		getApplicationContext().registerReceiver(mReceiver, intentFilter);
	}

	private void unregeisterReceiver() {
		if (mReceiver != null) {
			getApplicationContext().unregisterReceiver(mReceiver);
			mReceiver = null;
		}
	}

	// 删除通知
	private void clearNotification() {
		// 启动后删除之前我们定义的通知

		notificationManager.cancel(0);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 1000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();

			} else {
				this.finish();
				stopService(new Intent(this, Musicserver.class));
				unregeisterReceiver();
				clearNotification();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	

}
