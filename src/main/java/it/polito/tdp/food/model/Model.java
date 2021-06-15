package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private SimpleWeightedGraph<Food, DefaultWeightedEdge>graph;
	private Map <Integer, Food> idMap;
	private FoodDao dao;
	private Simulatore sim;
	
	public Model() {
		idMap= new HashMap <Integer,Food>();
		dao=new FoodDao();
	}
	
	public void creaGrafo(Integer porzioni) {
		graph= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		dao.loadAllVertici(idMap,porzioni);
		Graphs.addAllVertices(graph, idMap.values());
		
		for(Arco a : dao.listArchi(idMap))
			Graphs.addEdge(graph, a.getF1(), a.getF2(), a.getPeso());
		
		
	}
	
	public Integer getNVertici() {
		return graph.vertexSet().size();
	}
	
	public Integer getNArchi() {
		return graph.edgeSet().size();
	}
	
	public List <Food> getVertici(){
		List <Food> vertici = new ArrayList <Food>();
		for(Food f: graph.vertexSet())
			vertici.add(f);
		Collections.sort(vertici);
		return vertici;
	}

	public SimpleWeightedGraph<Food, DefaultWeightedEdge> getGraph() {
		return graph;
	}

	
	public List<Vicino> getTopFive(Food scelto) {
		//entranti e uscenti da scelto
		List <Vicino> vicini = new ArrayList <Vicino>();
		
		//PROVA NON FUNZION
		/*System.out.println("IN");
		for(DefaultWeightedEdge e: graph.incomingEdgesOf(scelto))
			System.out.println(e);
		System.out.println("OUT");
		for(DefaultWeightedEdge e: graph.outgoingEdgesOf(scelto))
			System.out.println(e);*/
		
		for(Food f: Graphs.neighborListOf(graph, scelto)) {
			DefaultWeightedEdge e= graph.getEdge(f, scelto);
			Vicino v = new Vicino(f,graph.getEdgeWeight(e));
			vicini.add(v);
		}
		
		if(vicini==null)
			return null;
		
		/*for(DefaultWeightedEdge e: graph.incomingEdgesOf(scelto)) {
			Food f= Graphs.getOppositeVertex(graph, e, scelto);
			Vicino v = new Vicino(f,graph.getEdgeWeight(e));
			vicini.add(v);
		}*/
			
		Collections.sort(vicini);
		
		List <Vicino> topFive = new ArrayList <Vicino>();
		for(int i=0;i<5;i++)
			if(vicini.get(i)!=null)
				topFive.add(vicini.get(i));
		return topFive;
	}
	
	
	public void simula(Integer K,Food scelto) {
		sim= new Simulatore ( scelto,  K, graph);
		sim.run();
		
	}
	
	public Integer getNCucinati() {
		return sim.getNCucinati();
	}
	
	public double getTotTIME() {
		return sim.getTotTIME();
	}
	
	
	

	

}
