package com.example.gsb;

public class Rapport {
	private String visMatricule;
	private int num;
	private int praNum;
	private String date;
	private String bilan;
	private String motif;
	
	public Rapport(String visMat, int n, int pranum, String date, String bilan, String motif){
		this.visMatricule=visMat;
		this.num=n;
		this.praNum=pranum;
		this.date=date;
		this.bilan=bilan;
		this.motif=motif;
		
	}
	
	/*	Accesseurs/Mutateur	*/
	public String getVisMat(){
		return this.visMatricule;
	}
	public void setVisMat(String mat){
		this.visMatricule=mat;
	}
	
	public int getNum(){
		return this.num;
	}
	public void setNum(int numero){
		this.num=numero;
	}
	
	public int getPraNum(){
		return this.praNum;
	}
	public void setPraNum(int pranumero){
		this.praNum=pranumero;
	}
	
	public String getDate(){
		return this.date;
	}
	public void setDate(String dat){
		this.date=dat;
	}
	
	public String getBilan(){
		return this.bilan;
	}
	public void setBilan(String bil){
		this.bilan=bil;
	}
	
	public String getMotif(){
		return this.motif;
	}
	public void setMotif(String mot){
		this.motif=mot;
	}
	
	
}
