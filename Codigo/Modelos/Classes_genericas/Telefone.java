package Modelos.Classes_genericas;

public class Telefone {
    private int idTelefone;
    private String fone;
    private DDD ddd;

    public int getIdTelefone() {
        return idTelefone;
    }

    public void setIdTelefone(int idTelefone) {
        this.idTelefone = idTelefone;
        this.ddd = new DDD();
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public DDD getDDD() {
        return ddd;
    }

    public void setDDD(DDD ddd) {
        this.ddd = ddd;
    }

}
