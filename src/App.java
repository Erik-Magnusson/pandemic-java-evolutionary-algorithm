import Controller.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Main JavaFX class.
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        IController controller = new Controller(800,800);

        stage.setTitle("Evolutionary Algorithm");
        stage.setScene(new Scene(controller.getView(),600,450));
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }


}
