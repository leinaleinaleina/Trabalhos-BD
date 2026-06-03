package DAO;

import Class.Classes_casodeuso.*;
import java.sql.*;

public class AgenciaDAO {
    private Connection conexao;

    public AgenciaDAO(Connection conexao) { this.conexao = conexao; }

    public void cadastrar(Agencia agencia) throws SQLException {
        
        String sql = "INSERT INTO Banco_agencia.Agencia (Numeroagencia, Tipoagencia, Banco_Codbanco, Endereco_idEndereco) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, agencia.getIdAgencia()); 
     
            int tipoAgenciaInt = Integer.parseInt(agencia.getTipoAgencia());
            stmt.setInt(2, tipoAgenciaInt);
            
            stmt.setInt(3, agencia.getBanco().getCodBanco());
            
            if (agencia.getEndereco() != null) {
                stmt.setInt(4, agencia.getEndereco().getIdEndereco());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            
            stmt.executeUpdate();
        }
    }

    public Agencia buscarAgenciaPorId(int numeroAgencia) throws SQLException {
        String sql = "SELECT * FROM Agencia WHERE Numeroagencia = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, numeroAgencia);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Agencia(rs.getInt("Numeroagencia"));
                }
            }
        }
        return null; // Retorna null se a agência não existir
    }
}