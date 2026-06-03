package DAO;

import Class.Classes_genericas.Telefone;
import java.sql.*;

public class TelefoneDAO {
    private Connection conexao;

    public TelefoneDAO(Connection conexao) { this.conexao = conexao; }

    // Salva na tabela Fonecliente vinculando o ID do Cliente
    public int cadastrarFoneCliente(Telefone telefone, int idCliente) throws SQLException {
        String sql = "INSERT INTO Banco_agencia.Fonecliente (Fonecliente, DDD_idDDD, Cliente_idCliente) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, telefone.getFone());
            stmt.setInt(2, telefone.getDDD().getIdDDD()); // FK para DDD
            stmt.setInt(3, idCliente);                   // FK para Cliente
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    telefone.setIdTelefone(rs.getInt(1));
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}