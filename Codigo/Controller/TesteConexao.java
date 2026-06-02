package Controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteConexao {
    
    public static void main(String[] args) {
        // 1. Configurações do Banco de Dados
        // Altere "meu_banco" para o nome do banco de dados que você vai criar no MySQL
        String url = "jdbc:mysql://localhost:3306/Banco_agencia"; 
        String usuario = "root";      
        String senha = "root";        

        System.out.println("Iniciando tentativa de conexão...");
        
        // 2. Tentativa de conexão APENAS usando try-with-resources
        try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
            
            if (conexao != null) {
                System.out.println("Sucesso! A conexão com o banco de dados foi estabelecida.");
            }

        } catch (SQLException e) {
            // 3. Tratamento de Erros
            System.err.println("Falha ao conectar com o banco de dados.");
            System.err.println("Motivo: " + e.getMessage());
            System.err.println("Código do erro (Vendor Code): " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
        }
    }
}