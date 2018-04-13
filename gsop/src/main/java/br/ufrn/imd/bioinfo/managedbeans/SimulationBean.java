package br.ufrn.imd.bioinfo.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import br.ufrn.imd.bioinfo.gsop.App;
import br.ufrn.imd.bioinfo.gsop.IndType;
import br.ufrn.imd.bioinfo.gsop.Simulation;
import br.ufrn.imd.bioinfo.gsop.SimulationData;
import br.ufrn.imd.bioinfo.gsop.controller.QueriesController;

@ManagedBean(name="simulationBean")
@SessionScoped
public class SimulationBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int builtGraphSize = 0;
	
	private String simResult;
	
	private SimulationData simulationData;
	
	private double coeff1;
	private double coeff2;
	
	private LineChartModel lineModel1;
	private LineChartModel areaModel;
	
	QueriesController queriesController;
		
	@PostConstruct
	public void init() {
		coeff1 = 1.0;
		coeff2 = 1.05;
		
		queriesController = new QueriesController();
		builtGraphSize = queriesController.getNodeCount();
		
		simulationData = new SimulationData();
		simulationData.setBirthRate(1.04);
		simulationData.setDeathRate(1.04);
		simulationData.setInitialPopulation(200);
		simulationData.setCycles(1000);
		simulationData.setPlotDensity(100);
		List<IndType> types = new ArrayList<IndType>();
		IndType typeA = new IndType();
		typeA.setInitialCoeff(coeff1);
		typeA.setInitialRatio(0.5);
		typeA.setType("A");
		types.add(typeA);
		
		IndType typeB = new IndType();
		typeB.setInitialCoeff(coeff2);
		typeB.setInitialRatio(0.5);
		typeB.setType("B");
		types.add(typeB);
		
		simulationData.setTypes(types);
		createLineModels();
		createAreaModel();
	}
	
	public void runSim() {
		simulationData.setDeathRate(simulationData.getBirthRate());
		simulationData.getTypes().get(0).setInitialCoeff(coeff1);
		simulationData.getTypes().get(1).setInitialCoeff(coeff2);
		simResult = App.runSim(simulationData);
		lineModel1 = updateLineChart(Simulation.getPartialFitnessAvg());
		lineModel1.setExtender("ext2");
		updateAreaChart();
	}
	
	public void runSimV3() {
		this.builtGraphSize = queriesController.getNodeCount();
		simulationData.setDeathRate(simulationData.getBirthRate());
		simulationData.getTypes().get(0).setInitialCoeff(coeff1);
		simulationData.getTypes().get(1).setInitialCoeff(coeff2);
		simulationData.setInitialPopulation(builtGraphSize);	
		simResult = App.runSimV3(simulationData);
		lineModel1 = updateLineChart(Simulation.getPartialFitnessAvg());
		lineModel1.setExtender("ext2");
		updateAreaChart();
	}
	
	private void createLineModels() {
        lineModel1 = initLinearModel();
        lineModel1.setTitle("Linear Chart");
        lineModel1.setLegendPosition("e");
        Axis yAxis = lineModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);
    }
	
	private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
 
        LineChartSeries fitnessLine = new LineChartSeries();
        fitnessLine.setLabel("Fitness");
 
        fitnessLine.set(0, 0);
 
        model.addSeries(fitnessLine);
         
        return model;
    }

	private LineChartModel updateLineChart(List<Double> avgList) {
		LineChartModel model = new LineChartModel();
		LineChartSeries fitnessLine = new LineChartSeries();
        fitnessLine.setLabel("Fitness");
        
        for(int i = 0; i < avgList.size(); i++) {
        	fitnessLine.set(i, avgList.get(i));
        }
        
        model.addSeries(fitnessLine);
        
        return model;
	}
	
	private void updateAreaChart() {
		areaModel = new LineChartModel();
		areaModel.setExtender("ext1");
		
		LineChartSeries typeA = new LineChartSeries();
        typeA.setFill(true);
        typeA.setLabel("A");        
        for(int i = 0; i < App.getTypeAPopHistory().size(); i++) {
        	typeA.set(i, App.getTypeAPopHistory().get(i));        	
        }
        
        LineChartSeries typeB = new LineChartSeries();
        typeB.setFill(true);
        typeB.setLabel("B");
        for(int i = 0; i < App.getTypeBPopHistory().size(); i++) {
        	typeB.set(i, App.getTypeBPopHistory().get(i));        	
        }
        areaModel.addSeries(typeA);
        areaModel.addSeries(typeB);
 
        areaModel.setTitle("Area Chart");
        areaModel.setLegendPosition("ne");
        areaModel.setStacked(true);
        areaModel.setShowPointLabels(true);
 
        Axis xAxis = new CategoryAxis("Cycles");
        areaModel.getAxes().put(AxisType.X, xAxis);
        Axis yAxis = areaModel.getAxis(AxisType.Y);
        yAxis.setLabel("Population");
        yAxis.setMin(0);
        yAxis.setMax(simulationData.getInitialPopulation()*1.1);
	}
	
	private void createAreaModel() {
        areaModel = new LineChartModel();
 
        LineChartSeries typeA = new LineChartSeries();
        typeA.setFill(true);
        typeA.setLabel("A");
        typeA.set("1", 1);        
 
        LineChartSeries typeB = new LineChartSeries();
        typeB.setFill(true);
        typeB.setLabel("B");
        typeB.set("1", 1);
        
 
        areaModel.addSeries(typeA);
        areaModel.addSeries(typeB);
 
        areaModel.setTitle("Area Chart");
        areaModel.setLegendPosition("ne");
        areaModel.setStacked(true);
        areaModel.setShowPointLabels(true);
 
        Axis xAxis = new CategoryAxis("Cycles");
        areaModel.getAxes().put(AxisType.X, xAxis);
        Axis yAxis = areaModel.getAxis(AxisType.Y);
        yAxis.setLabel("Population");
        yAxis.setMin(0);
        yAxis.setMax(simulationData.getInitialPopulation()*1.1);
    }

	public String getSimResult() {
		return simResult;
	}

	public void setSimResult(String simResult) {
		this.simResult = simResult;
	}

	public double getCoeff1() {
		return coeff1;
	}

	public void setCoeff1(double coeff1) {
		this.coeff1 = coeff1;
	}

	public double getCoeff2() {
		return coeff2;
	}

	public void setCoeff2(double coeff2) {
		this.coeff2 = coeff2;
	}

	public SimulationData getSimulationData() {
		return simulationData;
	}

	public void setSimulationData(SimulationData simulationData) {
		this.simulationData = simulationData;
	}

	public LineChartModel getLineModel1() {
		return lineModel1;
	}

	public void setLineModel1(LineChartModel lineModel1) {
		this.lineModel1 = lineModel1;
	}

	public LineChartModel getAreaModel() {
		return areaModel;
	}

	public void setAreaModel(LineChartModel areaModel) {
		this.areaModel = areaModel;
	}

	public int getBuiltGraphSize() {
		return builtGraphSize;
	}

	public void setBuiltGraphSize(int builtGraphSize) {
		this.builtGraphSize = builtGraphSize;
	}

	
			
}
