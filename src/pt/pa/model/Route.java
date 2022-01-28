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
    /**
     * Constructor of the class Route. Initializes the variables from the class.
     *
     * @param distance int
     *
     */
    public Route(int distance) {
        this.distance = distance;
    }
    /**
     * Getter method for the variable distance.
     *
     * @return Returns distance.
     */
    public int getDistance() {
        return this.distance;
    }

    /**
     * Setter method for the variable distance.
     *
     * @param distance  int
     *
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * toString of the class Route.
     *
     * @return String.
     *
     */
    @Override
    public String toString() {
        return String.valueOf(this.distance);
    }

}
