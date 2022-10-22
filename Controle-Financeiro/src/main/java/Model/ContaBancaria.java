package Model;

import Enums.EnumJustificativa;
import exception.*;
import utils.Operacao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class ContaBancaria {
    private Integer numeroConta;
    private Integer numeroAgencia;

    private Cliente cliente;
    private String justificativa;
    protected Double saldo;
    private Boolean status;
    // private Date dataDeNascimento;

    private LocalDate dataCriacao;
    private LocalDate dataCancelamento;
    private List<Operacao> operacoes;

    private EnumJustificativa enumJustificativa;

    public void setEnumJustificativa(EnumJustificativa enumJustificativa) {
        this.enumJustificativa = enumJustificativa;
    }

    public ContaBancaria() {
    }

    public ContaBancaria(Integer numeroConta, Integer numeroAgencia, Cliente cliente) {
        this.numeroConta = numeroConta;
        this.numeroAgencia = numeroAgencia;
        this.cliente = cliente;
        this.saldo = 0.0;
        this.status = true;
        this.operacoes = new ArrayList<>();
        this.dataCriacao = LocalDate.now().minusDays(5);
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

    public ContaBancaria(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Operacao> getExtrato() {
        return operacoes;
    }

    public void setNumeroConta(Integer numeroConta) {
        this.numeroConta = numeroConta;
    }

    public void setNumeroAgencia(Integer numeroAgencia) {
        this.numeroAgencia = numeroAgencia;
    }

    public void setExtrato(List<Operacao> operacaos) {
        this.operacoes = operacaos;
    }

    public void sacar(Double valor) throws SaldoInsuficienteException, CancelarContaExpetion {
        validarStatusConta(this);
        saldoSuficiente(valor);
        operacoes.add(new Operacao("Sacar", LocalDate.now(), valor, getSaldo()));
    }

    public void depositar(Double valor) throws IllegalArgumentException, CancelarContaExpetion, ValorInsuficienteException {
        validarStatusConta(this);
        validarValor(valor);

        this.saldo += valor;
        System.out.println("Deposito realizado no valor de R$ " + valor);

        operacoes.add(new Operacao("Deposito", LocalDate.now(), valor, getSaldo()));
    }

    public void transferir(Double valor, ContaBancaria contaDestinataria) throws TransferenciaDestinoNuloException, SaldoInsuficienteException, CancelarContaExpetion, ValorInsuficienteException {
        validarStatusConta(this, contaDestinataria);
        validarValor(valor);
        saldoSuficiente(valor);


        System.out.println("Tranferencia realizada com sucesso");
        System.out.println("De: " + this.cliente.getNomeCliente());
        System.out.println("Para: " + contaDestinataria.cliente.getNomeCliente());
        System.out.println("Valor: " + valor + "\n");

        this.saldo -= valor;
        contaDestinataria.saldo += valor;


        addExtrato(new Operacao("Transferencia", LocalDate.now(), valor, getSaldo()));
    }

    public void cancelarConta(ContaBancaria contaBancaria, EnumJustificativa enumJustificativa, String justificativa) throws IllegalArgumentException, CancelarContaExpetion {
        validarStatusConta(this);
        if (justificativa.isBlank() || justificativa == null) {
            throw new IllegalArgumentException("Não foi possível cancelar a conta, por não conter nenhuma justificativa !");
        }
        this.status = false;
        this.setEnumJustificativa(enumJustificativa);
        System.out.println("A conta " + this.numeroConta + " Foi cancelada pelo motivo de " + this.enumJustificativa + " com a seguinte justificativa " + justificativa);
        dataCancelamento = LocalDate.now();
    }

    public void consultarSaldo(LocalDate periodoInicial, LocalDate periodoEstipulado) throws DataInvalidaException, CancelarContaExpetion {
        validarStatusConta(this);

        if (periodoInicial.isAfter(periodoEstipulado)) {
            throw new DataInvalidaException();
        }
        System.out.println("Consultando extratos: ");
        System.out.println("De: " + periodoInicial.toString());
        System.out.println("Ate: " + periodoEstipulado.toString() + "\n");

        for (Operacao op : operacoes) {
            if (op.getTimestemp().isAfter(periodoInicial) || op.getTimestemp().isEqual(periodoInicial) && op.getTimestemp().isBefore(periodoEstipulado)) {
                System.out.println(op.toString());
            }
        }

    }

    public void addExtrato(Operacao transacoes) {
        operacoes.add(transacoes);
    }

    public void validarStatusConta(ContaBancaria contaBancaria) throws CancelarContaExpetion {
        if (contaBancaria.status == null || contaBancaria.status == false) {
            throw new CancelarContaExpetion("A conta está cancelada");
        }
    }

    public void validarStatusConta(ContaBancaria contaBancaria, ContaBancaria contaBancariaDestinataria) throws CancelarContaExpetion {
        validarStatusConta(contaBancaria);
        validarStatusConta(contaBancariaDestinataria);
        if (contaBancaria.numeroConta == contaBancaria.numeroConta) {
            throw new CancelarContaExpetion("Você está tentando transferir para a mesma conta");
        }
    }

    public void saldoSuficiente(Double valor) throws SaldoInsuficienteException {
        if (this.saldo >= valor) {
            this.saldo -= valor;
            System.out.println("Saque realizado no valor de " + valor);
        } else {
            throw new SaldoInsuficienteException("Para sacar o valor deverá ser maior que zero ou um valor suficiente");
        }
    }

    public void validarValor(Double valor) throws ValorInsuficienteException {
        if (valor == null || valor < 0) {
            throw new ValorInsuficienteException();
        }
    }
}
