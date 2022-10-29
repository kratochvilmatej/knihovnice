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
import java.util.Objects;

public class MainController {
    @FXML
    private Label lblStat;

    @FXML
    private TextField txUser;

    @FXML
    private TextField txPass;

   /* public void Login(ActionEvent event) {
        if (txUser.getText().equals("user") && txPass.getText().equals("pass")) {
            lblStat.setTextFill(Color.rgb(0, 255, 0, 1));
            lblStat.setText("Příhlášení Úspešné");
        } else {
            lblStat.setTextFill(Color.rgb(255, 0, 0, 1));
            lblStat.setText("Nesprávné Uživatelské Jméno nebo Heslo");
        }

    } */


    public void Reset(ActionEvent event) {
        txPass.setText("");
        txUser.setText("");
        lblStat.setText("");
    }

    File data = new File("data.txt");
    int ln;
    String Username;
    String Password;

    public void readFile() {
        try {
            FileReader fr = new FileReader(data);
            System.out.println("file exists!");
        } catch (FileNotFoundException ex) {
            try {
                FileWriter fw = new FileWriter(data);
                System.out.println("File created");
            } catch (IOException ex1) {
                System.out.println("error ex1 readFile");
            }
        }
    }

    public void addData(String usr, String pswd) {
        try {
            RandomAccessFile raf = new RandomAccessFile(data, "rw");
            for (int i = 0; i < ln; i++) {
                raf.readLine();
            }
            if (ln > 0) {
                raf.writeBytes("\r\n");
                raf.writeBytes("\r\n");
            }
            raf.writeBytes("Username:" + usr + "\r\n");
            raf.writeBytes("Password:" + pswd + "\r\n");
        } catch (FileNotFoundException ex) {
            System.out.println("error ex addData");
        } catch (IOException e) {
            System.out.println("sracka");
        }
    }

    public void CheckData(String usr, String pswd) {

        try {
            RandomAccessFile raf = new RandomAccessFile(data, "rw");
            String line = raf.readLine();
            Username = line.substring(9);
            Password = raf.readLine().substring(9);
            if (usr.equals(Username) & pswd.equals(Password)) {
                lblStat.setText("L");
            } else {
                lblStat.setText("Nesprávně zadané Heslo");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("error ex CheckData");
        } catch (IOException ex) {
            System.out.println("error ex CheckData");
        }
    }

    public void logic(String usr, String pswd) {
        try {
            RandomAccessFile raf = new RandomAccessFile(data, "rw");
            for (int i = 0; i < ln; i += 4) {
                System.out.println("count " + i);

                String forUser = raf.readLine().substring(9);
                String forPswd = raf.readLine().substring(9);
                if (usr.equals(forUser) & pswd.equals(forPswd)) {
                    lblStat.setTextFill(Color.rgb(0, 255, 0, 1));
                    lblStat.setText("Příhlášení Úspěšné");
                    break;
                } else if (i == (ln - 3)) {
                    lblStat.setTextFill(Color.rgb(255, 0, 0, 1));
                    lblStat.setText("Nesprávné Uživatelské jméno nebo Heslo");
                    break;
                }
                for (int k = 1; k < 2; k++) {
                    raf.readLine();
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("error ex logic");
        } catch (IOException ex) {
            System.out.println("error ex logic");
        }

    }

    public void countLines() {
        try {
            ln = 0;
            RandomAccessFile raf = new RandomAccessFile(data, "rw");
            for (int i = 0; raf.readLine() != null; i++) {
                ln++;
            }
            System.out.println("number of lines:" + ln);
        } catch (FileNotFoundException ex) {
            System.out.println("error ex countLines");
        } catch (IOException ex) {
            System.out.println("error ex countLines");
        }

    }

    public void Login(ActionEvent e) {
        readFile();
        countLines();
        logic(txUser.getText(), txPass.getText());
    }

    public void Register(ActionEvent e) {
        readFile();
        countLines();
        addData(txUser.getText(), txPass.getText());
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
}
