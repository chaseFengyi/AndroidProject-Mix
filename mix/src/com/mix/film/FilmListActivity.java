package com.mix.film;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mix.R;
import com.mix.MainActivity;
import com.mix.bean.AllMovieBean;
import com.mix.bean.HotFilmBean;
import com.mix.bean.PublicBean;
import com.mix.isbn.ContentActivity;
import com.mix.isbn.ISBNActivity;
import com.mix.publicpart.UriOfWhole;
import com.mix.util.GetHttpUtil;
import com.mix.util.JsonResolveUtil;

public class FilmListActivity extends Activity {
	private Button back;
	private ListView listView;
	public static String city;

	public static List<Object> movies = new ArrayList<Object>();
	private static MyAdapterFilm adapterFilm = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_filmlist);

		findView();
		onclick();
		setListView();

	}

	private void findView() {
		back = (Button) findViewById(R.id.backFilmList);
		listView = (ListView) findViewById(R.id.listViewFilm);
	}

	private void onclick() {
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("city", "");
				intent.setClass(FilmListActivity.this, FilmActivity.class);
				startActivity(intent);
			}
		});

	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		@SuppressLint("UseSparseArrays")
		public void handleMessage(Message msg) {
			if (msg.what == 0x01) {
				@SuppressWarnings("unchecked")
				List<PublicBean> list = (List<PublicBean>) msg.obj;
				for (int i = 0; i < list.size(); i++) {
					PublicBean publicBean = list.get(i);
					if (publicBean.getStatus() == 200
							&& publicBean.getMessage().equals("OK")) {
						List<Object> lists = publicBean.getList();
						for (int j = 0; j < lists.size(); j++) {
							HotFilmBean hotFilmBean = (HotFilmBean) lists
									.get(j);
							movies = hotFilmBean.getMovie();
							adapterFilm = new MyAdapterFilm(
									R.layout.filmlist_sample,
									FilmListActivity.this, movies);
							listView.setAdapter(adapterFilm);
						}
					} else {
						Toast.makeText(FilmListActivity.this,
								"sorry!还没有任何影片信息", Toast.LENGTH_SHORT).show();
					}
				}
			}
		}
	};

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("location", city));
			System.out.println("city=" + city);
			String jsonString = GetHttpUtil.getHttpOfTranslate(
					UriOfWhole.HOT_FILM, params);
			Message msg = handler.obtainMessage();
			msg.what = 0x01;
			msg.obj = JsonResolveUtil.getHotFilm(jsonString);
			handler.sendMessage(msg);
		}
	};

	// 获得第position位置的内容
	private AllMovieBean getContent(int number) {
		AllMovieBean map = (AllMovieBean) adapterFilm.getItem(number);
		return map;
	}

	private void setListView() {
		city = getIntent().getStringExtra("city");
		
		if (city.equals("")) {
			Toast.makeText(FilmListActivity.this, "请输入正确的城市名！",
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.putExtra("city", "");
			intent.setClass(FilmListActivity.this, FilmActivity.class);
			startActivity(intent);
		} else {
		
		if (GetHttpUtil.isNetWorkEnable(FilmListActivity.this)) {
			Thread thread = new Thread(runnable);
			thread.start();
		} else {
			Toast.makeText(FilmListActivity.this, "网络未连接，请联网后操作！",
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.putExtra("city", "");
			intent.setClass(FilmListActivity.this, FilmActivity.class);
			startActivity(intent);

		}
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				intent.setClass(FilmListActivity.this,
						FilmDetailsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("movies", getContent(arg2));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		}
	}

	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(FilmListActivity.this, FilmActivity.class);
			startActivity(intent);
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
}
