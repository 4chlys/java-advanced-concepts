package be.kdg.racebikeproject.view;

import be.kdg.racebikeproject.model.RaceBike;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

/**
 * View component for displaying and managing RaceBikes in MVP pattern.
 * Extends BorderPane to provide a structured layout.
 */
public class RaceBikesView extends BorderPane {
    private TableView<RaceBike> tableView;
    private TableColumn<RaceBike, String> modelColumn;
    private TableColumn<RaceBike, LocalDate> releaseDateColumn;
    private TableColumn<RaceBike, Double> priceColumn;

    private TextField modelTextField;
    private DatePicker releaseDatePicker;
    private TextField priceTextField;

    private Button addButton;
    private Button deleteButton;
    private Button updateButton;

    private Label statusLabel;

    /**
     * Constructor that initializes the view components and layout
     */
    public RaceBikesView() {
        initialiseNodes();
        layoutNodes();
    }

    /**
     * Initialize all UI components
     */
    private void initialiseNodes() {
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelColumn.setMinWidth(150);

        releaseDateColumn = new TableColumn<>("Release Date");
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        releaseDateColumn.setMinWidth(120);

        priceColumn = new TableColumn<>("Price (EUR)");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setMinWidth(100);

        tableView.getColumns().addAll(modelColumn, releaseDateColumn, priceColumn);

        modelTextField = new TextField();
        modelTextField.setPromptText("Model name");
        modelTextField.setPrefWidth(200);

        releaseDatePicker = new DatePicker();
        releaseDatePicker.setPromptText("Release date");
        releaseDatePicker.setPrefWidth(150);

        priceTextField = new TextField();
        priceTextField.setPromptText("Price");
        priceTextField.setPrefWidth(100);

        addButton = new Button("Add");
        addButton.setPrefWidth(80);

        deleteButton = new Button("Delete");
        deleteButton.setPrefWidth(80);
        deleteButton.setDisable(true);

        updateButton = new Button("Update");
        updateButton.setPrefWidth(80);
        updateButton.setDisable(true);

        statusLabel = new Label("Ready");
        statusLabel.setStyle("-fx-text-fill: gray;");
    }

    /**
     * Layout all components in the BorderPane
     */
    private void layoutNodes() {
        Label titleLabel = new Label("Race Bikes Management");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        VBox topBox = new VBox(10, titleLabel);
        topBox.setPadding(new Insets(10));
        topBox.setAlignment(Pos.CENTER);
        setTop(topBox);

        VBox centerBox = new VBox(10, tableView);
        centerBox.setPadding(new Insets(10));
        setCenter(centerBox);

        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER_LEFT);
        inputBox.getChildren().addAll(
                new Label("Model:"), modelTextField,
                new Label("Release Date:"), releaseDatePicker,
                new Label("Price:"), priceTextField
        );

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.getChildren().addAll(addButton, updateButton, deleteButton);

        VBox bottomBox = new VBox(10, inputBox, buttonBox, statusLabel);
        bottomBox.setPadding(new Insets(10));
        setBottom(bottomBox);

        setPadding(new Insets(10));
    }

    TableView<RaceBike> getTableView() {
        return tableView;
    }

    TextField getModelTextField() {
        return modelTextField;
    }

    DatePicker getReleaseDatePicker() {
        return releaseDatePicker;
    }

    TextField getPriceTextField() {
        return priceTextField;
    }

    Button getAddButton() {
        return addButton;
    }

    Button getDeleteButton() {
        return deleteButton;
    }

    Button getUpdateButton() {
        return updateButton;
    }

    Label getStatusLabel() {
        return statusLabel;
    }

    /**
     * Clear all input fields
     */
    void clearInputFields() {
        modelTextField.clear();
        releaseDatePicker.setValue(null);
        priceTextField.clear();
    }

    /**
     * Update status label with a message
     * @param message The status message to display
     */
    void setStatusMessage(String message) {
        statusLabel.setText(message);
    }

    /**
     * Set status message with color
     * @param message The status message
     * @param isError Whether this is an error message
     */
    void setStatusMessage(String message, boolean isError) {
        statusLabel.setText(message);
        if (isError) {
            statusLabel.setStyle("-fx-text-fill: red;");
        } else {
            statusLabel.setStyle("-fx-text-fill: green;");
        }
    }
}