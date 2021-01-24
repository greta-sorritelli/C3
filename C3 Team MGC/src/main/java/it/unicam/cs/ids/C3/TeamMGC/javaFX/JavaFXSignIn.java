package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.personale.GestorePersonale;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

import java.sql.SQLException;


public class JavaFXSignIn implements JavaFXController {

    private final GestoreClienti gestoreClienti = GestoreClienti.getInstance();
    private final GestoreClienti gestoreCorrieri = GestoreClienti.getInstance();

    /**
     * TabPane della finestra
     */
    @FXML
    TabPane tab = new TabPane();
    @FXML
    Tab signIn = new Tab();
    @FXML
    Tab cliente = new Tab();
    @FXML
    Tab corriere = new Tab();
    @FXML
    Tab commesso = new Tab();
    @FXML
    Tab addetto = new Tab();
    @FXML
    Tab commerciante = new Tab();
    @FXML
    Tab magazziniere = new Tab();

    /**
     * TextFields cliente
     */
    @FXML
    TextField nomeCliente;
    @FXML
    TextField cognomeCliente;
    @FXML
    TextField passwordCliente;

    /**
     * TextFields corriere
     */
    @FXML
    TextField nomeCorriere;
    @FXML
    TextField cognomeCorriere;
    @FXML
    TextField passwordCorriere;

    /**
     * TextFields commesso
     */
    @FXML
    TextField IDNegozioCommesso;
    @FXML
    TextField nomeCommesso;
    @FXML
    TextField cognomeCommesso;
    @FXML
    TextField passwordCommesso;

    /**
     * TextFields addetto
     */
    @FXML
    TextField IDNegozioAddetto;
    @FXML
    TextField nomeAddetto;
    @FXML
    TextField cognomeAddetto;
    @FXML
    TextField passwordAddetto;

    /**
     * TextFields commerciante
     */
    @FXML
    TextField IDNegozioCommerciante;
    @FXML
    TextField nomeCommerciante;
    @FXML
    TextField cognomeCommerciante;
    @FXML
    TextField passwordCommerciante;

    /**
     * TextFields magazziniere
     */
    @FXML
    TextField IDPPMagazziniere;
    @FXML
    TextField nomeMagazziniere;
    @FXML
    TextField cognomeMagazziniere;
    @FXML
    TextField passwordMagazziniere;

    @FXML
    ChoiceBox<String> utentiChoiceBox = new ChoiceBox<>();

    @FXML
    public void backToSignIn() {
        cliente.setDisable(true);
        corriere.setDisable(true);
        commesso.setDisable(true);
        addetto.setDisable(true);
        commerciante.setDisable(true);
        magazziniere.setDisable(true);
        signIn.setDisable(false);
        tab.getSelectionModel().select(signIn);
    }

    @FXML
    public void deleteTextFieldAddetto() {
        IDNegozioAddetto.clear();
        nomeAddetto.clear();
        cognomeAddetto.clear();
        passwordAddetto.clear();
    }

    @FXML
    public void deleteTextFieldCliente() {
        nomeCliente.clear();
        cognomeCliente.clear();
        passwordCliente.clear();
    }

    @FXML
    public void deleteTextFieldCommerciante() {
        IDNegozioCommerciante.clear();
        nomeCommerciante.clear();
        cognomeCommerciante.clear();
        passwordCommerciante.clear();
    }

    @FXML
    public void deleteTextFieldCommesso() {
        IDNegozioCommesso.clear();
        nomeCommesso.clear();
        cognomeCommesso.clear();
        passwordCommesso.clear();
    }

    @FXML
    public void deleteTextFieldCorriere() {
        nomeCorriere.clear();
        cognomeCorriere.clear();
        passwordCorriere.clear();
    }

    @FXML
    public void deleteTextFieldMagazziniere() {
        IDPPMagazziniere.clear();
        nomeMagazziniere.clear();
        cognomeMagazziniere.clear();
        passwordMagazziniere.clear();
    }

    @FXML
    public void goToUtente() {
        if (!utentiChoiceBox.getValue().isEmpty())
            switch (utentiChoiceBox.getValue()) {
                case "CLIENTE":
                    tab.getSelectionModel().select(cliente);
                    cliente.setDisable(false);
                    signIn.setDisable(true);
                    break;
                case "CORRIERE":
                    tab.getSelectionModel().select(corriere);
                    corriere.setDisable(false);
                    signIn.setDisable(true);
                    break;
                case "COMMESSO":
                    tab.getSelectionModel().select(commesso);
                    commesso.setDisable(false);
                    signIn.setDisable(true);
                    break;
                case "ADDETTO":
                    tab.getSelectionModel().select(addetto);
                    addetto.setDisable(false);
                    signIn.setDisable(true);
                    break;
                case "COMMERCIANTE":
                    tab.getSelectionModel().select(commerciante);
                    commerciante.setDisable(false);
                    signIn.setDisable(true);
                    break;
                case "MAGAZZINIERE":
                    tab.getSelectionModel().select(magazziniere);
                    magazziniere.setDisable(false);
                    signIn.setDisable(true);
                    break;
            }
    }

    @FXML
    public void showUtenti() {
        utentiChoiceBox.getItems().clear();
        utentiChoiceBox.getItems().add("CLIENTE");
        utentiChoiceBox.getItems().add("CORRIERE");
        utentiChoiceBox.getItems().add("COMMESSO");
        utentiChoiceBox.getItems().add("ADDETTO");
        utentiChoiceBox.getItems().add("COMMERCIANTE");
        utentiChoiceBox.getItems().add("MAGAZZINIERE");
    }

    @FXML
    public void signInCliente() {
        try {
            gestoreClienti.inserisciDati(nomeCliente.getText(), cognomeCliente.getText());
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    @FXML
    public void signInCorriere() {
        try {
            gestoreCorrieri.inserisciDati(nomeCorriere.getText(), cognomeCorriere.getText());
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }


}
