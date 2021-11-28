package pt.pa.model;

import pt.pa.graph.Graph;
import pt.pa.graph.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    private String folder;
    private String routesFile;
    private List<Vertex<Hub>> vertices;

    public FileReader(String folder, String routesFile) {
        this.folder = folder;
        this.routesFile = routesFile;
        this.vertices = new ArrayList<>();
    }

    // Create all initial vertices
    public void createVertices(Graph<Hub,Route> graph) {
        for (Hub hub : readHubs())
            this.vertices.add(graph.insertVertex(hub));
    }

    // Returns a list of Hubs with all the information
    private List<Hub> readHubs() {
        List<Hub> hubs = new ArrayList<>();
        hubs = readName(hubs);
        hubs = readWeight(hubs);
        hubs = readXY(hubs);
        return hubs;
    }

    // Reads a generic file and returns a list of all the rows of the file
    private Collection<String> readFile(String fileName) {
        Collection<String> collection = new ArrayList<>();
        File file = new File(this.folder + "/" + fileName);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty() && !line.startsWith("#"))
                    collection.add(line);
            };
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return collection;
    }

    // Reads name.txt file, adds attribute "code" and "name" to Hub, returns new modified list of Hubs
    private List<Hub> readName(List<Hub> hubs) {
        for (String line : readFile("/name.txt"))
            hubs.add(new Hub(line.trim()));
        return hubs;
    }

    // Reads weight.txt file, adds attribute "weight" to Hub, returns new modified list of Hubs
    private List<Hub> readWeight(List<Hub> hubs) {
        int i = 0;
        for (String line : readFile("/weight.txt")) {
            hubs.get(i).setPopulation(Integer.valueOf(line.trim()));
            i++;
        }
        return hubs;
    }

    // Reads xy.txt file, adds attribute "x" and "y" to Hub, returns new modified list of Hubs
    private List<Hub> readXY(List<Hub> hubs) {
        int i = 0;
        for (String line : readFile("/xy.txt")) {
            double x = Double.valueOf(line.split(" ")[0].trim());
            double y = Double.valueOf(line.split(" ")[1].trim());
            hubs.get(i).setCoordinates(x,y);
            i++;
        }
        return hubs;
    }

    // Reads routes_*.txt file, creates new Routes, adds them to the graph
    public List<Route> readRoutes(Graph<Hub,Route> graph) {
        List<Route> routes = new ArrayList<>();
        int row_index = 0;
        int column_index = 0;
        for (String row : readFile(this.routesFile)) {
            column_index = 0;
            for (String value : row.split(" ")) {
                if (row_index < column_index && Integer.valueOf(value) != 0)
                    graph.insertEdge(this.vertices.get(row_index),this.vertices.get(column_index),new Route(Integer.valueOf(value)));
                column_index++;
            }
            row_index++;
        }
        return routes;
    }

}
