//package WillHero;
//
//import javafx.animation.ScaleTransition;
//import javafx.animation.TranslateTransition;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontPosture;
//import javafx.scene.text.FontWeight;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Objects;
//import java.util.ResourceBundle;
//
//
//public class newGame implements Initializable {
//    //        private ArrayList<PLayer> players;
//    @FXML
//    private ImageView island1;
//    @FXML
//    private ImageView island2;
//    @FXML
//    private  ImageView back;
//    @FXML
//    private ImageView hero;
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        TranslateTransition trFloatingName = new TranslateTransition();
//        trFloatingName.setNode(island1);
//        trFloatingName.setByY(55);
//        trFloatingName.setDuration(Duration.millis(1000));
//        trFloatingName.setCycleCount(TranslateTransition.INDEFINITE);
//        trFloatingName.setAutoReverse(true);
//        trFloatingName.play();
//        TranslateTransition her= new TranslateTransition();
//        her.setNode(hero);
//        her.setByY(55);
//        her.setDuration(Duration.millis(1000));
//        her.setCycleCount(TranslateTransition.INDEFINITE);
//        her.setAutoReverse(true);
//        her.play();
//
//        TranslateTransition trFloatingName2 = new TranslateTransition();
//        trFloatingName2.setNode(island2);
//        trFloatingName2.setByY(-50);
//        trFloatingName2.setDuration(Duration.millis(1000));
//        trFloatingName2.setCycleCount(TranslateTransition.INDEFINITE);
//        trFloatingName2.setAutoReverse(true);
//        trFloatingName2.play();
//    }
//
//    public void shownewgame(MouseEvent newGame) throws IOException {
//        Node node = (Node) newGame.getSource();
//        Stage stage = (Stage) node.getScene().getWindow();
//        Image icon = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("mainIcon.png")).getPath()));
//        stage.setTitle("Will Hero:Leaderboard");
//        stage.getIcons().add(icon);
//        Parent root = FXMLLoader.load(Objects.requireNonNull(LeaderBoard.class.getResource("Arena.fxml")));
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//
//
//
//    }
//
//    public void setBack(MouseEvent back) throws IOException {
//        onClickZoom(this.back);
//        Node node = (Node) back.getSource();
//        Stage stage = (Stage) node.getScene().getWindow();
//
//        Image icon = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("mainIcon.png")).getPath()));
//        stage.setTitle("Will Hero:Leaderboard");
//        stage.getIcons().add(icon);
//
//        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//}
