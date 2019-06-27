package vuecontrole;

import exceptions.NumSecuException;
import main.RequeteAssurance;
import metier.Client;
import metier.NumSecu;
import metier.Risque;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import static java.awt.Color.*;

public class AjoutClient extends JFrame {
    private JTextField name;
    private JTextField surname;
    private JTextField numSecuInput;
    private JTextField revenus;
    private JButton btn_submit;
    private JComboBox risque;
    private JPanel panel;
    private JTextField phone;
    private JLabel result;
    private RequeteAssurance requete;


    private AjoutClient()throws SQLException, NumSecuException {
        initialisationFenetre();

        initConnexion();
        remplirComboBoxRisques();
        btn_submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sauvegarderClient();
                    result.setText("Client enregistré en base de donné");
                    result.setBackground(green);
                } catch (SQLException|NumSecuException ex) {
                    result.setText("Une erreur est survenu");
                    result.setBackground(red);
                    ex.printStackTrace();
                }
            }
        });
    }

    private void initialisationFenetre() {
        this.add(panel);
        this.getContentPane().setBackground(WHITE);
        this.setTitle("Gestion des Clients");
        this.setSize(500, 300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void initConnexion() {
        try {
            requete = RequeteAssurance.getInstance();
        } catch (Exception e) {
            System.out.println("souci de connexion");
        }
    }

    private void remplirComboBoxRisques() throws SQLException{
        DefaultComboBoxModel modelCombo = new DefaultComboBoxModel();
        List<Risque> allRisques = requete.ensRisques();

        for (Risque risque : allRisques) {
        modelCombo.addElement(risque);
    }
        risque.setModel(modelCombo);
    }

    private boolean sauvegarderClient() throws SQLException, NumSecuException {
        NumSecu numSecu = new NumSecu(numSecuInput.getText());
        Client newClient = new Client(name.getText(), surname.getText(), phone.getText(), Integer.valueOf(revenus.getText()), numSecu, risque.getSelectedIndex());
        return requete.ajouteClient(newClient);
    }

    public static void main(String[] args)throws SQLException, NumSecuException {
        new AjoutClient();
    }
}
