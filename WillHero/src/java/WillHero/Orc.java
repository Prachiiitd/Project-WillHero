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
    private int jumpSpeed;
    private double fromHeight;
    private final ImageView orc;

    public Orc(int x, int y) {
        Random random = new Random();
        this.orc = setOrc(x, y);
        this.jumpHeight = random.nextInt(120, 170);
        this.jumpSpeed = 1;
        this.fromHeight = random.nextInt(150, 300);

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(5), e -> jump()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }


    public void jump() {
        this.orc.setY(orc.getY() + jumpSpeed);
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(Game.getPlatformList());
//        objects.addAll(Game.getHero());

        if(orc.getY() < fromHeight-jumpHeight){
            jumpSpeed = 1;
        }

        for(Object object : objects){
            if(collision(object)){
                jumpSpeed = -1;
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
                System.out.println(" top coliision with plATFOR in orc");
                fromHeight = platform.getIsLand().getBoundsInLocal().getMinY();
                return true;
            }
        }

        if(object instanceof Hero hero){
//            top side collision with hero
            if(StaticFunction.topCollision(hero.getHero(), orc, 2)) {
                System.out.println(" top collision with hero in orc");
//                fromHeight = hero.getHero().getBoundsInLocal().getMinY();
                return true;
            }
        }

        return false;
    }
}


class RedOrc extends Orc {
    private ImageView redOrc;

    public RedOrc(int x, int y) {
        super(x, y);
//    
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

