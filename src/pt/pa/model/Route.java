package pt.pa.model;

/**
 * Class responsible for saving the data from
 * a route (edge).
 *
 * @author LD_202002394
 * @version Final
 */

public class Route {

    private int distance;

    public Route(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return String.valueOf(this.distance);
    }

}
