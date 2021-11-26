package pt.pa.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

       manager.createEdge(new Route(manager.getHub("Waco, TX"),manager.getHub("Vincennes, IN"),10));

       assertEquals(3, manager.countNeighbors("Waco, TX"));

       manager.removeEdge(manager.getRoute("Waco, TX", "Vincennes, IN"));
       manager.removeEdge(manager.getRoute("Waco, TX","Wichita Falls, TX"));

       assertEquals(1, manager.countNeighbors("Waco, TX"));
    }

    @Test
    void checkCreateVertex() {
        Hub hub = new Hub("Almada, PT");
        manager.createVertex(hub);
        assertThrows(InvalidVertexException.class, () -> {
            manager.createVertex(hub);
        });
    }

    @Test
    void checkCreateEdge() {
        Route route = new Route(manager.getHub("Weed, CA"),manager.getHub("Walla Walla, WA"), 600);
        manager.createEdge(route);
        assertThrows(InvalidEdgeException.class, () -> {
            manager.createEdge(route);
        });
    }

    @Test
    void checkRemoveVertex() {
        Hub hub = manager.getHub("Weed, CA");
        manager.removeVertex(hub);
        assertThrows(InvalidVertexException.class, () -> {
            manager.removeVertex(hub);
        });
    }

    @Test
    void checkRemoveEdge() {
        Route route = manager.getRoute("Weed, CA", "Yakima, WA");
        manager.removeEdge(route);
        assertThrows(InvalidEdgeException.class, () -> {
            manager.removeEdge(route);
        });
    }

    @Test
    void checkIsIsolated() {

    }
}