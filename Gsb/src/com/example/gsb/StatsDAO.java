package com.example.gsb;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class StatsDAO extends DAOBase{
	private String TABLE_NAME_RAPPORT=BdActivity.RAPPORT_TABLE_NAME;
	private String TABLE_NAME_PRATICIEN=BdActivity.PRATICIEN_TABLE_NAME;
	public StatsDAO(Context pContext) {
		super(pContext);
		// TODO Auto-generated constructor stub
	}
	
	public double recupStatsParVisiteur(int nbRapports, String clauses){
		double result = 0;
		open();
		mDb.execSQL("DROP VIEW IF EXISTS v1");
		mDb.execSQL("CREATE VIEW v1 as SELECT COUNT(*) as nbRapp FROM "+ TABLE_NAME_RAPPORT +" WHERE ("+clauses+")");
		Cursor c=mDb.rawQuery("SELECT v1.nbRapp FROM v1", null);
		if(c!=null)
		{
			c.moveToNext();
			if((nbRapports==0) || (c.getDouble(0)==0))
			{
				result=0;
			}
			else
			{
				result=nbRapports/c.getDouble(0);
			}
			Log.v("Pourcentage :", result+" %");
		}
		close();
		return result;	
	}
	
	public double recupStatsParPraticien(int nbRapports){
		double result = 0;
		open();
		mDb.execSQL("DROP VIEW IF EXISTS v2");
		mDb.execSQL("CREATE VIEW v2 as SELECT COUNT(*) as nbPrat FROM "+ TABLE_NAME_PRATICIEN);
		Cursor c=mDb.rawQuery("SELECT v2.nbPrat FROM v2", null);
		if(c!=null){
			c.moveToNext();
			result=nbRapports/c.getDouble(0);
		}
		close();
		return result;
	}

}
