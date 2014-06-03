package com.example.gsb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BdActivity extends SQLiteOpenHelper{
		//Table Visiteur
		 public static final String VISITEUR_KEY = "_id";
		 public static final String VISITEUR_MATRICULE = "matricule";
		 public static final String VISITEUR_NOM = "nom";
		 public static final String VISITEUR_PRENOM = "prenom";
		 public static final String VISITEUR_ADRESSE = "adresse";
		 public static final String VISITEUR_CP = "cp";
		 public static final String VISITEUR_VILLE = "ville";
		 public static final String VISITEUR_DATEEMB = "dateembauche";
		 public static final String VISITEUR_SECCODE = "seccode";
		 public static final String VISITEUR_LABCODE = "labcode";
		//Table Medicament 
		 public static final String MEDICAMENT_KEY = "_id";
		 public static final String MEDICAMENT_DEPOT = "depotlegal";
		 public static final String MEDICAMENT_NOMCOM = "nomcommercial";
		 public static final String MEDICAMENT_CODEFAM = "codefamille";
		 public static final String MEDICAMENT_COMPO = "composition";
		 public static final String MEDICAMENT_EFFETS = "effets";
		 public static final String MEDICAMENT_CONTRINDIC = "contreindic";
		 public static final String MEDICAMENT_PRIX = "prixech";
		//Table Rapport 
		 public static final String RAPPORT_KEY = "_id";
		 public static final String RAPPORT_VISMATRICULE = "vis_matricule";
		 public static final String RAPPORT_NUM = "rap_num";
		 public static final String RAPPORT_PRANUM = "pra_num";
		 public static final String RAPPORT_DATE= "rap_date";
		 public static final String RAPPORT_BILAN= "rap_bilan";
		 public static final String RAPPORT_MOTIF = "rap_motif";
		 //Table Praticien
		 public static final String PRATICIEN_KEY = "_id";
		 public static final String PRATICIEN_NUM = "pra_num";
		 public static final String PRATICIEN_NOM = "pra_nom";
		 public static final String PRATICIEN_PRENOM = "pra_prenom";
		 public static final String PRATICIEN_ADRESSE = "pra_adresse";
		 public static final String PRATICIEN_CP = "pra_cp";
		 public static final String PRATICIEN_VILLE = "pra_ville";
		 public static final String PRATICIEN_COEFFNOT = "pra_coeffnotoriete";
		 public static final String PRATICIEN_TYPCODE = "pra_typcode";
		 //Table Offre
		 public static final String OFFRE_KEY = "_id";
		 public static final String OFFRE_MATVIS = "vis_mat";
		 public static final String OFFRE_RAPNUM = "rap_num";
		 public static final String OFFRE_MEDDEPOT = "med_depotlegal";
		 public static final String OFFRE_QTE= "off_qte";
		 
		 //Def. du nom des tables
		 public static final String OFFRE_TABLE_NAME="Offre";
		 public static final String VISITEUR_TABLE_NAME = "Visiteur";
		 public static final String MEDIC_TABLE_NAME = "Medicament";
		 public static final String RAPPORT_TABLE_NAME = "Rapport";
		 public static final String PRATICIEN_TABLE_NAME = "Praticien";
		 
		 public static final String OFFRE_TABLE_DROP="DROP TABLE IF EXISTS " + OFFRE_TABLE_NAME + ";";;
		 public static final String VISITEUR_TABLE_DROP = "DROP TABLE IF EXISTS " + VISITEUR_TABLE_NAME + ";";
		 public static final String MEDIC_TABLE_DROP = "DROP TABLE IF EXISTS " + MEDIC_TABLE_NAME + ";";
		 public static final String RAPPORT_TABLE_DROP = "DROP TABLE IF EXISTS " + RAPPORT_TABLE_NAME + ";";
		 public static final String PRATICIEN_TABLE_DROP = "DROP TABLE IF EXISTS " + PRATICIEN_TABLE_NAME + ";";
		 //----------------------------------------CREATION DES TABLES-----------------------------------------------
		 public static final String VISITEUR_TABLE_CREATE =
		    "CREATE TABLE " + VISITEUR_TABLE_NAME + " (_id integer primary key autoincrement, " +
		    "matricule TEXT, " +
		    "nom TEXT, "+
		    "prenom TEXT, "+
		    "adresse TEXT, "+
		    "cp INTEGER, "+
		    "ville TEXT, "+
		    "dateembauche TEXT, "+
		    "seccode TEXT NOT NULL, "+
		    "labcode TEXT NOT NULL);";
		 
		 public static final String MEDIC_TABLE_CREATE =
				    "CREATE TABLE " + MEDIC_TABLE_NAME + " (_id integer primary key autoincrement, " +
				    "depotlegal TEXT, " +
				    "nomcommercial TEXT, "+
				    "codefamille TEXT, "+
				    "composition TEXT, "+
				    "effets TEXT, "+
				    "contreindic TEXT, " +
				    "prixech TEXT);";
		 
		 public static final String RAPPORT_TABLE_CREATE =
				    "CREATE TABLE " + RAPPORT_TABLE_NAME + " (_id integer primary key autoincrement, " +
				    "vis_matricule TEXT, " +
				    "rap_num INT, "+
				    "pra_num INT, " +
				    "rap_date TEXT, " +
				    "rap_bilan TEXT, " +
				    "rap_motif TEXT);";
		 
		 public static final String PRATICIEN_TABLE_CREATE =
				    "CREATE TABLE " + PRATICIEN_TABLE_NAME + " (_id integer primary key autoincrement, " +
				    "pra_num INT, " +
				    "pra_nom TEXT, "+
				    "pra_prenom TEXT, "+
				    "pra_adresse TEXT, "+
				    "pra_cp INTEGER, "+
				    "pra_ville TEXT, "+
				    "pra_coeffnotoriete FLOAT, "+
				    "pra_typcode TEXT NOT NULL);";
	
		 public static final String OFFRE_TABLE_CREATE =
				    "CREATE TABLE " + OFFRE_TABLE_NAME + " (_id integer primary key autoincrement, " +
				    "vis_mat INT, " +
				    "rap_num INT, "+
				    "med_depotlegal TEXT, "+
				    "off_qte INT);";
		//--------------------------------------------------------------------------------------------------------------
		  public BdActivity(Context context, String name, CursorFactory factory, int version) {
		    super(context, name, factory, version);
		  }
		
		  @Override
		  public void onCreate(SQLiteDatabase db) {
			 Log.v("DAOBase", "CREATION TABLE ACTIVITE");
			 db.execSQL(VISITEUR_TABLE_CREATE);
			 db.execSQL(RAPPORT_TABLE_CREATE);
			 db.execSQL(PRATICIEN_TABLE_CREATE);
			 db.execSQL(OFFRE_TABLE_CREATE);
			 //Insertion dans les tables REGION et SECTEUR
			 db.execSQL(MEDIC_TABLE_CREATE);
			 //db.execSQL("INSERT INTO Secteur VALUES(),()");
		  }
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			 //Log.v("DAOBase", "MAJ TABLE ACTIVITE");
			 db.execSQL(VISITEUR_TABLE_DROP);
			 db.execSQL(MEDIC_TABLE_DROP);
			 db.execSQL(RAPPORT_TABLE_DROP);
			 db.execSQL(PRATICIEN_TABLE_DROP);
			 db.execSQL(OFFRE_TABLE_DROP);
			 onCreate(db);
		}

}
