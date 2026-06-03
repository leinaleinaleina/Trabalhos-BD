package DAO;

import Modelos.Classes_genericas.Bairro;
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

    public Bairro buscarPorNome(String nomeBairro) throws SQLException {
        String sql = "SELECT * FROM Bairro WHERE Bairro = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nomeBairro);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Bairro b = new Bairro();
                    b.setIdBairro(rs.getInt("idBairro"));
                    b.setBairro(rs.getString("Bairro"));
                    return b;
                }
            }
        }
        return null;
    }
}