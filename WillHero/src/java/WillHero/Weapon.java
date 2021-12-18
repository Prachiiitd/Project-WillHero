package WillHero;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Objects;

public abstract class Weapon implements Cloneable {

    private final ImageView weaponImage;
    private boolean active = false;
    private final int damage;
    private final int range;

    public Weapon(int damage, int range) {
        this.weaponImage = setWeaponImage();
        this.damage = damage;
        this.range = range;
    }

    public abstract ImageView setWeaponImage();

    public ImageView getWeaponImage() {
        return weaponImage;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public boolean collision(Orc orc) {

        if (orc.getOrc().getBoundsInParent().intersects(weaponImage.getBoundsInParent())) {
            System.out.println("Collision with Weapon");
//                orc.setHealth(orc.getHealth() - damage);
            orc.setAlive(false);
            return true;
        }

        return false;
    }

    public void setActivate(boolean active, boolean visible) {
        this.getWeaponImage().setVisible(visible);
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public abstract void attack(Hero hero);

    public abstract void upgrade(ImageView character);

    @Override
    public Weapon clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (Weapon) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

class Weapon1 extends Weapon {

    public Weapon1() {
        super(10, 500);
    }

    @Override
    public ImageView setWeaponImage() {

        try {
            ImageView weapon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Shuriken.png"))));
            weapon.setFitWidth(25);
            weapon.setPreserveRatio(true);
            weapon.setVisible(false);
            return weapon;

        } catch (Exception e) {
            throw new NullPointerException("Closed chest couldn't be loaded");
        }
    }

    @Override
    public void attack(Hero hero) {
        System.out.println("Weapon 1 attack");
        Weapon1 c_weapon = this;
        StaticFunction.setTranslation(getWeaponImage(), getRange(), 0, 10000, 5, false);
        StaticFunction.setRotation(getWeaponImage(), 360, 500, -1, false);
    }

    @Override
    public void upgrade(ImageView character) {

    }
}

class Weapon2 extends Weapon {
    private final int defaultWidth;
    private final int defaultHeight;
    private Timeline fTimeline;
    private Timeline bTimeline;

    public Weapon2() {
        super(1, 1);
        this.fTimeline = null;
        this.bTimeline = null;
        this.defaultWidth = 50;
        this.defaultHeight = 10;
    }

    @Override
    public ImageView setWeaponImage() {

        try {
            ImageView weapon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Lance.png"))));
            weapon.setFitWidth(50); // 150 the max scaling
            weapon.setFitHeight(10); // 10 the max scaling

            weapon.setVisible(false);
            return weapon;

        } catch (Exception e) {
            throw new NullPointerException("Closed chest couldn't be loaded");
        }
    }

    @Override
    public void attack(Hero hero) {

        System.out.println("Weapon 2 attack attack");
        Weapon2 c_weapon = this;

        if (fTimeline != null && fTimeline.getStatus() == Animation.Status.RUNNING) {
            fTimeline.stop();
        }
        if (bTimeline != null && bTimeline.getStatus() == Animation.Status.RUNNING) {
            bTimeline.stop();
        }

        c_weapon.getWeaponImage().setFitWidth(50);

        fTimeline = (new Timeline(new KeyFrame(Duration.millis(0.8), fEvent -> attackAnimation(c_weapon, 1))));
        fTimeline.setCycleCount(150);
        fTimeline.setOnFinished(e -> {
                    bTimeline = new Timeline(new KeyFrame(Duration.millis(0.8), bEvent -> attackAnimation(c_weapon, -1)));
                    bTimeline.setCycleCount(150);
                    bTimeline.play();
                });

        fTimeline.play();
    }

    private void attackAnimation(Weapon c_weapon, int i) {
        System.out.println("Weapon 2 attack in progress");
        c_weapon.getWeaponImage().setFitWidth(c_weapon.getWeaponImage().getFitWidth() + i);
    }

    @Override
    public void upgrade(ImageView character) {

    }
}
