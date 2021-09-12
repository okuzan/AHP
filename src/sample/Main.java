package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public static double[] sndLvl;
    public static double[][] thirdLvl;
    static AnchorPane root;
    static List<Parent> kids = new ArrayList<>();
    private static int  curId = 0;
    public static String[] options;
    public static String[] params;
    public static Stage stage;

    public static void setPane(int id) {
        root.getChildren().remove(kids.get(curId));
        root.getChildren().add(kids.get(id));
        curId = id;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        options = new String[]{};
        params = new String[]{};
        root = FXMLLoader.load(getClass().getResource("views/anchor.fxml"));
        stage = primaryStage;
        primaryStage.setTitle("Hello");
        primaryStage.setScene(new Scene(root));
        primaryStage.sizeToScene();
//        primaryStage.setResizable(false);
        kids.add(FXMLLoader.load(getClass().getResource("views/start.fxml")));
        root.getChildren().add(kids.get(0));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
