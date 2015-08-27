package com.mix.weather;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mix.R;
import com.mix.MainActivity;
import com.mix.bean.PublicBean;
import com.mix.bean.Weather_CityBean;
import com.mix.publicpart.UriOfWhole;
import com.mix.translate.TranslateActivity;
import com.mix.util.GetHttpUtil;
import com.mix.util.JsonResolveUtil;

public class WeatherActivity extends Activity {
	private EditText inputCity;
	private Button search;
	private Button back;
	private Spinner provinceSpinner;
	private Spinner citySpinner;
	//从服务器获取完整省份，城市，地区名
	private static List<String>  provinces = new ArrayList<String>();
	private static List<String>  citys = new ArrayList<String>();
	//没有重复的所有省份，城市，地区名
	private static List<String>  subProvinces = new ArrayList<String>();
	private static List<String>  subCitys = new ArrayList<String>();
	//响应省份对应城市，相应城市对应地区
	private static List<String>  prov_city = new ArrayList<String>();
	
	private ArrayAdapter<String> adapterProvince;
	private ArrayAdapter<String> adapterCity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_weather);
		
		findView();
		onclick();
		if (GetHttpUtil.isNetWorkEnable(WeatherActivity.this)) {
			new Thread(runnable).start();
		} 
		
	}
	
	private void setSpinnerAdapter(ArrayAdapter<String> adapter,List<String> city,
			OnItemSelectedListener listener,Spinner spinner){
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,city);
		adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_print);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(listener);
		spinner.setVisibility(View.VISIBLE);
	}
	
	private void findView(){
		back = (Button)findViewById(R.id.back);
		inputCity = (EditText)findViewById(R.id.inputCity);
		search = (Button)findViewById(R.id.search);
		provinceSpinner = (Spinner)findViewById(R.id.provinceSpinner);
		citySpinner = (Spinner)findViewById(R.id.CitySpinner);
	}
	
	private void onclick(){
		back.setOnClickListener(listener);
		search.setOnClickListener(listener);
	}
	
	OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent();
			switch(arg0.getId()){
			case R.id.back:
				intent.setClass(WeatherActivity.this, MainActivity.class);
				break;
			case R.id.search:
				String city = inputCity.getText().toString();
				intent.putExtra("city", city);
				intent.setClass(WeatherActivity.this, WeathDetailsActivity.class);
				break;
			}
			startActivity(intent);
		}
	};
	
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent intent = new Intent();
			intent.setClass(WeatherActivity.this, MainActivity.class);
			startActivity(intent);
			return true;
		}else{
			return super.onKeyDown(keyCode, event);
		}
	}
	
	private class ProvinceSpinnerListener implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			String curProvince = subProvinces.get(arg2);
			System.out.println("curpro="+curProvince);
			prov_city.clear();
			for(int i = 0; i < citys.size(); i++){
				if(provinces.get(i).equals(curProvince)){
					prov_city.add(citys.get(i));
				}
			}
			HashSet<String> set = new HashSet<String>(prov_city);
			prov_city.clear();
			prov_city.addAll(set);
			setSpinnerAdapter(adapterCity, prov_city, 
					new CitySpinnerListener(), citySpinner);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	private class CitySpinnerListener implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			String curCity = prov_city.get(arg2);
			for(int i = 0; i < citys.size(); i++){
				if(citys.get(i).equals(curCity)){
					inputCity.setText(citys.get(i));
				}
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = GetHttpUtil.getHttpOfTranslate(UriOfWhole.CITY_WEATHER, params);
			Message msg = handler.obtainMessage();
			msg.what = 0x01;
			msg.obj = JsonResolveUtil.getAllCity_Weather(jsonString);
			handler.sendMessage(msg);
		}
	};
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 0x01){
				@SuppressWarnings("unchecked")
				List<PublicBean> list = (List<PublicBean>)msg.obj;
				System.out.println("list="+list);
				for(int i = 0; i < list.size(); i++){
					if(list.get(i).getStatus() == 200 &&
							list.get(i).getMessage().equals("OK")){
						List<Object> lists = list.get(i).getList();
						int j;
						for(j = 0; j < lists.size(); j++){
							Weather_CityBean weather_CityBean = 
									(Weather_CityBean) lists.get(j);
							provinces.add(weather_CityBean.getProvince());
							citys.add(weather_CityBean.getCity());
						}
						HashSet<String> set = new HashSet<String>(provinces);
						subProvinces.addAll(set);
						set = new HashSet<String>(citys);
						subCitys.addAll(set);
						setSpinnerAdapter(adapterProvince, subProvinces,
								new ProvinceSpinnerListener(),provinceSpinner);
						setSpinnerAdapter(adapterCity, subCitys,
								new CitySpinnerListener(),citySpinner);
					}else{
						Toast.makeText(WeatherActivity.this, 
								"对不起，获取地区信息失败!", Toast.LENGTH_SHORT).show();
					}
				}
			}
		}
	};
	
}
