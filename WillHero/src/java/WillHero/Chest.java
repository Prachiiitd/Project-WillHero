package WillHero;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public abstract class Chest  {
    private ImageView chest;
    private boolean isClose;
    private int jumpSpeed;
    private double fromHeight;
    private final Timeline timeline;

    public Chest(int x, int y) {
        this.chest = setChest(x, y);
        this.isClose = true;
        this.jumpSpeed = 1;
        this.fromHeight = 300;
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new javafx.animation.KeyFrame(Duration.millis(10), e -> motion()));
        timeline.play();
    }

    public ImageView setChest(double x, double y) throws NullPointerException {
        try {
            chest = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("chestClosed.png"))));
            chest.setFitWidth(50);
            chest.setPreserveRatio(true);
            chest.setX(x);
            chest.setY(y);
            return chest;
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
//            System.out.println("collision with chest called");
            if (hero.getHero().getBoundsInParent().intersects(chest.getBoundsInParent())) {
                System.out.println("collision with chest");
                if(isClose) {
                    isClose = false;
                    timeline.stop();
                    setOpenChest(chest);
                    this.getContent(hero);
                    return true;
                }
            }
        }

        if(object instanceof Platform platform) {

            if (StaticFunction.bottomCollision(chest, platform.getIsLand(), 0)) {
                fromHeight = platform.getIsLand().getBoundsInLocal().getMinY();
                return true;
            }
        }

        return false;
    }

    public void motion(){
        this.chest.setY(chest.getY() + jumpSpeed);

        for(Platform object : Game.getPlatformList()){
            if(collision(object)){
                if (isClose) {
                    jumpSpeed = -1;
                }
                else
                    jumpSpeed = 0;
                break;
            }

            if(chest.getY() < fromHeight - 28){
                jumpSpeed = 1;
                break;
            }
        }
    }

    abstract public void getContent(Hero hero);
}



class CoinChest extends Chest{
    private final Coin [] coins;

    public CoinChest(int x, int y) {
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

    public WeaponChest(int x, int y) {
        super(x, y);
        Random random = new Random();
        Weapon[] weaponList = {new Weapon1(), new Weapon2()};
        weapon = weaponList[random.nextInt(0, 1)];
    }

    @Override
    public void getContent(Hero hero) {
        if(weapon instanceof Weapon1){
            hero.getHelmet().setChoice(0);
            hero.getHelmet().getWeapon(0).setActivate(true);
        }
        if(weapon instanceof Weapon2){
            hero.getHelmet().setChoice(1);
            hero.getHelmet().getWeapon(1).setActivate(true);
        }
    }
}