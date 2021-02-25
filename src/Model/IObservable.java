package Model;

import java.util.List;

// This is the interface implemented by classes which are to be observed by IObserver.
public interface IObservable {

    List<ICity> getCities();
    List<ICity> getSolution();
    int getPopulationSize();
    int getFitness();

}
