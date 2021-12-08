package WillHero;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class Hero {

    private final Label name;
    private final ImageView hero;

    private boolean isAlive;
    private int reward;
    private int location;

    public Hero(Label name, int location) {
        isAlive = true;
        this.name = name;
        this.location=location;
        this.hero = setHero();
    }

    private ImageView setHero(){
        ImageView hero;
        try {
            hero = new ImageView(new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("hero.png")).getPath())));
            hero.setPreserveRatio(true);
            hero.setFitWidth(100);
            hero.setX(350);
            hero.setY(150);

        } catch (FileNotFoundException | NullPointerException e) {
            hero = null;
            System.out.println("Failed to load hero");
            e.printStackTrace();
        }

        return hero;
    }

    public Label getName() {
        return name;
    }

    public ImageView getHero() throws NullPointerException {
        return hero;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public void setScoreLabel(Label reward, Label location) {
        reward.setText(String.valueOf(this.reward));
        location.setText(String.valueOf(this.location));
    }


    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void jump() {
        int height = 150;
        int time  = 500;
        StaticFunction.setTranslation(hero, 0, -height, time,1, false);
    }

    public double getSpeed() {
        return -1;
    }

}
