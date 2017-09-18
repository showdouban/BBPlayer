package com.example.musicname;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.Header;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ab.util.AbJsonUtil;
import com.base.Base;
import com.base.Base.ShowapiResBodyEntity;
import com.base.Base.ShowapiResBodyEntity.PagebeanEntity;
import com.base.ContentlistEntity;
import com.base.MusicSeek;
import com.base.MusicSeek.ShowapiResBody;
import com.base.MusicSeek.ShowapiResBody.Pagebean;
import com.base.SonglistEntity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.show.api.ShowApiRequest;

public class DownFragment extends Fragment implements OnClickListener {
	String content;
	int allNUm = 0;
	int allPages = 0;
	int currentPage = 0;
	int pageSeek = 0;
	int flag = 1;
	String keyword = null;
	String downUrl = null;
	String seekNum = "";
	String singerName = null;
	String songName = null;
	private EditText et_Seek;
	private ListView listView;
	private DownAdapter downAdapter;
	private PtrFrameLayout mPtrFrameLayout;
	private LoadMoreListViewContainer loadMoreContainer;
	View view;
	// private EmptyLayout mEmptyLayout;
	private String title;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = inflater.inflate(R.layout.down_list, null);

		listView = (ListView) view.findViewById(R.id.music_player);
		et_Seek = (EditText) view.findViewById(R.id.et_seek);
		// mEmptyLayout = new EmptyLayout(this, listView);
		// mEmptyLayout.setErrorButtonClickListener(this);
		loadInformation();
		mPtrFrameLayout = (PtrFrameLayout) view
				.findViewById(R.id.load_more_grid_view_ptr_frame);
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {
				// here check list view, not content.
				return PtrDefaultHandler.checkContentCanBePulledDown(frame,
						listView, header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				pageSeek = 1;
				if (flag == 1) {
					loadMusic(title, pageSeek + "");
				}
				if (flag == 2) {
					seekMusic(seekNum, pageSeek + "");
				}
			}
		});
		loadMoreContainer = (LoadMoreListViewContainer) view
				.findViewById(R.id.load_more_grid_view_container);
		loadMoreContainer.useDefaultFooter();
		loadMoreContainer.setAutoLoadMore(true);
		loadMoreContainer.setLoadMoreHandler(new LoadMoreHandler() {
			@Override
			public void onLoadMore(LoadMoreContainer loadMoreContainer) {
				Log.e("dsdfsfsd", pageSeek + "");
				pageSeek = pageSeek + 1;
				if (pageSeek <= allPages) {
					loadMusic(title, pageSeek + "");
					if (flag == 1) {

					}
					if (flag == 2) {

						seekMusic(seekNum, pageSeek + "");
					}
				}
				else{
					Toast.makeText(getActivity(), "已经是最后一页", 1000).show();
					loadMoreContainer.loadMoreFinish(false, true);
				}
			}
		});

		downAdapter = new DownAdapter(getActivity());
		listView.setAdapter(downAdapter);
		
		
		return view;
	}

	void loadInformation() {
		view.findViewById(R.id.bt_seek).setOnClickListener(this);
		view.findViewById(R.id.bt_liuxing).setOnClickListener(this);
		view.findViewById(R.id.bt_neidi).setOnClickListener(this);
		view.findViewById(R.id.bt_guangtai).setOnClickListener(this);
		view.findViewById(R.id.bt_ktv).setOnClickListener(this);

	}
	//榜单搜索
	public void seekMusic(String itemid, String page) {
		downAdapter.setFlag(2);
		flag = 2;
		AsyncHttpResponseHandler resHandler = new AsyncHttpResponseHandler() {
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable e) {
				// 做一些异常处理
				e.printStackTrace();
			}

			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				try {
					long b = System.currentTimeMillis();
					content = new String(responseBody, "utf-8");
					SeekSuccess(content);
					// 在此对返回内容做处理
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		};
		new ShowApiRequest("http://route.showapi.com/213-3", "7927",
				"1fb1f3c73b854d619438685b8fd4da8b")
				.setResponseHandler(resHandler).addTextPara("itemid", itemid)
				.addTextPara("page", page)

				.post();

	}
	// 文本搜索
	public void loadMusic(String keyword, String page) {
		downAdapter.setFlag(1);
		flag = 1;
		AsyncHttpResponseHandler resHandler = new AsyncHttpResponseHandler() {
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable e) {
				// 做一些异常处理
				e.printStackTrace();
			}

			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				try {
					long b = System.currentTimeMillis();
					content = new String(responseBody, "utf-8");
					loadSuccess(content);
					// 在此对返回内容做处理
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				mPtrFrameLayout.refreshComplete();
				loadMoreContainer.loadMoreFinish(false, true);
			}
		};
		new ShowApiRequest("http://route.showapi.com/213-1", "7927",
				"1fb1f3c73b854d619438685b8fd4da8b")
				.setResponseHandler(resHandler).addTextPara("keyword", keyword)
				.addTextPara("page", page).post();
	}

	protected void loadSuccess(String content) {
		downAdapter.removeAll();
		Base result = (Base) AbJsonUtil.fromJson(content, Base.class);
		Log.d("mmmmmmmm", "result = " + content);
		if (result != null) {

			int code = result.getShowapi_res_code();
			if (code == 0) {
				ShowapiResBodyEntity entity = result.getShowapi_res_body();
				if (entity != null) {
					PagebeanEntity page = entity.getPagebean();
					if (page != null) {
						allNUm = page.getAllNum();
						allPages = page.getAllPages();
						currentPage = page.getCurrentPage();
						keyword = page.getKeyword();
						List<ContentlistEntity> contentList = page
								.getContentlist();

						downAdapter.addContentlistEntityAll(contentList);
					}

				}
			} else {
				Toast.makeText(getActivity(), "错误", 1000).show();
			}
		} else {
			Toast.makeText(getActivity(), "错误", 1000).show();
		}
	}

	protected void SeekSuccess(String content) {
		downAdapter.removeAll();
		MusicSeek result = (MusicSeek) AbJsonUtil.fromJson(content,
				MusicSeek.class);
		if (result != null) {
			Log.d("sihadad", content);
			Log.d("cccccccccc", "result = " + result.toString());
			int code = result.getShowapi_res_code();
			Log.d("可爱", code + "");
			// Log.d("ok",result.getShowapi_res_body().getPagebean().getTitle());
			if (code == 0) {
				ShowapiResBody entity = result.getShowapi_res_body();
				if (entity != null) {
					Pagebean page = entity.getPagebean();
					if (page != null) {
						List<SonglistEntity> songList = page.getSonglist();
						downAdapter.addSonglistEntityAll(songList);
					}

				}
			} else {
				Toast.makeText(getActivity(), "错误", 1000).show();
			}
		} else {
			Toast.makeText(getActivity(), "错误", 1000).show();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_seek:
			title = et_Seek.getText().toString().trim();
			pageSeek = 1;
			loadMusic(title, pageSeek + "");
			break;
		case R.id.bt_liuxing:
			pageSeek = 1;
			seekNum = "4";
			seekMusic(seekNum, pageSeek + "");
			break;
		case R.id.bt_neidi:
			pageSeek = 1;
			seekNum = "5";
			seekMusic(seekNum, pageSeek + "");
			break;
		case R.id.bt_guangtai:
			pageSeek = 1;
			seekNum = "6";
			seekMusic(seekNum, pageSeek + "");
			break;
		case R.id.bt_ktv:
			pageSeek = 1;
			seekNum = "101";
			seekMusic(seekNum, pageSeek + "");
			break;

		default:
			break;
		}
	}

}
