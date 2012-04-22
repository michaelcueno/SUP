import java.util.ArrayList;

public class Vertex<T>{
	
	T value;
	public ArrayList<T> dependencies;
	public boolean visited;
	int dist;
	T path;
	int indegree;
	int timeStamp;

	public Vertex(T value){
		dependencies = new ArrayList<T>();
		this.value = value;
		this.dist = 0;
		this.indegree = 0;
		this.visited = false;
		this.path = null;
	}

	public T getValue(){
		return this.value;
	}
	
	public int getDist(){
		return dist;
	}

	public void addAdj(T adj){

		dependencies.add(adj);
	}

	@Override
	public String toString(){
	
		return(value +" : "+ dependencies );

	}

	/*
	 * Returns true if the vertex has an indegree of zero
	 */
	public boolean isBasic(){
		return (dependencies.size() == 0);
	}
	public void setTime( int t ){
		this.timeStamp = t;
	}
	public void setDist( int d ){
		this.dist = d;
	}
}
