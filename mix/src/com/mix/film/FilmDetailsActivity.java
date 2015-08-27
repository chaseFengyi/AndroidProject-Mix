package com.mix.film;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mix.R;
import com.mix.MainActivity;
import com.mix.bean.AllMovieBean;
import com.mix.isbn.ISBNActivity;
import com.mix.publicpart.SeePicture;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FilmDetailsActivity extends Activity {
	private Button back;
	private TextView titleName, filmName, filmNation, filmActors, filmLength,
			filmScore, filmDirector, filmTags, filmWd, filmMessage;
	private ImageView picture;
	private static String path = "";
	private static Bitmap bitmap = null;
	private static int primaryWidth;
	private static int primaryHeight;

	private RelativeLayout title;
	private LinearLayout layoutTop;
	private LinearLayout layoutBottom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_filmdetails);

		findView();
		adaptView();
		onclick();
		listAllInformation();
	}

	private void onclick() {
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("city", FilmActivity.city);
				intent.setClass(FilmDetailsActivity.this,
						FilmListActivity.class);
				startActivity(intent);
			}
		});
	}

	private void findView() {
		title = (RelativeLayout) findViewById(R.id.relativeFilmDetails);
		back = (Button) findViewById(R.id.backFilmDetails);
		titleName = (TextView) findViewById(R.id.titleNameFilmDetils);
		filmName = (TextView) findViewById(R.id.filmNameFilmDetails);
		filmNation = (TextView) findViewById(R.id.filmNationFilmDetails);
		filmActors = (TextView) findViewById(R.id.filmActorsFilmDetails);
		filmLength = (TextView) findViewById(R.id.filmLengthFilmDetails);
		filmScore = (TextView) findViewById(R.id.filmScoreFilmDetails);
		filmDirector = (TextView) findViewById(R.id.filmDirectorFilmDetails);
		filmTags = (TextView) findViewById(R.id.filmTagsFilmDetails);
		filmWd = (TextView) findViewById(R.id.filmWDFilmDetails);
		filmMessage = (TextView) findViewById(R.id.filmMessageFilmDetails);
		picture = (ImageView) findViewById(R.id.pictureFilmDetils);
		layoutTop = (LinearLayout) findViewById(R.id.lineTop);
		layoutBottom = (LinearLayout) findViewById(R.id.linearBottom);
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
				LayoutParams.WRAP_CONTENT, 0);
		layout1.height = curHeight * 2 / 5;
		layoutTop.setLayoutParams(layout1);

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				0, 0);
		layoutParams.height = curHeight * 3 / 5;
		layoutParams.width = width;
		layoutBottom.setLayoutParams(layoutParams);
	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			bitmap = SeePicture.seePicture(path);
			primaryWidth = bitmap.getWidth();
			primaryHeight = bitmap.getHeight();
			Bitmap bit = SeePicture.scale(0.4, 0.5, primaryWidth,
					primaryHeight, bitmap);
			picture.setImageBitmap(bit);
		}
	};

	private void listAllInformation() {
		AllMovieBean allMovieBean = (AllMovieBean) getIntent()
				.getSerializableExtra("movies");
		titleName.setText(allMovieBean.getMovie_name());
		System.out.println("titleName=" + titleName.getText().toString());
		path = allMovieBean.getMovie_picture();
		// 显示图片的配置
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_launcher)
				// 加载开始默认的图片
				.cacheInMemory().cacheOnDisc()
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoader.getInstance().displayImage(path, picture, options);
		filmName.setText(allMovieBean.getMovie_name() + " "
				+ allMovieBean.getMovie_type() + " "
				+ allMovieBean.getMovie_release_date());
		filmName.setTextSize(20);
		TextPaint fn = filmName.getPaint();
		fn.setFakeBoldText(true);
		filmScore.setText("影片评分： " + allMovieBean.getMovie_score() + "\t");
		filmNation.setText("国家/地区： " + allMovieBean.getMovie_nation() + "\t");
		filmActors.setText("主演：" + allMovieBean.getMovie_staring());
		filmLength.setText("时长：" + allMovieBean.getMovie_length() + "\t");
		filmDirector.setText("导演：" + allMovieBean.getMovie_direction() + "\t");
		filmTags.setText("类型：" + allMovieBean.getMovie_tags());
		filmWd.setText("关键字：" + allMovieBean.getMovies_wd());
		filmMessage.setText("\t\t" + allMovieBean.getMovie_message());
	}

	// 清除缓存
	public void onClearMemoryClick(View view) {
		Toast.makeText(this, "清除内存缓存成功", Toast.LENGTH_SHORT).show();
		ImageLoader.getInstance().clearMemoryCache(); // 清除内存缓存
	}

	public void onClearDiskClick(View view) {
		Toast.makeText(this, "清除本地缓存成功", Toast.LENGTH_SHORT).show();
		ImageLoader.getInstance().clearDiscCache(); // 清除本地缓存
	}
	
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent intent = new Intent();
			intent.putExtra("city", FilmActivity.city);
			intent.setClass(FilmDetailsActivity.this,
					FilmListActivity.class);
			startActivity(intent);
			return true;
		}else{
			return super.onKeyDown(keyCode, event);
		}
	}
}
