package DAO;

import Modelos.Classes_genericas.Endereco;
import java.sql.*;

public class EnderecoDAO {
    private Connection conexao;

    public EnderecoDAO(Connection conexao) { this.conexao = conexao; }

    public int cadastrar(Endereco endereco) throws SQLException {
        String sql = "INSERT INTO Banco_agencia.Endereco (CEP, Bairro_idBairro, Logradouro_idLogradouro, Cidade_idCidade) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, endereco.getCEP());
            stmt.setInt(2, endereco.getBairro().getIdBairro());
            stmt.setInt(3, endereco.getLogradouro().getIdlogradouro());
            stmt.setInt(4, endereco.getCidade().getIdCidade());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    endereco.setIdEndereco(rs.getInt(1));
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}