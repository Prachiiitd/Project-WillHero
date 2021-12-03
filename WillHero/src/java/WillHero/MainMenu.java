package WillHero;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {

    @FXML
    private ImageView floatingName;
    @FXML
    private ImageView newGame;
    @FXML
    private ImageView resumeGame;
    @FXML
    private ImageView exit;
    @FXML
    private ImageView leaderboard;
    @FXML
    private ImageView setting;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TranslateTransition trFloatingName = new TranslateTransition();
        trFloatingName.setNode(floatingName);
        trFloatingName.setByY(100);
        trFloatingName.setDuration(Duration.millis(1000));
        trFloatingName.setCycleCount(TranslateTransition.INDEFINITE);
        trFloatingName.setAutoReverse(true);
        trFloatingName.play();
    }

    private void onClickZoom(ImageView image){
        ScaleTransition scale = new ScaleTransition();
        scale.setNode(image);
        scale.setDuration(Duration.millis(200));
        scale.setCycleCount(2);
        scale.setAutoReverse(true);
        scale.setByY(0.2);
        scale.setByX(0.2);
        scale.play();
    }

    public void setNewGame(MouseEvent newGame){
        onClickZoom(this.newGame);
    }

    public void setResumeGame(MouseEvent resumeGame) {
        onClickZoom(this.resumeGame);
    }

    public void setSetting(MouseEvent setting) {
        onClickZoom(this.setting);
    }

    public void setLeaderboard(MouseEvent leaderboard) {
        onClickZoom(this.leaderboard);
    }

    public void setExit(MouseEvent exit) {
        onClickZoom(this.exit);
    }
}