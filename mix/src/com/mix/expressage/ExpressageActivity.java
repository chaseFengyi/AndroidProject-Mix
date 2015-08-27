package com.mix.expressage;

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
import android.widget.TextView;

import com.example.mix.R;
import com.mix.MainActivity;
import com.mix.isbn.ISBNActivity;

public class ExpressageActivity extends Activity {
	private Button back;
	private TextView companyName;
	private EditText singleNumber;
	private Button search;
	
	public static String companyNameString = "";
	public static String singleNumberString = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_expressage);
		
		findView();
		onclick();

		Intent inten = getIntent();
		String name = inten.getStringExtra("name");
		System.out.println("name==="+name);
		companyName.setText(name);
	}
	
	private void findView(){
		back = (Button)findViewById(R.id.backExpressage);
		companyName = (TextView)findViewById(R.id.companyName);
		singleNumber = (EditText)findViewById(R.id.singleNumber);
		search = (Button)findViewById(R.id.searchExpressage);
	}
	
	private void onclick(){
		back.setOnClickListener(listener);
		companyName.setOnClickListener(listener);
		search.setOnClickListener(listener);
	}
	
	OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent();
			switch(arg0.getId()){
			case R.id.companyName:
				intent.putExtra("name", "");
				intent.setClass(ExpressageActivity.this, AllOfTheExpressageActivity.class);
				startActivity(intent);
				break;
			case R.id.backExpressage:
				intent.setClass(ExpressageActivity.this, MainActivity.class);
				startActivity(intent);
				break;
			case R.id.searchExpressage:
				companyNameString = companyName.getText().toString();
				singleNumberString = singleNumber.getText().toString();
				intent.setClass(ExpressageActivity.this, ResultOfSearchActivity.class);
				startActivity(intent);
				break;
			}
		}
	};
	
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent intent = new Intent();
			intent.setClass(ExpressageActivity.this, MainActivity.class);
			startActivity(intent);
			return true;
		}else{
			return super.onKeyDown(keyCode, event);
		}
	}
	
}
