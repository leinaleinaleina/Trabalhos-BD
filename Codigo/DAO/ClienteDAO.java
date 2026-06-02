package DAO;
import Class.Classes_genericas.Cliente;


public class ClienteDAO {
    public void cadastrarCliente(Cliente cliente) {
        new Cliente(cliente.getIdCliente());
        String nome = cliente.getNomecliente();
        String cpf = cliente.getCPF();
        String numero = cliente.getNumero();     
        String complemento = cliente.getComplemento();
        String email = cliente.getEmail().getEmail();
        String telefone = cliente.getTelefone().getFone();
    }

    public Cliente buscarClientePorId(int id) {
        
        return null; 
    }
}