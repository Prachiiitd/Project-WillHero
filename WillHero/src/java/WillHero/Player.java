package WillHero;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class Player {

    private final Label name;
    private final ImageView player;

    private boolean isAlive;
    private int reward;
    private int location;

    public Player(Label name, int location) {
        isAlive = true;
        this.name = name;
        this.location=location;
        this.player = setPlayer();
        jump();
    }

    private ImageView setPlayer(){
        ImageView player;
        try {
            player = new ImageView(new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("helmet.png")).getPath())));
            player.setPreserveRatio(true);
            player.setFitWidth(70);
            player.setX(345);
            player.setY(295);
        } catch (FileNotFoundException | NullPointerException e) {
            player = null;
            System.out.println("Failed to load player");
            e.printStackTrace();
        }

        return player;
    }

    public Label getName() {
        return name;
    }

    public ImageView getPlayer() throws NullPointerException {
        return player;
    }


    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = 1000;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        System.out.println(this.reward);
        this.reward = 50;
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

    public void jump(){

    }
}
