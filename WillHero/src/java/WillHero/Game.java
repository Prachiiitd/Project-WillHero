package WillHero;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class Game implements Initializable {

    private final Hero hero;
    public ParallelCamera camera;
    private VBox vBox;
    private StackPane stackPane;
    private Group screenObj;
    private final ArrayList<Platform> platforms;
    public ImageView island11;
    public ImageView island21;
    public ImageView island211;
    public ImageView island011;
    public Timeline tl;
    boolean gravity = true;


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
//    @FXML
//    private ImageView hero;
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

        hero = new Hero(new Label(), 0);
        currLocation = new Label();
        currReward = new Label();
        bestLocation = new Label();
        bestReward = new Label();
        platforms = new ArrayList<>();
   }

    public Game(Label nameLabel) {

        hero = new Hero(nameLabel, 0);
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

    public void start(Stage stage) throws IOException {

        gameAnchorPane  = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        screenAnchorPane =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ArenaScreen.fxml")));
        gameAnchorPane.setBackground(StaticFunction.defaultBackground());
        stackPane.getChildren().add(gameAnchorPane);
        stackPane.getChildren().add(screenAnchorPane);

        Image icon = new Image(new FileInputStream(Objects.requireNonNull(StaticFunction.class.getResource("mainIcon.png")).getPath()));
        stage.setTitle("WillHero: " + hero.getName().getText());
        stage.getIcons().add(icon);
        vBox.getChildren().add(stackPane);
        Scene scene = new Scene(vBox);
        buildWorld(platforms,screenObj);
        stackPane.setOnKeyPressed(new EventHandler<KeyEvent> () {

            @Override
            public void handle(KeyEvent event)
            {
                Node node=(Node) event.getSource();
                VBox vBox=(VBox) node.getScene().getRoot();
                StackPane stackPane = (StackPane) vBox.getChildren().get(0);

                System.out.println(hero.getHero().getX() + " " + hero.getHero().getY());
                if(stackPane.getChildren().size() == 2) return;

                if(event.getCode() == KeyCode.RIGHT)
                    hero.getHero().setX(hero.getHero().getX() + 5);
                if(event.getCode() == KeyCode.LEFT)
                    hero.getHero().setX(hero.getHero().getX() - 5);
                if(event.getCode() == KeyCode.UP)
                    hero.getHero().setY(hero.getHero().getY() + 5);
                if(event.getCode() == KeyCode.DOWN)
                    hero.getHero().setY(hero.getHero().getY() - 5);

                if(event.getCode().isDigitKey()){
                    screenObj.setLayoutX(screenObj.getLayoutX() - 30);
                }
            }
        });

        stage.setScene(scene);
        stage.show();

        tl = new Timeline(new KeyFrame(Duration.millis(200), e -> playGame(gameAnchorPane, hero, platforms, screenObj, currLocation, currReward)));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
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
//        StaticFunction.setTranslation(hero, 0, 40, 1000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setRotation(resumeIcon,360, 1000,2, true);
        StaticFunction.setRotation(restartIcon,360, 1000, 2,true);
        StaticFunction.setRotation(pauseIcon,360, 1000, 2,true);
        StaticFunction.setRotation(saveIcon,360, 1000, 2,true);
        StaticFunction.setRotation(mainMenuIcon,360, 1000, 2,true);



        StaticFunction.bestLocation(bestLocation);
        StaticFunction.bestReward(bestReward);


        if(!hero.isAlive()) gameOver();
    }

    @FXML
    public void buildWorld(ArrayList<Platform> platforms, Group screenObj) {

        platforms.add(new Platform(0, 150, 350, 700, 0));
        platforms.add(new Platform(2, 900, 350, 400, 0));
        platforms.add(new Platform(3, 1800, 350, 400, 50));
        for (Platform platform : platforms) {
            screenObj.getChildren().add(platform.getIsLand());
        }
        gameAnchorPane.getChildren().add(screenObj) ;
        gameAnchorPane.getChildren().add( hero.getHero());
        hero.setScoreLabel(currReward,currLocation);
    }

    @FXML
    public void showStats(Hero hero) {
        /*TODO find out the best location reached so far from database*/
        System.out.println("In ShowStats" + hero.getName().getText() + " " + hero.getLocation() + " " + hero.getReward());
        hero.setLocation(hero.getLocation() + 5);
        System.out.println("In ShowStats" + hero.getName().getText() + " " + hero.getLocation() + " " + hero.getReward());
    }

    @FXML
    public void playGame(AnchorPane gameAnchorPane, Hero hero, ArrayList<Platform> platforms, Group screenObj, Label currReward, Label currLocation) {

        if(!hero.isAlive()) {
            gameOver();
            return;
        }

        for (Platform platform : platforms) {
            System.out.println("ID:" + platform.getId() + " X:" + (screenObj.getTranslateX() + platform.getIsLand().getX()) + " Y:" + (screenObj.getTranslateY()+platform.getIsLand().getY()));
            if(platform.collided(hero.getHero(), screenObj)) {
                System.out.println("\t /h/hCollided with platform: " + platform.getId());
//                gravity = false;
//                hero.jump();
            } else gravity = true;
        }

        System.out.println("gravity: " + gravity);

//        gravity(hero.getHero(),gravity);
    }



    public void gravity(Node node, boolean isGravity) {
        if(isGravity) {
            System.out.println("Gravity");
            node.setTranslateY(node.getTranslateY() + 2);
        }
    }


    @FXML
    public void gameOver() {
        /*TODO yha se kaam krna h tumhe prachi*/

    }

    @FXML
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


    @FXML
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


    public void mainMenu(MouseEvent mainMenu) {
        StaticFunction.clickResponse(this.mainMenuIcon);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"DO you want to save the game?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Back to Main Menu");
        alert.initStyle(StageStyle.UNDECORATED);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() &&  result.get() == ButtonType.YES){
            MainMenu _mainMenu = new MainMenu();
            try {
                _mainMenu.start(StaticFunction.getStage(mainMenu));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void save(MouseEvent save)  {
        StaticFunction.clickResponse(this.saveIcon);

        Alert alert = new Alert(Alert.AlertType.INFORMATION,"DO you want to save the game?", ButtonType.OK);
        alert.setTitle("Save Prompt");
        alert.initStyle(StageStyle.UNDECORATED);
        alert.showAndWait();
    }


    public void restart(MouseEvent restart) {
        StaticFunction.clickResponse(this.restartIcon);

        Label nameLabel = hero.getName();
        World world = new World();
        world.start(StaticFunction.getStage(restart), nameLabel);
    }

//    public void camera(KeyEvent keyEvent) {
//        Node node=(Node) keyEvent.getSource();
//        VBox vBox=(VBox) node.getScene().getRoot();
//        StackPane stackPane = (StackPane) vBox.getChildren().get(0);
//
//        System.out.println(hero.getHero().getX() + " " + hero.getHero().getY());
//        if(stackPane.getChildren().size() == 2) return;
//        if(keyEvent.getCode() == KeyCode.LEFT) {
//            () -> this.camera().setX(this.camera(keyEvent).getX() - 10);
//
//
//    }
}
