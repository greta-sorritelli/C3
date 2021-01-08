package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.view.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXC3Controller {

    /**
     * This method open a new window.
     *
     * @param a Fxml path
     * @param b Title of window.
     */
    @FXML
    public void openWindow(String a, String b, Object controller) {
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

    public void visualizzaHomeAddetto(){
        openWindow("/HomeAddettoMagazzino.fxml", "Home Addetto Magazzino", new IAddettoMagazzino());
    }

    public void visualizzaHomeCommesso(){
        openWindow("/HomeCommesso.fxml", "Home Commesso", new ICommesso());
    }

    //todo fxml
    public void visualizzaHomeMagazziniere(){
        openWindow("/HomeMagazziniere.fxml", "Home Magazziniere", new IMagazziniere());
    }

    //todo fxml
    public void visualizzaHomeCorriere(){
        openWindow("/HomeCorriere.fxml", "Home Corriere", new ICorriere());
    }

    //todo fxml
    public void visualizzaHomeCliente(){
        openWindow("/HomeCliente.fxml", "Home Cliente", new ICliente());
    }

    //todo fxml
    public void visualizzaHomeCommerciante(){
        openWindow("/HomeCommerciante.fxml", "Home Commerciante", new ICommerciante());
    }


}