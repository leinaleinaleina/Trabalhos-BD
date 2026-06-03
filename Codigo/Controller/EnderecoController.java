package Controller;

import DAO.*;
import Modelos.Classes_genericas.*;
import java.sql.Connection;
import java.sql.SQLException;

public class EnderecoController {

    public Endereco cadastrarEnderecoCompleto(UF uf, Cidade cidade, Bairro bairro, Tipologra tipoLogradouro, Logradouro logradouro, String cep) {
        
        try (Connection conn = ConexaoBD.conectar()) {
            conn.setAutoCommit(false); 
            
            try {
                new UFDAO(conn).cadastrar(uf);
                new BairroDAO(conn).cadastrar(bairro);
                new TipolograDAO(conn).cadastrar(tipoLogradouro);
                
                cidade.setUf(uf);
                new CidadeDAO(conn).cadastrar(cidade);
                
                logradouro.setTipologra(tipoLogradouro);
                new LogradouroDAO(conn).cadastrar(logradouro);
                
                Endereco enderecoFinal = new Endereco(cep, 0);
                
                enderecoFinal.setBairro(bairro);
                enderecoFinal.setCidade(cidade);
                enderecoFinal.setLogradouro(logradouro);
                
                new EnderecoDAO(conn).cadastrar(enderecoFinal);
                
                conn.commit(); 
                System.out.println("Endereço cadastrado com sucesso! ID: " + enderecoFinal.getIdEndereco());
                
                return enderecoFinal;
                
            } catch (SQLException e) {
                conn.rollback(); // Cancela tudo se der erro no meio do caminho
                System.err.println("Erro ao cadastrar endereço. Transação cancelada: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Erro de conexão: " + e.getMessage());
        }
        return null;
    }
}
    
