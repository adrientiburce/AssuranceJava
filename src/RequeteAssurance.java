import metier.Risque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequeteAssurance {

    private Connection connection;
    private  String SELECT_QUERY = "SELECT r.NRISQUE, NIVEAU\n" +
            "FROM RISQUE r\n" +
            "         INNER JOIN CLIENT c\n" +
            "                    ON c.NRISQUE = r.NIVEAU";

    public RequeteAssurance() {
        try {
            // Create a named constant for the URL.
            final String DB_URL = "jdbc:derby:/Users/adrient/IdeaProjects/AssuranceDB/assurance";
//            // Createa connection to the database.
//            //Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection(DB_URL, "tom", "");
            System.out.println(connection);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
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
                System.out.println(res.getInt("niveau"));
                resRisque.add(risque);
            }
            res.close();
            stmt.close();

        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return resRisque;
    }

    public static void main(String[] args) throws SQLException {
        RequeteAssurance requete = new RequeteAssurance();
        List<Risque> mesRisques = requete.ensRisques();
        System.out.println(mesRisques);

    }
}
