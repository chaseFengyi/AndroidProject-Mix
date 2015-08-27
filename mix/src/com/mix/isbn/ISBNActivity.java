package com.mix.isbn;

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
import com.mix.translate.TranslateActivity;

public class ISBNActivity extends Activity {
	private Button back;
	private EditText inputISBN;
	private Button search;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_isbn);
		
		findView();
		onclick();
	}

	private void onclick() {
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(ISBNActivity.this, MainActivity.class));
			}
		});
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String IsbnNum = inputISBN.getText().toString();
				Intent intent = new Intent();
				intent.putExtra("isbn", IsbnNum);
				intent.setClass(ISBNActivity.this, ContentActivity.class);
				startActivity(intent);
			}
		});
	}

	private void findView() {
		back = (Button)findViewById(R.id.backISBN);
		inputISBN = (EditText)findViewById(R.id.inputIsbn);
		search = (Button)findViewById(R.id.searchISBN);
	}
	
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent intent = new Intent();
			intent.setClass(ISBNActivity.this, MainActivity.class);
			startActivity(intent);
			return true;
		}else{
			return super.onKeyDown(keyCode, event);
		}
	}
}
