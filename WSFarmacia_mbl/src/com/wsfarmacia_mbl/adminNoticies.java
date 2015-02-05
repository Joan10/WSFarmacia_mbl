package com.wsfarmacia_mbl;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

public class adminNoticies extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_noticies);
		
		final ImageButton btn = (ImageButton) findViewById(R.id.admin_noticies_popupMenuBtn);
		final PopupMenu popupMenu = new PopupMenu(this, btn);
		popupMenu.inflate(R.menu.admin_popup);	
		btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });	
		
		popupMenu.setOnMenuItemClickListener(
                new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_red:
                        Log.w("admin","RED");
                        break;
                    case R.id.menu_blue:
                        Log.w("admin","BLUE");
                        break;
                    case R.id.menu_green:
                    	Log.w("admin","GREEN");
                        break;
                }
                return true;
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
