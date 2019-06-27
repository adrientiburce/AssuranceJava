package vuecontrole;

import main.RequeteAssurance;
import metier.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.List;

import static java.awt.Color.WHITE;

public class GestionClients extends JFrame {

    private JPanel panel;
    private JList list_clients;
    private JButton add;
    private JButton update;
    private JButton delete;
    private JTextField editName;
    private JFrame frame;
    private RequeteAssurance requete;


    public GestionClients()throws SQLException {
        initialisationFenetre();

        initConnexion(); // init requete
        remplirListeClients(null);

        editName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String inputNameClient = editName.getText();
                try {
                    remplirListeClients(inputNameClient);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //requete.supprimerClient(client)
                list_clients.getSelectedIndex();
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

    private void remplirListeClients(String nomprenom)throws SQLException {
        DefaultListModel myModel = new DefaultListModel();
        List<Client> allClients;
        if (nomprenom == null) {
            allClients = requete.ensClients();
        } else {
            allClients = requete.ensClients(nomprenom);
        }
        for (Client client : allClients) {
            myModel.addElement(client);
        }
        list_clients.setModel(myModel);
    }

    public static void main(String[] args)throws SQLException {
        GestionClients gestionClients = new GestionClients();
    }

}
