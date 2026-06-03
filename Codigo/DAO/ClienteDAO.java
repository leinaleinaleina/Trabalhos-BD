package DAO;

import Modelos.Classes_genericas.Cliente;
import Modelos.Classes_genericas.Endereco;
import java.sql.*;

public class ClienteDAO {
    private Connection conexao;

    public ClienteDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Função para inserir dados
    public int cadastrarCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Banco_agencia.Cliente (Nomecliente, CPF, Complemento, Numero, Endereco_idEndereco) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cliente.getNomecliente());
            stmt.setString(2, cliente.getCPF());
            stmt.setString(3, cliente.getComplemento());
            stmt.setString(4, cliente.getNumero());
            
            // Tratamento da chave estrangeira de endereço
            if (cliente.getEndereco() != null) {
                stmt.setInt(5, cliente.getEndereco().getIdEndereco());
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }
            
            stmt.executeUpdate();
            
            // Recupera o ID gerado pelo banco para o objeto
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0; // Retorna 0 se não conseguir inserir ou recuperar o ID
    }

    // Função de busca pelo CPF
    public Cliente buscarClientePorCPF(String cpf) throws SQLException {
        String sql = "SELECT * FROM Cliente WHERE CPF = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente(rs.getInt("idCliente"));
                    cliente.setNomecliente(rs.getString("Nomecliente"));
                    cliente.setCPF(rs.getString("CPF"));
                    cliente.setComplemento(rs.getString("Complemento"));
                    cliente.setNumero(rs.getString("Numero"));
                    
                    int idEndereco = rs.getInt("Endereco_idEndereco"); 
                    
                    if (!rs.wasNull()) {
                        cliente.setEndereco(new Endereco(null, idEndereco)); 
                    }
                    return cliente;
                }
            }
        }
        return null; // Retorna null se não encontrar
    }
}