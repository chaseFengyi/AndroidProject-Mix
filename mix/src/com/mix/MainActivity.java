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

	int[] images = null;// 图片资源ID
	String[] titles = null;// 标题

	ArrayList<ImageView> imageSource = null;// 图片资源
	ArrayList<View> dots = null;// 点
	TextView title = null;
	ViewPager viewPager;// 用于显示图片
	MyPagerAdapter adapter;// viewPager的适配器
	private int currPage = 0;// 当前显示的页
	private int oldPage = 0;// 上一次显示的页

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
		titles = new String[] { "图片1", "图片2", "图片3", "图片4", "图片5" };
		// 将要显示的图片放到list集合中
		imageSource = new ArrayList<ImageView>();
		for (int i = 0; i < images.length; i++) {
			ImageView image = new ImageView(this);
			image.setBackgroundResource(images[i]);
			imageSource.add(image);
		}

		// 获取显示的点（即文字下方的点，表示当前是第几张）
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.dot1));
		dots.add(findViewById(R.id.dot2));
		dots.add(findViewById(R.id.dot3));
		dots.add(findViewById(R.id.dot4));
		dots.add(findViewById(R.id.dot5));

		// 图片的标题
		title = (TextView) findViewById(R.id.title);
		title.setText(titles[0]);

		// 显示图片的VIew
		viewPager = (ViewPager) findViewById(R.id.vp);
		// 为viewPager设置适配器
		adapter = new MyPagerAdapter();
		viewPager.setAdapter(adapter);
		// 为viewPager添加监听器，该监听器用于当图片变换时，标题和点也跟着变化
		MyPageChangeListener listener = new MyPageChangeListener();
		viewPager.setOnPageChangeListener(listener);

		// 开启定时器，每隔2秒自动播放下一张（通过调用线程实现）（与Timer类似，可使用Timer代替）
		ScheduledExecutorService scheduled = Executors
				.newSingleThreadScheduledExecutor();
		// 设置一个线程，该线程用于通知UI线程变换图片
		ViewPagerTask pagerTask = new ViewPagerTask();
		scheduled.scheduleAtFixedRate(pagerTask, 2, 2, TimeUnit.SECONDS);
	}

	// ViewPager每次仅最多加载三张图片（有利于防止发送内存溢出）
	private class MyPagerAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return images.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// 判断将要显示的图片是否和现在显示的图片是同一个
			// arg0为当前显示的图片，arg1是将要显示的图片
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// 销毁该图片
			container.removeView(imageSource.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// 初始化将要显示的图片，将该图片加入到container中，即viewPager中
			container.addView(imageSource.get(position));
			return imageSource.get(position);
		}
	}

	// 监听ViewPager的变化
	private class MyPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			// 当显示的图片发生变化之后
			// 设置标题
			title.setText(titles[position]);
			// 改变点的状态
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			dots.get(oldPage).setBackgroundResource(R.drawable.dot_normal);
			// 记录的页面
			oldPage = position;
			currPage = position;
		}
	}

	private class ViewPagerTask implements Runnable {
		@Override
		public void run() {
			// 改变当前页面的值
			currPage = (currPage + 1) % images.length;
			// 发送消息给UI线程
			handler.sendEmptyMessage(0);
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// 接收到消息后，更新页面
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
