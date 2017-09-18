/**
 * 
 */
package com.example.musicname;

import java.util.ArrayList;
import java.util.List;

import android.Manifest.permission;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Title: MusicAdapter.java Description: Copyright:Copyright(c)2015 Company:
 * 四川久聚科技股份有限公司 CreateTime:2015-8-28 上午11:22:09
 * 
 * @author liuwenxing
 * @version 1.0
 */
public class MusicAdapter extends BaseAdapter {
	private Context context;
	static ArrayList<MusicInformation> list = new ArrayList<MusicInformation>();
	private LayoutInflater mInflater;
	private SharedPreferences preferences;
	private Editor editor;

	public MusicAdapter(Context context) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		preferences = MusciActivity.sPreferences;
		editor = preferences.edit();
	}

	public void remove(int position) {
		list.remove(position);
		notifyDataSetChanged();
	}

	public void removeAll() {
		list.clear();
		notifyDataSetChanged();

	}

	public void addAll(List list) {
		this.list.addAll(list);
		notifyDataSetChanged();

	}

	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_music, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		;
		final MusicInformation mInformation = (MusicInformation) getItem(position);
		final String fileName = mInformation.getMusicName();
		final String musicPlayer = mInformation.getMusicPlayer();
		final int musicTime = mInformation.getMusicTime();
		int fen = (musicTime / 1000) / 60;
		final int positionNum = position;
		String fenString;
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
		final String time = fenString + ":" + miaoString;
		holder.tv_Name.setText(fileName);
		holder.tv_Music_Player.setText(musicPlayer);
		holder.tv_Music_Time.setText(time);
		holder.layout.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				String path = mInformation.getMusicPath();
				Intent intent = new Intent(context, Musicserver.class);
				intent.putExtra("path", path);
				context.startService(intent);

				editor.putString("filename", fileName);
				editor.putString("path", path);
				editor.putInt("time", musicTime);
				editor.putString("musictime", time);
				editor.putInt("position", positionNum);
				editor.commit();
				ConstUtil.musicName = mInformation.getMusicName();
				MusciActivity.remoteViews.setTextViewText(R.id.tv_music, ConstUtil.musicName);
				MusciActivity.notificationManager.notify(0, MusciActivity.notification);
				MusciActivity.tv_Name.setText(ConstUtil.musicName);
				MusciActivity.tv_Time.setText(time);
				MusciActivity.btnChange = true;
				MusciActivity.bt_Pause.setText("暂停");
				
//				Intent intent_play = new Intent();
//				intent_play.setAction(ConstUtil.PLAYFRAMGMENT_ACTION);
//				intent_play.putExtra("musicinfo", ConstUtil.STATE_PLAY);
//				//intent_play.putExtra("albumpic_big", albumpic_big);
//				
//				context.sendBroadcast(intent_play);
			}
		});

		return convertView;
	}

	class ViewHolder {
		public final View root;
		public final ImageView imageView;
		public final TextView tv_Name;
		public final TextView tv_Music_Player;
		public final TextView tv_Music_Time;
		public final CheckBox checkBox;
		public final LinearLayout layout;

		public ViewHolder(View root) {
			imageView = (ImageView) root.findViewById(R.id.imageView1);
			tv_Name = (TextView) root.findViewById(R.id.tv_name);
			tv_Music_Player = (TextView) root
					.findViewById(R.id.tv_music_player);
			tv_Music_Time = (TextView) root.findViewById(R.id.tv_music_time);
			checkBox = (CheckBox) root.findViewById(R.id.checkName);
			layout = (LinearLayout) root.findViewById(R.id.ll_infor);
			this.root = root;
		}

	}

}
