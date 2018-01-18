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
	
    public static void main( String[] args )
    {
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
    	
    	Simulation.printTypeCount(nodes);
    	
    	Simulation.printAvgFitness(nodes);
    	
    	
    	
    }
}

