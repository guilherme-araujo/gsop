package br.ufrn.imd.bioinfo.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.ufrn.imd.bioinfo.gsop.App;
import br.ufrn.imd.bioinfo.gsop.IndType;
import br.ufrn.imd.bioinfo.gsop.SimulationData;

@ManagedBean(name="simulationBean")
@SessionScoped
public class SimulationBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String simResult;
	
	private SimulationData simulationData;
	
	private double fitness1;
	private double fitness2;
		
	@PostConstruct
	public void init() {
		fitness1 = 1.0;
		fitness2 = 1.05;
		simulationData = new SimulationData();
		simulationData.setBirthRate(1.04);
		simulationData.setDeathRate(1.04);
		simulationData.setInitialPopulation(1000);
		simulationData.setCycles(40);
		List<IndType> types = new ArrayList<IndType>();
		IndType typeA = new IndType();
		typeA.setInitialFitness(fitness1);
		typeA.setInitialRatio(0.5);
		typeA.setType("A");
		types.add(typeA);
		
		IndType typeB = new IndType();
		typeB.setInitialFitness(fitness2);
		typeB.setInitialRatio(0.5);
		typeB.setType("B");
		types.add(typeB);
		
		simulationData.setTypes(types);
	}
	
	/*public SimulationBean() {
		fitness1 = 1.0;
		fitness2 = 1.05;
		simulationData = new SimulationData();
		simulationData.setBirthRate(1.04);
		simulationData.setDeathRate(1.02);
		simulationData.setInitialPopulation(1000);
		List<IndType> types = new ArrayList<IndType>();
		
		simulationData.setTypes(types);
	}*/
	
	public void runSim() {
		simulationData.setDeathRate(simulationData.getBirthRate());
		simulationData.getTypes().get(0).setInitialFitness(fitness1);
		simulationData.getTypes().get(1).setInitialFitness(fitness2);
		simResult = App.runSim(simulationData);
		
		//return "success";
	}

	public String getSimResult() {
		return simResult;
	}

	public void setSimResult(String simResult) {
		this.simResult = simResult;
	}

	public double getFitness1() {
		return fitness1;
	}

	public void setFitness1(double fitness1) {
		this.fitness1 = fitness1;
	}

	public double getFitness2() {
		return fitness2;
	}

	public void setFitness2(double fitness2) {
		this.fitness2 = fitness2;
	}

	public SimulationData getSimulationData() {
		return simulationData;
	}

	public void setSimulationData(SimulationData simulationData) {
		this.simulationData = simulationData;
	}
		
}
