package observer.observerswing;

import observer.observerswing.model.Person;
import observer.observerswing.view.WeatherSystem;

public class Main {

    public static void main(String[] args) {
        WeatherSystem system = new WeatherSystem();
        system.addPerson(new Person("Donald Trump","usa", true));
        system.addPerson(new Person("Simon Bolivar", "bo", false));
        system.addPerson(new Person("Mario Bros", "jpn", true));
        system.open();
    }
}
