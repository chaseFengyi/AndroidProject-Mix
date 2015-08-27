package com.mix.expressage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mix.R;
import com.mix.MainActivity;
import com.mix.bean.ExpressageBean;
import com.mix.bean.PublicBean;
import com.mix.isbn.ISBNActivity;
import com.mix.publicpart.UriOfWhole;
import com.mix.util.GetHttpUtil;
import com.mix.util.JsonResolveUtil;

public class ResultOfSearchActivity extends Activity {
	private Button back;
	private ListView listView;

	private static String companyName = "";
	private static String signalNumber = "";
	private static String com = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_resultexpressage);

		findView();
		onclick();
		setListView();
	}

	private void findView() {
		back = (Button) findViewById(R.id.backResultExpressage);
		listView = (ListView) findViewById(R.id.listview);
	}

	private void onclick() {
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ResultOfSearchActivity.this,
						ExpressageActivity.class);
				startActivity(intent);
			}
		});
	}

	// 获取listView中每一个Item的高度
	/**
	 * @param listView
	 * @return -1:获取失败 1:成功
	 */
	public int getItemHeight(ListView listView) {
		ListAdapter adapter = listView.getAdapter();

		if (adapter == null) {
			return -1;
		}

		View item = adapter.getView(0, null, listView);
		item.measure(0, 0);
		System.out.println("itemHeight=" + item.getMeasuredHeight());
		return item.getMeasuredHeight();
	}

	// 绑定数据
	public void bindItem(SimpleAdapter adapter) {
		/*
		 * Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(
		 * R.drawable.blue9)).getBitmap();
		 */
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.blue9);
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int itemHeight = 0;
		int width = bitmap.getWidth();
		System.out.println("itemCount=" + listAdapter.getCount());
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			View lisItem = listAdapter.getView(i, null, listView);
			// lisItem.measure(0, 0);
			lisItem.measure(
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			itemHeight = lisItem.getMeasuredHeight()
					+ listView.getDividerHeight();
			System.out.println("i===" + i);
			System.out.println("itemHeight=" + itemHeight);
			final Bitmap mBitmap = Bitmap.createScaledBitmap(bitmap, width,
					itemHeight, true);
			adapter.setViewBinder(new ViewBinder() {

				@Override
				public boolean setViewValue(View arg0, Object arg1, String arg2) {
					// TODO Auto-generated method stub
					if (arg0 instanceof ImageView) {
						ImageView imageView = (ImageView) arg0;
						imageView.setImageBitmap(mBitmap);
						return true;
					}
					return false;
				}
			});
		}
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0x01) {
				@SuppressWarnings("unchecked")
				List<PublicBean> list = (List<PublicBean>) msg.obj;
				for (int i = 0; i < list.size(); i++) {
					PublicBean publicBean = list.get(i);
					if (publicBean.getStatus() == 200
							&& (publicBean.getMessage().equals("ok") || publicBean
									.getMessage().equals("OK"))) {
						List<Object> lists = publicBean.getList();
						List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
						if (lists.size() <= 0) {
							Toast.makeText(ResultOfSearchActivity.this,
									"sorry!还没有任何发货信息", Toast.LENGTH_SHORT)
									.show();
						}
						for (int j = 0; j < lists.size(); j++) {
							Map<String, Object> map = new HashMap<String, Object>();
							ExpressageBean expressageBean = (ExpressageBean) lists
									.get(j);
							map.put("acceptTime",
									expressageBean.getAcceptTime());
							map.put("remark", expressageBean.getRemark());
							ls.add(map);
							SimpleAdapter adapter = new SimpleAdapter(
									ResultOfSearchActivity.this, ls,
									R.layout.result_expressage, new String[] {
											"acceptTime", "remark" },
									new int[] { R.id.acceptTime, R.id.remark });
							listView.setAdapter(adapter);

						}
					} else {
						System.out.println(")))))))(((((((((^^^^^^^^");
						Toast.makeText(ResultOfSearchActivity.this,
								"sorry!还没有任何发货信息", Toast.LENGTH_SHORT).show();
					}
				}
			}
		}
	};

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("postid", signalNumber));
			params.add(new BasicNameValuePair("com", com));
			String jsonString = GetHttpUtil.getHttpOfTranslate(
					UriOfWhole.COM_POSTID_EXPRESSAGE, params);
			System.out.println("json=" + jsonString);
			List<PublicBean> list = JsonResolveUtil
					.getJsonResultByExpressage(jsonString);
			Message msg = handler.obtainMessage();
			msg.what = 0x01;
			msg.obj = list;
			handler.sendMessage(msg);
		}
	};

	private void setListView() {
		companyName = ExpressageActivity.companyNameString;
		signalNumber = ExpressageActivity.singleNumberString;

		if (companyName.equals("") || signalNumber.equals("")) {
			Toast.makeText(ResultOfSearchActivity.this, "输入公司名或者快递号是空！",
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.setClass(ResultOfSearchActivity.this,
					ExpressageActivity.class);
			startActivity(intent);
		} else {
			for (int i = 0; i < AllOfTheExpressageActivity.b.size(); i++) {
				Map<String, String> map = AllOfTheExpressageActivity.b.get(i);
				Iterator iterator = map.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry entry = (Entry) iterator.next();
					if (companyName.equals(entry.getKey())) {
						com = (String) entry.getValue();
					}
				}
			}
			if (GetHttpUtil.isNetWorkEnable(ResultOfSearchActivity.this)) {
				Thread thread = new Thread(runnable);
				thread.start();
			} else {
				Toast.makeText(ResultOfSearchActivity.this, "网络未连接，请联网后操作！",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(ResultOfSearchActivity.this,
						ExpressageActivity.class);
				startActivity(intent);
			}
		}
	}

	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(ResultOfSearchActivity.this,
					ExpressageActivity.class);
			startActivity(intent);
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
