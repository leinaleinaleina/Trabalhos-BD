package Class.Classes_genericas;

public class Cliente {
    private final int idCliente;
    private String Nomecliente;
    private String CPF;
    private String Numero;
    private String Complemento;
    private Endereco endereco;
    private Email email;
    private Telefone telefone;

    public Cliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getNomecliente() {
        return Nomecliente;
    }

    public void setNomecliente(String nomecliente) {
        this.Nomecliente = nomecliente;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        this.Numero = numero;
    }

    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(String complemento) {
        this.Complemento = complemento;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public Telefone getTelefone() {
        return telefone;
    }
}
