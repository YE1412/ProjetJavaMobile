package com.example.gsb;

public class Praticien {
	private int num;
	private String nom;
	private String prenom;
	private String adresse;
	private long cp;
	private String ville;
	private double coeffNotoriete;
	private String typeCode;
	
	public Praticien(int numero, String frstname, String lstname, String address, long cp, String town, double coeff, String codetyp){
		this.num=numero;
		this.nom=frstname;
		this.prenom=lstname;
		this.adresse=address;
		this.cp=cp;
		this.ville=town;
		this.coeffNotoriete=coeff;
		this.typeCode=codetyp;
	}
	
	//Getters + Setters//
	public int getPraNum(){
		return this.num;
	}
	public void setPraNum(int numer){
		this.num=numer;
	}
	
	public String getPraNom(){
		return this.nom;
	}
	public void setPraNum(String name){
		this.nom=name;
	}
	
	public String getPraPrenom(){
		return this.prenom;
	}
	public void setPraPrenom(String Prenom){
		this.prenom=Prenom;
	}
	
	public String getPraAdresse(){
		return this.adresse;
	}
	public void setPraAdresse(String Adresse){
		this.adresse=Adresse;
	}
	
	public long getPraCp(){
		return this.cp;
	}
	public void setPraCp(long CP){
		this.cp=CP;
	}
	
	public String getPraVille(){
		return this.ville;
	}
	public void setPraVille(String Ville){
		this.ville=Ville;
	}
	
	public double getPraCoeff(){
		return this.coeffNotoriete;
	}
	public void setPraCoeff(double coef){
		this.coeffNotoriete=coef;
	}
	
	public String getPraCodeTyp(){
		return this.typeCode;
	}
	public void setPraCodeTyp(String typecode){
		this.typeCode=typecode;
	}
}
