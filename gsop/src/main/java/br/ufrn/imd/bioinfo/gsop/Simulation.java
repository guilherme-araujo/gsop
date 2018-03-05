package br.ufrn.imd.bioinfo.gsop;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import br.ufrn.imd.bioinfo.gsop.model.Node;

public class Simulation {
	
	private static SimulationData simulationData;
	
	public static void cycle(List<Node> nodes) {
		
		Random gerador = new Random();
		
		int chosen = gerador.nextInt(nodes.size());
		
		List<Integer> roleta = new LinkedList<>();
		
		for(int i = 0; i < nodes.size(); i++){
			if(i==chosen) continue;
			for(int j = 0; j < nodes.get(i).getVal(); j++){
				roleta.add(i);
			}
		}
		
		int chosen2 = gerador.nextInt(roleta.size());
		Node n = new Node();
		n.setHash(nodes.get(chosen).getHash());
		n.setType(nodes.get(roleta.get(chosen2)).getType());
		n.setVal(nodes.get(roleta.get(chosen2)).getVal());
		nodes.set(chosen, n);
		
	}
	
	public static void cycleV2(List<Node> nodes, double birthRate, double deathRate) {
		
		Random gerador = new Random();
		
		int dieCount = (int)((double)nodes.size()*(deathRate-1));
		int birthCount = (int)((double)nodes.size()*(birthRate-1));
		
		//deaths
		
		//System.out.println(dieCount);
		for(int i = 0; i < dieCount; i++) {
			int chosen = gerador.nextInt(nodes.size());
			nodes.remove(chosen);
		}
		
		//births
		
		for(int a = 0; a < birthCount; a++) {
			
			int count_a = 0, count_b = 0;
			
			count_a = typeCount("A", nodes);
			count_b = typeCount("B", nodes);
			
			double chanceA = ((double)count_a)*simulationData.getTypes().get(0).getInitialFitness();
			double chanceB = ((double)count_b)*simulationData.getTypes().get(1).getInitialFitness();
			
			int chosen = gerador.nextInt((int)(chanceA+chanceB));
			Node n = new Node();
			if(chosen < (int)chanceA) {
				n.setType(simulationData.getTypes().get(0).getType());
				n.setFitness(simulationData.getTypes().get(0).getInitialFitness());
			}else {
				n.setType(simulationData.getTypes().get(1).getType());
				n.setFitness(simulationData.getTypes().get(1).getInitialFitness());
			}
			nodes.add(n);
		
		}
		
	}
	
	public static int typeCount(String type, List<Node> nodes) {
		int count = 0;
    	
    	for(Node n : nodes) {
    		if(n.getType().compareTo(type)==0) {
    			count++;
    		}  		
    	}
    	return count;
	}
	
	public static String printTypeCount(List<Node> nodes) {
		int count_a = 0;
    	int count_b = 0;
    	
    	for(Node n : nodes) {
    		if(n.getType().compareTo("A")==0) {
    			count_a++;
    		}else {
    			count_b++;
    		}    		
    	}
    	
    	return "A: "+count_a+" B: "+count_b;
	}
	
	public static String printAvgFitness(List<Node> nodes) {
		double sum = 0;
		
		for(Node n : nodes) {
			sum += n.getFitness();
		}
		
		return "Avg. fitness: "+(sum/ (double)nodes.size());
	}

	public static SimulationData getSimulationData() {
		return simulationData;
	}

	public static void setSimulationData(SimulationData simulationData) {
		Simulation.simulationData = simulationData;
	}
	
	

}
