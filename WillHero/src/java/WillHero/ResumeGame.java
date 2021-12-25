package WillHero;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ResumeGame {

    @FXML
    private ImageView backIcon;
//    @FXML
//    private ImageView play;

    public void start(Stage stage) throws IOException {

        URL toScene = getClass().getResource("Saved.fxml");
        StaticFunction.setScene(stage, toScene, "WillHero: Saved");
    }

    public void setBack(MouseEvent backIcon) throws IOException {
        StaticFunction.clickResponse(this.backIcon);
//        Loader loadgame = new Loader();
//        loadgame.bringIn();
        URL toScene = getClass().getResource("MainMenu.fxml");
        StaticFunction.setScene(StaticFunction.getStage(backIcon), toScene, "WillHero: Main Menu");
    }

}