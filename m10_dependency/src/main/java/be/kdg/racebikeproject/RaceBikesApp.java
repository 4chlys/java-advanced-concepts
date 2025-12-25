package be.kdg.racebikeproject;

import be.kdg.racebikeproject.database.RaceBikeDbDao;
import be.kdg.racebikeproject.service.RaceBikesService;
import be.kdg.racebikeproject.service.RaceBikesServiceImpl;
import be.kdg.racebikeproject.view.RaceBikesPresenter;
import be.kdg.racebikeproject.view.RaceBikesView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main JavaFX Application for RaceBikes Management
 * Demonstrates MVP (Model-View-Presenter) pattern
 */
public class RaceBikesApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        RaceBikesService service = new RaceBikesServiceImpl(RaceBikeDbDao.getInstance("racebikes.db"));

        RaceBikesView view = new RaceBikesView();

        RaceBikesPresenter presenter = new RaceBikesPresenter(view, service);

        Scene scene = new Scene(view, 800, 600);

        primaryStage.setTitle("Race Bikes Management System");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(500);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}