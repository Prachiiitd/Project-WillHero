package WillHero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Translate;

import java.time.Duration;
import java.util.Objects;

public abstract class Weapon implements Cloneable{

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

    public boolean collision(ImageView object){
        //to  implemented

        return true;
    }

    public void setActivate(boolean active, boolean visible) {
        this.getWeaponImage().setVisible(visible);
        this.active = active;
    }

    public boolean isActive(){
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
        }  catch (Exception e) {
            throw new NullPointerException("Closed chest couldn't be loaded");
        }
    }

    @Override
    public void attack(Hero hero) {
        System.out.println("Weapon 1 attack");
        Weapon1 c_weapon = this;
        StaticFunction.setTranslation(c_weapon.getWeaponImage(), getRange(), 0, 10000, 5, false);
        StaticFunction.setRotation(c_weapon.getWeaponImage(), 360, 500, -1, false);
    }

    @Override
    public void upgrade(ImageView character) {

    }
}

class Weapon2 extends Weapon {

    public Weapon2() {
        super(1, 1);
    }

    @Override
    public ImageView setWeaponImage() {

        try {
            ImageView weapon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Shuriken.png"))));
            weapon.setFitWidth(50);
            weapon.setPreserveRatio(true);
            weapon.setVisible(false);
            return weapon;
        }  catch (Exception e) {
            throw new NullPointerException("Closed chest couldn't be loaded");
        }
    }

    @Override
    public void attack(Hero hero) {

    }

    @Override
    public void upgrade(ImageView character) {

    }
}
