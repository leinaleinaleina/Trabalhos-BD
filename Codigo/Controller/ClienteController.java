package Controller;

import DAO.ClienteDAO;
import DAO.ContaBancariaDAO;
import Modelos.Classes_casodeuso.*;
import Modelos.Classes_genericas.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClienteController {
    
    public int inserirNovoCliente(Cliente cliente) {
        try (Connection conn = ConexaoBD.conectar()) {
            ClienteDAO dao = new ClienteDAO(conn);
            int idGerado = dao.cadastrarCliente(cliente);
            System.out.println("Cliente " + cliente.getNomecliente() + " inserido com sucesso!");
            return idGerado; 
        } catch (SQLException e) {
            System.err.println("Erro ao inserir cliente: " + e.getMessage());
            return 0; // Retorna 0 em caso de erro
        }
    }

    // Busca por CPF e exibe as informações do cliente e suas contas
    public void consultarEExibirInformacoes(String cpf) {
        try (Connection conn = ConexaoBD.conectar()) {
            
            ClienteDAO clienteDAO = new ClienteDAO(conn);
            ContaBancariaDAO contaDAO = new ContaBancariaDAO(conn);
            
            Cliente cliente = clienteDAO.buscarClientePorCPF(cpf);
            
            if (cliente != null) {
                System.out.println("\n--- DADOS DO CLIENTE ---");
                System.out.println("ID: " + cliente.getIdCliente());
                System.out.println("Nome: " + cliente.getNomecliente());
                System.out.println("CPF: " + cliente.getCPF());
                
                System.out.println("\n--- CONTAS VINCULADAS ---");
                
                List<ContaBancaria> contas = contaDAO.listarContasPorCliente(cliente.getIdCliente());
                
                if (contas.isEmpty()) {
                    System.out.println("Nenhuma conta encontrada.");
                } else {
                    for (ContaBancaria c : contas) {
                        System.out.println("> ID Conta: " + c.getIdConta() + " | Saldo: R$ " + String.format("%.2f", c.getSaldo()));
                    }
                }
            } else {
                System.out.println("Nenhum cliente encontrado com o CPF informado: " + cpf);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar informacoes: " + e.getMessage());
        }
    }
}