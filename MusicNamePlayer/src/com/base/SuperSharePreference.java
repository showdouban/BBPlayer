package com.base;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * Title: SuperSharePreference.java
 * Description: SharePreference
 * Copyright:Copyright(c)2015
 * Company: 四川久聚科技股份有限公司
 * CreateTime:2015-8-3 下午1:40:48 
 * @author dengqinsheng
 * @version 1.0
 */
public class SuperSharePreference {
	private Context context;
	public static final String SHARE_NAME = "SuperPartners";

	public SuperSharePreference() {
		super();
	}

	public SuperSharePreference(Context context) {
		super();
		this.context = context;
	}

	public SharedPreferences getSharedPreferences() {
		SharedPreferences mData = context.getSharedPreferences(SHARE_NAME,
				Context.MODE_WORLD_READABLE);

		return mData;
	}

}
