package WillHero;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public abstract class Orc {

    private final ImageView orc;
    private final double jumpHeight;
    private final double dy;
    private double jumpSpeed;

    private boolean isAlive;
    private Timeline tl;

    public Orc(int x, int y) {
        Random random = new Random();

        this.isAlive = true;
        this.orc = setOrc(x, y);
        this.jumpHeight = 1.2;
        this.jumpSpeed = 0;
        this.dy = 0.01;

        tl = new Timeline(new KeyFrame(Duration.millis(5), e -> jump()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setJumpSpeed(double jumpSpeed) {
        this.jumpSpeed = jumpSpeed;
    }

    public Timeline getTimeline() {
        return tl;
    }

    public void jump() {
        this.orc.setY(orc.getY() + jumpSpeed);
        jumpSpeed += dy;

        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(Game.getPlatformList());
        objects.addAll(Game.getChestList());

        for(Object object : objects){
            if(collision(object)){
                jumpSpeed = jumpHeight;
                if (isAlive) {
                    jumpSpeed = jumpSpeed > 0 ? -jumpSpeed : jumpSpeed;
                    break;
                }
            }
        }
    }

    public void destroy(){
        if(tl != null && tl.getStatus() == Animation.Status.RUNNING) this.tl.stop();

        Image image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("marneKeBad.png")));
        this.orc.setImage(image);
        StaticFunction.setRotation(this.orc, 360, 500, -1, false);
        tl = new Timeline(new KeyFrame(Duration.millis(5), e -> {
            this.orc.setY(this.orc.getY() + 1);
            this.orc.setX(this.orc.getX() + 0.5);
        }));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }

    public ImageView getOrc() {
        return orc;
    }

    public abstract ImageView setOrc(int x, int y);

    public boolean collision(Object object){

        if(object instanceof Platform platform){
//            top side collision with platform
            if(StaticFunction.bottomCollision( orc, platform.getIsLand(), 2)){
                System.out.println(" top collision with platform in orc");
                return true;
            }

        }
        if(object instanceof Chest chest){

            // Bottom side collision of hero with orc top
            if(StaticFunction.bottomCollision(orc, chest.getChest(), 0)) {
                System.out.println("Bottom side collision of orc with chest right");
                return true;
            }

            // Right side collision of hero with orc left
            if(StaticFunction.rightCollision(orc, chest.getChest(),0)) {
                System.out.println("Right side collision of orc with chest right");
                chest.getChest().setX(chest.getChest().getX() + 5);

            }

            // Left side collision of hero with orc right
            if(StaticFunction.leftCollision( orc, chest.getChest(),0)) {
                System.out.println("Left side collision of orc with chest right");
                chest.getChest().setX(chest.getChest().getX() - 5);
            }

            // Top side collision of hero with orc bottom
            if(StaticFunction.topCollision(orc,chest.getChest(),  0)) {
                System.out.println("not possible");
                return true;
            }
        }

        if(object instanceof Hero hero){

            // Left side collision of orc with hero right
//            if(StaticFunction.leftCollision(orc, hero.getHero(),0)) {
//                System.out.println("hckh Left side collision of orc with hero right");
//                orc.setX(orc.getX() + 5);
//                return true;
//            }

            // Right side collision of orc with hero left
//            if(StaticFunction.rightCollision(orc, hero.getHero(),0)) {
//                System.out.println("khfb Right side collision of hero with orc right");
//                orc.setX(orc.getX() - 5);
//                return true;
//            }

            // Bottom side collision of orc with hero top
            if(StaticFunction.bottomCollision(orc, hero.getHero(), 0)) {
                System.out.println("Bottom side collision of orc with hero right");
                return true;
            }

            // Top side collision of orc with hero bottom
//            if(StaticFunction.topCollision(orc, hero.getHero(),  0)) {
//                System.out.println("Top side collision of hero with orc bottom");
//                return true;
//            }
        }

        return false;
    }

}


class RedOrc extends Orc {
    private final ImageView redOrc;

    public RedOrc(int x, int y) {
        super(x, y);
        this.redOrc = super.getOrc();
    }

    @Override
    public ImageView setOrc(int x, int y) throws NullPointerException {
        ImageView redOrc;
        try {
            redOrc = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("red.png"))));
            redOrc.setFitHeight(40);
            redOrc.setPreserveRatio(true);
            redOrc.setX(x);
            redOrc.setY(y);
            return redOrc;

        } catch (Exception e) {
            throw new NullPointerException("Coin couldn't be loaded");
        }
    }

    private void increaseReward(Hero hero) {
        hero.setReward(hero.getReward() + 1);
    }
}


class GreenOrc extends Orc {

    private ImageView greenOrc;

    public GreenOrc(int x, int y) {
        super(x, y);

    }

    @Override
    public ImageView setOrc(int x, int y) throws NullPointerException {
        ImageView greenOrc;
        try {
            greenOrc = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("green.png"))));
            greenOrc.setFitHeight(40);
            greenOrc.setPreserveRatio(true);
            greenOrc.setX(x);
            greenOrc.setY(y);
            return greenOrc;
        } catch (Exception e) {
            throw new NullPointerException("Coin couldn't be loaded");
        }
    }


    public void collision(Hero hero) {
        increaseReward(hero);
        this.greenOrc.setVisible(false);
    }

    private void increaseReward(Hero hero) {
        hero.setReward(hero.getReward() + 1);
    }


}


class BossOrc extends Orc {

    public BossOrc(int x, int y) {
        super(x, y);
    }

    @Override
    public ImageView setOrc(int x, int y) {
        return null;
    }
}
