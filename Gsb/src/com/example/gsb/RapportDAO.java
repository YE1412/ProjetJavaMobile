package com.example.gsb;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class RapportDAO extends DAOBase{
	private String RAPPORT_TABLE_NAME = BdActivity.RAPPORT_TABLE_NAME;
	private String RAPPORT_VISMATRICULE = BdActivity.RAPPORT_VISMATRICULE;
	private String RAPPORT_NUM = BdActivity.RAPPORT_NUM;
	private String RAPPORT_PRANUM = BdActivity.RAPPORT_PRANUM;
	private String RAPPORT_DATE = BdActivity.RAPPORT_DATE;
	private String RAPPORT_BILAN = BdActivity.RAPPORT_BILAN;
	private String RAPPORT_MOTIF = BdActivity.RAPPORT_MOTIF;
	
	public RapportDAO(Context pContext) {
		super(pContext);
		// TODO Auto-generated constructor stub
	}

	public void ajouterRapport(Rapport R){
		ContentValues value=new ContentValues();
		value.put(this.RAPPORT_VISMATRICULE, R.getVisMat());
		value.put(this.RAPPORT_NUM, R.getNum());
		value.put(this.RAPPORT_PRANUM, R.getPraNum());
		value.put(this.RAPPORT_DATE, R.getDate());
		value.put(this.RAPPORT_BILAN, R.getBilan());
		value.put(this.RAPPORT_MOTIF, R.getMotif());
		open();
		Log.v("TEST", R.getDate());
		mDb.insert(this.RAPPORT_TABLE_NAME, null, value);
		close();
	}
	public void supprimerRapport(int num){
		mDb.delete(this.RAPPORT_TABLE_NAME, this.RAPPORT_NUM + " = ?", new String[]{String.valueOf(num)});
	}
	public void majRapport(Rapport vaj, int numRappAMaj){
		ContentValues value=new ContentValues();
		value.put(this.RAPPORT_VISMATRICULE, vaj.getVisMat());
		value.put(this.RAPPORT_NUM, vaj.getNum());
		value.put(this.RAPPORT_PRANUM, vaj.getPraNum());
		value.put(this.RAPPORT_DATE, vaj.getDate());
		value.put(this.RAPPORT_BILAN, vaj.getBilan());
		value.put(this.RAPPORT_MOTIF, vaj.getMotif());
		open();
		mDb.update(RAPPORT_TABLE_NAME, value, RAPPORT_NUM+"=?", new String[]{String.valueOf(numRappAMaj)});
		close();
	}
	public ArrayList<Rapport> SelectionnerParDate(String Date, String visMat){
		ArrayList<Rapport> result = null;
		open();
		Cursor c=mDb.rawQuery("SELECT "+this.RAPPORT_NUM+", "+this.RAPPORT_PRANUM+", "+this.RAPPORT_DATE +", "+this.RAPPORT_BILAN +", "+this.RAPPORT_MOTIF+" FROM "+RAPPORT_TABLE_NAME+" WHERE "+this.RAPPORT_DATE+"=? AND "+this.RAPPORT_VISMATRICULE+"=?", new String[]{Date, visMat});
		if(c!=null){
			result=new ArrayList<Rapport>();
			while(c.moveToNext()){ 
				int rapNum = 0, praNum=0;
				String rapDate="", rapBilan="", rapMotif="";
				//for(int i=0; i<c.getCount(); i++){ 
					for(int j=0; j<c.getColumnCount(); j++){
						switch(j){
						case 0:
							rapNum= c.getInt(j);
							break;
						case 1:
							praNum=c.getInt(j);
							break;
						case 2:
							rapDate=c.getString(j);
							break;
						case 3:
							rapBilan=c.getString(j);
							break;
						case 4:
							rapMotif=c.getString(j);
							break;
						default:
							break;						
						}
					}
					Rapport rapport=new Rapport(visMat, rapNum, praNum, rapDate, rapBilan, rapMotif);
					result.add(rapport);
				//}
			}
		}
		close();
		return result;
	}
	public ArrayList<Rapport> SelectionnerParVis(String visMat){
		ArrayList<Rapport> result = null;
		open();
		Cursor c=mDb.rawQuery("SELECT "+this.RAPPORT_NUM+", "+this.RAPPORT_PRANUM+", "+this.RAPPORT_DATE +", "+this.RAPPORT_BILAN +", "+this.RAPPORT_MOTIF+" FROM "+RAPPORT_TABLE_NAME+" WHERE "+this.RAPPORT_VISMATRICULE+"=?", new String[]{visMat});
		if(c!=null){
			result=new ArrayList<Rapport>();
			while(c.moveToNext()){ 
				int rapNum = 0, praNum=0;
				String rapDate="", rapBilan="", rapMotif="";
				//for(int i=0; i<c.getCount(); i++){ 
					for(int j=0; j<c.getColumnCount(); j++){
						switch(j){
						case 0:
							rapNum= c.getInt(j);
							break;
						case 1:
							praNum=c.getInt(j);
							break;
						case 2:
							rapDate=c.getString(j);
							break;
						case 3:
							rapBilan=c.getString(j);
							break;
						case 4:
							rapMotif=c.getString(j);
							break;
						default:
							break;						
						}
					}
					Rapport rapport=new Rapport(visMat, rapNum, praNum, rapDate, rapBilan, rapMotif);
					result.add(rapport);
				//}
			}
		}
		close();
		return result;
	}
	public int getNbRapports(){
		int a = 0;
		open();
		Cursor c=mDb.rawQuery("SELECT COUNT(*) as Nb FROM "+this.RAPPORT_TABLE_NAME, null);
		c.moveToNext();
		a=c.getInt(0);
		close();
		return a;
	}
	public int getNbRapportsParVis(String visMat){
		int a = 0;
		open();
		Cursor c=mDb.rawQuery("SELECT COUNT(*) as Nb FROM "+this.RAPPORT_TABLE_NAME +" WHERE "+this.RAPPORT_VISMATRICULE+" =?", new String[]{visMat});
		c.moveToNext();
		a=c.getInt(0);
		close();
		return a;
	}
	
	public void viderLaTable(){
		open();
		mDb.execSQL("DELETE FROM "+BdActivity.RAPPORT_TABLE_NAME);
		Log.v("VIDAGE DE LA TABLE", "table vidée" );
		close();
	}
	
	public ArrayList<String> recupererDates(String visMat){
		ArrayList<String> result = null;
		open();
		Cursor c=mDb.rawQuery("SELECT DISTINCT "+this.RAPPORT_DATE+" FROM "+RAPPORT_TABLE_NAME+" WHERE "+this.RAPPORT_VISMATRICULE+"=?", new String[]{visMat});
		if(c!=null){
			result=new ArrayList<String>();
			while(c.moveToNext()){ 
				result.add(c.getString(0));
			}
		}
		close();
		return result;
	}
	
	public ArrayList<String> recupererVisiteurs(String labCode, String secCode, String visMat){
		ArrayList<String> result = null;
		open();
		Cursor c;
		if(secCode!=null){
			c=mDb.rawQuery("SELECT DISTINCT v."+BdActivity.VISITEUR_MATRICULE+", v."+BdActivity.VISITEUR_NOM+", v."+BdActivity.VISITEUR_PRENOM+" FROM "+BdActivity.VISITEUR_TABLE_NAME+" as v WHERE v."+BdActivity.VISITEUR_MATRICULE+" IN (SELECT "+BdActivity.VISITEUR_MATRICULE+" FROM "+BdActivity.RAPPORT_TABLE_NAME+")"+
					"AND v."+BdActivity.VISITEUR_LABCODE+"=? AND v."+BdActivity.VISITEUR_SECCODE+"=? AND v."+BdActivity.VISITEUR_MATRICULE+" != ? ", new String[]{labCode, secCode, visMat});
		}
		else
		{
			c=mDb.rawQuery("SELECT DISTINCT v."+BdActivity.VISITEUR_MATRICULE+", v."+BdActivity.VISITEUR_NOM+", v."+BdActivity.VISITEUR_PRENOM+" FROM "+BdActivity.VISITEUR_TABLE_NAME+" as v WHERE v."+BdActivity.VISITEUR_MATRICULE+" IN (SELECT "+BdActivity.VISITEUR_MATRICULE+" FROM "+BdActivity.RAPPORT_TABLE_NAME+")"+
					"AND v."+BdActivity.VISITEUR_LABCODE+"=? AND v."+BdActivity.VISITEUR_MATRICULE+" != ? ", new String[]{labCode, visMat});
		}
		if(c!=null){
		result=new ArrayList<String>();
			while(c.moveToNext()){ 
					result.add("-"+c.getString(0)+"- "+c.getString(1)+" "+c.getString(2));
			}
		}
		close();
		return result;
	}
}
