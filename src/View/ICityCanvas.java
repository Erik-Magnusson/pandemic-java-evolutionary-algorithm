package View;

import Model.ICity;
import javafx.scene.canvas.Canvas;

import java.util.List;

public interface ICityCanvas {

    void placeCities(List<ICity> cities);

    void drawConnections(List<ICity> cities);

    // Clears canvas
    void clear();

    // Returns the canvas component used to draw on.
    Canvas getCanvas();
}
