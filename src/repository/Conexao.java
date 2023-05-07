package repository;

import java.sql.*;


public class Conexao {

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/comunicados_db",
                    "postgres",
                    "admin");
        } catch (SQLException | ClassNotFoundException e) {
            throw  new RuntimeException(e);
        }
    }
}
