package Controller;

import Class.Classes_casodeuso.Agencia;
import Class.Classes_casodeuso.Banco;
import Class.Classes_casodeuso.ContaBancaria;
import Class.Classes_genericas.Cliente;
import DAO.AgenciaDAO;
import DAO.BancoDAO;
import DAO.ContaBancariaDAO;
import java.sql.Connection;
import java.sql.SQLException;

public class ContaController {

    public boolean abrirNovaContaCompleta(int idConta, Banco banco, Agencia agencia, Cliente cliente) {
        try (Connection conn = ConexaoBD.conectar()) {
            conn.setAutoCommit(false); 

            try {
                BancoDAO bancoDAO = new BancoDAO(conn);
                AgenciaDAO agenciaDAO = new AgenciaDAO(conn);
                ContaBancariaDAO contaDAO = new ContaBancariaDAO(conn);

                if (bancoDAO.buscarBancoPorId(banco.getCodBanco()) == null) {
                    bancoDAO.cadastrar(banco);
                }

                agencia.setBanco(banco);
                if (agenciaDAO.buscarAgenciaPorId(agencia.getIdAgencia()) == null) {
                    agenciaDAO.cadastrar(agencia);
                }

                ContaBancaria conta = new ContaBancaria(idConta, cliente, agencia);
                conta.setSaldo(0.0); // Conta sempre começa com saldo zero
                
                contaDAO.cadastrarConta(conta);

                conn.commit();
                System.out.println("Sucesso! Conta Bancária [" + idConta + "] aberta na Agência " + agencia.getIdAgencia());
                return true;

            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Falha na transação bancária. Rollback efetuado: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro de conexão à base de dados: " + e.getMessage());
            return false;
        }
    }
}