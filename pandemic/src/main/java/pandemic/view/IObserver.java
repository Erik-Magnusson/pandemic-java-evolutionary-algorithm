package java.pandemic.view;

import java.pandemic.model.IObservable;

// Implemented by classed which observe IObservable.
public interface IObserver {

    void update(IObservable o, int currentGen);
}
