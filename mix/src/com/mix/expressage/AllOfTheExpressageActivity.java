package com.mix.expressage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mix.R;
import com.mix.bean.AllExpressageBean;
import com.mix.bean.PublicBean;
import com.mix.dao.ExpressCarrier;
import com.mix.expressage.SideBar.OnTouchingLetterChangedListener;
import com.mix.publicpart.UriOfWhole;
import com.mix.util.GetHttpUtil;
import com.mix.util.JsonResolveUtil;

public class AllOfTheExpressageActivity extends Activity {
	private ListView sortListView;
	private SideBar sideBar;
	/**
	 * 显示字母的TextView
	 */
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;

	/**
	 * 汉字转换成拼音的类
	 */
	private List<SortModel> SourceDateList;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	public static List<Map<String, String>> b = new ArrayList<Map<String, String>>();

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = GetHttpUtil.getHttpOfTranslate(
					UriOfWhole.ALL_EXPRESSAGE, params);
			Message msg = handler.obtainMessage();
			msg.obj = JsonResolveUtil.getJsonResultByAllExpressage(jsonString);
			msg.what = 0x01;
			handler.sendMessage(msg);
		}
	};

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0x01) {
				@SuppressWarnings("unchecked")
				List<PublicBean> list = (List<PublicBean>) msg.obj;
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getStatus() == 200
							&& list.get(i).getMessage().equals("OK")) {
						List<Object> lists = list.get(i).getList();
						int j;
						String storeContent = "";
						for (j = 0; j < lists.size(); j++) {
							AllExpressageBean allExpressageBean = (AllExpressageBean) lists
									.get(j);
							Map<String, String> map = new HashMap<String, String>();
							map.put(allExpressageBean.getName(),
									allExpressageBean.getCom());
							b.add(map);
							storeContent += (allExpressageBean.getName() +"#"+allExpressageBean.getCom()+"\n");
						}
						
						ExpressCarrier.writeCover(storeContent);
						
						Set<Map<String, String>> set = new HashSet<Map<String, String>>(
								b);
						b.clear();
						b.addAll(set);
					} else {
						b = null;
					}
				}

				if (b == null) {
					Toast.makeText(AllOfTheExpressageActivity.this,
							"对不起，没有快递商信息!", Toast.LENGTH_SHORT).show();
				} else {
					initViews();
				}
			}
		}
	};

	// 获取所有快递商
	private void getAllExpressager() {
		new Thread(runnable).start();
	}

	/*
	 * for (j = 0; j < lists.size(); j++) {
							AllExpressageBean allExpressageBean = (AllExpressageBean) lists
									.get(j);
							Map<String, String> map = new HashMap<String, String>();
							map.put(allExpressageBean.getName(),
									allExpressageBean.getCom());
							b.add(map);
						}
						Set<Map<String, String>> set = new HashSet<Map<String, String>>(
								b);
						b.clear();
						b.addAll(set);
	 * */
	
	//显示从文件中读取的内容
	public void showContent(List<String> list){
		for(int i = 0; i < list.size(); i++){
			String[] express = (list.get(i)).split("#");
			Map<String, String> map = new HashMap<String, String>();
			map.put(express[0], express[1]);
			b.add(map);
		}
		Set<Map<String, String>> set = new HashSet<Map<String, String>>(
				b);
		b.clear();
		b.addAll(set);
		
		initViews();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_allexpressage);

		// 先在本地查找
		List<String> search = ExpressCarrier.read();
		System.out.println("search="+search);
		if (search == null || search.size() <= 0) {
			System.out.println("22222222222222222");
			if (GetHttpUtil.isNetWorkEnable(AllOfTheExpressageActivity.this)) {
				getAllExpressager();
			} else {
				Toast.makeText(AllOfTheExpressageActivity.this, "请连接网络~~~",
						Toast.LENGTH_SHORT).show();
			}
		}else{
			showContent(search);
		}
	}

	private void initViews() {
		// 实例化汉字转拼音类
		pinyinComparator = new PinyinComparator();

		sideBar = (SideBar) findViewById(R.id.sidrbar);
		// dialog = (TextView) findViewById(R.id.dialog);
		// sideBar.setTextView(dialog);

		// 设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}

			}
		});

		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 这里要利用adapter.getItem(position)来获取当前position所对应的对象
				// Toast.makeText(getApplication(),
				// ((SortModel)adapter.getItem(position)).getName(),
				// Toast.LENGTH_SHORT).show();
				String name = ((SortModel) adapter.getItem(position)).getName();
				Intent intent = new Intent();
				intent.putExtra("name", name);
				intent.setClass(AllOfTheExpressageActivity.this,
						ExpressageActivity.class);
				startActivity(intent);
			}
		});

		// SourceDateList =
		// filledData(getResources().getStringArray(R.array.date));
		SourceDateList = filledData(b);

		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);

		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

		// 根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	/**
	 * 为ListView填充数据
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(List<Map<String, String>> date) {
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < date.size(); i++) {
			SortModel sortModel = new SortModel();
			Map<String, String> map = date.get(i);
			Iterator iterator = map.entrySet().iterator();
			String name = "";
			while (iterator.hasNext()) {
				Entry entry = (Entry) iterator.next();
				name = (String) entry.getKey();
			}
			sortModel.setName(name);
			// 汉字转换成拼音
			// String pinyin = characterParser.getSelling(date[i]);
			String pinyin = Pinyin.HanyuToPinyin(name);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				/*
				 * if (name.toUpperCase().indexOf(
				 * filterStr.toString().toUpperCase()) != -1 ||
				 * characterParser.getSelling(name).toUpperCase()
				 * .startsWith(filterStr.toString().toUpperCase())) {
				 * filterDateList.add(sortModel); }
				 */
				if (name.toUpperCase().indexOf(
						filterStr.toString().toUpperCase()) != -1
						|| Pinyin.HanyuToPinyin(name).toUpperCase()
								.startsWith(filterStr.toString().toUpperCase())) {
					filterDateList.add(sortModel);
				}
			}
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
}
