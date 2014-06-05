package com.example.gsb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class ConnexionHTTP {
	private final static String hostWebDev="webdev.cluster1.easy-hebergement.net";
	private HttpPost httpLink;
	private String Path;
	private HttpClient httpClient;
	private ArrayList<BasicNameValuePair> donnees;
	public ConnexionHTTP(ArrayList<BasicNameValuePair> donnee) {
		//Client par défaut//
		String host=hostWebDev, pathFile="android_login_api/index.php";
		this.httpClient=new DefaultHttpClient();
		this.Path="http://"+host+"/"+pathFile;
		this.httpLink=new HttpPost(Path);
		this.donnees=donnee;
	}
	
	public StringBuilder getResponse(){
		StringBuilder sb=null;
		try{
			httpLink.setEntity(new UrlEncodedFormEntity(donnees));
			 HttpResponse response = httpClient.execute(httpLink);
			 HttpEntity entity = response.getEntity();
			 InputStream is = entity.getContent();
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
	            sb = new StringBuilder();
	            String line = reader.readLine();
	            sb.append(line + "\n");
	            is.close();
		}
		 catch (ClientProtocolException e) {
	            // TODO Auto-generated catch block
	        	Log.e("ClientProtocolExeption", e.getMessage());
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	        	Log.e("IO Exeption", e.getMessage());
	        	return sb;
	        }
	        catch(Exception e){
	        	
	        	Log.v("Exeption", e.toString());
	        	e.printStackTrace();
	        }
		Log.v("RESULTAT REQUETE", sb.toString());
		return sb;
		
	}
}
