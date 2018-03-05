package br.ufrn.imd.bioinfo.gsop;

public class IndType {
	
	private String type;
	private double initialFitness;
	private double initialRatio;
	
	public IndType() {
		initialFitness = 1.0;
		initialRatio = 0.5;
		
	}
	
	public IndType(String t) {
		type = t;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getInitialFitness() {
		return initialFitness;
	}

	public void setInitialFitness(double initialFitness) {
		this.initialFitness = initialFitness;
	}

	public double getInitialRatio() {
		return initialRatio;
	}

	public void setInitialRatio(double initialRatio) {
		this.initialRatio = initialRatio;
	}		
	
	
	

}
