package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:postgresql://localhost:5432/padaria_bd";
    private static final String USER = "postgres";
    private static final String PASSWORD = "PGCEAVI";
       
     public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão com o banco estabelecida com sucesso!");
            return conn;

        } catch (ClassNotFoundException e) {
            System.err.println("Driver PostgreSQL não encontrado. Verifique se o .jar está no classpath.");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("🔒 Conexão encerrada com sucesso.");
            } catch (SQLException e) {
                System.err.println("⚠️ Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}