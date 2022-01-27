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

    public Hub(String name, int population, Point coordinates) {
        this.name = name;
        this.population = population;
        this.coordinates = coordinates;
    }

    public Hub(String name) {
        this.name = name;
        this.population = -1;
        this.coordinates = new Point();
    }

    public Hub() {
        this.name = "";
        this.population = -1;
        this.coordinates = new Point();
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public int getPopulation() {
        return this.population;
    }

    public Point getCoordinates() {
        return this.coordinates;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
