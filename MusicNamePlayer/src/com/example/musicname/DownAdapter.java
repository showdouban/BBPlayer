/**
 * 
 */
package com.example.musicname;

/**
 * Title: DownAdapter.java
 * Description: 
 * Copyright:Copyright(c)2015
 * Company: 四川久聚科技股份有限公司
 * CreateTime:2015-9-7 上午10:36:17 
 * @author liuwenxing
 * @version 1.0
 */
/**
 * 
 */

import java.util.ArrayList;
import java.util.List;

import com.base.ContentlistEntity;
import com.base.Player;
import com.base.SonglistEntity;
import com.example.musicname.MusciActivity;
import com.example.musicname.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.util.Log;
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
public class DownAdapter extends BaseAdapter {
	private Context context;
	static ArrayList<ContentlistEntity> list1 = new ArrayList<ContentlistEntity>();
	static ArrayList<SonglistEntity> list2 = new ArrayList<SonglistEntity>();
	private LayoutInflater mInflater;
	//private SharedPreferences preferences;
	private Editor editor;
	int flag = 0;


	public int getFlag() {
		return flag;
	}


	public void setFlag(int flag) {
		this.flag = flag;
	}

	public DownAdapter(Context context ) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		
		//editor = preferences.edit();
	}
	
	public void remove(int position) {
		list1.remove(position);
		list2.remove(position);
		notifyDataSetChanged();
	}

	public void removeAll() {
		list1.clear();
		list2.clear();
		notifyDataSetChanged();

	}

	public void addContentlistEntityAll(List listm1) {
		this.list1.addAll(listm1);
		notifyDataSetChanged();

	}
	public void addSonglistEntityAll(List listm2) {
		this.list2.addAll(listm2);
		notifyDataSetChanged();
		
	}

	public int getCount() {
		// TODO Auto-generated method stub
		if(flag==1){
			return list1.size();
		}
		return list2.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(flag==1){
			return list1.get(position);
		}
		return list2.get(position);
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
			convertView = mInflater.inflate(R.layout.item_down_music, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		switch (flag) {
		//文本搜索显示
		case 1:
			final ContentlistEntity mInformation = (ContentlistEntity) getItem(position);
			final String fileName = mInformation.getSongname();
			final String musicPlayer = mInformation.getSingername();
			final String pathPicBig = mInformation.getAlbumpic_big();
			final String pathPicSmall = mInformation.getAlbumpic_small();
			Picasso.with(context)
			  .load(pathPicSmall)
	               .placeholder(R.drawable.qq)
	               .error(R.drawable.qq)
	               .into(holder.imageView);
			holder.tv_Name.setText(fileName);
			holder.tv_Music_Player.setText(musicPlayer);
			holder.layout.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					ConstUtil.musicName = fileName;
					MusciActivity.tv_Name.setText(fileName);
					// MusciActivity.tv_Time.setText(time);
					MusciActivity.btnChange = true;
					MusciActivity.bt_Pause.setText("暂停");
					String path = mInformation.getDownUrl();
					
					Intent intent = new Intent(context, Musicserver.class);
					//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("path", path);
					Log.d("ssssssssss", path);
					context.startService(intent);
					MusciActivity.remoteViews.setTextViewText(R.id.tv_music, ConstUtil.musicName);
					Log.d("dsdfs",ConstUtil.musicName);
					MusciActivity.notificationManager.notify(0, MusciActivity.notification);
					
					
					Intent intent_play_pic = new Intent();
					intent_play_pic.setAction(ConstUtil.PLAYFRAMGMENT_ACTION);
					intent_play_pic.putExtra("musicinfo", ConstUtil.STATE_PLAY);
					intent_play_pic.putExtra("pathPic", pathPicBig);
					
					context.sendBroadcast(intent_play_pic);

					// editor.putString("filename", fileName);
					// editor.putString("path", path);
					// editor.putString("musictime", time);
					// editor.putInt("position", positionNum);
					// editor.commit();
					// MusciActivity.tv_Name.setText(mInformation.getMusicName());
					// MusciActivity.tv_Time.setText(time);
					// MusciActivity.btnChange = true;
					// MusciActivity.bt_Pause.setText("暂停");
				}
			});
			break;
			//榜单搜索显示
		case 2:
			final SonglistEntity songInformation = (SonglistEntity) getItem(position);
			final String songName = songInformation.getSongname();
			final String singerPlayer = songInformation.getSingername();
			final String path = songInformation.getDownUrl();
			final int positionNum = position;
			holder.tv_Name.setText(songName);
			holder.tv_Music_Player.setText(singerPlayer);
			holder.tv_Name.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ConstUtil.musicName = songName;
					
					MusciActivity.tv_Name.setText(ConstUtil.musicName);
					// MusciActivity.tv_Time.setText(time);
					MusciActivity.btnChange = true;
					MusciActivity.bt_Pause.setText("暂停");
					Intent intent = new Intent(context, Musicserver.class);
					// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("path", path);
					context.startService(intent);
					MusciActivity.remoteViews.setTextViewText(R.id.tv_music, ConstUtil.musicName);
					Log.d("dsdfs",ConstUtil.musicName);
					MusciActivity.notificationManager.notify(0, MusciActivity.notification);
					Log.d("ssssssssss", path);

					// editor.putString("filename", songName);
					// editor.putString("path", path);
					// // editor.putString("musictime", time);
					// editor.putInt("position", positionNum);
					// editor.commit();
					 
					 
				}
			});
			break;

		default:
			break;
		}
			
		

		return convertView;
	}

	class ViewHolder {
		public final View root;
		public final ImageView imageView;
		public final TextView tv_Name;
		public final TextView tv_Music_Player;
		public final CheckBox checkBox;
		public final LinearLayout layout;

		public ViewHolder(View root) {
			imageView = (ImageView) root.findViewById(R.id.imageView1);
			tv_Name = (TextView) root.findViewById(R.id.tv_name);
			tv_Music_Player = (TextView) root
					.findViewById(R.id.tv_music_player);
			checkBox = (CheckBox) root.findViewById(R.id.checkName);
			layout = (LinearLayout) root.findViewById(R.id.ll_infor);
			this.root = root;
		}

	}

}

