package Modelos.Classes_genericas;

public class DDD {
    private int idDDD;
    private String DDD;
    private DDDI dddi;

    public int getIdDDD() {
        return idDDD;
    }

    public void setIdDDD(int idDDD) {
        this.idDDD = idDDD;
        this.dddi = new DDDI(idDDD, DDD);
    }

    public String getDDD() {
        return DDD;
    }

    public void setDDD(String DDD) {
        this.DDD = DDD;
    }

    public DDDI getDddi() {
        return dddi;
    }

    public void setDddi(DDDI dddi) {
        this.dddi = dddi;
    }
}