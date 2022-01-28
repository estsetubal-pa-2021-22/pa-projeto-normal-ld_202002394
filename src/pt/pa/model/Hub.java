package pt.pa.model;

import java.awt.*;

/**
 * Class responsible for saving the data from
 * a city (hubs).
 *
 * @author LD_202002394
 * @version Final
 */

public class Hub {

    private final String name;
    private int population;
    private Point coordinates;

    /**
     * Constructor of the class Hub. Initializes the variables from the class.
     *
     * @param name          String
     * @param population    int
     * @param coordinates    Point
     *
     */
    public Hub(String name, int population, Point coordinates) {
        this.name = name;
        this.population = population;
        this.coordinates = coordinates;
    }

    /**
     * Constructor of the class Hub. Initializes the variables from the class.
     *
     * @param name          String
     *
     */
    public Hub(String name) {
        this.name = name;
        this.population = -1;
        this.coordinates = new Point();
    }

    /**
     * Constructor of the class Hub. Initializes the variables from the class with prefixed values.
     *
     */
    public Hub() {
        this.name = "";
        this.population = -1;
        this.coordinates = new Point();
    }

    /**
     * Setter method for the variable population.
     *
     * @param population    int
     *
     */
    public void setPopulation(int population) {
        this.population = population;
    }

    /**
     * Setter method for the variable coordinates.
     *
     * @param coordinates    Point
     *
     */
    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Getter method for the variable population.
     *
     * @return Returns population.
     *
     */
    public int getPopulation() {
        return this.population;
    }

    /**
     * Getter method for the variable coordinates.
     *
     * @return Returns coordinates.
     *
     */
    public Point getCoordinates() {
        return this.coordinates;
    }

    /**
     * toString method for the class Hub.
     *
     * @return Returns name.
     *
     */
    @Override
    public String toString() {
        return this.name;
    }

}
