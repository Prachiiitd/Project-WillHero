package WillHero;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class StaticFunction {

    static void clickResponse(ImageView image) {
        ScaleTransition scale = new ScaleTransition();
        scale.setNode(image);
        scale.setDuration(Duration.millis(200));
        scale.setCycleCount(2);
        scale.setAutoReverse(true);
        scale.setByY(0.2);
        scale.setByX(0.2);
        scale.play();
    }

    static Stage getStage(MouseEvent mouseEvent){
        Node node = (Node) mouseEvent.getSource();
        return (Stage) node.getScene().getWindow();
    }

    static void onHover(Node node, Point3D axis){
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(node);
        rotate.setByAngle(360);
        rotate.setAxis(axis);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setDuration(Duration.millis(1000));
        rotate.play();
    }

    static void offHover(Node node){
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(node);
        rotate.stop();
    }

    static void setScene(Stage stage, URL toFrame, String title) throws IOException {

        Image icon = new Image(new FileInputStream(Objects.requireNonNull(StaticFunction.class.getResource("mainIcon.png")).getPath()));
        stage.setTitle("WillHero: " + title);
        stage.getIcons().add(icon);

        Scene scene = new Scene(FXMLLoader.load(toFrame));
        stage.setScene(scene);
        stage.show();
    }



}
