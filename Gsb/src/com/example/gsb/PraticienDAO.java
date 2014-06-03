package com.example.gsb;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class PraticienDAO extends DAOBase{
	private String PRATICIEN_NUM=BdActivity.PRATICIEN_NUM;
	private String PRATICIEN_NOM=BdActivity.PRATICIEN_NOM;
	private String PRATICIEN_PRENOM=BdActivity.PRATICIEN_PRENOM;
	private String PRATICIEN_ADRESSE=BdActivity.PRATICIEN_ADRESSE;
	private String PRATICIEN_CP=BdActivity.PRATICIEN_CP;
	private String PRATICIEN_VILLE=BdActivity.PRATICIEN_VILLE;
	private String PRATICIEN_COEFFNOTO=BdActivity.PRATICIEN_COEFFNOT;
	private String PRATICIEN_TYPCODE=BdActivity.PRATICIEN_TYPCODE;
	private String PRATICIEN_TABLE=BdActivity.PRATICIEN_TABLE_NAME;
	
	public PraticienDAO(Context pContext) {
		super(pContext);
		// TODO Auto-generated constructor stub
}
	
	public void ajouterPraticien(Praticien P){
		ContentValues value=new ContentValues();
		value.put(this.PRATICIEN_NUM, P.getPraNum());
		value.put(this.PRATICIEN_NOM, P.getPraNom());
		value.put(this.PRATICIEN_PRENOM, P.getPraPrenom());
		value.put(this.PRATICIEN_ADRESSE, P.getPraAdresse());
		value.put(this.PRATICIEN_CP, P.getPraCp());
		value.put(this.PRATICIEN_VILLE, P.getPraVille());
		value.put(this.PRATICIEN_COEFFNOTO, P.getPraCoeff());
		value.put(this.PRATICIEN_TYPCODE, P.getPraCodeTyp());
		open();
		Log.v("TEST","num Prat :"+ P.getPraNum());
		mDb.insert(this.PRATICIEN_TABLE, null, value);
		close();
	}
	public void supprimerPraticien(int num){
		mDb.delete(this.PRATICIEN_TABLE, this.PRATICIEN_NUM + " = ?", new String[]{String.valueOf(num)});
	}
	public void majPraticien(Praticien vaj, int numPratAMaj){
		ContentValues value=new ContentValues();
		value.put(this.PRATICIEN_NUM, vaj.getPraNum());
		value.put(this.PRATICIEN_NOM, vaj.getPraNom());
		value.put(this.PRATICIEN_PRENOM, vaj.getPraPrenom());
		value.put(this.PRATICIEN_ADRESSE, vaj.getPraAdresse());
		value.put(this.PRATICIEN_CP, vaj.getPraCp());
		value.put(this.PRATICIEN_VILLE, vaj.getPraVille());
		value.put(this.PRATICIEN_COEFFNOTO, vaj.getPraCoeff());
		value.put(this.PRATICIEN_TYPCODE, vaj.getPraCodeTyp());
		open();
		mDb.update(PRATICIEN_TABLE, value, PRATICIEN_NUM+"=?", new String[]{String.valueOf(numPratAMaj)});
		close();
	}
	public String[] Selectionner(int Num){
		String[] result = null;
		open();
		Cursor c=mDb.rawQuery("SELECT "+this.PRATICIEN_NUM+", "+this.PRATICIEN_NOM+", "+this.PRATICIEN_PRENOM +", "+this.PRATICIEN_ADRESSE +", "+this.PRATICIEN_CP+", "+this.PRATICIEN_VILLE+", "+this.PRATICIEN_COEFFNOTO+", "+this.PRATICIEN_TYPCODE+" FROM "+PRATICIEN_TABLE+" WHERE "+this.PRATICIEN_NUM+"=? ", new String[]{String.valueOf(Num)});
		if(c!=null){
			result=new String[c.getColumnCount()];
			while(c.moveToNext()){ 
					for(int j=0; j<result.length; j++){
						result[j]=c.getString(j);
					}	
			}
		}
		close();
		return result;
	}
	public int getNbPraticien(){
		int a = 0;
		open();
		Cursor c=mDb.rawQuery("SELECT COUNT(*) as Nb FROM "+this.PRATICIEN_TABLE, null);
		c.moveToNext();
		a=c.getInt(0);
		close();
		return a;
	}
	public ArrayList<Praticien> recupererTousLesPrat(){
		ArrayList<Praticien> result = null;
		open();
		Cursor cur=mDb.rawQuery("SELECT * FROM "+PRATICIEN_TABLE+" ORDER BY "+this.PRATICIEN_NOM+" ASC", null);
		if(cur!=null){
			result=new ArrayList<Praticien>();
			while(cur.getPosition()<cur.getCount()-1){
				Log.v("declar", "position c "+cur.getPosition());
				cur.moveToNext();
				String[] donnee=new String[cur.getColumnCount()];
				for(int i=0; i<cur.getColumnCount(); i++){
					donnee[i]=cur.getString(i);
				}
				Praticien prat=new Praticien(Integer.valueOf(donnee[1]),
						donnee[2], donnee[3], donnee[4], Long.valueOf(donnee[5]), donnee[6], Double.valueOf(donnee[7]), donnee[8]);
				result.add(prat);
					//Log.v("DETAIL MEDICAMENT", "Nom colonne :"+c.getColumnName(i)+" Nom contenu :"+c.getString(i));
			}
			
		}
		close();
		return result;
	}
	
	public void viderLaTable(){
		open();
		mDb.execSQL("DELETE FROM "+BdActivity.PRATICIEN_TABLE_NAME);
		Log.v("VIDAGE DE LA TABLE", "table vidée" );
		close();
	}
}
