package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public interface JavaFXController {

    default void alertWindow(String warningHeader, String warningMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        showAlert(alert, warningHeader, warningMessage);
    }

    /**
     * Metodo per la chiusura di una finestra
     * //todo controllare
     *
     * @param stage stage da chiudere
     */
    default void closeWindow(Stage stage) {
        stage.close();
    }

    default void errorWindow(String errorHeader, String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        showAlert(alert, errorHeader, errorMessage);
    }

    /**
     * Metodo per aprire una finestra
     *
     * @param a path del file fxml
     * @param b titolo della finestra
     */
    default void openWindow(String a, String b, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(a));
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(b);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
//            Image icon = new Image("/icon.png");
//            stage.getIcons().add(icon);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert alert, String header, String message) {
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    default void informationWindow(String successHeader, String successMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info!");
        showAlert(alert, successHeader, successMessage);
    }

    default void successWindow(String successHeader, String successMessage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success!");
        showAlert(alert, successHeader, successMessage);
    }

}
