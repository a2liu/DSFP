package network;

import static util.IOUtil.isNumber;
import static util.IOUtil.readFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import sun.misc.Queue;

/**
 * need to make new vertex object that holds values, otherwise this will become complicated quickly
 * @author liu_albert
 *
 */
public class RatingsGraph extends Graph<RatingsNode> {
	
	public RatingsGraph() {
	}
	
	@Override
	public boolean addVertex(Character t) {
		if (getDefaultID()>127 || (t.toString().charAt(0) < 48))
			return false;
		RatingsNode v = new RatingsNode(t);
		return addVertex(t,v);
	}
	
	/**
	 * LMAO NOT DONE AT ALL
	 * @param t
	 * @throws InterruptedException
	 * @throws IllegalArgumentException
	 */
	public void update(Character t) throws InterruptedException, IllegalArgumentException {
		Queue<Character> queue = new Queue<Character>();
		
		if (size() == 0)
			return;
		
		if (getVertex(t) == null)
			throw new IllegalArgumentException("'" + t.toString() + "' is not a valid node identifier.");
		
		//creating necessary containers
		Hashtable<Character, Boolean> reached = new Hashtable<Character,Boolean>();
		Enumeration<Character> vertexList = enumVertices();
		ArrayList<Edge> currentEdges;
		Character current;
		Character currentNeighbor;
		Character start = t;
		
		while (reached.size()<size()) {
			//start at a vertex
			if (!reached.containsKey(start)) {
				queue.enqueue(start);
				while (!queue.isEmpty()) {
					current = queue.dequeue();
					if (!reached.containsKey(current)) {
						currentEdges = getVertex(current).getEdges();//list of edges
						for (int x = 0; x<currentEdges.size();x++) {
							currentNeighbor = (Character) currentEdges.get(x).getDestination().getLabel(); //neighbor we're looking at
							if (!reached.containsKey(currentNeighbor)) {
									queue.enqueue(currentNeighbor);//only add the neighbor to the queue if it wasn't reached and isn't already in the queue
							}	
						}
						reached.put(current, true);
					}
				}
			}
			if (vertexList.hasMoreElements())
				start = vertexList.nextElement();
		}
	}
	
	private static <T> boolean queueContains(Queue<T> q,T o) {
		Enumeration e = q.elements();
		
		while(e.hasMoreElements()) {
			if (e.nextElement() == o)
				return true;
		}
		return false;
	}
	
	private void updateVertex(Character t) {
		this.getVertex(t).update();;
	}
	
	/**
	 * loads edges into the network
	 * @param document the name of the document to add from
	 * @throws IOException if the document doesn't exist
	 */
	public void loadNetwork(String document) throws IOException {
		ArrayList<String> input = readFile(document);
		String[] line;
		for (int x = 0; x< input.size(); x++) {
			line = input.get(x).split(",");
			loadEdge(line);
		}
	}
	
	/**I should put this in Network probably
	 * loads an edge into the network given an array of strings
	 * @param edge the array of strings to input
	 */
	private void loadEdge(String[] edge) {
		
		if (edge.length < 3)
			return;
		
		if(edge[0].length()==1 || isNumber(edge[0])) {
			if (edge[1].length()==1 || isNumber(edge[1])) {
				int id1;
				int id2;
				
				if(getVertex(edge[0].charAt(0))==null && getVertex(Integer.parseInt((edge[0]))) == null) {
					if (isNumber(edge[0])) {
						id1 = Integer.parseInt(edge[0]);
						addVertex(id1);
						addEdge(id1,id1,1);
					} else {
						id1 = edge[0].charAt(0)-48;
						addVertex(id1);
						addEdge(id1,id1,1);
					}
				} else {
					if (isNumber(edge[0])) {
						id1 = Integer.parseInt(edge[0]);
					} else {
						id1 = edge[0].charAt(0)-48;
					}
				}
				
				if(getVertex(edge[1].charAt(0))==null && getVertex(Integer.parseInt((edge[1]))) == null) {
					if (isNumber(edge[1])) {
						id2 = Integer.parseInt(edge[1]);
						addVertex(id2);
						addEdge(id2,id2,1);
					} else {
						id2 = edge[1].charAt(0)-48;
						addVertex(id2);
						addEdge(id2,id2,1);
					}
				} else {
					if (isNumber(edge[0])) {
						id2 = Integer.parseInt(edge[0]);
					} else {
						id2 = edge[0].charAt(0)-48;
					}
				}
				
				if (isNumber(edge[2]))
					addEdge(id1,id2,Integer.parseInt(edge[2]));
				else
					addEdge(id1,id2,1);
			}
		}
	}
	
	@Override
	public void incrementID() {
		return;
	}
}

class RatingsNode extends Vertex<Character> {
	
	private double r;//this node's rating
	private double v;//this node's total votes
	private EdgeList ratings; //The list of ratings this node has made
	
	RatingsNode(Character t) {
		super(t);
		r = 1.0;
		v = 1.0;
		
		//adding self to the list of nodes that have rated this node. We don't need to add self to the list we've rated because we already have a reference to this node.
		super.addEdge(new Edge(this, 1));//addEdge adds to the edgelist, which is the list of ratings this node has received
	}
	
	@Override
	public boolean addEdge(Edge e) {
		if (e.getDestination() instanceof RatingsNode)
			return super.addEdge(e);
		return false;
	}
	
	/**
	 * Adds a rating from this node to another node
	 * @param n the node being rated
	 * @param r the rating being given
	 */
	void addRating(RatingsNode n, int r) {
		if (ratings.add(new Edge(n,r))) {
			n.addEdge(new Edge(this,r));
			return;
		} else {
			ratings.remove(new Edge(n,r));
			n.rmEdge(new Edge(this,r));
			ratings.add(new Edge(n,r));
			n.addEdge(new Edge(this,r));
		}
	}
	
	/**
	 * recalculates the value of r and v and stores them
	 */
	void update() {
		double ratingSum = 0;
		double votes = 0;
		RatingsNode current;
		
		for (int x = 0; x < this.countEdges(); x++) {
			current = (RatingsNode) this.getEdges().get(x).getDestination();
			ratingSum+=this.getEdges().get(x).getLength()*current.getPower();
			votes+=current.getPower();
		}
		
		r = ratingSum/votes;
		v = votes;
	}
	
	/**
	 * Function to determine the power of a node given its rating and its total votes
	 * @param r rating of the node
	 * @param v votes the node has received
	 * @return the power of the node
	 */
	public static double getPower(double r, double v) {
		double x = 5.0 - Math.pow(1.5, 1-v);//first parameter of Math.power is the speed at which power increases relative to votes received
		return Math.pow(2,r)*x;
	}
	
	/**
	 * calculates the power of a specified rating node
	 * @param n the node whose power we are calculating
	 * @return the voting power
	 */
	public static double getPower(RatingsNode n) {
		return getPower(n.getR(), n.getV());
	}
	
	/**
	 * calculates the power of this rating node
	 * @return the power
	 */
	double getPower() {
		return getPower(this.r,this.v);
	}

	double getR() {
		return r;
	}

	void setNode(double r, double v) {
		this.r = r;
		this.v = v;
	}

	double getV() {
		return v;
	}
	
	
	
}
