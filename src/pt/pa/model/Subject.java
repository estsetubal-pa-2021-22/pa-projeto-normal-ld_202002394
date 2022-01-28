package pt.pa.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class related to the {@link Observable} on the Observer pattern.
 *
 * @author LD_202002394
 * @version Final
 */
public class Subject implements Observable {

    List<Observer> observerList;
    /**
     * Constructor of the class Subject. Creates an empty arraylist.
     *
     */
    public Subject() {
        this.observerList = new ArrayList<>();
    }

    /**
     * Method to add observers to the observers list.
     *
     * @param observers Observer
     */
    @Override
    public void addObservable(Observer... observers) {
        for (Observer obs : observers)
            if (!observerList.contains(obs))
                this.observerList.add(obs);
    }

    /**
     * Method to remover observer from the observers list.
     *
     * @param observer Observer
     */
    @Override
    public void removeObserver(Observer observer) {
        this.observerList.remove(observer);
    }

    /**
     * Method to notify observers when the list is updated.
     *
     * @param obj Object
     */
    @Override
    public void notifyObservers(Object obj) {
        for (Observer observer : this.observerList)
            observer.update(obj);
    }
}
