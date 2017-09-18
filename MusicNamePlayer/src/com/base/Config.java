package com.base;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Title: Config.java
 * Description: 存储键值对配置
 * Copyright:Copyright(c)2015
 * Company: 四川久聚科技股份有限公司
 * CreateTime:2015-8-5 下午1:45:14 
 * @author dengqinsheng
 * @version 1.0
 */
public class Config {
	public Context context;
	private static final String TOKEN_VALUE = "token_value";
	private static final String USER_ID = "user_id";
	private static final String USER_PHONE = "user_phone";
//	private static final String USER_PWD = "user_pwd";
	private static final String IS_FIRST_LOGIN = "is_first_login";
	private static final String USER_HEADPIC = "user_headPic";
	private static final String USER_NICKNAME = "user_nickName";
	private static final String USER_GENDER = "user_gender";
	private static final String USER_RANK ="user_rank";
	private static final String DWON_URL = "down_url";
	private static final String M4A = "m4a";
	private static final String SONG_NAME = "song_name";
	private static final String SINGER_NAME = "singer_name";
	/**分类版本号*/
	private static final String CATEGORY_VERSION ="category_version";

	public Config(Context context) {
		this.context = context;
	}

	public static Config getConfig(Context context) {
		return new Config(context);
	}
	
	public void setDownUrl(String downUrl) {
		new SuperSharePreference(context).getSharedPreferences().edit()
				.putString(DWON_URL, downUrl).commit();
	}

	public String getDownUrl() {
		SharedPreferences share = new SuperSharePreference(context)
				.getSharedPreferences();
		return share.getString(DWON_URL, "");

	}
	public void setM4a(String M4a) {
		new SuperSharePreference(context).getSharedPreferences().edit()
				.putString(M4A, M4a).commit();
	}

	public String getM4a() {
		SharedPreferences share = new SuperSharePreference(context)
				.getSharedPreferences();
		return share.getString(M4A, "");

	}
	public void setSongName(String M4a) {
		new SuperSharePreference(context).getSharedPreferences().edit()
				.putString(SONG_NAME, M4a).commit();
	}

	public String getSongName() {
		SharedPreferences share = new SuperSharePreference(context)
				.getSharedPreferences();
		return share.getString(SONG_NAME, "");

	}
	public void setSingerName(String M4a) {
		new SuperSharePreference(context).getSharedPreferences().edit()
				.putString(SINGER_NAME, M4a).commit();
	}

	public String getSingerName() {
		SharedPreferences share = new SuperSharePreference(context)
				.getSharedPreferences();
		return share.getString(SINGER_NAME, "");

	}
	

	public void setUserId(int userId) {
		new SuperSharePreference(context).getSharedPreferences().edit()
				.putInt(USER_ID, userId).commit();
	}

	public int getUserId() {
		SharedPreferences share = new SuperSharePreference(context)
				.getSharedPreferences();
		return share.getInt(USER_ID, -1);

	}

	public void setUserPhone(String userName) {
		new SuperSharePreference(context).getSharedPreferences().edit()
				.putString(USER_PHONE, userName).commit();
	}

	public String getUserPhone() {
		SharedPreferences share = new SuperSharePreference(context)
				.getSharedPreferences();
		return share.getString(USER_PHONE, "");

	}

//	public void setUserPwd(String userPwd) {
//		new SuperSharePreference(context).getSharedPreferences().edit()
//				.putString(USER_PWD, userPwd).commit();
//	}
//
//	public String getUserPwd() {
//		SharedPreferences share = new SuperSharePreference(context)
//				.getSharedPreferences();
//		return share.getString(USER_PWD, "");
//	}

	public void setTokenValue(String tokenValue) {
		if (context != null)
			new SuperSharePreference(context).getSharedPreferences().edit()
					.putString(TOKEN_VALUE, tokenValue).commit();
	}

	public String getTokenValue() {
		SharedPreferences share = new SuperSharePreference(context)
				.getSharedPreferences();
		return share.getString(TOKEN_VALUE, "");
	}
	
	public void setCateVersion(String version) {
		if (context != null)
			new SuperSharePreference(context).getSharedPreferences().edit()
			.putString(CATEGORY_VERSION, version).commit();
	}
	
	public String getCateVersion() {
		SharedPreferences share = new SuperSharePreference(context)
		.getSharedPreferences();
		return share.getString(CATEGORY_VERSION, "");
	}

	/**
	 * 
	 * Description: 头像
	 * CreateTime:2015-8-12 上午9:54:13 
	 *  @param headPic
	 */
	public void setHeadPic(String headPic) {
		new SuperSharePreference(context).getSharedPreferences().edit()
				.putString(USER_HEADPIC, headPic).commit();
	}

	public String getHeadPic() {
		SharedPreferences share = new SuperSharePreference(context)
				.getSharedPreferences();
		return share.getString(USER_HEADPIC, "");
	}

	/**
	 * 
	 * Description: 昵称
	 * CreateTime:2015-8-12 上午9:54:23 
	 *  @param nickName
	 */
	public void setNickName(String nickName) {
		new SuperSharePreference(context).getSharedPreferences().edit()
				.putString(USER_NICKNAME, nickName).commit();
	}

	public String getNickName() {
		SharedPreferences share = new SuperSharePreference(context)
				.getSharedPreferences();
		return share.getString(USER_NICKNAME, "");
	}

	/**
	 * 
	 * Description: 性别
	 * CreateTime:2015-8-12 上午9:55:06 
	 *  @param gender
	 */
	public void setGender(int gender) {
		new SuperSharePreference(context).getSharedPreferences().edit()
				.putInt(USER_GENDER, gender).commit();
	}

	public int getGender() {
		SharedPreferences share = new SuperSharePreference(context)
				.getSharedPreferences();
		return share.getInt(USER_GENDER, 0);
	}

	/**
	 * 用户级别
	 * @param rank
	 */
	public void setRank(int rank){
		new SuperSharePreference(context).getSharedPreferences().edit()
				.putInt(USER_RANK, rank).commit();
	}

	public  int getRank(){
		SharedPreferences share = new SuperSharePreference(context)
				.getSharedPreferences();
//		return share.getInt(USER_RANK, 0);
		return 0;
	}

	/**
	 * 登陆已经失效
	 */
	public void overTime() {
		setTokenValue(null);
	}

	public void setIsFirstLogin(boolean isFirst) {
		SharedPreferences share = new SuperSharePreference(context)
				.getSharedPreferences();
		share.edit().putBoolean(IS_FIRST_LOGIN, isFirst).commit();
	}

	public boolean isFirstLogin() {
		SharedPreferences share = new SuperSharePreference(context)
				.getSharedPreferences();
		return share.getBoolean(IS_FIRST_LOGIN, true);
	}
	
	public void logout(){
		setGender(0);
		setHeadPic("");
		setNickName("");
		setRank(0);
		setTokenValue("");
		setUserId(-1);
		setUserPhone("");
	}
}
