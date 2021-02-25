package View;

import Model.IObservable;

// Implemented by classed which observe IObservable.
public interface IObserver {

    void update(IObservable o, int currentGen);
}
