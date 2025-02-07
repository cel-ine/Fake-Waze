import java.sql.*;

public class DatabaseHandler {

    private static DatabaseHandler handler = null;
    private static Connection connection = null;  // Singleton connection
    
        private DatabaseHandler() {
            // Initialize database connection when the instance is created
            connection = getDBConnection();
        }
    
        public static DatabaseHandler getInstance() {
            if (handler == null) {
                handler = new DatabaseHandler();
            }
            return handler;
        }
    
        private Connection getDBConnection() {
            String dbUrl = "jdbc:mysql://127.0.0.1:3306/my_db?useSSL=false&serverTimezone=UTC"; // Fixes timezone issue
            String userName = "root";
            String password = "ilovecompsci";
        
            try {
                if (connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection(dbUrl, userName, password);
                    System.out.println("Connected to database!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }
        
        public ResultSet execQuery(String query) {
            ResultSet result = null;
            try (Statement stmt = connection.createStatement()) {
                result = stmt.executeQuery(query);
            } catch (SQLException ex) {
                System.out.println("Exception at execQuery: " + ex.getLocalizedMessage());
            }
            return result;
        }
    
        public static boolean validateLogin(String username, String password) {
            DatabaseHandler dbHandler = getInstance();
            
            if (dbHandler.connection == null) { // Check if connection is null before proceeding
                System.out.println("Database connection is null. Check your MySQL server.");
                return false;
            }
        
            String query = "SELECT * FROM WazerAcc WHERE Username = ? AND Password = ?";
            
            try (PreparedStatement pstmt = dbHandler.connection.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
        
                try (ResultSet result = pstmt.executeQuery()) {
                    return result.next(); // If a row exists, login is valid
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
        //DELETES ACCOUNT SA DATABASE
        public static boolean deleteAccount(String username) {
            if (connection == null) {
                System.out.println("Database connection is null. Check your MySQL server.");
                return false;
            }
    
            String query = "DELETE FROM WazerAcc WHERE Username = ?";
    
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, username);
                int affectedRows = pstmt.executeUpdate();
    
                // If rows are affected, deletion was successful
                return affectedRows > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    
        // Close database connection
        public static void closeConnection() {
            try {
                if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}