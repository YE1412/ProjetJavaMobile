package com.example.gsb;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class StatsActivity extends Activity {
	private String[] argv;
	private String[] arg;
	private ListView detailVis;
	private Button btnRetour;
	private String visEnVisu;
	private int nbRapp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		
		// On récupère l'intent qui a lancé cette activité
	    Intent i = getIntent();
	    String[] visit=i.getStringArrayExtra("donnesvisit");
	    String[] donnees=i.getStringArrayExtra(MainActivity.EXTRA_FLUX);
	    String don=i.getStringExtra("stats");
	    argv=donnees;
	    visEnVisu=don;
	    arg=visit;
	    
	    for(String s:arg)
	    {
	    	Log.v("Detail V", s);
	    }
	    //Récupération du nombre de rapports//
	    RapportDAO rapp=new RapportDAO(getApplicationContext());
	    nbRapp=rapp.getNbRapportsParVis(visEnVisu);
	    //Log.v("Nb RAPPORTS", " "+nbRapp);
	  //Gestion du detail des Visiteurs //
   		detailVis= (ListView) findViewById(R.id.listView1);
   		
		String[][] donnee;
		String[] mefVis=new String[2];
		StatsDAO stats=new StatsDAO(getApplicationContext());
		//Formattage de text: Affichade de max  2 Digits après la virgule//
		DecimalFormat df=new DecimalFormat("#0.00%");
			//Formattage de text: Pour requête SQL//
		String clauses="";
		for(int j=0; j<argv.length; j++)
		{
			switch(j)
			{
				case 0:
					clauses+="vis_matricule='"+argv[j]+"'";
					break;	
				default:
					clauses+=" OR vis_matricule='"+argv[j]+"'";
					break;
			}
		}
		mefVis[0]=df.format(stats.recupStatsParVisiteur(nbRapp, clauses));
		mefVis[1]=df.format(stats.recupStatsParPraticien(nbRapp));
		Log.v("Stat Visiteurs", mefVis[0]);
		donnee=new String[2][2];
		for(int j=0; j<mefVis.length; j++){
			switch(j){
			case 0:
				donnee[j][0]="Statistique nbRapport/Visiteurs";
				donnee[j][1]= mefVis[j];
				break;
			case 1:
				donnee[j][0]="Statistique nbRapport/Praticiens";
				donnee[j][1]= mefVis[j];
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
		//detailVisView.setVisibility(View.VISIBLE);
		
		//Boutton Retour//
		this.btnRetour=(Button) findViewById(R.id.button1);
		ActionBouttonRetour actionBout=new ActionBouttonRetour(btnRetour, arg, this);
		actionBout.retour();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stats, menu);
		return true;
	}

}
