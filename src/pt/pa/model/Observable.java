package pt.pa.model;

public interface Observable {

    public void addObservable(Observer... observers);

    public void removeObserver(Observer observer);

    public void notifyObservers(Object object);

}
