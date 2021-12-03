package WillHero;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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

    public void start(Stage stage) throws IOException {

//        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        Parent root = FXMLLoader.load(Objects.requireNonNull(WillHero.class.getResource("mainMenu.fxml")));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            exit(stage);
        });
    }

    public void exit(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"You're about to Exit!", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Exit");

        if (alert.showAndWait().get() == ButtonType.YES){
            stage.close();
        }
    }

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

        Node node = (Node) exit.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        exit(stage);
    }
}