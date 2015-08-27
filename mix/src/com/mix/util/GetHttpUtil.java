package com.mix.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class GetHttpUtil {
	//∑≠“Î,post,÷«ƒ‹øÏµ›
	public static String getHttpOfTranslate(String url,List<NameValuePair> params){
		String result = null;
		HttpPost httpRequest = new HttpPost(url);
		try {
			httpRequest.setEntity(
					new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = 
					new DefaultHttpClient().execute(httpRequest);
			if(httpResponse.getStatusLine().getStatusCode() == 200){
				result = EntityUtils.toString(httpResponse.getEntity(),"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	//≈–∂œ «∑Ò¡¨Ω”Õ¯¬Á
	public static boolean isNetWorkEnable(Context context){
		ConnectivityManager cm = (ConnectivityManager)context.
				getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo neInfo = cm.getActiveNetworkInfo();
		if(neInfo == null){
			return false;
		}else{
			return neInfo.isConnectedOrConnecting();
		}
	}
}
