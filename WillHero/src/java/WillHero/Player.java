package WillHero;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

public class Player {

    private Label name;
//    private final Node player;
    private boolean isAlive;
    private int reward;
    private int location;
    private float x;
    private float y;

    public Player() {
        isAlive = true;
    }

    public Label getName() {
        return name;
    }

    public void setName(Label name) {
        this.name = name;
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

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
    public boolean isAlive() {
        return isAlive;
    }
}
