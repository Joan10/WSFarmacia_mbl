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

public class adminNoticies extends Activity {
	
	//Pel pas de missatges a altres intents
	public final static String TITOL_NOTICIA = "com.wsfarmacia_mbl.titolnoticia";
	public final static String ID_NOTICIA = "com.wsfarmacia_mbl.idnoticia";
	
	//Listener pels botons de notícia
	OnClickListener boto_noticia_Listener;
	
	private void TreuNoticies(){
		/*
		 * Funció que posa els botons de cada notícia a la pantalla i
		 * hi afegeix el listener boto_noticia_listener.
		 */
		try {
			ConnexioServidor con = new ConnexioServidor();
			con.consultaBBDD("noticias@@LTIM@@lista");
			
			//Exemple d'iteració entre farmàcies
			
			for (int i=0; i<con.getNumEntrades();i++){
				for (int j=0; j<con.getNumElements(i); j++){
					Log.w("WSFARMACIA", con.treuElement(i, j));
				}
				Log.w("WSFARMACIA","----------");
			}
			
			
			for (int i=0; i<con.getNumEntrades();i++){
				TextView noticia = new TextView(this);
				noticia.setText(i+": "+con.treuElement(i, 1));
				noticia.setPadding(20, 20, 20, 20);
				noticia.setTextSize(15);
				noticia.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
				LinearLayout ll = (LinearLayout)findViewById(R.id.admin_noticies_linearlayout);
				ll.addView(noticia);
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
		setContentView(R.layout.admin_noticies);
		
		//Preparam el menú popup
		final ImageButton btn = (ImageButton) findViewById(R.id.admin_noticies_popupMenuBtn);
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
            	//Menú principal. "Popup" que apareix en pitjar al boto superior dret
            	//per navegar entre pantalles
            	Intent adminIntent;
                switch (item.getItemId()) {
                    case R.id.popup_menu_noticies:
                    	//setContentView(R.layout.admin_noticies);
                        break;
                    case R.id.popup_menu_farmacies:
        		    	
                    	adminIntent = new Intent(adminNoticies.this, adminFarmacies.class);
                    	adminNoticies.this.startActivity(adminIntent);
        		    	
                        break;
                    case R.id.popup_menu_medicaments:
                    	adminIntent = new Intent(adminNoticies.this, adminMedicaments.class);
                    	adminNoticies.this.startActivity(adminIntent);
                        break;
                    case R.id.popup_menu_entrades:
                    	adminIntent = new Intent(adminNoticies.this, adminEntrades.class);
                    	adminNoticies.this.startActivity(adminIntent);
                        break;
                    case R.id.popup_menu_sortides:
                    	adminIntent = new Intent(adminNoticies.this, adminSortides.class);
                    	adminNoticies.this.startActivity(adminIntent);
                        break;
                }
                return true;
            }
        });
		
		
		boto_noticia_Listener = new View.OnClickListener() {


		    @Override
		    public void onClick(View v) {
				/*
				 	En pitjar sobre un botó de notícia, hem d'identificar
				 	el botó i iniciar el nou intent. al nou intent li
				 	passam el títol de la notícia.
				 */
		    	Intent intentNoticia = new Intent(adminNoticies.this, visualitzacioNoticia.class);
				Button pressed = (Button)v;
				Log.w("boto", pressed.getText().toString());
				String message = pressed.getText().toString();
				intentNoticia.putExtra(TITOL_NOTICIA, message);
				intentNoticia.putExtra(ID_NOTICIA, String.valueOf(pressed.getId()));
				adminNoticies.this.startActivity(intentNoticia);
		    }
		};
		
		TreuNoticies();
		
		
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
