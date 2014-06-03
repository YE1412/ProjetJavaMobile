package com.example.gsb;

public class Medicament {
	private String depotlegal;
	private String nomcom;
	private String famcode;
	private String composition;
	private String effet;
	private String contreind;
	private String prix;
	
	public Medicament(String depotLegal, String nomCommercial, String familleCode, String composotion, String effets, String contreIndications, String prx){
		this.depotlegal=depotLegal;
		this.nomcom=nomCommercial;
		this.famcode=familleCode;
		this.composition=composotion;
		this.effet=effets;
		this.contreind=contreIndications;
		this.prix=prx;
	}
	
	public String getDepotL(){
		return depotlegal;
		
	}
	
	public String getNomCom(){
		return nomcom;
		
	}
	
	public String getFamCode(){
		return famcode;
		
	}
	
	public String getComp(){
		return composition;
		
	}
	
	public String getEffets(){
		return effet;
		
	}
	
	public String getContrInd(){
		return contreind;
		
	}
	
	public String getPrix(){
		return prix;
		
	}
}
