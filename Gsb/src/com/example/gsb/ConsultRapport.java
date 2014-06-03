package com.example.gsb;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class ConsultRapport extends Activity implements OnItemSelectedListener, View.OnClickListener{
	private String[] arg;
	private ArrayList<String> liste;
	private Spinner spinner;
	private Spinner spinnerAutre;
	private ListView detailRapp;
	private Button bout;
	private ArrayList<Rapport> listRapp;
	private ArrayList<Offre> listOffres;
	private Rapport rapportEnVisu;
	private Button bout_suiv;
	private Button bout_prec;
	private LinearLayout layOffre;
	private Context activ;
	private String visMatEnVis;
	private TextView texteAccueil;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consult_rapport);
		setTitle("Rapport des Visites");
		activ=this;
		// On récupère l'intent qui a lancé cette activité
	    Intent i = getIntent();
	    String[] donnees=i.getStringArrayExtra(MainActivity.EXTRA_FLUX);
	    liste=new ArrayList<String>();
	   
	    arg=donnees;
	   
	    //Gestion du Spinner //
	    spinner=(Spinner) findViewById(R.id.spinner1);
	    if(arg[3].equals("Visiteur")){
	    	 liste=i.getExtras().getStringArrayList(MainActivity.EXTRA_FLUX_DATE);
	    	//Adaptation de la vue du spinner et ajout des différents médicaments dans celui-ci//		
			ArrayAdapter<String> adapt= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
			adapt.add("Choisir une Date :");
			for(String date:liste){
				adapt.add(date);
			}
			//Log.v("TAILLE","Taille Med :"+meds.size()+" Taille session :"+sessionVis.length);
			//Application de l'adapte	
			spinner.setAdapter(adapt);
	    }
	    else{
	    	
	    	texteAccueil=(TextView) findViewById(R.id.textView1);
	    	texteAccueil.setText("Sélectionnez une date ou un visiteur pour récupérer les rapports");
	    	 liste=i.getExtras().getStringArrayList(MainActivity.EXTRA_FLUX_VIS);
	    	ArrayList<String> listeDates=new ArrayList<String>();
	    	listeDates=i.getExtras().getStringArrayList(MainActivity.EXTRA_FLUX_DATE);
	    	 //Adaptation de la vue du spinner et ajout des différents médicaments dans celui-ci//		
			
	    	LinearLayout lay_choix_spinn=(LinearLayout) findViewById(R.id.lay_choix_spinn);
	    	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200 , 100);
	    	lay_choix_spinn.removeAllViews();
	    	lay_choix_spinn.setOrientation(LinearLayout.HORIZONTAL);
	    	
	    	TextView text=new TextView(activ);
	    	text.setText("Ou ");
	    	
	    	spinnerAutre=new Spinner(activ);
	    	ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
			adapter.add("Choisir une Date :");
			for(String Date:listeDates){
				adapter.add(Date);
			}
			spinnerAutre.setAdapter(adapter);
			spinnerAutre.setOnItemSelectedListener(this);
			
	    	ArrayAdapter<String> adapt= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
			adapt.add("Choisir un Visiteur :");
			for(String visiteur:liste){
				adapt.add(visiteur);
			}
			//Log.v("TAILLE","Taille Med :"+meds.size()+" Taille session :"+sessionVis.length);
			//Application de l'adapte	
			spinner.setAdapter(adapt);
			lay_choix_spinn.addView(spinner, params);
			lay_choix_spinn.addView(text, new LinearLayout.LayoutParams(40,  LinearLayout.LayoutParams.WRAP_CONTENT));
			lay_choix_spinn.addView(spinnerAutre, params);
	    } 
	    spinner.setOnItemSelectedListener(this);
	    //Gestion du detail des Rapports //
	    detailRapp= (ListView) findViewById(R.id.listView1);
	    
	    //Gestion du boutton retour //
	    bout=(Button) findViewById(R.id.button1);
	    ActionBouttonRetour action=new ActionBouttonRetour(bout, arg, getApplicationContext());
	    action.retour();
	    
	  //Gestion des bouttons de navigations//
    	bout_suiv=(Button) findViewById(R.id.button3);
    	bout_prec=(Button) findViewById(R.id.button2);
    	
    	//Gestion du layout des Offres //
    	layOffre=(LinearLayout) findViewById(R.id.layout_O);
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.consult_rapport, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int ind=0;
		switch(v.getId()){
			case R.id.button3: //Cas du boutton Suivant //
				ind=listRapp.indexOf(rapportEnVisu);
				rapportEnVisu=listRapp.get(ind+1);
				afficherDetailsRapp(rapportEnVisu, ind+1);
				break;
			case R.id.button2: //Cas du boutton precedent //
				ind=listRapp.indexOf(rapportEnVisu);
				rapportEnVisu=listRapp.get(ind-1);
				afficherDetailsRapp(rapportEnVisu, ind-1);
				break;
			default:
				break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		//Log.v("argument spinner", "arg :"+arg0.getId());		
		RapportDAO manage = new RapportDAO(getApplicationContext());
		switch(arg0.getId()){
			case R.id.spinner1:
				
				if(!arg[3].equals("Visiteur")){
					if((arg2==0) && (spinnerAutre.getSelectedItemPosition()==0)){
						detailRapp.setVisibility(View.INVISIBLE);
						bout_suiv.setVisibility(View.INVISIBLE);
						bout_prec.setVisibility(View.INVISIBLE);
					}
					else if(arg2!=0){
					//detailRapp.setVisibility(View.GONE);
					//Récupération de tous les rapports médicaux a la date  choisie //
					
					
						if(spinnerAutre.getSelectedItemPosition()!=0)
						{
							spinnerAutre.setSelection(0);
						}
						//Log.v("ViewNumber","View group size("+arg0.getChildCount()+")");
						String visMEF[]=arg0.getSelectedItem().toString().split("-");
						visMatEnVis=visMEF[1];
						listRapp=manage.SelectionnerParVis(visMatEnVis);
						//Log.v("Taille tabRAP :", String.valueOf(listRapp.size()));
						if(listRapp.size()!=0){
							rapportEnVisu=listRapp.get(0);
							//On Affiche les boutton de navig. //
							bout_suiv.setVisibility(View.VISIBLE);
							bout_prec.setVisibility(View.VISIBLE);
								if(listRapp.size()==1){
									afficherDetailsRapp(rapportEnVisu, -1);
								}
								else{
									afficherDetailsRapp(rapportEnVisu, 0);
								}
							}
							else{ //Aucun rapport pour ce visiteur //
								bout_suiv.setVisibility(View.INVISIBLE);
								bout_prec.setVisibility(View.INVISIBLE);
								layOffre.removeAllViews();
								detailRapp.setVisibility(View.INVISIBLE);
							}
						}
						else
						{
							//On ne fait rien
						}
						
					}
					else
					{
							if(arg2==0){
								detailRapp.setVisibility(View.INVISIBLE);
								bout_suiv.setVisibility(View.INVISIBLE);
								bout_prec.setVisibility(View.INVISIBLE);
							}
							else
							{
							//manage.SelectionnerParDate(arg0.getSelectedItem().toString(), arg[1]);
							//Log.v("Test", "Date Sélectionnée :"+arg0.getSelectedItem().toString());
							visMatEnVis=arg[1];
							listRapp=manage.SelectionnerParDate(arg0.getSelectedItem().toString(), visMatEnVis);					
							//Log.v("Test", "Taille Liste :"+listRapp.size());
							if(listRapp.size()!=0){
								rapportEnVisu=listRapp.get(0);
								// On affiche les boutton de navigation entre les rapport //
								bout_suiv.setVisibility(View.VISIBLE);
								bout_prec.setVisibility(View.VISIBLE);
								if(listRapp.size()==1){
									afficherDetailsRapp(rapportEnVisu, -1);
								}
								else{
									afficherDetailsRapp(rapportEnVisu, 0);
								}
							}
							else{ //Aucun rapport a cette date //
								bout_suiv.setVisibility(View.INVISIBLE);
								bout_prec.setVisibility(View.INVISIBLE);
								layOffre.removeAllViews();
								detailRapp.setVisibility(View.INVISIBLE);
							}
						}
					}
				break;
			case -1:
				/*Sélection par dates */
				//Log.v("Choix par Date !", "choix");
				if((arg2==0) && (spinner.getSelectedItemPosition()==0)){
					detailRapp.setVisibility(View.INVISIBLE);
					bout_suiv.setVisibility(View.INVISIBLE);
					bout_prec.setVisibility(View.INVISIBLE);
				}
				else if(arg2!=0){
					if(spinner.getSelectedItemPosition()!=0){
						spinner.setSelection(0);
					}
					visMatEnVis=arg[1];
					manage = new RapportDAO(getApplicationContext());
					listRapp=manage.SelectionnerParDate(arg0.getSelectedItem().toString(), visMatEnVis);					
					//Log.v("Test", "Taille Liste :"+listRapp.size());
					if(listRapp.size()!=0){
						rapportEnVisu=listRapp.get(0);
						// On affiche les boutton de navigation entre les rapport //
						bout_suiv.setVisibility(View.VISIBLE);
						bout_prec.setVisibility(View.VISIBLE);
						if(listRapp.size()==1){
							afficherDetailsRapp(rapportEnVisu, -1);
						}
						else{
							afficherDetailsRapp(rapportEnVisu, 0);
						}
					}
					else{ //Aucun rapport a cette date //
						bout_suiv.setVisibility(View.INVISIBLE);
						bout_prec.setVisibility(View.INVISIBLE);
						layOffre.removeAllViews();
						detailRapp.setVisibility(View.INVISIBLE);
					}
				}
				else
				{
					//Ne rien faire
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void afficherDetailsRapp(Rapport rapp, int i){
		//Suppression des données de l'ancien panel des offres si elles existent + ajout du label Offres
		OffreDAO manage1=new OffreDAO(activ);
		listOffres=new ArrayList<Offre>();
		Log.v("Matricule Vis", arg[1]);
		listOffres=manage1.Selectionner(rapp.getNum(), visMatEnVis);
		layOffre.removeAllViews();
		if(listOffres.size()>0){
			Log.v("Offres", listOffres.get(0).getDepotLegal());
			LinearLayout OffLay=new LinearLayout(activ);
			OffLay.setOrientation(LinearLayout.VERTICAL);
			LinearLayout lay = null;
			
			//int j=0;
			for(Offre offre:listOffres){
				lay = new LinearLayout(getApplicationContext());
				lay.setOrientation(LinearLayout.HORIZONTAL);
				
				TextView txvTitle = new TextView( activ );
				txvTitle .setText( "Produit : ");
				txvTitle .setTextColor(Color.parseColor("#000000"));
				
				EditText med=new EditText(activ);
				med.setInputType(InputType.TYPE_CLASS_TEXT);
				med.setGravity(Gravity.CENTER);
				med.setVisibility(View.VISIBLE);
				med.setEnabled(false);
				
				EditText qte=new EditText(activ);
				qte.setInputType(InputType.TYPE_CLASS_NUMBER);
				qte.setGravity(Gravity.CENTER);
				qte.setVisibility(View.VISIBLE);
				qte.setEnabled(false);
				
				med.setText(offre.getDepotLegal());
				qte.setText(String.valueOf(offre.getQuantite()));
				
				//lay.removeAllViews();
				lay.addView(txvTitle, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.WRAP_CONTENT ));
				lay.addView(med, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT , 1f ));
				lay.addView(qte, new LinearLayout.LayoutParams(80, LinearLayout.LayoutParams.WRAP_CONTENT));
				
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams. WRAP_CONTENT );
				OffLay.addView(lay, params);
			}
			//layOffre.removeAllViews();
			layOffre.addView(OffLay);
		}
		
		//Gestion de l'apparence des bouttons lors de la navigation
		//entre les rapports du meme jour
		if(i==0){
			bout_suiv.setOnClickListener(this);
			//bout_prec.setOnClickListener(this);
			bout_prec.setEnabled(false);
			bout_suiv.setEnabled(true);
		}
		else if(i==(listRapp.size()-1)){
			//bout_suiv.setOnClickListener(this);
			bout_prec.setOnClickListener(this);
			bout_prec.setEnabled(true);
			bout_suiv.setEnabled(false);
		}
		else if(i==-1){
			// bout_suiv.setOnClickListener(this);
			 //   bout_prec.setOnClickListener(this);
			bout_prec.setEnabled(false);
			bout_suiv.setEnabled(false);
		}
		else{
			bout_suiv.setOnClickListener(this);
			bout_prec.setOnClickListener(this);
			bout_prec.setEnabled(true);
			bout_suiv.setEnabled(true);
		}
		
		 
		String[][] donnee;
		donnee=new String[5][2];
		for(int j=0; j<donnee.length; j++){
			switch(j){
			case 0:
				donnee[j][0]="Numéro Rapport";
				donnee[j][1]= String.valueOf(rapp.getNum());
				break;
			case 1:
				donnee[j][0]="Numéro Praticien";
				donnee[j][1]= String.valueOf(rapp.getPraNum());
				break;
			case 2:
				donnee[j][0]="Date Rapport";
				donnee[j][1]= rapp.getDate();
				break;
			case 3:
				donnee[j][0]="Bilan Rapport";
				donnee[j][1]= rapp.getBilan();
				break;
			case 4:
				donnee[j][0]="Motif Rapport";
				donnee[j][1]= rapp.getMotif();
				break;
			default:
				break;						
			}
		}
		ArrayList<HashMap<String, String>> list=new ArrayList<HashMap<String, String>>();
		Log.v("detail", donnee[0][1]+ " motif select. "+rapp.getMotif());
		HashMap<String, String> element;
		for(int j=0; j<donnee.length; j++){
			element=new HashMap<String, String>();
			element.put("1", donnee[j][0]);
			element.put("2", donnee[j][1]);
			list.add(element);			
		}		
		SimpleAdapter adapter= new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, new String[]{"1", "2"}, new int[]{android.R.id.text1, android.R.id.text2});	
		detailRapp.setAdapter(adapter);
		detailRapp.setVisibility(View.VISIBLE);
		 
		
	}

}
