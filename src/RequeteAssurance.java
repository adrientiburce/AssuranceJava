import metier.Client;
import metier.NumSecu;
import metier.Risque;
import org.apache.derby.client.am.SqlException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequeteAssurance {

    private Connection connection;
    private String SELECT_QUERY = "SELECT r.NRISQUE, NIVEAU\n" +
            "FROM RISQUE r\n" +
            "         INNER JOIN CLIENT c\n" +
            "                    ON c.NRISQUE = r.NIVEAU";

    public RequeteAssurance() {
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

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Risque> ensRisques() throws SQLException {
        List<Risque> resRisque = new ArrayList<>();
        try {
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

        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return resRisque;
    }

    public List<Client> ensClients() {
        String requete = "SELECT *\n" +
                "FROM CLIENT c\n" +
                "         INNER JOIN NUMSECU secu\n" +
                "                    ON secu.NNUMSECU = c.NCLIENT\n" +
                "ORDER BY NOM ASC";
        List<Client> clients = new ArrayList<>();
        try {
            Statement stmt = this.connection.createStatement();
            ResultSet res = stmt.executeQuery(requete);
            while (res.next()) {
                clients.add(generateClient(res));
            }
            res.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public List<Client> ensClients(String nomprenom) {
        String requete = "SELECT *\n" +
                "FROM CLIENT c\n" +
                "         INNER JOIN NUMSECU secu\n" +
                "                    ON secu.NNUMSECU = c.NCLIENT\n" +
                "WHERE LOWER(PRENOM) LIKE ? \n" +
                "OR LOWER(NOM) LIKE ? ";
        List<Client> clients = new ArrayList<>();
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

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

    public static void main(String[] args) throws SQLException {
        RequeteAssurance requete = new RequeteAssurance();
        //List<Risque> mesRisques = requete.ensRisques();
        //System.out.println(mesRisques);

        System.out.println(requete.ensClients());
        //requete.ensClients("cl");

        requete.close();
    }
}
