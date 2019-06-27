package vuecontrole;

import main.RequeteAssurance;
import metier.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import static java.awt.Color.*;

public class Accueil extends JFrame {
    private JButton button_accueil;
    private JPanel panel;
    private JList list1;
    private JFrame frame;
    private RequeteAssurance requete;
    public GestionClients fenetreClient;

    public Accueil() {
        initialisationFenetre();
        initConnexion();
        button_accueil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("test");
            }
        });
        button_accueil.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    fenetreClient = new GestionClients(); // new JfRAME
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void initialisationFenetre() {
        this.add(panel);
        this.getContentPane().setBackground(WHITE);
        this.setTitle("Accueil");
        this.setSize(500, 500);
        this.setVisible(true);
    }

    private void initConnexion() {
        try {
            requete = RequeteAssurance.getInstance();
        } catch (Exception e) {
            System.out.println("probleme de connexion");
            frame.dispose();
        }
    }

    public static void main(String[] args) {
        Accueil accueil = new Accueil();
    }

}

