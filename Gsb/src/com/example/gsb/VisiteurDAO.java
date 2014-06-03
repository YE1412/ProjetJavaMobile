package com.example.gsb;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class VisiteurDAO extends DAOBase{
	 private String VISITEUR_KEY = BdActivity.VISITEUR_KEY;
	 private String VISITEUR_MATRICULE = BdActivity.VISITEUR_MATRICULE;
	 private String VISITEUR_NOM = BdActivity.VISITEUR_NOM;
	 private String VISITEUR_PRENOM = BdActivity.VISITEUR_PRENOM;
	 private String VISITEUR_ADRESSE = BdActivity.VISITEUR_ADRESSE;
	 private String VISITEUR_CP = BdActivity.VISITEUR_CP;
	 private String VISITEUR_VILLE = BdActivity.VISITEUR_VILLE;
	 private String VISITEUR_DATEEMB = BdActivity.VISITEUR_DATEEMB;
	 private String VISITEUR_SECCODE = BdActivity.VISITEUR_SECCODE;
	 private String VISITEUR_LABCODE = BdActivity.VISITEUR_LABCODE;
	 private String VISITEUR_TABLE_NAME = BdActivity.VISITEUR_TABLE_NAME;
	 
	public VisiteurDAO(Context pContext) {
		super(pContext);
		// TODO Auto-generated constructor stub
	}
	public void ajouterVisiteur(Visiteur V){
		ContentValues value=new ContentValues();
		value.put(this.VISITEUR_MATRICULE, V.getMatricule());
		value.put(this.VISITEUR_NOM, V.getNom());
		value.put(this.VISITEUR_PRENOM, V.getPrenom());
		value.put(this.VISITEUR_ADRESSE, V.getAdresse());
		value.put(this.VISITEUR_CP, V.getCp());
		value.put(this.VISITEUR_VILLE, V.getVille());
		value.put(this.VISITEUR_DATEEMB, V.getDateEmb());
		value.put(this.VISITEUR_SECCODE, V.getSecCode());
		value.put(this.VISITEUR_LABCODE, V.getLabCode());
		open();
		Log.v(this.VISITEUR_TABLE_NAME, V.getPrenom());
		mDb.insert(this.VISITEUR_TABLE_NAME, null, value);
		close();
	}
	public void supprimerVisiteur(String matricule){
		mDb.delete(VISITEUR_TABLE_NAME, VISITEUR_MATRICULE + " = ?", new String[]{matricule});
	}
	public void majVisiteur(Visiteur vaj, String matriculeVisAMaj){
		ContentValues value=new ContentValues();
		value.put(this.VISITEUR_MATRICULE, vaj.getMatricule());
		value.put(this.VISITEUR_NOM, vaj.getNom());
		value.put(this.VISITEUR_PRENOM, vaj.getPrenom());
		value.put(this.VISITEUR_ADRESSE, vaj.getAdresse());
		value.put(this.VISITEUR_CP, vaj.getCp());
		value.put(this.VISITEUR_VILLE, vaj.getVille());
		value.put(this.VISITEUR_DATEEMB, vaj.getDateEmb());
		value.put(this.VISITEUR_SECCODE, vaj.getSecCode());
		value.put(this.VISITEUR_LABCODE, vaj.getLabCode());
		open();
		mDb.update(VISITEUR_TABLE_NAME, value, VISITEUR_MATRICULE+"=?", new String[]{matriculeVisAMaj});
		close();
	}
	public String[][] Selectionner(String matricule, String nom){
		String[][] result = null;
		open();
		Cursor c=mDb.rawQuery("SELECT "+this.VISITEUR_KEY +", "+this.VISITEUR_MATRICULE+", "+this.VISITEUR_NOM+" FROM "+this.VISITEUR_TABLE_NAME +" WHERE "+ this.VISITEUR_MATRICULE +"=? AND "+this.VISITEUR_NOM+"=?", new String[]{matricule, nom});
		if(c!=null){
			result=new String[c.getCount()][3];
			while(c.moveToNext()){ 
				for(int i=0; i<result.length; i++){
					result[i][0]=c.getString(0);
					result[i][1]=c.getString(1);
					result[i][2]=c.getString(2);
				}
			}
		}
		close();
		return result;
	}
	public int getNbUtilisateurs(){
		int a = 0;
		open();
		Cursor c=mDb.rawQuery("SELECT COUNT(*) as Nb FROM "+this.VISITEUR_TABLE_NAME, null);
		c.moveToNext();
		a=c.getInt(0);
		close();
		return a;
	}
	
	public void viderLaTable(){
		open();
		mDb.execSQL("DELETE FROM "+BdActivity.VISITEUR_TABLE_NAME);
		Log.v("VIDAGE DE LA TABLE", "table vidée" );
		close();
	}
	
	public ArrayList<String> selectionnerTousLesVis(String visMat){
		ArrayList<String>resultat=null;
		open();
		Cursor c=mDb.rawQuery("SELECT "+this.VISITEUR_MATRICULE+", "+this.VISITEUR_NOM+", "+this.VISITEUR_PRENOM+", "+this.VISITEUR_ADRESSE+", "+this.VISITEUR_CP+", "+this.VISITEUR_VILLE+", "+this.VISITEUR_DATEEMB+", "+this.VISITEUR_SECCODE+", "+this.VISITEUR_LABCODE+" FROM "+this.VISITEUR_TABLE_NAME+" WHERE "+this.VISITEUR_MATRICULE+" != ?", new String[]{visMat});
		if(c!=null)
		{
			resultat=new ArrayList<String>();
			while(c.moveToNext())
			{
				String vis="";
				for(int i=0; i<c.getColumnCount(); i++)
				{				
					switch(i)
					{
						case 0:
							vis+=c.getString(i);
							break;
						case 4:
							vis+=";"+c.getLong(i);
							break;
						default:
							vis+=";"+c.getString(i);
							break;
					}
				}
				resultat.add(vis);
				
			}
		}
		close();
		return resultat;
	}
}
