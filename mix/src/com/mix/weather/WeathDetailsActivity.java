package com.mix.weather;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mix.R;
import com.mix.bean.PublicBean;
import com.mix.bean.Weather_CurBean;
import com.mix.bean.WeekWeatherBean;
import com.mix.isbn.ContentActivity;
import com.mix.isbn.ISBNActivity;
import com.mix.publicpart.UriOfWhole;
import com.mix.translate.TranslateActivity;
import com.mix.util.GetHttpUtil;
import com.mix.util.JsonResolveUtil;

public class WeathDetailsActivity extends Activity {
	private Button back;
	private TextView cityName;
	private TextView curTime;
	private TextView centigrate;
	private TextView weather;
	private TextView refreshTime;
	private TextView tempRange_windDirection;
	private LineChart laterTime;
	private String minTemp;
	private String maxTemp;
	private String windDirection;// 风向
	private String windForce;// 级数
	private static String city;
	private ArrayList<Map<String, Object>> list1 = null;
	private static final int MessageType = 0x01;
	// private ProgressDialog progressDialog = null;
	private LinearLayout linear2;
	private LinearLayout details_linear1;
	private LinearLayout details_linear2;

	private LineChart lineChart;
	private LinearLayout weatherLinear;
	private int maxY = 0;
	private int minY = 0;
	private int startX = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_weatherdetails);
		// progressDialog = ProgressDialog.show(WeathDetailsActivity.this, "下载",
		// "正在下载,请稍候！",true);
		city = getIntent().getStringExtra("city");
		findView();
		adaptView();
		onclick();
		lineChart = new LineChart(WeathDetailsActivity.this);
		setValue();
	}

	private void findView() {
		back = (Button) findViewById(R.id.back);
		cityName = (TextView) findViewById(R.id.cityName);
		curTime = (TextView) findViewById(R.id.curTime);
		centigrate = (TextView) findViewById(R.id.centigrade);
		weather = (TextView) findViewById(R.id.weather);
		refreshTime = (TextView) findViewById(R.id.refreshTime);
		tempRange_windDirection = (TextView) findViewById(R.id.tempRange_windDirection);
		linear2 = (LinearLayout) findViewById(R.id.details_liner2);
		details_linear1 = (LinearLayout) findViewById(R.id.details_linear1);
		details_linear2 = (LinearLayout) findViewById(R.id.details_linear2);
		weatherLinear = (LinearLayout) findViewById(R.id.weatherDetailsLinear);
	}

	private void adaptView() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int width = metrics.widthPixels;
		int height = metrics.heightPixels;

		LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 0);
		layout.height = height / 12;
		layout.topMargin = 30;
		details_linear1.setLayoutParams(layout);

		details_linear2.setLayoutParams(layout);

		centigrate.setHeight(height / 12);
		refreshTime.setHeight(height / 12);
		tempRange_windDirection.setHeight(height / 12);

		maxY = 100;
		minY = 200;
		startX = width / 8;
	}

	private void onclick() {
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(WeathDetailsActivity.this,
						WeatherActivity.class));
			}
		});
	}

	private void addLayout(int[] maxTemp, int[] minTemp, int maxY, int minY,
			int startX) {
		LineChart lineChart = new LineChart(WeathDetailsActivity.this);

		lineChart.maxTemp = new int[maxTemp.length];
		for (int i = 0; i < maxTemp.length; i++) {
			lineChart.maxTemp[i] = maxTemp[i] * 10;
		}
		lineChart.maxY = maxY;

		lineChart.minTemp = new int[minTemp.length];
		for (int i = 0; i < minTemp.length; i++) {
			lineChart.minTemp[i] = minTemp[i] * 10;
		}
		lineChart.minY = minY;
		lineChart.startX = startX;

		lineChart.invalidate();
		weatherLinear.addView(lineChart);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0x01) {
				@SuppressWarnings("unchecked")
				List<PublicBean> list = (List<PublicBean>) msg.obj;
				System.out.println("list=" + list);
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getStatus() == 200
							&& list.get(i).getMessage().equals("OK")) {
						List<Object> lists = list.get(i).getList();
						int j;
						for (j = 0; j < lists.size(); j++) {
							Weather_CurBean weather_CurBean = (Weather_CurBean) lists
									.get(j);
							if (weather_CurBean.getCity().equals(
									weather_CurBean.getDistrict())) {
								cityName.setText(weather_CurBean.getDistrict()
										+ "  |");
							} else {
								cityName.setText(weather_CurBean.getCity()
										+ " " + weather_CurBean.getDistrict()
										+ "  |");
							}
							curTime.setText(weather_CurBean.getDateTime());
							centigrate.setText(weather_CurBean.getTemp());
							weather.setText(weather_CurBean.getWeather());
							String img_1 = weather_CurBean.getImg_1();
							String img_2 = weather_CurBean.getImg_2();
							int i1 = Integer.parseInt(img_1);
							int i2 = Integer.parseInt(img_2);
							refreshTime.setText("刷新时间：  "
									+ weather_CurBean.getRefreshTime());
							minTemp = weather_CurBean.getMinTemp();
							maxTemp = weather_CurBean.getMaxTemp();
							windDirection = weather_CurBean.getWindDirection();
							windForce = weather_CurBean.getWindForce();
							tempRange_windDirection.setText(minTemp + "~"
									+ maxTemp + " | " + windDirection
									+ windForce);
						}
						new Thread(runnable2).start();
						// progressDialog.dismiss();
					} else {
						Toast.makeText(WeathDetailsActivity.this,
								"对不起，获取" + city + "地区的天气信息失败!",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		}

		private boolean lookPicture(ImageView img, int i1, String img_1) {
			if (i1 <= 9) {
				img_1 = "a0" + img_1;
			} else {
				img_1 = "a" + img_1;
			}
			java.lang.reflect.Field[] fields = R.drawable.class
					.getDeclaredFields();
			System.out.println("img1=" + img_1);
			for (java.lang.reflect.Field field : fields) {
				if (img_1.equals(field.getName())) {
					try {
						System.out.println("img=" + field.getName() + ","
								+ field.getInt(field.getName()));
						img.setBackgroundResource(field.getInt(field.getName()));
						return true;
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return false;
		}

		private String getLaterTime(String times) {
			String time = times.split("年")[1];
			String year;
			String month;
			String day;
			year = times.split("年")[0];
			month = times.split("年")[1].split("月")[0];
			day = times.split("年")[1].split("月")[1].split("日")[0];
			int year1 = Integer.parseInt(year), month1 = Integer
					.parseInt(month), day1 = Integer.parseInt(day);
			for (int i = 1; i <= 5; i++) {
				day1 += 1;
				if (month1 == 1 || month1 == 3 || month1 == 5 || month1 == 7
						|| month1 == 8 || month1 == 10 || month1 == 12) {
					if (day1 > 31) {
						month1 += 1;
						if (month1 > 12) {
							year1 += 1;
						}
					}
				} else if (month1 == 4 || month1 == 6 || month1 == 9
						|| month1 == 11) {
					if (day1 > 30) {
						month1 += 1;
						if (month1 > 12) {
							year1 += 1;
						}
					}
				} else {
					if (day1 > 28) {
						month1 += 1;
						if (month1 > 12) {
							year1 += 1;
						}
					}
				}
				time += "  " + month1 + "月" + day1 + "日";
			}
			return time;
		}
	};

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("district", city));
			String jsonString = GetHttpUtil.getHttpOfTranslate(
					UriOfWhole.CURWEATHER, params);
			Message msg = handler.obtainMessage();
			msg.what = 0x01;
			msg.obj = JsonResolveUtil.getCur_Weather(jsonString);
			handler.sendMessage(msg);
		}
	};

	Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0x02) {
				@SuppressWarnings("unchecked")
				List<PublicBean> list = (List<PublicBean>) msg.obj;
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getStatus() == 200
							&& list.get(i).getMessage().equals("OK")) {
						List<Object> lists = list.get(i).getList();
						int j;
						list1 = new ArrayList<Map<String, Object>>();
						for (j = 0; j < lists.size(); j++) {
							WeekWeatherBean weekWeatherBean = (WeekWeatherBean) lists
									.get(j);
							int[] maxTemp = new int[weekWeatherBean.getTemp_1().length];
							int[] minTemp = new int[weekWeatherBean.getTemp_1().length];
							for (int k = 0; k < weekWeatherBean.getTemp_1().length; k++) {
								String[] array = weekWeatherBean.getTemp_1()[k]
										.split("℃~|℃");
								maxTemp[k] = Integer.parseInt(array[0]);
								minTemp[k] = Integer.parseInt(array[1]);
							}
							
							addLayout(maxTemp, minTemp, maxY, minY, startX);
							maxTemp = null;
							minTemp = null;
						}
					} else {
						Toast.makeText(WeathDetailsActivity.this,
								"对不起，获取" + city + "地区的天气信息失败!",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		}
	};

	Runnable runnable2 = new Runnable() {

		@Override
		public void run() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			System.out.println("city=" + city);
			params.add(new BasicNameValuePair("district", city));
			String jsonString = GetHttpUtil.getHttpOfTranslate(
					UriOfWhole.FUTHERWEATHER, params);
			System.out.println("jsoning=" + jsonString);
			Message msg = handler2.obtainMessage();
			msg.what = 0x02;
			msg.obj = JsonResolveUtil.getFur_Weather(jsonString);
			handler2.sendMessage(msg);
		}
	};

	private void setValue() {
		if (GetHttpUtil.isNetWorkEnable(WeathDetailsActivity.this)) {
			if (city.equals("")) {
				Toast.makeText(WeathDetailsActivity.this, "请输入正确的城市名！",
						Toast.LENGTH_SHORT).show();
				startActivity(new Intent(WeathDetailsActivity.this,
						WeatherActivity.class));
			} else
				new Thread(runnable).start();
		} else {
			Toast.makeText(WeathDetailsActivity.this, "网络未连接，请联网后操作！",
					Toast.LENGTH_SHORT).show();
			startActivity(new Intent(WeathDetailsActivity.this,
					WeatherActivity.class));
		}

	}

}
