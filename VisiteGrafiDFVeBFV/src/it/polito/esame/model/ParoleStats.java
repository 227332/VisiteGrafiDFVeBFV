package it.polito.esame.model;

public class ParoleStats {
	
	private int totParole;
	private int totColl; //numero collegamenti
	private Parola parola; //parola col max numero di collegamenti
	private int degP; //numero di collegamenti che ha parola
	
	public ParoleStats(int totParole, int totColl, Parola parola) {
		super();
		this.totParole = totParole;
		this.totColl = totColl;
		this.parola = parola;
	}
	
	public ParoleStats() {
		super();
		this.totParole = 0;
		this.totColl = 0;
		this.parola = null;
	}

	public int getTotParole() {
		return totParole;
	}

	public void setTotParole(int totParole) {
		this.totParole = totParole;
	}
	
	public int getDegP() {
		return degP;
	}

	public void setDegP(int n) {
		degP = n;
	}
	

	public int getTotColl() {
		return totColl;
	}

	public void setTotColl(int totColl) {
		this.totColl = totColl;
	}

	public Parola getParola() {
		return parola;
	}

	public void setParola(Parola parola) {
		this.parola = parola;
	}
	
	
	

}
