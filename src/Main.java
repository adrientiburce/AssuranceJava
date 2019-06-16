import java.sql.*;

public class Main {

    public static void main(String args[]) throws Exception {
        final String DATABASE_URL = "jdbc:derby:/Users/adrient/IdeaProjects/AssuranceDB/assurance";
        final String SELECT_QUERY = "SELECT * FROM RISQUE";
        try (
                Connection connection = DriverManager.getConnection(
                        DATABASE_URL, "tom", "");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SELECT_QUERY)) {
            // get ResultSet's meta data
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            System.out.printf("Authors Table of Books Database:%n%n");

            // display the names of the columns in the ResultSet
            for (int i = 1; i <= numberOfColumns; i++)
                System.out.printf("%-8s\t", metaData.getColumnName(i));
            System.out.println();

            // display query results
            while (resultSet.next()) {
                for (int i = 1; i <= numberOfColumns; i++)
                    System.out.printf("%-8s\t", resultSet.getObject(i));
                System.out.println();
            }
        } // AutoCloseable objects' close methods are called now
        catch (SQLException sqlException) {
            //sqlException.printStackTrace();
            System.out.println("ERROR: " + sqlException.getMessage());

        }
    }

}
