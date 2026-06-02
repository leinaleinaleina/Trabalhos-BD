package Class.Classes_casodeuso;

public class Transacao {
    private int idTransacao;
    private Tipotransacao tipoTransacao;
    private double valorTransacao;
    private String dataTransacao;

    public Transacao(int idTransacao) {
        this.idTransacao = idTransacao;
    }

    public int getIdTransacao() {
        return idTransacao;
    }

    public Tipotransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(Tipotransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public double getValorTransacao() {
        return valorTransacao;
    }

    public void setValorTransacao(double valorTransacao) {
        this.valorTransacao = valorTransacao;
    }

    public String getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(String dataTransacao) {
        this.dataTransacao = dataTransacao;
    }
}