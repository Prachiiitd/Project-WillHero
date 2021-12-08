package WillHero;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LeaderBoard {

    private TableView<Hero> table = new TableView<>();

    @FXML
    private ImageView backIcon;

    public void start(Stage stage) throws IOException {

        URL toScene = getClass().getResource("LeaderBoard.fxml");
        table.setEditable(true);
        table.setBackground(new Background(new BackgroundFill(Color.DARKTURQUOISE, null, null)));
        table.setMaxSize(300, 400);
        TableColumn firstNameCol = new TableColumn("Rank");
        TableColumn lastNameCol = new TableColumn("Hero\nUsername");
        TableColumn emailCol = new TableColumn("High Score");
        firstNameCol.setMaxWidth(70);
        lastNameCol.setMaxWidth(400);
        emailCol.setMaxWidth(200);
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
        table.setLayoutX(50);
        table.setLayoutY(50);
        StaticFunction.setScene(stage, toScene, "Leaderboard");
    }

    public void setBack(MouseEvent backIcon) throws IOException {
        StaticFunction.clickResponse(this.backIcon);

        URL toScene = getClass().getResource("MainMenu.fxml");
        StaticFunction.setScene(StaticFunction.getStage(backIcon), toScene, "WillHero: Main Menu");
    }
}
