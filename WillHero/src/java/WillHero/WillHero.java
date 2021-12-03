package WillHero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class WillHero extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(WillHero.class.getResource("mainMenu.fxml")));
        Scene scene = new Scene(root);
        scene.setFill(new LinearGradient(
                0, 0, 0, 1,true,                  //sizing
                CycleMethod.NO_CYCLE,                 //cycling
                new Stop(0, Color.web("#01D9F8FF")),    //colors
                new Stop(1, Color.web("#C4F4FEFF")))
        );

        stage.setTitle("Will Hero");

        Image icon = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("mainIcon.png")).getPath()));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}