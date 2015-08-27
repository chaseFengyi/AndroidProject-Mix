package com.mix.film;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class CrashApplication extends Application {
	// ��һ�ν���app
	public boolean isFirstIn = true;

	@Override
	public void onCreate() {
		super.onCreate();

		// ײ��Ĭ�ϵ�ImageLoader���ò���
		/*ImageLoaderConfiguration configuration = ImageLoaderConfiguration
				.createDefault(this);*/
		ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.
				Builder(getApplicationContext()).build();
		ImageLoader.getInstance().init(configuration);
	}
}