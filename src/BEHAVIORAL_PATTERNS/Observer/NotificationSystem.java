package BEHAVIORAL_PATTERNS.Observer;

import java.util.ArrayList;
import java.util.List;

public class NotificationSystem {
    private List<Observer> observers = new ArrayList<>();
    public void subscribe(Observer observer){
        observers.add(observer);
        System.out.println("Subscribed: " + ((User) observer).name);
    }

    public void notifyUser(String message){
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
