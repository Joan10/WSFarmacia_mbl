package com.wsfarmacia_mbl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

public class adminMedicaments extends Activity {
	
	//Pel pas de missatges a altres intents
	public final static String TITOL_MEDICAMENT = "com.wsfarmacia_mbl.titolmedicament";
	public final static String ID_MEDICAMENT= "com.wsfarmacia_mbl.idmedicament";
	
	//Listener pels botons de notícia
	OnClickListener boto_medicament_Listener;
	
	private void TreuMedicaments(){
		/*
		 * Funció que posa els botons de cada notícia a la pantalla i
		 * hi afegeix el listener boto_noticia_listener.
		 */
		Button myButton = new Button(this);
		myButton.setOnClickListener(boto_medicament_Listener);
		myButton.setText("El meu medicament");
		myButton.setId(1545);
		LinearLayout ll = (LinearLayout)findViewById(R.id.admin_medicaments_linearlayout);
		ll.addView(myButton);
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_medicaments);
		
		//Preparam el menú popup
		final ImageButton btn = (ImageButton) findViewById(R.id.admin_medicaments_popupMenuBtn);
		final PopupMenu popupMenu = new PopupMenu(this, btn);
		popupMenu.inflate(R.menu.admin_popup);	
		btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });	
		
		//Listener del menú popup
		popupMenu.setOnMenuItemClickListener(
                new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
            	Intent adminIntent;
            	//Menú principal. "Popup" que apareix en pitjar al boto superior dret
            	//per navegar entre pantalles
                switch (item.getItemId()) {
                    case R.id.popup_menu_noticies:
                    	adminIntent = new Intent(adminMedicaments.this, adminNoticies.class);
                    	adminMedicaments.this.startActivity(adminIntent);
                    	break;
                    case R.id.popup_menu_farmacies:
        		    	
                    	adminIntent = new Intent(adminMedicaments.this, adminFarmacies.class);
                    	adminMedicaments.this.startActivity(adminIntent);
        		    	
                        break;
                    case R.id.popup_menu_medicaments:

                        break;
                    case R.id.popup_menu_entrades:
                    	Log.w("admin","GREEN");
                        break;
                    case R.id.popup_menu_sortides:
                    	Log.w("admin","GREEN");
                        break;
                }
                return true;
            }
        });
		
		
		boto_medicament_Listener = new View.OnClickListener() {


		    @Override
		    public void onClick(View v) {
				/*
				 	En pitjar sobre un botó de notícia, hem d'identificar
				 	el botó i iniciar el nou intent. al nou intent li
				 	passam el títol de la notícia.
				 */
		    	Intent intentMedicament = new Intent(adminMedicaments.this, visualitzacioMedicaments.class);
				Button pressed = (Button)v;
				Log.w("boto", pressed.getText().toString());
				String message = pressed.getText().toString();
				intentMedicament.putExtra(TITOL_MEDICAMENT, message);
				intentMedicament.putExtra(ID_MEDICAMENT, String.valueOf(pressed.getId()));
				adminMedicaments.this.startActivity(intentMedicament);
		    }
		};
		
		TreuMedicaments();
		
		
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
