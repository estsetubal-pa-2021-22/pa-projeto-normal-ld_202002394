package pt.pa.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperties;
import pt.pa.graph.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NetworkManagerTest {

    NetworkManager manager;

    @BeforeEach
    void setUp() {
        this.manager = new NetworkManager("dataset/sgb32", "routes_1.txt");
    }

    /*
     1 - Check if initial number of vertices is right
     2 - Add Vertex
     3 - Check if number of vertices increases
     4 - Remove Vertex
     5 - Check if number of vertices decreases
    */
    @Test
    void numberOfVerticesChanges_After_InsertAndRemoveHub() {

    }

    /*
     1 - Check if initial number of edges is right
     2 - Add Edge
     3 - Check if number of edges increases
     4 - Remove Edge
     5 - Check if number of edges decreases
    */
    @Test
    void numberOfEdgesChanges_After_InsertAndRemoveRoute() {

    }

    /*
     1 - Check if there are any isolated hubs initially
     2 - Add Hub
     3 - Check if Hub is isolated
     4 - Add Route connected to new Hub
     5 - Check if Hub is still isolated
    */
    @Test
    void checkHubIsIsolated_After_InsertHubAndRoute() {

    }

    /*
     1 - Check and count what are the neighbors of a Hub
     2 - Add neighbor
     3 - Check and count if neighbors of the initial hub has increased
    */
    @Test
    void checkHubNeighbors_After_InsertNewNeighbor() {

    }

    /*
     1 - Check what are the 5 top Hubs with most neighbors
    */
    @Test
    void checkShortestPathAndDistance() {

    }

    /*
     1 - Check if breadthFirstOrder search is right
     2 - Check if depthFirstOrder search is right
    */
    @Test
    void checkSearchAlgorithms() {

    }

    @Test
    @DisplayName("Checks if the Hub with most neighbors is the expected Hub")
    void checkTop5HubsCentrality() {
        Hub mostNeighbors = manager.top5Centrality().get(0);
        Map<Hub, Integer> centralityMap = manager.getCentrality();

        for(Hub hub : centralityMap.keySet())
            assertTrue(manager.countNeighbors(mostNeighbors) >= centralityMap.get(hub));
    }

    @Test
    @DisplayName("Checks if getHub returns the correct value")
    void checkGetHub() {
        assertNull(manager.getHub("Lisboa, PT"));
        assertNotNull(manager.getHub("Weed, CA"));
    }

    @Test
    @DisplayName("Checks if getVertex returns the correct value")
    void checkGetVertex() {
        assertNotNull(manager.getVertex("Waco, TX"));
        assertNull(manager.getVertex("Seixal, PT"));
    }

    @Test
    @DisplayName("Verify if routes exist for Waco, TX")
    void checkGetRoute(){
        assertNotEquals(null,manager.getRoute("Waco, TX","Wichita Falls, TX"));
        assertEquals(null, manager.getRoute("Waco, TX","Vincennes, IN"));
    }

    @Test
    @DisplayName("Verify all neighbors for Waco, TX")
    void checkGetNeighbors(){

       List<Hub> neighbors = manager.getNeighbors(manager.getHub("Waco, TX"));

       assertTrue(neighbors.contains(manager.getHub("Victoria, TX")));
       assertTrue(neighbors.contains(manager.getHub("Wichita Falls, TX")));
       assertFalse(neighbors.contains(manager.getHub("Waco, TX")));

    }

    @Test
    @DisplayName("Verify if numbers of neighbors is correct for Waco, TX ")
    void checkCountNeighbors(){
       assertEquals(2, manager.countNeighbors("Waco, TX"));

       manager.createEdge(manager.getHub("Waco, TX"),manager.getHub("Vincennes, IN"),new Route(10));

       assertEquals(3, manager.countNeighbors("Waco, TX"));

       manager.removeEdge(manager.getRoute("Waco, TX", "Vincennes, IN"));
       manager.removeEdge(manager.getRoute("Waco, TX","Wichita Falls, TX"));

       assertEquals(1, manager.countNeighbors("Waco, TX"));
    }

    @Test
    @DisplayName("Checks if a hub can be added as a vertex 2 times in a row")
    void checkCreateVertex() {
        Hub hub = new Hub("Almada, PT");
        manager.createVertex(hub);
        assertThrows(InvalidVertexException.class, () -> {
            manager.createVertex(hub);
        });
    }

    @Test
    @DisplayName("Checks if a route can be added as an edge 2 times in a row")
    void checkCreateEdge() {
        Route route = new Route(600);

        manager.createEdge(manager.getHub("Weed, CA"),manager.getHub("Walla Walla, WA"), route);
        assertThrows(InvalidEdgeException.class, () -> {
            manager.createEdge(manager.getHub("Weed, CA"),manager.getHub("Walla Walla, WA"), route);
        });
    }

    @Test
    @DisplayName("Checks if a hub can be removed as a vertex 2 times in a row")
    void checkRemoveVertex() {
        Hub hub = manager.getHub("Weed, CA");
        manager.removeVertex(hub);
        assertThrows(InvalidVertexException.class, () -> {
            manager.removeVertex(hub);
        });
    }

    @Test
    @DisplayName("Checks if a route can be removed as an edge 2 times in a row")
    void checkRemoveEdge() {
        Route route = manager.getRoute("Weed, CA", "Yakima, WA");
        manager.removeEdge(route);
        assertThrows(InvalidEdgeException.class, () -> {
            manager.removeEdge(route);
        });
    }

    @Test
    @DisplayName("Checks if a hub is isolated upon new vertex, and is not isolated after adding a neighbor")
    void checkIsIsolated() {
        manager.createVertex(new Hub("Almada, PT"));
        assertTrue(manager.isIsolated(manager.getHub("Almada, PT")));
        manager.createEdge(manager.getHub("Weed, CA"),manager.getHub("Almada, PT"), new Route(250));
        assertFalse(manager.isIsolated(manager.getHub("Almada, PT")));
    }

    @Test
    @DisplayName("Check if initial graph has no isolated hub initally, then has 1 isolated hub after inserting new one")
    void checkCountIsolated() {
        assertEquals(0,manager.countIsolatedHubs());
        manager.createVertex(new Hub("Almada, PT"));
        assertEquals(1,manager.countIsolatedHubs());
    }

    @Test
    @DisplayName("Check that there are no isolated hubs initially, then has 1 isolated hub after inserting one")
    void checkGetIsolatedHubs() {
        assertTrue(manager.getIsolatedHubs().isEmpty());
        manager.createVertex(new Hub("Almada, PT"));
        assertFalse(manager.getIsolatedHubs().isEmpty());
    }

    @Test
    @DisplayName("Check if initial graph has 2 components, then has 3 components after adding new isolated hub")
    void checkComponents() {
        assertEquals(2,manager.components());
        manager.createVertex(new Hub("Almada, PT"));
        assertEquals(3,manager.components());
    }

    @Test
    @DisplayName("Check if the shortest path it being properly calculated, before and after the removal of a hub in the shortest path")
    void checkShortestPathTotalDistance() {
        assertEquals(2356,manager.shortestPathTotalDistance(manager.getHub("Victoria, TX"),manager.getHub("Wausau, WI")));
        manager.removeVertex(manager.getHub("Waterloo, IA"));
        assertEquals(2407,manager.shortestPathTotalDistance(manager.getHub("Victoria, TX"),manager.getHub("Wausau, WI")));

    }

    @Test
    @DisplayName("Check if the AreHubsInSameComponent is working as expected by preventing the Dijkstra from being calculated")
    void verifyAreHubsInSameComponent() {
        manager.removeVertex(manager.getHub("Watertown, SD"));
        assertThrows(IncompatibleHubsException.class, () -> {
            manager.shortestPathTotalDistance(manager.getHub("Victoria, TX"),manager.getHub("Winnipeg, MB"));
        });
    }

    @Test
    @DisplayName("Check if the shortest path it being calculated correctly")
    void checkShortestPath() {

        assertEquals("[Victoria, TX, Wichita Falls, TX, Wichita, KS, Yankton, SD, Waterloo, IA, Wausau, WI]",
                     manager.shortestPath(manager.getHub("Victoria, TX"),manager.getHub("Wausau, WI")).toString());
    }

    @Test
    @DisplayName("Validate DepthFirstSearch")
    void checkDepthFirstSearch() {

        assertEquals("[Weed, CA, Yakima, WA, Walla Walla, WA, Wenatchee, WA]",
                     manager.depthFirstSearch(manager.getHub("Weed, CA")).toString());

    }

    @Test
    @DisplayName("Validate BreathFirstSearch")
    void checkBreathFirstSearch() {

        assertEquals("[Weed, CA, Yakima, WA, Wenatchee, WA, Walla Walla, WA]",
                manager.breadthFirstSearch(manager.getHub("Weed, CA")).toString());

    }

    @Test
    @DisplayName("Validate replace vertex before and after the vertex has been removed")
    void checkReplaceVertex() {

        assertNotNull(manager.getVertex("Weed, CA"));
        manager.getGraph().replace(manager.getVertex("Weed, CA"),new Hub("Testing, TS"));
        assertNull(manager.getVertex("Weed, CA"));
        assertNotNull(manager.getVertex("Testing, TS"));

    }

    @Test
    @DisplayName("Validate replace edge before and after the vertex has been removed")
    void checkReplaceEdge() {

        assertEquals(595,manager.getEdge(manager.getRoute("Weed, CA","Yakima, WA")).element().getDistance());
        manager.getGraph().replace(manager.getEdge(manager.getRoute("Weed, CA","Yakima, WA")),new Route(150));
        assertEquals(150,manager.getEdge(manager.getRoute("Weed, CA","Yakima, WA")).element().getDistance());

    }

}