package Class.Classes_casodeuso;

public class Investimento {
    private int idInvestimento;
    private Tipoinvestimento tipoInvestimento;
    private double valorInvestimento;
    private String dataInvestimento;

    public Investimento(int idInvestimento) {
        this.idInvestimento = idInvestimento;
    }

    public int getIdInvestimento() {
        return idInvestimento;
    }

    public Tipoinvestimento getTipoInvestimento() {
        return tipoInvestimento;
    }

    public void setTipoInvestimento(Tipoinvestimento tipoInvestimento) {
        this.tipoInvestimento = tipoInvestimento;
    }

    public double getValorInvestimento() {
        return valorInvestimento;
    }

    public void setValorInvestimento(double valorInvestimento) {
        this.valorInvestimento = valorInvestimento;
    }

    public String getDataInvestimento() {
        return dataInvestimento;
    }

    public void setDataInvestimento(String dataInvestimento) {
        this.dataInvestimento = dataInvestimento;
    }
}