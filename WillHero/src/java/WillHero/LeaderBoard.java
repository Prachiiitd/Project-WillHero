package WillHero;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class LeaderBoard {

    private final TableView<Hero> table = new TableView<>();

    @FXML
    private ImageView backIcon;

    private AnchorPane anchorPane;
    private ArrayList<String> players;
    private ArrayList<Hero> heroes;
    private ReGenerateHero generateSprites;


    public void start(Stage stage, AnchorPane anchorPane) throws IOException {
        this.anchorPane = anchorPane;
        this.generateSprites =new ReGenerateHero();
        this.players = new LoadGames().getLoadablePlayersList();
        this.heroes = new ArrayList<>();
        loadHeroes();
        printHeroes();
    }

    private void loadHeroes() {
        for (String player: players) {
            try {
                Hero hero = generateSprites.getHero(player);
                heroes.add(hero);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    private void printHeroes() {
        for(Hero hero : heroes)
            System.out.println("Name: " + hero.getName() + " Location: " + hero.getLocation() + " Reward: " + hero.getReward());

    }

    public void setBack(MouseEvent backIcon) throws IOException {
        StaticFunction.clickResponse(this.backIcon);

        URL toScene = getClass().getResource("MainMenu.fxml");
        StaticFunction.setScene(StaticFunction.getStage(backIcon), toScene, "WillHero: Main Menu");
    }
}