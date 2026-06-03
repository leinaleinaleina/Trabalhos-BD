package DAO;

import Class.Classes_genericas.Tipologra;
import java.sql.*;

public class TipolograDAO {
    private Connection conexao;

    public TipolograDAO(Connection conexao) { this.conexao = conexao; }

    public int cadastrar(Tipologra tipo) throws SQLException {
        // No seu modelo a tabela chama-se Tipo_logradouro
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
}