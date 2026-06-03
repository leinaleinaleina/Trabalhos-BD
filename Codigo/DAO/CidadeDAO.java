package DAO;

import Modelos.Classes_genericas.Cidade;
import java.sql.*;

public class CidadeDAO {
    private Connection conexao;

    public CidadeDAO(Connection conexao) { this.conexao = conexao; }

    public int cadastrar(Cidade cidade) throws SQLException {
        String sql = "INSERT INTO Banco_agencia.Cidade (Cidade, UF_idUF) VALUES (?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cidade.getCidade());
            stmt.setInt(2, cidade.getUf().getIdUF()); 
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cidade.setIdCidade(rs.getInt(1));
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public Cidade buscarPorNomeEUf(String nomeCidade, int idUF) throws SQLException {
        String sql = "SELECT * FROM Cidade WHERE Cidade = ? AND UF_idUF = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nomeCidade);
            stmt.setInt(2, idUF);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cidade c = new Cidade(rs.getInt("idCidade"));
                    c.setCidade(rs.getString("Cidade"));
                    return c;
                }
            }
        }
        return null;
    }
}