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

public class adminSortides extends Activity {
	
	//Pel pas de missatges a altres intents
	
	public final static String MSG_ID_SORTIDES= "com.wsfarmacia_mbl.idsortides";
	public final static String MSG_DATAIHORA_SORTIDES = "com.wsfarmacia_mbl.dataihorasortides";
	public final static String MSG_MEDICAMENT_SORTIDES = "com.wsfarmacia_mbl.medicamentsortides";
	public final static String MSG_QUANTITAT_SORTIDES = "com.wsfarmacia_mbl.quantitatsortides";
	public final static String MSG_FARMACIA_SORTIDES = "com.wsfarmacia_mbl.quantitatfarmacia";
	
	//Listener pels botons de les sortides
	OnClickListener boto_sortides_Listener;
	private ConnexioServidor con;
	
	private void TreuSortides(){
		/*
		 * Funció que posa els botons de cada sortida a la pantalla i
		 * hi afegeix el listener boto_noticia_listener.
		 */
		try {
			con = new ConnexioServidor();
			con.consultaBBDD("salidas@@LTIM@@lista");
			
			//Exemple d'iteració entre elements
			
			for (int i=0; i<con.getNumEntrades();i++){
				for (int j=0; j<con.getNumElements(i); j++){
					Log.w("WSFARMACIA", con.treuElement(i, j));
				}
				Log.w("WSFARMACIA","----------");
			}
			
			
			for (int i=0; i<con.getNumEntrades();i++){
				//Pintam els botons rebuts
				Button sortida = new Button(this);
				sortida.setId(i);
				String medicament = new ConnexioServidor().getMedicamentFromId(con.treuElement(i, 1));
				
				sortida.setText(con.treuElement(i, 3)+" - "+medicament);
				
				sortida.setPadding(20, 20, 20, 20);
				sortida.setTextSize(15);
				sortida.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
				sortida.setOnClickListener(boto_sortides_Listener);
				LinearLayout ll = (LinearLayout)findViewById(R.id.admin_sortides_linearlayout);
				ll.addView(sortida);
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
		setContentView(R.layout.admin_sortides);
		
		//Preparam el menú popup
		final ImageButton btn = (ImageButton) findViewById(R.id.admin_sortides_popupMenuBtn);
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
                    	adminIntent = new Intent(adminSortides.this, adminNoticies.class);
                    	adminSortides.this.startActivity(adminIntent);
                    	break;
                    case R.id.popup_menu_farmacies:
        		    	
                    	adminIntent = new Intent(adminSortides.this, adminFarmacies.class);
                    	adminSortides.this.startActivity(adminIntent);
        		    	
                        break;
                    case R.id.popup_menu_medicaments:
                    	adminIntent = new Intent(adminSortides.this, adminMedicaments.class);
                    	adminSortides.this.startActivity(adminIntent);
                        break;
                    case R.id.popup_menu_entrades:
                    	adminIntent = new Intent(adminSortides.this, adminEntrades.class);
                    	adminSortides.this.startActivity(adminIntent);
                        break;
                    case R.id.popup_menu_sortides:
                        break;
                }
                return true;
            }
        });
		
		
		boto_sortides_Listener = new View.OnClickListener() {


		    @Override
		    public void onClick(View v) {
				/*
				 	En pitjar sobre un botó de notícia, hem d'identificar
				 	el botó i iniciar el nou intent. al nou intent li
				 	passam el títol de la notícia.
				 */
		    	Intent intentSortida = new Intent(adminSortides.this, visualitzacioSortida.class);
				Button pressed = (Button)v;
				Log.w("boto", pressed.getText().toString());

				String medicament= new ConnexioServidor().getMedicamentFromId(con.treuElement(pressed.getId(), 1));
				String farmacia= new ConnexioServidor().getFarmaciaFromId(con.treuElement(pressed.getId(), 4));

				
				intentSortida.putExtra(MSG_ID_SORTIDES, con.treuElement(pressed.getId(), 0));
				intentSortida.putExtra(MSG_DATAIHORA_SORTIDES, con.treuElement(pressed.getId(), 3));
				
				
				intentSortida.putExtra(MSG_MEDICAMENT_SORTIDES, medicament);
				intentSortida.putExtra(MSG_QUANTITAT_SORTIDES, con.treuElement(pressed.getId(), 2));
				
				intentSortida.putExtra(MSG_FARMACIA_SORTIDES, farmacia);

				
				
				adminSortides.this.startActivity(intentSortida);
		    }
		};
		
		TreuSortides();
		
		
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
