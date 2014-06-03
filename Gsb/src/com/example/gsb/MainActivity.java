package com.example.gsb;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gsb.MenuV;
import com.example.gsb.R;

import android.os.Build;
import android.os.Bundle;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnTouchListener, View.OnClickListener {
    
	public static final String KEY_SUCCESS = "success";
	/*private static String KEY_MATRICULE = "matricule";
	private static String KEY_NAME = "name";
	private static String KEY_PRENOM = "prenom";
	private static String KEY_ROLE = "role";
	private static String KEY_ADRESSE = "adresse";
	private static String KEY_CP = "cp";
	private static String KEY_VILLE = "ville";
	private static String KEY_DATEEMB = "dateEmb";
	private static String KEY_SECCODE = "sec_code";
	private static String KEY_LABCODE = "lab_code";*/
	private View mLoginStatusView;
	private List<Medicament> listMed;
	private List<Praticien> listPra;
	private List<Rapport> listRap;
	private List<Offre> listOffre;
	private List<Visiteur> listEmploye;
	private final String TAG_MED="Medicaments";
	private final String TAG_PRAT="Praticiens";
	private final String TAG_RAPP="Rapports";
	private final String TAG_OFFRE="Offres";
	private final String TAG_EMPLOYE="Employe";
	public static final String EXTRA_FLUX_MED = "com.example.android.authent.extra.medicaments";
	public static final String EXTRA_FLUX = "com.example.android.authent.extra.donnees";
	public static final String EXTRA_FLUX_DATE = "com.example.android.authent.extra.dates";
	public static final String EXTRA_FLUX_VIS= "com.example.android.authent.extra.visiteurs";
	public static final String EXTRA_FLUX_EMPLOYE="com.example.android.authent.extra.employes";
	public static final String EXTRA_FLUX_PRAT="com.example.android.authent.extra.prticiens";
	private View mLoginFormView;
	private final String KEY_VISITEUR="Visiteur";
	EditText text1;
	EditText text2;
	Spinner spinner;
	Button clear;
	Button go;
	String envoi="";
	private Context context;
	//private Object data;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout(getResources().getConfiguration().orientation);
        setTitle("Formulaire d'Identification");

		populate();
        context=this;
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.Regions, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		/*mLoginStatusView=findViewById(R.id.status);
		mLoginFormView=findViewById(R.id.form);*/
		
		clear=(Button) findViewById(R.id.button1);
		go=(Button) findViewById(R.id.button2);
		clear.setOnClickListener(this);
		go.setOnClickListener(this);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	//___________________________ACTION DES BOUTTONS______________________________//
			//________________________________________________________________//
	@Override
	public void onClick(View v) {
		final String nom=text1.getText().toString();
		final String mat=text2.getText().toString();
		final String reg=spinner.getSelectedItem().toString();
		switch(v.getId()){
		//Cas du boutton effacer//
			case R.id.button1:
				text1.setText("");
				text2.setText("");
				spinner.setSelection(0);
				break;
			//Cas du boutton Se connecter//
			case R.id.button2:
				if(nom.equals("")|| mat.equals("") || reg.equals("Choisir une région :")){
					Toast.makeText(this, "Veuillez renseigner tous les champs!", Toast.LENGTH_SHORT).show();
				}
				else{
					showProgress(true);
					 new Thread(new Runnable(){						 	
							@Override
							public void run() {
								// TODO Auto-generated method stub
								String[] regcode=reg.split("-");
								// Instructions consommatrices en temps
				    			ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
				    	        nameValuePairs.add(new BasicNameValuePair("tag", "login"));
				    	        nameValuePairs.add(new BasicNameValuePair("nom", nom));
				    	        nameValuePairs.add(new BasicNameValuePair("password", mat));
				    	        nameValuePairs.add(new BasicNameValuePair("reg", regcode[1]));
				    	        Log.v("Champs", "nom :"+nom+" pwd :"+mat+" reg :"+reg);
				    	       
				    	        	//********************************************//
				    	            //* Exécute la requête vers le serveur local *//
				    	            //********************************************//
				    	        	
				    	     
				    	            
				    	          //***************************//
				    	            //* Résultats de la requête *//
				    	            //***************************//
				    	        
				    	        	ConnexionHTTP ident= new ConnexionHTTP(nameValuePairs);
				    	        	StringBuilder sb=ident.getResponse();
				    	            if(sb!=null){
				    	            	JSONObject jObj = null;
				    	            	String result = sb.toString();
				    	            	Log.v("RESULTAT REQUETE HTTP", sb.toString());
										try {
											jObj = new JSONObject(result);
										} catch (JSONException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										} 
					    	            Log.v("RESULTAT REQUETE", result);
					    	            //***********************************************//
					    	            //* Si le résultat de la requête n'est pas nul *//
					    	            //**********************************************//
					    	            try {
											if (jObj.getString(KEY_SUCCESS) != null) 
											{
											  String res = jObj.getString(KEY_SUCCESS); 
											  Log.v("RETOUR DES DONNEES", res);
											  //********************************************//
											  //* Si il vaut 1, l'utilisateur est connecté *//
											  //********************************************//
   
											  if(Integer.parseInt(res) == 1)
											  {
											    //Mise a jour de la base SQLITE//
												  
											    	JSONArray med;
											    	JSONArray tabmeds;
											    	JSONArray prat;
											    	JSONArray tabprats;
											    	JSONArray rap;
											    	JSONArray tabrapps;
											    	JSONArray taboffres;
											    	JSONArray offre;
											    	JSONArray tabvisiteurs;
											    	JSONArray visiteur;
											    try{ 
											        listMed=new ArrayList<Medicament>();
											        tabmeds=jObj.getJSONArray(TAG_MED);
											        for(int i=0; i<tabmeds.length(); i++){ 
											        	med=tabmeds.getJSONArray(i);
											        	Medicament medicam=new Medicament(med.getString(0), med.getString(1), med.getString(2), med.getString(3), med.getString(4), med.getString(5), med.getString(6));
											    		 listMed.add(medicam);
											    		 //Log.v("RESULT", "taille du tableau "+i+":"+tab.getJSONArray(i).length());
											    	 }
											        Log.v("RESULT", "taille du tableau med:"+listMed.size());
											        MedicamentDAO basemanage=new MedicamentDAO(getApplicationContext());
											        basemanage.viderLaTable();
											        for(Medicament medicament:listMed){
											        	basemanage.ajouterMedicament(medicament);
											        }
											        
											        listPra=new ArrayList<Praticien>();
											        tabprats=jObj.getJSONArray(TAG_PRAT);
											        for(int i=0; i<tabprats.length(); i++){ 
											        	prat=tabprats.getJSONArray(i);
											        	Praticien pra=new Praticien(prat.getInt(0), prat.getString(1), prat.getString(2), prat.getString(3), prat.getLong(4), prat.getString(5), prat.getDouble(6), prat.getString(7));
											        	listPra.add(pra);
											    		 //Log.v("RESULT", "taille du tableau "+i+":"+tab.getJSONArray(i).length());
											    	 }
											        Log.v("RESULT", "taille du tableau med:"+listMed.size());
											        PraticienDAO basemanage2=new PraticienDAO(getApplicationContext());
											        basemanage2.viderLaTable();
											        for(Praticien praticien:listPra){
											        	basemanage2.ajouterPraticien(praticien);
											        }
											        JSONArray ind1 = (JSONArray) jObj.getJSONArray(TAG_RAPP).get(0);
											        String rapp1=String.valueOf(ind1.get(0));
											        if(!rapp1.equals("1")){
											        	listRap=new ArrayList<Rapport>();
												        tabrapps=jObj.getJSONArray(TAG_RAPP);
												        for(int i=0; i<tabrapps.length(); i++){ 
												        	rap=tabrapps.getJSONArray(i);
												        	Rapport rapport=new Rapport(rap.getString(0), rap.getInt(1), rap.getInt(2), rap.getString(3), rap.getString(4), rap.getString(5));
												        	listRap.add(rapport);
												    		 //Log.v("RESULT", "taille du tableau "+i+":"+tab.getJSONArray(i).length());
												    	}
												        RapportDAO basemanage3=new RapportDAO(getApplicationContext());
												        basemanage3.viderLaTable();
												        for(Rapport rappo:listRap){
												        	basemanage3.ajouterRapport(rappo);
												        }
											        }
											        ind1=(JSONArray) jObj.getJSONArray(TAG_OFFRE).get(0);
											        String off1=String.valueOf(ind1.get(0));
											        if(!off1.equals("1")){
											        	listOffre=new ArrayList<Offre>();
											        	taboffres=jObj.getJSONArray(TAG_OFFRE);
											        	 for(int i=0; i<taboffres.length(); i++){
											        		 offre=taboffres.getJSONArray(i);
											        		 Offre off=new Offre(offre.getString(0), offre.getInt(1), offre.getString(2), offre.getInt(3));
													        	listOffre.add(off);
											        	 }
											        	 OffreDAO basemanage4=new OffreDAO(getApplicationContext());
											        	 basemanage4.viderLaTable();
													     for(Offre offr:listOffre){
													        	basemanage4.ajoutOffre(offr);
													     }
											        }
											        ind1=(JSONArray) jObj.getJSONArray(TAG_EMPLOYE).get(0);
											        String emp1=String.valueOf(ind1.get(0));
											        if(!emp1.equals("1")){
											        	listEmploye=new ArrayList<Visiteur>();
											        	tabvisiteurs=jObj.getJSONArray(TAG_EMPLOYE);
											        	for(int i=0; i<tabvisiteurs.length(); i++){
											        		//Log.v("nombre employes :", String.valueOf(tabvisiteurs.length()));
											        		visiteur=tabvisiteurs.getJSONArray(i);
											        		Visiteur employe=new Visiteur(visiteur.getString(0), visiteur.getString(1), visiteur.getString(2), visiteur.getString(3), visiteur.getLong(4), visiteur.getString(5), visiteur.getString(6), visiteur.getString(7), visiteur.getString(8));
											        		listEmploye.add(employe);
											        	}
											        	VisiteurDAO basemanage5=new VisiteurDAO(getApplicationContext());
											        	basemanage5.viderLaTable();
											        	for(Visiteur vis:listEmploye){
											        		basemanage5.ajouterVisiteur(vis);
											        	}
											        }
												  //***************************************//
											      //* Lancement de l'Activity "DashBoard" *//
											      //***************************************//
												  Intent dashboard=null;
												  String role=jObj.getJSONObject(KEY_VISITEUR).getString("role");
												  if((role.equals("Délégué")) || (role.equals("Responsable"))){
													  dashboard = new Intent(getApplicationContext(), MenuDir.class);
												  }
												  else{
													  dashboard = new Intent(getApplicationContext(), MenuV.class);
												  }
												  dashboard.putExtra(EXTRA_FLUX, new String[]{jObj.getJSONObject(KEY_VISITEUR).getString("name"), jObj.getJSONObject(KEY_VISITEUR).getString("matricule"), jObj.getJSONObject(KEY_VISITEUR).getString("prenom"), jObj.getJSONObject(KEY_VISITEUR).getString("role"), jObj.getJSONObject(KEY_VISITEUR).getString("adresse"), jObj.getJSONObject(KEY_VISITEUR).getString("cp"), jObj.getJSONObject(KEY_VISITEUR).getString("ville"), jObj.getJSONObject(KEY_VISITEUR).getString("dateEmb"), jObj.getJSONObject(KEY_VISITEUR).getString("sec_code"), jObj.getJSONObject(KEY_VISITEUR).getString("lab_code")});				    	            		  												  
											      startActivity(dashboard);
												  //****************************//
											      //* Ferme l'Activity "Login" *//
											      //****************************//
   
											      finish();

											    }
											    catch(JSONException e){
											        	Log.v("Exeption", e.toString());
											        	e.printStackTrace();
											        } 
											  }
											  else{
												  //*************************************//
											      //* Si il vaut 0, erreur de connexion *//
											      //*************************************//
												  //Appeler d'une méthode (pour améliorer la lisibilité du code)
											  		UpdateIHM(2);
											  		Thread.currentThread().interrupt(); // Très important de réinterrompre
											  }
											}
										} catch (NumberFormatException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
				    	            }
				    	            else{ //Erreur de réseau//
				    	            	// Log.v("CONNEXION", null); 
				    	            	UpdateIHM(1);
				    	            	Thread.currentThread().interrupt();
				    	            }
						}
				    	    
					 }).start();	
				}
				break;
			default:
				break;
		}
				
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.button1:
				
				
				break;
			case R.id.button2:

				break;
			default:
				break;
		}
		return true;
	}
	//________________________________________________________________________________________________//
//________________________________________________________________________________________________________________//
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
					android.R.integer.config_mediumAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

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
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	
	public void UpdateIHM(final int type) {
		// TODO Auto-generated method stub
		//Déposer le Runnable dans la file d'attente de l'UI thread
    	runOnUiThread(new Runnable() {
           @Override
           public void run() {
           		//code exécuté par l'UI thread
        	   showProgress(false);
        	   AlertDialog.Builder box = new AlertDialog.Builder(context);			
       			final LinearLayout lay=(LinearLayout) findViewById(R.id.Dialog);
        	   switch(type){
        	   case 1:
        		   	box.setTitle("Information !");
   					box.setMessage("Connexion impossible, vérifiez que vous êtes bien connecté(e) à un réseau Internet !");
   					box.setNeutralButton("Ok", new DialogInterface.OnClickListener(){

        				@Override
        				public void onClick(DialogInterface dialog, int which) {
        					// TODO Auto-generated method stub
        					dialog.cancel();
        				}
        				
        			});
        			box.setView(lay);
        			box.show();
   					break;
        	   case 2:
        		   Toast.makeText(getApplicationContext(), "Matricule, Nom ou Region incorrecte !", Toast.LENGTH_LONG).show();
        		   break;
        		  default:
        			  break;
        	   }
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
	    	    	    	     R.layout.activity_main_horyzontal : 
	    	    	    	     R.layout.activity_main);
	 
	    	setContentView(res);
	    }
	 private void populate() {
	        // Attention ! 
	        // Vous devez impérativement retrouver votre TextView par findViewById() 
	        // sans le conserver dans une variable membre de votre activité : le fait
	        // d'avoir appelé setContentView() lors de l'étape précédente fait
	        // que le layout est rechargé, et le TextView recréé. 
	        // La référence conservée ne serait donc plus valide.
	 
		text1=(EditText) findViewById(R.id.editText);
		text2=(EditText) findViewById(R.id.editText2);
		spinner = (Spinner) findViewById(R.id.spinner1);
		mLoginFormView=findViewById(R.id.form);
		mLoginStatusView=findViewById(R.id.status);
	 }
	 
	 @Override
	    public void onConfigurationChanged(Configuration newConfig) {        
	        super.onConfigurationChanged(newConfig);
	        setLayout(newConfig.orientation);
	        populate();
	    }
	//_____________________________________________________________________________________________________________________________//
	 
}
