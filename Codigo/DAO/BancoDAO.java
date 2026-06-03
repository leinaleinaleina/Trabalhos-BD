package DAO;

import Modelos.Classes_casodeuso.*;
import java.sql.*;
public class BancoDAO {
    private Connection conexao;

    public BancoDAO(Connection conexao) { this.conexao = conexao; }

    public void cadastrar(Banco banco) throws SQLException {
        String sql = "INSERT INTO Banco_agencia.Banco (Codbanco, Nomebanco, CNPJ) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, banco.getCodBanco());
            stmt.setString(2, banco.getNomeBanco());
            stmt.setString(3, banco.getCNPJ());
            stmt.executeUpdate();
        }
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