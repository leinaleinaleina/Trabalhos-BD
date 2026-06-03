package DAO;

import Class.Classes_casodeuso.Banco;
import Class.Classes_genericas.Bairro;
import java.sql.*;

public class BairroDAO {
    private Connection conexao;

    public BairroDAO(Connection conexao) { this.conexao = conexao; }

    public int cadastrar(Bairro bairro) throws SQLException {
        String sql = "INSERT INTO Banco_agencia.Bairro (Bairro) VALUES (?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, bairro.getBairro());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    bairro.setIdBairro(rs.getInt(1));
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public Banco buscarBancoPorId(int codBanco) throws SQLException {
        String sql = "SELECT * FROM Banco WHERE Codbanco = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, codBanco);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Banco b = new Banco(rs.getInt("Codbanco"));
                    b.setNomeBanco(rs.getString("Nomebanco"));
                    b.setCNPJ(rs.getString("CNPJ"));
                    return b;
                }
            }
        }
        return null; // Retorna null se o banco não existir
    }
}