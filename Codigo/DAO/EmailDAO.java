package DAO;

import Modelos.Classes_genericas.Email;
import java.sql.*;

public class EmailDAO {
    private Connection conexao;

    public EmailDAO(Connection conexao) { this.conexao = conexao; }

    public int cadastrarEmailCliente(Email email, int idCliente) throws SQLException {
        String sql = "INSERT INTO Banco_agencia.Emailcliente (Emailcliente, Cliente_idCliente) VALUES (?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, email.getEmail());
            stmt.setInt(2, idCliente); // FK para Cliente
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    email.setIdEmail(rs.getInt(1));
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}