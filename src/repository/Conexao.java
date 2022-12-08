package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/comunicados_db"; // trocar nome do banco
        Connection connection = DriverManager.getConnection(url, "root", "");

        return connection;
    }
}
