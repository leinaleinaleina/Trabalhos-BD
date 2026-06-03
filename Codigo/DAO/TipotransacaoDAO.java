package DAO;

import Class.Classes_casodeuso.Tipotransacao;
import java.sql.*;

public class TipotransacaoDAO {
    private Connection conexao;

    public TipotransacaoDAO(Connection conexao) { this.conexao = conexao; }

    public int cadastrar(Tipotransacao tipo) throws SQLException {
        String sql = "INSERT INTO Banco_agencia.Tipotransacao (Tipotransacao) VALUES (?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, tipo.getTipotransacao());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    tipo.setIdTipoTransacao(rs.getInt(1)); 
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}