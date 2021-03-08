package pandemic.controller;

import pandemic.model.*;
import pandemic.view.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.List;

// Controller class of MVC. Initializes the view and the model.
// Handles running the model and updating the view.
public class Controller implements IController {

    private final IView view;
    private AnimationTimer animationTimer;
    private IAlgorithm algorithm;
    private int generations;

    public Controller(int x, int y) {

        this.view = new View(x,y);

        view.setStartButtonAction(actionEvent -> runAlgorithm());
        view.setStopButtonAction(actionEvent -> animationTimer.stop());
        view.setExitAction(actionEvent -> Platform.exit());
        // Create new window for the instructions.
        view.setHelpAction(actionEvent -> {
            Parent root;
            try {
                root = view.getInstructions();
                Stage stage = new Stage();
                stage.setTitle("Instructions");
                stage.setScene(new Scene(root, 350, 300));
                stage.show();
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    // Gets the user inputs from the view and creates a model/algorithm according to the parameters.
    // Creates an AnimationTimer to handle the model and view.
    public void runAlgorithm() {

        List<Double> sliderValues = view.getSliderValues();
        World world = new World(400,400,(int) Math.round(sliderValues.get(0)));
        this.generations = (int) Math.round(sliderValues.get(2));

        this.algorithm = new Algorithm(
                world,
                (int) Math.round(sliderValues.get(1)),
                (85 + sliderValues.get(3))/100,
                (sliderValues.get(4)/10),
                1 + (sliderValues.get(5)/100));

        animationTimer = new AnimationTimer() {
            int i = 0;
            @Override
            public void handle(long l) {
                algorithm.run();
                view.update(algorithm,i+1);
                i++;
                if (i==generations) animationTimer.stop();
            }
        };
        animationTimer.start();
    }

    @Override
    public Pane getView() {
        return this.view.getLayout();
    }
}
