package Controller;

import Class.Classes_genericas.*;
import DAO.*;
import java.sql.Connection;
import java.sql.SQLException;

public class EnderecoController {

    // Método completinho: cadastra todas as dependências e depois o endereço
    public Endereco cadastrarEnderecoCompleto(UF uf, Cidade cidade, Bairro bairro, Tipologra tipoLogradouro, Logradouro logradouro, String cep) {
        
        try (Connection conn = ConexaoBD.conectar()) {
            // Desativa auto-commit para garantir que ou salva tudo, ou não salva nada
            conn.setAutoCommit(false); 
            
            try {
                // 1. Salva Nível 1
                new UFDAO(conn).cadastrar(uf);
                new BairroDAO(conn).cadastrar(bairro);
                new TipolograDAO(conn).cadastrar(tipoLogradouro);
                
                // 2. Associa as FKs e salva Nível 2
                cidade.setUf(uf);
                new CidadeDAO(conn).cadastrar(cidade);
                
                logradouro.setTipologra(tipoLogradouro);
                new LogradouroDAO(conn).cadastrar(logradouro);
                
                // 3. Monta o Endereço final (Nível 3)
                Endereco enderecoFinal = new Endereco(cep, 0);
                
                enderecoFinal.setBairro(bairro);
                enderecoFinal.setCidade(cidade);
                enderecoFinal.setLogradouro(logradouro);
                
                new EnderecoDAO(conn).cadastrar(enderecoFinal);
                
                conn.commit(); // Confirma a transação inteira no banco
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