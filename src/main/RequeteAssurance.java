package main;

import exceptions.NumSecuException;
import metier.Client;
import metier.NumSecu;
import metier.Risque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequeteAssurance {

    private Connection connection;
    private static RequeteAssurance requete;

    private RequeteAssurance() {
        try {
            // Create a named constant for the URL.
            final String DB_URL = "jdbc:derby:/Users/adrient/IdeaProjects/AssuranceDB/assurance";
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            // Create a connection to the database.
            connection = DriverManager.getConnection(DB_URL, "tom", "");
            System.out.println(connection);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public static RequeteAssurance getInstance() {
        if (requete == null) {
            requete = new RequeteAssurance();
        }
        return requete;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Risque> ensRisques() throws SQLException {
        List<Risque> resRisque = new ArrayList<>();
        String SELECT_QUERY = "SELECT NIVEAU, NRISQUE FROM RISQUE";
        // Get a Statement object.
        Statement stmt = this.connection.createStatement();
        ResultSet res = stmt.executeQuery(SELECT_QUERY);
        while (res.next()) {
            Risque risque = new Risque(res.getInt("nrisque"), res.getInt("niveau"));
            //System.out.println(res.getInt("niveau"));
            resRisque.add(risque);
        }
        res.close();
        stmt.close();
        return resRisque;
    }

    public List<Client> ensClients() throws SQLException {
        String requete = "SELECT *\n" +
                "FROM CLIENT c\n" +
                "         INNER JOIN NUMSECU secu\n" +
                "                    ON secu.NNUMSECU = c.NCLIENT\n" +
                "ORDER BY NOM ASC";
        List<Client> clients = new ArrayList<>();
        Statement stmt = this.connection.createStatement();
        ResultSet res = stmt.executeQuery(requete);
        while (res.next()) {
            clients.add(generateClient(res));
        }
        res.close();
        stmt.close();
        return clients;
    }
    public Client selectClient(int idClient) throws SQLException {
        String requete = "SELECT * FROM CLIENT\n" +
                "WHERE NCLIENT = ?;";
        Client result = null;
        PreparedStatement pstmt = connection.prepareStatement(requete);
        pstmt.setInt(1, idClient);
        ResultSet res = pstmt.executeQuery();
        while (res.next()) {
            result = generateClient(res);
        }
        res.close();
        pstmt.close();
        return result;
    }
    public List<Client> ensClients(String nomprenom) throws SQLException {
        String requete = "SELECT *\n" +
                "FROM CLIENT c\n" +
                "         INNER JOIN NUMSECU secu\n" +
                "                    ON secu.NNUMSECU = c.NCLIENT\n" +
                "WHERE LOWER(PRENOM) LIKE ? \n" +
                "OR LOWER(NOM) LIKE ? ";
        List<Client> clients = new ArrayList<>();
        PreparedStatement pstmt = connection.prepareStatement(requete);
        pstmt.setString(1, '%' + nomprenom.toLowerCase() + '%');
        pstmt.setString(2, '%' + nomprenom.toLowerCase() + '%');
        ResultSet res = pstmt.executeQuery();
        while (res.next()) {
            System.out.println(res.getString("prenom") + ' ' + res.getString("nom"));
            //System.out.println(res.getDouble("revenu"));
            clients.add(generateClient(res));
        }
        res.close();
        pstmt.close();
        return clients;
    }

    private int ajouteNumSecu(NumSecu s) throws SQLException {
        int resNumSecu = 0;
        String requete = "INSERT INTO NUMSECU(SEXE, ANNEENAISSANCE, MOISNAISSANCE, DEPARTEMENT, COMMUNE, ORDRE, CLE)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, s.getSexe());
        pstmt.setInt(2, s.getAnneNaissance());
        pstmt.setInt(3, s.getMoisNaissance());
        pstmt.setInt(4, s.getDepartement());
        pstmt.setInt(5, s.getCodeCommune());
        pstmt.setInt(6, s.getOrdre());
        pstmt.setInt(7, s.getCle());
        pstmt.executeUpdate();
        ResultSet res = pstmt.getGeneratedKeys();
        if (res.next()) {
            int id = res.getInt(1);
            resNumSecu = id;
            System.out.println("Id du nouvel enregistrement : " + id);
        }
        res.close();
        pstmt.close();
        return resNumSecu;
    }

    public boolean ajouteClient(Client c) throws SQLException {
        // try add numsecu before
        int resAddSecu = ajouteNumSecu(c.getnNumSecu());
        if (resAddSecu < 0) return false;
        boolean resClient = false;
        String requeteClient = "INSERT INTO CLIENT(NOM, PRENOM, NNUMSECU, TELEPHONE, REVENU, NRISQUE)\n" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(requeteClient);
        pstmt.setString(1, c.getNom());
        pstmt.setString(2, c.getPrenom());
        pstmt.setInt(3, resAddSecu);
        pstmt.setString(4, c.getTelephone());
        pstmt.setInt(5, c.getRevenu());
        pstmt.setInt(6, c.getRisque());
        int nbRows = pstmt.executeUpdate();
        if (nbRows > 0) resClient = true;
        pstmt.close();
        return resClient;
    }

    public boolean supprimerClient(Client c) throws SQLException {
        boolean result = false;
        String requete = "DELETE FROM NUMSECU\n" +
                "WHERE NNUMSECU = ?";
        PreparedStatement pstmt = connection.prepareStatement(requete);
        pstmt.setInt(1, c.getnNumSecu().getnNumSecu());

        int nbRows = pstmt.executeUpdate();
        if (nbRows > 0) result = true;
        pstmt.close();
        return result;
    }

    public boolean miseAJourClient(Client c) throws SQLException {
        boolean resUpdateClient = false;
        String requeteClient = "UPDATE CLIENT\n" +
                "SET NOM=?, PRENOM=?, TELEPHONE=?, REVENU=?, NRISQUE=?\n" +
                "WHERE NCLIENT=?";
        PreparedStatement pstmt = connection.prepareStatement(requeteClient);
        pstmt.setString(1, c.getNom());
        pstmt.setString(2, c.getPrenom());
        pstmt.setString(3, c.getTelephone());
        pstmt.setInt(4, c.getRevenu());
        pstmt.setInt(5, c.getRisque());
        // we get targeted client
        pstmt.setInt(6, c.getnClient());
        int nbRows = pstmt.executeUpdate();
        if (nbRows > 0) resUpdateClient = true;
        pstmt.close();
        return resUpdateClient;
    }

    /**
     * create a Client Object from DB Result
     *
     * @param res
     * @return
     * @throws SQLException
     */
    private Client generateClient(ResultSet res) throws SQLException {
        return new Client(
                res.getInt("nclient"),
                res.getString("nom"),
                res.getString("prenom"),
                res.getString("telephone"),
                res.getInt("revenu"),
                new NumSecu(res.getInt("nnumsecu"), res.getInt("sexe"), res.getInt("anneenaissance"), res.getInt("moisnaissance"), res.getInt("departement"), res.getInt("commune"), res.getInt("ordre"), res.getInt("cle")),
                res.getInt("nrisque"));
    }

    public static void main(String[] args) throws SQLException, NumSecuException {
        RequeteAssurance requete = new RequeteAssurance();
//        List<Risque> mesRisques = requete.ensRisques();
//        System.out.println(mesRisques);
        //System.out.println(requete.ensClients());

        //requete.ensClients("cl");
        NumSecu s = new NumSecu(1321, 0, 98, 10, 24, 24, 22, 168);
//        System.out.println(requete.ajouteNumSecu(s));

//        Client client = new Client("Tib", "Adrien", "0777053188", 45000, s, 3);
        Client clientBis = new Client( 621, "Veraldi", "Lucia", "0777053188", 45000, s, 3);

        //System.out.println(requete.ajouteClient(clientBis));
        System.out.println(requete.supprimerClient(clientBis));
//        System.out.println(requete.miseAJourClient(clientBis));
        requete.close();
    }
}
