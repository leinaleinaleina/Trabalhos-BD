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
                UFDAO ufDAO = new UFDAO(conn);
                BairroDAO bairroDAO = new BairroDAO(conn);
                TipolograDAO tipoDAO = new TipolograDAO(conn);
                CidadeDAO cidadeDAO = new CidadeDAO(conn);
                LogradouroDAO logradouroDAO = new LogradouroDAO(conn);
                EnderecoDAO enderecoDAO = new EnderecoDAO(conn);


                UF ufExistente = ufDAO.buscarPorNome(uf.getUF());
                if (ufExistente != null) { uf = ufExistente; } 
                else { ufDAO.cadastrar(uf); }

                Bairro bairroExistente = bairroDAO.buscarPorNome(bairro.getBairro());
                if (bairroExistente != null) { bairro = bairroExistente; }
                else { bairroDAO.cadastrar(bairro); }


                Tipologra tipoExistente = tipoDAO.buscarPorNome(tipoLogradouro.getTipologra());
                if (tipoExistente != null) { tipoLogradouro = tipoExistente; }
                else { tipoDAO.cadastrar(tipoLogradouro); }

                cidade.setUf(uf);
                Cidade cidadeExistente = cidadeDAO.buscarPorNomeEUf(cidade.getCidade(), uf.getIdUF());
                if (cidadeExistente != null) { cidade = cidadeExistente; }
                else { cidadeDAO.cadastrar(cidade); }

                logradouro.setTipologra(tipoLogradouro);
                Logradouro logradouroExistente = logradouroDAO.buscarLogradouro(logradouro.getLogradouro(), tipoLogradouro.getIdtipologra());
                if (logradouroExistente != null) { logradouro = logradouroExistente; }
                else { logradouroDAO.cadastrar(logradouro); }

   
                Endereco enderecoFinal = enderecoDAO.buscarEnderecoExistente(cep, bairro.getIdBairro(), logradouro.getIdlogradouro(), cidade.getIdCidade());
                
                if (enderecoFinal == null) {
                    enderecoFinal = new Endereco(cep, 0);
                    enderecoFinal.setBairro(bairro);
                    enderecoFinal.setCidade(cidade);
                    enderecoFinal.setLogradouro(logradouro);
                    enderecoDAO.cadastrar(enderecoFinal);
                }

                conn.commit();
                System.out.println("Endereco mapeado e vinculado com sucesso! ID: " + enderecoFinal.getIdEndereco());
                return enderecoFinal;
                
            } catch (SQLException e) {
                conn.rollback(); 
                System.err.println("Erro ao processar o endereco. Transacao cancelada: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Erro de conexao: " + e.getMessage());
        }
        return null;
    }
}

    
