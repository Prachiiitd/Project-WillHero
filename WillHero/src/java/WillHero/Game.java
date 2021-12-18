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
import javafx.scene.control.ProgressBar;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class Game implements Initializable {

    @FXML
    private ImageView tempIl1;
    @FXML
    private ImageView tempIl2;
    @FXML
    private ImageView tempIl3;
    @FXML
    private ImageView tempIl4;
    @FXML
    private ImageView tempIl5;
    @FXML
    private ImageView tempIl6;
    @FXML
    private Text floatingName;

    @FXML
    private ImageView pauseIcon;
    @FXML
    private ImageView mainMenuIcon;
    @FXML
    private ImageView restartIcon;
    @FXML
    private ImageView saveIcon;
    @FXML
    private ImageView resumeIcon;
    @FXML
    private AnchorPane gameAnchorPane;
    @FXML
    private AnchorPane screenAnchorPane;
    @FXML
    private Label currLocation;
    @FXML
    private Label currReward;
    @FXML
    private Label bestLocation = new Label();
    @FXML
    private Label bestReward = new Label();
    @FXML
    private ProgressBar progressBar = new ProgressBar();

    private Hero hero;
    private VBox vBox;
    private StackPane stackPane;
    private Group screenObj;
    private static ArrayList<Hero> heroWrap;
    private static ArrayList<Platform> platforms;
    private static ArrayList<Orc> orcs;
    private ArrayList<Chest> chests;
    private ArrayList<Coin> coins;
    private static Timeline tl;
    Camera camera;

    


    public void start(Stage stage, Label nameLabel, VBox vBox, StackPane stackPane, AnchorPane gameAnchorPane, AnchorPane screenAnchorPane) throws IOException {

        hero = new Hero(nameLabel, 0);
        this.vBox = vBox;
        this.stackPane = stackPane;
        this.gameAnchorPane = gameAnchorPane;
        this.screenAnchorPane = screenAnchorPane;
        this.camera = new PerspectiveCamera();

        bestLocation = new Label();
        bestReward = new Label();
        screenObj = new Group();
        platforms = new ArrayList<>();
        coins = new ArrayList<>();
        orcs = new ArrayList<>();
        chests = new ArrayList<>();
        heroWrap = new ArrayList<>();
        heroWrap.add(hero);
        stage.getScene().setCamera(camera);

        buildWorld(coins, platforms,screenObj);
        stackPane.setOnKeyPressed(new EventHandler<KeyEvent> () {

            @Override
            public void handle(KeyEvent event)
            {
//                gravity(hero.getHero(), false, 0);

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
                if(event.getCode() == KeyCode.SPACE)
                    for (Node object : screenObj.getChildren()) {
                        ImageView imageView = (ImageView) object;
//                        StaticFunction.setTranslation(imageView, -30, 0, 3000, 1, false);
                        imageView.setX(imageView.getX() + 100 );
                    }

                if(event.getCode().isDigitKey()){
//                    hero.getHero().setX(hero.getHero().getX() + 100);
//                    camera.translateXProperty().set(hero.getHero().getX() + 100);
//                    hero.useWeapon();
                    for (Node object : screenObj.getChildren()) {
                        ImageView imageView = (ImageView) object;
                        imageView.setX(imageView.getX() -100);
                        hero.setLocation(hero.getLocation() + 1);
//                        StaticFunction.setTranslation(imageView, -30, 0, 3000, 1, false);
                    }
                }
            }
        });


        tl = new Timeline(new KeyFrame(Duration.millis(5), e -> playGame(gameAnchorPane, hero, platforms, screenObj, currLocation, currReward)));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StaticFunction.setTranslation(floatingName, 0, 100, 1000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(tempIl1, 0, 25, 2000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(tempIl2, 0, -25, 2000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(tempIl3, 0, 25, 2000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(tempIl4, 1, 20, 2000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(tempIl5, 0, -25, 2000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(tempIl6, 0, 25, 2000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(tempIl6, 0, 40, 1000,TranslateTransition.INDEFINITE, true);
        StaticFunction.setRotation(resumeIcon,360, 1000,2, true);
        StaticFunction.setRotation(restartIcon,360, 1000, 2,true);
        StaticFunction.setRotation(pauseIcon,360, 1000, 2,true);
        StaticFunction.setRotation(saveIcon,360, 1000, 2,true);
        StaticFunction.setRotation(mainMenuIcon,360, 1000, 2,true);

        StaticFunction.bestLocation(bestLocation);
        StaticFunction.bestReward(bestReward);
    }

    public static ArrayList<Hero> getHero() {
        return heroWrap;
    }
    public static ArrayList<Orc> getOrcList() {
        return orcs;
    }
    public static ArrayList<Platform> getPlatformList() {
        return platforms;
    }

    @FXML
    public void buildWorld(ArrayList<Coin> coins, ArrayList<Platform> platforms, Group screenObj) {
        //Initialize Game
        platforms.add(new Platform(3, 100, 350, 200, 50));
        platforms.add( new Platform(0, 400, 350, 600, 0));
        ImageView gate = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("gate.png"))));
        gate.setPreserveRatio(true); gate.setFitWidth(220); gate.setLayoutX(350); gate.setLayoutY(150);
        ImageView gif = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("WN8R.gif"))));
        gif.setPreserveRatio(true); gif.setFitWidth(190); gif.setLayoutX(364); gif.setLayoutY(160);
        screenObj.getChildren().addAll(platforms.get(0).getIsLand(), gif,gate, platforms.get(1).getIsLand());

        Group platform = getPlatforms(platforms);
//        GreenOrc g=new GreenOrc(1450,250);
        Chest co1 = new WeaponChest(650,200);
        Chest co2 = new CoinChest(1850,200);
        chests.add(co1); chests.add(co2);

        //Add orc manually
        Orc r=new RedOrc(2500,250);
        orcs.add(r);

        WindMill windMill = new WindMill(platforms.get(5).getIsLand().getX() + platforms.get(5).getIsLand().getBoundsInParent().getWidth() / 2, platforms.get(5).getIsLand().getY() - 300);
        screenObj.getChildren().addAll(windMill.getWindMill().getChildren());
        screenObj.getChildren().add(r.getOrc());
        screenObj.getChildren().addAll(co1.getChest(), co2.getChest());
        screenObj.getChildren().addAll(platform.getChildren());

        gameAnchorPane.getChildren().addAll(screenObj);
        gameAnchorPane.getChildren().add( hero.getHero());
        hero.setScoreLabel(currReward,currLocation);
    }


    private Group getPlatforms(ArrayList<Platform> platforms) {
        Group group = new Group();
        int z=0;
        for(int i = 0; i < 20; i++) {

            if(i+3 > 4) {
                int n = (i+3) % 4;
                int speed = 0;
                if(i*25 % 40 == 0) {
                    speed = 50;
                }
                int x = (int) (platforms.get(platforms.size()-1).getIsLand().getX() + platforms.get(platforms.size()-1).getIsLand().getFitWidth() + 200);
                Platform platform  = new Platform(n, x, 350, n*100, speed);
                if(z%3==0) {
                    Group coi = getCoinCluster(x, 300, coins);
                    if (i%3==0){
                        GreenOrc g=new GreenOrc(x-30,250);
                        orcs.add(g);
                        screenObj.getChildren().add(g.getOrc());
                    }
                    screenObj.getChildren().addAll(coi.getChildren());
                }
                platforms.add(platform);
                group.getChildren().addAll(platform.getIsLand());
            }
            z++;
        }
        return group;
    }

    private Group getCoinCluster(int x,int y,ArrayList<Coin> coins){
        Group group=new Group();

        for(int i=0;i<3;i++) {
            Coin c1 = new Coin(x+60*i, y);
            Coin c2=new Coin(x+60*i,y-55);
            group.getChildren().addAll(c1.getCoin(),c2.getCoin());
            coins.add(c1);
            coins.add(c2);
        }
        return group;
    }

    @FXML
    public void playGame(AnchorPane gameAnchorPane, Hero hero, ArrayList<Platform> platforms, Group screenObj, Label currReward, Label currLocation) {

        entityCollision();
        setScoreLabel();

        if(hero.getHero().getY() > 480) {
            hero.setAlive(false);
            StaticFunction.setRotation(hero.getHero(), 360, 100, -1, true);

            if(hero.getHero().getY() > 700){
                System.out.println("\t/h/h/h/h Game over");
                gameOver(gameAnchorPane);
            }
        }

        if(!hero.isAlive()) {
            hero.getHero().setScaleY(0.5);

            System.out.println("orc se mra h");
            gameOver(gameAnchorPane);
        }
    }


    private void activateJump(){
//        for(Orc orc : orcs){
//            orc.jump(hero, platforms);
//        }
    }


    private void entityCollision() {
        coins.removeIf(coin -> coin.collision(hero));
        for(Chest chest : chests) chest.collision(hero);
    }

    private void setScoreLabel() {
//        System.out.println(hero.getReward());
        currReward.setText("" + hero.getReward());
        currLocation.setText("" + hero.getLocation()/70);
    }

    public void gravity(ImageView node, boolean isGravity, int speed) {
        if(isGravity) {
            node.setY(node.getY() + speed);
        } else {
            node.setY(node.getY());
        }
    }


    public void gameOver(AnchorPane gameAnchorPane) {
        tl.pause();
        System.out.println("Game Over");
        VBox vBox=(VBox)gameAnchorPane.getScene().getRoot();
        StackPane stackPane = (StackPane) vBox.getChildren().get(0);

        try {
            Parent loadGameOver =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GameOver.fxml")));
            stackPane.getChildren().add(loadGameOver);

        } catch (IOException e) {
            System.out.println("ArenaScreen couldn't be loaded");
            e.printStackTrace();
        }
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

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to go to MainMenu?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Back to Main Menu");
        alert.initStyle(StageStyle.UNDECORATED);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() &&  result.get() == ButtonType.YES){
            tl.stop();
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

    @FXML
    public void restart(MouseEvent restart) {
        StaticFunction.clickResponse(this.restartIcon);
        tl.stop();
        Label nameLabel = new Label();
        World world = new World();
        world.start(StaticFunction.getStage(restart), nameLabel);
    }

    public void resurrect(MouseEvent resurrect) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"You dont have enough coins to resurrect.", ButtonType.OK);
        alert.setTitle("Resurrect");
        alert.initStyle(StageStyle.UNDECORATED);
        alert.showAndWait();
        tl.stop();
    }
}