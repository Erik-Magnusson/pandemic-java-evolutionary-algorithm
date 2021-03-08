package pandemic.view;

import pandemic.model.IObservable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// The main class of the View. Contains control panel, menu, info bar and a canvas for drawing.
public class View implements IView {

    private final int X_SIZE;
    private final int Y_SIZE;
    private final BorderPane layout = new BorderPane();
    private ICityCanvas cityCanvas;

    private Button startButton;
    private Button stopButton;
    private Label nrOfGen;
    private Label nrOfPop;
    private Label fitness;
    private List<SliderPane> sliders;

    private MenuItem exitMenuItem;
    private MenuItem helpMenuItem;
    private Pane instructions;

    public View(int x, int y) {
        X_SIZE = x;
        Y_SIZE = y;
        initComponents();
    }

    @Override
    // Clears canvas, places cities and draws new connections.
    // Also updates bottom bar to reflect current generation, fitness and population.
    public void update(IObservable observable, int currentGen) {
        cityCanvas.clear();
        cityCanvas.placeCities(observable.getCities());
        cityCanvas.drawConnections(observable.getSolution());
        nrOfPop.setText(String.valueOf(observable.getPopulationSize()));
        nrOfGen.setText(String.valueOf(currentGen));
        fitness.setText(String.valueOf(observable.getFitness()));
    }


    private void initComponents()  {
        Control menu = initMenu();
        Pane controlPanel = initControlPanel();
        Pane bottomBar = initBottomBar();
        this.cityCanvas = new CityCanvas(X_SIZE/2,Y_SIZE/2);

        this.layout.setTop(menu);
        this.layout.setCenter(controlPanel);
        this.layout.setRight(cityCanvas.getCanvas());
        this.layout.setBottom(bottomBar);

    }

    // Panel with sliders and start/stop buttons.
    private Pane initControlPanel() {
        VBox controlPanel = new VBox();

        this.sliders = new ArrayList<>();
        this.sliders.add(new SliderPane("Number of cities",2,15,10));
        this.sliders.add(new SliderPane("Starting population",10,250,100));
        this.sliders.add(new SliderPane("Number of generations",10,100,50));
        this.sliders.add(new SliderPane("Selection pressure",0,10,5));
        this.sliders.add(new SliderPane("Mutation rate",0,10,5));
        this.sliders.add(new SliderPane("Reproduction rate",0,10,5));

        this.startButton = new Button("START");
        startButton.setPrefWidth(200);
        startButton.setPrefHeight(100);
        startButton.setStyle("-fx-background-color: #31b053; -fx-text-fill: white;");

        this.stopButton = new Button("STOP");
        stopButton.setPrefWidth(200);
        stopButton.setPrefHeight(100);
        stopButton.setStyle("-fx-background-color: #c9243d; -fx-text-fill: white;");

        controlPanel.getChildren().addAll(this.sliders);
        controlPanel.getChildren().add(this.startButton);
        controlPanel.getChildren().add(this.stopButton);

        return controlPanel;
    }

    // Menu contains File -> Exit and Help -> Instructions.
    private Control initMenu() {
        MenuBar menu = new MenuBar();

        menu.getMenus().add(new Menu("File"));
        menu.getMenus().add(new Menu("Help"));

        this.exitMenuItem = new MenuItem("Exit");
        this.helpMenuItem = new MenuItem("Instructions");
        menu.getMenus().get(0).getItems().add(exitMenuItem);
        menu.getMenus().get(1).getItems().add(helpMenuItem);

        this.instructions = new VBox();
        // Load the instructions from external file.
        try {
            instructions.getChildren().add(
                    new Text(Files.readString(
                            Paths.get(System.getProperty("user.dir"), "src/main/resources/instructions.txt")
                    )));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return menu;
    }

    // Bottom info bar to showcase current generation, fitness and population.
    private Pane initBottomBar() {
        HBox bottomBar = new HBox();
        Label genLabel = new Label("Generation:");
        Label popLabel = new Label("Population:");
        Label fitLabel = new Label("Fitness:");
        this.nrOfGen = new Label("N/A");
        this.nrOfPop = new Label("N/A");
        this.fitness = new Label("N/A");

        bottomBar.getChildren().addAll(
                genLabel,
                nrOfGen,
                fitLabel,
                fitness,
                popLabel,
                nrOfPop
        );

        bottomBar.setSpacing(10);
        bottomBar.setStyle("-fx-background-color: #d4c7c9;");
        return bottomBar;
    }

    @Override
    public List<Double> getSliderValues() {
        List<Double> sliderValues = new ArrayList<>();
        this.sliders.forEach(s -> sliderValues.add(s.getValue()));
        return sliderValues;
    }

    @Override
    public void setStartButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.startButton.setOnAction(eventHandler);
    }

    @Override
    public void setStopButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.stopButton.setOnAction(eventHandler);
    }

    @Override
    public void setExitAction(EventHandler<ActionEvent> eventHandler) {
        this.exitMenuItem.setOnAction(eventHandler);
    }

    @Override
    public void setHelpAction(EventHandler<ActionEvent> eventHandler) {
        this.helpMenuItem.setOnAction(eventHandler);
    }

    @Override
    public Pane getLayout() {
        return this.layout;
    }

    @Override
    public Pane getInstructions() {
        return new VBox(this.instructions);
    }




}
