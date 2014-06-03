package com.example.gsb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DAOBase {
	// Nous sommes � la premi�re version de la base
	  // Si je d�cide de la mettre � jour, il faudra changer cet attribut
	  protected final static int VERSION = 5;
	  // Le nom du fichier qui repr�sente ma base
	  protected final static String NOM = "gsb_ppe4.db";
	    
	  protected SQLiteDatabase mDb = null;
	  protected BdActivity mHandler = null;
	    
	  public DAOBase(Context pContext) {
	    this.mHandler = new BdActivity(pContext, NOM, null, VERSION);
	    
	  }
	    
	  public SQLiteDatabase open() {
	    // Pas besoin de fermer la derni�re base puisque getWritableDatabase s'en charge
	    mDb = mHandler.getWritableDatabase();
	    //mDb = SQLiteDatabase.openDatabase(NOM, null, SQLiteDatabase.OPEN_READWRITE);
	    return mDb;
	  }
	    
	  public void close() {
	    mDb.close();
	  }
	    
	  public SQLiteDatabase getDb() {
	    return mDb;
	  }
}
