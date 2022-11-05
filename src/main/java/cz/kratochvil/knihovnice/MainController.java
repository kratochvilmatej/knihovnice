package cz.kratochvil.knihovnice;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

public class MainController {
    @FXML
    private Label lblStat;

    @FXML
    private Label lblLogged;

    @FXML
    private Label lblLoggedText;

    @FXML
    private Label lblKnihovnaMain;

    @FXML
    private Label lblSiteInfo;

    @FXML
    private Label lblKnihovnaLogin;

    @FXML
    private TextField txUser;

    @FXML
    private TextField txPass;

    @FXML
    private ImageView imgIkonaMain;

    @FXML
    private ImageView imgIkonaLogin;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnToReg;

    @FXML
    private Button btnReg;

    @FXML
    private Button btnBackLogin;

    @FXML
    private Button btnLogOut;

    public String usernames = "";

    public String passwords = "";

    public String loggedUser;

    public HashMap<String, String> loginy = new HashMap<>();

    public void Login(ActionEvent event) {
        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/usernames.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            usernames = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] username = usernames.split(";");

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/passwords.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            passwords = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] password = passwords.split(";");

        for (int x = 0; x < username.length; x++) {
            loginy.put(username[x], password[x]);
        }

        String heslo = loginy.get(txUser.getText());

        if (txPass.getText().equals(heslo)) {
            loggedUser = txUser.getText();
            try {
                prepniMain(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
            lblLogged.setText(loggedUser);
        } else {
            lblStat.setTextFill(Color.rgb(255, 0, 0, 1));
            lblStat.setText("Nesprávné Uživatelské Jméno nebo Heslo");
        }
    }

    public void register(ActionEvent event) {
        if (!txUser.getText().contains(";") || !txPass.getText().contains(";")) {
            if (!alreadyExists()) {
                if (!txUser.getText().contains("\\s+") || !txPass.getText().contains("\\s+")) {
                    if (!txUser.getText().isBlank() && !txPass.getText().isBlank()) {
                        String regUsername = txUser.getText();
                        String regPassword = txPass.getText();
                        try {
                            prepniLogin(event);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/usernames.txt"), regUsername + ";", StandardOpenOption.APPEND);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/passwords.txt"), regPassword + ";", StandardOpenOption.APPEND);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        lblStat.setTextFill(Color.rgb(255, 0, 0, 1));
                        lblStat.setText("Uživatelské Jméno ani Heslo nesmí být prázdné");
                    }
                } else {
                    lblStat.setTextFill(Color.rgb(255, 0, 0, 1));
                    lblStat.setText("Uživatelské Jméno ani Heslo nesmí obsahovat mezery!");
                }
            } else {
                lblStat.setTextFill(Color.rgb(255, 0, 0, 1));
                lblStat.setText("Tento uživatel již existuje!");
            }
        } else {
            lblStat.setTextFill(Color.rgb(255, 0, 0, 1));
            lblStat.setText("Uživatelské Jméno ani Heslo nesmí obsahovat tento znak!");
        }
    }

    public void reset(ActionEvent event) {
        txPass.setText("");
        txUser.setText("");
        lblStat.setText("");
    }

    public boolean alreadyExists() {
        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/usernames.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            usernames = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (usernames.contains(txUser.getText())) {
            return true;
        } else {
            return false;
        }
    }

    public void prepniRegister(ActionEvent e) throws IOException {
        lblSiteInfo.setText("Registrace nového uživatele");
        btnToReg.setVisible(false);
        btnLogin.setVisible(false);
        btnReg.setVisible(true);
        btnBackLogin.setVisible(true);
        lblStat.setText("");
    }

    public void prepniLogin(ActionEvent e) throws IOException {
        lblSiteInfo.setText("Přihlášení Uživatele");
        btnReg.setVisible(false);
        btnBackLogin.setVisible(false);
        imgIkonaMain.setVisible(false);
        lblKnihovnaMain.setVisible(false);
        lblLogged.setVisible(false);
        lblLoggedText.setVisible(false);
        btnLogOut.setVisible(false);
        imgIkonaLogin.setVisible(true);
        lblKnihovnaLogin.setVisible(true);
        lblSiteInfo.setVisible(true);
        txUser.setVisible(true);
        txPass.setVisible(true);
        btnReset.setVisible(true);
        btnLogin.setVisible(true);
        btnToReg.setVisible(true);
        txUser.clear();
        txPass.clear();
        lblStat.setText("");
    }

    public void prepniMain(ActionEvent e) throws IOException {
        txUser.setVisible(false);
        txPass.setVisible(false);
        lblStat.setVisible(false);
        imgIkonaLogin.setVisible(false);
        lblKnihovnaLogin.setVisible(false);
        btnLogin.setVisible(false);
        btnReset.setVisible(false);
        btnToReg.setVisible(false);
        lblSiteInfo.setVisible(false);
        btnReg.setVisible(false);
        btnBackLogin.setVisible(false);
        imgIkonaMain.setVisible(true);
        lblKnihovnaMain.setVisible(true);
        lblLogged.setVisible(true);
        lblLoggedText.setVisible(true);
        btnLogOut.setVisible(true);
    }

    public void logOut(ActionEvent e) throws IOException {
        loggedUser = "";
        prepniLogin(e);
    }
}