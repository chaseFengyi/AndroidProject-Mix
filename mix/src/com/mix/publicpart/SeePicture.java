package com.mix.publicpart;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class SeePicture {
	public static Bitmap seePicture(String path){
		byte[] data = getImage(path);
		Bitmap bitmap = null;
		if(data != null){
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		}
		return bitmap;
	}
	
	private static byte[] getImage(String path){
		URL url;
		try {
			url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			InputStream insStream = conn.getInputStream();
			if(conn.getResponseCode() == 200){
				return readStream(insStream);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] readStream(InputStream insStream) {
		ByteArrayOutputStream outstreStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		try {
			while((len = insStream.read(buffer)) != -1){
				outstreStream.write(buffer,0,len);
			}
			outstreStream.close();
			insStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outstreStream.toByteArray();
	}
	
	/**
	 * @param scale_width//缩放比例
	 * @param scale_height  缩放比例
	 * @param primaryWidth 初始宽度
	 * @param primaryHeight 初始高度
	 * @param bitmap 获取图片的bitmap
	 * @param imageView
	 */
	public static Bitmap scale(double scale_width, double scale_height,
			int primaryWidth,int primaryHeight,Bitmap bitmap){
		double scaleWidth = 1;//初始比例
		double scaleHeight = 1;
		scaleWidth = scaleWidth * scale_width;
		scaleHeight = scaleHeight * scale_height;
		
		Matrix matrix = new Matrix();//矩阵，用于图片比例缩放
		matrix.postScale((float)scaleWidth, (float)scaleHeight);
		
		//缩放后的bitmap
		Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, primaryWidth,primaryHeight,matrix,true);
//		imageView.setImageBitmap(bitmap1);
		return bitmap1;
	}
}
