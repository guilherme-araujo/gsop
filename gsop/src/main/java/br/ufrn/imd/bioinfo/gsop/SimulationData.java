package br.ufrn.imd.bioinfo.gsop;

import java.util.List;

public class SimulationData {

	private int initialPopulation;
	private double birthRate;
	private double deathRate;
	private List<IndType> types;
	private int cycles;
	
	public int getInitialPopulation() {
		return initialPopulation;
	}
	public void setInitialPopulation(int initialPopulation) {
		this.initialPopulation = initialPopulation;
	}
	public double getBirthRate() {
		return birthRate;
	}
	public void setBirthRate(double birthRate) {
		this.birthRate = birthRate;
	}
	public double getDeathRate() {
		return deathRate;
	}
	public void setDeathRate(double deathRate) {
		this.deathRate = deathRate;
	}
	public List<IndType> getTypes() {
		return types;
	}
	public void setTypes(List<IndType> types) {
		this.types = types;
	}
	public int getCycles() {
		return cycles;
	}
	public void setCycles(int cycles) {
		this.cycles = cycles;
	}
	
	
	
}
