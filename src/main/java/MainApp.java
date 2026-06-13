import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Model.AppConfig;
import Util.ConfigLoader;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));

            AppConfig config = ConfigLoader.getConfig();

            Scene scene = new Scene(loader.load(), config.getScreenWidth(), config.getScreenHeight());

            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}