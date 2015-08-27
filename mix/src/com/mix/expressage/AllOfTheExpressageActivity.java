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
	 * ��ʾ��ĸ��TextView
	 */
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;

	/**
	 * ����ת����ƴ������
	 */
	private List<SortModel> SourceDateList;

	/**
	 * ����ƴ��������ListView�����������
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
							"�Բ���û�п������Ϣ!", Toast.LENGTH_SHORT).show();
				} else {
					initViews();
				}
			}
		}
	};

	// ��ȡ���п����
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
	
	//��ʾ���ļ��ж�ȡ������
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

		// ���ڱ��ز���
		List<String> search = ExpressCarrier.read();
		System.out.println("search="+search);
		if (search == null || search.size() <= 0) {
			System.out.println("22222222222222222");
			if (GetHttpUtil.isNetWorkEnable(AllOfTheExpressageActivity.this)) {
				getAllExpressager();
			} else {
				Toast.makeText(AllOfTheExpressageActivity.this, "����������~~~",
						Toast.LENGTH_SHORT).show();
			}
		}else{
			showContent(search);
		}
	}

	private void initViews() {
		// ʵ��������תƴ����
		pinyinComparator = new PinyinComparator();

		sideBar = (SideBar) findViewById(R.id.sidrbar);
		// dialog = (TextView) findViewById(R.id.dialog);
		// sideBar.setTextView(dialog);

		// �����Ҳഥ������
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// ����ĸ�״γ��ֵ�λ��
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
				// ����Ҫ����adapter.getItem(position)����ȡ��ǰposition����Ӧ�Ķ���
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

		// ����a-z��������Դ����
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);

		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

		// �������������ֵ�ĸı�����������
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// ������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
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
	 * ΪListView�������
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
			// ����ת����ƴ��
			// String pinyin = characterParser.getSelling(date[i]);
			String pinyin = Pinyin.HanyuToPinyin(name);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
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
	 * ����������е�ֵ���������ݲ�����ListView
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

		// ����a-z��������
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
}
