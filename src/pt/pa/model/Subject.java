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

    public Subject() {
        this.observerList = new ArrayList<>();
    }

    @Override
    public void addObservable(Observer... observers) {
        for (Observer obs : observers)
            if (!observerList.contains(obs))
                this.observerList.add(obs);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observerList.remove(observer);
    }

    @Override
    public void notifyObservers(Object obj) {
        for (Observer observer : this.observerList)
            observer.update(obj);
    }
}
