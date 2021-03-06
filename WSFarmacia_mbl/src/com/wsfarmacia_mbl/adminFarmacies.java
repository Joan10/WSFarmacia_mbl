package com.wsfarmacia_mbl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

public class adminFarmacies extends Activity {
	

	
	private void TreuFarmacies(){
		/*
		 * Funció que posa els botons de cada notícia a la pantalla i
		 * hi afegeix el listener boto_noticia_listener.
		 */
		try {
			ConnexioServidor con = new ConnexioServidor();
			con.consultaBBDD("farmacias@@LTIM@@lista");
			
			//Exemple d'iteració entre farmàcies
			
			for (int i=0; i<con.getNumEntrades();i++){
				for (int j=0; j<con.getNumElements(i); j++){
					Log.w("WSFARMACIA", con.treuElement(i, j));
				}
				Log.w("WSFARMACIA","----------");
			}
			
			
			for (int i=0; i<con.getNumEntrades();i++){
				TextView farmacia = new TextView(this);
				farmacia.setText(i+": "+con.treuElement(i, 1));
				farmacia.setPadding(20, 20, 20, 20);
				farmacia.setTextSize(15);
				farmacia.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
				LinearLayout ll = (LinearLayout)findViewById(R.id.admin_farmacies_linearlayout);
				ll.addView(farmacia);
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
		setContentView(R.layout.admin_farmacies);
		
		final ImageButton btn = (ImageButton) findViewById(R.id.admin_farmacies_popupMenuBtn);
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
            	//Menú principal. "Popup" que apareix en pitjar al boto superior dret
            	//per navegar entre pantalles
            	Intent adminIntent;
                switch (item.getItemId()) {
                    case R.id.popup_menu_noticies:
        		    	
                    	adminIntent = new Intent(adminFarmacies.this, adminNoticies.class);
                    	adminFarmacies.this.startActivity(adminIntent);
                        break;
                    case R.id.popup_menu_farmacies:
                       // Log.w("admin","BLUE");
                        break;
                    case R.id.popup_menu_medicaments:
                    	adminIntent = new Intent(adminFarmacies.this, adminMedicaments.class);
                    	adminFarmacies.this.startActivity(adminIntent);
                        break;
                    case R.id.popup_menu_entrades:
                    	adminIntent = new Intent(adminFarmacies.this, adminEntrades.class);
                    	adminFarmacies.this.startActivity(adminIntent);
                        break;
                    case R.id.popup_menu_sortides:
                    	adminIntent = new Intent(adminFarmacies.this, adminSortides.class);
                    	adminFarmacies.this.startActivity(adminIntent);
                        break;
                }
                return true;
            }
        });
		
		
		TreuFarmacies();

		
		
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
