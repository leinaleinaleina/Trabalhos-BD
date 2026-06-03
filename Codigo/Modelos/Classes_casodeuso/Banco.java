package Modelos.Classes_casodeuso;


public class Banco {
        private int codBanco;
        private String nomeBanco;
        private String CNPJ;

        public Banco(int codBanco) {
            this.codBanco = codBanco;
        }

        public int getCodBanco() {
            return codBanco;
        }

        public void setNomeBanco(String nomeBanco) {
            this.nomeBanco = nomeBanco;
        }

        public String getNomeBanco() {
            return nomeBanco;
        }
        
        public void setCNPJ(String CNPJ) {
            this.CNPJ = CNPJ;
        }

        public String getCNPJ() {
            return CNPJ;
        }

        
}