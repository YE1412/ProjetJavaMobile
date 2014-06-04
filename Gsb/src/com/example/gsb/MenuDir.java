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

public class MenuDir extends Activity implements View.OnTouchListener, View.OnClickListener {
	
	private ArrayList<String> listeVis;
	private ArrayList<String> listePraticiens;
	private String[] SessionVis;
	/*private VisiteurDAO basemanage;
	private Visiteur vis;*/
	private Button btn1;
	private Button btn2;
	private Button btn4;
	private Button btn5;
	private Button btn6;
	private Button btn7;
	private Button btn8;
	private TextView accueil;
	private View mLoginFormView;
	private ArrayList<String> listeMedicaments;
	private ArrayList<Medicament> listMed;
	private ArrayList<Praticien> listPrat;
	private String EXTRA_FLUX_VIS = MainActivity.EXTRA_FLUX_VIS;
	private String EXTRA_FLUX_MED = MainActivity.EXTRA_FLUX_MED;
	private String EXTRA_FLUX_PRAT = MainActivity.EXTRA_FLUX_PRAT;
	private String EXTRA_FLUX_EMPLOYE=MainActivity.EXTRA_FLUX_EMPLOYE;
	private String EXTRA_FLUX_DATES=MainActivity.EXTRA_FLUX_DATE;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout(getResources().getConfiguration().orientation);
		
		// On récupère l'intent qui a lancé cette activité
	    Intent i = getIntent();
	    String[] donnees=i.getStringArrayExtra("com.example.android.authent.extra.donnees");
	    SessionVis=donnees;
	    /*String nom=donnees[0], mat=donnees[1], prenom=donnees[2], adresse=donnees[4], ville=donnees[6], dateEmb=donnees[7], seccode=donnees[8], labcode=donnees[9];
	    Long cp=Long.valueOf(donnees[5]);*/
	    
	    accueil= (TextView) findViewById(R.id.textView1);
	    accueil.setText("Bienvenue dans votre espace personel M./Mme "+donnees[0]+" "+donnees[2]+" !");
	   //Définition des bouttons// 
	    this.btn1 = (Button) findViewById(R.id.Button02);
	    btn1.setOnClickListener(this);
	    this.btn2 = (Button) findViewById(R.id.Button01);
	    btn2.setOnClickListener(this);
	    this.btn4 = (Button) findViewById(R.id.Button03);
	    btn4.setOnClickListener(this);
	    this.btn5 = (Button) findViewById(R.id.Button04);
	    btn5.setOnClickListener(this);
	    this.btn6 = (Button) findViewById(R.id.Button06);
	    btn6.setOnClickListener(this);
	    this.btn7 = (Button) findViewById(R.id.Button05);
	    btn7.setOnClickListener(this);
	    this.btn8 = (Button) findViewById(R.id.button2);
	    btn8.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_dir, menu);
		return true;
	}
	//___________________________ACTION DES BOUTTONS______________________________//
			//_________________________________________________________________//
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent dashboard=null;
		switch(v.getId()){
		case R.id.Button02: //Nouveau Rapport//
			dashboard = new Intent(getApplicationContext(), NouvRapport.class);
  		  	dashboard.putExtra(MainActivity.EXTRA_FLUX, SessionVis);
  		  	startActivity(dashboard);
  		  	finish();
			break;
		case R.id.Button01: //Consulter Rapports//
			dashboard = new Intent(getApplicationContext(), ConsultRapport.class);
  		  	dashboard.putExtra(MainActivity.EXTRA_FLUX, SessionVis);
  		  	RapportDAO manage1 = new RapportDAO(getApplicationContext());
			listeVis=new ArrayList<String>();
			ArrayList<String> listeDates=new ArrayList<String>();
			//récupérer visiteur ayant écrit des rapport dans la base //
			if(SessionVis[3]!="Responsable"){
				listeVis=manage1.recupererVisiteurs(SessionVis[9], SessionVis[8], SessionVis[1]);
			}
			else{
				listeVis=manage1.recupererVisiteurs(SessionVis[9], null, SessionVis[1]);
			}
			listeDates=manage1.recupererDates(SessionVis[1]);
			dashboard.putStringArrayListExtra(EXTRA_FLUX_VIS, listeVis);
			dashboard.putStringArrayListExtra(EXTRA_FLUX_DATES, listeDates);
  		  	startActivity(dashboard);
  		  	finish();
			break;
		case R.id.Button03: //Cas de consultation des médicaments//
			dashboard = new Intent(getApplicationContext(), ConsultMed.class);
	  		dashboard.putExtra(MainActivity.EXTRA_FLUX, SessionVis);
	  		MedicamentDAO manage = new MedicamentDAO(getApplicationContext());
			 listMed=new ArrayList<Medicament>();
			 listeMedicaments=new ArrayList<String>();
			 listMed=manage.recupererMedicaments(null, null);
			 for(Medicament medic:listMed){
				 listeMedicaments.add(medic.getDepotL());
			 }
			dashboard.putStringArrayListExtra(EXTRA_FLUX_MED, listeMedicaments);
			finish();
			startActivity(dashboard);
			break;
		case R.id.Button06:	//Consultation des praticiens//
			dashboard = new Intent(getApplicationContext(), ConsultPrat.class);
  		  	PraticienDAO managePra = new PraticienDAO(getApplicationContext());
			listPrat=new ArrayList<Praticien>();
			listePraticiens=new ArrayList<String>();
			//listMed=manage.recupererMedicaments(null, null);
			listPrat=managePra.recupererTousLesPrat();
			for(Praticien praticien:listPrat){
				listePraticiens.add(praticien.getPraNom()+" "+praticien.getPraPrenom());
			}
			dashboard.putExtra(MainActivity.EXTRA_FLUX, SessionVis);
			dashboard.putStringArrayListExtra(EXTRA_FLUX_PRAT, listePraticiens);
			startActivity(dashboard); 
			finish();	
			break;
		case R.id.Button05: //Autres Visiteurs//
			dashboard = new Intent(getApplicationContext(), ConsultVis.class);
  		  	dashboard.putExtra(MainActivity.EXTRA_FLUX, SessionVis);  		  	
  		  	//Récupération des visiteurs //
  		  	VisiteurDAO manage2=new VisiteurDAO(getApplicationContext());
  		  	listeVis=new ArrayList<String>();
  		 	listeVis=manage2.selectionnerTousLesVis(SessionVis[1]);
  		 	dashboard.putStringArrayListExtra(EXTRA_FLUX_EMPLOYE, listeVis);
  		 	startActivity(dashboard);
  		 	finish();
			break;
		case R.id.button2: //Se deconnecter//
			dashboard = new Intent(getApplicationContext(),MainActivity.class);
			startActivity(dashboard);
			finish();
			break;
		case R.id.Button04: // Cas de la création de nouveau praticien //
			dashboard = new Intent(getApplicationContext(), NouvPrat.class);
			dashboard.putExtra(MainActivity.EXTRA_FLUX, SessionVis);
			startActivity(dashboard);
			finish();
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
	//_________________________________________________________________________//
//_________________________________________________________________________________//
//_________________________________________GESTION DU CHANGEMENT DE CONFIGIRATION_______________________________________//
	private void setLayout(int orientation) {
	    	// Les layouts R.layout.layoutHorizontal et R.layout.layoutVertical ci-dessous 
	    	// correspondent aux fichiers layoutHorizontal.xml et layoutVertical.xml 
	    	// définis dans le répertoire res/layout de l'arborescence projet (cf étape 
	    	// précédente).
	 
	    	final int res = (orientation == Configuration.ORIENTATION_LANDSCAPE ? 
	    	    	    	     R.layout.activity_menu_dir_horizontal : 
	    	    	    	     R.layout.activity_menu_dir);
	    	
	    	setContentView(res);
	    	mLoginFormView=(View) findViewById(R.id.RelativeLayout1);
	    }
	 
	 @Override
	    public void onConfigurationChanged(Configuration newConfig) {        
	        super.onConfigurationChanged(newConfig);
	 
	        setLayout(newConfig.orientation);
	    }
//_____________________________________________________________________________________________________________________________//
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
	        	   Redir.putExtra(MainActivity.EXTRA_FLUX, SessionVis);
	        	   Redir.putStringArrayListExtra(EXTRA_FLUX_MED, listeMedicaments);
	        	   finish();		
	       			startActivity(Redir);
	           }
	        });
		}
 }
