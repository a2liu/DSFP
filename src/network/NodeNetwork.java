package network;

public class NodeNetwork<E> extends Network<String,Node<E>> {
	
	/**
	 * adds another vertex
	 */
	public boolean addVertex(String key) {}
	
	public boolean rmVertex(String key) {}
	
	public Vertex findVertex(String key) {}
}

class Node<E> extends Vertex {
	
	private String name;
	private E data;
	
	Node() {
		this(0,null,null);
	}
	
	Node(int i) {
		this(i,null,null);
	}
	
	Node(int i, E e) {
		this(0,null,e);
	}
	
	Node(int i,String n, E e) {
		super(i);
		name = n;
		data = e;
	}
	
	@Override
	public String getLabel() {
		return name;
	}
	
	public E getData() {
		return data;
	}
}
