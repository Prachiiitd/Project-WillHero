package WillHero;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class ResumeGame {

    @FXML
    private ImageView backIcon;

    private AnchorPane anchorPane;
    private Stage stage;

    private ArrayList<String> loadGames;
    private RegenerateSprite reGenerateSprites;
    private ReGenerateHero reGenerateHero;
    private ArrayList<Hero> heroes;
    private String gameName;

    private Hero generatedHero;
    private final ArrayList<Platform> generatedPlatform = new ArrayList<>();
    private final ArrayList<Coin> generatedCoin = new ArrayList<>();
    private final ArrayList<Obstacle> generatedObstacle = new ArrayList<>();
    private final ArrayList<Chest> generatedChest = new ArrayList<>();
    private final ArrayList<Orc> generatedOrc = new ArrayList<>();
    private int choice;

    public void start(Stage stage, AnchorPane anchorPane) throws IOException {
        this.anchorPane = anchorPane;
        this.stage = stage;
        this.heroes = new ArrayList<>();
        this.reGenerateHero = new ReGenerateHero();
        this.loadGames = new LoadGames().getLoadableGamesList();
        this.reGenerateSprites = new RegenerateSprite(generatedPlatform, generatedCoin,
                                        generatedObstacle, generatedChest, generatedOrc);
        this.choice = -1;
        loadHeroes();
        listGame();
    }

    private void loadHeroes() {
        for (String _game: loadGames) {
            try {
                Hero hero = reGenerateHero.getHero(_game);
                heroes.add(hero);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void listGame() {
        // heroes to be loaded on the AnchorPane.
        choice = 0;
        generatedHero = heroes.get(choice);
        gameName = loadGames.get(choice);

        try {
            storeSprites();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void storeSprites() throws IOException, ClassNotFoundException {
        reGenerateSprites.regenerate(gameName);
        resumeGame();
    }

    private void resumeGame(){
        System.out.println(generatedHero.getName());
        System.out.println(generatedPlatform.size());
        System.out.println(generatedChest.size());
        System.out.println(generatedCoin.size());
        System.out.println(generatedObstacle.size());
        System.out.println(generatedOrc.size());

        AnchorPane gameAnchorPane;
        StackPane stackPane = new StackPane();
        VBox vBox = new VBox();

        try {
            FXMLLoader root_gameAnchorPane = new FXMLLoader(Objects.requireNonNull(getClass().getResource("Game.fxml")));

            gameAnchorPane = root_gameAnchorPane.load();
            gameAnchorPane.setBackground(StaticFunction.defaultBackground());
            stackPane.getChildren().add(gameAnchorPane);

            Image icon = new Image(new FileInputStream(Objects.requireNonNull(StaticFunction.class.getResource("mainIcon.png")).getPath()));
            stage.setTitle("WillHero: " + generatedHero.getName());

            Game gameController = root_gameAnchorPane.getController();
            gameController.resume(stage, generatedHero, vBox, stackPane, gameAnchorPane,
                                    generatedPlatform, generatedCoin, generatedObstacle, generatedChest, generatedOrc);

            stage.getIcons().add(icon);
            vBox.getChildren().add(stackPane);
            Scene scene = new Scene(vBox);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBack(MouseEvent backIcon) throws IOException {
        StaticFunction.clickResponse(this.backIcon);
        URL toScene = getClass().getResource("MainMenu.fxml");
        StaticFunction.setScene(StaticFunction.getStage(backIcon), toScene, "WillHero: Main Menu");
    }
}
