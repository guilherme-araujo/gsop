package br.ufrn.imd.bioinfo.gsop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import br.ufrn.imd.bioinfo.gsop.model.Node;

/**
 * Hello world!
 *
 */
public class App 
{
	
	private static final Driver driver = Neo4jDriverInstance.getDriver();
	
	private static List<Integer> typeAPopHistory;
	private static List<Integer> typeBPopHistory;
	
	public static String runSim(SimulationData simulationData) {
		
		List<Node> nodes = new LinkedList<Node>();
		
		for(int i = 0; i < simulationData.getInitialPopulation(); i++) {
			Node n = new Node();
			n.setFitness(0);
			if(i < simulationData.getInitialPopulation()*0.5) {
				n.setType(simulationData.getTypes().get(0).getType());
				n.setCoeff(simulationData.getTypes().get(0).getInitialCoeff());
			}else {
				n.setType(simulationData.getTypes().get(1).getType());
				n.setCoeff(simulationData.getTypes().get(1).getInitialCoeff());
			}
			nodes.add(n);
		}
    	
		int count = 0;
		
		Simulation.setSimulationData(simulationData);
		
		long tStart = System.currentTimeMillis();
    	
		//Contagem parcial de fitness
		int steps = simulationData.getPlotDensity();
		int step = simulationData.getCycles()/steps;
		if(step==0) step++;
		
		typeAPopHistory = new ArrayList<Integer>();
		typeBPopHistory = new ArrayList<Integer>();
		Simulation.setPartialFitnessAvg(new ArrayList<Double>());
		
    	for(count = 0; count < simulationData.getCycles(); count++) {
    		Simulation.cycleV2(nodes, simulationData.getBirthRate(), simulationData.getDeathRate());
    		
    		if(count%step == 0) {
    			Simulation.addPartialFitnessAvg(Simulation.avgFitness(nodes));
    			typeAPopHistory.add(Simulation.typeCount("A", nodes));
        		typeBPopHistory.add(Simulation.typeCount("B", nodes));
    		}
    		//System.out.println((nodes.size()));
    	}
    	
    	long tEnd = System.currentTimeMillis();
    	long tDelta = tEnd - tStart;
    	double elapsedSeconds = tDelta / 1000.0;
		    	
		String result = Simulation.printTypeCount(nodes) + "\n" + Simulation.printAvgCoeff(nodes)
			+ "\n" + Simulation.printAvgFitness(nodes);
		result += "\n Cycles: "+count+"\nTime: "+elapsedSeconds;
				
    	return result;
	}
	
	public static String runSim() {
		List<Node> nodes = new LinkedList<Node>();
    	
    	try ( Session session = driver.session() ){
			
			StatementResult result;
			
			//populate individuals list
			result = session.run("match (n) return n");
			while (result.hasNext())
            {
                Record record = result.next();
                
                Node n = new Node();
                n.setHash(record.get(0).get("uuid").toString());
                n.setType(record.get(0).get("type").toString());
                if(n.getType().compareTo("\"A\"")==0) {
                	n.setVal(5);
                }else {
                	n.setVal(6);
                }
                
                nodes.add(n);
                
            }
			
    	}    	  
    	
    	int count = 0;
    	
    	for(count = 0; count < 1000; count++) {
    		Simulation.cycle(nodes);    		
    	}
    	
    	String result = Simulation.printTypeCount(nodes) + "\n" + Simulation.printAvgCoeff(nodes);
    	
    	return result;
	}
	
    public static void main( String[] args )
    {
    	/*List<Node> nodes = new LinkedList<Node>();
    	
    	try ( Session session = driver.session() ){
			
			StatementResult result;
			
			//populate individuals list
			result = session.run("match (n) return n");
			while (result.hasNext())
            {
                Record record = result.next();
                
                Node n = new Node();
                n.setHash(record.get(0).get("uuid").toString());
                n.setType(record.get(0).get("type").toString());
                if(n.getType().compareTo("\"A\"")==0) {
                	n.setVal(5);
                }else {
                	n.setVal(6);
                }
                
                nodes.add(n);
                
            }
			
    	}    	  
    	
    	int count = 0;
    	
    	for(count = 0; count < 1000; count++) {
    		Simulation.cycle(nodes);    		
    	}
    	
    	System.out.println(Simulation.printTypeCount(nodes));
    	
    	System.out.println(Simulation.printAvgCoeff(nodes));*/
    	System.out.println(runSim());
    	
    	
    }

	public static List<Integer> getTypeAPopHistory() {
		return typeAPopHistory;
	}

	public static void setTypeAPopHistory(List<Integer> typeAPopHistory) {
		App.typeAPopHistory = typeAPopHistory;
	}

	public static List<Integer> getTypeBPopHistory() {
		return typeBPopHistory;
	}

	public static void setTypeBPopHistory(List<Integer> typeBPopHistory) {
		App.typeBPopHistory = typeBPopHistory;
	}
    
    
}

