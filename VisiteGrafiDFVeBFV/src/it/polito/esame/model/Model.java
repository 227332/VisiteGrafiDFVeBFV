package it.polito.esame.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.esame.dao.ParoleDAO;

public class Model {

	int length;
	List<Parola> dizionario;
	/*
	 * OSS: conviene definire parole come attributo perché non la uso
	 * solo nel metodo caricaParole() ma anche per fare la ricerca del cammino
	 */
	List<Parola>  parole; // conterrà tutte le parole di lunghezza length
	UndirectedGraph<Parola, DefaultEdge> grafo;
	
	
	public Model(){
		ParoleDAO dao = new ParoleDAO();
		dizionario = dao.getAllParola();
		
	}
	
	public ParoleStats caricaParole(int lun){
		length=lun;
		parole = new ArrayList<>();//è giusto instanziarla qua???
		
		//carico le parole di lunghezza prefissata
		for(Parola p : dizionario){
			if(p.getNome().length()== length)
				parole.add(p);
		}
		
		grafo = new SimpleGraph<Parola,DefaultEdge>(DefaultEdge.class);
		Graphs.addAllVertices(grafo, parole);
		
		for(Parola p1:parole){
			for(Parola p2 : parole){
				if(sonoCollegati(p1,p2))
					grafo.addEdge(p1, p2);
			}
		}
		
		ParoleStats stats = new ParoleStats();
		stats.setTotParole(parole.size());
		stats.setTotColl(grafo.edgeSet().size());
		
		Parola maxDeg=null;
		for(Parola p:parole){
			if(maxDeg==null || grafo.degreeOf(p)>grafo.degreeOf(maxDeg)){
				maxDeg = p;
			}
		}
		stats.setParola(maxDeg);
		stats.setDegP(grafo.degreeOf(maxDeg));
		
		return stats;
	}
	
	/*
	 * Restituisce true se p1 e p2 differiscono in una sola lettera
	 */
	public boolean sonoCollegati(Parola p1,Parola p2){
		int count = 0;
		for(int i=0; i<p1.getNome().length();i++){
			if(p1.getNome().charAt(i)!=p2.getNome().charAt(i))
				count++;
		}
		
		if(count==1){
			//System.out.println("("+p1.getNome()+","+p2.getNome()+")");
			return true;
		}
		
		return false;
	}
	
	
	public List<Parola> cercaCammino(String s1, String s2){
		Parola p1temp = new Parola(s1);
		Parola p2temp = new Parola(s2);
		/*
		 * RICORDA di fare tutti i check necessari, se no rischi di non
		 * gestire delle eccezioni!!!
		 */
		if(parole.contains(p1temp) && parole.contains(p2temp)){
			Parola p1 = parole.get(parole.indexOf(p1temp));
			Parola p2 = parole.get(parole.indexOf(p2temp));

			DijkstraShortestPath<Parola,DefaultEdge> cammino = new DijkstraShortestPath<>(grafo,p1,p2);

			/*
			 * OSS: conviene farsi restituire il GraphPath invece della List<DefaultEdge>
			 * perché Graphs ha un metodo che, se gli passi un GraphPath, ti restituisce
			 * tutti i vertici che compongono tale path!!!
			 */
			GraphPath<Parola,DefaultEdge> path = cammino.getPath();
			if(path==null){
				return null;
			}
			else{

				return Graphs.getPathVertexList(path);

			}
		}
		return null;
	}

	/*
	 * OSS: La visita in ampiezza BFV si può anche implementare senza usare
	 * l' iterator di java...Una sua implementazione ricorre all'uso di una 
	 * coda FIFO, qui chiamata toVisit, per estrarre i vertici per livelli ed
	 * è:
	 * 
	  Queue<Parola> BFV(Parola p){
	  Queue<Parola> toVisit = new LinkedList<Parola>();
	  Queue<Parola> visited = new LinkedList<Parola>();
	  //aggiungo la source
	  toVisit.add(p);
	  Parola estratta;
	  while((estratta = toVisit.poll())!=null){
		  if(!visited.contains(estratta)){
			  visited.add(estratta);
			  for(Parola p2 : Graphs.neighborListOf(grafo, estratta))
				  toVisit.add(p2);//li metto in fondo alla coda
		  }
	  }
	  return visited;
	  }
	 */
	public Queue<Parola> visitaBFV(String s1) {
		//essendo FIFO, capisco come visita il grafo
		Queue<Parola> Visited = new LinkedList<Parola>();
		Parola p1temp = new Parola(s1);
		/*
		 * RICORDA di fare tutti i check necessari, se no rischi di non
		 * gestire delle eccezioni!!!
		 */
		if(!parole.contains(p1temp))
			return null;
		
		
		Parola p1 = parole.get(parole.indexOf(p1temp));
		GraphIterator<Parola, DefaultEdge> bfv = new BreadthFirstIterator<Parola, DefaultEdge>(grafo, p1);
		while (bfv.hasNext()) {
			Parola p = bfv.next();
			Visited.add(p);
		}
		return Visited;
	}

	
	
	
	
	
	/*
	 * OSS: La visita in profondità DFV si può anche implementare senza usare
	 * l' iterator di java...Una sua implementazione ricorre all'uso della 
	 * ricorsione ed è:
	 * 	
	  Queue<Parola> DFV(Parola p){
	  Queue<Parola> visited = new LinkedList<Parola>();
	  
	  recursiveDFV(p,visited);
	  return visited;
	  }
	  
	  void recursiveDFV(Parola p, Queue<Parola> visited){
	  if(visited.contains(p))
		  return; //ho già visitato p e quindi anche tutti i suoi vicini a cascata
	  
	  visited.add(p);
	  for(Parola p2 : Graphs.neighborListOf(grafo,p))
		recursiveDFV(p2,visited);//li metto in fondo alla coda
	  
	  }
	*/
	
	public Queue<Parola> visitaDFV(String s2) {
		//essendo FIFO, capisco come visita il grafo
		Queue<Parola> Visited = new LinkedList<Parola>();
		Parola p2temp = new Parola(s2);
		/*
		 * RICORDA di fare tutti i check necessari, se no rischi di non
		 * gestire delle eccezioni!!!
		 */
		if(!parole.contains(p2temp))
			return null;
		
		
		Parola p2 = parole.get(parole.indexOf(p2temp));
		GraphIterator<Parola, DefaultEdge> dfv = new DepthFirstIterator<Parola, DefaultEdge>(grafo, p2);
		while (dfv.hasNext()) {
			Parola p = dfv.next();
			Visited.add(p);
		}
		return Visited;
	}
}
