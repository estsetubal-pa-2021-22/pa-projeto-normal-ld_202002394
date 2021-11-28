package pt.pa.model;

public class Route {

    private int distance;

    public Route(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return this.distance;
    }

    @Override
    public String toString() {
        return String.valueOf(this.distance);
    }

}
