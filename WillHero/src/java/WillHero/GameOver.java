package WillHero;

import Exceptions.AlreadyResurrectedException;
import Exceptions.InsufficientCoinException;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameOver implements Initializable {

    @FXML
    private ImageView mainMenuIcon;
    @FXML
    private ImageView restartIcon;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label currLocation;
    @FXML
    private StackPane stackPane;
    @FXML
    private Parent loadGameOver;

    private Timeline timeline;
    private Hero hero;

    public void start(Timeline timeline, Hero hero, StackPane stackPane, Parent loadGameOver) {
        this.timeline = timeline;
        this.hero = hero;
        this.loadGameOver = loadGameOver;
        this.stackPane = stackPane;
        currLocation.setText(hero.getLocation() + "");
        progressBar.setProgress(hero.getLocation()/120.0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StaticFunction.setRotation(restartIcon,360, 1000, 2,true);
        StaticFunction.setRotation(mainMenuIcon,360, 1000, 2,true);
    }

    public void mainMenu(MouseEvent mainMenu) {
        StaticFunction.mainMenuFn(mainMenu, this.mainMenuIcon, timeline);
    }

    public void restart(MouseEvent restart) {
        StaticFunction.clickResponse(this.restartIcon);
        timeline.stop();
        Label nameLabel = new Label(hero.getName());
        World world = new World();
        world.start(StaticFunction.getStage(restart), nameLabel);
    }

    public void resurrect() {

        try {
            resurrectHelp();
        } catch (InsufficientCoinException e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"InsufficientCoins, Do Restart or Back to MainMenu.", ButtonType.OK);
            alert.setTitle("Resurrect");
            alert.showAndWait();
        } catch (AlreadyResurrectedException e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Hero is already have been resurrected.", ButtonType.OK);
            alert.setTitle("Resurrect");
            alert.showAndWait();
        }
    }

    private void resurrectHelp() throws InsufficientCoinException, AlreadyResurrectedException {
        if(hero.getReward() < 5) throw new InsufficientCoinException("Insufficient Coins to Resurrect");
        if(hero.isResurrected()) throw new AlreadyResurrectedException("Hero is already have been resurrected");
        if(timeline.getStatus() == Timeline.Status.PAUSED) {
            stackPane.getChildren().remove(loadGameOver);
            hero.setReward(hero.getReward() - 5);
            double minx = Double.MAX_VALUE;
            for(Platform p : Game.getPlatformList()) {
                if (p.getIsLand().getX()>hero.getX()) {
                    //commit
                }

            }
            hero.getHero().setX(hero.getHero().getX() - 200);
            hero.getHero().setY(250);
            hero.setAlive(true);
            hero.setResurrected(true);
            hero.getHero().disableProperty();
            timeline.play();
        }
    }
}
