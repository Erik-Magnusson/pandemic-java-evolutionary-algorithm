package pandemic.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;

import java.util.List;

public interface IView extends IObserver {
    List<Double> getSliderValues();

    void setStartButtonAction(EventHandler<ActionEvent> eventHandler);

    void setStopButtonAction(EventHandler<ActionEvent> eventHandler);

    void setExitAction(EventHandler<ActionEvent> eventHandler);

    void setHelpAction(EventHandler<ActionEvent> eventHandler);

    Pane getLayout();

    Pane getInstructions();
}
