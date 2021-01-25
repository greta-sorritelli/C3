package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Classe principale per la GUI JavaFX. Estende {@link Application}.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class JavaFXC3 extends Application {

    /**
     * Apre la finestra principale.
     * @param stage stage
     * @throws Exception eccezione
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/HomeApp.fxml"));
        stage.setTitle("C3 Home");
        Image ico = new Image("/png/icon.png");
        stage.getIcons().add(ico);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
