package com.example.musicname;

import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements OnClickListener {
	private final static String fileName = "*.mp3";
	private List resultList = new ArrayList();
	private List absoluteList = new ArrayList();
	private ListView listView;
	static SeekBar seekBar;
	private int musicPosition;
	private MusicAdapter musicAdapter;
	private ViewFlipper viewFlipper;
	private float startX;
	private float startY;
	private Animation enter_Lefttoright;
	private Animation out_Lefttoright;
	private Animation enter_Righttoleft;
	private Animation out_Righttoleft;
	private Intent intent;
	private BaseHelper baseHelper;
	private ContentValues values;
	private int musicNun = 0;
	SharedPreferences sharedPreferences;
	Editor editor;
	private PtrFrameLayout mPtrFrameLayout;
	private List<MusicInformation> musicInformation = new ArrayList<MusicInformation>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sharedPreferences = getSharedPreferences("ljq",
				Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
		editor = sharedPreferences.edit();// 获取编辑器
		loginInformation();
		String token = sharedPreferences.getString("token", "");
		if (token == "") {
	//		findMP3(getSDPath(), fileName, resultList);
			findMP3();
			selectShow();
		
		} else {
			selectShow();
	
		}
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				// TODO Auto-generated method stub
	//			findMP3(getSDPath(), fileName, resultList);
				findMP3();
				selectShow();
				
				mPtrFrameLayout.refreshComplete();

			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {
				// TODO Auto-generated method stub
				return PtrDefaultHandler.checkContentCanBePulledDown(frame,
						listView, header);
			}
		});

		listView.setAdapter(musicAdapter);
		// mPtrFrameLayout.autoRefresh();
	}

	void loginInformation() {

		listView = (ListView) findViewById(R.id.music_player);
		seekBar = (SeekBar) findViewById(R.id.seekBar1);
		
		
		findViewById(R.id.bt_previous).setOnClickListener(this);
		findViewById(R.id.bt_next).setOnClickListener(this);
		findViewById(R.id.bt_play).setOnClickListener(this);
		findViewById(R.id.bt_stop).setOnClickListener(this);
		findViewById(R.id.bt_pause).setOnClickListener(this);

		mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.load_more_grid_view_ptr_frame);
		mPtrFrameLayout.setLoadingMinTime(1000);

		seekBar.setOnSeekBarChangeListener(sChangeListener);
		viewFlipper = (ViewFlipper) findViewById(R.id.view);
		enter_Lefttoright = AnimationUtils.loadAnimation(this,
				R.anim.enter_lefttoright);
		out_Lefttoright = AnimationUtils.loadAnimation(this,
				R.anim.out_lefttoright);
		enter_Righttoleft = AnimationUtils.loadAnimation(this,
				R.anim.enter_righttoleft);
		out_Righttoleft = AnimationUtils.loadAnimation(this,
				R.anim.out_righttoleft);
		intent = new Intent(MainActivity.this, Musicserver.class);

		musicAdapter = new MusicAdapter(MainActivity.this);

		baseHelper = new BaseHelper(MainActivity.this);
		values = new ContentValues();

		MusicBoxReceiver mReceiver = new MusicBoxReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConstUtil.MUSICBOX_ACTION);
		registerReceiver(mReceiver, filter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				musicPosition = position;

				intent.putExtra("music", absoluteList.get(musicPosition)
						.toString());
				startService(intent);

			}
		});
	}

	OnSeekBarChangeListener sChangeListener = new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			// 当拖动停止后，控制mediaPlayer播放指定位置的音乐
			Musicserver.mediaPlayer.seekTo(seekBar.getProgress());
			Musicserver.isChanging = false;
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

		}
	};

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		// TODO Auto-generated method stub
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			startX = ev.getX();
			startY = ev.getY();
		}
		if (ev.getAction() == MotionEvent.ACTION_UP) {
			float endX = ev.getX();
			float endY = ev.getY();
			if (changeY(endY)) {
				if (endX > startX) {
					viewFlipper.setInAnimation(enter_Lefttoright);
					viewFlipper.setOutAnimation(out_Lefttoright);
					viewFlipper.showNext();// 显示下一页
				}
				if (endX < startX) {
					viewFlipper.setInAnimation(enter_Righttoleft);
					viewFlipper.setOutAnimation(out_Righttoleft);
					viewFlipper.showPrevious();// 显示上一页
				}
				return true;
			}
		}

		return super.dispatchTouchEvent(ev);
	}

	public boolean changeY(float Y) {
		if (Y < startY + 100) {
			if (startY - 100 < Y) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_previous:

			break;
		case R.id.bt_next:

			break;
		case R.id.bt_play:

			break;
		case R.id.bt_stop:

			break;
		case R.id.bt_pause:

			break;

		default:
			break;
		}
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			deleteAll();
			insert();
		
		
		}

	};
	
//	private void findMP3(final String baseDirName, final String targetFileName,
//			final List fileList) {
//		
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub	
//				String tempName = null;
//				fileList.clear();
//				absoluteList.clear();
//				if (baseDirName == null) {
//					Toast.makeText(MainActivity.this, "不存在SD卡", 1000).show();
//				} else {
//					File file = new File(baseDirName);
//					if (!file.exists() || !file.isDirectory()) {
//						Toast.makeText(MainActivity.this,
//								"文件查找失败：" + baseDirName + "不是一个目录！", 1000)
//								.show();
//					} else {
//						String[] filelist = file.list();
//								
//						for (int i = 0; i < filelist.length; i++) {
//							File readfile = new File(baseDirName + "/"
//									+ filelist[i]);
//							if (!readfile.isDirectory()) {
//								tempName = readfile.getName();
//								if ((tempName.trim().toLowerCase()
//										.endsWith(".mp3"))) {
//									fileList.add(tempName);
//									absoluteList.add(readfile.getAbsolutePath());
//									
//								
//									
//								}
//							} else if (readfile.isDirectory()) {
//								findMP3(baseDirName + "/" + filelist[i],
//										targetFileName, fileList);
//							}
//
//						}
//						
//					}
//					
//					editor.putString("token", "liuwenxing");
//					editor.commit();// 提交修改
//					 Message msg = new Message();
//					 msg.obj = fileList;
//					 
//					 handler.sendMessage(msg);
//				}
//
//			}
//
//		}).start();
//		
//	}

	private void findMP3(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				musicInformation.clear();
				ContentResolver cr = MainActivity.this.getContentResolver();
				Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,  
		                null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
				while(cursor.moveToNext()){
					String dataurl = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));//歌曲路径
					String tilte = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)); //歌曲名称
					String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));//歌手名
					int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));//总播放时长
					long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)); //歌曲大小
					Log.d("Time",size+"");
					if(size>2400000){
						MusicInformation mm = new MusicInformation();
						mm.setMusicPath(dataurl);
						mm.setMusicName(tilte);
						mm.setMusicPlayer(artist);
						mm.setMusicTime(duration);
						musicInformation.add(mm);
					}
					
				}
				editor.putString("token", "liuwenxing");
				editor.commit();// 提交修改
				 Message msg = new Message();
				 
				 
				 handler.sendMessage(msg);
			}
			
		}).start();
		
		
	}
	

	// 判断是否存在SD卡
	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			return sdDir.toString();
		}
		return null;

	}

	/**
	 * 向后台Service发送控制广播
	 * 
	 * @param state
	 *            int state 控制状态码
	 * */
	protected void sendBroadcastToService(int state) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setAction(ConstUtil.MUSICSERVICE_ACTION);
		intent.putExtra("control", state);
		// 向后台Service发送播放控制的广播
		sendBroadcast(intent);
	}

	// 创建一个广播接收器用于接收后台Service发出的广播
	class MusicBoxReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			// 获取Intent中的current消息，current代表当前正在播放的歌曲
			int current = intent.getIntExtra("current", -1);

		}

	}

	public void selectShow() {
		SQLiteDatabase db = baseHelper.getReadableDatabase();
		ArrayList<MusicInformation> list = new ArrayList<MusicInformation>();
		Cursor cursor = db.query("music", new String[] { "musicname",
				"musicpath","musicplayer","musictime" }, null, null, null, null, null);

		while (cursor.moveToNext()) {
			MusicInformation music = new MusicInformation();
			music.setMusicName(cursor.getString(cursor
					.getColumnIndex("musicname")));
			music.setMusicPath(cursor.getString(cursor
					.getColumnIndex("musicpath")));
			music.setMusicPlayer(cursor.getString(cursor
					.getColumnIndex("musicplayer")));
			music.setMusicTime(cursor.getInt(cursor
					.getColumnIndex("musictime")));
			
			Log.d("name", music.getMusicName());
			list.add(music);
			db.close();
		}
		editor.putInt("num", list.size());
		editor.commit();
		musicAdapter.removeAll();
		musicAdapter.addAll(list);

	}

	public void deleteAll() {
		SQLiteDatabase db = baseHelper.getWritableDatabase();
		db.delete("music", null, null);
		db.close();
	}
	public void insert(){
		SQLiteDatabase db = baseHelper.getWritableDatabase();
		for(int i=0;i<musicInformation.size();i++){
		values.put("musicname", musicInformation.get(i).getMusicName());
		values.put("musicpath",musicInformation.get(i).getMusicPath());
		values.put("musicplayer",musicInformation.get(i).getMusicPlayer());
		values.put("musictime",musicInformation.get(i).getMusicTime());
		
		db.insert("music", null, values);
		}
		db.close();
	}
}
