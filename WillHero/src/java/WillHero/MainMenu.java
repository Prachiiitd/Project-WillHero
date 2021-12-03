package WillHero;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {

    @FXML
    private ImageView floatingName;
    @FXML
    private ImageView newGame;
    @FXML
    private ImageView resumeGame;
    @FXML
    private ImageView exit;
    @FXML
    private ImageView leaderboard;
    @FXML
    private ImageView setting;

    public void start(Stage stage) throws IOException {

//        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        Parent root = FXMLLoader.load(Objects.requireNonNull(WillHero.class.getResource("mainMenu.fxml")));
        Scene scene = new Scene(root);

        Image icon = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("mainIcon.png")).getPath()));
        stage.setTitle("Will Hero");
        stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            exit(stage);
        });
    }

    public void exit(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"You're about to Exit!", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Exit");
        alert.initStyle(StageStyle.UNDECORATED);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() &&  result.get() == ButtonType.YES){
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TranslateTransition trFloatingName = new TranslateTransition();
        trFloatingName.setNode(floatingName);
        trFloatingName.setByY(100);
        trFloatingName.setDuration(Duration.millis(1000));
        trFloatingName.setCycleCount(TranslateTransition.INDEFINITE);
        trFloatingName.setAutoReverse(true);
        trFloatingName.play();
    }

    private void onClickZoom(ImageView image){
        ScaleTransition scale = new ScaleTransition();
        scale.setNode(image);
        scale.setDuration(Duration.millis(200));
        scale.setCycleCount(2);
        scale.setAutoReverse(true);
        scale.setByY(0.2);
        scale.setByX(0.2);
        scale.play();
    }

    public void setNewGame(MouseEvent newGame) throws FileNotFoundException {
        onClickZoom(this.newGame);

        Image icon = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("mainIcon.png")).getPath()));
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(35);
        imageView.setPreserveRatio(true);

        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.initStyle(StageStyle.UNDECORATED);
        textInputDialog.setHeaderText(null);
        textInputDialog.setContentText("Enter Your Name");
        textInputDialog.setGraphic(imageView);

        Alert alert = new Alert(Alert.AlertType.WARNING,"Name Can not be blank", ButtonType.OK);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);

        Label label = new Label();

        Optional<String> result = textInputDialog.showAndWait();
        System.out.println((textInputDialog.getEditor().getText()));

        result.ifPresent(name -> {
            label.setText(name);

            if (label.getText().length() == 0) {
                alert.showAndWait();
            } else {
                System.out.println(label.toString());
                System.out.println("load game page with label");
            }
        });
    }


    public void setResumeGame(MouseEvent resumeGame) {
        onClickZoom(this.resumeGame);
    }

    public void setSetting(MouseEvent setting) {
        onClickZoom(this.setting);
    }

    public void setLeaderboard(MouseEvent leaderboard) {
        onClickZoom(this.leaderboard);
    }

    public void setExit(MouseEvent exit) {
        onClickZoom(this.exit);

        Node node = (Node) exit.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        exit(stage);
    }
}