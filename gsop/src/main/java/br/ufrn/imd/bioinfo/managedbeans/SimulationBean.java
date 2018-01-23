package br.ufrn.imd.bioinfo.managedbeans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.ufrn.imd.bioinfo.gsop.App;

@ManagedBean(name="simulationBean")
@SessionScoped
public class SimulationBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String simResult;
	
	public void runSim() {
		simResult = App.runSim();
		
		//return "success";
	}

	public String getSimResult() {
		return simResult;
	}

	public void setSimResult(String simResult) {
		this.simResult = simResult;
	}
	
	
	
}
