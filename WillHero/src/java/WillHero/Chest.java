package WillHero;

import javafx.scene.image.ImageView;
import java.util.ArrayList;

public abstract class Chest  {

    abstract  public  void collision(ImageView object);
    abstract public void getContent(Hero hero);
}


class CoinChest extends Chest{
    ArrayList<Coin> coins = new ArrayList<>();

    @Override
    public void collision(ImageView object) {

    }

    @Override
    public void getContent(Hero hero) {

    }
}


class WeaponChest extends  Chest{
    Weapon weapon;

    @Override
    public void collision(ImageView object) {

    }

    @Override
    public void getContent(Hero hero) {

    }
}