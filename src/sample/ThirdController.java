package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
public class ThirdController implements Initializable {

    public MenuBar menuBar;

    @FXML
    private GridPane grid;
    private int counter = 0;
    public Slider[] sliders;
    public CheckBox[] boxes;

    private Label label;
    private int k;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String s = "Comparison for: " + Main.params[0];
        int l = Main.options.length;
        List<String[]> pairs = Tools.makePairs(Main.options);
        k = l * (l - 1) / 2;
        sliders = new Slider[k];
        Main.thirdLvl = new double[Main.params.length][k];
        boxes = new CheckBox[k];
        grid.setPadding(new Insets(10, 10, 10, 10));

        for (int i = 0; i < k; i++) {
            CheckBox box = new CheckBox();
            String[] p = pairs.get(i);
            String str = p[0] + " : " + p[1];
            Label label = new Label(str);
            label.setPrefWidth(50);
            Slider slider = new Slider(1, 9, 1);
            slider.valueProperty().addListener((obs, oldval, newVal) ->
                    slider.setValue(newVal.intValue()));
            slider.setMajorTickUnit(1);
            slider.setSnapToTicks(true);
            slider.setShowTickMarks(true);
            slider.setShowTickLabels(true);
            slider.setPrefWidth(160);

            boxes[i] = box;
            sliders[i] = slider;

            HBox hbox = new HBox(10);
            hbox.getChildren().addAll(label, slider, box);
            grid.add(hbox, 0, i);
        }

        label = new Label(s);
        grid.add(label, 0, k + 1);

        Button btn = new Button("Submit");
        btn.setOnAction(e -> renew());
        grid.add(btn, 0, k + 2);
    }


    private String getWinner() {
        double[] res = Tools.finalResult();
        List<Pair> words = new ArrayList<>();
        for (int i = 0; i < res.length; i++)
            words.add(new Pair(Main.options[i], res[i]));
        words.sort(Comparator.comparing(p -> -p.d()));
        String win = "";
        for (Pair p : words) win += p.present() + "\n";
        return win;
    }

    private void renew() {
        if (counter < Main.params.length) {
            for (int i = 0; i < k; i++) {
                double g = sliders[i].getValue();
                if (boxes[i].isSelected()) g = 1 / g;
                Main.thirdLvl[counter][i] = g;
                sliders[i].setValue(1);
                boxes[i].setSelected(false);
            }
            counter++;
            if (counter != Main.params.length) {
                String s = "Comparing for: " + Main.params[counter];
                label.setText(s);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("RESULTS");
                alert.setHeaderText("Winner was determined!");
                alert.setContentText("Results table:\n" + getWinner());
                alert.showAndWait();
                System.exit(0);
            }
        }
    }
}

