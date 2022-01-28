package pt.pa.graph;

import java.util.*;

/**
 * ADT Graph implementation that stores a collection of edges (and vertices) and
 * where each edge contains the references for the vertices it connects.
 * <br>
 * This is a replacement for the class GraphEdgeList responsible.
 *
* @param <V> Type of element stored at a vertex
* @param <E> Type of element stored at an edge
 *
 * @author LD_202002394
 * @version Final
 */

public class GraphAdjacencyList<V,E> implements Graph<V, E> {

    private Map<V, Vertex<V>> vertices;

    public GraphAdjacencyList() {
        this.vertices = new HashMap<>();
    }

    /**
     * Method to validate if the vertices are adjacent.
     *
     * @param u     Vertex
     * @param v     Vertex
     *
     * @return      Returns true if the vertices are adjacent, false otherwise
     */
    @Override
    public boolean areAdjacent(Vertex<V> u, Vertex<V> v) throws InvalidVertexException {
        MyVertex myU = checkVertex(u);
        MyVertex myV = checkVertex(v);

        //is there a common edge between myU.incidentEdges and myV.incidentEdges ?

        Set<Edge<E,V>> intersection = new HashSet<>(myU.incidentEdges);
        intersection.retainAll(myV.incidentEdges);

        return !intersection.isEmpty();
    }

    /**
     * Method to check the number of vertices.
     *
     * @return      Returns the number of vertices on the graph.
     */
    @Override
    public int numVertices() {
        return vertices.size();
    }

    /**
     * Method to check the number of edges.
     *
     * @return      Returns the number of edges on the graph.
     */
    @Override
    public int numEdges() {
        return edges().size();
    }

    /**
     * Method to get a collection of the vertices.
     *
     * @return      Returns a collection containing the vertices on the graph.
     */
    @Override
    public Collection<Vertex<V>> vertices() {
        List<Vertex<V>> list = new ArrayList<>();
        for (Vertex<V> vertex : vertices.values())
            list.add(vertex);
        return list;
    }

    /**
     * Method to get a collection of the edges.
     *
     * @return      Returns a collection containing the edges on the graph.
     */
    @Override
    public Collection<Edge<E, V>> edges() {
        List<Edge<E, V>> list = new ArrayList<>();
        for (Vertex<V> vertex : vertices.values())
            for (Edge<E, V> edge : checkVertex(vertex).incidentEdges)
                if (!list.contains(edge))
                    list.add(edge);
        return list;
    }

    /**
     * Method to get a collection with the incident edges of the vertex selected.
     *
     * @param v     Vertex
     *
     * @return      Returns a collection with the incident edges of the vertex.
     */
    @Override
    public Collection<Edge<E, V>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
        MyVertex vertex = checkVertex(v);
        return vertex.incidentEdges;
    }

    /**
     * Method to get the opposite vertex of the edge, given the origin vertex.
     *
     * @param v     Vertex
     * @param e     Edge
     *
     * @return      Returns the opposite vertex of the edge.
     */
    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E, V> e) throws InvalidVertexException, InvalidEdgeException {
        MyVertex vertex = checkVertex(v);
        Edge<E, V> edge = checkEdge(e);
        return edge.vertices()[0] == vertex ? edge.vertices()[1] : edge.vertices()[0];
    }

    /**
     * Method to insert a vertex into the graph.
     *
     * @param vElement     Hub
     *
     * @return      Returns the created vertex.
     */
    @Override
    public Vertex<V> insertVertex(V vElement) throws InvalidVertexException {
        if (existsVertexWith(vElement))
            throw new InvalidVertexException("There's already a vertex with this element.");
        MyVertex newVertex = new MyVertex(vElement);
        vertices.put(vElement, newVertex);
        return newVertex;
    }

    /**
     * Method to insert a vertex into the graph.
     *
     * @param edgeElement     Route
     * @param v               Vertex
     * @param u               Vertex
     *
     * @return      Returns the created edge.
     */
    @Override
    public Edge<E, V> insertEdge(Vertex<V> u, Vertex<V> v, E edgeElement) throws InvalidVertexException, InvalidEdgeException {
        for(Vertex<V> vertex : vertices.values())
        if (existsEdgeOnVertex((MyVertex) vertex, edgeElement)) {
            throw new InvalidEdgeException("There's already an edge with this element.");
        }

        MyVertex outVertex = checkVertex(u);
        MyVertex inVertex = checkVertex(v);
        MyEdge newEdge = new MyEdge(edgeElement);

        outVertex.incidentEdges.add(newEdge);
        inVertex.incidentEdges.add(newEdge);
        return newEdge;
    }

    /**
     * Method to insert an edge into the graph.
     *
     * @param vElement1     Hub
     * @param vElement2     Hub
     * @param edgeElement   Route
     *
     * @return      Returns the created edge.
     */
    @Override
    public Edge<E, V> insertEdge(V vElement1, V vElement2, E edgeElement) throws InvalidVertexException, InvalidEdgeException {
        return insertEdge(vertexOf(vElement1), vertexOf(vElement2), edgeElement);
    }

    /**
     * Method to remove a vertex from the graph.
     *
     * @param v     Vertex
     *
     * @return      Returns the deleted vertex.
     */
    @Override
    public V removeVertex(Vertex<V> v) throws InvalidVertexException {
        MyVertex vertex = checkVertex(v);
        MyVertex oppositeVertex;
        for (Edge<E, V> edge : vertex.incidentEdges) {
            oppositeVertex = (MyVertex) opposite(vertex, edge);
            oppositeVertex.incidentEdges.remove(edge);
        }
        //vertex.incidentEdges.clear();
        this.vertices.remove(vertex.element());
        return vertex.element();
    }

    /**
     * Method to remove an edge from the graph.
     *
     * @param e     Edge
     *
     * @return      Returns the deleted edge.
     */
    @Override
    public E removeEdge(Edge<E, V> e) throws InvalidEdgeException {
        Edge<E, V> edge = checkEdge(e);
        List<MyVertex> vertices = new ArrayList(this.vertices.values());
        for (MyVertex vertex : vertices)
            if (vertex.incidentEdges.contains(edge))
                vertex.incidentEdges.remove(edge);
        return edge.element();
    }

    /**
     * Method to replace a vertex on the graph.
     *
     * @param v              Vertex
     * @param newElement     Hub
     *
     * @return      Returns the old vertex.
     */
    @Override
    public V replace(Vertex<V> v, V newElement) throws InvalidVertexException {

        if (existsVertexWith(newElement))
            throw new InvalidVertexException("There's already a vertex with this element.");

        MyVertex vertex = checkVertex(v);

        V oldElement = vertex.element;
        vertex.element = newElement;

        return oldElement;
    }

    /**
     * Method to replace an edge on the graph.
     *
     * @param e              edge
     * @param newElement     route
     *
     * @return      Returns the old edge.
     */
    @Override
    public E replace(Edge<E, V> e, E newElement) throws InvalidEdgeException {

        if (existsEdgeWith(newElement)) {
            throw new InvalidEdgeException("There's already an edge with this element.");
        }

        MyEdge edge = checkEdge(e);
        E oldElement = edge.element;
        edge.element = newElement;

        return oldElement;
    }

    /**
     * Method to verify if there is already an edge with that route.
     *
     * @param edgeElement   Edge
     *
     * @return      Returns true if there is already an edge with the route, otherwise returns false.
     */
    private boolean existsEdgeWith(E edgeElement) {
        for (Edge<E, V> edge : edges())
            if (edge.element().equals(edgeElement))
                return true;
        return false;
    }

    /**
     * Method to search for the Vertex containing the given Hub.
     *
     * @param vElement   Hub
     *
     * @return      Returns vertex found related to the hub. If no vertex were found, returns null.
     */
    private MyVertex vertexOf(V vElement) {
        for (Vertex<V> v : vertices.values())
            if (v.element().equals(vElement))
                return (MyVertex) v;
        return null;
    }

    /**
     * Implementation of the specific vertex of the class {@link GraphAdjacencyList}
     *
     */
    private class MyVertex implements Vertex<V> {
        private V element;
        private List<Edge<E,V>> incidentEdges;

        /**
         * Constructor of the class MyVertex.
         *
         * @param element   Hub
         */
        public MyVertex(V element) {
            this.element = element;
            this.incidentEdges = new ArrayList<>();
        }

        /**
         * Method to get the element of the Hub.
         *
         * @return      Returns element of the hub.
         */
        @Override
        public V element() {
            return element;
        }

        /**
         * Method toString of the MyVertex class.
         *
         * @return      Returns toString of the class.
         */
        @Override
        public String toString() {
            return "Vertex{" + element + '}' + " --> " + incidentEdges.toString();
        }
    }

    /**
     * Implementation of the specific edge of the class {@link GraphAdjacencyList}
     *
     */
    private class MyEdge implements Edge<E, V> {
        private E element;

        /**
         * Constructor of the class MyVertex.
         *
         * @param element   Route
         */
        public MyEdge(E element) {
            this.element = element;
        }

        /**
         * Method to get the element of the Route.
         *
         * @return      Returns element of the route.
         */
        @Override
        public E element() {
            return element;
        }

        /**
         * Method to vertices related to the route.
         *
         * @return      Returns array of vertex linked to the route.
         */
        @Override
        public Vertex<V>[] vertices() {
            //if the edge exists, then two existing vertices have the edge
            //in their incidentEdges lists
            List<Vertex<V>> adjacentVertices = new ArrayList<>();

            for(Vertex<V> v : GraphAdjacencyList.this.vertices.values()) {
                MyVertex myV = (MyVertex) v;

                if( myV.incidentEdges.contains(this)) {
                    adjacentVertices.add(v);
                }
            }

            if(adjacentVertices.isEmpty()) {
                return new Vertex[]{null, null}; //edge was removed meanwhile
            } else {
                return new Vertex[]{adjacentVertices.get(0), adjacentVertices.get(1)};
            }
        }

        /**
         * Method toString of the MyVertex class.
         *
         * @return      Returns toString of the class.
         */
        @Override
        public String toString() {
            return "Edge{" + element + "}";
        }
    }

    /**
     * Method to verify there is a vertex with the given hub.
     *
     * @param vElement   Hub
     *
     * @return      Returns true if a vertex is found, otherwise returns false.
     */
    private boolean existsVertexWith(V vElement) {
        return vertices.containsKey(vElement);
    }

    /**
     * Method to verify the vertex has any edges.
     *
     * @param vElement   Route
     * @param vertex     Vertex
     *
     * @return      Returns true if an edge is found, otherwise returns false.
     */
    private boolean existsEdgeOnVertex(MyVertex vertex, E vElement) {
        for (Edge<E, V> edge : vertex.incidentEdges)
            if (edge.element().equals(vElement))
                return true;
        return false;
    }

    /**
     * Method get a vertex.
     *
     * @param v     Vertex
     *
     * @throws InvalidVertexException if vertex is null
     * @throws InvalidVertexException if not a vertex
     * @throws InvalidVertexException if vertex does not belong to this graph
     *
     * @return      Returns vertex found.
     */
    private MyVertex checkVertex(Vertex<V> v) throws InvalidVertexException {
        if(v == null) throw new InvalidVertexException("Null vertex.");

        MyVertex vertex;
        try {
            vertex = (MyVertex) v;
        } catch (ClassCastException e) {
            throw new InvalidVertexException("Not a vertex.");
        }

        if (!vertices.containsValue(v)) {
            throw new InvalidVertexException("Vertex does not belong to this graph.");
        }

        return vertex;
    }

    /**
     * Method get a edge.
     *
     * @param e     Edge
     *
     * @throws InvalidEdgeException if edge is null
     * @throws InvalidEdgeException if not an edge
     * @throws InvalidEdgeException if edge does not belong to this graph
     *
     * @return      Returns edge found.
     */
    private MyEdge checkEdge(Edge<E, V> e) throws InvalidEdgeException {
        if(e == null) throw new InvalidEdgeException("Null edge.");

        MyEdge edge;
        try {
            edge = (MyEdge) e;
        } catch (ClassCastException ex) {
            throw new InvalidVertexException("Not an edge.");
        }

        if (!edges().contains(edge)) {
            throw new InvalidEdgeException("Edge does not belong to this graph.");
        }

        return edge;
    }
    /**
     * Method toString of the GraphAdjacencyList class.
     *
     * @return      Returns toString of the class.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Graph | Adjacency List : \n");

        for(Vertex<V> v : vertices.values()) {
            sb.append( String.format("%s", v) );
            sb.append("\n");
        }

        return sb.toString();
    }
}
