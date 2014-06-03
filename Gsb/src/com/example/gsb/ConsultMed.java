package com.example.gsb;

import java.util.ArrayList;
import java.util.HashMap;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

public class ConsultMed extends Activity implements OnItemSelectedListener, View.OnClickListener {
	private ListView detailMed;
	private Spinner spinn;
	private Button boutonRetour;
	private Button Bouton_Next;
	private Button Bouton_Prev;
	private String[] sessionVis;
	private ArrayList<String> meds;
	private ArrayList<Medicament> listMed;
	private Medicament medEnVisu;
	private int index;
	private int indexSpinn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consult_med);
		setTitle("Médicaments");
		
		// On récupère l'intent qui a lancé cette activité
	    Intent i = getIntent();
	    String[] donnees=i.getStringArrayExtra(MainActivity.EXTRA_FLUX);
	    meds=new ArrayList<String>();
	    meds=i.getExtras().getStringArrayList(MainActivity.EXTRA_FLUX_MED);
	    sessionVis=donnees;
	    
		detailMed= (ListView) findViewById(R.id.listView1);
		spinn=(Spinner) findViewById(R.id.spinner1);
		//Adaptation de la vue du spinner et ajout des différents médicaments dans celui-ci//		
		ArrayAdapter<String> adapt= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
		adapt.add("Choisir le médicament :");
		for(String nomcom:meds){
			adapt.add(nomcom);
		}
		//Log.v("TAILLE","Taille Med :"+meds.size()+" Taille session :"+sessionVis.length);
		//Application de l'adapte	
		spinn.setAdapter(adapt);
		//Ecoute du spinner//		
		spinn.setOnItemSelectedListener(this);
		//Log.v("Contenu du Spinner :", String.valueOf(spinn.getCount()));
		
		//Action boutton retour//
		boutonRetour=(Button) findViewById(R.id.button1);
		ActionBouttonRetour action = new ActionBouttonRetour(boutonRetour, sessionVis, getApplicationContext());
		action.retour();
		
		Bouton_Next=(Button) findViewById(R.id.button2);
		Bouton_Prev=(Button) findViewById(R.id.button3);
		Bouton_Next.setOnClickListener(this);
		Bouton_Prev.setOnClickListener(this);
		
		MedicamentDAO base= new MedicamentDAO(this);
		listMed=new ArrayList<Medicament>();
		listMed = base.recupererMedicaments(null, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.consult_med, menu);
		return true;
	}
	
//____________________________________GESTION DE SPINNER______________________________________________//
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		if(arg2==0){
			detailMed.setVisibility(View.GONE);
			Bouton_Prev.setVisibility(View.GONE);
			Bouton_Next.setVisibility(View.GONE);
			//Log.v("Suppression des layout", arg0.toString());
		}
		else{
			Bouton_Prev.setVisibility(View.VISIBLE);
			Bouton_Next.setVisibility(View.VISIBLE);
			//Recuperation de l'index du spinner//
			indexSpinn=arg2;
			if(arg0.getChildCount()!=0){
				medEnVisu=listMed.get(arg2-1);
				if(listMed.size()==1){
					afficherDetailsMed(medEnVisu, -1);
				}
				else{
					//Affichage des détails à l'index du medicament select.
					//se trouvant dans la liste de medicaments//
					afficherDetailsMed(medEnVisu, listMed.indexOf(medEnVisu));
				}			
			}
			else{// Aucun Médicaments dans la base //
				detailMed.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
//____________________________________________________________________________________________________________//
	//_____________________________GESTION DES BOUTTONS_____________________________________________________//
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
			case R.id.button2: //Cas du boutton suivant //
				index=listMed.indexOf(medEnVisu);
				medEnVisu=listMed.get(index+1);
				spinn.setSelection(indexSpinn+1, true);
				afficherDetailsMed(medEnVisu, index+1);
				break;
			case R.id.button3: //Cas du boutton précédent //
				index=listMed.indexOf(medEnVisu);
				medEnVisu=listMed.get(index-1);
				spinn.setSelection(indexSpinn-1, true);
				afficherDetailsMed(medEnVisu, index-1);
				break;
		}
	}
//_________________________________________________________________________________________________________//
	public void afficherDetailsMed(Medicament med, int i){
		if(i==0){
			Bouton_Next.setOnClickListener(this);
			//Bouton_Prev.setOnClickListener(this);
			Bouton_Prev.setEnabled(false);
			Bouton_Next.setEnabled(true);
		}
		else if(i==(listMed.size()-1)){
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
			donnee=new String[7][2];
			
			for(int j=0; j<7; j++){
					switch(j){
					case 1:
						donnee[j][0]="Nom Commercial";
						donnee[j][1]=med.getNomCom();
						break;
					case 2:
						donnee[j][0]="Code Famille";
						donnee[j][1]=med.getFamCode();
						break;
					case 3:
						donnee[j][0]="Composition";
						donnee[j][1]=med.getComp();
						break;
					case 4:
						donnee[j][0]="Effets";
						donnee[j][1]=med.getEffets();
						break;
					case 5:
						donnee[j][0]="Contre-Indications";
						donnee[j][1]=med.getContrInd();
						break;
					case 6:
						donnee[j][0]="Prix";
						donnee[j][1]=med.getPrix();
						break;
					default:
						donnee[j][0]="Depot Legal";
						donnee[j][1]=med.getDepotL();
						break;
					}
			}
			
			ArrayList<HashMap<String, String>> list=new ArrayList<HashMap<String, String>>();
			//Log.v("detail", listMed.get(0).getNomCom()+ " prod select. "+arg0.getSelectedItem().toString());
			HashMap<String, String> element;
			for(int j=1; j<donnee.length; j++){
				element=new HashMap<String, String>();
				element.put("1", donnee[j][0]);
				element.put("2", donnee[j][1]);
				list.add(element);			
			}
			SimpleAdapter adapter= new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, new String[]{"1", "2"}, new int[]{android.R.id.text1, android.R.id.text2});

			detailMed.setAdapter(adapter);
			detailMed.setVisibility(View.VISIBLE);
		}
		
		

}
