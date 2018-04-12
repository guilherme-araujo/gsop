package br.ufrn.imd.bioinfo.gsop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import br.ufrn.imd.bioinfo.gsop.model.GsopNode;

public class Simulation {

	private static SimulationData simulationData;

	private static List<Double> partialFitnessAvg;

	public static void cycleV3(Map<String, GsopNode> nodes, double deathRate) {

		// Contagem de nascimentos e mortes
		Random gerador = new Random();

		int dieCount = (int) ((double) nodes.size() * (deathRate - 1));
		
		// deaths

		List<String> selectedKeys = new ArrayList<String>();
		List<String> keys = new ArrayList<String>(nodes.keySet());
		// System.out.println(dieCount);
		for (int i = 0; i < dieCount; i++) {
			int chosen = gerador.nextInt(nodes.size());
			selectedKeys.add(keys.get(chosen));			
		}

		// births
		for (String key : keys) {
			//gerar roleta baseado em vizinhança, acesso ao banco? pre-loading?
			
			//escolher tipo propagado
			
			//substituir tipo do nó
		}
		
		

	}

	public static void cycle(List<GsopNode> nodes) {

		Random gerador = new Random();

		int chosen = gerador.nextInt(nodes.size());

		List<Integer> roleta = new LinkedList<>();

		for (int i = 0; i < nodes.size(); i++) {
			if (i == chosen)
				continue;
			for (int j = 0; j < nodes.get(i).getVal(); j++) {
				roleta.add(i);
			}
		}

		int chosen2 = gerador.nextInt(roleta.size());
		GsopNode n = new GsopNode();
		n.setHash(nodes.get(chosen).getHash());
		n.setType(nodes.get(roleta.get(chosen2)).getType());
		n.setVal(nodes.get(roleta.get(chosen2)).getVal());
		nodes.set(chosen, n);

	}

	public static void cycleV2(List<GsopNode> nodes, double birthRate, double deathRate) {

		Random gerador = new Random();

		int dieCount = (int) ((double) nodes.size() * (deathRate - 1));
		int birthCount = (int) ((double) nodes.size() * (birthRate - 1));

		// deaths

		// System.out.println(dieCount);
		for (int i = 0; i < dieCount; i++) {
			int chosen = gerador.nextInt(nodes.size());
			nodes.remove(chosen);
		}

		// births

		for (int a = 0; a < birthCount; a++) {

			int count_a = 0, count_b = 0;

			count_a = typeCount("A", nodes);
			count_b = typeCount("B", nodes);

			double chanceA = ((double) count_a) * simulationData.getTypes().get(0).getInitialCoeff();
			double chanceB = ((double) count_b) * simulationData.getTypes().get(1).getInitialCoeff();

			int chosen = gerador.nextInt((int) (chanceA + chanceB));
			GsopNode n = new GsopNode();
			if (chosen < (int) chanceA) {
				n.setType(simulationData.getTypes().get(0).getType());
				n.setCoeff(simulationData.getTypes().get(0).getInitialCoeff());
			} else {
				n.setType(simulationData.getTypes().get(1).getType());
				n.setCoeff(simulationData.getTypes().get(1).getInitialCoeff());
			}
			nodes.add(n);
			int pivot = gerador.nextInt(nodes.size() - 1);
			for (int i = 0; i < nodes.size(); i++) {
				if (++pivot > nodes.size() - 1)
					pivot = 0;
				if (nodes.get(pivot).getType() == n.getType()) {
					nodes.get(pivot).setFitness(nodes.get(pivot).getFitness() + 1);
					break;
				}
			}

		}

	}

	public static int typeCount(String type, List<GsopNode> nodes) {
		int count = 0;

		for (GsopNode n : nodes) {
			if (n.getType().compareTo(type) == 0) {
				count++;
			}
		}
		return count;
	}

	public static String printTypeCount(List<GsopNode> nodes) {
		int count_a = 0;
		int count_b = 0;

		for (GsopNode n : nodes) {
			if (n.getType().compareTo("A") == 0) {
				count_a++;
			} else {
				count_b++;
			}
		}

		return "A: " + count_a + " B: " + count_b;
	}

	public static double avgCoeff(List<GsopNode> nodes) {
		double sum = 0;

		for (GsopNode n : nodes) {
			sum += n.getCoeff();
		}

		return sum / (double) nodes.size();
	}

	public static String printAvgCoeff(List<GsopNode> nodes) {
		return "Avg. coefficient: " + avgCoeff(nodes);
	}

	public static double avgFitness(List<GsopNode> nodes) {
		double sum = 0;

		for (GsopNode n : nodes) {
			sum += n.getFitness();

		}

		return sum / (double) nodes.size();
	}

	public static String printAvgFitness(List<GsopNode> nodes) {
		return "Avg. fitness: " + avgFitness(nodes);
	}

	public static SimulationData getSimulationData() {
		return simulationData;
	}

	public static void setSimulationData(SimulationData simulationData) {
		Simulation.simulationData = simulationData;
	}

	public static List<Double> getPartialFitnessAvg() {
		return partialFitnessAvg;
	}

	public static void setPartialFitnessAvg(List<Double> partialFitnessAvg) {
		Simulation.partialFitnessAvg = partialFitnessAvg;
	}

	public static void addPartialFitnessAvg(Double avg) {
		if (partialFitnessAvg == null)
			partialFitnessAvg = new ArrayList<Double>();
		partialFitnessAvg.add(avg);
	}

}
