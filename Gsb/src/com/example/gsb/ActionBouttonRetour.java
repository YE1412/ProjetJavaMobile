package com.example.gsb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class ActionBouttonRetour extends Activity implements View.OnClickListener{
	private Button button;
	private String[] arguments;
	private Context context;
	public static final String EXTRA_FLUX = "com.example.android.authent.extra.donnees";
	public ActionBouttonRetour(Button bout, String[] args, Context appContext){
		button=bout;
		arguments=args;
		context=appContext;
	}
	
	public void retour(){
		button.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent Menu;
		if(arguments[3].equals("Visiteur")){
			Menu = new Intent(context, MenuV.class);
		}
		else{
			Menu = new Intent(context, MenuDir.class);
		}
		//Nettoyage de la tâche antérieure//
		Menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		//Activation de la tâche postérieure//
		Menu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//Lancement de la tâche//
		Menu.putExtra(EXTRA_FLUX, arguments);
		context.startActivity(Menu);
	}
}
