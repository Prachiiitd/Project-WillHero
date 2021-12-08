package WillHero;

import java.util.ArrayList;

public class Helmet {
    private final ArrayList<Weapon> weapons;

    public Helmet(){
        weapons = new ArrayList<>();
    }

    public void addWeapon(Weapon weapon){
        weapons.add(weapon);
    }

    public  Weapon getWeapon(int index){
        return weapons.get(index);
    }
    
    public  int getPower(int i){
        return weapons.get(i).getDamage();
    }
}