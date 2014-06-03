package com.example.gsb;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class ConsultVis extends Activity implements OnItemSelectedListener, View.OnClickListener{
	private String[] argv;
	private ArrayList<String> liste;
	private String[] listeMatricules;
	private Spinner spinner;
	private ListView detailVis;
	private Button bout;
	/*private ArrayList<Visiteur> listVisiteurs;
	private Visiteur visiteurtEnVisu;*/
	private Button bout_suiv;
	private Button bout_prec;
	private LinearLayout detailVisView;
	private Button bout_stat;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consult_vis);
		
		// On récupère l'intent qui a lancé cette activité
	    Intent i = getIntent();
	    String[] donnees=i.getStringArrayExtra(MainActivity.EXTRA_FLUX);
	    argv=donnees;
	    liste=new ArrayList<String>();
	    liste=i.getStringArrayListExtra(MainActivity.EXTRA_FLUX_EMPLOYE);
	    

	    //Gestion du Spinner //
	    listeMatricules=new String[liste.size()];
	    spinner=(Spinner) findViewById(R.id.spinner1);	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
	    adapter.add("Chosir un visiteur :");
	    int incr=0;
	    for(String vis:liste){
	    	String[] visMEF=vis.split(";");
	    	//Log.v("Taille VisTab :", String.valueOf(visMEF.length));
	    	listeMatricules[incr]=visMEF[0];
	    	adapter.add(visMEF[1]+" "+visMEF[2]);
	    	incr++;
	    	Log.v("Visiteur", visMEF[0]+" "+visMEF[1]+" "+visMEF[2]);
	    }
	    spinner.setAdapter(adapter);
	    spinner.setOnItemSelectedListener(this);
	  //Gestion du boutton retour //
	   bout=(Button) findViewById(R.id.button3);
	   ActionBouttonRetour action=new ActionBouttonRetour(bout, argv, getApplicationContext());
	   action.retour();
	   
	   //Gestion des bouttons de navigations//
   		bout_suiv=(Button) findViewById(R.id.button2);
   		bout_prec=(Button) findViewById(R.id.button1);
   		bout_suiv.setVisibility(View.GONE);
   		bout_prec.setVisibility(View.GONE);
   		
   		bout_stat=(Button) findViewById(R.id.button4);
   		bout_stat.setOnClickListener(this);
   		//Gestion du detail des Visiteurs //
   		detailVis= (ListView) findViewById(R.id.listView1);
   		detailVisView= (LinearLayout) findViewById(R.id.layout_detail_vis);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.consult_vis, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.button4:
				Intent dashboard=new Intent(getApplicationContext(), StatsActivity.class);
				 dashboard.putExtra(MainActivity.EXTRA_FLUX, listeMatricules);
				 dashboard.putExtra("donnesvisit", argv);
				 dashboard.putExtra("stats", listeMatricules[spinner.getSelectedItemPosition()-1]);
				 //Log.v("VISTEUR AYANT LES STATS", listeMatricules[spinner.getSelectedItemPosition()-1]);
			     startActivity(dashboard);
			     
				 break;
			default:
				break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		if(arg2==0){
			detailVisView.setVisibility(View.INVISIBLE);
			/*bout_suiv.setVisibility(View.GONE);
			bout_prec.setVisibility(View.GONE);*/
		}
		else{
			String visiteur=liste.get(arg2-1);
			String[][] donnee;
			String[] mefVis=visiteur.split(";");
			donnee=new String[7][2];
			for(int j=0; j<mefVis.length; j++){
				switch(j){
				case 0:
					donnee[j][0]="Matricule";
					donnee[j][1]= mefVis[j];
					break;
				case 3:
					donnee[j-2][0]="Adresse";
					donnee[j-2][1]= mefVis[j];
					break;
				case 4:
					donnee[j-2][0]="Code Postal";
					donnee[j-2][1]= mefVis[j];
					break;
				case 5:
					donnee[j-2][0]="Ville";
					donnee[j-2][1]= mefVis[j];
					break;
				case 6:
					donnee[j-2][0]="Date Embauche";
					donnee[j-2][1]= mefVis[j];
					break;
				case 7:
					donnee[j-2][0]="Code Secteur";
					donnee[j-2][1]= mefVis[j];
					break;
				case 8:
					donnee[j-2][0]="Code Laboratoire";
					donnee[j-2][1]= mefVis[j];
					break;
				default:
					break;						
				}
				
			}
			ArrayList<HashMap<String, String>> list=new ArrayList<HashMap<String, String>>();
			//Log.v("detail", donnee[0][1]+ " motif select. "+rapp.getMotif());
			HashMap<String, String> element;
			for(int j=0; j<donnee.length; j++){
				element=new HashMap<String, String>();
				element.put("1", donnee[j][0]);
				element.put("2", donnee[j][1]);
				list.add(element);			
			}		
			SimpleAdapter adapt= new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, new String[]{"1", "2"}, new int[]{android.R.id.text1, android.R.id.text2});	
			detailVis.setAdapter(adapt);
			detailVisView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
