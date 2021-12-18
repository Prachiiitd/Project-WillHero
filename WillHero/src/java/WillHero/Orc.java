package WillHero;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public abstract class Orc {
    private final int jumpHeight;
    private double jumpSpeed;
    private double fromHeight;
    private final ImageView orc;

    public Orc(int x, int y) {
        Random random = new Random();
        this.orc = setOrc(x, y);
        this.jumpSpeed = 0;
        this.jumpHeight = random.nextInt(120, 170);
        this.fromHeight = random.nextInt(200, 300);

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(5), e -> jump()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }


    public void jump() {
        this.orc.setY(orc.getY() + jumpSpeed);
        ArrayList<Object> objects = new ArrayList<>(Game.getPlatformList());

        if(orc.getY() < fromHeight-jumpHeight){
            jumpSpeed = jumpSpeed > 0? jumpSpeed : -jumpSpeed;
        }

        for(Object object : objects){
            if(collision(object)){
                jumpSpeed = jumpSpeed > 0? -jumpSpeed : jumpSpeed;
                break;
            }
        }
    }

    public ImageView getOrc() {
        return orc;
    }

    public abstract ImageView setOrc(int x, int y);

    public boolean collision(Object object){

        if(object instanceof Platform platform){
//            top side collision with platform
            if(StaticFunction.bottomCollision( orc, platform.getIsLand(), 2)){
//                System.out.println(" top collision with platform in orc");
                fromHeight = platform.getIsLand().getBoundsInLocal().getMinY();
                return true;
            }
        }

        if(object instanceof Hero hero){

            // Left side collision of orc with hero right
            if(StaticFunction.leftCollision(orc, hero.getHero(),0)) {
                System.out.println("hckh Left side collision of hero with orc right");
                orc.setX(orc.getX() + 5);
                return true;
            }

            // Right side collision of orc with hero left
            if(StaticFunction.rightCollision(orc, hero.getHero(),0)) {
                System.out.println("khfb Right side collision of hero with orc right");
                orc.setX(orc.getX() - 5);
                return true;
            }

            // Bottom side collision of hero with orc top
            if(StaticFunction.bottomCollision(orc, hero.getHero(), 0)) {
                System.out.println("Bottom side collision of hero with orc right");
//                fromHeight = orc.getBoundsInLocal().getMinY();
//                jumpSpeed = 1;
//                return true;
            }

            // Top side collision of hero with orc bottom
            if(StaticFunction.topCollision(orc, hero.getHero(),  0)) {
                System.out.println("Top side collision of hero with orc bottom");
//                return true;
            }
        }

        return false;
    }
}


class RedOrc extends Orc {
    private ImageView redOrc;

    public RedOrc(int x, int y) {
        super(x, y);
        this.redOrc = super.getOrc();
    }

    @Override
    public ImageView setOrc(int x, int y) throws NullPointerException {
        ImageView redorc;
        try {
            redorc = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("red.png"))));
            redorc.setFitWidth(35);
            redorc.setPreserveRatio(true);
            redorc.setX(x);
            redorc.setY(y);
            return redorc;
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
        ImageView greenorc;
        try {
            greenorc = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("green.png"))));
            greenorc.setFitWidth(50);
            greenorc.setPreserveRatio(true);
            greenorc.setX(x);
            greenorc.setY(y);
            return greenorc;
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

