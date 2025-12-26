package be.kdg.racebikeproject.view;

import be.kdg.racebikeproject.model.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;


import java.time.LocalDate;

/**
 * View component for displaying and managing RaceBikes in MVP pattern.
 * Extends BorderPane to provide a structured layout.
 */
public class RaceBikesView extends BorderPane {
    private TableView<RaceBike> tableView;

    private TableColumn<RaceBike, Brand> brandColumn;
    private TableColumn<RaceBike, String> modelColumn;
    private TableColumn<RaceBike, Color> colorColumn;
    private TableColumn<RaceBike, LocalDate> releaseDateColumn;
    private TableColumn<RaceBike, Number> priceColumn;
    private TableColumn<RaceBike, Currency> currencyTableColumn;
    private TableColumn<RaceBike, FrameMaterial> frameMaterialColumn;
    private TableColumn<RaceBike, BrakeType> brakeTypeColumn;


    private TextField modelTextField;
    private DatePicker releaseDatePicker;
    private TextField priceTextField;
    private ComboBox<Brand> brandComboBox;
    private ComboBox<Color> colorComboBox;
    private ComboBox<Currency> currencyComboBox;
    private ComboBox<FrameMaterial> frameMaterialComboBox;
    private ComboBox<BrakeType> brakeTypeComboBox;


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

        brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        colorColumn = new TableColumn<>("Color");
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));

        releaseDateColumn = new TableColumn<>("Release Date");
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));

        priceColumn = new TableColumn<>("Price (€)");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        currencyTableColumn = new TableColumn<>("Currency");
        currencyTableColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));

        frameMaterialColumn = new TableColumn<>("Frame Material");
        frameMaterialColumn.setCellValueFactory(new PropertyValueFactory<>("frameMaterial"));

        brakeTypeColumn = new TableColumn<>("Brakes");
        brakeTypeColumn.setCellValueFactory(new PropertyValueFactory<>("brakeType"));

        tableView.getColumns().addAll(
                brandColumn,
                modelColumn,
                colorColumn,
                releaseDateColumn,
                priceColumn,
                currencyTableColumn,
                frameMaterialColumn,
                brakeTypeColumn
        );


        brandComboBox = new ComboBox<>();
        brandComboBox.setItems(FXCollections.observableArrayList(Brand.values()));
        brandComboBox.setPrefWidth(150);
        brandComboBox.setPromptText("Brand");

        modelTextField = new TextField();
        modelTextField.setPromptText("Model name");
        modelTextField.setPrefWidth(200);

        colorComboBox = new ComboBox<>();
        colorComboBox.setItems(FXCollections.observableArrayList(Color.values()));
        colorComboBox.setPrefWidth(150);
        colorComboBox.setPromptText("Color");

        releaseDatePicker = new DatePicker();
        releaseDatePicker.setPromptText("Release date");
        releaseDatePicker.setPrefWidth(150);

        priceTextField = new TextField();
        priceTextField.setPromptText("Price");
        priceTextField.setPrefWidth(100);

        currencyComboBox = new ComboBox<>();
        currencyComboBox.setItems(FXCollections.observableArrayList(Currency.values()));
        currencyComboBox.setPrefWidth(75);

        frameMaterialComboBox = new ComboBox<>();
        frameMaterialComboBox.setItems(FXCollections.observableArrayList(FrameMaterial.values()));
        frameMaterialComboBox.setPrefWidth(150);

        brakeTypeComboBox = new ComboBox<>();
        brakeTypeComboBox.setItems(FXCollections.observableArrayList(BrakeType.values()));
        brakeTypeComboBox.setPrefWidth(150);

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

        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(5);
        formGrid.setPadding(new Insets(10));

        formGrid.add(new Label("Brand"), 0, 0);
        formGrid.add(brandComboBox, 0, 1);

        formGrid.add(new Label("Model"), 1, 0);
        formGrid.add(modelTextField, 1, 1);

        formGrid.add(new Label("Color"), 2, 0);
        formGrid.add(colorComboBox, 2, 1);

        formGrid.add(new Label("Release Date"), 3, 0);
        formGrid.add(releaseDatePicker, 3, 1);

        formGrid.add(new Label("Price"), 4, 0);
        formGrid.add(priceTextField, 4, 1);

        formGrid.add(new Label("Currency"), 5, 0);
        formGrid.add(currencyComboBox, 5, 1);

        formGrid.add(new Label("Frame Material"), 6, 0);
        formGrid.add(frameMaterialComboBox, 6, 1);

        formGrid.add(new Label("Brakes"), 7, 0);
        formGrid.add(brakeTypeComboBox, 7, 1);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.getChildren().addAll(addButton, updateButton, deleteButton);

        VBox bottomBox = new VBox(15, formGrid, buttonBox, statusLabel);
        bottomBox.setPadding(new Insets(10));

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

    ComboBox<Brand> getBrandComboBox() {
        return brandComboBox;
    }

    ComboBox<Color> getColorComboBox() {
        return colorComboBox;
    }

    ComboBox<Currency> getCurrencyComboBox() {
        return currencyComboBox;
    }

    ComboBox<FrameMaterial> getFrameMaterialComboBox() {
        return frameMaterialComboBox;
    }

    ComboBox<BrakeType> getBrakeTypeComboBox() {
        return brakeTypeComboBox;
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
        brandComboBox.getSelectionModel().clearSelection();
        colorComboBox.getSelectionModel().clearSelection();
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