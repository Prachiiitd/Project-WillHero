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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Hero {

    private final Label name;
    private final ImageView hero;
    private final Helmet helmet;
    private final int jumpHeight;
    private final Timeline tl;

    private double fromHeight;
    private boolean isAlive;
    private double jumpSpeed;
    private int location;
    private int reward;

    public Hero(Label name, int location) {
        this.isAlive = true;
        this.name = name;
        this.jumpHeight = 120;
        this.fromHeight = 400;
        this.jumpSpeed = 0.8;
        this.location = location;
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
            hero.setFitWidth(30);
            hero.setX(410);
            hero.setY(150);

        } catch (FileNotFoundException | NullPointerException e) {
            hero = null;
            System.out.println("Failed to load hero");
            e.printStackTrace();
        }

        return hero;
    }

    public void useWeapon() {
        helmet.getWeapon(helmet.getChoice()).attack(this);
    }

    public Label getName() {
        return name;
    }

    public ImageView getHero() {
        return hero;
    }

    // Setting up game Stats
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
    // End of game stats

    // Life and Death
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }
    // End of life and death


    public Helmet getHelmet() {
        return helmet;
    }

    // Jumping
    public void jump() {
        this.hero.setY(hero.getY() + jumpSpeed);
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(Game.getPlatformList());
        objects.addAll(Game.getOrcList());

        for(Object object : objects){
            if(collision(object)){
                if (isAlive) {
                    jumpSpeed = jumpSpeed > 0? -jumpSpeed : jumpSpeed;
                }
                else {
                    jumpSpeed = 0;
                }
                break;
            }
        }

        if(hero.getY() < fromHeight-jumpHeight){
            jumpSpeed = jumpSpeed > 0? jumpSpeed : -jumpSpeed;
//            System.out.println("fromHeight: " + fromHeight);
        }
    }
    // End of jumping

    // Collision
    private boolean collision(Object object){

        // Hero's Collision with Platform
        if(object instanceof Platform platform){

            // Hero's base collide with top the top edge of Platform
            if(StaticFunction.bottomCollision( hero, platform.getIsLand(), 0)){
                fromHeight = platform.getIsLand().getBoundsInLocal().getMinY();
//                jumpSpeed = 1;
                return true;
            }

            // Hero's right collides with the left edge of Platform
            if(StaticFunction.rightCollision( hero, platform.getIsLand(), 0)){
                fromHeight = platform.getIsLand().getBoundsInLocal().getMinY();
                return true;
            }

            // Hero's left collides with the right edge of Platform
            if(StaticFunction.leftCollision( hero, platform.getIsLand(), 0)){

            }
        }

        // Collision ith orc
        if(object instanceof Orc orc){

            // Left side collision of hero with orc right
            if(StaticFunction.leftCollision( hero, orc.getOrc(),0)) {
                System.out.println("Left side collision of hero with orc right");
//                isAlive = false;
            }

            // Right side collision of hero with orc left
            if(StaticFunction.rightCollision(hero, orc.getOrc(),0)) {
                System.out.println("Right side collision of hero with orc right");
                orc.getOrc().setX(orc.getOrc().getX() + 5);
                return false;
            }

            // Bottom side collision of hero with orc top
            if(StaticFunction.bottomCollision(hero, orc.getOrc(), 0)) {
                System.out.println("Bottom side collision of hero with orc right");
                fromHeight = orc.getOrc().getBoundsInLocal().getMinY();
                jumpSpeed = 1;
                return true;
            }

            // Top side collision of hero with orc bottom
            if(StaticFunction.topCollision(hero, orc.getOrc(),  0)) {
                System.out.println("Top side collision of hero with orc bottom");
                isAlive = false;
                return true;
            }
        }


        return false;
    }
    // End of collision
}