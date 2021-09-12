package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class Controller implements Initializable {

    public MenuBar menuBar;
    public TextArea paramsArea;
    public TextArea optionsArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void getInput() throws IOException {
        String[] opts = Main.options;
        String[] pars = Main.params;

        if (optionsArea.getText().trim().equals("") || optionsArea.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No data entered");
            alert.setContentText("Fill the required forms!");
            alert.showAndWait();
        } else {
            Main.options = Arrays.stream(optionsArea.getText().trim().split("\n")).
                    filter(x -> !x.isEmpty()).toArray(String[]::new);
            Main.params = Arrays.stream(paramsArea.getText().trim().split("\n")).
                    filter(x -> !x.isEmpty()).toArray(String[]::new);
            boolean dupe = false;
            Set<String> set = new HashSet<>();
            for (String name : Main.options) if (!set.add(name)) dupe = true;
            for (String name : Main.params) if (!set.add(name)) dupe = true;

            if (dupe) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Duplicates");
                alert.setContentText("Two options|parameters can't have the same name!");
                alert.showAndWait();

            } else if (Main.options.length > 8 || Main.params.length > 8 ||
                    Main.options.length < 2 || Main.params.length < 2) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Too much data");
                alert.setContentText("Maximum entries allowed 8, minimum 2 required!");
                alert.showAndWait();

            } else {
                if (!Arrays.equals(pars, Main.params) || !Arrays.equals(opts, Main.options)) {
                    System.out.println(Arrays.toString(pars));
                    System.out.println(Arrays.toString(Main.params));
                    System.out.println(Arrays.toString(opts));
                    System.out.println(Arrays.toString(Main.options));
                    System.out.println("different");
                    Parent p = Main.kids.get(0);
                    Main.kids.clear();
                    Main.kids.add(p);
                    Main.kids.add(FXMLLoader.load(getClass().getResource("views/sndWin.fxml")));
                }
                Main.setPane(1);
                Main.stage.sizeToScene();
            }
        }
    }

    public void about(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Guide");
        alert.setHeaderText("How to enter your data");
        alert.setContentText("1 Win: names of your options and parameters."+
                "\n2 Win: compare your parameters with each other" +
                "\n3 Win: compare options based on parameters" +
                "\n! Use checkbox to indicate 1/X value " +
                "\n(to set that A compares to B as 1 to 3," +
                "\nselect 3 on the slider and tick corresponding checkbox).");
        alert.showAndWait();

    }
}
