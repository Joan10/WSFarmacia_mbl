package com.wsfarmacia_mbl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

public class adminEntrades extends Activity {
	
	//Pel pas de missatges a altres intents
	
	public final static String MSG_ID_ENTRADES= "com.wsfarmacia_mbl.identrades";
	public final static String MSG_DATAIHORA_ENTRADES = "com.wsfarmacia_mbl.dataihoraentrades";
	public final static String MSG_MEDICAMENT_ENTRADES = "com.wsfarmacia_mbl.medicamententrades";
	public final static String MSG_QUANTITAT_ENTRADES = "com.wsfarmacia_mbl.quantitatentrades";
	
	//Listener pels botons de notícia
	OnClickListener boto_entrades_Listener;

	
	private void TreuEntrades(){
		/*
		 * Funció que posa els botons de cada entrada a la pantalla i
		 * hi afegeix el listener boto_noticia_listener.
		 */
		try {
			ConnexioServidor con = new ConnexioServidor();
			con.consultaBBDD("medicamentos@@LTIM@@lista");
			
			//Exemple d'iteració entre elements
			
			for (int i=0; i<con.getNumEntrades();i++){
				for (int j=0; j<con.getNumElements(i); j++){
					Log.w("WSFARMACIA", con.treuElement(i, j));
				}
				Log.w("WSFARMACIA","----------");
			}
			
			
			for (int i=0; i<con.getNumEntrades();i++){
				//Pintam els botons rebuts
				Button entrada = new Button(this);
				entrada.setText(i+": "+con.treuElement(i, 1));
				entrada.setPadding(20, 20, 20, 20);
				entrada.setTextSize(30);
				entrada.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
				LinearLayout ll = (LinearLayout)findViewById(R.id.admin_entrades_linearlayout);
				ll.addView(entrada);
			}
		}catch (Exception e){
			//Error fent la connexió, mostram un missatge.
			Log.e("ERROR", "Impossible realitzar la connexió");

			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setMessage(R.string.errorconnexio_desc)
				   .setTitle(R.string.errorconnexio_titol);

			AlertDialog dialog = builder.create();
			dialog.show();
		}
			
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_entrades);
		
		//Preparam el menú popup
		final ImageButton btn = (ImageButton) findViewById(R.id.admin_entrades_popupMenuBtn);
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
                    	adminIntent = new Intent(adminEntrades.this, adminNoticies.class);
                    	adminEntrades.this.startActivity(adminIntent);
                    	break;
                    case R.id.popup_menu_farmacies:
        		    	
                    	adminIntent = new Intent(adminEntrades.this, adminFarmacies.class);
                    	adminEntrades.this.startActivity(adminIntent);
        		    	
                        break;
                    case R.id.popup_menu_medicaments:
                    	adminIntent = new Intent(adminEntrades.this, adminMedicaments.class);
                    	adminEntrades.this.startActivity(adminIntent);
                        break;
                    case R.id.popup_menu_entrades:
                        break;
                    case R.id.popup_menu_sortides:
                    	adminIntent = new Intent(adminEntrades.this, adminSortides.class);
                    	adminEntrades.this.startActivity(adminIntent);
                        break;
                }
                return true;
            }
        });
		
		
		boto_entrades_Listener = new View.OnClickListener() {


		    @Override
		    public void onClick(View v) {
				/*
				 	En pitjar sobre un botó de notícia, hem d'identificar
				 	el botó i iniciar el nou intent. al nou intent li
				 	passam el títol de la notícia.
				 */
		    	Intent intentEntrada = new Intent(adminEntrades.this, visualitzacioEntrada.class);
				Button pressed = (Button)v;
				Log.w("boto", pressed.getText().toString());
				String message = pressed.getText().toString();
				intentEntrada.putExtra(MSG_ID_ENTRADES, String.valueOf(pressed.getId()));
				adminEntrades.this.startActivity(intentEntrada);
		    }
		};
		
		TreuEntrades();
		
		
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
