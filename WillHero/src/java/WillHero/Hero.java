package WillHero;

import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Hero implements Serializable {

    private transient final Label nameLabel;
    private transient final ImageView hero;
    private transient final Timeline tl;
    private transient final double jumpHeight;
    private transient final double dy;

    private double jumpSpeed;
    private final Helmet helmet;
    private final String name;
    private boolean isAlive;
    private int location;
    private int reward;

    public Hero(String name, int location) {
        this.isAlive = true;
        this.name = name;
        this.nameLabel = new Label(name);
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

    private ImageView setHero() {
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
        if (helmet.getWeapon(helmet.getChoice()).isActive()) {
            helmet.getWeapon(helmet.getChoice()).attack(this);
        }
    }

    public Label getName() {
        return nameLabel;
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
        this.helmet.getWeapon(0).getWeaponImage().setY(hero.getY() + jumpSpeed);
        this.helmet.getWeapon(1).getWeaponImage().setY(this.helmet.getWeapon(1).getWeaponImage().getY() + jumpSpeed);
        jumpSpeed += dy;

        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(Game.getPlatformList());
        objects.addAll(Game.getOrcList());
        objects.addAll(Game.getTntList());

        for (Object object : objects) {
            if (collision(object)) {
                jumpSpeed = jumpHeight;
                if (isAlive) {
                    StaticFunction.setScaling(hero, 0, -0.1, 100, 2, true);
                    jumpSpeed = jumpSpeed > 0 ? -jumpSpeed : jumpSpeed;
                } else {
                    jumpSpeed = 0;
                }
                break;
            }
        }
    }

    private boolean collision(Object object) {

        // Hero's Collision with Platform
        if (object instanceof Platform platform) {

            // Hero's base collide with top the top edge of Platform
            if (StaticFunction.bottomCollision(hero, platform.getIsLand(), 4)) {
                return true;
            }

            // Hero's right collides with the left edge of Platform
            if (StaticFunction.rightCollision(hero, platform.getIsLand(), 4)) {
                return true;
            }

            // Hero's left collides with the right edge of Platform
            if (StaticFunction.leftCollision(hero, platform.getIsLand(), 4)) {

            }
        }
        if (object instanceof Obstacle tnt) {

            // Right side collision of hero with orc left

            if (StaticFunction.rightCollision(hero, tnt.getObstacle(), 3)) {

                if((((Tnt)tnt).getHitCount())==1){
                    ((Tnt)tnt).setHitCount(2);
                }
                if((((Tnt)tnt).getHitCount())==0){
                    ((Tnt)tnt).setHitCount(1);
                }

                if(((Tnt)tnt).isBlast()) isAlive=false;
                else{
                    tnt.getObstacle().setX(tnt.getObstacle().getX()+20);
                }
//                else {
//                    for(Node node:together().getChildren()){
//                        if(node instanceof ImageView){
//                            ((ImageView)node).setX(((ImageView)node).getX()-25);
//                        }
//                    }
//
//                }

                return true;
            }

            // Left side collision of hero with orc right
            if (StaticFunction.leftCollision(hero, tnt.getObstacle(), 3)) {

                if((((Tnt)tnt).getHitCount())==0){
                    ((Tnt)tnt).setHitCount(1);
                }
                if(((Tnt)tnt).isBlast()) isAlive=false;
                else{
                    tnt.getObstacle().setX(tnt.getObstacle().getX()-20);
                }
//                else {
//                    for(Node node:together().getChildren()){
//                        if(node instanceof ImageView){
//                            ((ImageView)node).setX(((ImageView)node).getX()-25);
//                        }
//                    }
//
//                }

                return true;

            }
//            if(Object instanceof Tnt tnt){

//            }

            // Bottom side collision of hero with orc top
            if (StaticFunction.bottomCollision(hero, tnt.getObstacle(), 3)) {
                if((((Tnt)tnt).getHitCount())==0){
                    ((Tnt)tnt).setHitCount(1);
                }
                if(((Tnt)tnt).isBlast()) isAlive=false;

                return true;
            }

            // Top side collision of hero with orc bottom
            if (StaticFunction.topCollision(hero, tnt.getObstacle(), 3)) {
                if((((Tnt)tnt).getHitCount())==1){
                    ((Tnt)tnt).setHitCount(2);
                }
                if((((Tnt)tnt).getHitCount())==0){
                    ((Tnt)tnt).setHitCount(1);
                }
                if(((Tnt)tnt).isBlast()) isAlive=false;
                return true;
            }
        }

        // Collision with orc
        if (object instanceof Orc orc) {

            // Bottom side collision of hero with orc top
            if (StaticFunction.bottomCollision(hero, orc.getOrc(), 3)) {
                return true;
            }

            // Right side collision of hero with orc left
            if (StaticFunction.rightCollision(hero, orc.getOrc(), 3)) {
                orc.getOrc().setX(orc.getOrc().getX() + 5);
            }

            // Left side collision of hero with orc right
            if (StaticFunction.leftCollision(hero, orc.getOrc(), 3)) {
                orc.getOrc().setX(orc.getOrc().getX() - 5);
            }

            // Top side collision of hero with orc bottom
            if (StaticFunction.topCollision(hero, orc.getOrc(), 3)) {
                isAlive = false;
                return true;
            }
        }

        return false;
    }
    // End of collision
}