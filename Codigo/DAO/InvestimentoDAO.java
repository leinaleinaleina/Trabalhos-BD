package DAO;

import Class.Classes_casodeuso.*;
import Controller.ConexaoBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestimentoDAO {

    public boolean realizarInvestimento(String descTipo, double valor, String data, ContaBancaria conta) {
        try (Connection conn = ConexaoBD.conectar()) {
            conn.setAutoCommit(false);
            
            try {
                if (conta.getSaldo() < valor) {
                    System.out.println("Saldo insuficiente para realizar este investimento.");
                    return false;
                }


                String sqlTipo = "INSERT INTO Tipoinvestimento (Tipoinvestimento) VALUES (?)";
                int idTipo = 0;
                try (PreparedStatement stmt = conn.prepareStatement(sqlTipo, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, descTipo);
                    stmt.executeUpdate();
                    try (ResultSet rs = stmt.getGeneratedKeys()) { if (rs.next()) idTipo = rs.getInt(1); }
                }


                String sqlInv = "INSERT INTO Investimento (Datainvestimento, Valorinvestimento, Tipoinvestimento_idTipoinvestimento, Contabancaria_idContabancaria, Contabancaria_Agencia_Numeroagencia, Contabancaria_Agencia_Banco_Codbanco) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sqlInv)) {
                    stmt.setString(1, data);
                    stmt.setString(2, String.valueOf(valor));
                    stmt.setInt(3, idTipo);
                    stmt.setInt(4, conta.getIdConta());
                    stmt.setInt(5, conta.getAgencia().getIdAgencia());
                    stmt.setInt(6, conta.getAgencia().getBanco().getCodBanco());
                    stmt.executeUpdate();
                }

                double novoSaldo = conta.getSaldo() - valor;
                String sqlSaldo = "UPDATE Contabancaria SET Saldo = ? WHERE idContabancaria = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sqlSaldo)) {
                    stmt.setString(1, String.valueOf(novoSaldo));
                    stmt.setInt(2, conta.getIdConta());
                    stmt.executeUpdate();
                }

                conn.commit();
                conta.setSaldo(novoSaldo);
                System.out.println("Investimento efetuado! O valor foi deduzido da sua conta. Novo saldo: R$ " + String.format("%.2f", novoSaldo));
                return true;
                
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Erro no investimento: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro de ligação: " + e.getMessage());
            return false;
        }
    }


    public List<Investimento> consultarInvestimentos(int idConta) {
        List<Investimento> lista = new ArrayList<>();
        String sql = "SELECT i.*, ti.Tipoinvestimento as NomeTipo FROM Investimento i INNER JOIN Tipoinvestimento ti ON i.Tipoinvestimento_idTipoinvestimento = ti.idTipoinvestimento WHERE Contabancaria_idContabancaria = ?";
        
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idConta);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Investimento i = new Investimento(rs.getInt("idInvestimento"));
                    i.setDataInvestimento(rs.getString("Datainvestimento"));
                    i.setValorInvestimento(Double.parseDouble(rs.getString("Valorinvestimento")));
                    i.setTipoInvestimento(new Tipoinvestimento(rs.getInt("Tipoinvestimento_idTipoinvestimento"), rs.getString("NomeTipo")));
                    lista.add(i);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar investimentos: " + e.getMessage());
        }
        return lista;
    }
}