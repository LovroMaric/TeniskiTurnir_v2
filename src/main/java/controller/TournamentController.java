package controller;

import Model.*;
import Service.IPlayerService;
import Service.ITournamentService;
import Service.xml.TournamentXmlService;
import Service.ICategoryService;
import Util.AppContext;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import Util.Session;
import Util.XmlLogger;

import java.math.BigDecimal;

public class TournamentController {

    @FXML private TableView<Player> playerTable;
    @FXML private TableView<Tournament> tournamentTable;

    @FXML private TableColumn<Tournament, Long> idCol;
    @FXML private TableColumn<Tournament, String> nameCol;
    @FXML private TableColumn<Tournament, Integer> yearCol;
    @FXML private TableColumn<Tournament, BigDecimal> prizeCol;
    @FXML private TableColumn<Tournament, SurfaceType> surfaceCol;
    @FXML private TableColumn<Tournament, String> countryCol;
    @FXML private TableColumn<Tournament, String> categoryCol;

    @FXML private TableColumn<Player, Long> playerIdCol;
    @FXML private TableColumn<Player, String> playerNameCol;
    @FXML private TableColumn<Player, Integer> rankingCol;

    @FXML private TextField nameField;
    @FXML private TextField yearField;
    @FXML private TextField prizeField;
    @FXML private ComboBox<SurfaceType> surfaceBox;
    @FXML private ComboBox<Country> countryBox;
    @FXML private ComboBox<Category> categoryBox;
    @FXML private ImageView tournamentImageView;

    @FXML
    private TextField imagePathField;

    private final ITournamentService tournamentService = AppContext.getTournamentService();

    private final IPlayerService playerService = AppContext.getPlayerService();

    private final TournamentXmlService xmlService = new TournamentXmlService();

    private final ICategoryService categoryService = AppContext.getCategoryService();

    private Player draggedPlayer;

    private static final String TABLE_HIGHLIGHT = "-fx-background-color: #c8e6c9;";

    private static final String ROW_HIGHLIGHT = "-fx-background-color: #a5d6a7;";

    @FXML
    public void add() {

        try {

            Tournament t = new Tournament(null, nameField.getText(), Integer.parseInt(yearField.getText()), new BigDecimal(prizeField.getText()), surfaceBox.getValue(), imagePathField.getText(), countryBox.getValue(), categoryBox.getValue(), null, null);

            tournamentService.add(t);

            XmlLogger.log(Session.getCurrentUser().getUsername(), "ADD TOURNAMENT: " + t.getName());

            loadTournaments();

            showInfo("Tournament added.");

        } catch (Exception e) {
            showError("Error adding tournament.");
        }
    }

    @FXML
    public void update() {

        Tournament selected = tournamentTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showError("Select tournament first.");
            return;
        }

        try {

            selected.setName(nameField.getText());

            selected.setFoundedYear(Integer.parseInt(yearField.getText()));

            selected.setPrizeMoney(new BigDecimal(prizeField.getText()));

            selected.setSurface(surfaceBox.getValue());

            selected.setImagePath(imagePathField.getText());

            selected.setCountry(countryBox.getValue());

            selected.setCategory(categoryBox.getValue());

            tournamentService.update(selected);
            XmlLogger.log(Session.getCurrentUser().getUsername(), "UPDATE TOURNAMENT: " + selected.getName());

            loadTournaments();

            showInfo("Tournament updated.");

        } catch (Exception e) {
            showError("Error updating tournament.");
        }
    }

    @FXML
    public void delete() {

        Tournament selected = tournamentTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showError("Select tournament first.");
            return;
        }

        XmlLogger.log(Session.getCurrentUser().getUsername(), "DELETE TOURNAMENT: " + selected.getName());
        tournamentService.delete(selected.getId());

        loadTournaments();

        showInfo("Tournament deleted.");
    }

    @FXML
    public void initialize() {

        initializeTournamentColumns();
        initializePlayerColumns();

        loadPlayers();
        loadTournaments();

        surfaceBox.setItems(FXCollections.observableArrayList(SurfaceType.values()));
        countryBox.setItems(FXCollections.observableArrayList(AppContext.getCountryService().findAll()));
        categoryBox.setItems(FXCollections.observableArrayList(categoryService.findAll()));

        initDragAndDrop();
    }

    @FXML
    public void exportXml() {

        Tournament selected = tournamentTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showError("Select a tournament first.");
            return;
        }

        xmlService.export(selected, "tournament.xml");

        showInfo("XML exported successfully!");
    }

    private void initializeTournamentColumns() {

        idCol.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getId()));

        nameCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getName()));

        yearCol.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getFoundedYear()));

        prizeCol.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getPrizeMoney()));

        surfaceCol.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getSurface()));

        countryCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getCountry() != null ? d.getValue().getCountry().getName() : ""));

        categoryCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getCategory() != null ? d.getValue().getCategory().getName() : ""));
    }

    private void initializePlayerColumns() {

        playerIdCol.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getId()));

        playerNameCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getFullName()));

        rankingCol.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getRanking()));
    }

    private void loadPlayers() {
        playerTable.setItems(FXCollections.observableArrayList(playerService.findAll()));
    }

    private void loadTournaments() {
        tournamentTable.setItems(FXCollections.observableArrayList(tournamentService.findAll()));
    }

    private void initDragAndDrop() {

        playerTable.setOnDragDetected(event -> {

            draggedPlayer = playerTable.getSelectionModel().getSelectedItem();
            if (draggedPlayer == null) return;

            Dragboard db = playerTable.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putString(String.valueOf(draggedPlayer.getId()));

            db.setContent(content);

            playerTable.setCursor(Cursor.CLOSED_HAND);

            event.consume();
        });

        tournamentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {

            if (selected != null) {
                nameField.setText(selected.getName());
                yearField.setText(String.valueOf(selected.getFoundedYear()));
                prizeField.setText(selected.getPrizeMoney().toString());
                surfaceBox.setValue(selected.getSurface());
                imagePathField.setText(selected.getImagePath());

                loadTournamentImage(selected.getImagePath());

                countryBox.setValue(selected.getCountry());
                categoryBox.setValue(selected.getCategory());
            }
        });

        playerTable.setOnDragDone(e -> playerTable.setCursor(Cursor.DEFAULT));

        tournamentTable.setOnDragOver(event -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
                tournamentTable.setStyle(TABLE_HIGHLIGHT);
            }
            event.consume();
        });

        tournamentTable.setOnDragExited(event -> tournamentTable.setStyle(""));

        tournamentTable.setRowFactory(tv -> {

            TableRow<Tournament> row = new TableRow<>();

            row.setOnDragOver(event -> {
                if (event.getDragboard().hasString()) {
                    row.setStyle(ROW_HIGHLIGHT);
                }
            });

            row.setOnDragExited(e -> row.setStyle(""));

            return row;
        });

        tournamentTable.setOnDragDropped(event -> {

            Dragboard db = event.getDragboard();
            boolean success = false;

            Tournament selected = tournamentTable.getSelectionModel().getSelectedItem();

            if (db.hasString() && selected != null) {

                try {
                    Long playerId = Long.parseLong(db.getString());

                    tournamentService.addPlayerToTournament(selected.getId(), playerId, "N/A");

                    XmlLogger.log(Session.getCurrentUser().getUsername(), "ADD PLAYER TO TOURNAMENT: player " + playerId + " -> " + selected.getName());

                    success = true;
                    showInfo("Player added to tournament.");

                } catch (Exception e) {
                    showError("Error adding player.");
                }
            }

            event.setDropCompleted(success);
            event.consume();
        });
    }

    private void loadTournamentImage(String path) {

        if (path == null || path.isBlank()) {
            tournamentImageView.setImage(null);
            return;
        }

        try {

            File file = new File(path);

            System.out.println("Tournament image path: "
                    + file.getAbsolutePath());

            if (!file.exists()) {

                System.out.println("Image NOT FOUND!");

                tournamentImageView.setImage(null);

                return;
            }

            Image image = new Image(
                    file.toURI().toString(),
                    false
            );

            System.out.println("Image error: " + image.isError());
            System.out.println("Image width: " + image.getWidth());
            System.out.println("Image height: " + image.getHeight());

            tournamentImageView.setImage(image);

        } catch (Exception e) {

            e.printStackTrace();

            tournamentImageView.setImage(null);
        }
    }

    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(msg);
        a.showAndWait();
    }
}