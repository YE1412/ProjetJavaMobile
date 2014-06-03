package com.example.gsb;

public class Offre {
	private String vis_matricule;
	private int rap_num;
	private String depot_legal;
	private int quantite;
	
	//	Constructeur	//
	public Offre(String vismat, int rapnum, String depot, int qte){
		this.vis_matricule=vismat;
		this.rap_num=rapnum;
		this.depot_legal=depot;
		this.quantite=qte;
	}
	
	//	Accesseurs / Mutateurs //
	public void setVisMat(String mat){
		this.vis_matricule=mat;
	}
	public String getVisMat(){
		return this.vis_matricule;
	}
	
	public void setRapNum(int num){
		this.rap_num=num;
	}
	public int getRapNum(){
		return this.rap_num;
	}
	
	public void setDepotLegal(String depotL){
		this.depot_legal=depotL;
	}
	public String getDepotLegal(){
		return this.depot_legal;
	}
	
	public void setQuantite(int quant){
		this.quantite=quant;
	}
	public int getQuantite(){
		return this.quantite;
	}
}
