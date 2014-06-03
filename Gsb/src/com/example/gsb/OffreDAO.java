package com.example.gsb;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class OffreDAO extends DAOBase{
	private String TABLE_NAME_OFFRE=BdActivity.OFFRE_TABLE_NAME;
	//private String KEY_OFFRE=BdActivity.OFFRE_KEY;
	private String VIS_MAT_OFFRE=BdActivity.OFFRE_MATVIS;
	private String RAP_NUM_OFFRE=BdActivity.OFFRE_RAPNUM;
	private String MED_DL_OFFRE=BdActivity.OFFRE_MEDDEPOT;
	private String QTE_OFFRE=BdActivity.OFFRE_QTE;
	
	public OffreDAO(Context pContext) {
		super(pContext);
		// TODO Auto-generated constructor stub
	}
	
	public void ajoutOffre(Offre O){
		ContentValues value=new ContentValues();
		value.put(this.VIS_MAT_OFFRE, O.getVisMat());
		value.put(this.RAP_NUM_OFFRE, O.getRapNum());
		value.put(this.MED_DL_OFFRE, O.getDepotLegal());
		value.put(this.QTE_OFFRE, O.getQuantite());
		
		open();
		Log.v("TEST OFFRE", O.getDepotLegal());
		mDb.insert(this.TABLE_NAME_OFFRE, null, value);
		close();
	}
	
	public void supprimerOffre(int numRapp){
		mDb.delete(this.TABLE_NAME_OFFRE, this.RAP_NUM_OFFRE + " = ?", new String[]{String.valueOf(numRapp)});
	}
	
	public void majOffre(Offre vaj, int numRappAMaj){
		ContentValues value=new ContentValues();
		value.put(this.VIS_MAT_OFFRE, vaj.getVisMat());
		value.put(this.RAP_NUM_OFFRE, vaj.getRapNum());
		value.put(this.MED_DL_OFFRE, vaj.getDepotLegal());
		value.put(this.QTE_OFFRE, vaj.getQuantite());
		
		open();
		mDb.update(TABLE_NAME_OFFRE, value, RAP_NUM_OFFRE+"=?", new String[]{String.valueOf(numRappAMaj)});
		close();
	}
	
	public void viderLaTable(){
		open();
		mDb.execSQL("DELETE FROM "+BdActivity.OFFRE_TABLE_NAME);
		Log.v("VIDAGE DE LA TABLE", "table vidée" );
		close();
	}
	
	public ArrayList<Offre> Selectionner(int NumRapp, String visMat){
		ArrayList<Offre> result = null;
		open();
		String requete="SELECT "+this.MED_DL_OFFRE+", "+this.QTE_OFFRE+" FROM "+BdActivity.OFFRE_TABLE_NAME+" WHERE "+this.RAP_NUM_OFFRE+" = ? AND "+this.VIS_MAT_OFFRE+" = ?";
		Cursor c=mDb.rawQuery(requete, new String[]{String.valueOf(NumRapp), visMat});
		Log.v("Requete Offres", requete);
		if(c!=null){
			result=new ArrayList<Offre>();
			while(c.moveToNext()){ 
				int qte = 0;
				String depot="";
				//for(int i=0; i<c.getCount(); i++){ 
					for(int j=0; j<c.getColumnCount(); j++){
						switch(j){
						case 0:
							depot= c.getString(j);
							break;
						case 1:
							qte=c.getInt(j);
							break;
						default:
							break;						
						}
					}
					Offre off=new Offre(visMat, NumRapp, depot, qte);
					result.add(off);
					//Log.v("Offres Enr", off.getDepotLegal());
				//}
			}
		}
		close();
		return result;
	}

}
