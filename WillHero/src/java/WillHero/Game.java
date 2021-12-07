package WillHero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class Game implements Initializable {

    private final Player player;
    private VBox vBox;
    private StackPane stackPane;
    private Group screenObj;
    private ArrayList<Platform> platforms;
    public ImageView island11;
    public ImageView island21;
    public ImageView island211;
    public ImageView island011;
    private Timeline tl;


    @FXML
    private AnchorPane gameAnchorPane;
    @FXML
    private AnchorPane screenAnchorPane;
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
    private Text floatingName;
    @FXML
    private ImageView mainMenuIcon;
    @FXML
    private ImageView restartIcon;
    @FXML
    private ImageView saveIcon;
    @FXML
    private ImageView resumeIcon;
    @FXML
    private Label bestLocation;
    @FXML
    private Label bestReward;

    public Game() {

        player = new Player(new Label(), 0);
        currLocation = new Label();
        currReward = new Label();
        bestLocation = new Label();
        bestReward = new Label();
   }

    public Game(Label nameLabel) {
        player = new Player(nameLabel, 0);
        vBox  = new VBox();
        stackPane = new StackPane();
        currLocation = new Label();
        currReward = new Label();
        bestLocation = new Label();
        bestReward = new Label();
        screenAnchorPane = new AnchorPane();
        gameAnchorPane = new AnchorPane();
        screenObj = new Group();
        platforms = new ArrayList<>();
    }

    public void start(Stage stage, Label nameLabel) throws IOException {

        gameAnchorPane  = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        screenAnchorPane =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ArenaScreen.fxml")));
        gameAnchorPane.setBackground(StaticFunction.defaultBackground());
        stackPane.getChildren().add(gameAnchorPane);
        stackPane.getChildren().add(screenAnchorPane);

        Image icon = new Image(new FileInputStream(Objects.requireNonNull(StaticFunction.class.getResource("mainIcon.png")).getPath()));
        stage.setTitle("WillHero: " + player.getName().getText());
        stage.getIcons().add(icon);
        vBox.getChildren().add(stackPane);
        Scene scene = new Scene(vBox);

        tl = new Timeline(new KeyFrame(Duration.millis(10), e -> playGame(gameAnchorPane, player)));
        tl.setCycleCount(Timeline.INDEFINITE);
        buildWorld(platforms,screenObj);
        showStats(player);


        stackPane.setOnKeyPressed(new EventHandler<KeyEvent> () {
            int var = 0;
            @Override
            public void handle(KeyEvent event)
            {
                Node node=(Node) event.getSource();
                VBox vBox=(VBox) node.getScene().getRoot();
                StackPane stackPane = (StackPane) vBox.getChildren().get(0);

                System.out.println(player.getPlayer().getX() + " " + player.getPlayer().getY());
                if(stackPane.getChildren().size() == 2) return;

                if(event.getCode().isWhitespaceKey()) {
                    player.getPlayer().setX(player.getPlayer().getX() + 5);
                }
                if(event.getCode().isArrowKey()) {
                    player.getPlayer().setY(player.getPlayer().getY() + 5);
                }
                if(event.getCode().isDigitKey()) {
                    screenObj.setTranslateX(screenObj.getTranslateX() - 200);
                    var+=60;
                }
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StaticFunction.setTranslation(floatingName, 0, 100, 1000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(island2, 0, 25, 2000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(island11, 0, -25, 2000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(island211, 0, 25, 2000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(island21, 1, 20, 2000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(island011, 0, -25, 2000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(island1, 0, 25, 2000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(greenOrc1, 0, 40, 1000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(hero, 0, 40, 1000,TranslateTransition.INDEFINITE, true);

        StaticFunction.bestLocation(bestLocation);
        StaticFunction.bestReward(bestReward);

        if(!player.isAlive()) gameOver();
    }


    public void buildWorld(ArrayList<Platform> platforms, Group screenObj) {

        platforms.add(new Platform(0, 150, 350, 700, 0));
        platforms.add(new Platform(2, 900, 350, 400, 0));
        platforms.add(new Platform(3, 1800, 350, 400, 50));
        for (Platform platform : platforms) {
            screenObj.getChildren().add(platform.getIsLand());
        }
        gameAnchorPane.getChildren().add(screenObj);
        gameAnchorPane.getChildren().add( player.getPlayer());
        player.setScoreLabel(currReward,currLocation);
    }


    public void showStats(Player player) {
        /*TODO find out the best location reached so far from database*/
        System.out.println(player.getName().getText() + " " + player.getLocation() + " " + player.getReward());
        player.setLocation(player.getLocation() + 20);
        System.out.println(player.getName().getText() + " " + player.getLocation() + " " + player.getReward());
    }


    public void playGame(AnchorPane gameAnchorPane, Player player) {
        player.setLocation(player.getLocation() + 1);
        System.out.println(player.getLocation());
    }


    public void pause(MouseEvent pause) {
        StaticFunction.clickResponse(this.pauseIcon);

        Node node=(Node) pause.getSource();
        VBox vBox=(VBox)node.getScene().getRoot();
        StackPane stackPane = (StackPane) vBox.getChildren().get(0);

        try {
            Parent loadArena =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ArenaScreen.fxml")));
            stackPane.getChildren().add(loadArena);


        } catch (IOException e) {
            System.out.println("ArenaScreen couldn't be loaded");
            e.printStackTrace();
        }
    }


    public void mainMenu(MouseEvent mainMenu) {
        StaticFunction.clickResponse(this.mainMenuIcon);

        URL toScene = getClass().getResource("MainMenu.fxml");
        try {
            StaticFunction.setScene(StaticFunction.getStage(mainMenu), toScene, "WillHero: Main Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restart(MouseEvent restart) {
        StaticFunction.clickResponse(this.restartIcon);

        Game restarted = new Game();

//        try {
//            StaticFunction.setScene(StaticFunction.getStage(restart), toScene, "WillHero: " + player.getName().getText());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

//    private void jump(){
//        TranslateTransition translate = new TranslateTransition();
//        {
//            translate.setDuration(Duration.millis(200));
//            translate.setCycleCount(2);
//            translate.setAutoReverse(true);
//            Node ball;
//            translate.setNode(ball);
//        }
//        translate.setByY(-40);
//        translate.play();
//    }

    public void gameOver() {
        /*TODO yha se kaam krna h tumhe prachi*/

    }

    public void resumeGame(MouseEvent resume) {
        StaticFunction.clickResponse(this.resumeIcon);

        Node node=(Node) resume.getSource();
        VBox vBox=(VBox) node.getScene().getRoot();
        StackPane stackPane = (StackPane) vBox.getChildren().get(0);
        try {
            stackPane.getChildren().remove(stackPane.getChildren().get(1));
            System.out.println("\nremoved ");
        } catch (NullPointerException e){
            System.out.println("Already Deleted");
        }
    }
}
