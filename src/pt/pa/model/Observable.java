package pt.pa.model;

/**
 * Class related to the {@link Subject} on the Observer pattern.
 *
 * @author LD_202002394
 * @version Final
 */

public interface Observable {

    /**
     * Method to preset the addObservable method.
     *
     * @param observers Observer
     */
    void addObservable(Observer... observers);

    /**
     * Method to preset the removeObserver method.
     *
     * @param observer Observer
     * @deprecated No being used, created to comlete the Observer pattern
     */
    void removeObserver(Observer observer);

    /**
     * Method to preset the notifyObservers method.
     *
     * @param object Object
     */
    void notifyObservers(Object object);

}
