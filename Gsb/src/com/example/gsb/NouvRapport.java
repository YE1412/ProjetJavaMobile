package com.example.gsb;

import java.util.ArrayList;

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
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class NouvRapport extends Activity implements View.OnTouchListener, View.OnClickListener, OnItemSelectedListener {
	private View mLoginStatusView;
	private View mLoginFormView;
	private String[] argVis;
	private Rapport rap;
	private Offre off;
	private Button b1;
	private Button b2;
	private Button b3;
	private Button ajout_layout;
	private Spinner Spin;
	private Spinner Spinnerprat;
	private Spinner Spinnermotif;
	private LinearLayout lay_offres;
	private ArrayList<Medicament> listmed;
	private ArrayList<Praticien> praticiens;
	private ArrayList<Offre> listOffre;
	private Context activ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nouv_rapport);
		setTitle("Création d'un nouveau Rapport");
		activ=this;
		mLoginStatusView=findViewById(R.id.status);
		mLoginFormView=findViewById(R.id.scrollView1);
		//Récupération de l'Intent qui à lancé l'activité//
		Intent intent = getIntent();
		this.argVis=intent.getStringArrayExtra(MainActivity.EXTRA_FLUX);
		/*argVis=new String[10];
		argVis[0]="Labouré-Morel";
		argVis[1]="j45";
		argVis[2]="Saout";
		argVis[3]="Responsable";
		argVis[4]="38 cours Berriat";
		argVis[5]="52000";
		argVis[6]="CHAUMONT";
		argVis[7]="1998-02-25";
		argVis[8]="N";
		argVis[9]="SW";*/
		
		//Récupération des medicaments de la base //
		MedicamentDAO base=new MedicamentDAO(this);
		listmed=base.recupererMedicaments(null, null);
		
		//Gestion des Spinner//
		Spin=(Spinner) findViewById(R.id.Spinner04);
		ArrayAdapter<String> adapt= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
		adapt.add("Médicament :");
		for(Medicament medic:listmed){
			adapt.add(medic.getDepotL());
		}
		Spin.setAdapter(adapt);
		Spin.setOnItemSelectedListener(this);
		
		PraticienDAO mana = new PraticienDAO(getApplicationContext());
		praticiens=mana.recupererTousLesPrat();
		Spinnerprat=(Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> adapterprat= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
		adapterprat.add("Praticien :");
		for(Praticien pra:praticiens){
			adapterprat.add(pra.getPraNum()+" "+pra.getPraNom()+" "+pra.getPraPrenom());
		}
		Spinnerprat.setAdapter(adapterprat);
		Spinnerprat.setOnItemSelectedListener(this);
		
		Spinnermotif=(Spinner) findViewById(R.id.spinner2);
		ArrayAdapter<String> adaptermotif= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
		adaptermotif.add("Périodicité");
		adaptermotif.add("Actualisation");
		adaptermotif.add("Relance");
		adaptermotif.add("Sollicitation praticien");
		adaptermotif.add("Autre");
		Spinnermotif.setAdapter(adaptermotif);
		Spinnermotif.setOnItemSelectedListener(this);
		
		//Gestion des boutton//
		b1=(Button) findViewById(R.id.button2);
		b2=(Button) findViewById(R.id.button3);
		b3=(Button) findViewById(R.id.button4);
		
		ajout_layout=(Button) findViewById(R.id.Button04);
		
		ActionBouttonRetour action= new ActionBouttonRetour(b1, argVis, getApplicationContext());
		action.retour();
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		ajout_layout.setOnClickListener(new OnClickListener(){	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Paramètres généraux du layout :


				//Déclaration du layout qui contient le produit et sa quantitée et instanciation de celui-ci.
				LinearLayout lay = new LinearLayout(activ);
				//Définition de son orientation.
				lay.setOrientation(LinearLayout.HORIZONTAL);
				//Définition de son padding (le padding est la distance en pixels, entre le bord du composant et son contenu).
				//lilPost.setPadding(0, 15, 0, 0);
				//Définition de la couleur de fond du layout.
				//lilPost .setBackgroundColor(0xFF007AAD);			
				//Tout d'abord, déclarez et instanciez le composant en lui passant son Contexte.
				//Ici le contexte n'est autre que l'activité dans lequel l'IHM est construite, d'où le this.

				TextView txvTitle = new TextView( activ );
				//Affectation du texte à afficher par le composant.
				txvTitle .setText( "Produit : ");
				//Affectation de sa couleur de fond.
				//txvTitle .setBackgroundColor(0x00000000);
				//Affectation de sa couleur de text.
				txvTitle .setTextColor(Color.parseColor("#000000"));
				
				//Gestion du spinner et remplissage de celui-ci en conséquence//
				Spinner produit_spinn=new Spinner(activ);
				//Recup de med précédent sélectionner pour la requete
				String meds="";
				int panelcount=lay_offres.getChildCount();
				String[] medprec=new String[panelcount];
				for(int i=0; i<panelcount; i++){
					LinearLayout layout=(LinearLayout) lay_offres.getChildAt(i);
					Spinner Spinner=(Spinner) layout.getChildAt(1);
					medprec[i]=Spinner.getSelectedItem().toString();
					if(i==0){
						meds+="?";
					}
					else
					{
						meds+=" ?";
					}
				}
				MedicamentDAO base=new MedicamentDAO(getApplicationContext());
				listmed=base.recupererMedicaments(meds, medprec);
				ArrayAdapter<String> adapter= new ArrayAdapter<String>(activ, android.R.layout.simple_spinner_dropdown_item);
				adapter.add("Médicament :");
				for(Medicament medic:listmed){
					adapter.add(medic.getDepotL());
				}
				//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				produit_spinn.setAdapter(adapter);
				produit_spinn.setVisibility(View.VISIBLE);
				produit_spinn.setOnItemSelectedListener(new OnItemSelectedListener(){

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						LinearLayout lay=(LinearLayout) arg1.getParent().getParent();
						Button boutton_plus=(Button) lay.getChildAt(3);
						if(arg2==0){
							boutton_plus.setVisibility(View.GONE);
						}
						else{
							boutton_plus.setVisibility(View.VISIBLE);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						
					}
					
				});
				
				EditText qte=new EditText(activ);
				qte.setInputType(InputType.TYPE_CLASS_NUMBER);
				qte.setGravity(Gravity.CENTER);
				qte.setVisibility(View.VISIBLE);
				
				
				Button Ancien_Butt=ajout_layout;
				Ancien_Butt.setVisibility(View.GONE);
				
				Button Nouv_Butt=new Button(activ, null, android.R.attr.buttonStyleSmall);
				Nouv_Butt.setText(R.string.bun_ajoutprod);
				Nouv_Butt.setOnClickListener(this);
				Nouv_Butt.setVisibility(View.VISIBLE);
				
				ajout_layout=Nouv_Butt;
				
				//Définition du typeFace du composant (Italic, Bold, Normal,Bold_Italic), un autre type de typeFace étant la police : MonoSpace, Sans_Serif, Serif.
				//txvTitle .setTypeface(Typeface. defaultFromStyle (Typeface. BOLD ));

				//Le texte à afficher quand il n'y a aucun texte à afficher par le composant (i.e. quand le composant n'a rien à afficher).
				//txvTitle .setHint( "C'est quoi un HintText" );

				//La couleur du hint-texte.
				//txvTitle .setHintTextColor(0xFF555555);


				//Pour permettre de sauver en mémoire l'état du composant quand l'activité sera détruite pour être restauré plus tard.
				//txvTitle .setFreezesText( true );

				//Ajout d'une scroll bar au composant.
				//txvTitle .setHorizontallyScrolling( true );

				//Mise en place d'une ligne de transparence horizontale (à droite et à gauche) lors du scroll du composant ou setVerticalFadingEdgeEnabled pour mettre cette ligne verticale (en haut et en bas), ou les deux. Si le composant n'a pas de scrollBar, cela ne fera rien.
				//txvTitle .setHorizontalFadingEdgeEnabled( true );

				//Définition de la hauteur/largeur de cette ligne de transparence.
				//txvTitle .setLines(2);


				//Gérez la visibilité du composant, il peut être Visible, Invisible (caché mais l'espace pour le composant reste réservé) ou Gone (caché et l'espace du composant est libéré et utilisé par les autres composants).
				txvTitle .setVisibility(View. VISIBLE );

				//Et ainsi de suite, explorez l'API.


				//Et ajout du composant au layout qui le contient.
				lay .addView( txvTitle , new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.WRAP_CONTENT ) );
				//Définition du paramètre de placement du composant au sein du layout et instanciation (avec la spécification de la manière dont le composant va remplir l'espace).
				lay .addView( produit_spinn , new LinearLayout.LayoutParams(0  , LinearLayout.LayoutParams.WRAP_CONTENT, 1f ) );
				lay .addView( qte , new LinearLayout.LayoutParams(80 , LinearLayout.LayoutParams.WRAP_CONTENT ) );
				lay .addView( Nouv_Butt , new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT  , LinearLayout.LayoutParams.WRAP_CONTENT ) );
				
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams. WRAP_CONTENT );

				//Définition de la gravité du composant (comment il se positionne au sein de la zone qui lui est allouée).
				//params . gravity =Gravity. CENTER_HORIZONTAL ;

				//Définition des marges du composant (distance, en pixels, autour du composant).
				//params .setMargins(5, 5, 5, 5);

				//Ajout du composant dans le layout avec les paramètres de positionnement définis ci-dessus.
				lay_offres .addView( lay , params );
			}
			
		});
		
		lay_offres=(LinearLayout) findViewById(R.id.layout_offres);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nouv_rapport, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.button3: //Cas du boutton Effacer//
			
			break;
		case R.id.button4: // Cas du boutton Enoyer //
			String[] resultat;
			String date, prat, motif, bilan;
			LinearLayout panel;
			
			EditText textdate=(EditText) findViewById(R.id.editText2);
			date=textdate.getText().toString();
			
			Spinner Spinprat=(Spinner) findViewById(R.id.spinner1);
			prat=Spinprat.getSelectedItem().toString();
			
			Spinner textmotif=(Spinner) findViewById(R.id.spinner2);
			motif=textmotif.getSelectedItem().toString();
			
			if(motif.equals("Autre")){
				EditText textmot=(EditText) findViewById(R.id.editText4);
				motif+=": "+textmot.getText().toString();
			}
			
			EditText textbilan=(EditText) findViewById(R.id.editText5);
			bilan=textbilan.getText().toString();
			
			panel=this.lay_offres;
			
			resultat=verifChamps(date, prat, motif, bilan, panel);
			int err=0;
			for(int i=0; i<resultat.length; i++){
				switch(i){
				case 4:
					if(!(resultat[i]==null)){
						String[] offres=resultat[i].split(";");
						for(String offre:offres){
							if(offre.equals("false")){
								err++;
							}
						}
					}
					break;
				default:
					if(resultat[i].equals("false")){
						err++;
					}
					break;
				}
				Log.v("Resutat Verifs", "Champs n "+(i+1)+": "+resultat[i]);
			}
			AlertDialog.Builder box = new AlertDialog.Builder(this);			
			final LinearLayout lay=(LinearLayout) findViewById(R.id.Dialog);
			if(err>0){
				Log.v("STOP", "Champs nom remplis :"+err);				
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
				//	Rapport	//
				RapportDAO managebd=new RapportDAO(getApplicationContext());
				int numRapp=managebd.getNbRapports()+1;
				String[] pratExplode=prat.split(" ");
				this.rap=new Rapport(argVis[1], numRapp, Integer.valueOf(pratExplode[0]), date, bilan, motif);
				//	Base SQlite	//
				managebd.ajouterRapport(rap);
				//	Offres	//
				int nbLigne=lay_offres.getChildCount();
				LinearLayout premiereOffreLayout=(LinearLayout) lay_offres.getChildAt(0); 
				listOffre=new ArrayList<Offre>();
				switch(nbLigne){
					case 1:
						if(premiereOffreLayout.getChildAt(3).isShown()){
							OffreDAO offremanage=new OffreDAO(getApplicationContext());
							Spinner cb=(Spinner) premiereOffreLayout.getChildAt(1);
							EditText et=(EditText) premiereOffreLayout.getChildAt(2);
							off=new Offre(argVis[1], numRapp, cb.getSelectedItem().toString(), Integer.valueOf(et.getText().toString()));
							//	Base SQLite	//
							offremanage.ajoutOffre(off);
							listOffre.add(off);
						}
						break;
					default:
						for(int i=0; i<nbLigne; i++){
							LinearLayout layout=(LinearLayout) lay_offres.getChildAt(i);
							Spinner cb=(Spinner) layout.getChildAt(1);
							EditText et=(EditText) layout.getChildAt(2);
							if(cb.getSelectedItemPosition()!= 0){
								off=new Offre(argVis[1], numRapp, cb.getSelectedItem().toString(), Integer.valueOf(et.getText().toString()));
								listOffre.add(off);
							}
						}
						break;
				}
				showProgress(true);
				new Thread(new Runnable (){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						String[] dateMEF=rap.getDate().split("/");
						String date=dateMEF[2]+"-"+dateMEF[1]+"-"+dateMEF[0];
						ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
						nameValuePairs.add(new BasicNameValuePair("tag","insert"));
						nameValuePairs.add(new BasicNameValuePair("type","rapport"));
						nameValuePairs.add(new BasicNameValuePair("auteur", rap.getVisMat()));
						nameValuePairs.add(new BasicNameValuePair("numeroP",String.valueOf(rap.getPraNum())));
						nameValuePairs.add(new BasicNameValuePair("date", date));
						nameValuePairs.add(new BasicNameValuePair("bilan",rap.getBilan()));
						nameValuePairs.add(new BasicNameValuePair("motif",rap.getMotif()));
						
						if(listOffre.size() != 0){
							String offresMEF="";
							for(Offre offreAMEF:listOffre){
								offresMEF+=offreAMEF.getDepotLegal()+";"+offreAMEF.getQuantite()+" ";
							}
							Log.v("envoi", "Offres MEF :"+offresMEF);
							nameValuePairs.add(new BasicNameValuePair("offres", offresMEF));
						}
						
						ConnexionHTTP ident= new ConnexionHTTP(nameValuePairs);
	    	        	StringBuilder sb=ident.getResponse();
	    	            if(sb!=null){
	    	            	String result = sb.toString().substring(1);
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
		}
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		switch(arg0.getId()){
			// TODO Auto-generated method stub
		case R.id.Spinner04:
			LinearLayout lay=(LinearLayout) arg1.getParent().getParent();
			Button boutton_plus=(Button) lay.getChildAt(3);
			if(arg2==0){
				boutton_plus.setVisibility(View.GONE);
			}
			else{
				boutton_plus.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.spinner1:
			EditText textprat=(EditText) findViewById(R.id.editText3);
			if(arg2==0){
				textprat.setText("");	
			}
			else{
				PraticienDAO managePra=new PraticienDAO(getApplicationContext());
				String[] mefPra=arg0.getSelectedItem().toString().split(" ");
				String[] praDet=managePra.Selectionner(Integer.valueOf(mefPra[0]));
				textprat.setText(praDet[6]);
			}
			textprat.refreshDrawableState();
			break;
		case R.id.spinner2:
			EditText textmotif=(EditText) findViewById(R.id.editText4);
			if(arg2==4){
				textmotif.setEnabled(true);
			}
			else{
				textmotif.setEnabled(false);
			}
			textmotif.refreshDrawableState();
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private String[] verifChamps(String Date, String Praticien, String Motif, String Bilan, LinearLayout panelOffres){
		String[] result;
		result=new String[5];
		for(int i=0; i<result.length; i++){
			switch(i){
				case 0:
					String[] DateForme;
					DateForme=Date.split("/");
					if(DateForme.length!=3){
						result[i]="false";
					}
					else{
						if((Integer.valueOf(DateForme[0])<32) && (Integer.valueOf(DateForme[0])>0)){
							if((Integer.valueOf(DateForme[1])<13) && (Integer.valueOf(DateForme[1])>0)){
								if((Integer.valueOf(DateForme[2])<9999) && (Integer.valueOf(DateForme[2])>1000)){
									result[i]="true";
								}
								else{
									result[i]="false";
								}
							}
							else{
								result[i]="false";
							}
						}
						else{
							result[i]="false";
						}
					}
					break;
				case 1:
					if(Praticien.equals("Praticien :")){
						result[i]="false";
					}
					else{
						result[i]="true";
					}
					break;
				case 2:
					EditText motifautre=(EditText) findViewById(R.id.editText4);
					if(Motif.equals("")){
						result[i]="false";
					}
					else if((Motif.equals("Autre")) && (motifautre.getText().toString().equals(""))){
						result[i]="false";
					}
					else{
						result[i]="true";
					}
					break;
				case 3:
					if(Bilan.equals("")){
						result[i]="false";
					}
					else{
						result[i]="true";
					}
					break;
				case 4:
					for(int j=0; j<panelOffres.getChildCount(); j++){
						LinearLayout pan;
						Spinner spinn;
						EditText qte;
						pan=(LinearLayout) panelOffres.getChildAt(j);
						spinn=(Spinner) pan.getChildAt(1);
						qte=(EditText) pan.getChildAt(2);
						if(j==0){
							if(!spinn.getSelectedItem().toString().equals("Médicament :")){
								if((!qte.getText().toString().equals("")) && (Integer.valueOf(qte.getText().toString()) > 0)){
									result[i]="true";
								}
								else{
									result[i]="false";
								}
							}
						}
						/*else if((j)==(panelOffres.getChildCount()-1)){
							
						}*/
						
						else{
							if(!spinn.getSelectedItem().toString().equals("Médicament :")){
								if((!qte.getText().toString().equals("")) && (Integer.valueOf(qte.getText().toString()) > 0)){
									result[i]+=";true";
								}
								else{
									result[i]+=";false";
								}
							}
						}
					}
					break;
				default:			
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
    				box.setMessage("Rapport ajouté à la base avec succès !");
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
    				box.setMessage("Erreur liée au réseau lors de l'ajout du rapport à la base distante !");
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
    				box.setMessage("Erreur liée à la conversion des données du rapport !");
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
    				box.setMessage("Erreur inconnue, rapport non enregistré !");
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

}
