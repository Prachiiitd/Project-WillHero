package WillHero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Objects;

public class Coin {
    private final ImageView coin;

    public Coin(int x, int y) {
        this.coin = setCoin(x,y);
    }

    private ImageView setCoin(int x, int y) throws NullPointerException {
        ImageView coin;
        try {
            coin = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/coin.png"))));
        }  catch (Exception e) {
            throw new NullPointerException("Coin couldn't be loaded");
        }
        coin.setFitWidth(20);
        coin.setPreserveRatio(true);
        coin.setX(x);
        coin.setY(y);
        return coin;
    }

    public ImageView getCoin() {
        return coin;
    }

    public void collision(Hero hero) {
        increaseReward(hero);
        this.coin.setVisible(false);
    }

    private void increaseReward(Hero hero) {
        hero.setReward(hero.getReward() + 1);
    }
}

