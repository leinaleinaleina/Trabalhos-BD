package Class.Classes_casodeuso;
import Class.Classes_genericas.Endereco;

public class Agencia {
    private int idAgencia;
    private String nomeAgencia;
    private String tipoAgencia;
    private Endereco endereco;
    private Banco banco;

        public Agencia(int idAgencia) {
            this.idAgencia = idAgencia;
        }

        public int getIdAgencia() {
            return idAgencia;  
        }

        public void setNomeAgencia(String nomeAgencia) {
            this.nomeAgencia = nomeAgencia;
        }

        public String getNomeAgencia() {
            return nomeAgencia;
        }
        
        public void setTipoAgencia(String tipoAgencia) {
            this.tipoAgencia = tipoAgencia;
        }
        public String getTipoAgencia() {
            return tipoAgencia;
        }

        public void setEndereco(Endereco endereco) {
            this.endereco = endereco;
        }
        
        public Endereco getEndereco() {
            return endereco;
        }

        public void setBanco(Banco banco) {
            this.banco = banco;
        }  

        public Banco getBanco() {
            return banco;
        }

}