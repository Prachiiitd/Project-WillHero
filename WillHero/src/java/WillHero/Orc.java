package WillHero;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Orc {
    private final int jumpHeight;
    private int jumpSpeed;
    private double height;
    private final ImageView orc;
    private final Timeline tl;

    public Orc(int x, int y) {
        this.orc = setOrc(x,y);
        this.jumpHeight = 150;
        this.jumpSpeed = 4;
        this.height = 300;
        this.motion();
        this.tl = new Timeline(new KeyFrame(Duration.millis(5), e -> motion()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }



    private void motion() {
        this.orc.setY(orc.getY() + jumpSpeed);

        for(Platform platform : Game.getPlatformList()){
            if((orc.getX() < Game.getHero().getHero().getX() + Game.getHero().getHero().getFitWidth()
                    && orc.getX() + orc.getFitWidth() > Game.getHero().getHero().getX()
                    && orc.getY() + orc.getFitHeight() > Game.getHero().getHero().getY())
                    || collision(platform)) {
                jumpSpeed = -1;
                break;
            }

            if(orc.getY() < height-jumpHeight && !collision(platform)) {
                jumpSpeed = 1;
                break;
            }

            if(orc.getY() - Game.getHero().getHero().getY() < height-jumpHeight && collision(platform)) {
                System.out.println("Collision on the top");
                jumpSpeed = 1;
                break;
            }

        }
    }

    public ImageView getOrc() {
        return orc;
    }

    public abstract  ImageView setOrc(int x,int y);

    public boolean collision(Object object) {

        if (object instanceof Hero hero) {
            if (orc.getBoundsInParent().intersects(hero.getHero().getBoundsInParent())) {
                System.out.println("Collision");

                if (orc.getY() + orc.getFitHeight() >= hero.getHero().getY() && orc.getY() + orc.getFitHeight() <= hero.getHero().getY() + hero.getHero().getFitHeight()) { //collision on the bottom
                    System.out.println("Collision on the bottom" + hero.getHero().getY() + " " + (orc.getY()  + orc.getFitHeight()));
                    height = Math.min(hero.getHero().getY(), height);
                    hero.setAlive(false);
                    return true;
                }
            }
        }

        if(object instanceof Platform platform){
            if (orc.getBoundsInParent().intersects(platform.getIsLand().getBoundsInParent())) {
                height = Math.min(platform.getIsLand().getY(), height);
                return true;
            }
        }
        return false;
    }
}


class RedOrc extends Orc {
    private  ImageView redOrc;
    public RedOrc(int x, int y){
        super(x,y);
//        super.
        this.redOrc=super.getOrc();
    }
    @Override
    public ImageView setOrc(int x, int y) throws NullPointerException {
        ImageView redorc;
        try {
            redorc = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("red.png"))));
            redorc.setFitWidth(50);
            redorc.setPreserveRatio(true);
            redorc.setX(x);
            redorc.setY(y);
            return redorc;
        }  catch (Exception e) {
            throw new NullPointerException("Coin couldn't be loaded");
        }
    }

    private void increaseReward(Hero hero) {
        hero.setReward(hero.getReward() + 1);
    }
}




class GreenOrc extends Orc {

    private  ImageView greenOrc;
    public GreenOrc(int x, int y) {
        super(x,y);

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
        }  catch (Exception e) {
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

