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
}