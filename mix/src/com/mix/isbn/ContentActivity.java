package com.mix.isbn;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

import com.example.mix.R;
import com.mix.MainActivity;
import com.mix.bean.ISBNBean;
import com.mix.bean.PublicBean;
import com.mix.expressage.ExpressageActivity;
import com.mix.expressage.ResultOfSearchActivity;
import com.mix.publicpart.UriOfWhole;
import com.mix.util.GetHttpUtil;
import com.mix.util.JsonResolveUtil;

public class ContentActivity extends Activity {
	private Button back;
	private ImageView picture;
	private TextView isbnName, isbnNumber, authorInfo, pages, author,
			translator, price, binding, publisher, pubDate;
	private static String isbnNum;

	private RelativeLayout title;
	private LinearLayout layoutTop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_content);

		findView();
		adaptView();
		onclick();
		setValue();
	}

	private void onclick() {
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("isbn", "");
				intent.setClass(ContentActivity.this, ISBNActivity.class);
				startActivity(intent);
			}
		});
	}

	private void findView() {
		title = (RelativeLayout) findViewById(R.id.relative1_Content);
		back = (Button) findViewById(R.id.backContent);
		picture = (ImageView) findViewById(R.id.picture);
		isbnName = (TextView) findViewById(R.id.isbnName);
		isbnNumber = (TextView) findViewById(R.id.isbnNumber);
		pages = (TextView) findViewById(R.id.pages);
		author = (TextView) findViewById(R.id.author);
		translator = (TextView) findViewById(R.id.translator);
		price = (TextView) findViewById(R.id.price);
		binding = (TextView) findViewById(R.id.binding);
		publisher = (TextView) findViewById(R.id.publisher);
		pubDate = (TextView) findViewById(R.id.pubDate);
		authorInfo = (TextView) findViewById(R.id.authorInfo);
		layoutTop = (LinearLayout) findViewById(R.id.contentTop);
	}

	private void adaptView() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int width = metrics.widthPixels;
		int hight = metrics.heightPixels;

		int titleHeight = title.getLayoutParams().height;
		if (titleHeight < 0) {
			titleHeight = 0 - titleHeight;
		}
		int curHeight = hight - titleHeight;
		System.out.println("titleHeight=" + titleHeight);

		LinearLayout.LayoutParams layout1 = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 0);
		layout1.height = curHeight * 2 / 5;
		layoutTop.setLayoutParams(layout1);

		authorInfo.setVerticalScrollBarEnabled(true);
		authorInfo.setMovementMethod(ScrollingMovementMethod.getInstance());
		authorInfo.setHeight(curHeight * 3 / 5);

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0x01) {
				List<PublicBean> list = (List<PublicBean>) msg.obj;
				System.out.println("list=" + list);
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getStatus() == 200
							&& list.get(i).getMessage().equals("OK")) {
						List<Object> lists = list.get(i).getList();
						for (int j = 0; j < lists.size(); j++) {
							ISBNBean isbnBean = (ISBNBean) lists.get(j);
							picture.setBackgroundResource(R.drawable.book);
							isbnNumber.setText(isbnBean.getIsbn10() + "/"
									+ isbnBean.getIsbn13());
							pages.setText("页数: " + isbnBean.getPages() + "页");
							author.setText("作者: " + isbnBean.getAuthor());
							price.setText("价格： " + isbnBean.getPrice());
							binding.setText("装帧方式: " + isbnBean.getBinding());
							publisher
									.setText("出版社： " + isbnBean.getPublisher());
							pubDate.setText("出版时间： " + isbnBean.getPubDate());
							authorInfo
									.setText("  " + isbnBean.getAuthor_info());
						}
					} else {
						Toast.makeText(ContentActivity.this, "sorry!没有获取到任何信息",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		}
	};

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("isbn", isbnNum));
			System.out.println("isbnn=" + isbnNum);
			String jsonString = GetHttpUtil.getHttpOfTranslate(
					UriOfWhole.ISBN_INFO, params);
			System.out.println("jsonString=" + jsonString);
			Message msg = handler.obtainMessage();
			msg.obj = JsonResolveUtil.getIsbnInfo(jsonString);
			msg.what = 0x01;
			handler.sendMessage(msg);
		}
	};

	private void setValue() {
		isbnNum = getIntent().getStringExtra("isbn");

		if (isbnNum.equals("")) {
			Toast.makeText(ContentActivity.this, "输入ISBN号是空！",
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.putExtra("isbn", "");
			intent.setClass(ContentActivity.this, ISBNActivity.class);
			startActivity(intent);
		} else {
			if (GetHttpUtil.isNetWorkEnable(ContentActivity.this)) {
				new Thread(runnable).start();
			} else {
				Toast.makeText(ContentActivity.this, "网络未连接，请联网后操作！",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.putExtra("isbn", "");
				intent.setClass(ContentActivity.this, ISBNActivity.class);
				startActivity(intent);
			}
		}
	}

	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(ContentActivity.this, ISBNActivity.class);
			startActivity(intent);
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
}
