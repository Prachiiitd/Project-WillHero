package WillHero;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.ImageCursor;
import javafx.scene.Node;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.transform.Rotate;
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

    static Stage getStage(MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getSource();
        return (Stage) node.getScene().getWindow();
    }

    static void onHover(Node node, Point3D axis) {
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(node);
        rotate.setByAngle(360);
        rotate.setAxis(axis);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setDuration(Duration.millis(1000));
        rotate.play();
    }

    static void offHover(Node node) {
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(node);
        rotate.stop();
    }

    static void setScene(Stage stage, URL toFrame, String title) throws IOException {

        Image icon = new Image(new FileInputStream(Objects.requireNonNull(StaticFunction.class.getResource("mainIcon.png")).getPath()));
        stage.setTitle("WillHero: " + title);
        stage.getIcons().add(icon);
        Parent root =  FXMLLoader.load(toFrame);


        Scene scene = new Scene(root);

        ((AnchorPane) scene.getRoot()).setBackground(defaultBackground());

        stage.setScene(scene);
        stage.show();
    }

    static void setTranslation(Node node, float xMotion, float yMotion, int duration, int cycle, boolean reverse) {
        TranslateTransition trFloatingName = new TranslateTransition();
        trFloatingName.setNode(node);
        trFloatingName.setByY(xMotion);
        trFloatingName.setByY(yMotion);
        trFloatingName.setDuration(Duration.millis(duration));
        trFloatingName.setCycleCount(cycle);
        trFloatingName.setAutoReverse(reverse);
        trFloatingName.play();
    }

    static void setRotation (Node node, int angle, int duration, int cycle, boolean reverse) {RotateTransition trFloatingName6= new RotateTransition();
        RotateTransition trFloatingName= new RotateTransition();
        trFloatingName.setNode(node);
        trFloatingName.setAxis(Rotate.Z_AXIS);
        trFloatingName.setDuration(Duration.millis(duration));
        trFloatingName.setCycleCount(cycle);
        trFloatingName.setByAngle(angle);
        trFloatingName.setAutoReverse(reverse);
        trFloatingName.play();
    }

    static Background defaultBackground(){
        return new Background(new BackgroundFill(
                new LinearGradient(
                        0, 0, 0 , 1, true,                  //sizing
                        CycleMethod.NO_CYCLE,                 //cycling
                        new Stop(0, Color.web("#01D9F8FF")),    //colors
                        new Stop(1, Color.web("#C4F4FEFF"))
                ),  CornerRadii.EMPTY, Insets.EMPTY)
        );
    }

    static void bestReward(Label label) {
        int reward;
        reward = 80;
        label.setText(String.valueOf(reward));
    }

    static void bestLocation(Label label) {
        int location;
        location = 20;
        label.setText(String.valueOf(location));
    }

    static boolean bottomCollision(ImageView node1, ImageView node2, double margin) {
//        node1 ka bottom collides with node2 ka top
        return node1.getBoundsInParent().intersects(node2.getBoundsInParent()) &&
                node1.getBoundsInParent().getMaxY() > node2.getBoundsInParent().getMinY() &&
                node1.getBoundsInParent().getMinY() < node2.getBoundsInParent().getMinY() &&
                node1.getBoundsInParent().getMaxY() < node2.getBoundsInParent().getMaxY() &&
                node1.getBoundsInParent().getMaxX() > node2.getBoundsInParent().getMinX() &&
                node1.getBoundsInParent().getMinX() < node2.getBoundsInParent().getMaxX();
    }

    static boolean topCollision(ImageView node1, ImageView node2, double margin) {
//        node1 ka top collides with node2 ka bottom
        return node1.getBoundsInParent().intersects(node2.getBoundsInParent()) &&
                node1.getBoundsInParent().getMaxY() > node2.getBoundsInParent().getMinY() &&
                node1.getBoundsInParent().getMinY() < node2.getBoundsInParent().getMinY() &&
                node1.getBoundsInParent().getMaxY() < node2.getBoundsInParent().getMaxY() &&
                node1.getBoundsInParent().getMaxX() > node2.getBoundsInParent().getMinX() &&
                node1.getBoundsInParent().getMinX() < node2.getBoundsInParent().getMaxX();
    }

    static boolean leftCollision(Node node1, Node node2, double margin) {
//        node1 ka left collides with node2 ka right
        return node1.getBoundsInParent().intersects(node2.getBoundsInParent()) &&
                node1.getBoundsInParent().getMaxX() > node2.getBoundsInParent().getMinX() &&
                node1.getBoundsInParent().getMinX() < node2.getBoundsInParent().getMaxX() &&
                node1.getBoundsInParent().getMaxY() > node2.getBoundsInParent().getMinY() &&
                node1.getBoundsInParent().getMinY() < node2.getBoundsInParent().getMaxY();
    }

    static boolean rightCollision(Node node1, Node node2, double margin) {
//        node1 ka right collides with node2 ka left
        return node1.getBoundsInParent().intersects(node2.getBoundsInParent()) &&
                node1.getBoundsInParent().getMaxX() > node2.getBoundsInParent().getMinX() &&
                node1.getBoundsInParent().getMinX() < node2.getBoundsInParent().getMaxX() &&
                node1.getBoundsInParent().getMaxY() > node2.getBoundsInParent().getMinY() &&
                node1.getBoundsInParent().getMinY() < node2.getBoundsInParent().getMaxY();
    }

}