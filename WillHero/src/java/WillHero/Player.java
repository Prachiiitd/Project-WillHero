package WillHero;

import javafx.scene.control.Label;

public class Player {

    private Label name;

    public Player(Label nameLabel) {
        this.name = nameLabel;
    }

    public Label getName() {
        return name;
    }
}
