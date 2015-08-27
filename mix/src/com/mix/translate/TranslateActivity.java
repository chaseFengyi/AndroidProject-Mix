package com.mix.translate;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mix.R;
import com.mix.MainActivity;
import com.mix.bean.PublicBean;
import com.mix.bean.TranslateBean;
import com.mix.publicpart.UriOfWhole;
import com.mix.util.GetHttpUtil;
import com.mix.util.JsonResolveUtil;
import com.mix.weather.WeatherActivity;

public class TranslateActivity extends Activity {
	private Button back;
	public static EditText input;
	private TextView output;
	private Button translate;

	private RelativeLayout relativeLayout;

	private static String inputString = "";
	private static String outputString = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_translste);

		findView();
		output.setVisibility(View.GONE);
		output.setMovementMethod(ScrollingMovementMethod.getInstance());
		adaptView();
		input.addTextChangedListener(mTextChangeListener);
		input.setSelection(input.length());
		onclick();
	}

	private TextWatcher mTextChangeListener = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);

			int hight = metrics.heightPixels;

			/*RelativeLayout.LayoutParams layout = (LayoutParams) input
					.getLayoutParams();
			layout.height = hight / 4;
			input.setLayoutParams(layout);*/
			
			int w = View.MeasureSpec.makeMeasureSpec(0,
					View.MeasureSpec.UNSPECIFIED);
			int h = View.MeasureSpec.makeMeasureSpec(0,
					View.MeasureSpec.UNSPECIFIED);
			input.measure(w, h);
			int inputHight = input.getMeasuredHeight();
			
			if (inputHight >= (hight / 5)) {
				input.setHeight(hight / 5);
				input.setMovementMethod(ScrollingMovementMethod.getInstance());
			}
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			
		}
	};

	private void findView() {
		back = (Button) findViewById(R.id.backTranslate);
		input = (EditText) findViewById(R.id.input);
		output = (TextView) findViewById(R.id.output);
		translate = (Button) findViewById(R.id.translat);
		relativeLayout = (RelativeLayout) findViewById(R.id.translateRelative);
	}

	private void adaptView() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int hight = metrics.heightPixels;

		output.setVerticalScrollBarEnabled(true);
		output.setHeight(hight / 2 - translate.getHeight());

	}

	private void onclick() {
		back.setOnClickListener(listener);
		translate.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent();
			switch (arg0.getId()) {
			case R.id.backTranslate:
				intent.setClass(TranslateActivity.this, MainActivity.class);
				startActivity(intent);
				break;
			case R.id.translat:
				output.setVisibility(View.VISIBLE);
				translate();
				break;
			}
		}
	};

	Runnable runnbale = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			System.out.println("inputString=" + inputString);
			params.add(new BasicNameValuePair("q", inputString));
			params.add(new BasicNameValuePair("output", "auto"));
			String jsonString = GetHttpUtil.getHttpOfTranslate(
					UriOfWhole.TRANSLATE_URI, params);
			System.out.println("jsonString=" + jsonString);
			Message msg = handler.obtainMessage();
			msg.obj = JsonResolveUtil.getJsonResultByTranslate(jsonString);
			msg.what = 0x01;
			handler.sendMessage(msg);
		}
	};

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == 0x01) {
				@SuppressWarnings("unchecked")
				List<PublicBean> list = (List<PublicBean>) msg.obj;
				System.out.println("list=" + list);
				for (int i = 0; i < list.size(); i++) {
					PublicBean publicBean = list.get(i);
					if ((publicBean.getStatus() == 200)
							&& publicBean.getMessage().contains("OK")) {
						List<Object> lists = publicBean.getList();
						if (lists == null) {
							Toast.makeText(TranslateActivity.this,
									"对不起，太难了，不会翻译", Toast.LENGTH_SHORT).show();
						} else {
							for (int j = 0; j < lists.size(); j++) {
								TranslateBean translateBean = (TranslateBean) lists
										.get(j);
								outputString = translateBean.getDst();
								System.out.println("output=" + outputString);
								output.setText(outputString);
							}

						}
					} else {
						Toast.makeText(TranslateActivity.this, "对不起，太难了，不会翻译",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		}

	};

	private void translate() {
		inputString = input.getText().toString();
		System.out.println("inputstring=" + inputString);
		if (GetHttpUtil.isNetWorkEnable(TranslateActivity.this)) {
			Thread thread = new Thread(runnbale);
			thread.start();
		} else {
			Toast.makeText(TranslateActivity.this, "网络未连接，请联网后操作！",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent intent = new Intent();
			intent.setClass(TranslateActivity.this, MainActivity.class);
			startActivity(intent);
			return true;
		}else{
			return super.onKeyDown(keyCode, event);
		}
	}
}
