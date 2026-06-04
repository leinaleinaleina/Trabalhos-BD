package DAO;

import Modelos.Classes_genericas.Tipologra;
import java.sql.*;

public class TipolograDAO {
    private Connection conexao;

    public TipolograDAO(Connection conexao) { this.conexao = conexao; }

    public int cadastrar(Tipologra tipo) throws SQLException {
        String sql = "INSERT INTO Banco_agencia.Tipo_logradouro (Tipo_logradouro) VALUES (?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, tipo.getTipologra());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    tipo.setIdtipologra(rs.getInt(1));
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public Tipologra buscarPorNome(String nomeTipo) throws SQLException {
        String sql = "SELECT * FROM Tipo_logradouro WHERE Tipo_logradouro = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nomeTipo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Tipologra t = new Tipologra();
                    t.setIdtipologra(rs.getInt("idTipo_logradouro"));
                    t.setTipologra(rs.getString("Tipo_logradouro"));
                    return t;
                }
            }
        }
        return null;
    }
}