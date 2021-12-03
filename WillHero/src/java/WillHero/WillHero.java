package WillHero;

import javafx.animation.PauseTransition;
import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class WillHero extends Application {


    public static void mainMenu(Stage stage) throws IOException {
        MainMenu mainMenu = new MainMenu();
        mainMenu.start(stage);
    }

    @Override
    public void start(Stage stage) throws IOException {

        Image start = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("start.jpg")).getPath()));
        Image icon = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("mainIcon.png")).getPath()));

        ImageView imageView = new ImageView(start);
        imageView.setFitWidth(650);
        imageView.setPreserveRatio(true);

        Group root = new Group(imageView);
        Scene scene = new Scene(root);

        stage.setTitle("Will Hero");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();

        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished( event -> {
            try {
                stage.close();
                mainMenu(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        delay.play();
    }

    public static void main(String[] args) {
        launch();
    }
}