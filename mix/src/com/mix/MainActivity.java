package com.mix;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mix.R;
import com.mix.expressage.ExpressageActivity;
import com.mix.film.FilmActivity;
import com.mix.isbn.ISBNActivity;
import com.mix.translate.TranslateActivity;
import com.mix.weather.WeatherActivity;

public class MainActivity extends Activity {
	private Button weather;
	private Button translate;
	private Button isbn;
	private Button film;
	private Button expressage;
	private FrameLayout frameLayout;

	int[] images = null;// ͼƬ��ԴID
	String[] titles = null;// ����

	ArrayList<ImageView> imageSource = null;// ͼƬ��Դ
	ArrayList<View> dots = null;// ��
	TextView title = null;
	ViewPager viewPager;// ������ʾͼƬ
	MyPagerAdapter adapter;// viewPager��������
	private int currPage = 0;// ��ǰ��ʾ��ҳ
	private int oldPage = 0;// ��һ����ʾ��ҳ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();

		findView();
		adaptView();
		click();
	}

	public void init() {
		images = new int[] { R.drawable.title_one9, R.drawable.title_two9,
				R.drawable.title_three9, R.drawable.title_four9,
				R.drawable.title_five9 };
		titles = new String[] { "ͼƬ1", "ͼƬ2", "ͼƬ3", "ͼƬ4", "ͼƬ5" };
		// ��Ҫ��ʾ��ͼƬ�ŵ�list������
		imageSource = new ArrayList<ImageView>();
		for (int i = 0; i < images.length; i++) {
			ImageView image = new ImageView(this);
			image.setBackgroundResource(images[i]);
			imageSource.add(image);
		}

		// ��ȡ��ʾ�ĵ㣨�������·��ĵ㣬��ʾ��ǰ�ǵڼ��ţ�
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.dot1));
		dots.add(findViewById(R.id.dot2));
		dots.add(findViewById(R.id.dot3));
		dots.add(findViewById(R.id.dot4));
		dots.add(findViewById(R.id.dot5));

		// ͼƬ�ı���
		title = (TextView) findViewById(R.id.title);
		title.setText(titles[0]);

		// ��ʾͼƬ��VIew
		viewPager = (ViewPager) findViewById(R.id.vp);
		// ΪviewPager����������
		adapter = new MyPagerAdapter();
		viewPager.setAdapter(adapter);
		// ΪviewPager��Ӽ��������ü��������ڵ�ͼƬ�任ʱ������͵�Ҳ���ű仯
		MyPageChangeListener listener = new MyPageChangeListener();
		viewPager.setOnPageChangeListener(listener);

		// ������ʱ����ÿ��2���Զ�������һ�ţ�ͨ�������߳�ʵ�֣�����Timer���ƣ���ʹ��Timer���棩
		ScheduledExecutorService scheduled = Executors
				.newSingleThreadScheduledExecutor();
		// ����һ���̣߳����߳�����֪ͨUI�̱߳任ͼƬ
		ViewPagerTask pagerTask = new ViewPagerTask();
		scheduled.scheduleAtFixedRate(pagerTask, 2, 2, TimeUnit.SECONDS);
	}

	// ViewPagerÿ�ν�����������ͼƬ�������ڷ�ֹ�����ڴ������
	private class MyPagerAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return images.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// �жϽ�Ҫ��ʾ��ͼƬ�Ƿ��������ʾ��ͼƬ��ͬһ��
			// arg0Ϊ��ǰ��ʾ��ͼƬ��arg1�ǽ�Ҫ��ʾ��ͼƬ
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// ���ٸ�ͼƬ
			container.removeView(imageSource.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// ��ʼ����Ҫ��ʾ��ͼƬ������ͼƬ���뵽container�У���viewPager��
			container.addView(imageSource.get(position));
			return imageSource.get(position);
		}
	}

	// ����ViewPager�ı仯
	private class MyPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			// ����ʾ��ͼƬ�����仯֮��
			// ���ñ���
			title.setText(titles[position]);
			// �ı���״̬
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			dots.get(oldPage).setBackgroundResource(R.drawable.dot_normal);
			// ��¼��ҳ��
			oldPage = position;
			currPage = position;
		}
	}

	private class ViewPagerTask implements Runnable {
		@Override
		public void run() {
			// �ı䵱ǰҳ���ֵ
			currPage = (currPage + 1) % images.length;
			// ������Ϣ��UI�߳�
			handler.sendEmptyMessage(0);
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// ���յ���Ϣ�󣬸���ҳ��
			viewPager.setCurrentItem(currPage);
		};
	};

	private void findView() {
		weather = (Button) findViewById(R.id.weather);
		translate = (Button) findViewById(R.id.translate);
		isbn = (Button) findViewById(R.id.isbn);
		film = (Button) findViewById(R.id.film);
		expressage = (Button) findViewById(R.id.expressage);
		frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
	}

	private void adaptView() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int width = metrics.widthPixels;
		int hight = metrics.heightPixels;

		RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(0,
				0);
		layout.width = width;
		layout.height = hight / 4 ;
		frameLayout.setLayoutParams(layout);
		
		int curHeight = hight - (hight / 4);
		
		weather.setWidth(width / 2 );
		weather.setHeight(curHeight / 5 + 10);
		translate.setWidth(width / 2 );
		translate.setHeight(curHeight / 5 + 10);
		isbn.setHeight(curHeight / 5 + 10);
		film.setHeight(curHeight / 5 + 10);
		expressage.setHeight(curHeight / 5 + 10);
	}

	private void click() {
		weather.setOnClickListener(listener);
		translate.setOnClickListener(listener);
		isbn.setOnClickListener(listener);
		film.setOnClickListener(listener);
		expressage.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (arg0.getId()) {
			case R.id.weather:
				intent.setClass(MainActivity.this, WeatherActivity.class);
				break;
			case R.id.translate:
				intent.setClass(MainActivity.this, TranslateActivity.class);
				break;
			// case R.id.trainticket:
			// break;
			case R.id.isbn:
				intent.setClass(MainActivity.this, ISBNActivity.class);
				break;
			case R.id.film:
				intent.setClass(MainActivity.this, FilmActivity.class);
				break;
			case R.id.expressage:
				intent.setClass(MainActivity.this, ExpressageActivity.class);
				break;
			}
			startActivity(intent);
		}
	};
	
}
