package br.ufrn.imd.bioinfo.gsop;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
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
	
	private static final Driver driver = GraphDatabase.driver( "bolt://10.7.43.2:7687", AuthTokens.basic( "neo4j", "bif$2017" ) );;	
	
	public static String runSim(SimulationData simulationData) {
		
		List<Node> nodes = new LinkedList<Node>();
		
		for(int i = 0; i < simulationData.getInitialPopulation(); i++) {
			if(i < simulationData.getInitialPopulation()*0.5) {
				Node n = new Node();
				n.setType(simulationData.getTypes().get(0).getType());
				n.setFitness(simulationData.getTypes().get(0).getInitialFitness());
				nodes.add(n);
			}else {
				Node n = new Node();
				n.setType(simulationData.getTypes().get(1).getType());
				n.setFitness(simulationData.getTypes().get(1).getInitialFitness());
				nodes.add(n);
			}
		}
    	
		int count = 0;
		
		Simulation.setSimulationData(simulationData);
		
		long tStart = System.currentTimeMillis();
    	
    	for(count = 0; count < simulationData.getCycles(); count++) {
    		/*if(!Simulation.cycleV2(nodes, simulationData.getBirthRate(), simulationData.getDeathRate())) {
    			limit = true;
    			break;
    		}*/
    		Simulation.cycleV2(nodes, simulationData.getBirthRate(), simulationData.getDeathRate());
    		//System.out.println((nodes.size()));
    	}
    	
    	long tEnd = System.currentTimeMillis();
    	long tDelta = tEnd - tStart;
    	double elapsedSeconds = tDelta / 1000.0;
		
    	
		String result = Simulation.printTypeCount(nodes) + "\n" + Simulation.printAvgFitness(nodes);
		result += "\n Cycles: "+count+"\nTime: "+elapsedSeconds;
		/*if(limit) {
			result += "\n Simulation limted by 20 million nodes.";
		}*/
		
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
    	
    	String result = Simulation.printTypeCount(nodes) + "\n" + Simulation.printAvgFitness(nodes);
    	
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
    	
    	System.out.println(Simulation.printAvgFitness(nodes));*/
    	System.out.println(runSim());
    	
    	
    }
}

