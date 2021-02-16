package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Classe principale per la GUI JavaFX. Estende {@link Application}.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class JavaFXC3 extends Application {

    /**
     * Apre lo splash screen per l' anteprima.
     *
     * @param stage stage
     *
     * @throws Exception eccezione
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/SplashFXML.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        Image ico = new Image("/png/icon.png");
        stage.getIcons().add(ico);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}
