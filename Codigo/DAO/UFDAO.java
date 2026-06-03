package DAO;

import Class.Classes_genericas.UF;
import java.sql.*;

public class UFDAO {
    private Connection conexao;

    public UFDAO(Connection conexao) { this.conexao = conexao; }

    public int cadastrar(UF uf) throws SQLException {
        String sql = "INSERT INTO Banco_agencia.UF (UF) VALUES (?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, uf.getUF());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    uf.setIdUF(rs.getInt(1));
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}