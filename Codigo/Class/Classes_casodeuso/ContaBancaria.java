package Class.Classes_casodeuso;
import Class.Classes_genericas.Cliente;

public class ContaBancaria {
    private int idConta;
    private Cliente cliente;
    private Agencia agencia;
    private double saldo;

    public ContaBancaria(int idConta, Cliente cliente, Agencia agencia) {
        this.idConta = idConta;
        this.cliente = cliente;
        this.agencia = agencia;
        this.saldo = 0.0;
    }

    public int getIdConta() {
        return idConta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
    this.saldo = saldo;
    }
}

    