package cz.kratochvil.knihovnice;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    private Label lblSiteInfo;

    @FXML
    private Label lblKnizka1;

    @FXML
    private Label lblKnizka2;

    @FXML
    private Label lblKnizka3;

    @FXML
    private Label lblKnizka4;

    @FXML
    private Label lblKnizka5;

    @FXML
    private Label lblKnizka6;

    @FXML
    private Label lblKnizka7;

    @FXML
    private Label lblKnizka8;

    @FXML
    private Label lblKnizka9;

    @FXML
    private Label lblVypujceni;

    @FXML
    private Label lblVraceni;

    @FXML
    private TextField txUser;

    @FXML
    private TextField txPass;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnReg;

    @FXML
    private Button btnBackLogin;

    @FXML
    private Button btnToReg;

    @FXML
    private Button btnPujcit;

    @FXML
    private Button btnNaVraceni;

    @FXML
    private Button btnVraceni;

    @FXML
    private Button btnBackVypujceni;

    @FXML
    private CheckBox chk1;

    @FXML
    private CheckBox chk2;

    @FXML
    private CheckBox chk3;

    @FXML
    private CheckBox chk4;

    @FXML
    private CheckBox chk5;

    @FXML
    private CheckBox chk6;

    @FXML
    private CheckBox chk7;

    @FXML
    private CheckBox chk8;

    @FXML
    private CheckBox chk9;

    @FXML
    private AnchorPane pneVyber;

    @FXML
    private AnchorPane pneLogin;

    @FXML
    private AnchorPane pneMain;

    public String usernames = "";

    public String passwords = "";

    public String jedna = "";

    public String dva = "";

    public String tri = "";

    public String ctyri = "";

    public String pet = "";

    public String sest = "";

    public String sedm = "";

    public String osm = "";

    public String devet = "";

    public String loggedUser;

    public HashMap<String, String> loginy = new HashMap<>();

    public void Login(ActionEvent event) {
        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/usernames.txt");
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
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/passwords.txt");
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
            knizky();
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
                            Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/usernames.txt"), regUsername + ";", StandardOpenOption.APPEND);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/passwords.txt"), regPassword + ";", StandardOpenOption.APPEND);
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
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/usernames.txt");
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
        pneLogin.setVisible(true);
        pneMain.setVisible(false);
        pneVyber.setVisible(false);
        btnLogin.setVisible(true);
        btnToReg.setVisible(true);
        btnReg.setVisible(false);
        btnBackLogin.setVisible(false);
        lblSiteInfo.setText("Přihlášení Uživatele");
        txUser.clear();
        txPass.clear();
        lblStat.setText("");
    }

    public void prepniMain(ActionEvent e) throws IOException {
        pneLogin.setVisible(false);
        pneMain.setVisible(true);
        pneVyber.setVisible(true);

    }

    public void logOut(ActionEvent e) throws IOException {
        loggedUser = "";
        prepniLogin(e);
        chk1.setSelected(false);
        chk2.setSelected(false);
        chk3.setSelected(false);
        chk4.setSelected(false);
        chk5.setSelected(false);
        chk6.setSelected(false);
        chk7.setSelected(false);
        chk8.setSelected(false);
        chk9.setSelected(false);
    }

    public void knizky() {

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/jedna.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            jedna = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] jednicka = jedna.split(";");

        lblKnizka1.setText(jednicka[0]);

        if (!jednicka[2].isBlank()) {
            chk1.setDisable(true);
        } else if (jednicka[2] == loggedUser) {
            chk1.setDisable(true);
            chk1.setSelected(true);
        } else {
            chk1.setDisable(false);
        }

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/dva.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            dva = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String[] dvojka = dva.split(";");

        lblKnizka2.setText(dvojka[0]);

        if (!dvojka[2].isBlank()) {
            chk2.setDisable(true);
        } else if (dvojka[2] == loggedUser) {
            chk2.setDisable(true);
            chk2.setSelected(true);
        } else {
            chk2.setDisable(false);
        }

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/tri.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            tri = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String[] trojka = tri.split(";");

        lblKnizka3.setText(trojka[0]);

        if (!trojka[2].isBlank()) {
            chk3.setDisable(true);
        } else if (trojka[2] == loggedUser) {
            chk3.setDisable(true);
            chk3.setSelected(true);
        } else {
            chk3.setDisable(false);
        }

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/ctyri.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            ctyri = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String[] ctyrka = ctyri.split(";");

        lblKnizka4.setText(ctyrka[0]);

        if (!ctyrka[2].isBlank()) {
            chk4.setDisable(true);
        } else if (ctyrka[2] == loggedUser) {
            chk4.setDisable(true);
            chk4.setSelected(true);
        } else {
            chk4.setDisable(false);
        }

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/pet.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            pet = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String[] petka = pet.split(";");

        lblKnizka5.setText(petka[0]);

        if (!petka[2].isBlank()) {
            chk5.setDisable(true);
        } else if (petka[2] == loggedUser) {
            chk5.setDisable(true);
            chk5.setSelected(true);
        } else {
            chk5.setDisable(false);
        }

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/sest.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            sest = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String[] sestka = sest.split(";");

        lblKnizka6.setText(sestka[0]);

        if (!sestka[2].isBlank()) {
            chk6.setDisable(true);
        } else if (sestka[2] == loggedUser) {
            chk6.setDisable(true);
            chk6.setSelected(true);
        } else {
            chk6.setDisable(false);
        }

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/sedm.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            sedm = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String[] sedmicka = sedm.split(";");

        lblKnizka7.setText(sedmicka[0]);

        if (!sedmicka[2].isBlank()) {
            chk7.setDisable(true);
        } else if (sedmicka[2] == loggedUser) {
            chk7.setDisable(true);
            chk7.setSelected(true);
        } else {
            chk7.setDisable(false);
        }

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/osm.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            osm = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String[] osmicka = osm.split(";");

        lblKnizka8.setText(osmicka[0]);

        if (!osmicka[2].isBlank()) {
            chk8.setDisable(true);
        } else if (osmicka[2] == loggedUser) {
            chk8.setDisable(true);
            chk8.setSelected(true);
        } else {
            chk8.setDisable(false);
        }

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/devet.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            devet = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String[] devitka = devet.split(";");

        lblKnizka9.setText(devitka[0]);

        if (!devitka[2].isBlank()) {
            chk9.setDisable(true);
        } else if (devitka[2] == loggedUser) {
            chk9.setDisable(true);
            chk9.setSelected(true);
        } else {
            chk9.setDisable(false);
        }
    }

    public void vypujcit(ActionEvent e) {
        if (chk1.isSelected()) {
            try {
                File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/jedna.txt");
                FileReader reader = new FileReader(file);
                BufferedReader bfreader = new BufferedReader(reader);
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = bfreader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                jedna = sb.toString();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            String[] jednicka = jedna.split(";");

            try {
                Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/jedna.txt"), jednicka[0] + ";" + jednicka[1] + ";" + loggedUser + ";");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            chk1.setDisable(true);
        }
        if (chk2.isSelected()) {
            try {
                File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/dva.txt");
                FileReader reader = new FileReader(file);
                BufferedReader bfreader = new BufferedReader(reader);
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = bfreader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                dva = sb.toString();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            String[] dvojka = dva.split(";");

            try {
                Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/dva.txt"), dvojka[0] + ";" + dvojka[1] + ";" + loggedUser + ";");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            chk2.setDisable(true);
        }
        if (chk3.isSelected()) {
            try {
                File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/tri.txt");
                FileReader reader = new FileReader(file);
                BufferedReader bfreader = new BufferedReader(reader);
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = bfreader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                tri = sb.toString();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            String[] trojka = tri.split(";");

            try {
                Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/tri.txt"), trojka[0] + ";" + trojka[1] + ";" + loggedUser + ";");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            chk3.setDisable(true);
        }
        if (chk4.isSelected()) {
            try {
                File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/ctyri.txt");
                FileReader reader = new FileReader(file);
                BufferedReader bfreader = new BufferedReader(reader);
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = bfreader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                ctyri = sb.toString();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            String[] ctyrka = ctyri.split(";");

            try {
                Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/ctyri.txt"), ctyrka[0] + ";" + ctyrka[1] + ";" + loggedUser + ";");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            chk4.setDisable(true);
        }
        if (chk5.isSelected()) {
            try {
                File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/pet.txt");
                FileReader reader = new FileReader(file);
                BufferedReader bfreader = new BufferedReader(reader);
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = bfreader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                pet = sb.toString();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            String[] petka = pet.split(";");

            try {
                Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/pet.txt"), petka[0] + ";" + petka[1] + ";" + loggedUser + ";");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            chk5.setDisable(true);
        }
        if (chk6.isSelected()) {
            try {
                File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/sest.txt");
                FileReader reader = new FileReader(file);
                BufferedReader bfreader = new BufferedReader(reader);
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = bfreader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                sest = sb.toString();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            String[] sestka = sest.split(";");

            try {
                Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/sest.txt"), sestka[0] + ";" + sestka[1] + ";" + loggedUser + ";");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            chk6.setDisable(true);
        }
        if (chk7.isSelected()) {
            try {
                File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/sedm.txt");
                FileReader reader = new FileReader(file);
                BufferedReader bfreader = new BufferedReader(reader);
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = bfreader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                sedm = sb.toString();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            String[] sedmicka = sedm.split(";");

            try {
                Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/sedm.txt"), sedmicka[0] + ";" + sedmicka[1] + ";" + loggedUser + ";");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            chk7.setDisable(true);
        }
        if (chk8.isSelected()) {
            try {
                File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/osm.txt");
                FileReader reader = new FileReader(file);
                BufferedReader bfreader = new BufferedReader(reader);
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = bfreader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                osm = sb.toString();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            String[] osmicka = osm.split(";");

            try {
                Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/osm.txt"), osmicka[0] + ";" + osmicka[1] + ";" + loggedUser + ";");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            chk8.setDisable(true);
        }
        if (chk9.isSelected()) {
            try {
                File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/devet.txt");
                FileReader reader = new FileReader(file);
                BufferedReader bfreader = new BufferedReader(reader);
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = bfreader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                devet = sb.toString();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            String[] devitka = devet.split(";");

            try {
                try {
                    Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/devet.txt"), devitka[0] + ";" + devitka[1] + ";" + loggedUser + ";");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            chk9.setDisable(true);
        }
        if (!chk1.isSelected() && !chk2.isSelected() && !chk3.isSelected() && !chk4.isSelected() && !chk5.isSelected() && !chk6.isSelected() && !chk7.isSelected() && !chk8.isSelected() && !chk9.isSelected()) {
            lblVypujceni.setTextFill(Color.rgb(255, 0, 0, 1));
            lblVypujceni.setText("Vyberte knihy, které chcete vypůjčit!");
        } else {
            lblVypujceni.setTextFill(Color.rgb(0, 255, 0, 1));
            lblVypujceni.setText("Vybrané knihy úspěšně vypůjčeny!");
        }
        chk1.setSelected(false);
        chk2.setSelected(false);
        chk3.setSelected(false);
        chk4.setSelected(false);
        chk5.setSelected(false);
        chk6.setSelected(false);
        chk7.setSelected(false);
        chk8.setSelected(false);
        chk9.setSelected(false);
    }

    public void prepniVraceni(ActionEvent e) {
        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/jedna.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            jedna = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] jednicka = jedna.split(";");
        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/dva.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            dva = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] dvojka = dva.split(";");

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/tri.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            tri = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] trojka = tri.split(";");

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/ctyri.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            ctyri = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] ctyrka = ctyri.split(";");

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/pet.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            pet = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] petka = pet.split(";");

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/sest.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            sest = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] sestka = sest.split(";");

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/sedm.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            sedm = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] sedmicka = sedm.split(";");

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/osm.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            osm = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] osmicka = osm.split(";");

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/devet.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            devet = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String[] devitka = devet.split(";");

        chk1.setDisable(true);
        chk2.setDisable(true);
        chk3.setDisable(true);
        chk4.setDisable(true);
        chk5.setDisable(true);
        chk6.setDisable(true);
        chk7.setDisable(true);
        chk8.setDisable(true);
        chk9.setDisable(true);

        lblVypujceni.setText("");
        lblVraceni.setVisible(true);
        btnNaVraceni.setVisible(true);
        btnBackVypujceni.setVisible(true);
        btnPujcit.setVisible(false);
        btnVraceni.setVisible(false);

        if (jednicka[2].equals(loggedUser)) {
            chk1.setDisable(false);
        }
        if (dvojka[2].equals(loggedUser)) {
            chk2.setDisable(false);
        }
        if (trojka[2].equals(loggedUser)) {
            chk3.setDisable(false);
        }
        if (ctyrka[2].equals(loggedUser)) {
            chk4.setDisable(false);
        }
        if (petka[2].equals(loggedUser)) {
            chk5.setDisable(false);
        }
        if (sestka[2].equals(loggedUser)) {
            chk6.setDisable(false);
        }
        if (sedmicka[2].equals(loggedUser)) {
            chk7.setDisable(false);
        }
        if (osmicka[2].equals(loggedUser)) {
            chk8.setDisable(false);
        }
        if (devitka[2].equals(loggedUser)) {
            chk9.setDisable(false);
        }
    }

    public void vraceni(ActionEvent e) {
        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/jedna.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            jedna = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] jednicka = jedna.split(";");
        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/dva.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            dva = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] dvojka = dva.split(";");

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/tri.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            tri = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] trojka = tri.split(";");

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/ctyri.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            ctyri = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] ctyrka = ctyri.split(";");

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/pet.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            pet = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] petka = pet.split(";");

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/sest.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            sest = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] sestka = sest.split(";");

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/sedm.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            sedm = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] sedmicka = sedm.split(";");

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/osm.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            osm = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] osmicka = osm.split(";");

        try {
            File file = new File("src/main/resources/cz/kratochvil/knihovnice/data/devet.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bfreader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            devet = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String[] devitka = devet.split(";");


        if (!chk1.isSelected() && !chk2.isSelected() && !chk3.isSelected() && !chk4.isSelected() && !chk5.isSelected() && !chk6.isSelected() && !chk7.isSelected() && !chk8.isSelected() && !chk9.isSelected()) {
            lblVypujceni.setTextFill(Color.rgb(255, 0, 0, 1));
            lblVypujceni.setText("Vyberte knihy, které chcete vrátit nebo se navraťte k vypůjčení!");

            } else {

            if (chk1.isSelected()) {
                try {
                    Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/jedna.txt"), jednicka[0] + ";" + jednicka[1] + ";" + " " + ";");
                    chk1.setDisable(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if (chk2.isSelected()) {
                try {
                    Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/dva.txt"), dvojka[0] + ";" + dvojka[1] + ";" + " " + ";");
                    chk2.setDisable(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if (chk3.isSelected()) {
                try {
                    Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/tri.txt"), trojka[0] + ";" + trojka[1] + ";" + " " + ";");
                    chk3.setDisable(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
            if (chk4.isSelected()) {
                try {
                    Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/ctyri.txt"), ctyrka[0] + ";" + ctyrka[1] + ";" + " " + ";");
                    chk4.setDisable(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
            if (chk5.isSelected()) {
                try {
                    Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/pet.txt"), petka[0] + ";" + petka[1] + ";" + " " + ";");
                    chk5.setDisable(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
            if (chk6.isSelected()) {
                try {
                    Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/sest.txt"), sestka[0] + ";" + sestka[1] + ";" + " " + ";");
                    chk6.setDisable(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
            if (chk7.isSelected()) {
                try {
                    Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/sedm.txt"), sedmicka[0] + ";" + sedmicka[1] + ";" + " " + ";");
                    chk7.setDisable(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
            if (chk8.isSelected()) {
                try {
                    Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/osm.txt"), osmicka[0] + ";" + osmicka[1] + ";" + " " + ";");
                    chk8.setDisable(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
            if (chk9.isSelected()) {
                try {
                    Files.writeString(Paths.get("src/main/resources/cz/kratochvil/knihovnice/data/devet.txt"), devitka[0] + ";" + devitka[1] + ";" + " " + ";");
                    chk9.setDisable(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

                lblVypujceni.setTextFill(Color.rgb(0, 255, 0, 1));
                lblVypujceni.setText("Knihy úspěšně vráceny!");
                vypniVraceni(e);
                chk1.setSelected(false);
                chk2.setSelected(false);
                chk3.setSelected(false);
                chk4.setSelected(false);
                chk5.setSelected(false);
                chk6.setSelected(false);
                chk7.setSelected(false);
                chk8.setSelected(false);
                chk9.setSelected(false);
            }
        }

        public void vypniVraceni(ActionEvent e){
            btnBackVypujceni.setVisible(false);
            btnNaVraceni.setVisible(false);
            lblVraceni.setVisible(false);
            btnPujcit.setVisible(true);
            btnVraceni.setVisible(true);
            knizky();
            if(lblVypujceni.getTextFill().toString().equals("0xff0000ff"))  {
                lblVypujceni.setText("");
            }

        }
    }