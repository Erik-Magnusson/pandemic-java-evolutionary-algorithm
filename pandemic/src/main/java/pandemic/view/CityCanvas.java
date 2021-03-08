package java.pandemic.view;

import java.pandemic.model.ICity;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


// This is the class where the cities and the connections between them are drawn.
public class CityCanvas implements ICityCanvas {

    private final Canvas canvas;
    private final GraphicsContext gc;
    List<ICity> cities;

    public CityCanvas(double width, double height) {
        this.canvas = new Canvas(width,height);
        this.gc = this.canvas.getGraphicsContext2D();
    }

    @Override
    // Places colored circles on the canvas to indicate the positions of the cities.
    public void placeCities(List<ICity> cities){
        this.cities = cities;
        gc.setStroke(Color.MEDIUMVIOLETRED);
        cities.forEach(c -> gc.strokeOval(c.getX()-c.getSize()/2,c.getY()-c.getSize()/2,c.getSize(),c.getSize()));
    }

    @Override
    // Draws lines between the cities to indicate the path of the solution.
    public void drawConnections(List<ICity> cities) {
        int sz = cities.size();
        gc.setStroke(Color.BLACK);
        for (int i = 0; i <= sz; i++) {
            gc.strokeLine(cities.get(i % sz).getX(), cities.get(i % sz).getY(), cities.get((i + 1) % sz).getX(), cities.get((i + 1) % sz).getY());
        }
    }

    @Override
    // Clears the entire canvas.
    public void clear() {
        gc.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
    }

    @Override
    public Canvas getCanvas() {
        return this.canvas;
    }
}
