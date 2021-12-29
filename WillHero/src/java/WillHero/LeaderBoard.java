package WillHero;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class LeaderBoard implements Initializable {
    @FXML
    private  TableView<Hero> table;
    @FXML
    private TableColumn<Hero, String> name;
    @FXML
    private TableColumn<Hero, Integer> reward;
    @FXML
    private TableColumn<Hero, Integer> location;


    @FXML
    private ImageView backIcon;

    private AnchorPane anchorPane;
    private ArrayList<String> players;
    private ArrayList<Hero> heroes;
    private ReGenerateHero reGenerateHero;
    private Stage stage;


    public void start(Stage stage, AnchorPane anchorPane,ArrayList<String> players) throws IOException {
        this.anchorPane = anchorPane;
        this.stage = stage;
        this.reGenerateHero =new ReGenerateHero();
        this.players = players;
        this.heroes = new ArrayList<>();
//
//        loadHeroes();
//        printHeroes();
    }

    private void loadHeroes() {
//        StaticFunction.getPlayers(players);
        heroes=StaticFunction.getHeroes();
//        for (String player: players) {
//            try {
//                Hero hero = reGenerateHero.getHero(player);
//                heroes.add(hero);
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.reGenerateHero =new ReGenerateHero();
//        this.players = players;
//        this.players=new LoadGames().getLoadablePlayersList();
//        this.heroes = new ArrayList<>();
        loadHeroes();
        printHeroes();
        ObservableList<Hero> data = table.getItems();
        for(Hero hero : heroes)
            data.add(hero);
        table.setItems(data);
        name.setCellValueFactory(new PropertyValueFactory<Hero,String>( "name" ));
        reward.setCellValueFactory(new PropertyValueFactory<Hero,Integer>( "reward" ));
        location.setCellValueFactory(new PropertyValueFactory<Hero,Integer>( "location" ));
    }
}