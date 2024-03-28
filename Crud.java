import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Crud {

    private static final String DB_URL = "jdbc:mysql://host:port/nome_banco";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "password";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Conexão com o banco de dados estabelecida.");
            
            createTable(connection);
            
            insertData(connection, "Samuel", 30);
            
            readData(connection);
            
            updateData(connection, 1, "Jose", 40);

            deleteData(connection, 1);
            
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "age INT)";
        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.executeUpdate();
            System.out.println("Tabela 'users' criada (ou já existe).");
        }
    }

    private static void insertData(Connection connection, String name, int age) throws SQLException {
        String insertSQL = "INSERT INTO users (name, age) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.executeUpdate();
            System.out.println("Dados inseridos com sucesso.");
        }
    }

    private static void readData(Connection connection) throws SQLException {
        String selectSQL = "SELECT * FROM users";
        try (PreparedStatement statement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                System.out.println("ID: " + id + ", Nome: " + name + ", Idade: " + age);
            }
        }
    }

    private static void updateData(Connection connection, int id, String name, int age) throws SQLException {
        String updateSQL = "UPDATE users SET name = ?, age = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setInt(3, id);
            statement.executeUpdate();
            System.out.println("Dados atualizados com sucesso.");
        }
    }

    private static void deleteData(Connection connection, int id) throws SQLException {
        String deleteSQL = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Dados excluídos com sucesso.");
        }
    }
}

