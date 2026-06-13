package controller;

import Model.Country;
import Model.Player;
import Service.ICountryService;
import Service.IPlayerService;
import Util.AppContext;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import Util.Session;
import Util.XmlLogger;

public class PlayerController {

    private final IPlayerService playerService = AppContext.getPlayerService();

    private final ICountryService countryService = AppContext.getCountryService();

    @FXML private TableView<Player> playerTable;

    @FXML private TableColumn<Player, Long> colId;
    @FXML private TableColumn<Player, String> colFirstName;
    @FXML private TableColumn<Player, String> colLastName;
    @FXML private TableColumn<Player, Integer> colRanking;
    @FXML private TableColumn<Player, String> colCountry;
    @FXML private ImageView playerImageView;

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField rankingField;
    @FXML private TextField imagePathField;

    @FXML private ComboBox<Country> countryCombo;

    @FXML
    public void initialize() {

        setupTable();
        loadCountries();
        loadPlayers();

        playerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {if (newVal != null) {fillForm(newVal);}});
    }

    private void setupTable() {

        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getId()));

        colFirstName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getFirstName()));

        colLastName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLastName()));

        colRanking.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getRanking()));

        colCountry.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCountry() != null ? data.getValue().getCountry().getName() : ""));
    }

    private void loadPlayers() {
        playerTable.setItems(FXCollections.observableArrayList(playerService.findAll()));
    }

    private void loadCountries() {
        countryCombo.setItems(FXCollections.observableArrayList(countryService.findAll()));
    }

    @FXML
    public void addPlayer() {

        if (!validate()) return;

        Player p = new Player(null, firstNameField.getText(), lastNameField.getText(), Integer.parseInt(rankingField.getText()), imagePathField.getText());

        p.setCountry(countryCombo.getValue());

        playerService.save(p);
        XmlLogger.log(Session.getCurrentUser().getUsername(), "ADD PLAYER: " + p.getFullName());
        refresh();
        clearForm();
    }

    @FXML
    public void updatePlayer() {

        Player selected = playerTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        if (!validate()) return;

        selected.setFirstName(firstNameField.getText());
        selected.setLastName(lastNameField.getText());
        selected.setRanking(Integer.parseInt(rankingField.getText()));
        selected.setImagePath(imagePathField.getText());
        selected.setCountry(countryCombo.getValue());

        playerService.update(selected);
        XmlLogger.log(Session.getCurrentUser().getUsername(), "UPDATE PLAYER: " + selected.getFullName());
        refresh();
    }

    @FXML
    public void deletePlayer() {

        Player selected = playerTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        XmlLogger.log(Session.getCurrentUser().getUsername(), "DELETE PLAYER: " + selected.getFullName());
        playerService.delete(selected.getId());

        refresh();
        clearForm();
    }

    private void fillForm(Player p) {

        firstNameField.setText(p.getFirstName());
        lastNameField.setText(p.getLastName());
        rankingField.setText(String.valueOf(p.getRanking()));
        imagePathField.setText(p.getImagePath());

        countryCombo.setValue(p.getCountry());
        loadPlayerImage(p.getImagePath());
    }

    private void loadPlayerImage(String path) {

        if (path == null || path.isBlank()) {
            playerImageView.setImage(null);
            return;
        }

        File file = new File(path);

        if (file.exists()) {
            playerImageView.setImage(new Image(file.toURI().toString()));
        } else {
            playerImageView.setImage(null);
        }
    }

    @FXML
    public void clearForm() {

        firstNameField.clear();
        lastNameField.clear();
        rankingField.clear();
        imagePathField.clear();

        countryCombo.getSelectionModel().clearSelection();
        playerTable.getSelectionModel().clearSelection();
    }

    private boolean validate() {

        if (firstNameField.getText().isEmpty()) return false;
        if (lastNameField.getText().isEmpty()) return false;
        if (rankingField.getText().isEmpty()) return false;
        if (countryCombo.getValue() == null) return false;

        try {
            Integer.parseInt(rankingField.getText());
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private void refresh() {
        loadPlayers();
    }

}