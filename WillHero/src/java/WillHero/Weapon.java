package WillHero;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.io.Serializable;
import java.util.Objects;

public abstract class Weapon implements Serializable {

    private transient final ImageView weaponImage;
    private boolean active = false;
    private int damage;
    private int range;

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

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range){
        this.range = range;
    }

    public void collision(Orc orc) {
        if (orc.getOrc().getBoundsInParent().intersects(weaponImage.getBoundsInParent())) {
            orc.setHp(orc.getHp() - damage);
        }
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
}

class Weapon1 extends Weapon implements Cloneable{

    private transient Timeline fTimeline;
    private transient Timeline bTimeline;
    public Weapon1() {
        super(5, 100);
        this.fTimeline = null;
        this.bTimeline = null;
    }


    @Override
    public Weapon clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (Weapon) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
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

        Weapon1 c_weapon = (Weapon1) this.clone();

        if (fTimeline != null && fTimeline.getStatus() == Animation.Status.RUNNING) {
            fTimeline.stop();
        }
        if (bTimeline != null && bTimeline.getStatus() == Animation.Status.RUNNING) {
            bTimeline.stop();
        }

        c_weapon.getWeaponImage().setFitWidth(25);

        fTimeline = (new Timeline(new KeyFrame(Duration.millis(0.8), fEvent -> attackAnimation(c_weapon, 1))));
        fTimeline.setCycleCount(getRange());
        fTimeline.setOnFinished(e -> {
            bTimeline = new Timeline(new KeyFrame(Duration.millis(0.8), bEvent -> attackAnimation(c_weapon, -1)));
            bTimeline.setCycleCount(getRange());
            bTimeline.play();
        });

        fTimeline.play();
    }

    @Override
    public void upgrade(ImageView character) {
        super.setRange(getRange()+1);
        super.setDamage(getDamage()+1);

    }
    private void attackAnimation(Weapon c_weapon, int i) {
        System.out.println("Weapon 1 attack in progress");
        c_weapon.getWeaponImage().setX(c_weapon.getWeaponImage().getX() + i);
    }
}

class Weapon2 extends Weapon {
    private transient Timeline fTimeline;
    private transient Timeline bTimeline;

    public Weapon2() {
        super(3, 100);
        this.fTimeline = null;
        this.bTimeline = null;
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

        Weapon2 c_weapon = this;

        if (fTimeline != null && fTimeline.getStatus() == Animation.Status.RUNNING) {
            fTimeline.stop();
        }
        if (bTimeline != null && bTimeline.getStatus() == Animation.Status.RUNNING) {
            bTimeline.stop();
        }

        c_weapon.getWeaponImage().setFitWidth(50);

        fTimeline = (new Timeline(new KeyFrame(Duration.millis(0.8), fEvent -> attackAnimation(c_weapon, 1))));
        fTimeline.setCycleCount(getRange());
        fTimeline.setOnFinished(e -> {
            bTimeline = new Timeline(new KeyFrame(Duration.millis(0.8), bEvent -> attackAnimation(c_weapon, -1)));
            bTimeline.setCycleCount(getRange());
            bTimeline.play();
        });

        fTimeline.play();
    }

    private void attackAnimation(Weapon c_weapon, int i) {
        c_weapon.getWeaponImage().setFitWidth(c_weapon.getWeaponImage().getFitWidth() + i);
    }

    @Override
    public void upgrade(ImageView character) {
        super.setRange(getRange()+1);
        super.setDamage(getDamage()+1);
    }
}