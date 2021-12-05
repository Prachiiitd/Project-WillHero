package WillHero;

import Exceptions.WorldNotExistException;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {

    private World newGame;
    private LeaderBoard leaderBoard;

    public MainMenu() {
        this.newGame = new World();
        this.leaderBoard = new LeaderBoard();
    }

    @FXML
    private Label bestReward;
    @FXML
    private Label bestLocation;
    @FXML
    private ImageView floatingName;
    @FXML
    private ImageView newGameIcon;
    @FXML
    private ImageView resumeGameIcon;
    @FXML
    private ImageView exit;
    @FXML
    private ImageView leaderboardIcon;
    @FXML
    private ImageView settingIcon;

    private void exit(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"You're about to Exit!", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Exit");
        alert.initStyle(StageStyle.UNDECORATED);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() &&  result.get() == ButtonType.YES){
            stage.close();
        }
    }

//    @FXML
//    private void onHover(){
//        StaticFunction.onHover(newGameIcon, Rotate.X_AXIS);
//    }

    public void start(Stage stage) throws IOException {

//        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        URL toScene = getClass().getResource("MainMenu.fxml");
        StaticFunction.setScene(stage, toScene, "Main Menu");

        stage.setOnCloseRequest(event -> {
            event.consume();
            exit(stage);
        });
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

        showLocation();
        showReward();
    }


    public void newGame(MouseEvent newGameIcon) throws FileNotFoundException {
        StaticFunction.clickResponse(this.newGameIcon);

        Image icon = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("mainIcon.png")).getPath()));
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(35);
        imageView.setPreserveRatio(true);

        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.initStyle(StageStyle.UNDECORATED);
        textInputDialog.setHeaderText(null);
        textInputDialog.setContentText("Enter Your Name");
        textInputDialog.setGraphic(imageView);

        Label label = new Label();

        Optional<String> result = textInputDialog.showAndWait();
        System.out.println((textInputDialog.getEditor().getText()));

        result.ifPresent(name -> {
            label.setText(name);

            if (label.getText().length() == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING,"Name Can not be blank", ButtonType.OK);
                alert.initStyle(StageStyle.UNDECORATED);
                alert.setHeaderText(null);
                alert.showAndWait();

            } else {

                newGame = new World();
                try {
                    newGame.start(StaticFunction.getStage(newGameIcon), label);
                } catch (WorldNotExistException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    public void resumeGame(MouseEvent resumeGameIcon) {
        StaticFunction.clickResponse(this.resumeGameIcon);
    }

    public void setting(MouseEvent settingIcon) {
        StaticFunction.clickResponse(this.settingIcon);
    }

    public void showLeaderboard(MouseEvent leaderboardIcon) {
        StaticFunction.clickResponse(this.leaderboardIcon);

        leaderBoard = new LeaderBoard();
        try {
            leaderBoard.start(StaticFunction.getStage(leaderboardIcon));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLocation(){
       /*TODO find out the best location reached so far from database*/
        int best_location = 212;
        bestLocation.setText(String.valueOf(best_location));
    }

    public void showReward(){
        /*TODO find out the best reward got so far from database*/
        int best_reward = 212;
        bestReward.setText(String.valueOf(best_reward));
    }

    public void exit(MouseEvent exit) {
        StaticFunction.clickResponse(this.exit);
        exit(StaticFunction.getStage(exit));
    }
}































