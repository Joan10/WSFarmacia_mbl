package com.wsfarmacia_mbl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	public static String IP_SERVER ="";
	// IP del servidor
	//El declaram estàtic per fer-ho més senzill
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		AlertDialog.Builder promptip = new AlertDialog.Builder(this);

		promptip.setMessage(R.string.introip_msg)
			   .setTitle(R.string.introip_titol);

		// Set up the input
		final EditText input = new EditText(this);
		// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		promptip.setView(input);

		// Set up the buttons
		promptip.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        IP_SERVER = input.getText().toString();
		    }
		});
		promptip.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.cancel();
		    }
		});
		AlertDialog dialog = promptip.create();
		dialog.show();
		
		Button buttonOne = (Button) findViewById(R.id.activity_main_entra_admin);
		buttonOne.setOnClickListener(new Button.OnClickListener() {
		    public void onClick(View v) {
		    	Intent adminIntent = new Intent(MainActivity.this, adminNoticies.class);
		    	MainActivity.this.startActivity(adminIntent);
		    }
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
