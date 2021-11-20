package pt.pa.model;

public class Route {

    private int distance;
    private Hub origin;
    private Hub destination;

    public Route(Hub origin, Hub destination, int distance) {
        this.distance = distance;
        this.origin = origin;
        this.destination = destination;
    }

    public Hub origin() {
        return this.origin;
    }

    public Hub destination() {
        return this.destination;
    }

    public int getDistance() {
        return this.distance;
    }

    // Returns a boolean value, if a given Hub is part of this Route
    public boolean containsHub(Hub hub) {
        return this.origin.equals(hub) || this.destination.equals(hub);
    }

    // Returns a boolean value, if a given Route is the same as this one (has the same Hubs)
    public boolean equals(Route route) {
        return this.containsHub(route.origin()) && this.containsHub(route.destination());
    }

    @Override
    public String toString() {
        return String.valueOf(this.distance);
    }

}
