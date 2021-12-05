package WillHero;

import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Game implements Initializable {

    private Player player;
    private final StackPane stackPane = new StackPane();

    @FXML
    private AnchorPane game;
    @FXML
    private ImageView island1;
    @FXML
    private ImageView island2;
    @FXML
    private ImageView greenOrc1;
    @FXML
    private ImageView hero;
    @FXML
    private Label currLocation;
    @FXML
    private Label currReward;
    @FXML
    private ImageView pauseIcon;

    @FXML
    private AnchorPane ArenaScreen;
    @FXML
    private ImageView floatingName;
    @FXML
    private ImageView mainMenuIcon;
    @FXML
    private ImageView restartIcon;
    @FXML
    private ImageView saveIcon;
    @FXML
    private ImageView resume1;
    @FXML
    private Text resume2;
    @FXML
    private Rectangle resume3;


    public void start(Stage stage, Label nameLabel) throws IOException {

        player = new Player(nameLabel);
        System.out.println("jjjj");
        Parent game = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        System.out.println("hi");
        Parent arenaScreen = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ArenaScreen.fxml")));

        ObservableList<Node> list = stackPane.getChildren();
        list.addAll(game, arenaScreen);

        Image icon = new Image(new FileInputStream(Objects.requireNonNull(StaticFunction.class.getResource("mainIcon.png")).getPath()));
        stage.setTitle("WillHero: " + player.getName().getText());
        stage.getIcons().add(icon);

        Scene scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
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

//        showLocation();
//        showReward();
    }

    public void showLocation(){
        /*TODO find out the best location reached so far from database*/
        int location = 100;
        currLocation.setText(String.valueOf(location));
    }

    public void showReward(){
        /*TODO find out the best reward got so far from database*/
        int reward = 100;
        currReward.setText(String.valueOf(reward));
    }


    public void pause(MouseEvent mouseEvent) {
    }

    public void mainMenu(MouseEvent mouseEvent) {
    }

    public void restart(MouseEvent mouseEvent) {
    }



    public void resumeGame(MouseEvent mouseEvent) {
    }
}
