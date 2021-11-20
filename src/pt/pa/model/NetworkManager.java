package pt.pa.model;

import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import pt.pa.graph.Edge;
import pt.pa.graph.Graph;
import pt.pa.graph.GraphEdgeList;
import pt.pa.graph.Vertex;

import java.util.*;

public class NetworkManager {

    private Graph<Hub,Route> graph;
    private List<Vertex<Hub>> vertices;

    public NetworkManager(String folder, String routesFile) {
        FileReader fileReader = new FileReader(folder, routesFile);
        this.vertices = new ArrayList<>();
        this.graph = new GraphEdgeList<>();
        addVertices(fileReader.readHubs());
        addEdges(fileReader.readRoutes(this.vertices));
    }

    // ALEX
    // Returns the Graph
    public Graph<Hub,Route> getGraph() {
        return this.graph;
    }

    // ALEX
    // Sets the vertices position
    public void setCoordinates(SmartGraphPanel<Hub, Route> graphView) {
        for (Vertex<Hub> vertex : graph.vertices())
            graphView.setVertexPosition(vertex, vertex.element().getX(), vertex.element().getY());
    }

    // ALEX
    // Add a given list of Hubs to the graph
    private void addVertices(List<Hub> hubs) {
        for (Hub hub : hubs) createVertex(hub);
    }

    // ALEX
    // Add a given list of Routes to the graph
    private void addEdges(List<Route> routes) {
        for (Route route : routes) createEdge(route);
    }

    // ALEX
    // Given a Hub, adds a new Vertex to the graph
    public Vertex<Hub> createVertex(Hub hub) {
        Vertex<Hub> vertex = this.graph.insertVertex(hub);
        this.vertices.add(vertex);
        return vertex;
    }

    // ALEX
    // Given a Route, adds a new Edge to the graph
    public Edge<Route,Hub> createEdge(Route route) {
        return this.graph.insertEdge(route.origin(), route.destination(), route);
    }

    // ALEX
    // Given a Hub, removes the corresponding Vertex from the graph and returns it
    public Vertex<Hub> removeVertex(Hub hub) {
        Vertex<Hub> vertex = getVertex(hub);
        this.graph.removeVertex(vertex);
        this.vertices.remove(vertex);
        return vertex;
    }

    // ALEX
    // Given a Route, removes the corresponding Edge from the graph and returns it
    public Edge<Route,Hub> removeEdge(Route route) {
        Edge<Route,Hub> edge = getEdge(route);
        this.graph.removeEdge(edge);
        return edge;
    }

    // ALEX
    // Returns a boolean value, if Hub doesn't have neighbors
    public boolean isIsolated(Hub hub) {
        for (Edge<Route, Hub> edge : this.graph.edges())
            if (edge.element().containsHub(hub))
                return false;
        return true;
    }

    // ALEX
    // Returns a list of all the isolated Hubs
    public List<Hub> getIsolatedHubs() {
        List<Hub> list = new ArrayList<>();
        for (Vertex<Hub> vertex : this.graph.vertices())
            if (isIsolated(vertex.element()))
                list.add(vertex.element());
        return list;
    }

    // ALEX
    // Returns the number of isolated Hubs
    public int countIsolatedHubs() {
        return getIsolatedHubs().size();
    }

    // RAFA
    // Given a name, returns the corresponding Hub. Null if it doesn't find
    public Hub getHub(String name) {
        return null;
    }

    // RAFA
    // Given a Hub, returns the corresponding Vertex. Null if it doesn't find
    public Vertex<Hub> getVertex(Hub hub) {
        return null;
    }

    // DANIEL
    // Given 2 Hubs, returns the corresponding Route. Null if it doesn't find
    public Route getRoute(Hub origin, Hub destination) {
        return null;
    }

    // DANIEL
    // Given a Route, returns the corresponding Edge. Null if it doesn't find
    public Edge<Route,Hub> getEdge(Route route) {
        return null;
    }

    // DANIEL
    // Given a Hub, returns a list of all the neighboring Hubs (utilizar método graph.incidentEdges())
    public List<Hub> getNeighbors(Hub hub) {
        return null;
    }

    // DANIEL
    // Given a Hub, returns the number of neighbors
    public int countNeighbors(Hub hub) {
        return 0;
    }

    // RAFA
    // Returns a map of all the Hubs (Key) and the number of neighbors (Value)
    public Map<Hub,Integer> getCentrality() {
        return null;
    }

    // RAFA
    // Returns the top 5 Hubs with most neighbors (from method getCentrality()), on descending order
    public List<Hub> top5Centrality() {
        return null;
    }

    // HENRIQUE
    // Returns a boolean value, if 2 given Hubs are neighbors
    public boolean areNeighbors(Hub origin, Hub destination) {
        return false;
    }

    // HENRIQUE
    // Returns the shortest path between any 2 Hubs
    public List<Hub> shortestPath(Hub origin, Hub destination) {
        return null;
    }

    // HENRIQUE
    // Returns a collection of all the visited Hubs, by breadth first order, starting at a root Hub
    public List<Hub> breadthFirstSearch(Hub root) {
        return null;
    }

    // HENRIQUE
    // Returns a collection of all the visited Hubs, by depth first order, starting at a root Hub
    public List<Hub> depthFirstSearch(Hub root) {
        return null;
    }

    // HENRIQUE
    // Returns the number of the graph components
    public int components() {
        return 0;
    }

    // HENRIQUE
    // Returns the Vertex with minimum path cost
    private Vertex<Hub> findMinVertex(Set<Vertex<Hub>> unvisited, Map<Vertex<Hub>,Double> costs) {
        return null;
    }

    // HENRIQUE
    // Returns the minimum cost of a path, given Hubs of origin and destination
    public double minimumCostPath(Vertex<Hub> origin, Vertex<Hub> destination, List<Vertex<Hub>> localsPath) {
        return 0;
    }

    // HENRIQUE
    // Fill dijsktra table (maps costs and predecessors)
    private void dijkstra(Vertex<Hub> orig, Map<Vertex<Hub>, Double> costs, Map<Vertex<Hub>, Vertex<Hub>> predecessors) {

    }

    // HENRIQUE
    // Returns the total distance of the shortest path between any 2 Hubs
    public int shortestPathTotalDistance(Hub origin, Hub destination) {
        return 0;
    }

    // DO NOT IMPLEMENT
    // Returns a list of the farthest 2 Hubs
    public List<Hub> farthestHubs() {
        return null;
    }

    // DO NOT IMPLEMENT
    // Returns a list of Hubs which their path goes through less or equal to a threshold value from a certain Hub
    public List<Hub> closeHubs(Hub hub, int threshold) {
        return null;
    }

}
