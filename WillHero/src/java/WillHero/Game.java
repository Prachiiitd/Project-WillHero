package WillHero;

import Exceptions.DeadBossOrcException;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.*;

public class Game implements Initializable {

    @FXML
    private transient ImageView tempIl1;
    @FXML
    private transient ImageView tempIl2;
    @FXML
    private transient ImageView tempIl3;
    @FXML
    private transient ImageView tempIl4;
    @FXML
    private transient ImageView tempIl5;
    @FXML
    private transient ImageView tempIl6;
    @FXML
    private transient Text floatingName;

    @FXML
    private transient ImageView pauseIcon;
    @FXML
    private transient AnchorPane gameAnchorPane;
    @FXML
    private transient Label currLocation;
    @FXML
    private transient Label currReward;
    @FXML
    private transient Label bestLocation = new Label();
    @FXML
    private transient Label bestReward = new Label();
    @FXML
    private transient ProgressBar progressBar = new ProgressBar();

    private Hero hero;
    private VBox vBox;
    private StackPane stackPane;
    private Group screenObj;
    private static ArrayList<Platform> platforms;
    private static ArrayList<Orc> orcs;
    private static ArrayList<Chest> chests;
    private static ArrayList<Obstacle> tnts;
    private ArrayList<Coin> coins;
    private ArrayList<ImageView> gate;
    private ArenaScreen arenaScreen;

    private static double hSpeed = 0;
    private double dx = 0.7;
    private double xDist = 0;
    private Timeline tlh;

    private static Timeline tl;
    Camera camera;

    public void start(Stage stage, String name, VBox vBox, StackPane stackPane, AnchorPane gameAnchorPane) throws IOException {
        hero = new Hero(name, 0, 0, true, false, new Helmet(new Weapon1(5, 100, false), new Weapon2(3, 100, false), 0), 0, 450, 150);
        this.vBox = vBox;
        this.stackPane = stackPane;
        this.gameAnchorPane = gameAnchorPane;
        this.camera = new PerspectiveCamera();

        this.bestLocation = new Label();
        this.bestReward = new Label();
        this.screenObj = new Group();
        this.gate = new ArrayList<>();
        this.arenaScreen = new ArenaScreen();
        this.coins = new ArrayList<>();

        platforms = new ArrayList<>();
        orcs = new ArrayList<>();
        chests = new ArrayList<>();
        tnts = new ArrayList<>();

        stage.getScene().setCamera(camera);
        customWorld();
        buildWorld(screenObj);

        stackPane.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {

                Node node = (Node) event.getSource();
                VBox vBox = (VBox) node.getScene().getRoot();
                StackPane stackPane = (StackPane) vBox.getChildren().get(0);

                System.out.println(hero.getHero().getX() + " " + hero.getHero().getY());
                if (stackPane.getChildren().size() == 2) return;

                if (event.getCode() == KeyCode.RIGHT)
                    hero.getHero().setX(hero.getHero().getX() + 5);
                if (event.getCode() == KeyCode.LEFT)
                    hero.getHero().setX(hero.getHero().getX() - 5);
                if (event.getCode() == KeyCode.UP)
                    hero.getHero().setY(hero.getHero().getY() + 5);
                if (event.getCode() == KeyCode.DOWN)
                    hero.getHero().setY(hero.getHero().getY() - 5);
                if (event.getCode() == KeyCode.SPACE)
                    for (Node object : screenObj.getChildren()) {
                        ImageView imageView = (ImageView) object;
                        imageView.setX(imageView.getX() + 100);
                    }

                if (event.getCode().isDigitKey()) {
                    if (tlh != null && tlh.getStatus() == Animation.Status.RUNNING) {
                        tlh.stop();
                    }

                    xDist = 0;
                    dx = dx>=0? dx: -dx;

                    hero.useWeapon();
                    hero.setLocation(hero.getLocation() + 1);
                    currLocation.setText((Integer.parseInt(currLocation.getText())+100)/100 + "");
                    hero.getTl().pause();

                    tlh = new Timeline(new KeyFrame(Duration.millis(5), e -> moveObject()));
                    tlh.setCycleCount(30);
                    tlh.setOnFinished(e -> {tlh.stop();hero.getTl().play(); xDist = 0; dx = dx>0? dx: -dx;});
                    tlh.play();
                }
            }
        });


        tl = new Timeline(new KeyFrame(Duration.millis(5), e -> playGame(gameAnchorPane, hero, platforms, screenObj, currLocation, currReward)));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();

        loadScreen();
    }

    private void moveObject() {
        hSpeed+=dx;
        xDist+=hSpeed;
        double maxXDist = 150;
        if(xDist > maxXDist /2) {
            dx = dx>0? -dx: dx;
        }

        for (Node object : screenObj.getChildren()) {
            ImageView imageView = (ImageView) object;
            imageView.setX(imageView.getX() - hSpeed);
        }

        for (Platform platform : platforms) {
            platform.setX(platform.getX() - hSpeed);
        }
        for (Orc orc : orcs) {
            orc.setX(orc.getX() - hSpeed);
        }
        for (Chest chest : chests) {
            chest.setX(chest.getX() - hSpeed);
        }
        for (Obstacle tnt : tnts) {
            tnt.setX(tnt.getX() - hSpeed);
        }
        for (Coin coin : coins) {
            coin.setX(coin.getX() - hSpeed);
        }
    }

    public void resume(Stage stage, Hero hero, VBox vBox, StackPane stackPane, AnchorPane gameAnchorPane,
                       ArrayList<Platform> generatedPlatforms, ArrayList<Coin> generatedCoins,
                       ArrayList<Obstacle> generatedObstacles, ArrayList<Chest> generatedChests,
                       ArrayList<Orc> generatedOrcs) throws IOException {
        this.hero = hero;
        this.vBox = vBox;
        this.stackPane = stackPane;
        this.gameAnchorPane = gameAnchorPane;
        this.camera = new PerspectiveCamera();

        this.bestLocation = new Label();
        this.bestReward = new Label();
        this.screenObj = new Group();
        this.coins = generatedCoins;

        platforms = generatedPlatforms;
        orcs = generatedOrcs;
        chests = generatedChests;
        tnts = generatedObstacles;

        gate = new ArrayList<>();
        arenaScreen = new ArenaScreen();
        stage.getScene().setCamera(camera);

        buildWorld(screenObj);

        stackPane.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {

                Node node = (Node) event.getSource();
                VBox vBox = (VBox) node.getScene().getRoot();
                StackPane stackPane = (StackPane) vBox.getChildren().get(0);

                System.out.println(hero.getHero().getX() + " " + hero.getHero().getY());
                if (stackPane.getChildren().size() == 2) return;

                if (event.getCode() == KeyCode.RIGHT)
                    hero.getHero().setX(hero.getHero().getX() + 5);
                if (event.getCode() == KeyCode.LEFT)
                    hero.getHero().setX(hero.getHero().getX() - 5);
                if (event.getCode() == KeyCode.UP)
                    hero.getHero().setY(hero.getHero().getY() + 5);
                if (event.getCode() == KeyCode.DOWN)
                    hero.getHero().setY(hero.getHero().getY() - 5);
                if (event.getCode() == KeyCode.SPACE)
                    for (Node object : screenObj.getChildren()) {
                        ImageView imageView = (ImageView) object;
                        imageView.setX(imageView.getX() + 100);
//                        gate.get(0).setX(gate.get(0).getX() -100);
                    }

                if (event.getCode().isDigitKey()) {
                    if (tlh != null && tlh.getStatus() == Animation.Status.RUNNING) {
                        tlh.stop();
                    }
                    xDist = 0;
                    dx = dx>0? dx: -dx;

                    hero.useWeapon();
                    hero.setLocation(hero.getLocation() + 1);
                    currLocation.setText((Integer.parseInt(currLocation.getText())+100)/100 + "");
                    hero.getTl().pause();

                    tlh = new Timeline(new KeyFrame(Duration.millis(5), e -> moveObject()));
                    tlh.setCycleCount(30);
                    tlh.setOnFinished(e -> {tlh.stop();hero.getTl().play(); xDist = 0; dx = dx>0? dx: -dx;});
                    tlh.play();
                }
            }
        });

        tl = new Timeline(new KeyFrame(Duration.millis(5), e -> playGame(gameAnchorPane, hero, platforms, screenObj, currLocation, currReward)));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
        if (hero.isAlive())
            loadScreen();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StaticFunction.setTranslation(floatingName, 0, 100, 1000, TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(tempIl1, 0, 25, 2000, TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(tempIl2, 0, -25, 2000, TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(tempIl3, 0, 25, 2000, TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(tempIl4, 1, 20, 2000, TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(tempIl5, 0, -25, 2000, TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(tempIl6, 0, 25, 2000, TranslateTransition.INDEFINITE, true);
        StaticFunction.setTranslation(tempIl6, 0, 40, 1000, TranslateTransition.INDEFINITE, true);
        StaticFunction.setRotation(pauseIcon, 360, 1000, 2, true);

        StaticFunction.bestLocation(bestLocation);
        StaticFunction.bestReward(bestReward);
    }


    public static ArrayList<Orc> getOrcList() {
        return orcs;
    }

    public static ArrayList<Platform> getPlatformList() {
        return platforms;
    }

    public static ArrayList<Chest> getChestList() {
        return chests;
    }

    public static ArrayList<Obstacle> getTntList() {
        return tnts;
    }

    public static double gethSpeed() {
        return hSpeed;
    }

    @FXML
    public void buildWorld(Group screenObj) {
        //Initialize Game

        ImageView gat = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("gate.png"))));
        gat.setPreserveRatio(true);
        gat.setFitWidth(220);
        gat.setLayoutX(350 - hero.getLocation() * 150);
        gat.setLayoutY(135);

        ImageView gif = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("WN8R.gif"))));
        gif.setPreserveRatio(true);
        gif.setFitWidth(190);
        gif.setLayoutX(364 - hero.getLocation() * 150);
        gif.setLayoutY(160);
        gate.add(gif);
        gate.add(gat);

        ArrayList<Coin> coi = getCoinCluster(700, 260);
//                    screenObj.getChildren().addAll(coi.getChildren());
        coins.addAll(coi);
        ArrayList<Coin> coi2 = getCoinCluster(1000, 200);
//                    screenObj.getChildren().addAll(coi.getChildren());
        coins.addAll(coi2);

        for (ImageView imageView : gate) {
            screenObj.getChildren().add(imageView);
        }
        for (Platform platform : platforms) {
            screenObj.getChildren().add(platform.getIsLand());
        }
        for (Coin coin : coins) {
            screenObj.getChildren().add(coin.getCoin());
        }
        for (Chest chest : chests) {
            screenObj.getChildren().add(chest.getChest());
        }
        for (Obstacle obstacle : tnts) {
            screenObj.getChildren().add(obstacle.getObstacle());
        }
        for (Orc orc : orcs) {
            screenObj.getChildren().add(orc.getOrc());
        }

        gameAnchorPane.getChildren().addAll(screenObj);
        gameAnchorPane.getChildren().addAll(hero.getHero(), hero.getHelmet().getWeapon(0).getWeaponImage(), hero.getHelmet().getWeapon(1).getWeaponImage());
    }


    private void customWorld() {
        platforms.add(new Platform(3, 0, 350, 200, 50));
        platforms.add(new Platform(0, 300, 350, 600, 0));

        int z = 0;
        Random random = new Random();
        int pr = 0;
        for (int i = 0; i < 32; i++) {
            pr += 1;
            if (i + 3 > 4) {
                int speed = 1;
                if (i * 25 % 40 == 0) {
                    speed = 50;
                }
                int x = (int) (platforms.get(platforms.size() - 1).getIsLand().getX() + platforms.get(platforms.size() - 1).getIsLand().getFitWidth() + 150);
                Platform platform = new Platform(random.nextInt(0, 5), x, random.nextInt(300, 350), random.nextInt(2, 4) * 100, speed);
                if ((i + 1) % 2 == 0) {
                    GreenOrc g = new GreenOrc(x + 40, 250);
                    orcs.add(g);
//                    screenObj.getChildren().add(g.getOrc());
                }
                if (i % 5 == 0) {
                    ArrayList<Coin> coi = getCoinCluster(x, random.nextInt(130, 190));
                    coins.addAll(coi);
//                    screenObj.getChildren().addAll(coi.getChildren());
                }
                if ((i - 1) % 6 == 0 || (i - 2) % 6 == 0) {
                    Obstacle tnt = new Tnt(x + 60, 250);
                    tnts.add(tnt);
//                    screenObj.getChildren().add(tnt.getObstacle());

                }

                if (i % 3 == 0) {
                    if (i % 2 == 1) {
                        GreenOrc g = new GreenOrc(x, 250);
                        orcs.add(g);
//                        screenObj.getChildren().add(g.getOrc());

                    } else {
                        Orc g = new RedOrc(x - 10, 250);
                        orcs.add(g);
//                        screenObj.getChildren().add(g.getOrc());

                    }
                    ArrayList<Coin> coi = getCoinCluster(x, random.nextInt(130, 190));
//                    screenObj.getChildren().addAll(coi.getChildren());
                    coins.addAll(coi);

                }
                if (i % 4 == 0) {

                    ArrayList<Coin> coi = getCoinCluster(x, random.nextInt(260, 300));
                    coins.addAll(coi);
                    if (i % 8 == 0) {
                        Chest c = new CoinChest(x, 200);
                        chests.add(c);
//                        screenObj.getChildren().addAll(coi.getChildren());
//                        screenObj.getChildren().add(c.getChest());
                    } else {
                        Chest c = new WeaponChest(x, 200);
                        chests.add(c);
//                        screenObj.getChildren().addAll(coi.getChildren());
//                        screenObj.getChildren().add(c.getChest());
                    }
                }
                platforms.add(platform);
//                group.getChildren().addAll(platform.getIsLand());
            }
            z++;
        }
        int x = (int) (platforms.get(platforms.size() - 1).getIsLand().getX() + platforms.get(platforms.size() - 1).getIsLand().getFitWidth());
        platforms.add(new Platform(0, x + 150, 350, 500, 50));
        platforms.add(new Platform(4, x + 500 + 140, 350, 420, 50));
        Orc boss = new BossOrc(x + 200 + 150, 200);
        orcs.add(boss);
        ImageView gif = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Gif2.gif"))));
        gif.setPreserveRatio(true);
        gif.setFitWidth(190);
        gif.setLayoutX(x + 700);
        gif.setLayoutY(160);
        gate.add(gif);
    }


    public void loadScreen() throws IOException {

        FXMLLoader root_screenAnchorPane = new FXMLLoader(Objects.requireNonNull(getClass().getResource("ArenaScreen.fxml")));
        AnchorPane screenAnchorPane = root_screenAnchorPane.load();
        stackPane.getChildren().add(screenAnchorPane);
        arenaScreen = root_screenAnchorPane.getController();
        arenaScreen.start(tl, hero, stackPane, chests, orcs, coins, tnts, platforms, screenAnchorPane);
    }


    private ArrayList<Coin> getCoinCluster(int x, int y) {
//        Group group=new Group();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Coin c1 = new Coin(x + 60 * i, y);
            Coin c2 = new Coin(x + 60 * i, y - 55);
            coins.add(c1);
            coins.add(c2);
        }
        return coins;
    }

    @FXML
    public void playGame(AnchorPane gameAnchorPane, Hero hero, ArrayList<Platform> platforms, Group screenObj, Label currReward, Label currLocation) {

        entityCollision();

        garbageRemover(gameAnchorPane);
        setScoreLabel();
    try {
        if (Math.abs(hero.getHero().getX() - getOrcList().get(getOrcList().size() - 1).getOrc().getX()) <= 350) {
            System.out.println("hello1" + ((BossOrc) (getOrcList().get(getOrcList().size() - 1))).getHspeed());
            if (getOrcList().get(getOrcList().size() - 1).getOrc().getX() <= hero.getHero().getX()) {

                ((BossOrc) (getOrcList().get(getOrcList().size() - 1))).setHspeed(0.07);
                System.out.println(" hello2 " + ((BossOrc) (getOrcList().get(getOrcList().size() - 1))).getHspeed());
            } else {
                ((BossOrc) (getOrcList().get(getOrcList().size() - 1))).setHspeed(-0.07);
                System.out.println("hello3" + ((BossOrc) (getOrcList().get(getOrcList().size() - 1))).getHspeed());
            }
        } else {
            ((BossOrc) (getOrcList().get(getOrcList().size() - 1))).setHspeed(0);
            System.out.println("hello4" + ((BossOrc) (getOrcList().get(getOrcList().size() - 1))).getHspeed());
        }
    }
    catch (ClassCastException e){
        System.out.println("hello5");
//        tl.stop();
    gameWin(gameAnchorPane);
//        declareWinner();
    }

        if (hero.getHero().getY() > 480) {
            hero.setAlive(false);
            Transition trh = StaticFunction.setRotation(hero.getHero(), 360, 100, -1, true);

            if (hero.getHero().getY() > 700) {
                trh.stop();
                hero.getHero().setRotate(0);
                gameOver(gameAnchorPane);
            }
        }

        if (!hero.isAlive()) {
            hero.getHero().setScaleY(0.5);

            gameOver(gameAnchorPane);
        }
    }
    private void declareWinner(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Woohoo!");
        alert.setHeaderText(null);
        alert.setContentText("You Won");
        alert.initStyle(StageStyle.UNDECORATED);
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.isPresent() &&  result.get() == ButtonType.YES){
//
////            stage.close();
//        }
//        Platform.runLater(alert::showAndWait);

        alert.showAndWait();
        tl.pause();


    }


    private void entityCollision() {
        coins.removeIf(coin -> coin.collision(hero));
        for (Chest chest : chests) chest.collision(hero);
        for (Orc orc : orcs) {
            hero.getHelmet().getWeapon(hero.getHelmet().getChoice()).collision(orc);
            if (!orc.isAlive()) {
                orc.destroy();
                if (orc.getOrc().getY() > 900) {
                    orc.getOrc().setVisible(false);
                }
            }
        }
    }

    private void garbageRemover(AnchorPane pane) {
        try {
            Object lock = new Object();
            for (Obstacle tnt : tnts) {
                if (!tnt.getObstacle().isVisible()) {
                    tnts.remove(tnt);
                    tnt = null;
                }
            }

            for (Orc orc : orcs) {
                if (!orc.getOrc().isVisible()) {
                    orcs.remove(orc);
                    orc = null;
                }
            }
            for (Coin coin : coins) {
                if (!coin.getCoin().isVisible()) {
                    coins.remove(coin);
                    coin = null;
                }
            }

        } catch (ClassCastException e) {
            System.out.println("Error");
        } catch (ConcurrentModificationException ignored) {
        }
    }


    private void setScoreLabel() {
        progressBar.setProgress(hero.getLocation() / 1400.0); ///not working
        currReward.setText("" + hero.getReward());
        currLocation.setText(hero.getLocation() + "");
    }

    public void gameWin(AnchorPane gameAnchorPane) {
        tl.pause();
        System.out.println("Game Win");
        VBox vBox = (VBox) gameAnchorPane.getScene().getRoot();
        StackPane stackPane = (StackPane) vBox.getChildren().get(0);

        try {
            FXMLLoader gameOverFXML = new FXMLLoader(Objects.requireNonNull(getClass().getResource("GameOver.fxml")));
            AnchorPane gameOverPane = gameOverFXML.load();
            stackPane.getChildren().add(gameOverPane);
            GameOver gameOver = gameOverFXML.getController();
            gameOver.start(tl, hero, stackPane, gameOverPane);

        } catch (IOException e) {
            System.out.println("ArenaScreen couldn't be loaded");
            e.printStackTrace();
        }
    }


    public void gameOver(AnchorPane gameAnchorPane) {
        tl.pause();
        System.out.println("Game Over");
        VBox vBox = (VBox) gameAnchorPane.getScene().getRoot();
        StackPane stackPane = (StackPane) vBox.getChildren().get(0);

        try {
            FXMLLoader gameOverFXML = new FXMLLoader(Objects.requireNonNull(getClass().getResource("GameOver.fxml")));
            AnchorPane gameOverPane = gameOverFXML.load();
            stackPane.getChildren().add(gameOverPane);
            GameOver gameOver = gameOverFXML.getController();
            gameOver.start(tl, hero, stackPane, gameOverPane);

        } catch (IOException e) {
            System.out.println("ArenaScreen couldn't be loaded");
            e.printStackTrace();
        }
    }

    @FXML
    public void pause(MouseEvent pause) {
        StaticFunction.clickResponse(this.pauseIcon);
        try {
            loadScreen();

        } catch (IOException e) {
            System.out.println("ArenaScreen couldn't be loaded");
            e.printStackTrace();
        }
    }
}

