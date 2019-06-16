import java.sql.Connection;
import java.sql.DriverManager;

public class RequeteAssurance {

    private Connection connection;

    public RequeteAssurance() {
        try {
            // Create a named constant for the URL.
            final String DB_URL = "jdbc:derby:assurance";
            // Createa connection to the database.
            connection = DriverManager.getConnection(DB_URL);
            System.out.println(connection);
            // Close the connection.
            //connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        RequeteAssurance requete = new RequeteAssurance();
    }
}
