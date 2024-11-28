package observer.observerswing.model;

import java.util.ArrayList;
import java.util.List;

public class ConcreteSubject implements Subject {
    private List<Observer> observers;
    private double state;
    public ConcreteSubject(){
        observers = new ArrayList<>();
    }
    
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer each: observers){
            each.update(state);
        }
    }
    public void setState(double newState){
        state = newState;
        this.notifyObservers();
    }
}
