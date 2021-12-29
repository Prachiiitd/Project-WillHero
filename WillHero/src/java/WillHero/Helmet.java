package WillHero;

import javafx.scene.layout.AnchorPane;

import java.io.Serializable;
import java.util.ArrayList;

public class Helmet implements Serializable {

    private final ArrayList<Weapon> weapons;
    int choice;

    public Helmet(Weapon weapon1, Weapon weapon2, int choice) {
        weapons = new ArrayList<>();
        weapons.add(weapon1);
        weapons.add(weapon2);
        this.choice = choice;

    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
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