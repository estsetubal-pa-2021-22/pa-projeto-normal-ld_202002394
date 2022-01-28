package pt.pa.model;

import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import pt.pa.graph.*;
import pt.pa.model.exceptions.IncompatibleHubsException;

import java.util.*;

/**
 * Class responsible for starting the application.
 * <br>
 * It consists of the "model" in the MVC Programming pattern.
 * <br>
 * It consists of the "subject" on the Observer pattern.
 *
 * @author LD_202002394
 * @version Final
 */

public class NetworkManager extends Subject {

    private final Graph<Hub,Route> graph;
    private List<Hub> hubs;

    /**
     * Constructor of the class NetworkManager. Initializes the variables from the class.
     *
     * @param folder        String
     * @param routesFile    String
     */
    public NetworkManager(String folder, String routesFile) {
        graph = new GraphAdjacencyList<>();
        hubs = new ArrayList<>();
        createGraphElements(new DatasetReader(folder, routesFile));
    }

    /**
     * Getter method for the variable graph.
     *
     * @return returns the variable graph.
     */
    public Graph<Hub,Route> getGraph() {
        return this.graph;
    }

    /**
     * Method to create all the vertices and edges of the graph given a dataset.
     *
     * @param datasetReader     DatasetReader
     */
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

    /**
     * Method to set the vertices position.
     *
     * @param graphView     SmartGraphPanel
     */
    public void setCoordinates(SmartGraphPanel<Hub, Route> graphView) {
        for (Vertex<Hub> vertex : graph.vertices())
            graphView.setVertexPosition(vertex, vertex.element().getCoordinates().getX(), vertex.element().getCoordinates().getY() - 25);
    }

    /**
     * Getter method for the hub variable.
     *
     * @return Returns variable hubs.
     */
    public List<Hub> getHubs(){
        return hubs;
    }

    /**
     * Getter method to get all routes from the variable graph.
     *
     * @return Returns all routes.
     */
    public List<Route> getRoutes(){
        List<Route> routes = new ArrayList<>();
        for(Edge<Route,Hub> edges : graph.edges())
            routes.add(edges.element());
        return routes;
    }

    /**
     * Method to count all the hubs.
     *
     * @return Returns the number of total routes.
     */
    public int countHubs() {
        return getHubs().size();
    }

    /**
     * Method to count all the routes.
     *
     * @return Returns the number of total routes.
     */
    public int countRoutes() {
        return getRoutes().size();
    }

    /**
     * Method that given a Hub, adds new Vertex to the graph.
     *
     * @param hub   Hub
     * @return Returns a vertex.
     * @throws InvalidVertexException Throws if the vertex is not valid
     */
    public Vertex<Hub> createVertex(Hub hub) throws InvalidVertexException {
        Vertex<Hub> vertex = graph.insertVertex(hub);
        hubs.add(hub);
        notifyObservers(this);
        return vertex;
    }

    /**
     * Method that given a Hub and a List of Hubs, adds new Vertex to the Graph
     *
     * @param hub   Hub
     * @param hubs  List hubs
     * @return Returns a vertex.
     * @throws InvalidVertexException Throws if the vertex is not valid
     */
    public Vertex<Hub> createVertex(Hub hub, List<Hub> hubs) throws InvalidVertexException {
        Vertex<Hub> vertex = graph.insertVertex(hub);
        this.hubs = hubs;
        notifyObservers(this);
        return vertex;
    }

    /**
     * Method that given a route adds a new Edge to the graph
     *
     * @param origin        Hub
     * @param destination   Hub
     * @param route         Route
     * @throws InvalidEdgeException Throws if the edge is not valid
     */
    public void createEdge(Hub origin, Hub destination, Route route) throws InvalidEdgeException {
        graph.insertEdge(getVertex(origin), getVertex(destination), route);
        notifyObservers(this);
    }

    /**
     * Method that given a Hub, removes the corresponding Vertex from the graph.
     *
     * @param hub   Hub
     * @return  Returns the corresponding vertex.
     * @throws InvalidVertexException Throws if the vertex is not valid
     */
    public Vertex<Hub> removeVertex(Hub hub) throws InvalidVertexException {
        Vertex<Hub> vertex = getVertex(hub);
        graph.removeVertex(vertex);
        hubs.remove(hub);
        notifyObservers(this);
        return vertex;
    }

    /**
     * Method that given a Route, removes the corresponding Edge from the graph.
     *
     * @param route   Route
     * @return  Returns the corresponding edge.
     * @throws InvalidVertexException Throws if the vertex is not valid
     */
    public Edge<Route,Hub> removeEdge(Route route) throws InvalidEdgeException {
        Edge<Route,Hub> edge = getEdge(route);
        if (edge == null)
            throw new InvalidEdgeException();
        this.graph.removeEdge(edge);
        notifyObservers(this);
        return edge;
    }

    /**
     * Method to check if Hub does not have neighbors.
     *
     * @param hub Hub
     * @return Returns Boolean value
     */
    public boolean isIsolated(Hub hub) {
        Vertex<Hub> vertex = getVertex(hub);
        for (Edge<Route, Hub> edge : this.graph.edges())
            if (edge.vertices()[0].equals(vertex) || edge.vertices()[1].equals(vertex))
                return false;
        return true;
    }

    /**
     * Getter method for the list of all the isolated Hubs.
     *
     * @return Returns a list.
     */
    public List<Hub> getIsolatedHubs() {
        List<Hub> list = new ArrayList<>();
        for (Vertex<Hub> vertex : this.graph.vertices())
            if (isIsolated(vertex.element()))
                list.add(vertex.element());
        return list;
    }

    /**
     * Method to count all the isolated hubs.
     *
     * @return Returns the size() of getIsolatedHubs().
     */
    public int countIsolatedHubs() {
        return getIsolatedHubs().size();
    }

    /**
     * Getter method for the corresponding name of the Hub.
     *
     * @param name  String
     * @return Returns vertex.element() or null.
     */
    public Hub getHub(String name) {
        for (Vertex<Hub> vertex : graph.vertices())
            if (vertex.element().toString().equalsIgnoreCase(name))
                return vertex.element();
        return null;
    }

    /**
     * Getter method for the corresponding vertex of the Hub.
     *
     * @param hub   String
     * @return Returns vertex or null.
     */
    public Vertex<Hub> getVertex(Hub hub) {
        for (Vertex<Hub> vertex : graph.vertices())
            if (vertex.element().equals(hub))
                return vertex;
        return null;
    }

    /**
     * Getter method for the corresponding Vertex of a city.
     *
     * @param name  String
     * @return Returns getVertex() function.
     */
    public Vertex<Hub> getVertex(String name) {
        return getVertex(getHub(name));
    }

    /**
     * Getter method to get the corresponding Route of 2 Hubs.
     *
     * @param origin        Hub
     * @param destination   Hub
     * @return Returns edge.element() or null.
     */
    public Route getRoute(Hub origin, Hub destination) {
        Vertex<Hub> originVertex = getVertex(origin);
        Vertex<Hub> destinationVertex = getVertex(destination);
        for(Edge<Route, Hub> edge:graph.edges())
            if (edge.vertices()[0].equals(originVertex) || edge.vertices()[1].equals(originVertex))
                if (edge.vertices()[0].equals(destinationVertex) || edge.vertices()[1].equals(destinationVertex))
                    return edge.element();
        return null;
    }

    /**
     * Getter method to get the corresponding Route of 2 Hub names.
     *
     * @param origin        Hub
     * @param destination   Hub
     * @return Returns getRoute() function.
     */
    public Route getRoute(String origin, String destination) {
        return getRoute(getHub(origin),getHub(destination));
    }

    /**
     * Getter method to get the corresponding Edge of a Route.
     *
     * @param route     Route
     * @return Returns edge or null.
     */
    public Edge<Route,Hub> getEdge(Route route) {
        for(Edge<Route, Hub> edge:graph.edges())
            if(edge.element().equals(route))
                return edge;
        return null;
    }

    /**
     * Getter method to list all the neighboring Hubs of a Hub
     *
     * @param hub   Hub
     * @return Returns the variable hubs
     */
    public List<Hub> getNeighbors(Hub hub) {
        List<Hub> hubs = new ArrayList<>();
        for (Edge<Route,Hub> edge: graph.incidentEdges(getVertex(hub)))
            hubs.add(graph.opposite(getVertex(hub),edge).element());
        return hubs;
    }

    /**
     * Method to count all the neighbors of a Hub.
     *
     * @param hub   Hub
     * @return Returns the total neighbors of a Hub.
     */
    public int countNeighbors(Hub hub) {
        return getNeighbors(hub).size();
    }


    /**
     * Method to count all the neighbors of a Hub name.
     *
     * @param name   String
     * @return Returns the number of neighbors.
     */
    public int countNeighbors(String name) {
        return getNeighbors(getHub(name)).size();
    }

    /**
     * Getter method to get the map of all the Hubs and the number of neighbors
     *
     * @return Returns the variable map.
     */
    public Map<Hub,Integer> getCentrality() {
        Map<Hub, Integer> map = new HashMap<>();
        for (Vertex<Hub> vertex : graph.vertices())
            map.put(vertex.element(), countNeighbors(vertex.element()));
        return map;
    }

    /**
     * Method to get the top 5 Hubs with most neighbors on descending order.
     *
     * @return Returns the variable hubs
     */
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

    /**
     * Method to check if 2 given Hubs are neighbors.
     *
     * @param origin        Hub
     * @param destination   Hub
     * @return Returns a Boolean value.
     */
    public boolean areNeighbors(Hub origin, Hub destination) {
        return getRoute(origin,destination) != null;
    }

    /**
     * Method that gets the shortest path between 2 Hubs.
     *
     * @param origin        Hub
     * @param destination   Hub
     * @return Returns the correctPath variable.
     * @throws IncompatibleHubsException Throws if Hubs is incompatible
     */
    public List<Hub> shortestPath(Hub origin, Hub destination) throws IncompatibleHubsException {
        if(hubsNotInSameComponent(origin, destination)) throw new IncompatibleHubsException();
        List<Vertex<Hub>> path = new ArrayList<>();
        minimumCostPath(getVertex(origin),getVertex(destination),path);
        List<Hub> correctPath = new ArrayList<>();
        for(Vertex<Hub> elem: path)
            correctPath.add(elem.element());
        return correctPath;
    }

    /**
     * Method that gets a collection of all the visited Hubs, by breadth first order starting at a root Hub
     *
     * @param root      Hub
     * @return Returns the list of visited Hubs.
     */
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

    /**
     * Method that gets a collection of all the visited Hubs, by depth first order starting at a root Hub
     *
     * @param root      Hub
     * @return Returns the list of visited Hubs.
     */
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

    /**
     * Method that counts the number of graph components.
     *
     * @return Returns the variable components.
     */
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

    /**
     * Method that checks if the given Hubs are in the same component.
     *
     * @param origin        Hub
     * @param destination   Hub
     * @return Returns a Boolean value.
     */
    private boolean hubsNotInSameComponent(Hub origin, Hub destination){
        List<Hub> componentList = depthFirstSearch(origin);
        for(Hub elem : componentList)
            if(elem.equals(destination))
                return false;
        return true;
    }

    /**
     * Method that finds the Vertex with minimum path cost.
     *
     * @param unvisited     Set<Vertex<Hub>>
     * @param costs         Map<Vertex<Hub>,Double>
     * @return Returns the vertex with minimum path cost.
     */
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

    /**
     * Method that finds the minimum cost of a path, given Hubs of origin and destination.
     *
     * @param origin        Vertex
     * @param destination   Vertex
     * @param localsPath    List
     * @return Returns the minimum cost of a path.
     */
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

    /**
     * Method that calculates using the Dijsktra algorithm, the cost between the origin Hub and its predecessors.
     *
     * @param orig              Vertex<Hub>
     * @param costs             Map<Vertex<Hub>,Double>
     * @param predecessors      Map<Vertex<Hub>, Vertex<Hub>>
     */
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

    /**
     * Method that gets the total distance of the shortest path between 2 Hubs.
     *
     * @param origin        Hub
     * @param destination   Hub
     * @return Returns the total distance number.
     * @throws IncompatibleHubsException Throws if Hubs are incompatible
     */
    public int shortestPathTotalDistance(Hub origin, Hub destination) throws IncompatibleHubsException {
        if(hubsNotInSameComponent(origin, destination))
            throw new IncompatibleHubsException();
        return (int) minimumCostPath(getVertex(origin),getVertex(destination),new ArrayList<>());
    }

    /**
     * Method that returns the total distance of the shortest path between any 2 Hubs
     *
     * @param path        List
     * @return Returns the total path distance.
     * @throws IncompatibleHubsException Throws if Hubs are incompatible
     */
    public int pathDistance(List<Hub> path) throws IncompatibleHubsException {
        int sum = 0;
        for (int i = 0; i < path.size(); i++)
            if (i < path.size() - 1)
                sum += getRoute(path.get(i),path.get(i+1)).getDistance();
        return sum;
    }

    /**
     * Getter method that finds the path using the Dijkstra algorithm, given the predecessor and its destination.
     *
     * @param predecessors      Map<Vertex<Hub>, Vertex<Hub>>
     * @param destination       Vertex<Hub>
     * @return Returns the List path of the hubs
     */
    private List<Hub> getDijkstraPath(Map<Vertex<Hub>, Vertex<Hub>> predecessors, Vertex<Hub> destination) {
        List<Hub> path = new ArrayList<>();
        while (destination != null) {
            path.add(destination.element());
            destination = predecessors.get(destination);
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Method to calculate the shortest path to get to the 2 farthest away hubs in the entire graph.
     * @return Returns the shortest path.
     */
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

    /**
     * Getter method to calculate the cost of a given path (list of hubs).
     *
     * @param path  List<Hub>
     * @return Returns the cost.
     */
    private int getPathCost(List<Hub> path) {
        int cost = 0;
        for (int i = 0; i < path.size(); i++)
            if (i < path.size() - 1)
                cost += getRoute(path.get(i),path.get(i+1)).getDistance();
        return cost;
    }

    /**
     * Method to calculate the shortest path of the farthest hub from a given origin
     *
     * @param origin    Hub
     * @return Returns the shortest path.
     */
    public List<Hub> farthestHub(Hub origin) {
        List<Hub> shortestPath = new ArrayList<>();
        double maxDistance = 0;
        Map<Vertex<Hub>, Vertex<Hub>> predecessors = new HashMap<>();
        Map<Vertex<Hub>, Double> costs = new HashMap<>();
        dijkstra(getVertex(origin), costs, predecessors);
        for (Vertex<Hub> v : costs.keySet())
            if (maxDistance < costs.get(v)) {
                maxDistance = costs.get(v);
                shortestPath = getDijkstraPath(predecessors, v);
            }
        return shortestPath;
    }

    /**
     * Method to get a list of hubs which their path goes through less or equal to a threshold value of routes
     * from a certain Hub
     *
     * @param origin        Hub
     * @param threshold     int
     * @return Returns a list of hubs.
     */
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

    /**
     * Getter method to get a matrix with all current available routes.
     *
     * @param vertices List<Vertex<Hub>>
     * @return Returns a matrix.
     */
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

    /**
     * Method to save the file in a specified folder with all the current routes.
     *
     * @param folderName    String
     * @return Returns the generated file name
     */
    public String saveRoutes(String folderName) {
        List<Vertex<Hub>> vertices = new ArrayList<>();
        for (Hub hub : hubs)
            vertices.add(getVertex(hub));
        FileWriter fileWriter = new FileWriter();
        fileWriter.matrixToList(getRoutesMatrix(vertices));
        return fileWriter.saveFile(folderName);
    }

}
