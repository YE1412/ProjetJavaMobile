package com.example.gsb;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class MedicamentDAO extends DAOBase{
	public static final String MEDIC_KEY = BdActivity.MEDICAMENT_KEY;
	 public static final String MEDIC_DEPOT = BdActivity.MEDICAMENT_DEPOT;
	 public static final String MEDIC_NOMCOM = BdActivity.MEDICAMENT_NOMCOM;
	 public static final String MEDIC_FAMCODE = BdActivity.MEDICAMENT_CODEFAM;
	 public static final String MEDIC_COMPOS = BdActivity.MEDICAMENT_COMPO;
	 public static final String MEDIC_EFFETS = BdActivity.MEDICAMENT_EFFETS;
	 public static final String MEDIC_CONTREINDIC = BdActivity.MEDICAMENT_CONTRINDIC;
	 public static final String MEDIC_PRIX = BdActivity.MEDICAMENT_PRIX;
	 public static final String MEDIC_TABLE_NAME = BdActivity.MEDIC_TABLE_NAME;
	public MedicamentDAO(Context pContext) {
		super(pContext);
		// TODO Auto-generated constructor stub
	}

	public void ajouterMedicament(Medicament M){
		ContentValues value=new ContentValues();
		value.put(MedicamentDAO.MEDIC_DEPOT, M.getDepotL());
		value.put(MedicamentDAO.MEDIC_NOMCOM, M.getNomCom());
		value.put(MedicamentDAO.MEDIC_FAMCODE, M.getFamCode());
		value.put(MedicamentDAO.MEDIC_COMPOS, M.getComp());
		value.put(MedicamentDAO.MEDIC_EFFETS, M.getEffets());
		value.put(MedicamentDAO.MEDIC_CONTREINDIC, M.getContrInd());
		value.put(MedicamentDAO.MEDIC_PRIX, M.getPrix());
		open();
		Log.v("TEST", M.getDepotL());
		mDb.insert(MedicamentDAO.MEDIC_TABLE_NAME, null, value);
		close();
	}
	
	public String[][] Selectionner(String depotL){
		String[][] result = null;
		open();
		Cursor c=mDb.rawQuery("SELECT nomcommercial, codefamille, composition, effets, contreindic, prixech from Medicament Where depotlegal = ?", new String[]{depotL});
		if(c!=null){
			result=new String[c.getColumnCount()][2];
			c.moveToFirst();
				for(int i=0; i<result.length; i++){
					if(c.getColumnName(i).equals("nomcommercial")){
						result[i][0]="Nom Commercial :";
					}
					else if(c.getColumnName(i).equals("codefamille")){
						result[i][0]="Code Famille :";
					}
					else if(c.getColumnName(i).equals("composition")){
						result[i][0]="Composotion :";					
					}
					else if(c.getColumnName(i).equals("effets")){
						result[i][0]="Effets :";
					}
					else if(c.getColumnName(i).equals("contreindic")){
						result[i][0]="Contre-Indications :";
					}
					else{
						result[i][0]="Prix Echantillon :";
					}
					result[i][1]=c.getString(i);
					/*if(!c.isNull(i)){
						result[i][1]=c.getString(i);
						Log.v("DETAIL MEDICAMENT", "Donnée :"+result[i][1]);
					}
					else{
						Log.v("DETAIL MEDICAMENT", "Valeur NULL ! --> Nom colonne :"+result[i][0]);
					}*/
					//Log.v("DETAIL MEDICAMENT", "Nom colonne :"+c.getColumnName(i)+" Nom contenu :"+c.getString(i));
				}
			}
		close();
		return result;
	}
	
	public int getNbMedicaments(){
		int a = 0;
		open();
		Cursor c=mDb.rawQuery("SELECT COUNT(*) as Nb FROM "+MedicamentDAO.MEDIC_TABLE_NAME, null);
		c.moveToNext();
		a=c.getInt(0);
		close();
		return a;
	}
	
	public void viderLaTable(){
		open();
		mDb.execSQL("DELETE FROM "+MedicamentDAO.MEDIC_TABLE_NAME);
		Log.v("VIDAGE DE LA TABLE", "table vidée" );
		close();
	}
	
	public int majLaTable(Medicament M){
		int result=0;
		ContentValues value=new ContentValues();
		value.put(MedicamentDAO.MEDIC_DEPOT, M.getDepotL());
		value.put(MedicamentDAO.MEDIC_NOMCOM, M.getNomCom());
		value.put(MedicamentDAO.MEDIC_FAMCODE, M.getFamCode());
		value.put(MedicamentDAO.MEDIC_COMPOS, M.getComp());
		value.put(MedicamentDAO.MEDIC_EFFETS, M.getEffets());
		value.put(MedicamentDAO.MEDIC_CONTREINDIC, M.getContrInd());
		value.put(MedicamentDAO.MEDIC_PRIX, M.getPrix());
		open();
		result=mDb.update(MedicamentDAO.MEDIC_TABLE_NAME, value, null, null);
		close();
		return result;
	}
	
	public ArrayList<Medicament> recupererMedicaments(String clause, String[] depotL){
		ArrayList<Medicament> medicaments = null;
		Medicament med=null;
		open();
		Cursor c;
		if(clause==null){
			c=mDb.rawQuery("SELECT * FROM "+MedicamentDAO.MEDIC_TABLE_NAME, null);
		}
		else{
			clause=clause.replaceAll(" ", " AND "+MedicamentDAO.MEDIC_DEPOT+" != ");
			Log.v("Préparation", "Medicaments mise en forme pour requete : "+clause);
			c=mDb.rawQuery("SELECT * FROM "+MedicamentDAO.MEDIC_TABLE_NAME+" WHERE "+MedicamentDAO.MEDIC_DEPOT+" != "+clause, depotL);
		}
		if(c!=null){
		Log.v("declar", "nb meds "+c.getCount());
		medicaments=new ArrayList<Medicament>();
		while(c.getPosition()<c.getCount()-1){
				Log.v("declar", "position c "+c.getPosition());
				c.moveToNext();
				String[] donnee=new String[c.getColumnCount()];
				for(int i=0; i<c.getColumnCount(); i++){
					donnee[i]=c.getString(i);
				}
				med=new Medicament(donnee[1], donnee[2], donnee[3], donnee[4], donnee[5], donnee[6], donnee[7]);
				medicaments.add(med);
					//Log.v("DETAIL MEDICAMENT", "Nom colonne :"+c.getColumnName(i)+" Nom contenu :"+c.getString(i));
			}
			
		}
		close();
		return medicaments;
	}
		
}
