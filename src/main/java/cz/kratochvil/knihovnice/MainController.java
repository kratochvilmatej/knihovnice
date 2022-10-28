package cz.kratochvil.knihovnice;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class MainController {
    @FXML
    private Label lblStat;

    @FXML
    private TextField txUser;

    @FXML
    private TextField txPass;

    public void Login(ActionEvent event) {
        if (txUser.getText().equals("user") && txPass.getText().equals("pass")) {
            lblStat.setTextFill(Color.rgb(0, 255, 0, 1));
            lblStat.setText("Příhlášení Úspešné");
        } else {
            lblStat.setTextFill(Color.rgb(255, 0, 0, 1));
            lblStat.setText("Nesprávné Uživatelské Jméno nebo Heslo");
        }

    }

}
