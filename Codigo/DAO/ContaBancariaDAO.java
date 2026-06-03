package DAO;

import Modelos.Classes_casodeuso.*;
import Modelos.Classes_genericas.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaBancariaDAO {
    private Connection conexao;

    public ContaBancariaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public int cadastrarConta(ContaBancaria conta) throws SQLException {
        String sql = "INSERT INTO Contabancaria (idContabancaria, Saldo, Agencia_Numeroagencia, Agencia_Banco_Codbanco, Cliente_idCliente) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, conta.getIdConta());
            stmt.setString(2, String.valueOf(conta.getSaldo()));
            stmt.setInt(3, conta.getAgencia().getIdAgencia());
            stmt.setInt(4, conta.getAgencia().getBanco().getCodBanco());
            stmt.setInt(5, conta.getCliente().getIdCliente());
            
            stmt.executeUpdate();
            
            // Retorna o próprio ID 
            return conta.getIdConta();
            }
    }

    public void atualizarSaldo(int idConta, double novoSaldo) throws SQLException {
        String sql = "UPDATE Contabancaria SET Saldo = ? WHERE idContabancaria = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, String.valueOf(novoSaldo)); // Saldo no E-R é VARCHAR
            stmt.setInt(2, idConta);
            stmt.executeUpdate();
        }
    }


    public List<ContaBancaria> listarContasPorCliente(int idCliente) throws SQLException {
        List<ContaBancaria> listaContas = new ArrayList<>();
        
        String sql = "SELECT * FROM Contabancaria WHERE Cliente_idCliente = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idConta = rs.getInt("idContabancaria");
                    String saldoStr = rs.getString("Saldo"); 
                    int numAgencia = rs.getInt("Agencia_Numeroagencia");
                    int codBanco = rs.getInt("Agencia_Banco_Codbanco");

                    Cliente cliente = new Cliente(idCliente);
                    
                    Banco banco = new Banco(codBanco);
                    Agencia agencia = new Agencia(numAgencia);
                    agencia.setBanco(banco); 

                    ContaBancaria conta = new ContaBancaria(idConta, cliente, agencia);

                    if (saldoStr != null && !saldoStr.isEmpty()) {
                        conta.setSaldo(Double.parseDouble(saldoStr));
                    }

                    listaContas.add(conta);
                }
            }
        }
        
        return listaContas;
    }

    public ContaBancaria buscarContaValidandoCPF(int idConta, String cpf) throws SQLException {
        String sql = "SELECT c.* FROM Contabancaria c INNER JOIN Cliente cl ON c.Cliente_idCliente = cl.idCliente WHERE c.idContabancaria = ? AND cl.CPF = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idConta);
            stmt.setString(2, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    Cliente cliente = new Cliente(rs.getInt("Cliente_idCliente"));
                    
                    Banco banco = new Banco(rs.getInt("Agencia_Banco_Codbanco"));
                    Agencia agencia = new Agencia(rs.getInt("Agencia_Numeroagencia"));
                    
                    agencia.setBanco(banco); 
                    
                    ContaBancaria conta = new ContaBancaria(rs.getInt("idContabancaria"), cliente, agencia);
                    
                    String saldoStr = rs.getString("Saldo");
                    conta.setSaldo(saldoStr != null && !saldoStr.isEmpty() ? Double.parseDouble(saldoStr) : 0.0);
                    return conta;
                }
            }
        }
        return null;
    }
}