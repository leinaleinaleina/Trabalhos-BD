package DAO;

import Modelos.Classes_genericas.DDD;
import java.sql.*;

public class DDDDAO {
    private Connection conexao;

    public DDDDAO(Connection conexao) { this.conexao = conexao; }

    public int cadastrar(DDD ddd) throws SQLException {
        String sql = "INSERT INTO Banco_agencia.DDD (DDD, DDDI_idDDDI) VALUES (?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ddd.getDDD());
            stmt.setInt(2, ddd.getDddi().getIdDDDI()); 
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    ddd.setIdDDD(rs.getInt(1));
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}