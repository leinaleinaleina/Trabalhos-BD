package Modelos.Classes_genericas;

public class DDDI {
    private int idDDDI;
    private String DDDI;

    public DDDI() {
    }

    public DDDI(int idDDDI, String DDDI) {
        this.idDDDI = idDDDI;
        this.DDDI = DDDI;
    }

    public int getIdDDDI() {
        return idDDDI;
    }

    public void setIdDDDI(int idDDDI) {
        this.idDDDI = idDDDI;
    }

    public String getDDDI() {
        return DDDI;
    }

    public void setDDDI(String DDDI) {
        this.DDDI = DDDI;
    }
}
