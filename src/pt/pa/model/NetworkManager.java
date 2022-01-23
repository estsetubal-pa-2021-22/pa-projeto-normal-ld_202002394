package pt.pa.model;

import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import pt.pa.graph.*;
import pt.pa.model.exceptions.IncompatibleHubsException;

import java.util.*;

public class NetworkManager extends Subject {

    private final Graph<Hub,Route> graph;
    private List<Hub> hubs;

    public NetworkManager(String folder, String routesFile) {
        graph = new GraphAdjacencyList<>();
        hubs = new ArrayList<>();
        createGraphElements(new DatasetReader(folder, routesFile));
    }

    // ALEX
    // Returns the Graph
    public Graph<Hub,Route> getGraph() {
        return this.graph;
    }

    // ALEX
    // Creates all the vertices and edges of the graph, given a dataset
    public void createGraphElements(DatasetReader datasetReader) {
        List<Hub> newHubs = new ArrayList<>(datasetReader.getHubs());
        for (Vertex<Hub> vertex : graph.vertices())
            graph.removeVertex(vertex);
        hubs.clear();
        int[][] routes = datasetReader.getRoutes();
        for (int row = 0; row < newHubs.size(); row++) {
            createVertex(newHubs.get(row));
            for (int column = 0; column < row; column++)
                if (routes[row][column] != 0)
                    createEdge(newHubs.get(row), newHubs.get(column), new Route(routes[row][column]));
        }
    }

    // ALEX
    // Sets the vertices position
    public void setCoordinates(SmartGraphPanel<Hub, Route> graphView) {
        for (Vertex<Hub> vertex : graph.vertices())
            graphView.setVertexPosition(vertex, vertex.element().getCoordinates().getX(), vertex.element().getCoordinates().getY() - 25);
    }

    // RAFA
    // Gets all the Hubs from the Graph
    public List<Hub> getHubs(){
        return hubs;
    }

    // RAFA
    // Gets all the Routes from the Graph
    public List<Route> getRoutes(){
        List<Route> routes = new ArrayList<>();
        for(Edge<Route,Hub> edges : graph.edges())
            routes.add(edges.element());
        return routes;
    }

    public int countHubs() {
        return getHubs().size();
    }

    public int countRoutes() {
        return getRoutes().size();
    }

    // ALEX
    // Given a Hub, adds a new Vertex to the graph
    public Vertex<Hub> createVertex(Hub hub) throws InvalidVertexException {
        Vertex<Hub> vertex = graph.insertVertex(hub);
        hubs.add(hub);
        notifyObservers(this);
        return vertex;
    }

    public Vertex<Hub> createVertex(Hub hub, List<Hub> hubs) throws InvalidVertexException {
        Vertex<Hub> vertex = graph.insertVertex(hub);
        this.hubs = hubs;
        notifyObservers(this);
        return vertex;
    }

    // ALEX
    // Given a Route, adds a new Edge to the graph
    public Edge<Route,Hub> createEdge(Hub origin, Hub destination, Route route) throws InvalidEdgeException {
        Edge<Route,Hub> edge = graph.insertEdge(getVertex(origin), getVertex(destination), route);
        notifyObservers(this);
        return edge;
    }

    // ALEX
    // Given a Hub, removes the corresponding Vertex from the graph and returns it
    public Vertex<Hub> removeVertex(Hub hub) throws InvalidVertexException {
        Vertex<Hub> vertex = getVertex(hub);
        graph.removeVertex(vertex);
        hubs.remove(hub);
        notifyObservers(this);
        return vertex;
    }

    // ALEX
    // Given a Route, removes the corresponding Edge from the graph and returns it
    public Edge<Route,Hub> removeEdge(Route route) throws InvalidEdgeException {
        Edge<Route,Hub> edge = getEdge(route);
        if (edge == null)
            throw new InvalidEdgeException();
        this.graph.removeEdge(edge);
        notifyObservers(this);
        return edge;
    }

    // ALEX
    // Returns a boolean value, if Hub doesn't have neighbors
    public boolean isIsolated(Hub hub) {
        Vertex<Hub> vertex = getVertex(hub);
        for (Edge<Route, Hub> edge : this.graph.edges())
            if (edge.vertices()[0].equals(vertex) || edge.vertices()[1].equals(vertex))
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
        for (Vertex<Hub> vertex : graph.vertices())
            if (vertex.element().toString().equalsIgnoreCase(name))
                return vertex.element();
        return null;
    }

    // RAFA
    // Given a Hub, returns the corresponding Vertex. Null if it doesn't find
    public Vertex<Hub> getVertex(Hub hub) {
        for (Vertex<Hub> vertex : graph.vertices())
            if (vertex.element().equals(hub))
                return vertex;
        return null;
    }

    //RAFA
    //Given a city name, returns the corresponding Vertex. Null if it doesn't find
    public Vertex<Hub> getVertex(String name) {
        return getVertex(getHub(name));
    }

    // DANIEL
    // Given 2 Hubs, returns the corresponding Route. Null if it doesn't find
    public Route getRoute(Hub origin, Hub destination) {
        Vertex<Hub> originVertex = getVertex(origin);
        Vertex<Hub> destinationVertex = getVertex(destination);
        for(Edge<Route, Hub> edge:graph.edges())
            if (edge.vertices()[0].equals(originVertex) || edge.vertices()[1].equals(originVertex))
                if (edge.vertices()[0].equals(destinationVertex) || edge.vertices()[1].equals(destinationVertex))
                    return edge.element();
        return null;
    }

    // DANIEL
    // Given 2 hub names, returns the corresponding Route. Null if it doesn't find
    public Route getRoute(String origin, String destination) {
        return getRoute(getHub(origin),getHub(destination));
    }

    // DANIEL
    // Given a Route, returns the corresponding Edge. Null if it doesn't find
    public Edge<Route,Hub> getEdge(Route route) {
        for(Edge<Route, Hub> edge:graph.edges())
            if(edge.element().equals(route))
                return edge;
        return null;
    }

    // DANIEL
    // Given a Hub, returns a list of all the neighboring Hubs (utilizar método graph.incidentEdges())
    public List<Hub> getNeighbors(Hub hub) {
        List<Hub> hubs = new ArrayList<>();
        for (Edge<Route,Hub> edge: graph.incidentEdges(getVertex(hub)))
            hubs.add(graph.opposite(getVertex(hub),edge).element());
        return hubs;
    }

    // DANIEL
    // Given a Hub, returns the number of neighbors
    public int countNeighbors(Hub hub) {
        return getNeighbors(hub).size();
    }

    // DANIEL
    // Given a Hub name, returns the number of neighbors
    public int countNeighbors(String name) {
        return getNeighbors(getHub(name)).size();
    }

    // RAFA
    // Returns a map of all the Hubs (Key) and the number of neighbors (Value)
    public Map<Hub,Integer> getCentrality() {
        Map<Hub, Integer> map = new HashMap<>();
        for (Vertex<Hub> vertex : graph.vertices())
            map.put(vertex.element(), countNeighbors(vertex.element()));
        return map;
    }

    // RAFA
    // Returns the top Hubs (max 5) with most neighbors (from method getCentrality()), on descending order
    public List<Hub> topCentrality() {
        List<Hub> hubs = new ArrayList<>();
        List<Map.Entry<Hub, Integer>> list = new ArrayList<>(getCentrality().entrySet());
        list.sort(Map.Entry.comparingByValue());

        if(list.size() <= 5)
            for (int i = list.size() - 1; i >= 0; i--) hubs.add(list.get(i).getKey());
        else
        for (int i = list.size() - 1; i > list.size() - 6; i--)
            hubs.add(list.get(i).getKey());
        return hubs;
    }

    // RAFA
    // Returns a boolean value, if 2 given Hubs are neighbors
    public boolean areNeighbors(Hub origin, Hub destination) {
        return getRoute(origin,destination) != null;
    }

    // HENRIQUE
    // Returns the shortest path between 2 Hubs
    public List<Hub> shortestPath(Hub origin, Hub destination) throws IncompatibleHubsException {
        if(hubsNotInSameComponent(origin, destination)) throw new IncompatibleHubsException();
        List<Vertex<Hub>> path = new ArrayList<>();
        minimumCostPath(getVertex(origin),getVertex(destination),path);
        List<Hub> correctPath = new ArrayList<>();
        for(Vertex<Hub> elem: path)
            correctPath.add(elem.element());
        return correctPath;
    }

    // HENRIQUE
    // Returns a collection of all the visited Hubs, by breadth first order, starting at a root Hub
    public List<Hub> breadthFirstSearch(Hub root) {
        Set<Hub> visited = new HashSet<>();
        Queue<Hub> queue = new LinkedList<>();
        List<Hub> list = new ArrayList<>();
        visited.add(root);
        queue.add(root);
        while (!queue.isEmpty()) {
            Hub v = queue.remove();
            list.add(v);
            for (Hub elem : getNeighbors(v))
                if (!visited.contains(elem)) {
                    queue.add(elem);
                    visited.add(elem);
                }
        }
        return list;
    }

    // HENRIQUE
    // Returns a collection of all the visited Hubs, by depth first order, starting at a root Hub
    public List<Hub> depthFirstSearch(Hub root) {
        Set<Hub> visited = new HashSet<>();
        Stack<Hub> stack = new Stack<>();
        List<Hub> list = new ArrayList<>();
        visited.add(root);
        stack.add(root);
        while (!stack.isEmpty()) {
            Hub v = stack.pop();
            list.add(v);
            for (Hub elem : getNeighbors(v))
                if (!visited.contains(elem)) {
                    stack.add(elem);
                    visited.add(elem);
                }
        }
        return list;
    }

    // ALEX
    // Returns the number of the graph components
    public int countComponents() {
        int components = 0;
        List<Hub> visitedHubs = new ArrayList<>();
        for (Vertex<Hub> vertex : graph.vertices())
            if (!visitedHubs.contains(vertex.element())) {
                components++;
                visitedHubs.addAll(breadthFirstSearch(vertex.element()));
            }
        return components;
    }

    //HENRIQUE
    //Verifies if the given Hubs are in the same component
    private boolean hubsNotInSameComponent(Hub origin, Hub destination){
        List<Hub> componentList = depthFirstSearch(origin);
        for(Hub elem : componentList)
            if(elem.equals(destination))
                return false;
        return true;
    }

    // HENRIQUE
    // Returns the Vertex with minimum path cost
    private Vertex<Hub> findMinVertex(Set<Vertex<Hub>> unvisited, Map<Vertex<Hub>,Double> costs) {
        Vertex<Hub> vMin = null;
        double minCost = Double.MAX_VALUE;
        for (Vertex<Hub> v : unvisited)
            if (costs.get(v) < minCost){
                vMin = v;
                minCost = costs.get(v);
            }
        return vMin;
    }

    // HENRIQUE
    // Returns the minimum cost of a path, given Hubs of origin and destination
    public double minimumCostPath(Vertex<Hub> origin, Vertex<Hub> destination, List<Vertex<Hub>> localsPath) {
        Map<Vertex<Hub>, Double> costs = new HashMap<>();
        Map<Vertex<Hub>, Vertex<Hub>> predecessors = new HashMap<>();
        dijkstra(origin, costs, predecessors);
        Vertex<Hub> v = destination;
        while (v != origin) {
            localsPath.add(0, v);
            v = predecessors.get(v);
        }
        localsPath.add(0, v);
        return costs.get(destination);
    }

    // HENRIQUE
    // Fill dijsktra table (maps costs and predecessors)
    private void dijkstra(Vertex<Hub> orig, Map<Vertex<Hub>, Double> costs, Map<Vertex<Hub>, Vertex<Hub>> predecessors) {
        List<Hub> hubs = depthFirstSearch(orig.element());
        Set<Vertex<Hub>> unvisited = new HashSet<>();
        for (Hub hub : hubs)
            unvisited.add(getVertex(hub.toString()));
        for(Vertex<Hub> v : unvisited)
            costs.put(v,Double.MAX_VALUE);
        costs.put(orig,0.0);
        Vertex<Hub> u;
        while(!unvisited.isEmpty()){
            u = findMinVertex(unvisited,costs);
            if(costs.get(u)==Double.MAX_VALUE)
                return; //case there is no path
            unvisited.remove(u); //mark as visited
            for(Edge<Route,Hub> e :graph.incidentEdges(u)){
                Vertex<Hub> v = graph.opposite(u,e); //adjacent vertex to u
                double costV = e.element().getDistance() + costs.get(u);
                if(costV < costs.get(v)){ //I found a cheaper path, so I'm going to replace the value
                    costs.put(v,costV);
                    predecessors.put(v,u);
                }
            }
        }
    }

    // HENRIQUE
    // Returns the total distance of the shortest path between any 2 Hubs
    public int shortestPathTotalDistance(Hub origin, Hub destination) throws IncompatibleHubsException{
        if(hubsNotInSameComponent(origin, destination))
            throw new IncompatibleHubsException();
        return (int) minimumCostPath(getVertex(origin),getVertex(destination),new ArrayList<>());
    }

    // Returns the total distance of the shortest path between any 2 Hubs
    public int pathDistance(List<Hub> path) throws IncompatibleHubsException {
        int sum = 0;
        for (int i = 0; i < path.size(); i++)
            if (i < path.size() - 1)
                sum += getRoute(path.get(i),path.get(i+1)).getDistance();
        return sum;
    }

    //
    private List<Hub> getDijkstraPath(Map<Vertex<Hub>, Vertex<Hub>> predecessors, Vertex<Hub> destination) {
        List<Hub> path = new ArrayList<>();
        while (destination != null) {
            path.add(destination.element());
            destination = predecessors.get(destination);
        }
        Collections.reverse(path);
        return path;
    }

    // Returns the path of the 2 farthest away hubs in the entire graph
    public List<Hub> farthestHubs(){
        List<Hub> shortestPath = new ArrayList<>();
        double maxDistance = 0;
            for (Hub hub1 : getHubs()) {
                List<Hub> path = farthestHub(hub1);
                int pathCost = getPathCost(path);
                if(maxDistance < pathCost){
                    maxDistance = pathCost;
                    shortestPath = path;
                }
            }
        return shortestPath;
    }

    // Returns the cost of a given path (list of hubs)
    private int getPathCost(List<Hub> path) {
        int cost = 0;
        for (int i = 0; i < path.size(); i++)
            if (i < path.size() - 1)
                cost += getRoute(path.get(i),path.get(i+1)).getDistance();
        return cost;
    }

    // Returns the shortest path (list of hubs) of the farthest hub from a given origin
    public List<Hub> farthestHub(Hub origin) {
        List<Hub> shortestPath = new ArrayList<>();
        double maxDistance = 0;
        Map<Vertex<Hub>, Vertex<Hub>> predecessors = new HashMap<>();
        Map<Vertex<Hub>, Double> costs = new HashMap<>();
        dijkstra(getVertex(origin), costs, predecessors);
        for(Vertex<Hub> v : costs.keySet())
            if(maxDistance < costs.get(v)){
                maxDistance = costs.get(v);
                shortestPath = getDijkstraPath(predecessors,v);
            }
        return shortestPath;
    }

    // Returns a list of Hubs which their path goes through less or equal to a threshold value of routes from a certain Hub
    public List<Hub> closeHubs(Hub origin, int threshold) {
        List<Hub> hubs = new ArrayList();
        Map<Vertex<Hub>, Vertex<Hub>> predecessors = new HashMap<>();
        Map<Vertex<Hub>, Double> costs = new HashMap<>();
        dijkstra(getVertex(origin), costs, predecessors);
        for(Vertex<Hub> v : predecessors.keySet()){
            List<Hub> path = getDijkstraPath(predecessors,v);
            if(path.size()-1 <= threshold)
                hubs.add(v.element());
        }
        return hubs;
    }

    // Returns a matrix with all current available routes
    private int[][] getRoutesMatrix(List<Vertex<Hub>> vertices) {
        int size = vertices.size();
        int[][] matrix = new int[size][size];
        for (int row = 0; row < size; row++)
            for (int column = 0; column < size; column++)
                if (areNeighbors(vertices.get(row).element(), vertices.get(column).element()))
                    matrix[row][column] = getRoute(vertices.get(row).element(), vertices.get(column).element()).getDistance();
                else
                    matrix[row][column] = 0;
        return matrix;
    }

    // Saves a file in a specified folder with all the current routes, returns the generated file name
    public String saveRoutes(String folderName) {
        List<Vertex<Hub>> vertices = new ArrayList<>();
        for (Hub hub : hubs)
            vertices.add(getVertex(hub));
        FileWriter fileWriter = new FileWriter();
        fileWriter.matrixToList(getRoutesMatrix(vertices));
        return fileWriter.saveFile(folderName);
    }

}
