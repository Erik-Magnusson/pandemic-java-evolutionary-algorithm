package pandemic.view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

// Class for slider and accompanying labels.
public class SliderPane extends GridPane {

    private final Slider slider;
    private final Label label;
    private final Label value;

    public SliderPane(String label, double min, double max, double def) {
        super();
        this.label = new Label(label);
        this.slider = new Slider(min,max,def);
        this.value = new Label(String.valueOf(((int)def)));

        this.add(this.label,0,0);
        this.add(this.slider,0,1);
        this.add(this.value,1,0);

        this.slider.setPrefWidth(160);
        this.setHgap(10);
        this.setVgap(5);

        this.setPadding(new Insets(10,0,10,0));

        // Bind value label to slider value.
        this.slider.valueProperty().addListener((observableValue, oldValue, newValue) -> value.textProperty().setValue(
                String.valueOf((int) Math.round(newValue.doubleValue()))));
    }

    // Returns value of the slider.
    public double getValue() {
        return this.slider.getValue();
    }



}
