package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;
import Util.Session;
import Util.XmlLogger;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        openPlayers();
    }

    @FXML
    public void openPlayers() {
        loadView("/fxml/players.fxml");
    }

    @FXML
    public void openTournaments() {
        loadView("/fxml/tournament.fxml");
    }

    private void loadView(String fxml) {

        try {
            Parent view = FXMLLoader.load(
                    getClass().getResource(fxml)
            );

            contentArea.getChildren().setAll(view);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void logout() {

        XmlLogger.log(Session.getCurrentUser().getUsername(), "LOGOUT");

        try {
            Parent view = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));

            contentArea.getScene().setRoot(view);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}