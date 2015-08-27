package com.mix.film;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mix.R;
import com.mix.bean.AllMovieBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyAdapterFilm extends BaseAdapter {
	private int resource;
	private Context context;
	private List<Object> movies;

	public MyAdapterFilm(int resource, Context context, List<Object> movies) {
		this.resource = resource;
		this.context = context;
		this.movies = movies;
	}

	public void updateListView(List<Object> movies) {
		this.movies = movies;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return movies.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return movies.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		final AllMovieBean allMovieBean = (AllMovieBean) movies.get(arg0);
		if (allMovieBean != null) {
			LinearLayout ll;
			ll = (LinearLayout)LayoutInflater.from(context).inflate(resource, null);
			final ImageView movie_picture = (ImageView) ll
					.findViewById(R.id.picture);
			TextView movie_name = (TextView) ll.findViewById(R.id.filmName);
			TextView movie_score = (TextView) ll.findViewById(R.id.score);
			TextView movie_actor = (TextView) ll.findViewById(R.id.actor);
			TextView movie_director = (TextView) ll
					.findViewById(R.id.director);
			movie_name.setText(allMovieBean.getMovie_name());
			movie_score.setText("评分： " + allMovieBean.getMovie_score());
			movie_actor
					.setText("主演： " + allMovieBean.getMovie_staring());
			movie_director.setText("导演： "
					+ allMovieBean.getMovie_direction());
			String path = allMovieBean.getMovie_picture();
			// 显示图片的配置
			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.showImageForEmptyUri(R.drawable.ic_launcher)//加载开始默认的图片
					.cacheInMemory()
					.cacheOnDisc()
					.bitmapConfig(Bitmap.Config.RGB_565).build();
			ImageLoader.getInstance().displayImage(path, movie_picture,options);
			return ll;
		}
		return null;
	}


}
