package WillHero;

import javafx.scene.layout.AnchorPane;

import java.io.Serializable;
import java.util.ArrayList;

public class Helmet implements Serializable {

    private final ArrayList<Weapon> weapons;
    int choice = 0;

    public Helmet(){
        weapons = setWeapons();
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    private ArrayList<Weapon> setWeapons() {

        ArrayList<Weapon> weapons = new ArrayList<Weapon>();
        weapons.add(new Weapon1());
        weapons.add(new Weapon2());
        return weapons;
    }

    public void upgradeWeapon(Weapon weapon){
        weapons.add(weapon);
    }

    public  Weapon getWeapon(int index){
        return weapons.get(index);
    }

    public  int get(int i){
        return weapons.get(i).getDamage();
    }
}