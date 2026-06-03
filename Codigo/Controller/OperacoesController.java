package Controller;

import DAO.InvestimentoDAO;
import DAO.TransacaoDAO;
import Modelos.Classes_casodeuso.ContaBancaria;
import Modelos.Classes_casodeuso.Investimento;
import Modelos.Classes_casodeuso.Transacao;
import java.util.List;

public class OperacoesController {

    private TransacaoDAO transacaoDAO;
    private InvestimentoDAO investimentoDAO;

    public OperacoesController() {
        this.transacaoDAO = new TransacaoDAO();
        this.investimentoDAO = new InvestimentoDAO();
    }

    // TRANSAÇÕES
    public boolean realizarTransacao(String descTipo, int tipoMovimentacao, double valor, String data, ContaBancaria conta) {

        return transacaoDAO.realizarTransacao(descTipo, tipoMovimentacao, valor, data, conta);
    }

    public List<Transacao> consultarTransacoes(int idConta) {
        return transacaoDAO.consultarTransacoes(idConta);
    }

    // INVESTIMENTOS
    public boolean realizarInvestimento(String descTipo, double valor, String data, ContaBancaria conta) {
        return investimentoDAO.realizarInvestimento(descTipo, valor, data, conta);
    }

    public List<Investimento> consultarInvestimentos(int idConta) {
        return investimentoDAO.consultarInvestimentos(idConta);
    }
}