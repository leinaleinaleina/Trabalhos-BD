package Class.Classes_genericas;

public class Cidade {
    private int idCidade;
    private String cidade;
    private UF uf;

    public Cidade(int idCidade) {
        this.idCidade = idCidade;
    }

    public int getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(int idCidade) {
        this.idCidade = idCidade;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public UF getUf() {
        return uf;
    }

    public void setUf(UF uf) {
        this.uf = uf;
    }
}