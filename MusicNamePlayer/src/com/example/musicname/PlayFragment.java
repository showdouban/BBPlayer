/**
 * 
 */
package com.example.musicname;



import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.base.CircleImageView;
import com.base.TypegifView;
import com.squareup.picasso.Picasso;

/**
 * Title: PlayFragment.java
 * Description: 
 * Copyright:Copyright(c)2015
 * Company: 四川久聚科技股份有限公司
 * CreateTime:2015-8-31 下午9:13:15 
 * @author liuwenxing
 * @version 1.0
 */
public class PlayFragment extends Fragment {
	private ShowLRCBroadReceiver receiver;
	CircleImageView infoOperatingIV ;
	LinearInterpolator lin ;
	CircleImageView infoOperatingIVBack ;
	ImageView ib_Check;
	private ObjectAnimator anim;
	private ObjectAnimator animBack;
	private RotateAnimation animation;
	private RotateAnimation animationBack;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragement_play, null);
		Button btn = (Button)view. findViewById(R.id.button1);
		Button btn2 = (Button) view.findViewById(R.id.button2);
		final TypegifView vw = (TypegifView)view.findViewById(R.id.gifView1);
		
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				vw.setStart();
			}
		});

		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {	
				vw.setStop();
			}
		});
			
		
		receiver = new ShowLRCBroadReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConstUtil.PLAYFRAMGMENT_ACTION);
		getActivity().registerReceiver(receiver, filter);
		
		infoOperatingIV = (CircleImageView)view.findViewById(R.id.infoOperating); 
		infoOperatingIVBack = (CircleImageView)view.findViewById(R.id.info); 
		ib_Check = (ImageView)view.findViewById(R.id.check);
		
		   lin = new LinearInterpolator();  
		   anim = ObjectAnimator.ofFloat(infoOperatingIV, "rotation", 0, 360);
		   anim.setDuration(10000);
		   anim.setRepeatCount(-1);
		   anim.setInterpolator(lin);  
		   anim.setRepeatMode(ObjectAnimator.RESTART);
		   
		   animBack = ObjectAnimator.ofFloat(infoOperatingIVBack, "rotation", 0, 360);
		   animBack.setDuration(10000);
		   animBack.setRepeatCount(-1);
		   animBack.setInterpolator(lin);  
		   animBack.setRepeatMode(ObjectAnimator.RESTART);
		   
		   animation =new RotateAnimation(0f,20f,Animation.RELATIVE_TO_SELF, 
				   0.5f,Animation.RELATIVE_TO_SELF,0f);
		   animation.setInterpolator(lin);
		   animation.setDuration(1000);//设置动画持续时间 
		   animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态 
		   animationBack =new RotateAnimation(20f,0f,Animation.RELATIVE_TO_SELF, 
				   0.5f,Animation.RELATIVE_TO_SELF,0f);
		   animationBack.setInterpolator(lin);
		   animationBack.setDuration(500);//设置动画持续时间 
		   animationBack.setFillAfter(true);//动画执行完后是否停留在执行完的状态 
		   
		  
		   
		   
//		operatingAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.tip);  
//		operatingAnim.setInterpolator(lin);  
//		infoOperatingIV.setAnimation(animation);  
		
		//infoOperatingIV.clearAnimation();  //停止旋转animation
		return view;
	}
	
	//保证手机横屏时不会重绘
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}


	class ShowLRCBroadReceiver extends BroadcastReceiver{
	 String path;
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int control = intent.getIntExtra("musicinfo", -1);
			try {
				path = intent.getStringExtra("pathPic").trim();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			//String albumpic_big = intent.getStringExtra("albumpic_big");
			switch (control) {
			case ConstUtil.STATE_PLAY:
//				if (operatingAnim != null) {  
//				    infoOperatingIV.startAnimation(operatingAnim);  
//				} 
				//Log.e("09823432", path);
				Picasso.with(getActivity())
				  .load(path)
		               .placeholder(R.drawable.qq)
		               .error(R.drawable.personal_head)
		               .into(infoOperatingIV);
				if(anim.isStarted()&&animBack.isStarted()){
					anim.resume();
					animBack.resume();
					
					//anim.setCurrentPlayTime(1);
				}else{
					anim.start();
					animBack.start();
					
				}
				ib_Check.startAnimation(animation); 
				break;
			case ConstUtil.STATE_PAUSE:
//				infoOperatingIV.clearAnimation();  //停止旋转animation
//				operatingAnim.
				anim.pause();
				animBack.pause();
				ib_Check.startAnimation(animationBack); 
				
				break;
			default:
				break;
			}
			
		}
	}


	
	
}

