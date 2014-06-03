package com.example.gsb;

import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class NouvPrat extends Activity implements View.OnTouchListener, View.OnClickListener{
	private View mLoginStatusView;
	private View mLoginFormView;
	private String[] visiteur;
	private Praticien prat;
	private Button bouttonRetour;
	private Button bouttonEffacer;
	private Button bouttonEnvoi;
	private Spinner spinnTypePra;
	private Context activ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nouv_pra);
		setTitle("Création d'un nouveau Praticien");
		
		activ=this;
		mLoginStatusView=findViewById(R.id.status);
		mLoginFormView=findViewById(R.id.scrollView1);
		
		//Récupération de l'Intent qui à lancé l'activité//
		Intent intent = getIntent();
		this.visiteur=intent.getStringArrayExtra(MainActivity.EXTRA_FLUX);
				
		//Gestion du Spinner//
		spinnTypePra=(Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
		adapter.add("Type de Praticien :");
		adapter.add("-MH- Médecin Hospitalier");
		adapter.add("-MV- Médecine de Ville");
		adapter.add("-PH- Pharmacien Hospitalier");
		adapter.add("-PO- Pharmacien Officine");
		adapter.add("-PS- Personnel de Santé");		
		spinnTypePra.setAdapter(adapter);
		
		//Gestion des boutton//
		bouttonRetour=(Button) findViewById(R.id.button1);
		ActionBouttonRetour action= new ActionBouttonRetour(bouttonRetour, visiteur, getApplicationContext());
		action.retour();
		bouttonEffacer=(Button) findViewById(R.id.button2);
		bouttonEffacer.setOnClickListener(this);
		bouttonEnvoi=(Button) findViewById(R.id.button3);
		bouttonEnvoi.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nouv_prat, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		EditText nom, prenom, adresse, ville, cp, coeff;
		nom=(EditText) findViewById(R.id.editText1);
		prenom=(EditText) findViewById(R.id.editText2);
		adresse=(EditText) findViewById(R.id.editText3);
		ville=(EditText) findViewById(R.id.editText5);
		cp=(EditText) findViewById(R.id.editText4);
		coeff=(EditText) findViewById(R.id.editText6);
		switch(arg0.getId())
		{
			case R.id.button2: //Cas du boutton Effacer //
				RAZ(nom, prenom, adresse, ville, cp, coeff, spinnTypePra);
				break;
			case R.id.button3: // Cas du boutton Envoyer //
				final String[] ChampsForm=new String[6];
				String[] resultat;
				String txtNom, txtPrenom, txtAdresse, txtVille, txtCp, txtCoeff;
				txtNom=nom.getText().toString();
				txtPrenom=prenom.getText().toString();
				txtAdresse=adresse.getText().toString();
				txtVille=ville.getText().toString();
				txtCp=cp.getText().toString();
				txtCoeff=coeff.getText().toString();
				
				for(int j=0; j<ChampsForm.length; j++)
				{
					switch(j)
					{
						case 0:
							ChampsForm[j]=txtNom;
							break;
						case 1:
							ChampsForm[j]=txtPrenom;
							break;
						case 2:
							ChampsForm[j]=txtAdresse;
							break;
						case 3:
							ChampsForm[j]=txtVille.toUpperCase(Locale.getDefault());
							break;
						case 4:
							ChampsForm[j]=txtCp;
							break;
						case 5:
							ChampsForm[j]=txtCoeff;
							break;
						default:
							break;
					}
				}
				resultat=verifFormulaire(ChampsForm);
				int err = 0;
				for(int i=0; i<resultat.length; i++)
				{
					if(resultat[i].equals("false"))
					{
						err++;
					}
				}
				AlertDialog.Builder box = new AlertDialog.Builder(this);
				final LinearLayout lay=(LinearLayout) findViewById(R.id.Dialog);
				if(err>0){
					//Log.v("STOP", "Champs nom remplis :"+err);				
					box.setTitle("Erreur !");
					box.setMessage(err+" Champs non valides ou incomplets !");
					box.setNeutralButton("Ok", new DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
						
					});
					box.setView(lay);
					box.show();
				}
				else{
					final String[] typeMEF=spinnTypePra.getSelectedItem().toString().split("-");
					// Praticien //
					PraticienDAO manageprat=new PraticienDAO(getApplicationContext());
					int numPra=manageprat.getNbPraticien()+1;
					this.prat=new Praticien(numPra, ChampsForm[0], ChampsForm[1], ChampsForm[2], Long.valueOf(ChampsForm[4]), ChampsForm[3], Double.valueOf(ChampsForm[5]), typeMEF[1]);
					
					// Base SQLite //
					manageprat.ajouterPraticien(prat);
					
					// Base Distante //
					showProgress(true);
					new Thread(new Runnable (){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
							nameValuePairs.add(new BasicNameValuePair("tag","insert"));
							nameValuePairs.add(new BasicNameValuePair("type","praticien"));
							nameValuePairs.add(new BasicNameValuePair("nom", ChampsForm[0]));
							nameValuePairs.add(new BasicNameValuePair("prenom", ChampsForm[1]));
							nameValuePairs.add(new BasicNameValuePair("adresse", ChampsForm[2]));
							nameValuePairs.add(new BasicNameValuePair("cp", ChampsForm[4]));
							nameValuePairs.add(new BasicNameValuePair("ville", ChampsForm[3]));
							nameValuePairs.add(new BasicNameValuePair("coeff", ChampsForm[5]));
							nameValuePairs.add(new BasicNameValuePair("code_type",typeMEF[1]));
							
							ConnexionHTTP ident= new ConnexionHTTP(nameValuePairs);
		    	        	StringBuilder sb=ident.getResponse();
		    	            if(sb!=null){
		    	            	String result = sb.toString();
		    	            	JSONObject jObj = null;
								try {
									jObj = new JSONObject(result);
									if (jObj.getInt(MainActivity.KEY_SUCCESS) != 0){
										UpdateIHM(1);
										Thread.currentThread().interrupt();
									}
									else{
										UpdateIHM(4);
										Thread.currentThread().interrupt();
									}
								}catch (JSONException e1){
									// TODO Auto-generated catch block
									e1.printStackTrace();
									UpdateIHM(3);
							  		Thread.currentThread().interrupt();
									
								} 
		    	            }
		    	            else{ //Erreur liée au réseau//
		    	            	UpdateIHM(2);
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
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	public void RAZ(EditText nom, EditText prenom, EditText adresse, EditText ville, EditText cp, EditText coeff, Spinner type){
		
		type.setSelection(0);
		nom.setText("");
		prenom.setText("");
		adresse.setText("");
		ville.setText("");
		cp.setText("");
		coeff.setText("");
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

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
	
	public String[] verifFormulaire(String[] Champs)
	{	
		String[] result=new String[7];
		for(int i=0; i<result.length; i++)
		{	
			//result[i]="false";
			switch(i)
			{
				case 4:
					if(Champs[i].length()!=5)
					{
						result[i]="false";
					}
					else
					{
						if((Long.valueOf(Champs[i])<01000) || (Long.valueOf(Champs[i])>97499))
						{
							result[i]="false";
						}
						else
						{
							result[i]="true";
						}
					}
					break;
				case 5:
					if(Champs[i].isEmpty())
					{
						result[i]="false";
					}
					else
					{
						if((Double.valueOf(Champs[i])<0) || (Double.valueOf(Champs[i])>=10000))
						{
							result[i]="false";
						}
						else
						{
							result[i]="true";
						}
					}
					break;
				case 6:
					if(spinnTypePra.getSelectedItemPosition() == 0)
					{
						result[i]="false";
					}
					else
					{
						result[i]="true";
					}
					break;
				default:
					if(Champs[i].isEmpty())
					{
						result[i]="false";
					}
					else
					{
						result[i]="true";
					}
					break;
			}
		}
		return result;
	}
	
	public void UpdateIHM(final int type) {
		// TODO Auto-generated method stub
		//Déposer le Runnable dans la file d'attente de l'UI thread
    	runOnUiThread(new Runnable() {
           @Override
           public void run() {
        	   AlertDialog.Builder box = new AlertDialog.Builder(activ);			
   				final LinearLayout lay=(LinearLayout) findViewById(R.id.Dialog);
        	   switch(type){
        	   	case 1:
        			box.setTitle("Information !");
    				box.setMessage("Praticien ajouté à la base avec succès !");
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
        	   		box.setTitle("Erreur !");
    				box.setMessage("Erreur liée au réseau lors de l'ajout du praticien à la base distante !");
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
        	   	case 3:
        	   		box.setTitle("Erreur !");
    				box.setMessage("Erreur liée à la conversion des données du formulaire !");
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
        	   	case 4:
        	   		box.setTitle("Erreur !");
    				box.setMessage("Erreur inconnue, praticien non enregistré !");
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
        		  default:
        			   break;
        	   }
           		//code exécuté par l'UI thread
        	   showProgress(false);
        	  // Toast.makeText(getApplicationContext(), "Connexion échouée, Matricule, Nom ou Region incorrecte !", Toast.LENGTH_SHORT).show();
            }
        });
	}
}
