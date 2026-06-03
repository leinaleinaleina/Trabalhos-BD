package Controller;

import Class.Classes_genericas.DDD;
import Class.Classes_genericas.DDDI;
import Class.Classes_genericas.Telefone;
import DAO.DDDDAO;
import DAO.DDDIDAO;
import DAO.TelefoneDAO;
import java.sql.Connection;
import java.sql.SQLException;

public class TelefoneController {

    public boolean cadastrarTelefoneCliente(String dddiTexto, String dddTexto, String numeroFone, int idCliente) {
        try (Connection conn = ConexaoBD.conectar()) {
            conn.setAutoCommit(false); // Inicia transação protegida

            try {
         
                DDDI dddi = new DDDI(0, dddiTexto);
                new DDDIDAO(conn).cadastrar(dddi);

                DDD ddd = new DDD();
                ddd.setDDD(dddTexto);
                ddd.setDddi(dddi);
                new DDDDAO(conn).cadastrar(ddd);

                Telefone telefone = new Telefone();
                telefone.setFone(numeroFone);
                telefone.setDDD(ddd);
                new TelefoneDAO(conn).cadastrarFoneCliente(telefone, idCliente);

                conn.commit(); // Confirma a transação
                System.out.println("Telefone " + numeroFone + " cadastrado com sucesso para o Cliente " + idCliente);
                return true;

            } catch (SQLException e) {
                conn.rollback(); 
                System.err.println("Erro ao cadastrar telefone. Rollback efetuado: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro de conexão no TelefoneController: " + e.getMessage());
            return false;
        }
    }
}