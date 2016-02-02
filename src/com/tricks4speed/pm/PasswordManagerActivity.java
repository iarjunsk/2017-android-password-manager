package com.tricks4speed.pm;

import java.io.File;
import java.io.FileWriter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordManagerActivity extends Activity {

	// initialise
	EditText site, username, password, Additional;
	Button save, reset;
	public String savedata = "";

	// for inflating the menu
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	// on selection of the menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.view_passwords:
			Intent intent = new Intent(this, viewPass.class);
			startActivity(intent);
			return true;
		case R.id.mp:
			Intent intent2 = new Intent(this, masterPass.class);
			startActivity(intent2);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		initialise();
		
		//to set the site Edit Text to get the focus
		site.requestFocus();
		site.setFocusableInTouchMode(true);

		// save the data to the textfile
		save.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				// creats hidden directory if not existing
				File dir = new File(Environment
						.getExternalStorageDirectory().getAbsolutePath()
						+ "/.sk/");
				if (!dir.exists()) {
					dir.mkdirs();
				}

				// saving data part
				String sFileName = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/.sk/" + "logp.csv";

				try {
					FileWriter writer = new FileWriter(sFileName, true);

					String sSite, sUser, sPass, sAdd;

					sSite = site.getText().toString();
					sUser = username.getText().toString();
					sPass = password.getText().toString();
					sAdd = Additional.getText().toString();
					
					
					
					if ((sSite.equals("")) && (sUser.equals(""))
							&& (sPass.equals("")) && (sAdd.equals(""))) {
						Toast.makeText(getBaseContext(), "Please Enter Atleast one Field",
								Toast.LENGTH_SHORT).show();

					} else {
						if (sSite.equals(""))
							sSite = "N/A";
						if (sUser.equals(""))
							sUser = "N/A";
						if (sPass.equals(""))
							sPass = "N/A";
						if (sAdd.equals(""))
							sAdd = "N/A";
						
						// encrypting the passwords before saving
						SimpleCrypto mcrypt = new SimpleCrypto();
						sPass = SimpleCrypto.bytesToHex( mcrypt.encrypt(sPass) );
						//sPass = SimpleCrypto.encrypt("fugly", sPass);

						

						writer.append(sSite);
						writer.append(',');
						writer.append(sUser);
						writer.append(',');

						writer.append(sPass);
						writer.append(',');
						writer.append(sAdd);

						writer.append('\n');

						// generate whatever data you want

						writer.flush();
						writer.close();

						Toast.makeText(getBaseContext(), "Saved :)",
								Toast.LENGTH_SHORT).show();
					}

				} catch (Exception e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		// Reset
		reset.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				site.setText("");
				username.setText("");
				password.setText("");
				Additional.setText("");
			}
		});

	}

	public void initialise() {

		site = (EditText) findViewById(R.id.EditTextSite);
		username = (EditText) findViewById(R.id.EditTextUsername);
		password = (EditText) findViewById(R.id.editTextPassword);
		Additional = (EditText) findViewById(R.id.editTextAdditional);

		save = (Button) findViewById(R.id.buttonSave);
		reset = (Button) findViewById(R.id.ButtonReset);
	}

}