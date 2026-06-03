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
}