package com.example.gsb;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
public class TestCursorActivity extends Activity{
	ListView liste=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_cursor);
		
		// On récupère l'intent qui a lancé cette activité
	    Intent i = getIntent();
		String[] donnees=i.getStringArrayExtra("com.example.gsb.NOM");
		liste = (ListView) findViewById(R.id.listView1);
	    List<String> exemple = new ArrayList<String>();
	    for(int j=0; j<donnees.length; j++){
	    	exemple.add(donnees[j]);
	    }
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exemple);
	    liste.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_cursor, menu);
		return true;
	}

}
