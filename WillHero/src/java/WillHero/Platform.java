package WillHero;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Objects;

public class Platform implements Serializable {

    private transient final ImageView isLand;

    private final int speed;

    public Platform(int id, int x, int y, int size, int speed) {

        this.isLand = setIsLnd(id, x, y, size);
        this.speed = speed;
        motion();
    }

    private ImageView setIsLnd(int id, int x, int y, int size) throws NullPointerException {
        ImageView isLand;
        try {
            isLand = new ImageView(new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("island" + id + ".png")).getPath())));
            isLand.setPreserveRatio(true);
            isLand.setFitWidth(size);
            isLand.setX(x);
            isLand.setY(y);
            return isLand;
        } catch (FileNotFoundException | NullPointerException e) {
            isLand = null;
            System.out.println("Failed to load Platform");
        }
        return null;
    }

    public ImageView getIsLand() throws NullPointerException {
        return isLand;
    }

    private void motion() {
        StaticFunction.setTranslation(this.getIsLand(),0,speed,2000,-1,true);
    }
}