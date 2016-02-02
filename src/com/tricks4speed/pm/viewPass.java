package com.tricks4speed.pm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class viewPass extends Activity {
	
	
	
	
	
	// for inflating the menu
		public boolean onCreateOptionsMenu(Menu menu) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.menu2, menu);
			return true;
		}

		// on selection of the menu
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle item selection
			switch (item.getItemId()) {
			case R.id.add_pass:
				Intent intent = new Intent(this, PasswordManagerActivity.class);
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
		
		
		
	
	
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpass);
		


		// Find the directory for the SD Card using the API
		// *Don't* hardcode "/sdcard"
		File sdcard = Environment.getExternalStorageDirectory();

		// Get the text file
		File file = new File(sdcard, ".sk/logp.csv");

		// Read text from file
		StringBuilder text = new StringBuilder();

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			while ((line = br.readLine()) != null) {
				String[] temp = line.split(",");

				TableLayout table = (TableLayout) findViewById(R.id.maintable);

				// Inflate your row "template" and fill out the fields.
				TableRow row = (TableRow) LayoutInflater.from(viewPass.this)
						.inflate(R.layout.attrib_row, null);
				((TextView) row.findViewById(R.id.attrib_site))
						.setText(temp[0]);
				((TextView) row.findViewById(R.id.attrib_username))
						.setText(temp[1]);

				// decrypted clear text
				SimpleCrypto mcrypt = new SimpleCrypto();
				String cleartext ="";
				try{cleartext=new String( mcrypt.decrypt( temp[2] ) );}
				catch (Exception e) {e.printStackTrace();}
						
				((TextView) row.findViewById(R.id.attrib_pass))
						.setText(cleartext);
				
				
				((TextView) row.findViewById(R.id.attrib_additional))
						.setText(temp[3]);

				table.addView(row);
				
				// add a null row to the end of each data
				TableRow rownull = (TableRow) LayoutInflater.from(viewPass.this)
						.inflate(R.layout.empty_row, null);
				table.addView(rownull);
				
				//dont know if this is needed
				table.requestLayout();

			}
			br.close();
		} catch (IOException e) {
			// You'll need to add proper error handling here
			e.printStackTrace();
		}

	}

}
