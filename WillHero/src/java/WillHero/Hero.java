package WillHero;

import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

public class Hero {

    private final Label name;
    private final ImageView hero;
    private final Helmet helmet;
    private final int jumpHeight;
    private int jumpSpeed;
    private final Timeline tl;

    private boolean isAlive;
    private int reward;
    private int location;
    public double fromHeight;

    public Hero(Label name, int location) {
        isAlive = true;
        this.name = name;
        this.jumpHeight = 150;
        this.jumpSpeed = 1;
        this.fromHeight = 400;
        this.location=location;
        this.hero = setHero();
        this.helmet = new Helmet();
        this.tl = new Timeline(new KeyFrame(Duration.millis(5), e -> jump()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }

    private ImageView setHero(){
        ImageView hero;
        try {
            hero = new ImageView(new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("hero.png")).getPath())));
            hero.setPreserveRatio(true);
            hero.setFitWidth(50);
            hero.setX(410);
            hero.setY(150);

        } catch (FileNotFoundException | NullPointerException e) {
            hero = null;
            System.out.println("Failed to load hero");
            e.printStackTrace();
        }

        return hero;
    }

    public Label getName() {
        return name;
    }

    public ImageView getHero() throws NullPointerException {
        return hero;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public void setScoreLabel(Label reward, Label location) {
        reward.setText(String.valueOf(this.reward));
        location.setText(String.valueOf(this.location));
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }


    public void jump() {
        this.hero.setY(hero.getY() + jumpSpeed);

//        Iterator platformIterator = StaticFunction.getPlatformList();
        for(Platform platform : Game.getPlatformList()) {
            if(collision(platform)){
                if (isAlive)
                    jumpSpeed = -1;
                else
                    jumpSpeed = 0;
                break;
            }

            if(hero.getY() < platform.getIsLand().getY()-jumpHeight){
                jumpSpeed = 1;
                break;
            }
        }
    }


    private boolean collision(Object object){

        if(object instanceof Platform platform){
//            top side collision with platform
            if(StaticFunction.topCollision(platform.getIsLand(), hero)){
                fromHeight = Math.min(fromHeight, platform.getIsLand().getBoundsInLocal().getMinY());
                return true;
            }
        }

        if(object instanceof Orc orc){
//            top side collision with orc
            if(StaticFunction.topCollision(orc.getOrc(), hero)) {
                fromHeight = Math.min(fromHeight, orc.getOrc().getBoundsInLocal().getMinY());
                return true;
            }
        }

        return false;
    }

    public double getSpeed() {
        return -1;
    }

}