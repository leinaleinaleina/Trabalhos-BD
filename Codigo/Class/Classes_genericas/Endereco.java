package Class.Classes_genericas;

public class Endereco {
    private String CEP;
    private int idEndereco;
    private Bairro bairro;
    private Cidade cidade;
    private Logradouro logradouro;

    public Endereco(String CEP, int idEndereco) {
        this.CEP = CEP;
        this.idEndereco = idEndereco;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public Cidade getCidade() {
        return cidade;
    }
    
    public Logradouro getLogradouro() {
        return logradouro;
    }
}