package br.ufrn.imd.bioinfo.gsop;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;

public class Neo4jDAO {

	private static final Driver driver = Neo4jDriverInstance.getDriver();

	public void eraseDB() {
		try ( Session session = driver.session() ){
			session.run("MATCH (n) DETACH DELETE n");
		}
	}

	//Erdős–Rényi model
	//edges: total number of edges
	public void generateERGraph(int nodes, int edges) {
		try (Session session = driver.session()){
			session.run("CALL apoc.generate.er("+nodes+","+edges+" ,'TMP_LABEL','TMP_REL')");
		}
	}

	//Barabási–Albert model
	//edges: number of edges per node
	public void generateBAGraph(int nodes, int edges) {
		try (Session session = driver.session()){
			session.run("CALL apoc.generate.ba("+nodes+","+edges+" ,'TMP_LABEL','TMP_REL')");
		}
	}

	//Watts–Strogatz model
	//meanDegree: degree which a node in the graph has on average (best to choose something < 10)
	/*
	 * * beta: probability an edge will be rewired. Rewiring means that an edge is removed and replaced by another edge
	 *       created from a pair chosen at random from a set of unconnected node pairs. Controls the clustering of the graph.
	 *       beta = 1.0: Erdos-Renyi model
	 *       beta = 0.0: Ring graph
	 *       0.0 < beta < 1.0: Fast convergence towards a random graph, but still sufficiently clustered.
	 *
	 * Recommended value of beta to exploit typical (randomness & clustering) properties of the W-S model: 0.4 < beta < 0.6
	 */
	public void generateWSGraph(int nodes, int meanDegree, double beta) {
		try (Session session = driver.session()){
			session.run("CALL apoc.generate.ws("+nodes+","+meanDegree+" ,"+beta+", 'TMP_LABEL','TMP_REL')");
		}
	}

	public void generateCompleteGraph(int nodes) {
		try (Session session = driver.session()){
			session.run("CALL apoc.generate.complete("+nodes+",'TMP_LABEL','TMP_REL')");
		}
	}

	/*
	 *  * Uses Blitzstein-Diaconis algorithm Ref:
	 * <p/>
	 * A SEQUENTIAL IMPORTANCE SAMPLING ALGORITHM FOR GENERATING RANDOM GRAPHS WITH PRESCRIBED DEGREES
	 * By Joseph Blitzstein and Persi Diaconis (Stanford University). (Harvard, June 2006)
	 */

	public void generateSimpleGraph(int nodes, int degree) {
		String degreeList = "["; 
		for(int i = 0; i < nodes-1; i++) {
			degreeList+=degree+",";
		}
		degreeList+=degree+"]";
		try (Session session = driver.session()){
			session.run("CALL apoc.generate.simple("+degreeList+",'TMP_LABEL','TMP_REL')");
		}
	}

}
