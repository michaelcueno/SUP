/***********************************************************************
 * Shortest Unweighted Path ---- Toy program for practice
 * 
 * Page 327 - Data Structures and algorithms analysis (Wiess) 
 * 
 ***********************************************************************/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Stack;
import java.util.Scanner;

public class SUP { 

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner fileScanner = new Scanner(new File(args[0]));
		Scanner keyboard = new Scanner(System.in);
		String name = null;
		Graph<String> graph = new Graph<String>();
		
		// Initialize Graph with adjacency list implementation  

		while(fileScanner.hasNextLine()){

			String line = fileScanner.nextLine();	
			Scanner lineScanner = new Scanner(line);
			
			if(lineScanner.hasNext()){

				name = lineScanner.next();
				if(graph.contains(name)){
					if(graph.get(name).visited){
						Vertex<String> v = graph.get(name);
						v.visited = false;
					}else{
						System.out.println("input file contains repeated vertice names");
						System.exit(0);
					}
				}else
					Vertex<String> v = new Vertex<String>(name);

				if(lineScanner.hasNext()){
					if(lineScanner.next().equals(":")){
					
						while(lineScanner.hasNext()){
							String vert = lineScanner.next();
							if(graph.contains(vert)){
								Vertex<String> w = graph.get(vert); 
								v.addAdj( w );
							}else{
								Vertex<String> w = new Vertex<String>(vert);
								w.visited = true; //Flag to indicate this vertex appears in the graph as a dependency
								graph.add(vert, w);
								v.addAdj(w);
							}
						}
					}
				}
				graph.add(name, v);
			}
		}
	
		System.out.print("Vertices: ");	
		for(String vert : graph.getKeys()){
			System.out.print(vert + " ");
		}
		System.out.println();
		System.out.println("Start: ");
		String start = keyboard.next();
		System.out.println("End: ");
		String end = keyboard.next();

		graph.setDistances(start);
		System.out.println("dist using setDistances() 'naive approach': " + graph.get(end).getDist());
		graph.reset();
		graph.QueueDist(start);
		System.out.println("dist using faster, Queue implementation: " + graph.get(end).getDist());
		//Print out shortest path
		Stack<Vertex<String>> s = new Stack<Vertex<String>>();
		Vertex<String> v = graph.get(end);
		while(v != null ){
			s.push(v);
			v = graph.get(v.path);
		}
		while(!s.empty()){
			System.out.print(" "+ s.pop().getValue());
		}
		

	}
}




