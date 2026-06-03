package DAO;

import Class.Classes_genericas.DDDI;
import java.sql.*;

public class DDDIDAO {
    private Connection conexao;

    public DDDIDAO(Connection conexao) { this.conexao = conexao; }

    public int cadastrar(DDDI dddi) throws SQLException {
        String sql = "INSERT INTO Banco_agencia.DDDI (DDDI) VALUES (?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, dddi.getDDDI());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    dddi.setIdDDDI(rs.getInt(1));
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}