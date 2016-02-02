package com.tricks4speed.pm;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class masterPass extends Activity {

	EditText cur, new1, rep;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.masterpass);

		cur = (EditText) findViewById(R.id.etCurrent);
		new1 = (EditText) findViewById(R.id.etNew);
		rep = (EditText) findViewById(R.id.etRepeat);
		
		//to set cur EditText to get the focus
		cur.requestFocus();
		cur.setFocusableInTouchMode(true);

		Button save;
		save = (Button) findViewById(R.id.bSave);

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String Scur, Snew1, Srep;
				Scur = cur.getText().toString();
				Snew1 = new1.getText().toString();
				Srep = rep.getText().toString();

				if (Scur.equals("") || Snew1.equals("") || Srep.equals("")) {
					Toast.makeText(getBaseContext(), "Enter all the Fields",
							Toast.LENGTH_SHORT).show();
				}

				else {

					// Get Value from Share preference
					SharedPreferences sp1 = getSharedPreferences("Login", 0);
					String pass = sp1.getString("Psw", null);

					// Update the Share Preference values:
					if (pass.equals(Scur)) {

						if (Snew1.equals(Srep)) {
							SharedPreferences.Editor Ed = sp1.edit();
							Ed.putString("Psw", Snew1);
							Ed.commit();
							
							Toast.makeText(getBaseContext(),
									"Master Password Changed",
									Toast.LENGTH_SHORT).show();
							
						} else {
							Toast.makeText(getBaseContext(),
									"New Pass != Repeat Pass",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getBaseContext(),
								"Wrong Current Password!", Toast.LENGTH_SHORT)
								.show();
					}

				}

			}
		});

	}

}
