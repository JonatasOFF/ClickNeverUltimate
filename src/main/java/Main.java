import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    public static void main(String[] args) {
       launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("fxml/firstPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setTitle("TESTANDO");
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(2);
    }
}
