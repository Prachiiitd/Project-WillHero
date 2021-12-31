package WillHero;

import javafx.animation.*;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Hero implements Serializable {

    private transient ImageView hero;
    private transient Timeline tl;


    private double jumpSpeed = 0;
    private final Helmet helmet;
    private final String name;
    private boolean isAlive;
    private int location;
    private int reward;
    private boolean resurrected;
//    private CheckBox remark;

    public Hero(String name, int location, int reward, boolean isAlive, boolean isResurrected, Helmet helmet) {
        this.isAlive = isAlive;
        this.name = name;
        this.resurrected = isResurrected;
        this.location = location;
        this.reward = reward;
        this.helmet = helmet;
        this.setHero();
//        this.remark = new CheckBox();

        tl = new Timeline(new KeyFrame(Duration.millis(5), e -> jump()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }

    public void setHero() {
        try {
            this.hero = new ImageView(new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("hero.png")).getPath())));
            this.hero.setPreserveRatio(true);
            this.hero.setFitHeight(40);
            this.hero.setX(400);
            this.hero.setY(150);

        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println("Failed to load hero");
            e.printStackTrace();
        }
    }

    public void useWeapon() {
        if (helmet.getWeapon(helmet.getChoice()).isActive()) {
            helmet.getWeapon(helmet.getChoice()).attack(this);
        }
    }

    public Timeline getTl() {
        return tl;
    }

    public String getName() {
        return name;
    }

    public ImageView getHero() {
        return hero;
    }

    // Setting up game Stats
    public int getLocation() {
        return location;
    }
//    public CheckBox getCheckBox() {
//        return remark;
//    }

    public void setLocation(int location) {
        this.location = location;

    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public void setResurrected(boolean resurrected) {
        this.resurrected = resurrected;
    }

    public boolean isResurrected() {
        return resurrected;
    }

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
    public void jump() throws NullPointerException {
        this.hero.setY(hero.getY() + jumpSpeed);
        this.helmet.getWeapon(0).getWeaponImage().setY(hero.getY() + jumpSpeed);
        this.helmet.getWeapon(1).getWeaponImage().setY(this.helmet.getWeapon(1).getWeaponImage().getY() + jumpSpeed);
        double dy = 0.01;
        jumpSpeed += dy;

        ArrayList<Object> objects = new ArrayList<>();
        try {
            objects.addAll(Game.getPlatformList());
            objects.addAll(Game.getOrcList());
            objects.addAll(Game.getTntList());
        }
        catch (NullPointerException ignored) {
        }

        for (Object object : objects) {

            if (collision(object)) {
                jumpSpeed = 1.5;
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

                return true;
            }

            if (StaticFunction.leftCollision(hero, tnt.getObstacle(), 3)) {

                if((((Tnt)tnt).getHitCount())==0){
                    ((Tnt)tnt).setHitCount(1);
                }
                if(((Tnt)tnt).isBlast()) isAlive=false;
                else{
                    tnt.getObstacle().setX(tnt.getObstacle().getX()-20);
                }
                return true;
            }

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

            // Right side collision of hero with orc left
            if (StaticFunction.leftCollision(orc.getOrc(), hero, 10)) {
                orc.getOrc().setX(orc.getOrc().getX() + 20);
            }

            // Left side collision of hero with orc right
            if (StaticFunction.leftCollision(hero, orc.getOrc(), 10)) {
                orc.getOrc().setX(orc.getOrc().getX() - 20);
            }

            // Bottom side collision of hero with orc top
            if (StaticFunction.bottomCollision(hero, orc.getOrc(), 3)) {
                return true;
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