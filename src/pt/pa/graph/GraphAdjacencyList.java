package pt.pa.graph;

import java.util.*;

public class GraphAdjacencyList<V,E> implements Graph<V, E> {

    private Map<V, Vertex<V>> vertices;

    public GraphAdjacencyList() {
        this.vertices = new HashMap<>();
    }

    @Override
    public boolean areAdjacent(Vertex<V> u, Vertex<V> v) throws InvalidVertexException {
        MyVertex myU = checkVertex(u);
        MyVertex myV = checkVertex(v);

        //is there a common edge between myU.incidentEdges and myV.incidentEdges ?

        Set<Edge<E,V>> intersection = new HashSet<>(myU.incidentEdges);
        intersection.retainAll(myV.incidentEdges);

        return !intersection.isEmpty();
    }

    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public int numEdges() {
        return edges().size();
    }

    @Override
    public Collection<Vertex<V>> vertices() {
        List<Vertex<V>> list = new ArrayList<>();
        for (Vertex<V> vertex : vertices.values())
            list.add(vertex);
        return list;
    }

    @Override
    public Collection<Edge<E, V>> edges() {
        List<Edge<E, V>> list = new ArrayList<>();
        for (Vertex<V> vertex : vertices.values())
            for (Edge<E, V> edge : checkVertex(vertex).incidentEdges)
                if (!list.contains(edge))
                    list.add(edge);
        return list;
    }

    @Override
    public Collection<Edge<E, V>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
        MyVertex vertex = checkVertex(v);
        return vertex.incidentEdges;
    }

    // DANIEL
    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E, V> e) throws InvalidVertexException, InvalidEdgeException {

        MyEdge edge = checkEdge(e);
        checkVertex(v);

        if (!edges().contains(v)) {
            return null;
        }

        if (edge.vertices()[0] == v) {
            return edge.vertices()[1];
        } else {
            return edge.vertices()[0];
        }
    }

    // RAFA
    @Override
    public Vertex<V> insertVertex(V vElement) throws InvalidVertexException {
        MyVertex newVertex = new MyVertex(vElement);

        vertices.put(vElement, newVertex);
        return newVertex;
    }

    // RAFA
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

    // RAFA
    @Override
    public Edge<E, V> insertEdge(V vElement1, V vElement2, E edgeElement) throws InvalidVertexException, InvalidEdgeException {
        return insertEdge(vertexOf(vElement1), vertexOf(vElement2), edgeElement);
    }

    // DANIEL
    @Override
    public V removeVertex(Vertex<V> v) throws InvalidVertexException {
        vertices.remove(v);
        return null;
    }

    // DANIEL
    @Override
    public E removeEdge(Edge<E, V> e) throws InvalidEdgeException {

        checkEdge(e);

        E element = e.element();
        edges().remove(e.element());

        return element;
    }

    // HENRIQUE
    @Override
    public V replace(Vertex<V> v, V newElement) throws InvalidVertexException {
        return null;
    }

    // HENRIQUE
    @Override
    public E replace(Edge<E, V> e, E newElement) throws InvalidEdgeException {
        return null;
    }

    private MyVertex vertexOf(V vElement) {
        for (Vertex<V> v : vertices.values())
            if (v.element().equals(vElement))
                return (MyVertex) v;
        return null;
    }

    private class MyVertex implements Vertex<V> {
        private V element;
        private List<Edge<E,V>> incidentEdges;

        public MyVertex(V element) {
            this.element = element;
            this.incidentEdges = new ArrayList<>();
        }

        @Override
        public V element() {
            return element;
        }

        @Override
        public String toString() {
            return "Vertex{" + element + '}' + " --> " + incidentEdges.toString();
        }
    }

    private class MyEdge implements Edge<E, V> {
        private E element;

        public MyEdge(E element) {
            this.element = element;
        }

        @Override
        public E element() {
            return element;
        }

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

        @Override
        public String toString() {
            return "Edge{" + element + "}";
        }
    }

    private boolean existsVertexWith(V vElement) {
        return vertices.containsKey(vElement);
    }

    private boolean existsEdgeOnVertex(MyVertex vertex, E vElement) {
        for (Edge<E, V> edge : vertex.incidentEdges)
            if (edge.element().equals(vElement))
                return true;
        return false;
    }

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
