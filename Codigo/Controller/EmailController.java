package Controller;

import DAO.EmailDAO;
import Modelos.Classes_genericas.Email;
import java.sql.Connection;
import java.sql.SQLException;

public class EmailController {

    public boolean cadastrarEmailCliente(String enderecoEmail, int idCliente) {
        try (Connection conn = ConexaoBD.conectar()) {
            
            Email email = new Email();
            email.setEmail(enderecoEmail);
            
            EmailDAO dao = new EmailDAO(conn);
            dao.cadastrarEmailCliente(email, idCliente);
            
            System.out.println("E-mail " + enderecoEmail + " cadastrado com sucesso para o Cliente " + idCliente);
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar e-mail no banco de dados: " + e.getMessage());
            return false;
        }
    }
}