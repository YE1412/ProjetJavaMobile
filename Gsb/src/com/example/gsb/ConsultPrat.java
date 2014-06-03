package com.example.gsb;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class ConsultPrat extends Activity implements OnItemSelectedListener, View.OnClickListener{
	private ListView detailPraticien;
	private Spinner spinn;
	private Button boutonRetour;
	private Button Bouton_Next;
	private Button Bouton_Prev;
	private String[] Visiteur;
	private ArrayList<String> praticiens;
	private ArrayList<Praticien> listPra;
	private Praticien pratEnVisu;
	private int index;
	private int indexSpinn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consult_pra);
		setTitle("Consultation des Praticiens");
		
		// On récupère l'intent qui a lancé cette activité
	    Intent i = getIntent();
	    String[] donnees=i.getStringArrayExtra(MainActivity.EXTRA_FLUX);
	    praticiens=new ArrayList<String>();
	    praticiens=i.getExtras().getStringArrayList(MainActivity.EXTRA_FLUX_PRAT);
	    Visiteur=donnees;
	    
	    detailPraticien= (ListView) findViewById(R.id.listView1);
		spinn=(Spinner) findViewById(R.id.spinner1);
		//Adaptation de la vue du spinner et ajout des différents médicaments dans celui-ci//		
		ArrayAdapter<String> adapt= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
		adapt.add("Choisir le praticien :");
		for(String nomPrenom:praticiens){
			adapt.add(nomPrenom);
		}
		
		spinn.setAdapter(adapt);
		//Ecoute du spinner//		
		spinn.setOnItemSelectedListener(this);
		
		//Action boutton retour//
		boutonRetour=(Button) findViewById(R.id.button3);
		ActionBouttonRetour action = new ActionBouttonRetour(boutonRetour, Visiteur, getApplicationContext());
		action.retour();
				
		Bouton_Next=(Button) findViewById(R.id.button2);
		Bouton_Prev=(Button) findViewById(R.id.button1);
		Bouton_Next.setOnClickListener(this);
		Bouton_Prev.setOnClickListener(this);
				
		PraticienDAO base= new PraticienDAO(this);
		listPra=new ArrayList<Praticien>();
		listPra = base.recupererTousLesPrat();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.consult_prat, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.button2: //Cas du boutton suivant //
			index=listPra.indexOf(pratEnVisu);
			pratEnVisu=listPra.get(index+1);
			spinn.setSelection(indexSpinn+1, true);
			afficherDetailsPrat(pratEnVisu, index+1);
			break;
		case R.id.button1: //Cas du boutton précédent //
			index=listPra.indexOf(pratEnVisu);
			pratEnVisu=listPra.get(index-1);
			spinn.setSelection(indexSpinn-1, true);
			afficherDetailsPrat(pratEnVisu, index-1);
			break;
	}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		if(arg2==0){
			detailPraticien.setVisibility(View.INVISIBLE);
			Bouton_Prev.setVisibility(View.INVISIBLE);
			Bouton_Next.setVisibility(View.INVISIBLE);
			//Log.v("Suppression des layout", arg0.toString());
		}
		else{
			Bouton_Prev.setVisibility(View.VISIBLE);
			Bouton_Next.setVisibility(View.VISIBLE);
			//Recuperation de l'index du spinner//
			indexSpinn=arg2;
			if(arg0.getChildCount()!=0){
				pratEnVisu=listPra.get(arg2-1);
				if(listPra.size()==1){
					afficherDetailsPrat(pratEnVisu, -1);
				}
				else{
					//Affichage des détails à l'index du medicament select.
					//se trouvant dans la liste de medicaments//
					afficherDetailsPrat(pratEnVisu, listPra.indexOf(pratEnVisu));
				}			
			}
			else{// Aucun Médicaments dans la base //
				detailPraticien.setVisibility(View.INVISIBLE);
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void afficherDetailsPrat(Praticien pra, int i){
		if(i==0){
			Bouton_Next.setOnClickListener(this);
			//Bouton_Prev.setOnClickListener(this);
			Bouton_Prev.setEnabled(false);
			Bouton_Next.setEnabled(true);
		}
		else if(i==(listPra.size()-1)){
			//Bouton_Next.setOnClickListener(this);
			Bouton_Prev.setOnClickListener(this);
			Bouton_Prev.setEnabled(true);
			Bouton_Next.setEnabled(false);
		}
		else if(i==-1){
			// Bouton_Next.setOnClickListener(this);
			 //   Bouton_Prev.setOnClickListener(this);
			Bouton_Prev.setEnabled(false);
			Bouton_Next.setEnabled(false);
		}
		else{
			Bouton_Next.setOnClickListener(this);
			Bouton_Prev.setOnClickListener(this);
			Bouton_Prev.setEnabled(true);
			Bouton_Next.setEnabled(true);
		}	
			String[][] donnee;
			donnee=new String[6][2];
			
			for(int j=0; j<6; j++){
					switch(j){
						case 0:
							donnee[j][0]="Numéro";
							donnee[j][1]=String.valueOf(pra.getPraNum());
							break;
						case 1:
							donnee[j][0]="Adresse";
							donnee[j][1]=pra.getPraAdresse();
							break;
						case 2:
							donnee[j][0]="Code Postal";
							donnee[j][1]=String.valueOf(pra.getPraCp());
							break;
						case 3:
							donnee[j][0]="Ville";
							donnee[j][1]=pra.getPraVille();
							break;
						case 4:
							donnee[j][0]="Coefficient Notoriété";
							donnee[j][1]=String.valueOf(pra.getPraCoeff());
							break;
						case 5:
							donnee[j][0]="Code Type";
							donnee[j][1]=pra.getPraCodeTyp();
							break;
						default:
							break;
					}
			}
			
			ArrayList<HashMap<String, String>> list=new ArrayList<HashMap<String, String>>();
			//Log.v("detail", listMed.get(0).getNomCom()+ " prod select. "+arg0.getSelectedItem().toString());
			HashMap<String, String> element;
			for(int j=0; j<donnee.length; j++){
				element=new HashMap<String, String>();
				element.put("1", donnee[j][0]);
				element.put("2", donnee[j][1]);
				list.add(element);			
			}
			SimpleAdapter adapter= new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, new String[]{"1", "2"}, new int[]{android.R.id.text1, android.R.id.text2});

			detailPraticien.setAdapter(adapter);
			detailPraticien.setVisibility(View.VISIBLE);
		}

}
