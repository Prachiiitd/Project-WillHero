package WillHero;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public abstract class Orc implements Serializable {

    private transient final ImageView orc;
    private transient Timeline tl;

    private final double jumpHeight;
    private final double dy;
    private double jumpSpeed;
    private int hp;
    private boolean isAlive;


    public Orc(int x, int y, int hp) {
        Random random = new Random();
        this.hp = hp;
        this.isAlive = true;
        this.orc = setOrc(x, y);
        this.jumpHeight = random.nextDouble(1, 1.3);
        this.jumpSpeed = 0;
        this.dy = random.nextDouble(0.008, 0.01);

        this.tl = new Timeline(new KeyFrame(Duration.millis(5), e -> jump()));
        this.tl.setCycleCount(Timeline.INDEFINITE);
        this.tl.play();
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void jump() {
        this.orc.setY(orc.getY() + jumpSpeed);
        jumpSpeed += dy;
        if (hp <= 0) {
            this.setAlive(false);
        }
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(Game.getPlatformList());
        objects.addAll(Game.getChestList());

        for (Object object : objects) {
            if (collision(object)) {
                jumpSpeed = jumpHeight;
                if (isAlive) {
                    jumpSpeed = jumpSpeed > 0 ? -jumpSpeed : jumpSpeed;
                    break;
                }
            }
        }
    }

    public void destroy() {
        if (tl != null && tl.getStatus() == Animation.Status.RUNNING) this.tl.stop();

        Image image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("marneKeBad.png")));
        this.orc.setImage(image);
        StaticFunction.setRotation(this.orc, 360, 500, -1, false);
        tl = new Timeline(new KeyFrame(Duration.millis(5), e -> {
            this.orc.setY(this.orc.getY() + 2);
            this.orc.setX(this.orc.getX() + 0.5);
        }));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }

    public ImageView getOrc() {
        return orc;
    }

    public abstract ImageView setOrc(int x, int y);

    public boolean collision(Object object) {

        if (object instanceof Platform platform) {
//            top side collision with platform
            if (StaticFunction.bottomCollision(orc, platform.getIsLand(), 4)) {
                return true;
            }

        }

        if (object instanceof Orc orci) {
            if (StaticFunction.bottomCollision(orc, orci.getOrc(), 3)) {
                return true;
            }
            if (StaticFunction.topCollision(orc, orci.getOrc(), 3)) {
                return true;
            }
        }

        if (object instanceof Obstacle tnt) {

            // Right side collision of hero with orc left

            if (StaticFunction.rightCollision(orc, tnt.getObstacle(), 3)) {
                System.out.println("Right side collision of hero with TNT right");
                if ((((Tnt) tnt).getHitCount()) == 0) {
                    ((Tnt) tnt).setHitCount(1);
                }

                if (((Tnt) tnt).isBlast()) {
                    isAlive = false;
                    destroy();
                }

                return true;
            }

            // Left side collision of hero with orc right
            if (StaticFunction.leftCollision(orc, tnt.getObstacle(), 3)) {
                System.out.println("Left side collision of hero with TNT right");

                if ((((Tnt) tnt).getHitCount()) == 0) {
                    ((Tnt) tnt).setHitCount(1);
                }

                if (((Tnt) tnt).isBlast()) {
                    isAlive = false;
                    destroy();
                }
                return true;
            }

            // Bottom side collision of hero with orc top
            if (StaticFunction.bottomCollision(orc, tnt.getObstacle(), 3)) {
                System.out.println("Bottom side collision of hero with TNT right");

                if ((((Tnt) tnt).getHitCount()) == 0) {
                    ((Tnt) tnt).setHitCount(1);
                }
                if (((Tnt) tnt).isBlast()) {
                    isAlive = false;
                    destroy();
                }

                return true;
            }

            // Top side collision of hero with orc bottom
            if (StaticFunction.topCollision(orc, tnt.getObstacle(), 3)) {
                System.out.println("Top side collision of hero with TNT bottom");

                if ((((Tnt) tnt).getHitCount()) == 0) {
                    ((Tnt) tnt).setHitCount(1);
                }
                if (((Tnt) tnt).isBlast()) {
                    isAlive = false;
                    destroy();
                }
                return true;
            }
        }

        if (object instanceof Chest chest) {

            // Bottom side collision of hero with orc top
            if (StaticFunction.bottomCollision(orc, chest.getChest(), 3)) {
                return true;
            }

            // Right side collision of hero with orc left
            if (StaticFunction.rightCollision(orc, chest.getChest(), 3)) {
                chest.getChest().setX(chest.getChest().getX() + 5);

            }

            // Left side collision of hero with orc right
            if (StaticFunction.leftCollision(orc, chest.getChest(), 3)) {
                chest.getChest().setX(chest.getChest().getX() - 5);
            }

            // Top side collision of hero with orc bottom
            if (StaticFunction.topCollision(orc, chest.getChest(), 3)) {
                return true;
            }
        }

        if (object instanceof Hero hero) {
            // Bottom side collision of orc with hero top
            return StaticFunction.bottomCollision(orc, hero.getHero(), 0);
        }

        return false;
    }


}


class RedOrc extends Orc {

    public RedOrc(int x, int y) {
        super(x, y, 10);
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


    public GreenOrc(int x, int y) {
        super(x, y, 12);
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

    private void increaseReward(Hero hero) {
        hero.setReward(hero.getReward() + 1);
    }
}


class BossOrc extends Orc {

    public BossOrc(int x, int y) {
        super(x, y, 150);
    }

    @Override
    public ImageView setOrc(int x, int y) throws NullPointerException {
        ImageView redOrc;
        try {
            redOrc = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("bossOrc.png"))));
            redOrc.setFitHeight(80);
            redOrc.setPreserveRatio(true);
            redOrc.setX(x);
            redOrc.setY(y);
            return redOrc;

        } catch (Exception e) {
            throw new NullPointerException("Coin couldn't be loaded");
        }
    }


}