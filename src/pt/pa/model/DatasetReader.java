package pt.pa.model;

import pt.pa.model.exceptions.NonEqualHubsException;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * Class responsible for importing the graph from a given
 * file (in text) to the program.
 *
 * @author LD_202002394
 * @version Final
 */
public class DatasetReader {

    private final String folder;
    private final String routesFile;
    private final List<Hub> hubs;
    private final int[][] routes;

    /**
     * Constructor of the DatasetReader class.
     *
     * @param folder       folder
     * @param routesFile   routesFile
     */
    public DatasetReader(String folder, String routesFile) {
        this.folder = folder;
        this.routesFile = routesFile;
        this.hubs = readHubs();
        this.routes = readRoutes();
    }

    /**
     * Second constructor of the DatasetReader class.
     *
     * @param path       path
     * @param hubs       list of hubs
     */
    public DatasetReader(String path, List<Hub> hubs) {
        this.folder = path.substring(0,path.lastIndexOf("/"));
        this.routesFile = path.substring(path.lastIndexOf("/") + 1);
        this.hubs = hubs;
        this.routes = readRoutes();
    }

    /**
     * Method used to get the hubs from the class.
     *
     * @return Returns List of hubs.
     *
     */
    public List<Hub> getHubs() {
        return this.hubs;
    }

    /**
     * Method used to get the routes from the class.
     *
     * @return Returns int matrix with the routes.
     */
    public int[][] getRoutes() {
        return this.routes;
    }

    /**
     * Method the information of all the hubs.
     *
     * @return Returns a list of Hubs with all the information.
     */
    private List<Hub> readHubs() {
        List<Hub> hubs = new ArrayList<>();
        readName(hubs);
        readWeight(hubs);
        readXY(hubs);
        return hubs;
    }

    /**
     * Method that reads a generic file and returns a list of all the rows of the file.
     *
     * @param fileName String
     *
     * @return Returns a list of all the rows of the file.
     */
    private Collection<String> readFile(String fileName) {
        Collection<String> collection = new ArrayList<>();
        File file = new File(this.folder + "/" + fileName);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty() && !line.startsWith("#"))
                    collection.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return collection;
    }

    /**
     * Reads name.txt file, adds attribute "code" and "name" to Hub, returns new modified list of Hubs.
     *
     * @param hubs List of hubs
     *
     * @return Returns new modified list of Hubs.
     */
    private void readName(List<Hub> hubs) {
        for (String line : readFile("/name.txt"))
            hubs.add(new Hub(line.trim()));
    }

    /**
     * Reads weight.txt file, adds attribute "weight" to Hub.
     *
     * @param hubs List of hubs
     *
     * @return Returns new modified list of Hubs.
     */
    private void readWeight(List<Hub> hubs) {
        int i = 0;
        for (String line : readFile("/weight.txt")) {
            hubs.get(i).setPopulation(Integer.parseInt(line.trim()));
            i++;
        }
    }

    /**
     * Reads xy.txt file, adds attribute "x" and "y" to Hub.
     *
     * @param hubs List of hubs
     *
     * @return Returns new modified list of Hubs.
     */
    private void readXY(List<Hub> hubs) {
        int i = 0;
        for (String line : readFile("/xy.txt")) {
            int x = Integer.parseInt(line.split(" ")[0].trim());
            int y = Integer.parseInt(line.split(" ")[1].trim());
            hubs.get(i).setCoordinates(new Point(x,y));
            i++;
        }
    }

    /**
     * Reads routes_*.txt file, returns a matrix.
     *
     * @throws NonEqualHubsException NonEqualHubsException.
     *
     * @return Returns a matrix.
     */
    private int[][] readRoutes() throws NonEqualHubsException {
        if (readFile(routesFile).size() != hubs.size()) throw new NonEqualHubsException();
        int[][] matrix = new int[hubs.size()][hubs.size()];
        int row_index = 0;
        for (String row : readFile(routesFile)) {
            int column_index = 0;
            for (String value : row.split(" ")) {
                if (column_index < row_index && Integer.parseInt(value) != 0)
                    matrix[row_index][column_index] = Integer.parseInt(value);
                else
                    matrix[row_index][column_index] = 0;
                column_index++;
            }
            row_index++;
        }
        return matrix;
    }

}
