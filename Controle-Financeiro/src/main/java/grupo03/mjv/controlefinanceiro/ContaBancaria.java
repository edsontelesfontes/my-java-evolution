package grupo03.mjv.controlefinanceiro;

import exception.CancelarContaExpetion;
import exception.DataInvalidaException;
import exception.SaldoInsuficienteException;
import exception.TransferenciaDestinoNuloException;
import utils.Extrato;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ContaBancaria {
    private Integer numeroConta;
    private Integer numeroAgencia;
    private String nomeCliente;
    private String justificativa;
    private Double saldo;
    private Boolean status;
   // private Date dataDeNascimento;
    private List<Extrato> extrato;



    public ContaBancaria() {
    }

    public ContaBancaria(Integer numeroConta, Integer numeroAgencia, String nomeCliente) {
        this.numeroConta = numeroConta;
        this.numeroAgencia = numeroAgencia;
        this.nomeCliente = nomeCliente;
        this.saldo = 0.0;
        this.status = true;
     //   this.dataDeNascimento = dataDeNascimento;
        this.extrato = new ArrayList<>();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContaBancaria that = (ContaBancaria) o;
        return numeroConta.equals(that.numeroConta) && numeroAgencia.equals(that.numeroAgencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroConta, numeroAgencia);
    }


//    public Date getDataDeNascimento() {
//        return dataDeNascimento;
//    }

    public Integer getNumeroConta() {
        return numeroConta;
    }

    public Integer getNumeroAgencia() {
        return numeroAgencia;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public Double getSaldo() {
        return saldo;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public List<Extrato> getExtrato() {
        return extrato;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
    public void setNumeroConta(Integer numeroConta) {
        this.numeroConta = numeroConta;
    }

    public void setNumeroAgencia(Integer numeroAgencia) {
        this.numeroAgencia = numeroAgencia;
    }
    public void setExtrato(List<Extrato> extrato) {
        this.extrato = extrato;
    }

    public void sacar(Double valor) throws SaldoInsuficienteException {
        if(this.saldo >= valor){
            this.saldo -= valor;
            System.out.println("Saque efetuado no valor de " + valor);
        }
        else{
            throw new SaldoInsuficienteException("O valor do saque não pode ser maior que o valor do saldo");
        }

//        if (isSaldoSuficiente(valor)) {
////            System.out.println("Você não tem saldo o suficiente !");
////            System.out.println("O seu saldo atual é de " + this.saldo);
//            throw new SaldoInsuficienteException("O valor do saque não pode ser maior que o valor do saldo");
//        } else {
//            this.saldo -= valor;
//            System.out.println("Saque efetuado no valor de " + valor);
//        }
        extrato.add(new Extrato("Sacar", LocalDate.now(), valor, getSaldo()));

    }

    public void depositar(Double valor) throws IllegalArgumentException {
        if (valor > 0) {
            this.saldo += valor;
            System.out.println("Deposito realizado no valor de R$" + valor);
        } else {
            System.out.println("Deposite um valor válido !");
            throw new IllegalArgumentException("O valor a ser depositado não pode ser 0 ou menor que 0");
        }
        
        extrato.add(new Extrato("Deposito", LocalDate.now(), valor, getSaldo()));
    }

    public void transferir(Double valor, ContaBancaria contaDestinataria) throws TransferenciaDestinoNuloException, SaldoInsuficienteException {
        if(valor <= 0){
            throw new SaldoInsuficienteException("O valor transferido não pode ser maior que o saldo !");
        }
        if (contaDestinataria == null) {
            throw new TransferenciaDestinoNuloException("A conta destino informada é nula, por favor digitar uma conta válida");
        }
        else if (valor > 0) {
            System.out.println("Tranferencia realizada com sucesso");
            System.out.println("De: " + this.nomeCliente);
            System.out.println("Para: " + contaDestinataria.nomeCliente);
            System.out.println("Valor: " + valor + "\n");
            contaDestinataria.saldo += valor;
            this.saldo -= valor;
        } else if (isMesmaConta(contaDestinataria)) {
            System.out.println("Você está tentando transferir para a mesma conta !");
            return;
        }

//        System.out.println("Tranferencia realizada com sucesso");
//        System.out.println("De: " + this.nomeCliente);
//        System.out.println("Para: " + contaDestinataria.nomeCliente);
//        System.out.println("Valor: " + valor + "\n");
//        contaDestinataria.saldo += valor;
//        this.saldo -= valor;
        addExtrato(new Extrato("Transferencia", LocalDate.now(), valor, getSaldo()));
    }

    public boolean cancelarConta(ContaBancaria contaBancaria, String justificativa) throws IllegalArgumentException, CancelarContaExpetion {
        if (justificativa.isBlank()) {
            throw new IllegalArgumentException("Não foi possível cancelar a conta, por não conter nenhuma justificativa !");
        }
        if (contaBancaria.getStatus() == false) {
           // System.out.println("Está conta já esta cancelada por motivo de " + justificativa);
            throw new CancelarContaExpetion("Está conta já esta cancelada por motivo de " + justificativa);
        } else {
            this.justificativa = justificativa;
            this.status = false;
            System.out.println("A conta " + this.numeroConta + " Foi canceladam pelo motivo de " + justificativa);
        }
        return false;
    }

    public void consultarSaldo(LocalDate periodoInicial, LocalDate periodoEstipulado) throws DataInvalidaException {
        if (periodoInicial.isAfter(periodoEstipulado)) {
            throw new DataInvalidaException();
        }
        System.out.println("Consultando extratos: ");
        System.out.println("De: " + periodoInicial.toString());
        System.out.println("Ate: " + periodoEstipulado.toString() + "\n");

        for (Extrato extratoDemonstrativo : this.extrato) {
            if (extratoDemonstrativo.getTimestemp().isAfter(periodoInicial) || extratoDemonstrativo.getTimestemp().isEqual(periodoInicial)
                    && extratoDemonstrativo.getTimestemp().isBefore(periodoEstipulado))
                System.out.println(extratoDemonstrativo);
        }

    }

    public void addExtrato(Extrato transacoes) {
        extrato.add(transacoes);
    }

//    public Boolean isSaldoSuficiente(Double valor) {
//        if(this.saldo >= valor){
//            return true;
//        }
//        else {
//            return false;
//        }
//    }
    public Boolean isMesmaConta(ContaBancaria numeroContaDestinataria){
        if(this.numeroConta == numeroContaDestinataria.getNumeroConta()){
            return true;
        }
        else{
            return false;
        }
    }
}
