package WillHero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public abstract class Weapon {

    private final ImageView weaponImage;
    private final int damage;
    private final int range;
    private boolean active = false;

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

    public boolean collided(ImageView object){
        //to  implemented

        return true;
    }

    public void setActivate(){
        active = true;
    }

    public boolean isActive(){
        return active;
    }
    public abstract void attack(ImageView character);
    public abstract void upgrade(ImageView character);
}


class Weapon1 extends Weapon {

    public Weapon1() {
        super(1, 1);
    }

    @Override
    public ImageView setWeaponImage() {
        return new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Shuriken.png"))));
    }

    @Override
    public void attack(ImageView character) {
        System.out.println("Weapon 1 attack");
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
        return new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("blade.png"))));
    }

    @Override
    public void attack(ImageView character) {

    }

    @Override
    public void upgrade(ImageView character) {

    }
}
