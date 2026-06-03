package DAO;

import Modelos.Classes_genericas.Logradouro;
import java.sql.*;

public class LogradouroDAO {
    private Connection conexao;

    public LogradouroDAO(Connection conexao) { this.conexao = conexao; }

    public int cadastrar(Logradouro logradouro) throws SQLException {
        String sql = "INSERT INTO Banco_agencia.Logradouro (Logradouro, Tipo_logradouro_idTipo_logradouro) VALUES (?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, logradouro.getLogradouro());
            stmt.setInt(2, logradouro.getTipologra().getIdtipologra()); // Pega a FK
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    logradouro.setIdlogradouro(rs.getInt(1));
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public Logradouro buscarLogradouro(String nome, int idTipo) throws SQLException {
        String sql = "SELECT * FROM Logradouro WHERE Logradouro = ? AND Tipo_logradouro_idTipo_logradouro = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setInt(2, idTipo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Logradouro l = new Logradouro();
                    l.setIdlogradouro(rs.getInt("idLogradouro"));
                    l.setLogradouro(rs.getString("Logradouro"));
                    return l;
                }
            }
        }
        return null;
    }
}