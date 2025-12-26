package be.kdg.racebikeproject.view;

import be.kdg.racebikeproject.exceptions.RaceBikeException;
import be.kdg.racebikeproject.model.*;
import be.kdg.racebikeproject.service.RaceBikesService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Presenter component in MVP pattern.
 * Handles the logic between View and Model/Service.
 */
public class RaceBikesPresenter {

    private final RaceBikesView view;
    private final RaceBikesService service;
    private final ObservableList<RaceBike> raceBikesList;

    /**
     * Constructor that connects the view with the service
     *
     * @param view    The view component
     * @param service The service layer
     */
    public RaceBikesPresenter(RaceBikesView view, RaceBikesService service) {
        this.view = view;
        this.service = service;

        this.raceBikesList = FXCollections.observableArrayList();

        view.getTableView().setItems(raceBikesList);

        addEventHandlers();

        loadRaceBikes();
    }

    /**
     * Add event handlers to view components
     */
    private void addEventHandlers() {
        view.getAddButton().setOnAction(event -> handleAdd());

        view.getUpdateButton().setOnAction(event -> handleUpdate());

        view.getDeleteButton().setOnAction(event -> handleDelete());

        view.getTableView().getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> handleSelection(newValue)
        );
    }

    /**
     * Load all race bikes from the service
     */
    private void loadRaceBikes() {
        try {
            raceBikesList.clear();

            raceBikesList.addAll(service.getAllRaceBikes());

            view.setStatusMessage("Loaded " + raceBikesList.size() + " race bikes", false);
        } catch (RaceBikeException e) {
            showError("Error loading race bikes", e.getMessage());
        }
    }

    /**
     * Handle adding a new race bike
     */
    private void handleAdd() {
        try {
            if (!validateInput()) return;

            RaceBike newBike = new RaceBike(
                    view.getBrandComboBox().getValue(),
                    view.getModelTextField().getText().trim(),
                    view.getColorComboBox().getValue(),
                    0.0,
                    0,
                    view.getReleaseDatePicker().getValue(),
                    new BigDecimal(view.getPriceTextField().getText().trim()),
                    Currency.EUR,
                    null, null, null, null, null
            );

            if (service.addRaceBike(newBike)) {
                raceBikesList.add(newBike);
                view.clearInputFields();
                view.setStatusMessage("Successfully added: " + newBike.getModel(), false);
            } else {
                showError("Error adding race bike", "Could not add bike to database");
            }

        } catch (RaceBikeException e) {
            showError("Error adding race bike", e.getMessage());
        } catch (NumberFormatException e) {
            showError("Invalid Input", "Price must be a valid number");
        }
    }


    /**
     * Handle updating an existing race bike
     */
    private void handleUpdate() {
        RaceBike selectedBike = view.getTableView().getSelectionModel().getSelectedItem();

        if (selectedBike == null) {
            showWarning("No Selection", "Please select a race bike to update");
            return;
        }

        try {
            if (!validateInput()) return;

            selectedBike.setBrand(view.getBrandComboBox().getValue());
            selectedBike.setModel(view.getModelTextField().getText().trim());
            selectedBike.setColor(view.getColorComboBox().getValue());
            selectedBike.setReleaseDate(view.getReleaseDatePicker().getValue());
            selectedBike.setPrice(new BigDecimal(view.getPriceTextField().getText().trim()));

            if (service.updateRaceBike(selectedBike)) {
                view.getTableView().refresh();
                view.setStatusMessage("Successfully updated: " + selectedBike.getModel(), false);
            } else {
                showError("Error updating race bike", "Could not update bike in database");
            }

        } catch (RaceBikeException e) {
            showError("Error updating race bike", e.getMessage());
        } catch (NumberFormatException e) {
            showError("Invalid Input", "Price must be a valid number");
        }
    }


    /**
     * Handle deleting a race bike
     */
    private void handleDelete() {
        RaceBike selectedBike = view.getTableView().getSelectionModel().getSelectedItem();

        if (selectedBike == null) {
            showWarning("No Selection", "Please select a race bike to delete");
            return;
        }

        Optional<ButtonType> result = showConfirmation(
                "Confirm Deletion",
                "Are you sure you want to delete: " + selectedBike.getModel() + "?"
        );

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (service.deleteRaceBike(selectedBike)) {
                    raceBikesList.remove(selectedBike);
                    view.clearInputFields();
                    view.setStatusMessage("Successfully deleted: " + selectedBike.getModel(), false);
                } else {
                    showError("Error deleting race bike", "Could not delete bike from database");
                }

            } catch (Exception e) {
                showError("Error deleting race bike", e.getMessage());
            }
        }
    }

    /**
     * Handle table selection changes
     */
    private void handleSelection(RaceBike selectedBike) {
        boolean hasSelection = selectedBike != null;

        view.getUpdateButton().setDisable(!hasSelection);
        view.getDeleteButton().setDisable(!hasSelection);

        if (hasSelection) {
            view.getBrandComboBox().setValue(selectedBike.getBrand());
            view.getModelTextField().setText(selectedBike.getModel());
            view.getColorComboBox().setValue(selectedBike.getColor());
            view.getReleaseDatePicker().setValue(selectedBike.getReleaseDate());
            view.getPriceTextField().setText(
                    selectedBike.getPrice() != null ? selectedBike.getPrice().toString() : ""
            );
            view.getCurrencyComboBox().setValue(selectedBike.getCurrency());
            view.getFrameMaterialComboBox().setValue(selectedBike.getFrameMaterial());
            view.getBrakeTypeComboBox().setValue(selectedBike.getBrakeType());
        }
    }

    /**
     * Validate input fields
     *
     * @return true if valid, false otherwise
     */
    private boolean validateInput() {
        String model = view.getModelTextField().getText();
        LocalDate releaseDate = view.getReleaseDatePicker().getValue();
        String priceText = view.getPriceTextField().getText();

        if (view.getBrandComboBox().getValue() == null) {
            showWarning("Invalid Input", "Please select a brand");
            return false;
        }

        if (model == null || model.trim().isEmpty()) {
            showWarning("Invalid Input", "Model cannot be empty");
            return false;
        }

        if (view.getColorComboBox().getValue() == null) {
            showWarning("Invalid Input", "Please select a color");
            return false;
        }

        if (releaseDate == null) {
            showWarning("Invalid Input", "Please select a release date");
            return false;
        }

        if (priceText == null || priceText.trim().isEmpty()) {
            showWarning("Invalid Input", "Price cannot be empty");
            return false;
        }

        try {
            BigDecimal price = new BigDecimal(priceText.trim());
            if (price.compareTo(BigDecimal.ZERO) < 0) {
                showWarning("Invalid Input", "Price cannot be negative");
                return false;
            }
        } catch (NumberFormatException e) {
            showWarning("Invalid Input", "Price must be a valid number");
            return false;
        }

        return true;
    }

    /**
     * Show error alert
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

        view.setStatusMessage("Error: " + message, true);
    }

    /**
     * Show warning alert
     */
    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Show confirmation dialog
     */
    private Optional<ButtonType> showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }
}