package Modelos.Classes_casodeuso;

public class Tipotransacao {
    private int idTipoTransacao;
    private String Tipotransacao;

    public Tipotransacao(int idTipoTransacao, String Tipotransacao) {
        this.idTipoTransacao = idTipoTransacao;
        this.Tipotransacao = Tipotransacao;
    }

    public int getIdTipoTransacao() {
        return idTipoTransacao;
    }

    public String getTipotransacao() {
        return Tipotransacao;
    }

    public void setIdTipoTransacao(int idTipoTransacao) {
        this.idTipoTransacao = idTipoTransacao;
    }
    
    public void setTipotransacao(String Tipotransacao) {
        this.Tipotransacao = Tipotransacao;
    }
}