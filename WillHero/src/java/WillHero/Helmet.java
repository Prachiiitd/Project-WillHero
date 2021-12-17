package WillHero;

import java.util.ArrayList;

public class Helmet {
    private final ArrayList<Weapon> weapons;

    public Helmet(){
        weapons = setWeapons();
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