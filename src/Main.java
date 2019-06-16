import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        System.out.println("test");

/*        try {
            // Create a named constant for the URL.
            // NOTE: This value is specific for Java DB.
            final String DB_URL = "jdbc:derby:/Users/adrient/IdeaProjects/AssuranceDB/assurance;create=true";

            // Createa connection to the database.
            Connection conn = DriverManager.getConnection(DB_URL);
            // Build the UnpaidInvoice table.


            //buildAll(conn);


            // Close the connection.
            conn.close();
        } catch (Exception e) {
            System.out.println("Error Creating the Coffee Table");
            System.out.println(e.getMessage());
        }*/
    }


/*    public static void buildAll(Connection conn) {
        try {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute(SQL_STRING);

            System.out.println("UnpaidOrder table created.");
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }*/
}
