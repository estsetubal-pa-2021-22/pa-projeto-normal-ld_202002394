package pt.pa.model;

/**
 * Class related to the {@link Subject} on the Observer pattern.
 *
 * @author LD_202002394
 * @version Final
 */

public interface Observable {

    public void addObservable(Observer... observers);

    //deprecated
    public void removeObserver(Observer observer);

    public void notifyObservers(Object object);

}
