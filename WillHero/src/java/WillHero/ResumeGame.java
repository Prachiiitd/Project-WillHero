package WillHero;


import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ResumeGame {

    @FXML
    private ImageView backIcon;

    private AnchorPane anchorPane;

    public void start(Stage stage, AnchorPane anchorPane) throws IOException {
        this.anchorPane = anchorPane;
    }

    private void resumeGame(){

    }

    public void setBack(MouseEvent backIcon) throws IOException {
        StaticFunction.clickResponse(this.backIcon);
        URL toScene = getClass().getResource("MainMenu.fxml");
        StaticFunction.setScene(StaticFunction.getStage(backIcon), toScene, "WillHero: Main Menu");
    }
}