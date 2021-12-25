package WillHero;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Obstacle implements Serializable {

    private transient final ImageView obstacle;

    public Obstacle(double x, double y) {
        obstacle= setObstacle(x, y);
    }

    public  ImageView getObstacle() {
        return obstacle;
    }

    public abstract ImageView setObstacle(double x, double y);

}

class Tnt extends Obstacle {

    private transient final double jumpHeight;
    private transient final double dx;
    private transient final ImageView tnt;
    private boolean blast;
    private double vJumpSpeed;
    private double hJumpSpeed;
    private transient final Timeline tl;
    private long blastTime;
    private double dy;
    private int hitCount;

    public Tnt(double x, double y) {
        super(x, y);
        this.tnt = setObstacle(x, y);
        this.jumpHeight = 1;
        this.vJumpSpeed = 0;
        this.hJumpSpeed = 0;
        this.blastTime=0;
        this.hitCount = 0;
        this.dy = 0.01;
        this.dx = 0.01;
        this.blast=false;

        this.tl = new Timeline(new KeyFrame(Duration.millis(5), e -> jump()));
        this.tl.setCycleCount(Timeline.INDEFINITE);
        this.tl.play();
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public boolean isBlast() {
        return blast;
    }

    @Override
    public ImageView setObstacle(double x, double y) throws NullPointerException {
        ImageView tnt;
        try {
            tnt = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("tnt.png"))));
            tnt.setFitHeight(40);
            tnt.setPreserveRatio(true);
            tnt.setX(x);
            tnt.setY(y);
            return tnt;
        } catch (Exception e) {
            throw new NullPointerException("Obstacle couldn't be loaded");
        }
    }


    private void jump() {
        if( hitCount>0 && !blast){
            if(blastTime>=5000)
                setBlast();
            blastTime+=5;

        }
        if(hitCount==0){
            vJumpSpeed=0.4;
            hJumpSpeed=0;
        }
        else if(hitCount==1 ){

            dy=0.05;
            hJumpSpeed=0;
            vJumpSpeed+=dy;
        }
        else if(hitCount==2){
            dy=0.01;
            vJumpSpeed+=dy;
            hJumpSpeed+=dx;
        }
        super.getObstacle().setY(super.getObstacle().getY()+ vJumpSpeed);
        super.getObstacle().setX(super.getObstacle().getX() +hJumpSpeed);
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(Game.getPlatformList());
        objects.addAll(Game.getChestList());

        for(Object object : objects){
            if(collision(object)){

                if(hitCount==1) {
                    vJumpSpeed = jumpHeight * 2/3;
                }
                else if(hitCount==2) {
                    vJumpSpeed = jumpHeight;
                }
                vJumpSpeed = vJumpSpeed > 0 ? -vJumpSpeed : vJumpSpeed;
                break;
            }
        }
    }


    public void setBlast(){
        try {
            super.getObstacle().setImage(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("blast.gif"))));
            blast=true;
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished( event -> {
                super.getObstacle().setVisible(false);
                blast=false;
                tl.stop();
            });
            delay.play();

        }  catch (NullPointerException e) {
            throw new NullPointerException("OpenChest couldn't be loaded");
        }

    }


    private boolean collision(Object object) {

        if (object instanceof Platform platform) {
//          top side collision with platform
            if (StaticFunction.bottomCollision(super.getObstacle(), platform.getIsLand(), 7)) {

                if (hitCount == 0) {
                    super.getObstacle().setY(platform.getIsLand().getBoundsInParent().getMinY() - super.getObstacle().getFitHeight());
                }

                if (hitCount == 2) {
                    hitCount = 1;
                }
                return true;
            }
            return false;
        }

        if (object instanceof Hero || object instanceof Orc) {
            ImageView objectImage;
            if (object instanceof Hero) {
                objectImage = ((Hero) object).getHero();
            } else {
                objectImage = ((Orc) object).getOrc();
            }

            if (StaticFunction.bottomCollision(super.getObstacle(),objectImage,3) && blast) {
                objectImage.setVisible(false);
            }
        }

        if (object instanceof Chest chest) {
            // Bottom side collision of hero with orc top
            if (StaticFunction.bottomCollision(super.getObstacle(), chest.getChest(), 0)) {
                return true;
            }

            // Right side collision of hero with orc left
            if (StaticFunction.rightCollision(super.getObstacle(), chest.getChest(), 0)) {
                chest.getChest().setX(chest.getChest().getX() + 5);
            }

            // Left side collision of hero with orc right
            if (StaticFunction.leftCollision(super.getObstacle(), chest.getChest(), 0)) {
                chest.getChest().setX(chest.getChest().getX() - 5);
            }

            // Top side collision of hero with orc bottom
            return StaticFunction.topCollision(super.getObstacle(), chest.getChest(), 0);
        }
        return false;
    }


}