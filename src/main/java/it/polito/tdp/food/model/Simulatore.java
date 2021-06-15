package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.model.Event.EventType;

public class Simulatore {
	
	private PriorityQueue<Event> queue;
	private SimpleWeightedGraph<Food, DefaultWeightedEdge>graph;
	private List <Food> cucinati;
	private Integer K;
	private List <Stazione> stazioni;
	private double totTIME=0.0; //minutes
	
	public Simulatore( Food scelto, Integer K, SimpleWeightedGraph<Food, DefaultWeightedEdge>graph) {
		queue = new PriorityQueue<Event> ();
		this.graph=graph;
		cucinati = new LinkedList<Food>(); //cibi già cucinati
		this.K=K;//stazioni
		stazioni= new ArrayList <Stazione>();
		
		for(int i=1;i<=K;i++)
			stazioni.add(new Stazione(i));
		
		int j=0;
		for(Food f: Graphs.neighborListOf(graph, scelto)) {
			double minutes = graph.getEdgeWeight(graph.getEdge(f, scelto));
			
			//stazioni libere???
			if(j<K)
				j++;
			else //tutte le stazioni sono occupate
				break;
			
			//tra un n° di min pari alle calorie dell'edge il cibo f in preparazione sarà pronto
			Event e = new Event (minutes,EventType.FINE,f);
			queue.add(e);
			cucinati.add(f);
			
			totTIME+=minutes;
		}
		
		
			
	}
	
	public void run() {
		while(!queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}//while
	}
	
	private void processEvent(Event e) {
		switch(e.getType()) {
				
			case FINE: //prossimo arco da percorrere
				DefaultWeightedEdge prossimoEdge = this.getTopCalorie(e.getInPreparazione());
				
				//null se cibi già tutti cucinati
				//non ci sono più vicini --> questa stazione si ferma
				if(prossimoEdge==null)
					break;
				
				double calorieMinuti= graph.getEdgeWeight(prossimoEdge); 
				Food prossimo = Graphs.getOppositeVertex(graph, prossimoEdge, e.getInPreparazione());
						
									//quando finirà di preparare il next cibo
				Event e2 = new Event (e.getMinutes()+calorieMinuti,EventType.FINE,prossimo);
				cucinati.add(prossimo);
				queue.add(e2);
				totTIME+=calorieMinuti;
				
				break;
				
			default:
				break;
		}
	}
	
	/**
	 * partendo dall'ultimo cibo preparato
	 * sceglie l'arco con più calorie tra i vicini
	 * @param f
	 * @return
	 */
	private DefaultWeightedEdge getTopCalorie (Food ultimo) {
		double max=0.0;
		DefaultWeightedEdge e=null;
		
		for(Food f: Graphs.neighborListOf(graph, ultimo)) {
			if(!cucinati.contains(f) & graph.getEdgeWeight(graph.getEdge(f, ultimo))>max) {
				max= graph.getEdgeWeight(graph.getEdge(f, ultimo));
				e= graph.getEdge(f, ultimo);
				
			}
				
		}
		return e;
	}
	
	
	public Integer getNCucinati() {
		return cucinati.size();
	}
	
	public double getTotTIME() {
		return this.totTIME;
	}

}
