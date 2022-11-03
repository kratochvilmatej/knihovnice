package cz.kratochvil.knihovnice;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Objects;

public class MainController {
    @FXML
    private Label lblStat;

    @FXML
    public Label lblLogged;

    @FXML
    private TextField txUser;

    @FXML
    private TextField txPass;

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

        String[] username = usernames.split("\\s+");

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

        String[] password = passwords.split("\\s+");

        for (int x = 0; x < username.length; x++) {
            loginy.put(username[x], password[x]);
        }
        for (String i : loginy.keySet()) {
            System.out.println(i + " " + loginy.get(i));
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

    public void Register(ActionEvent event) {
        if (alreadyExists()==false) {
            if (!txUser.getText().contains("\\s+") || !txPass.getText().contains("\\s+")) {
                if (!txUser.getText().isBlank() && !txPass.getText().isBlank()) {
                    try {
                        prepniLogin(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String regUsername = txUser.getText();
                    String regPassword = txPass.getText();

                    try {
                        Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/usernames.txt"), regUsername + " ", StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/passwords.txt"), regPassword + " ", StandardOpenOption.APPEND);
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
            lblStat.setText("Tento uživatel již existuje, zvolte jiné Uživatelské jméno!");
        }
    }

    public void Reset(ActionEvent event) {
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
        if(usernames.contains(txUser.getText())) {
            return true;
        } else {
            return false;
        }
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void prepniRegister(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Register.fxml")));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    public void prepniLogin(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    public void prepniMain(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root, 1080, 720);
        stage.setScene(scene);
        stage.show();
    }
}