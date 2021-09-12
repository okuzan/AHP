package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SndController implements Initializable {

    public MenuBar menuBar;

    @FXML
    private GridPane grid;
    public Slider[] sliders;
    public CheckBox[] boxes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int l = Main.params.length;
        boolean huge = l >= 6;
        List<String[]> pairs = Tools.makePairs(Main.params);
        int k = l * (l - 1) / 2;
        sliders = new Slider[k];
        boxes = new CheckBox[k];
        grid.setHgap(20);
        grid.setPadding(new Insets(10, 10, 10, 10));
        for (int i = 0; i < k; i++) {
            CheckBox box = new CheckBox();
            String[] p = pairs.get(i);
            String s = p[0] + " : " + p[1];
            Label label = new Label(s);
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
            if (huge && i >= (k / 2)) grid.add(hbox, 1, i - (k / 2));
            else grid.add(hbox, 0, i);
        }

        Button btn3 = new Button();
        btn3.setText("Clear");
        btn3.setOnAction(e -> {
            for (int i = 0; i < sliders.length; i++) {
                sliders[i].setValue(1);
                boxes[i].setSelected(false);
            }
        });

        Button btn = new Button();
        btn.setText("Proceed");
        btn.setOnAction(e -> {
            try {
                getInput();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        Button btn2 = new Button();
        btn2.setText("Back");
        btn2.setOnAction(e -> {
            Main.setPane(0);
            Main.stage.sizeToScene();
        });

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(btn2, btn3, btn);

        grid.add(hbox, 0, k + 1);
        GridPane.setMargin(btn, new Insets(10, 0, 0, 0));
        GridPane.setHalignment(btn, HPos.CENTER);

        Window win = Main.root.getScene().getWindow();
        if (huge) {
            win.setWidth(560);
            win.setHeight((k / 2.0) * 45 + 50);
        } else {
            win.setWidth(290);
            win.setHeight(k * 45 + 70);
        }
    }


    private void getInput() throws IOException {
        int k = sliders.length;
        double[] ans = new double[k];
        for (int i = 0; i < k; i++) {
            int v = (int) sliders[i].getValue();
            if (boxes[i].isSelected()) ans[i] = 1.0 / v;
            else ans[i] = v;
        }
        Main.sndLvl = ans;
        Main.kids.add(FXMLLoader.load(getClass().getResource("views/thirdWin.fxml")));
        Main.setPane(2);
        Main.stage.sizeToScene();
    }
}
