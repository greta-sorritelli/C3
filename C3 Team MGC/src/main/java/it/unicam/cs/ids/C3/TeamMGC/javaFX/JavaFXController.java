package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

/**
 * Questa interfaccia è implementata dalle classi che hanno la responsabilità di gestire il comportamento di
 * una finestra della GUI.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public interface JavaFXController {

    default void alertWindow(String warningHeader, String warningMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        showAlert(alert, warningHeader, warningMessage, 2);
    }

    /**
     * Metodo per la chiusura di una finestra
     *
     * @param stage stage da chiudere
     */
    default void closeWindow(Stage stage) {
        stage.close();
    }

    default void errorWindow(String errorHeader, String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        showAlert(alert, errorHeader, errorMessage, 2);
    }

    default void informationWindow(String successHeader, String successMessage, int sec) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info!");
        showAlert(alert, successHeader, successMessage, sec);
    }

    /**
     * Metodo per aprire una finestra
     *
     * @param a          path del file fxml
     * @param b          titolo della finestra
     * @param controller Controller della Finestra
     */
    default void openWindow(String a, String b, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(a));
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(b);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            Image icon = new Image("/png/icon.png");
            stage.getIcons().add(icon);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert alert, String header, String message, int sec) {
        alert.setHeaderText(header);
        alert.setContentText(message);
        PauseTransition delay = new PauseTransition(Duration.seconds(sec));
        delay.setOnFinished(e -> alert.close());
        alert.show();
        delay.play();
    }

    default void successWindow(String successHeader, String successMessage, int sec) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success!");
        showAlert(alert, successHeader, successMessage, sec);
    }
}
