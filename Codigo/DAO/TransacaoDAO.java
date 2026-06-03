package DAO;

import Controller.ConexaoBD;
import Modelos.Classes_casodeuso.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {

    public boolean realizarTransacao(String descTipo, int tipoMovimentacao, double valor, String data, ContaBancaria conta) {
        try (Connection conn = ConexaoBD.conectar()) {
            conn.setAutoCommit(false); // Bloqueia a base de dados para garantir que tudo é salvo junto
            
            try {
                if (tipoMovimentacao == 2) {
                    valor = valor * -1;
                }

                String sqlTipo = "INSERT INTO Tipotransacao (Tipotransacao) VALUES (?)";
                int idTipo = 0;
                try (PreparedStatement stmt = conn.prepareStatement(sqlTipo, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, descTipo);
                    stmt.executeUpdate();
                    try (ResultSet rs = stmt.getGeneratedKeys()) { if (rs.next()) idTipo = rs.getInt(1); }
                }

                String sqlTrans = "INSERT INTO Transacao (Valortransacao, Data_transacao, Tipotransacao_idTipotransacao, Contabancaria_idContabancaria, Contabancaria_Agencia_Numeroagencia, Contabancaria_Agencia_Banco_Codbanco) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sqlTrans)) {
                    stmt.setString(1, String.valueOf(valor));
                    stmt.setString(2, data);
                    stmt.setInt(3, idTipo);
                    stmt.setInt(4, conta.getIdConta());
                    stmt.setInt(5, conta.getAgencia().getIdAgencia());
                    stmt.setInt(6, conta.getAgencia().getBanco().getCodBanco());
                    stmt.executeUpdate();
                }


                double novoSaldo = conta.getSaldo() + valor; 
                String sqlSaldo = "UPDATE Contabancaria SET Saldo = ? WHERE idContabancaria = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sqlSaldo)) {
                    stmt.setString(1, String.valueOf(novoSaldo));
                    stmt.setInt(2, conta.getIdConta());
                    stmt.executeUpdate();
                }

                conn.commit();
                conta.setSaldo(novoSaldo); 
                System.out.println("Transacao efetuada com sucesso! Novo saldo: R$ " + String.format("%.2f", novoSaldo));
                return true;
                
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Erro na transacao: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro de ligacao: " + e.getMessage());
            return false;
        }
    }


    public List<Transacao> consultarTransacoes(int idConta) {
        List<Transacao> lista = new ArrayList<>();
        String sql = "SELECT t.*, tp.Tipotransacao as NomeTipo FROM Transacao t INNER JOIN Tipotransacao tp ON t.Tipotransacao_idTipotransacao = tp.idTipotransacao WHERE Contabancaria_idContabancaria = ?";
        
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idConta);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transacao t = new Transacao(rs.getInt("idTransacao"));
                    t.setValorTransacao(Double.parseDouble(rs.getString("Valortransacao")));
                    t.setDataTransacao(rs.getString("Data_transacao"));
                    t.setTipoTransacao(new Tipotransacao(rs.getInt("Tipotransacao_idTipotransacao"), rs.getString("NomeTipo")));
                    lista.add(t);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar transações: " + e.getMessage());
        }
        return lista;
    }
}