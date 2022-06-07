//TODO:
//  (1) Implement the graph!
//  (2) Update this code to meet the style and JavaDoc requirements.

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
/**
 * Project 4 for CS310.
 * @author Corey
 *
 */
class ThreeTenGraph	implements Graph<ThreeTenNode, ThreeTenEdge>, DirectedGraph<ThreeTenNode, ThreeTenEdge> {

	//********************************************************************************
	//   YOU MAY instantiate this storage with a ThreeTenMap or HashMap
	//   IF YOU CHOOSE TO USE java.util.HashMap, but you must use
	//   this variable (with this name) for the map storage
	//   (we will be checking that your map has the right values!).
	//********************************************************************************
	/**
	 * Stores a map of a map of Nodes and Edges.
	 */
	HashMap<ThreeTenNode,HashMap<ThreeTenNode,ThreeTenEdge>> storage;

	/**
	 * Number of edges in the graph.
	 */
	private int edges;

	/**
	 * Number of vertices in the graph.
	 */
	private int vertices;

	//********************************************************************************
	//   HINTS:
	//   - You'll save yourself a lot of headache if you deep copy all collections
	//     rather than returning the original collections (what if you return a HashSet
	//     and someone modifies it?!).
	//   - You'll probably want some additional storage for quickly finding other
	//     things...
	//   - ArrayList, HashSet, HashMap, ThreeTenSet, and ThreeTenMap all implement
	//     the collection interface. So if you're supposed to return a collection,
	//     then return one of those.
	//********************************************************************************

	/**
	 * Creates a new graph. Initializing all appropriate instance variables.
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenGraph() {
		this.storage = new HashMap<>();
	}

	/**
	 * Returns a view of all edges in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees 
	 * about the ordering of the vertices within the set.
	 * @return a Collection view of all edges in this graph
	 */
	public Collection<ThreeTenEdge> getEdges() {

		Set<ThreeTenNode> keys = this.storage.keySet();
		Collection<ThreeTenEdge> edges = new ArrayList<>();

		for(ThreeTenNode node:keys) {
			edges.addAll(this.storage.get(node).values());
		}

		return edges;
	}

	/**
	 * Returns a view of all vertices in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees 
	 * about the ordering of the vertices within the set.
	 * @return a Collection view of all vertices in this graph
	 */
	public Collection<ThreeTenNode> getVertices() {
		return this.storage.keySet();
	}

	/**
	 * Returns the number of edges in this graph.
	 * @return the number of edges in this graph
	 */
	public int getEdgeCount() {
		return this.edges;
	}

	/**
	 * Returns the number of vertices in this graph.
	 * @return the number of vertices in this graph
	 */
	public int getVertexCount() {
		return this.vertices;
	}

	/**
	 * If directed_edge is a directed edge in this graph, returns the source; 
	 * otherwise returns null. 
	 * The source of a directed edge d is defined to be the vertex for which  
	 * d is an outgoing edge.
	 * directed_edge is guaranteed to be a directed edge if 
	 * its EdgeType is DIRECTED. 
	 * @param directedEdge the directed edge wanting the source of
	 * @return  the source of directed_edge if it is a directed edge in this graph, or null otherwise
	 */
	public ThreeTenNode getSource(ThreeTenEdge directedEdge) {

		if(this.getEdges().contains(directedEdge))
		{

			Set<ThreeTenNode> set = this.storage.keySet();

			for(ThreeTenNode node : set) {
				if(this.storage.get(node).containsValue(directedEdge)) {
					return node;
				}
			}

		}

		return null;
	}

	/**
	 * If directed_edge is a directed edge in this graph, returns the destination; 
	 * otherwise returns null. 
	 * The destination of a directed edge d is defined to be the vertex 
	 * incident to d for which  
	 * d is an incoming edge.
	 * directed_edge is guaranteed to be a directed edge if 
	 * its EdgeType is DIRECTED. 
	 * @param directedEdge the directed edge wanting the destination of
	 * @return  the destination of directed_edge if it is a directed edge in this graph, or null otherwise
	 */
	public ThreeTenNode getDest(ThreeTenEdge directedEdge) {

		if(this.getEdges().contains(directedEdge))
		{

			Set<ThreeTenNode> set = this.storage.keySet();

			for(ThreeTenNode node : set) {
				if(this.storage.get(node).containsValue(directedEdge)) {

					Set<ThreeTenNode> set2 = this.storage.get(node).keySet();

					for(ThreeTenNode node2 : set2) {
						if(this.storage.get(node).get(node2) == directedEdge)
							return node2;
					}
				}
			}

		}
		return null;
	}

	/**
	 * Returns a Collection view of the predecessors of vertex 
	 * in this graph.  A predecessor of vertex is defined as a vertex v 
	 * which is connected to 
	 * vertex by an edge e, where e is an outgoing edge of 
	 * v and an incoming edge of vertex.
	 * @param vertex	the vertex whose predecessors are to be returned
	 * @return  a Collection view of the predecessors of vertex in this graph
	 */
	public Collection<ThreeTenNode> getPredecessors(ThreeTenNode vertex) {

		Collection<ThreeTenNode> set = new ArrayList<>();;

		Collection<ThreeTenNode> nodeSet = this.storage.keySet();

		for(ThreeTenNode node : nodeSet) {

			if(this.storage.get(node).containsKey(vertex)) {
				set.add(node);
			}

		}

		return set;
	}

	/**
	 * Returns a Collection view of the successors of vertex 
	 * in this graph.  A successor of vertex is defined as a vertex v 
	 * which is connected to 
	 * vertex by an edge e, where e is an incoming edge of 
	 * v and an outgoing edge of vertex.
	 * @param vertex	the vertex whose predecessors are to be returned
	 * @return  a Collection view of the successors of vertex in this graph
	 */
	public Collection<ThreeTenNode> getSuccessors(ThreeTenNode vertex) {

		Collection<ThreeTenNode> nodeSet = this.storage.get(vertex).keySet();

		return nodeSet;
	}

	/**
	 * Returns a Collection view of the incoming edges incident to vertex
	 * in this graph.
	 * @param vertex	the vertex whose incoming edges are to be returned
	 * @return  a Collection view of the incoming edges incident to vertex in this graph
	 */
	public Collection<ThreeTenEdge> getInEdges(ThreeTenNode vertex) {

		Collection<ThreeTenEdge> edges = new ArrayList<>();

		Set<ThreeTenNode> set = this.storage.keySet();

		for(ThreeTenNode node : set) {

			if(this.storage.get(node).containsKey(vertex)) {
				edges.add(this.storage.get(node).get(vertex));
			}

		}

		return edges;
	}

	/**
	 * Returns a Collection view of the outgoing edges incident to vertex
	 * in this graph.
	 * @param vertex	the vertex whose outgoing edges are to be returned
	 * @return  a Collection view of the outgoing edges incident to vertex in this graph
	 */
	public Collection<ThreeTenEdge> getOutEdges(ThreeTenNode vertex) {

		if(vertex == null) {
			return null;
		}
		Collection<ThreeTenNode> nodes = this.storage.get(vertex).keySet();
		Collection<ThreeTenEdge> edges = new ArrayList<>();

		for(ThreeTenNode node : nodes) {

			edges.add(this.storage.get(vertex).get(node));

		}


		return edges;
	}

	/**
	 * Returns an edge that connects v1 to v2.
	 * If this edge is not uniquely
	 * defined (that is, if the graph contains more than one edge connecting 
	 * v1 to v2), any of these edges 
	 * may be returned.  findEdgeSet(v1, v2) may be 
	 * used to return all such edges.
	 * Returns null if either of the following is true:
	 * <ul>
	 * <li/>v1 is not connected to v2
	 * <li/>either v1 or v2 are not present in this graph
	 * </ul> 
	 * 
	 * <p><b>Note</b>: for purposes of this method, v1 is only considered to be connected to
	 * v2 via a given <i>directed</i> edge e if
	 * v1 == e.getSource() && v2 == e.getDest() evaluates to true.
	 * (v1 and v2 are connected by an undirected edge u if 
	 * u is incident to both v1 and v2.)
	 * @param v1 node one of connection
	 * @param v2 node two of connection
	 * @return  an edge that connects v1 to v2, or null if no such edge exists (or either vertex is not present)
	 * @see Hypergraph#findEdgeSet(Object, Object) 
	 */
	public ThreeTenEdge findEdge(ThreeTenNode v1, ThreeTenNode v2) {

		if(this.storage.get(v1).containsKey(v2)){		
			return this.storage.get(v1).get(v2);
		}



		return null;
	}

	/**
	 * Adds edge e to this graph such that it connects 
	 * vertex v1 to v2. 
	 * If this graph does not contain v1, v2, 
	 * or both, implementations may choose to either silently add 
	 * the vertices to the graph or throw an IllegalArgumentException.
	 * If this graph assigns edge types to its edges, the edge type of
	 * e will be the default for this graph.
	 * See Hypergraph.addEdge() for a listing of possible reasons
	 * for failure. In addition, this should fail if the vertices or edge
	 * violates any given restrictions (such as invalid IDs).
	 * Equivalent to addEdge(e, new Pair(v1, v2)).
	 * @param e the edge to be added
	 * @param v1 the first vertex to be connected
	 * @param v2 the second vertex to be connected
	 * @return true if the add is successful, false otherwise
	 * @see Hypergraph#addEdge(Object, Collection)
	 * @see #addEdge(Object, Object, Object, EdgeType)
	 */
	public boolean addEdge(ThreeTenEdge e, ThreeTenNode v1, ThreeTenNode v2) {

		if(!storage.containsKey(v1) || !storage.containsKey(v2))
			throw new IllegalArgumentException();

		storage.get(v1).put(v2, e);
		storage.get(v2).put(v1, e);
		this.edges++;

		return true;
	}

	/**
	 * Adds vertex to this graph.
	 * Fails if vertex is null or already in the graph.
	 * Also fails if the vertex violates and constraints given in
	 * the project (such as ID restrictions).
	 * 
	 * @param vertex	the vertex to add
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if vertex is null
	 */
	public boolean addVertex(ThreeTenNode vertex) {

		if(vertex == null || this.storage.containsKey(vertex) )
			return false;

		this.storage.put(vertex, new HashMap<ThreeTenNode,ThreeTenEdge>());
		this.vertices++;
		return true;
	}

	/**
	 * Removes edge from this graph.
	 * Fails if edge is null, or is otherwise not an element of this graph.
	 * 
	 * @param edge the edge to remove
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean removeEdge(ThreeTenEdge edge) {


		Collection<ThreeTenNode> set = this.storage.keySet();
		ArrayList<ThreeTenNode> nodes = new ArrayList<>();

		for(ThreeTenNode node : set) {
			Set<ThreeTenNode> set2 = this.storage.get(node).keySet();

			for(ThreeTenNode node2 : set2) {	
				if(this.storage.get(node).get(node2) == edge) {
					nodes.add(node2);
				}	
			}	

			this.storage.get(node).keySet().removeIf(entry -> this.storage.get(node).get(entry) == edge);

		}


		this.edges--;

		return true;
	}

	/**
	 * Removes vertex from this graph.
	 * As a side effect, removes any edges e incident to vertex if the 
	 * removal of vertex would cause e to be incident to an illegal
	 * number of vertices.  (Thus, for example, incident hyperedges are not removed, but 
	 * incident edges--which must be connected to a vertex at both endpoints--are removed.) 
	 * 
	 * <p>Fails under the following circumstances:
	 * <ul>
	 * <li/>vertex is not an element of this graph
	 * <li/>vertex is null
	 * </ul>
	 * 
	 * @param vertex the vertex to remove
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean removeVertex(ThreeTenNode vertex) {

		if(vertex == null)
			return false;



		Collection<ThreeTenEdge> edges = this.getIncidentEdges(vertex);

		for(ThreeTenEdge edge : edges) {
			this.removeEdge(edge);
		}

		this.storage.remove(vertex);


		this.vertices--;

		return true;
	}


	/**
	 * Returns true if this graph's vertex collection contains vertex.
	 * Equivalent to getVertices().contains(vertex).
	 * @param vertex the vertex whose presence is being queried
	 * @return true iff this graph contains a vertex vertex
	 */
	public boolean containsVertex(ThreeTenNode vertex) {
		return getVertices().contains(vertex);
	}

	/**
	 * Returns true if this graph's edge collection contains edge.
	 * Equivalent to getEdges().contains(edge).
	 * @param edge the edge whose presence is being queried
	 * @return true iff this graph contains an edge edge
	 */
	public boolean containsEdge(ThreeTenEdge edge) {
		return getEdges().contains(edge);
	}

	/**
	 * Returns true if vertex and edge 
	 * are incident to each other.
	 * Equivalent to getIncidentEdges(vertex).contains(edge) and to
	 * getIncidentVertices(edge).contains(vertex).
	 * @param vertex node you with to check
	 * @param edge edge you wish to check
	 * @return true if vertex and edge are incident to each other
	 */
	public boolean isIncident(ThreeTenNode vertex, ThreeTenEdge edge) {
		return getIncidentEdges(vertex).contains(edge);
	}

	/**
	 * Returns true if v1 and v2 share an incident edge.
	 * Equivalent to getNeighbors(v1).contains(v2).
	 * 
	 * @param v1 the first vertex to test
	 * @param v2 the second vertex to test
	 * @return true if v1 and v2 share an incident edge
	 */
	public boolean isNeighbor(ThreeTenNode v1, ThreeTenNode v2) {
		return getNeighbors(v1).contains(v2);
	}

	/**
	 * Returns the collection of vertices in this graph which are connected to edge.
	 * Note that for some graph types there are guarantees about the size of this collection
	 * (i.e., some graphs contain edges that have exactly two endpoints, which may or may 
	 * not be distinct).  Implementations for those graph types may provide alternate methods 
	 * that provide more convenient access to the vertices.
	 * 
	 * @param edge the edge whose incident vertices are to be returned
	 * @return  the collection of vertices which are connected to edge, or null if edge is not present
	 */
	public Collection<ThreeTenNode> getIncidentVertices(ThreeTenEdge edge) {
		if(!containsEdge(edge)) return null;
		ArrayList<ThreeTenNode> nodes = new ArrayList<>();
		nodes.add(getSource(edge));
		nodes.add(getDest(edge));
		return nodes;
	}

	/**
	 * Returns the collection of vertices which are connected to vertex
	 * via any edges in this graph.
	 * If vertex is connected to itself with a self-loop, then 
	 * it will be included in the collection returned.
	 * 
	 * @param vertex the vertex whose neighbors are to be returned
	 * @return  the collection of vertices which are connected to vertex, or null if vertex is not present
	 */
	public Collection<ThreeTenNode> getNeighbors(ThreeTenNode vertex) {
		if(!containsVertex(vertex)) return null;
		ArrayList<ThreeTenNode> nodes = new ArrayList<>();
		nodes.addAll(getPredecessors(vertex));
		for(ThreeTenNode n : getSuccessors(vertex)) {
			if(!nodes.contains(n)) {
				nodes.add(n);
			}
		}
		return nodes;
	}

	/**
	 * Returns the collection of edges in this graph which are connected to vertex.
	 * 
	 * @param vertex the vertex whose incident edges are to be returned
	 * @return  the collection of edges which are connected to vertex, or null if vertex is not present
	 */
	public Collection<ThreeTenEdge> getIncidentEdges(ThreeTenNode vertex) {
		if(!containsVertex(vertex)) return null;
		ArrayList<ThreeTenEdge> edges = new ArrayList<>();
		edges.addAll(getInEdges(vertex));
		edges.addAll(getOutEdges(vertex));
		return edges;
	}

	/**
	 * Returns true if v1 is a predecessor of v2 in this graph.
	 * Equivalent to v1.getPredecessors().contains(v2).
	 * @param v1 the first vertex to be queried
	 * @param v2 the second vertex to be queried
	 * @return true if v1 is a predecessor of v2, and false otherwise.
	 */
	public boolean isPredecessor(ThreeTenNode v1, ThreeTenNode v2) {
		return getPredecessors(v1).contains(v2);
	}

	/**
	 * Returns true if v1 is a successor of v2 in this graph.
	 * Equivalent to v1.getSuccessors().contains(v2).
	 * @param v1 the first vertex to be queried
	 * @param v2 the second vertex to be queried
	 * @return true if v1 is a successor of v2, and false otherwise.
	 */
	public boolean isSuccessor(ThreeTenNode v1, ThreeTenNode v2) {
		return getSuccessors(v1).contains(v2);
	}

	/**
	 * Returns the number of edges incident to vertex.  
	 * Special cases of interest:
	 * <ul>
	 * <li/> Incident self-loops are counted once.
	 * <li> If there is only one edge that connects this vertex to
	 * each of its neighbors (and vice versa), then the value returned 
	 * will also be equal to the number of neighbors that this vertex has
	 * (that is, the output of getNeighborCount).
	 * <li> If the graph is directed, then the value returned will be 
	 * the sum of this vertex's indegree (the number of edges whose 
	 * destination is this vertex) and its outdegree (the number
	 * of edges whose source is this vertex), minus the number of
	 * incident self-loops (to avoid double-counting).
	 * </ul>
	 * 
	 * <p>Equivalent to getIncidentEdges(vertex).size().
	 * @param vertex the vertex whose degree is to be returned
	 * @return the degree of this node
	 * @see Hypergraph#getNeighborCount(Object)
	 */
	public int degree(ThreeTenNode vertex) {
		return getIncidentEdges(vertex).size();
	}

	/**
	 * Returns the number of vertices that are adjacent to vertex
	 * (that is, the number of vertices that are incident to edges in vertex's
	 * incident edge set).
	 * 
	 * <p>Equivalent to getNeighbors(vertex).size().
	 * @param vertex the vertex whose neighbor count is to be returned
	 * @return the number of neighboring vertices
	 */
	public int getNeighborCount(ThreeTenNode vertex) {
		return getNeighbors(vertex).size();
	}

	/**
	 * Returns the number of incoming edges incident to vertex.
	 * Equivalent to getInEdges(vertex).size().
	 * @param vertex	the vertex whose indegree is to be calculated
	 * @return  the number of incoming edges incident to vertex
	 */
	public int inDegree(ThreeTenNode vertex) {
		return getInEdges(vertex).size();
	}

	/**
	 * Returns the number of outgoing edges incident to vertex.
	 * Equivalent to getOutEdges(vertex).size().
	 * @param vertex	the vertex whose outdegree is to be calculated
	 * @return  the number of outgoing edges incident to vertex
	 */
	public int outDegree(ThreeTenNode vertex) {
		return getOutEdges(vertex).size();
	}

	/**
	 * Returns the number of predecessors that vertex has in this graph.
	 * Equivalent to vertex.getPredecessors().size().
	 * @param vertex the vertex whose predecessor count is to be returned
	 * @return  the number of predecessors that vertex has in this graph
	 */
	public int getPredecessorCount(ThreeTenNode vertex) {
		return getPredecessors(vertex).size();
	}

	/**
	 * Returns the number of successors that vertex has in this graph.
	 * Equivalent to vertex.getSuccessors().size().
	 * @param vertex the vertex whose successor count is to be returned
	 * @return  the number of successors that vertex has in this graph
	 */
	public int getSuccessorCount(ThreeTenNode vertex) {
		return getSuccessors(vertex).size();
	}

	/**
	 * Returns the vertex at the other end of edge from vertex.
	 * (That is, returns the vertex incident to edge which is not vertex.)
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return the vertex at the other end of edge from vertex
	 */
	public ThreeTenNode getOpposite(ThreeTenNode vertex, ThreeTenEdge edge) {
		Pair<ThreeTenNode> p = getEndpoints(edge);
		if(p.getFirst().equals(vertex)) {
			return p.getSecond();
		}
		else {
			return p.getFirst();
		}
	}

	/**
	 * Returns all edges that connects v1 to v2.
	 * If this edge is not uniquely
	 * defined (that is, if the graph contains more than one edge connecting 
	 * v1 to v2), any of these edges 
	 * may be returned.  findEdgeSet(v1, v2) may be 
	 * used to return all such edges.
	 * Returns null if v1 is not connected to v2.
	 * <br/>Returns an empty collection if either v1 or v2 are not present in this graph.
	 *  
	 * <p><b>Note</b>: for purposes of this method, v1 is only considered to be connected to
	 * v2 via a given <i>directed</i> edge d if
	 * v1 == d.getSource() && v2 == d.getDest() evaluates to true.
	 * (v1 and v2 are connected by an undirected edge u if 
	 * u is incident to both v1 and v2.)
	 * @param v1 is node one
	 * @param v2 is node two
	 * @return  a collection containing all edges that connect v1 to v2, or null if either vertex is not present
	 * @see Hypergraph#findEdge(Object, Object) 
	 */
	public Collection<ThreeTenEdge> findEdgeSet(ThreeTenNode v1, ThreeTenNode v2) {
		ThreeTenEdge edge = findEdge(v1, v2);
		if(edge == null) {
			return null;
		}

		ArrayList<ThreeTenEdge> ret = new ArrayList<>();
		ret.add(edge);
		return ret;

	}

	/**
	 * Returns true if vertex is the source of edge.
	 * Equivalent to getSource(edge).equals(vertex).
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return true iff vertex is the source of edge
	 */
	public boolean isSource(ThreeTenNode vertex, ThreeTenEdge edge) {
		return getSource(edge).equals(vertex);
	}

	/**
	 * Returns true if vertex is the destination of edge.
	 * Equivalent to getDest(edge).equals(vertex).
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return true iff vertex is the destination of edge
	 */
	public boolean isDest(ThreeTenNode vertex, ThreeTenEdge edge) {
		return getDest(edge).equals(vertex);
	}

	/**
	 * Adds edge e to this graph such that it connects 
	 * vertex v1 to v2.
	 * Equivalent to addEdge(e, new Pair(v1, v2)).
	 * If this graph does not contain v1, v2, 
	 * or both, implementations may choose to either silently add 
	 * the vertices to the graph or throw an IllegalArgumentException.
	 * If edgeType is not legal for this graph, this method will
	 * throw IllegalArgumentException.
	 * See Hypergraph.addEdge() for a listing of possible reasons
	 * for failure.
	 * @param e the edge to be added
	 * @param v1 the first vertex to be connected
	 * @param v2 the second vertex to be connected
	 * @param edgeType the type to be assigned to the edge
	 * @return true if the add is successful, false otherwise
	 * @see Hypergraph#addEdge(Object, Collection)
	 * @see #addEdge(Object, Object, Object)
	 */
	public boolean addEdge(ThreeTenEdge e, ThreeTenNode v1, ThreeTenNode v2, EdgeType edgeType) {
		//NOTE: Only undirected edges allowed

		if(edgeType != EdgeType.DIRECTED) {
			throw new IllegalArgumentException();
		}

		return addEdge(e, v1, v2);
	}

	/**
	 * Adds edge to this graph.
	 * Fails under the following circumstances:
	 * <ul>
	 * <li/>edge is already an element of the graph 
	 * <li/>either edge or vertices is null
	 * <li/>vertices has the wrong number of vertices for the graph type
	 * <li/>vertices are already connected by another edge in this graph,
	 * and this graph does not accept parallel edges
	 * </ul>
	 * 
	 * @param edge edge
	 * @param vertices vertices
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if edge or vertices is null, or if a different vertex set in this graph is already connected by edge, or if vertices are not a legal vertex set for edge 
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(ThreeTenEdge edge, Collection<? extends ThreeTenNode> vertices) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}

		ThreeTenNode[] vs = (ThreeTenNode[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1]);
	}

	/**
	 * Adds edge to this graph with type edge_type.
	 * Fails under the following circumstances:
	 * <ul>
	 * <li/>edge is already an element of the graph 
	 * <li/>either edge or vertices is null
	 * <li/>vertices has the wrong number of vertices for the graph type
	 * <li/>vertices are already connected by another edge in this graph,
	 * and this graph does not accept parallel edges
	 * <li/>edge_type is not legal for this graph
	 * </ul>
	 * @param edgeType type of edge
	 * @param edge edge
	 * @param vertices vertices
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if edge or vertices is null, or if a different vertex set in this graph is already connected by edge, or if vertices are not a legal vertex set for edge 
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(ThreeTenEdge edge, Collection<? extends ThreeTenNode> vertices, EdgeType edgeType) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}

		ThreeTenNode[] vs = (ThreeTenNode[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1], edgeType);
	}

	/**
	 * Returns the number of edges of type edge_type in this graph.
	 * @param edgeType the type of edge for which the count is to be returned
	 * @return the number of edges of type edge_type in this graph
	 */
	public int getEdgeCount(EdgeType edgeType) {
		if(edgeType != EdgeType.UNDIRECTED) {
			return getEdgeCount();
		}
		return 0;
	}

	/**
	 * Returns the collection of edges in this graph which are of type edge_type.
	 * @param edgeType the type of edges to be returned
	 * @return the collection of edges which are of type edge_type, or null if the graph does not accept edges of this type
	 * @see EdgeType
	 */
	public Collection<ThreeTenEdge> getEdges(EdgeType edgeType) {
		if(edgeType != EdgeType.UNDIRECTED) {
			return getEdges();
		}
		return null;
	}

	/**
	 * Returns the number of vertices that are incident to edge.
	 * For hyperedges, this can be any nonnegative integer; for edges this
	 * must be 2 (or 1 if self-loops are permitted). 
	 * 
	 * <p>Equivalent to getIncidentVertices(edge).size().
	 * @param edge the edge whose incident vertex count is to be returned
	 * @return the number of vertices that are incident to edge.
	 */
	public int getIncidentCount(ThreeTenEdge edge) {
		return getIncidentVertices(edge).size();
	}

	/**
	 * Returns the endpoints of edge as a Pair ThreeTenNode.
	 * @param edge the edge whose endpoints are to be returned
	 * @return the endpoints (incident vertices) of edge
	 */
	@SuppressWarnings("unchecked")
	public Pair<ThreeTenNode> getEndpoints(ThreeTenEdge edge) {
		Object[] ends = getIncidentVertices(edge).toArray();
		return new Pair<>((ThreeTenNode)ends[0],(ThreeTenNode)ends[1]);
	}

	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to edit/fix the JavaDocs)
	//********************************************************************************

	/**
	 *Returns a {@code Factory} that creates an instance of this graph type.
	 *@return new graph.
	 */
	public static Factory<Graph<ThreeTenNode,ThreeTenEdge>> getFactory() { 
		return new Factory<Graph<ThreeTenNode,ThreeTenEdge>> () {
			public Graph<ThreeTenNode,ThreeTenEdge> create() {
				return new ThreeTenGraph();
			}
		};
	}
	/**
	 *Returns a {@code Factory} that creates an instance of this graph type.
	 *@return new graph.
	 */
	public static Factory<DirectedGraph<ThreeTenNode,ThreeTenEdge>> getDirectedFactory() { 
		return new Factory<DirectedGraph<ThreeTenNode,ThreeTenEdge>> () {
			public DirectedGraph<ThreeTenNode,ThreeTenEdge> create() {
				return new ThreeTenGraph();
			}
		};
	}

	/**
	 * Returns the edge type of edge in this graph.
	 * @param edge edge
	 * @return the EdgeType of edge, or null if edge has no defined type
	 */
	public EdgeType getEdgeType(ThreeTenEdge edge) {
		return EdgeType.DIRECTED;
	}

	/**
	 * Returns the default edge type for this graph.
	 * 
	 * @return the default edge type for this graph
	 */
	public EdgeType getDefaultEdgeType() {
		return EdgeType.DIRECTED;
	}
}
