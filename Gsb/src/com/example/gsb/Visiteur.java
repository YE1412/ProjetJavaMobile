package com.example.gsb;

public class Visiteur {
	private long id;
	private String matricule;
	private String nom;
	private String prenom;
	private String adresse;
	private long cp;
	private String ville;
	private String date_emb;
	private String sec_code;
	private String lab_code;
	
	/*	Constructeur	*/
	public Visiteur(String matricule, String nom, String prenom, String adresse, long cp, String ville, String date_emb, String sec_code, String lab_code){
		this.matricule=matricule;
		this.nom=nom;
		this.prenom=prenom;
		this.adresse=adresse;
		this.cp=cp;
		this.ville=ville;
		this.date_emb=date_emb;
		this.sec_code=sec_code;
		this.lab_code=lab_code;
	}
	/*	Accesseurs/Mutateur	*/
	public long getId(){
		return this.id;
	}
	public void setId(long id){
		this.id=id;
	}
	
	public String getMatricule(){
		return this.matricule;
	}
	public void setMatricule(String matricule){
		this.matricule=matricule;
	}
	
	public String getNom(){
		return this.nom;
	}
	public void setNom(String nom){
		this.nom=nom;
	}
	
	public String getPrenom(){
		return this.prenom;
	}
	public void setPrenom(String prenom){
		this.prenom=prenom;
	}
	
	public String getAdresse(){
		return this.adresse;
	}
	public void setAdresse(String adresse){
		this.adresse=adresse;
	}
	
	public long getCp(){
		return this.cp;
	}
	public void setCp(long cp){
		this.cp=cp;
	}
	
	public String getVille(){
		return this.ville;
	}
	public void setVille(String ville){
		this.ville=ville;
	}
	
	public String getDateEmb(){
		return this.date_emb;
	}
	public void setDateEmb(String dateEmb){
		this.date_emb=dateEmb;
	}
	
	public String getSecCode(){
		return this.sec_code;
	}
	public void setSecCode(String seccode){
		this.sec_code=seccode;
	}
	
	public String getLabCode(){
		return this.lab_code;
	}
	public void setLabCode(String labcode){
		this.lab_code=labcode;
	}
}
