package WillHero;

import Exceptions.WorldNotExistException;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
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
//    private MediaPlayer mediaPlayer;

    @FXML
    private Label bestReward;
    @FXML
    private Label bestLocation;
    @FXML
    private Text floatingName;
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


    public MainMenu() {
        this.newGame = new World();
        this.leaderBoard = new LeaderBoard();
    }

    private void exit(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"You're about to Exit!", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Exit");
        alert.initStyle(StageStyle.UNDECORATED);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() &&  result.get() == ButtonType.YES){
            stage.close();
        }
    }

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
        StaticFunction.setTranslation(floatingName, 0, 80, 1000,TranslateTransition.INDEFINITE, true);
        StaticFunction.bestLocation(bestLocation);
        StaticFunction.bestReward(bestReward);


//        if(mediaPlayer==null){
////            Media sound = new Media(new File("ColorSwitch\\src\\sounds\\Background.mp3").toURI().toString());
//            Media sound = null;
//            sound = new Media(new File(Objects.requireNonNull(getClass().getResource("background.mp3")).getFile()).toURI().toString());
//            mediaPlayer = new MediaPlayer(sound);
//            mediaPlayer.setStartTime(Duration.seconds(1));
//            mediaPlayer.setStartTime(Duration.seconds(100));
//            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
////            mediaPlayer.play();
//        }
//        if(!mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)){
////            mediaPlayer.play();
//        }

    }

    public void showLeaderboard(MouseEvent leaderboardIcon) {
        StaticFunction.clickResponse(this.leaderboardIcon);

        try {

            Stage stage  = StaticFunction.getStage(leaderboardIcon);
            URL toScene = getClass().getResource("LeaderBoard.fxml");
            Image icon = new Image(new FileInputStream(Objects.requireNonNull(StaticFunction.class.getResource("mainIcon.png")).getPath()));

            FXMLLoader boardAnchor = new FXMLLoader(toScene);

            AnchorPane leaderBoardPane = boardAnchor.load();
            leaderBoardPane.setBackground(StaticFunction.defaultBackground());
            Scene scene = new Scene(leaderBoardPane);

            leaderBoard = boardAnchor.getController();
            leaderBoard.start(stage, leaderBoardPane);

            stage.setTitle("WillHero: Leader Board");
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
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

        try {
            Stage stage  = StaticFunction.getStage(resumeGameIcon);
            URL toScene = getClass().getResource("Saved.fxml");
            Image icon = new Image(new FileInputStream(Objects.requireNonNull(StaticFunction.class.getResource("mainIcon.png")).getPath()));

            FXMLLoader resumeAnchor = new FXMLLoader(toScene);

            AnchorPane resumeAnchorPane = resumeAnchor.load();
            resumeAnchorPane.setBackground(StaticFunction.defaultBackground());
            Scene scene = new Scene(resumeAnchorPane);

            ResumeGame resumeGame = resumeAnchor.getController();
            resumeGame.start(stage, resumeAnchorPane);

            stage.setTitle("WillHero: Resume Game");
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setting(MouseEvent settingIcon) {
        StaticFunction.clickResponse(this.settingIcon);

        Setting setting = new Setting();
        try {
            setting.start(StaticFunction.getStage(settingIcon));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit(MouseEvent exit) {
        StaticFunction.clickResponse(this.exit);
        exit(StaticFunction.getStage(exit));
    }
}





























