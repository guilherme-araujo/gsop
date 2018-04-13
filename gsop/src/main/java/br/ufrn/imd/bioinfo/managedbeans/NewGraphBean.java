package br.ufrn.imd.bioinfo.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.ufrn.imd.bioinfo.gsop.controller.NewGraphController;
import br.ufrn.imd.bioinfo.gsop.controller.QueriesController;

@ManagedBean(name="newGraphBean")
@SessionScoped
public class NewGraphBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int numNodes;
	
	private int numEdges;
	
	private String result;
	
	List<String> generatedNodes;
	
	QueriesController queries;
	
	NewGraphController newGraph;
			
	@PostConstruct
	public void init() {
		numNodes = 100;
		numEdges = 4;
		result = "";
		queries = new QueriesController();
		newGraph = new NewGraphController();
		generatedNodes = queries.listAllAsStringWithNeighbours();
	}
	
	public void generateba() {
				
		newGraph.generateBAGraph(numNodes, numEdges);
		
		generatedNodes = queries.listAllAsStringWithNeighbours();		
		
	}

	public int getNumNodes() {
		return numNodes;
	}

	public void setNumNodes(int numNodes) {
		this.numNodes = numNodes;
	}

	public int getNumEdges() {
		return numEdges;
	}

	public void setNumEdges(int numEdges) {
		this.numEdges = numEdges;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<String> getGeneratedNodes() {
		generatedNodes = queries.listAllAsStringWithNeighbours();
		return generatedNodes;
	}

	public void setGeneratedNodes(List<String> generatedNodes) {
		this.generatedNodes = generatedNodes;
	}
	
	
	
}
