package WillHero;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class Platform {

    private final int length;
    private final int id;
    private final ImageView isLand;
    static int allot = 0;
    private final int speed;

    public Platform(int id, int x, int y, int size, int speed) {
        System.out.println("in pla con" + id);
        this.isLand = setIsLnd(id, x, y, size);
        this.id = id;
        this.speed = speed;
        this.length = (int) isLand.getFitWidth();
        motion();
        allot++;
    }

    private ImageView setIsLnd(int id, int x, int y, int size) throws NullPointerException {
        ImageView isLand;
        try {
            System.out.println("in pla" + id);
            isLand = new ImageView(new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("island" + id + ".png")).getPath())));
            isLand.setPreserveRatio(true);
            isLand.setFitWidth(size);
            isLand.setX(x);
            isLand.setY(y);
            return isLand;
        } catch (FileNotFoundException e) {
            throw new NullPointerException("Failed to load Platform");
        }
    }

    public ImageView getIsLand() throws NullPointerException {
        return isLand;
    }

    private void motion() {
        StaticFunction.setTranslation(isLand, 0, speed, 3000, -1, true);
    }

    public int getId() {
        return id;
    }

    public boolean collided(ImageView obj, Group screen) throws ClassCastException {
        boolean isCollision = false;

        ImageView island = (ImageView) this.getIsLand().clone();
        island.setX(island.getX() + screen.getLayoutX());
        island.setY(island.getY() + screen.getLayoutY());
        System.out.println("in coll island hero X:" + island.getX() + " Y:" + island.getY());
        if (obj.getBoundsInParent().intersects(isLand.getBoundsInParent())) {
            isCollision = true;
        }

        return isCollision;
    }
}
