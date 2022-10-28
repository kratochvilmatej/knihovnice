package cz.kratochvil.knihovnice;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("/resources/Login.fxml"));
        stage.setScene(new Scene(root, 400, 400));
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
