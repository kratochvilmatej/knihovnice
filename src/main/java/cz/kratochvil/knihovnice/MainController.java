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

import javax.net.ssl.HandshakeCompletedEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
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
    private Label lblStatReg;

    @FXML
    private TextField txUser;

    @FXML
    private TextField txPass;

    public String usernames = "";

    public String passwords = "";

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
                //sb.append("\n");
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
                //sb.append("\n");
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
            lblStat.setTextFill(Color.rgb(0, 255, 0, 1));
            lblStat.setText("Příhlášení Úspešné");
            String loggedUser = txUser.getText();
            //prepni na knihovnu
        } else {
            lblStat.setTextFill(Color.rgb(255, 0, 0, 1));
            lblStat.setText("Nesprávné Uživatelské Jméno nebo Heslo");
        }
    }

    public void Register(ActionEvent event) {
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
                    /*BufferedWriter writer = new BufferedWriter(new FileWriter("usernames.txt"));
                    writer.write(usernames + regUsername);
                    writer.close();*/
                    Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/usernames.txt"), regUsername + " ", StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    /*BufferedWriter writer = new BufferedWriter(new FileWriter("passwords.txt"));
                    writer.write(passwords + regPassword);
                    writer.close();*/
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
    }


    public void Reset(ActionEvent event) {
        txPass.setText("");
        txUser.setText("");
        lblStat.setText("");
    }

    /*File data = new File("data.txt");
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
    }*/

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