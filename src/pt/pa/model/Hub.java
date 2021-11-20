package pt.pa.model;

public class Hub {

    private String name;
    private int population;
    private double x;
    private double y;

    public Hub(String name) {
        this.name = name;
        this.population = -1;
        this.x = -1;
        this.y = -1;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getPopulation() {
        return this.population;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
