package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Splash screen per l' anteprima dell' applicazione
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class SplashFXMLController implements Initializable, JavaFXController {

    @FXML
    private StackPane rootPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new SplashScreen().start();
    }


    /**
     * Apre la finestra principale.
     */
    private class SplashScreen extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/HomeApp.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (root != null) {
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle("C3 Home");
                            Image ico = new Image("/png/icon.png");
                            stage.getIcons().add(ico);
                            stage.setResizable(false);
                            stage.show();
                            closeWindow((Stage) rootPane.getScene().getWindow());
                        }
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}