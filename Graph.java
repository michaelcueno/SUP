/*
 * Simple Graph class. Holds parameterized values of T in a 
 * HashMap. Uses a dependency list implementation. 
 * 		-Depends on Vertex.java
 */

import java.util.HashMap;
import java.util.Stack;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;

public class Graph <T> {

	private static int FLAG = 9999999; // Represents infinity (Used as a flag)

	public HashMap<T,Vertex<T>> map = new HashMap<T,Vertex<T>>();	
	public int time = 1;	

	public boolean add( T value, Vertex<T> v ){
	
		if(map.containsKey(value)){
			return false;
		}else{
			map.put( value, v );
			return true;

		}
	}

	public Set<T> getKeys(){
		return map.keySet();
	}

	public Vertex get( T name ){
		return map.get(name);
	}
	public void setIndegrees(){

		for( Vertex<T> v : map.values() ){
			if(!v.isBasic()){
				for( Vertex<T> w : v.dependencies ){
					w.indegree++;
					
				}
			}
		}	
	}

	/**
	 * Takes the starting vertex and finds the distances to every other vertex 
	 * in the graph. For the Unweighted case only.                  
	 */
	public void setDistances(T name){

		for( Vertex<T> v : map.values()) {
			v.dist = FLAG;
			v.visited = false;
		}	

		map.get(name).setDist(0);

		for(int i = 0 ; i < map.size(); i++){
			for( Vertex<T> v : map.values() ) {
				if( v.dist == i && !v.visited){
					v.visited = true;
					for( Vertex<T> w : v.dependencies){
						if(w.dist == FLAG){
							w.setDist(i+1);
							w.path = v.value;
						}
					}
				}
			}
		}

	}
	
	/**
	 * Better approach to above problem using a Queue implementation to 
	 * find the shortest path on an unweighted graph
	 */
	public void QueueDist( T name ){
	
		Queue<Vertex<T>> q = new LinkedList<Vertex<T>>();
		for( Vertex v : map.values() ){
	
			v.setDist(FLAG);
		}

		Vertex<T> v = map.get(name);
		v.setDist(0);
		q.offer(v);

		while(q.size() != 0 ){
			
			v = q.poll();
			for( Vertex<T> w : v.dependencies ){

				if(w.dist == FLAG){
					w.setDist(v.dist + 1);
					w.path = v.getValue();
					q.offer(w);
				}
			}

		}


	}

	public boolean hasCycle(){
		
		int count = 0;
		Stack<Vertex<T>> s = new Stack<Vertex<T>>();

		for( Vertex<T> v : map.values() ){
			if(v.indegree == 0)
				s.push(v);
		}

		while( !s.empty() ){
			
			Vertex<T> v = (Vertex<T>)s.pop();
			count++;
			for( Vertex<T> w : v.dependencies ){

				if(--w.indegree == 0)
					s.push(w);
			}	
		}
		
		if( count != map.size() ){
			return true;
		}else	
			return false;
	}

	/*
	 * Returns true if the graph contains key name
	*/
	public boolean contains(T value){
		if(map.containsKey(value)){
			return true;
		}else	
			return false;
	}

	public int getTime( T value ){
		if(map.containsKey(value)){
			return map.get(value).timeStamp;
		}else{
			System.out.println("File does not exist");
			return -1;
		}
	}
	
	public void touch( T value ){

		Vertex<T> v = map.get(value);
		
		if(map.containsKey(value)){
			if(v.isBasic() ){  
				v.setTime(time);
				System.out.println("File '" + value + "' has been modified");
				time++;
			}else
				System.out.println("cannot touch a target file");
		}else
			System.out.println("File does not exist");
	}

	public void make( T value ){
		
		if(map.get(value).isBasic()){

			System.out.println("Cannot make basic files");
		}else{

			update( value );
			reset();
		}
	}

	/*
	 * Recursive function that updates a vertex's timestamp iff any of 
	 * its dependacies have a timestamp greater than its own.
	*/
	private void update( T value ){

		if(map.containsKey( value )){
			Vertex<T> v = map.get( value );		

			//Base Case
			if( v.isBasic() ){
				v.visited = true;
				return;

			}else {
				if(!v.visited){
					boolean upToDate = true;
					for( Vertex<T> w : v.dependencies ){
						update(w.getValue());
						if( w.timeStamp > v.timeStamp ){
							upToDate = false;
						}
					}
					v.visited = true;
					if(upToDate){
						System.out.println( v.getValue() + " is up to date");
					}else{
						System.out.println("making " + v.getValue() + "...done");
						v.setTime(time);
						time++;	
					}
				}
			}

		}else
			System.out.println("File does not exist");

	}

	public void reset(){
		for( Vertex<T> v : map.values() ){
			v.visited = false;
			v.dist = 0;
		}
	}

	@Override
	public String toString(){
		String result = "";
		for( Vertex<T> v : map.values() ){
			result += " " + v.getValue();
			result += ": [" ;
			for( Vertex<T> w : v.dependencies){
				result += w.getValue() +"," ;
			}
			result += "]";
		} 
		return result;
	}
}
