package DAO;

import Modelos.Classes_casodeuso.Tipoinvestimento;
import java.sql.*;

public class TipoinvestimentoDAO {
    private Connection conexao;

    public TipoinvestimentoDAO(Connection conexao) { this.conexao = conexao; }

    public int cadastrar(Tipoinvestimento tipo) throws SQLException {
        String sql = "INSERT INTO Banco_agencia.Tipoinvestimento (Tipoinvestimento) VALUES (?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, tipo.getTipoinvestimento());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}