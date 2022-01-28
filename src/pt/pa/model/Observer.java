package pt.pa.model;

/**
 * Class only used to be notified y by the subject on the Observer Pattern.
 *
 * @author LD_202002394
 * @version Final
 */

public interface Observer {

    /**
     * Method to preset the update method.
     *
     * @param obj Object
     */
    void update(Object obj);

}
