package com.mix.film;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.mix.R;
import com.mix.MainActivity;
import com.mix.isbn.ISBNActivity;

public class FilmActivity extends Activity {
	private Button back;
	private EditText inputCity;
	private Button search;
	public static String city;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_film);
		
		findView();
		onclick();
	}
	
	private void findView(){
		back = (Button)findViewById(R.id.backFilm);
		inputCity = (EditText)findViewById(R.id.inputCityFilm);
		search = (Button)findViewById(R.id.searchFilm);
	}
	
	private void onclick(){
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(FilmActivity.this, MainActivity.class));
			}
		});
		
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				city = inputCity.getText().toString();
				Intent intent = new Intent();
				intent.putExtra("city", city);
				intent.setClass(FilmActivity.this, FilmListActivity.class);
				startActivity(intent);
			}
		});
	}
	
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent intent = new Intent();
			intent.setClass(FilmActivity.this, MainActivity.class);
			startActivity(intent);
			return true;
		}else{
			return super.onKeyDown(keyCode, event);
		}
	}
}
