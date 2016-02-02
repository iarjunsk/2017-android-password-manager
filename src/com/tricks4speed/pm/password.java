package com.tricks4speed.pm;

import java.security.PublicKey;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;
import android.content.Intent;

public class password extends Activity {

	EditText passw;
	DigitalClock dg;
	public int counter = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password);

		passw = (EditText) findViewById(R.id.passwordText);
		dg = (DigitalClock) findViewById(R.id.digitalClock1);

		// Create Share Preference for Login detials
		SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
		SharedPreferences.Editor Ed = sp.edit();

		// create shared preference to check if the app is opened for the first time
		SharedPreferences fir = getSharedPreferences("Fir", MODE_PRIVATE);
		SharedPreferences.Editor firEd = fir.edit();

		//checks if app is opened for the first time.
		if (fir.getBoolean("my_first_time", true)) {
			Ed.putString("Psw", "8989");  //if yes.Then password is Login password is set to 8989
			Ed.commit();   // write the value..

			Toast.makeText(getBaseContext(), "Initial Password is 8989", Toast.LENGTH_SHORT).show();

			Toast.makeText(getBaseContext(), "Tap the Clock 3 times !!", Toast.LENGTH_LONG).show();

			// record the fact that the app has been started at least once
			fir.edit().putBoolean("my_first_time", false).commit();  

		} 
		

		// for hiding the text Field
		dg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				counter++;
				if (counter >= 3)
					passw.setVisibility(View.VISIBLE);
			}
		});

		passw.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				
				//Checks if the password is entered ie NEXT button or Enter is pressed.
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					
					// compare the password field pass value with stored password value.
					if (passw.getText().toString().trim()
							.equals(getSharedPreferences("Login", MODE_PRIVATE).getString("Psw", null).trim() )) {
						
						// if password is correct, we login to the main screen.
						Intent intent = new Intent(password.this, PasswordManagerActivity.class);
						startActivity(intent);
						finish();
					} else {
						Toast.makeText(getBaseContext(), "Wrong Password", Toast.LENGTH_LONG).show();

					}

					return true;
				}
				return false;
			}
		});

	}

}
