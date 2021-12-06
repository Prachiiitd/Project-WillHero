package WillHero;

import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Game implements Initializable {

    private final Player player;
    private final VBox vBox;
    private static StackPane stackPane;

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
        player = new Player();
        vBox  = new VBox();
        stackPane = new StackPane();
        currLocation = new Label();
        currReward = new Label();
        bestLocation = new Label();
        bestReward = new Label();
        screenAnchorPane = new AnchorPane();
        gameAnchorPane = new AnchorPane();
    }

    public void start(Stage stage, Label nameLabel) throws IOException {
        player.setName(nameLabel);

        gameAnchorPane  = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        screenAnchorPane =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ArenaScreen.fxml")));
        stackPane.getChildren().add(gameAnchorPane);
        stackPane.getChildren().add(screenAnchorPane);

        Image icon = new Image(new FileInputStream(Objects.requireNonNull(StaticFunction.class.getResource("mainIcon.png")).getPath()));
        stage.setTitle("WillHero: " + player.getName().getText());
        stage.getIcons().add(icon);

        vBox.getChildren().add(stackPane);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StaticFunction.setTranslation(floatingName, 0, 100, 1000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(island2, 0, 25, 2000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(island1, 0, 25, 2000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(greenOrc1, 0, 40, 1000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(hero, 0, 40, 1000,TranslateTransition.INDEFINITE, true);
        StaticFunction.bestLocation(bestLocation);
        StaticFunction.bestReward(bestReward);
        showStats();

        gameAnchorPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event)
            {
                if(stackPane.getChildren().size() == 2) return;
                System.out.println(stackPane.getChildren().size());

            }
        });

        if(!player.isAlive()) gameOver();

    }

    public void buildWorld() {

    }

    



    public void showStats(){
        /*TODO find out the best location reached so far from database*/

        currReward.setText(String.valueOf(player.getReward()));
        currLocation.setText(String.valueOf(player.getLocation()));
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

    }

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
