/**
 * 
 */
package com.example.musicname;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class Musicserver extends Service implements OnBufferingUpdateListener, OnCompletionListener,
OnPreparedListener {

	Timer mTimer;
	TimerTask mTimerTask;
	static boolean isChanging = false;// 互斥变量，防止定时器与SeekBar拖动时进度冲突
	// 创建一个媒体播放器的对象
	static MediaPlayer mediaPlayer;
	// 当前的播放的音乐
	int current = 0;
	// 当前播放状态
	int state = ConstUtil.STATE_NON;
	// 记录Timer运行状态
	boolean isTimerRunning = false;
	Editor editor;
	static int musicNum = 0;
	MusicSercieReceiver receiver;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// 注册接收器
		receiver = new MusicSercieReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConstUtil.MUSICSERVICE_ACTION);
		registerReceiver(receiver, filter);
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setOnBufferingUpdateListener(this);
		mediaPlayer.setOnPreparedListener(this);
		editor = MusciActivity.sPreferences.edit();
		editor.putBoolean("token", true);
		editor.commit();
		// 为mediaPlayer的完成事件创建监听器
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mTimer.cancel();// 取消定时器
				musicNum = musicNum + 1;
				MusicInformation mf = (MusicInformation) MusicAdapter.list
						.get(musicNum);
				prepareAndPlay(mf.getMusicPath());
				loadInformation(mf.getMusicName(), mf.getMusicTime());
			}
		});
		
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		playPic();
		final String path = intent.getStringExtra("path");
	
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				prepareAndPlay(path);
			}
		}).start();
		
		musicNum = MusciActivity.sPreferences.getInt("position", 0);
		state = ConstUtil.STATE_PLAY;
		
	}

	// /**
	// * 装载和播放音乐
	// * @param index int index 播放第几首音乐的索引
	// * */
	// protected void prepareAndPlay(int index) {
	// // TODO Auto-generated method stub
	// if (isTimerRunning) {//如果Timer正在运行
	// mTimer.cancel();//取消定时器
	// isTimerRunning=false;
	// }
	// if (index>2) {
	// current=index=0;
	// }
	// if (index<0) {
	// current=index=2;
	// }
	// //发送广播停止前台Activity更新界面
	// Intent intent=new Intent();
	// intent.putExtra("current", index);
	// intent.setAction(ConstUtil.MUSICBOX_ACTION);
	// sendBroadcast(intent);
	// try {
	//
	// //准备播放音乐
	// mediaPlayer.prepare();
	// //播放音乐
	// mediaPlayer.start();
	// //getDuration()方法要在prepare()方法之后，否则会出现Attempt to call getDuration without
	// a valid mediaplayer异常
	// MainActivity.seekBar.setMax(mediaPlayer.getDuration());//设置SeekBar的长度
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// //----------定时器记录播放进度---------//
	// mTimer = new Timer();
	// mTimerTask = new TimerTask() {
	// @Override
	// public void run() {
	// isTimerRunning=true;
	// if(isChanging==true)//当用户正在拖动进度进度条时不处理进度条的的进度
	// return;
	// MainActivity.seekBar.setProgress(mediaPlayer.getCurrentPosition());
	// }
	// };
	// //每隔10毫秒检测一下播放进度
	// mTimer.schedule(mTimerTask, 0, 10);
	// }
	//
	protected void prepareAndPlay(String path) {
		
		if (isTimerRunning) {// 如果Timer正在运行
			mTimer.cancel();// 取消定时器
			isTimerRunning = false;
		}
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(path);
			// 准备播放音乐
			mediaPlayer.prepare();
			// getDuration()方法要在prepare()方法之后，否则会出现Attempt to call getDuration
			// without a valid mediaplayer异常
			MusciActivity.seekBar.setMax(mediaPlayer.getDuration());// 设置SeekBar的长度
			Log.e("成功", mediaPlayer.getDuration() + "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 播放音乐
		mediaPlayer.start();
		state = ConstUtil.STATE_PLAY;
		// ----------定时器记录播放进度---------//
		mTimer = new Timer();
		mTimerTask = new TimerTask() {
			@Override
			public void run() {
				isTimerRunning = true;
				if (isChanging == true)// 当用户正在拖动进度进度条时不处理进度条的的进度
					return;
				MusciActivity.seekBar.setProgress(mediaPlayer
						.getCurrentPosition());
				// Log.d("sss",mediaPlayer.getCurrentPosition()+"");
			}
		};
		// 每隔10毫秒检测一下播放进度
		mTimer.schedule(mTimerTask, 0, 10);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	// 创建广播接收器用于接收前台Activity发去的广播
	class MusicSercieReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int control = intent.getIntExtra("control", -1);
			switch (control) {
			case ConstUtil.STATE_PLAY:// 播放音乐
				playPic();
				

				if (state == ConstUtil.STATE_PAUSE) {// 如果原来状态是暂停
					mediaPlayer.start();
					musicNum = MusciActivity.sPreferences.getInt("position", 0);
					// Log.d("重启",path);
				} else if (state != ConstUtil.STATE_PLAY) {

				}
				MusciActivity.btnChange = true;
				ConstUtil.play = "暂停";
				MusciActivity.remoteViews.setTextViewText(R.id.tv_music, ConstUtil.musicName);
				MusciActivity.notificationManager.notify(0, MusciActivity.notification);
				MusciActivity.bt_Pause.setText(ConstUtil.play);
				
				state = ConstUtil.STATE_PLAY;
				break;
			case ConstUtil.STATE_PAUSE:// 暂停播放
				if (state == ConstUtil.STATE_PLAY) {
					mediaPlayer.pause();
					state = ConstUtil.STATE_PAUSE;
					MusciActivity.btnChange = false;
					ConstUtil.play = "播放";
					MusciActivity.remoteViews.setTextViewText(R.id.tv_music, ConstUtil.musicName);
					MusciActivity.notificationManager.notify(0, MusciActivity.notification);
					MusciActivity.bt_Pause.setText(ConstUtil.play);
					pausePic();
				}
				break;
			case ConstUtil.STATE_STOP:// 停止播放
				if (state == ConstUtil.STATE_PLAY
						|| state == ConstUtil.STATE_PAUSE) {
					mediaPlayer.stop();
					MusciActivity.btnChange = false;
					state = ConstUtil.STATE_STOP;
					MusciActivity.btnChange = false;
					ConstUtil.play = "播放";
					MusciActivity.remoteViews.setTextViewText(R.id.tv_music, ConstUtil.musicName);
					MusciActivity.notificationManager.notify(0, MusciActivity.notification);
					MusciActivity.bt_Pause.setText(ConstUtil.play);
				}
				break;
			case ConstUtil.STATE_PREVIOUS:// 上一首
				playPic();
			
				if (musicNum > 0) {
					musicNum = musicNum - 1;
					MusicInformation mm1 = (MusicInformation) MusicAdapter.list
							.get(musicNum);

					Log.d("MusicInformation", mm1.getMusicName());
					state = ConstUtil.STATE_PLAY;
					prepareAndPlay(mm1.getMusicPath());
					// loadMusic(mm1, musicNum);
					loadInformation(mm1.getMusicName(), mm1.getMusicTime());

					MusciActivity.btnChange = true;
					ConstUtil.play = "暂停";
					MusciActivity.bt_Pause.setText(ConstUtil.play);
					
				} else {
					// Toast.makeText(this, "已经是第一首了", 1000).show();
				}
				break;
			case ConstUtil.STATE_NEXT:// 下一首
				// int position2 =
				// MusciActivity.sPreferences.getInt("position",0);
				playPic();
				
				if (musicNum < MusicAdapter.list.size() - 1) {
					musicNum = musicNum + 1;
					MusicInformation mm2 = (MusicInformation) MusicAdapter.list
							.get(musicNum);
					Log.d("MusicInformation", mm2.getMusicName());
					state = ConstUtil.STATE_PLAY;
					prepareAndPlay(mm2.getMusicPath());
					// loadMusic(mm2, position2+1);
					loadInformation(mm2.getMusicName(), mm2.getMusicTime());
					MusciActivity.btnChange = true;
					ConstUtil.play = "暂停";
					MusciActivity.bt_Pause.setText(ConstUtil.play);
					
				}
				break;
			default:
				break;
			}
		}

	}
	
	void playPic(){
		Intent intent_play_pic = new Intent();
		intent_play_pic.setAction(ConstUtil.PLAYFRAMGMENT_ACTION);
		intent_play_pic.putExtra("musicinfo", ConstUtil.STATE_PLAY);
		
		sendBroadcast(intent_play_pic);
	}
	void pausePic(){
		Intent intent_pause_pic = new Intent();
		intent_pause_pic.setAction(ConstUtil.PLAYFRAMGMENT_ACTION);
		intent_pause_pic.putExtra("musicinfo", ConstUtil.STATE_PAUSE);
		
		sendBroadcast(intent_pause_pic);
	}
	void loadMusic(int positionNum) {
		MusicInformation mm = (MusicInformation) MusicAdapter.list
				.get(musicNum);
		String fileName = mm.getMusicName();
		String path = mm.getMusicPath();

		int musicTime = mm.getMusicTime();
		final String time = timeChange(musicTime);

		editor.putString("filename", fileName);
		editor.putString("path", path);
		editor.putString("musictime", time);
		editor.putInt("position", positionNum);
		editor.putBoolean("token", false);
		editor.commit();

	}

	void loadInformation(String filename, int time) {
		String name = filename;
		int musicTime = time;
		ConstUtil.musicName = filename;
		final String timeNum = timeChange(musicTime);
		MusciActivity.remoteViews.setTextViewText(R.id.tv_music, ConstUtil.musicName);
		MusciActivity.notificationManager.notify(0, MusciActivity.notification);
		MusciActivity.tv_Name.setText(name);
		MusciActivity.tv_Time.setText(timeNum);
	}

	static String timeChange(int musicTime) {

		String fenString;
		int fen = (musicTime / 1000) / 60;
		if (fen < 10) {
			fenString = "0" + fen + "";
		} else {
			fenString = fen + "";
		}
		int miao = (musicTime / 1000) % 60;
		String miaoString;
		if (miao < 10) {
			miaoString = "0" + miao + "";
		} else {
			miaoString = miao + "";
		}
		final String timeNum = fenString + ":" + miaoString;
		return timeNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
		mediaPlayer.stop();
		loadMusic(musicNum);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer.OnPreparedListener#onPrepared(android.media.MediaPlayer)
	 */
	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		mp.start();
		Log.e("mediaPlayer", "onPrepared");
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer.OnCompletionListener#onCompletion(android.media.MediaPlayer)
	 */
	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer.OnBufferingUpdateListener#onBufferingUpdate(android.media.MediaPlayer, int)
	 */
	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
		MusciActivity.seekBar.setSecondaryProgress(percent);
		int currentProgress = MusciActivity.seekBar.getMax()
				* mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
		Log.e(currentProgress + "% play", percent + " buffer");
	}

}
