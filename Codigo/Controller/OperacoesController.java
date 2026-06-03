package Controller;

import Class.Classes_casodeuso.ContaBancaria;
import Class.Classes_casodeuso.Investimento;
import Class.Classes_casodeuso.Transacao;
import DAO.InvestimentoDAO;
import DAO.TransacaoDAO;
import java.util.List;

public class OperacoesController {

    private TransacaoDAO transacaoDAO;
    private InvestimentoDAO investimentoDAO;

    // O construtor já instancia os DAOs para não precisarmos fazer isso toda hora
    public OperacoesController() {
        this.transacaoDAO = new TransacaoDAO();
        this.investimentoDAO = new InvestimentoDAO();
    }

    // TRANSAÇÕES
    public boolean realizarTransacao(String descTipo, int tipoMovimentacao, double valor, String data, ContaBancaria conta) {
        // O Controller apenas delega a função para o DAO correspondente
        return transacaoDAO.realizarTransacao(descTipo, tipoMovimentacao, valor, data, conta);
    }

    public List<Transacao> consultarTransacoes(int idConta) {
        return transacaoDAO.consultarTransacoes(idConta);
    }

    // INVESTIMENTOS
    public boolean realizarInvestimento(String descTipo, double valor, String data, ContaBancaria conta) {
        // O Controller delega a função para o DAO correspondente
        return investimentoDAO.realizarInvestimento(descTipo, valor, data, conta);
    }

    public List<Investimento> consultarInvestimentos(int idConta) {
        return investimentoDAO.consultarInvestimentos(idConta);
    }
}