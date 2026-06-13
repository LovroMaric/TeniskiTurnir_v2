package controller;

import Model.User;
import Service.IUserService;
import Service.UserService;
import Util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Util.XmlLogger;

import java.util.Optional;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    private final IUserService userService = new UserService();

    @FXML
    public void login(ActionEvent event) {

        String username = usernameField.getText();
        String password = passwordField.getText();

        Optional<User> optionalUser = userService.login(username, password);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            Session.setCurrentUser(user);

            XmlLogger.log(user.getUsername(), "LOGIN");

            messageLabel.setText("Login successful!");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));

                Scene scene = new Scene(loader.load());

                Stage stage = new Stage();
                stage.setTitle("Main Window");
                stage.setScene(scene);
                stage.show();

                ((Stage) usernameField.getScene().getWindow()).close();

            } catch (Exception e) {
                e.printStackTrace();
                messageLabel.setText("Error opening main window!");
            }

        } else {
            messageLabel.setText("Invalid username or password!");
        }
    }


    @FXML
    private void loginSuccess() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) usernameField.getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Main App");
            stage.show();

        } catch (Exception e) {
            System.out.println("ERROR OPENING MAIN WINDOW:");
            e.printStackTrace();
        }
    }
}