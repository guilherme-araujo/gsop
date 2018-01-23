package br.ufrn.imd.bioinfo.gsop;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import br.ufrn.imd.bioinfo.gsop.model.Node;

public class Simulation {
	
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
	
	public static String printTypeCount(List<Node> nodes) {
		int count_a = 0;
    	int count_b = 0;
    	
    	for(Node n : nodes) {
    		if(n.getType().compareTo("\"A\"")==0) {
    			count_a++;
    		}else {
    			count_b++;
    		}    		
    	}
    	
    	return "A: "+count_a+" B: "+count_b;
	}
	
	public static String printAvgFitness(List<Node> nodes) {
		float sum = 0;
		
		for(Node n : nodes) {
			sum += n.getVal();
		}
		
		return "Avg. fitness: "+(sum/ (float)nodes.size()) ;
	}

}
