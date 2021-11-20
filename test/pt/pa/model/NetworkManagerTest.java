package pt.pa.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void checkTop5HubsCentrality() {

    }

    /*
     1 - Choose any 2 Hubs (that are connected)
     2 - Check if shortest path is right
     3 - Check if shortest path distance is right
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

}