package WillHero;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Setting {

    public void start(Stage stage) throws IOException {

        URL toScene = getClass().getResource("Setting.fxml");
        StaticFunction.setScene(stage, toScene, "Settings");
    }


}
