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
    private final double jumpHeight;
    private final double dy;
    private double jumpSpeed;
    private final Timeline tl;

    private boolean isAlive;
    private int location;
    private int reward;

    public Hero(Label name, int location) {
        this.isAlive = true;
        this.name = name;
        this.jumpHeight = 1.5;
        this.jumpSpeed = 0;
        this.dy = 0.01;

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
            hero.setFitHeight(40);
            hero.setX(400);
            hero.setY(150);

        } catch (FileNotFoundException | NullPointerException e) {
            hero = null;
            System.out.println("Failed to load hero");
            e.printStackTrace();
        }

        return hero;
    }

    public void useWeapon() {
        if(helmet.getWeapon(helmet.getChoice()).isActive()) {
            helmet.getWeapon(helmet.getChoice()).attack(this);
        }
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
        this.helmet.getWeapon(1).getWeaponImage().setY(this.helmet.getWeapon(1).getWeaponImage().getY() + jumpSpeed);
        jumpSpeed += dy;
//        this.helmet.getWeapon(0).getWeaponImage().setY(hero.getY() + 5 + jumpSpeed);

        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(Game.getPlatformList());
        objects.addAll(Game.getOrcList());

        for (Object object : objects) {
            if (collision(object)) {
                jumpSpeed = jumpHeight;
                if (isAlive) {
                    StaticFunction.setScaling(hero,0,-0.1,100,2,true);
                    jumpSpeed = jumpSpeed > 0 ? -jumpSpeed : jumpSpeed;
                } else {
                    jumpSpeed = 0;
                }
                break;
            }
        }
    }
    // End of jumping

    // Collision
    private boolean collision(Object object){

        // Hero's Collision with Platform
        if(object instanceof Platform platform){

            // Hero's base collide with top the top edge of Platform
            if(StaticFunction.bottomCollision( hero, platform.getIsLand(), 0)){
                return true;
            }

            // Hero's right collides with the left edge of Platform
            if(StaticFunction.rightCollision( hero, platform.getIsLand(), 0)){
                return true;
            }

            // Hero's left collides with the right edge of Platform
            if(StaticFunction.leftCollision( hero, platform.getIsLand(), 0)){

            }
        }

        // Collision with orc
        if(object instanceof Orc orc){

            // Bottom side collision of hero with orc top
            if(StaticFunction.bottomCollision(hero, orc.getOrc(), 0)) {
                System.out.println("Bottom side collision of hero with orc right");
                return true;
            }

            // Right side collision of hero with orc left
            if(StaticFunction.rightCollision(hero, orc.getOrc(),0)) {
                System.out.println("Right side collision of hero with orc right");
                orc.getOrc().setX(orc.getOrc().getX() + 5);
            }

            // Left side collision of hero with orc right
            if(StaticFunction.leftCollision( hero, orc.getOrc(),0)) {
                System.out.println("Left side collision of hero with orc right");
                orc.getOrc().setX(orc.getOrc().getX() - 5);
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