package WillHero;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ResumeGame {

    @FXML
    private ImageView backIcon;

    public void start(Stage stage) throws IOException {

        URL toScene = getClass().getResource("ResumeGame.fxml");
        StaticFunction.setScene(stage, toScene, "Leaderboard");
    }

    public void setBack(MouseEvent backIcon) throws IOException {
        StaticFunction.clickResponse(this.backIcon);

        URL toScene = getClass().getResource("MainMenu.fxml");
        StaticFunction.setScene(StaticFunction.getStage(backIcon), toScene, "WillHero: Main Menu");
    }
}