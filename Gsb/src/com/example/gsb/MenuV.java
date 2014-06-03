package com.example.gsb;

import java.util.ArrayList;
import android.os.Build;
import android.os.Bundle;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuV extends Activity implements View.OnTouchListener, View.OnClickListener {
TextView accueil;
private Button B1;
private Button B2;
private Button B3;
private Button B4;
private Button B5;
private Button B6;
private View mLoginFormView;
private ArrayList<String> listeMedicaments;
private ArrayList<String> listeDates;
private ArrayList<String> listePrat;
private String[] args;
private ArrayList<Medicament> listMed;
private ArrayList<Praticien> listPraticiens;
private String EXTRA_FLUX_MED = MainActivity.EXTRA_FLUX_MED;
private String EXTRA_FLUX_DATE = MainActivity.EXTRA_FLUX_DATE;
private String EXTRA_FLUX_PRAT = MainActivity.EXTRA_FLUX_PRAT;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout(getResources().getConfiguration().orientation);
        populate();
		String[] donnees = null;
		// On récupère l'intent qui a lancé cette activité
	    Intent i = getIntent();
	    donnees=i.getStringArrayExtra("com.example.android.authent.extra.donnees");
		/*donnees=new String[3];
		donnees[0]="";
	    donnees[2]="";*/		
	    this.args=donnees;
	    
	    accueil= (TextView) findViewById(R.id.textView);
	    accueil.setText("Bienvenue dans votre espace personel M./Mme "+donnees[0]+" "+donnees[2]+" !");
	  
	    B1= (Button) findViewById(R.id.button1);
	    B2= (Button) findViewById(R.id.button2);
	    B3= (Button) findViewById(R.id.button3);
	    B4= (Button) findViewById(R.id.button4);
	    B5= (Button) findViewById(R.id.button5);
	    B6= (Button) findViewById(R.id.button6);
	    B1.setOnClickListener(this);
	    B2.setOnClickListener(this);
	    B3.setOnClickListener(this);
	    B4.setOnClickListener(this);
	    B5.setOnClickListener(this);
	    B6.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_v, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent Redir;
		switch(v.getId()){
			case R.id.button1: //Création d'un nouveau rapport//
				Redir = new Intent(MenuV.this, NouvRapport.class);
				 this.finish();
				 Redir.putExtra(MainActivity.EXTRA_FLUX, args);
				 startActivity(Redir);
				break;
			case R.id.button2: //Consultation des praticiens//
				 Redir = new Intent(MenuV.this, ConsultPrat.class);
				 Redir.putExtra(MainActivity.EXTRA_FLUX, args);
				 PraticienDAO managePra = new PraticienDAO(getApplicationContext());
				 listePrat=new ArrayList<String>();
				 //récupérer tous les praticiens de la base //
				 listPraticiens=managePra.recupererTousLesPrat();
				 for(Praticien pra:listPraticiens){
					 listePrat.add(pra.getPraNom()+" "+pra.getPraPrenom());
				 }
				 Redir.putStringArrayListExtra(EXTRA_FLUX_PRAT, listePrat);
	        	 finish();		
	       		startActivity(Redir);
				break;
			case R.id.button3: // Consultation des rapports //
				 Redir = new Intent(MenuV.this, ConsultRapport.class);
				 Redir.putExtra(MainActivity.EXTRA_FLUX, args);
				 RapportDAO manage1 = new RapportDAO(getApplicationContext());
				 listeDates=new ArrayList<String>();
				 //récupérer dates des rapport dans la base //
				 listeDates=manage1.recupererDates(args[1]);
				 Redir.putStringArrayListExtra(EXTRA_FLUX_DATE, listeDates);
	        	 finish();		
	       		startActivity(Redir);
				break;
			case R.id.button4: //Se Déconnecter //
				 Redir = new Intent(MenuV.this, MainActivity.class);
				 this.finish();
				 startActivity(Redir);
				break;
			case R.id.button5: //Consultation des medicaments//
				Redir = new Intent(MenuV.this, ConsultMed.class);
				Redir.putExtra(MainActivity.EXTRA_FLUX, args);
				 MedicamentDAO manage = new MedicamentDAO(getApplicationContext());
				 listMed=new ArrayList<Medicament>();
				 listeMedicaments=new ArrayList<String>();
				 listMed=manage.recupererMedicaments(null, null);
				 for(Medicament medic:listMed){
					 listeMedicaments.add(medic.getDepotL());
				 }
				 Redir.putStringArrayListExtra(EXTRA_FLUX_MED, listeMedicaments);
				 this.finish();
				 startActivity(Redir);
				break;
			case R.id.button6: // Cas de la création des praticiens //
				Redir = new Intent(MenuV.this, NouvPrat.class);
				Redir.putExtra(MainActivity.EXTRA_FLUX, args);
				this.finish();
				startActivity(Redir);
				break;
			default:
				break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			/*mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});*/
			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			//mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	
	public void UpdateIHM() {
		// TODO Auto-generated method stub
		//Déposer le Runnable dans la file d'attente de l'UI thread
    	runOnUiThread(new Runnable() {
           @Override
           public void run() {
           		//code exécuté par l'UI thread
        	   showProgress(false);
        	   Intent Redir=new Intent(getApplicationContext(), ConsultMed.class);
        	   Redir.putExtra(MainActivity.EXTRA_FLUX, args);
        	   Redir.putStringArrayListExtra(EXTRA_FLUX_MED, listeMedicaments);
        	   finish();		
       			startActivity(Redir);
           }
        });
	}
	
	//_________________________________________GESTION DU CHANGEMENT DE CONFIGIRATION_______________________________________//	
		 private void setLayout(int orientation) {
		    	// Les layouts R.layout.layoutHorizontal et R.layout.layoutVertical ci-dessous 
		    	// correspondent aux fichiers layoutHorizontal.xml et layoutVertical.xml 
		    	// définis dans le répertoire res/layout de l'arborescence projet (cf étape 
		    	// précédente).
		 
		    	final int res = (orientation == Configuration.ORIENTATION_LANDSCAPE ? 
		    	    	    	     R.layout.activity_menu_v_horizontal : 
		    	    	    	     R.layout.activity_menu_v);
		 
		    	setContentView(res);
		    }
		 private void populate() {
		        // Attention ! 
		        // Vous devez impérativement retrouver votre TextView par findViewById() 
		        // sans le conserver dans une variable membre de votre activité : le fait
		        // d'avoir appelé setContentView() lors de l'étape précédente fait
		        // que le layout est rechargé, et le TextView recréé. 
		        // La référence conservée ne serait donc plus valide.
		 
			mLoginFormView=(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ?
					findViewById(R.id.GridLayout1):
						findViewById(R.id.LinearLayout1));
		 }
		 
		 @Override
		    public void onConfigurationChanged(Configuration newConfig) {        
		        super.onConfigurationChanged(newConfig);
		 
		        setLayout(newConfig.orientation);
		        populate();
		    }
		//_____________________________________________________________________________________________________________________________//

}
