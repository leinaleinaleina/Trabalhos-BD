package Modelos.Classes_casodeuso;

public class Tipoinvestimento {
    private int idTipoinvestimento;
    private String Tipoinvestimento;

    public Tipoinvestimento(int idTipoinvestimento, String Tipoinvestimento) {
        this.idTipoinvestimento = idTipoinvestimento;
        this.Tipoinvestimento = Tipoinvestimento;
    }

    public int getIdTipoinvestimento() {
        return idTipoinvestimento;
    }

    public String getTipoinvestimento() {
        return Tipoinvestimento;
    }
}