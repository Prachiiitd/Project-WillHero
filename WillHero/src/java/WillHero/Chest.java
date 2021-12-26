package WillHero;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public abstract class Chest implements Serializable {
    private transient ImageView chest;
    private boolean isClose;
    private final double jumpHeight;
    private final double dy;
    private double jumpSpeed;
    private transient final Timeline timeline;
    private final double x;
    private final double y;

    public Chest(double x, double y) {
        this.x = x;
        this.y = y;
        setChest();
        this.isClose = true;
        this.jumpHeight = 0.4;
        this.jumpSpeed = 0;
        this.dy = 0.01;
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new javafx.animation.KeyFrame(Duration.millis(5), e -> motion()));
        timeline.play();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setChest() throws NullPointerException {
        try {
            this.chest = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("chestClosed.png"))));
            this.chest.setFitHeight(50);
            this.chest.setPreserveRatio(true);
            this.chest.setX(this.x);
            this.chest.setY(this.y);

        }  catch (Exception e) {
            throw new NullPointerException("Closed chest couldn't be loaded");
        }
    }

    public void setOpenChest(ImageView imageView) throws NullPointerException {
        try {
            imageView.setImage(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("chestOpen.png"))));

        }  catch (Exception e) {
            throw new NullPointerException("OpenChest couldn't be loaded");
        }
    }

    public boolean isClose() {
        return isClose;
    }

    public ImageView getChest() {
        return chest;
    }

    public boolean collision(Object object) {

        if (object instanceof Hero hero) {

            if (hero.getHero().getBoundsInParent().intersects(chest.getBoundsInParent())) {
                if(isClose) {
                    isClose = false;
                    setOpenChest(chest);
                    this.getContent(hero);
                    return true;
                }
            }
        }

        if(object instanceof Platform platform) {

            if (StaticFunction.bottomCollision(chest, platform.getIsLand(), 4)) {
                if(!isClose) {

                }
                return true;
            }
        }

        return false;
    }

    public void motion(){
        this.chest.setY(chest.getY() + jumpSpeed);
        jumpSpeed += dy;

        for(Platform object : Game.getPlatformList()){
            if(collision(object)){
                jumpSpeed = jumpHeight;
                if (isClose) {
                    jumpSpeed = jumpSpeed > 0 ? -jumpSpeed : jumpSpeed;
                }

                break;
            }
        }
    }

    abstract public void getContent(Hero hero);
}



class CoinChest extends Chest{
    private final Coin [] coins;

    public CoinChest(double x, double y) {
        super(x, y);
        Random random = new Random();
        this.coins = new Coin[random.nextInt(0, 12) + 2];;
    }

    @Override
    public void getContent(Hero hero) {
        for(int i = 0; i < coins.length; i++) increaseReward(hero);
    }

    private void increaseReward(Hero hero) {
        hero.setReward(hero.getReward() + 1);
    }

}


class WeaponChest extends  Chest{

    private final Weapon weapon;

    public WeaponChest(double x, double y) {
        super(x, y);
        Random random = new Random();
        Weapon[] weaponList = {new Weapon1(), new Weapon2()};
        weapon = weaponList[random.nextInt(1,2)];
    }

    @Override
    public void getContent(Hero hero) {

        if(weapon instanceof Weapon1){
            hero.getHelmet().setChoice(0);
            hero.getHelmet().getWeapon(0).setActivate(true, true);
            hero.getHelmet().getWeapon(0).getWeaponImage().setX(hero.getHero().getX() + 5);
            hero.getHelmet().getWeapon(0).getWeaponImage().setY(hero.getHero().getY() + 5);
        }

        if(weapon instanceof Weapon2){
            hero.getHelmet().setChoice(1);
            hero.getHelmet().getWeapon(1).setActivate(true, true);
            hero.getHelmet().getWeapon(1).getWeaponImage().setX(hero.getHero().getX());
            hero.getHelmet().getWeapon(1).getWeaponImage().setY(hero.getHero().getBoundsInParent().getMaxY() - 15);
        }
    }
}