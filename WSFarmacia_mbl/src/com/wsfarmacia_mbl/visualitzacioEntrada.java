package com.wsfarmacia_mbl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

public class visualitzacioEntrada extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.visualitzacio_entrada);
		
		final ImageButton back = (ImageButton) findViewById(R.id.visualitzacio_entrada_back);
		
		back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });	
		
		Intent intent = getIntent();
		TextView id_entrada = (TextView) findViewById(R.id.visualitzacio_entrada_id);
		TextView dataihora_entrada = (TextView) findViewById(R.id.visualitzacio_entrada_dataihora);
		TextView medicament_entrada = (TextView) findViewById(R.id.visualitzacio_entrada_medicament);
		TextView quantitat_entrada = (TextView) findViewById(R.id.visualitzacio_entrada_quantitat);
		
		
		id_entrada.setText(intent.getStringExtra(adminEntrades.MSG_ID_ENTRADES));
		dataihora_entrada.setText(intent.getStringExtra(adminEntrades.MSG_DATAIHORA_ENTRADES));
		medicament_entrada.setText(intent.getStringExtra(adminEntrades.MSG_MEDICAMENT_ENTRADES));
		quantitat_entrada.setText(intent.getStringExtra(adminEntrades.MSG_QUANTITAT_ENTRADES));
		
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
