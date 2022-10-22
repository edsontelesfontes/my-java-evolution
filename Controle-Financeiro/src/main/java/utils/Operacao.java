package utils;

import java.time.LocalDate;

public class Operacao {
    String transacao;
    LocalDate timestemp;

    Double valorDaTransacao;

    Double saldoDaConta;

    public Operacao(String transacao, LocalDate timestemp, Double valorDaTransacao, Double saldoDaConta) {
        this.transacao = transacao;
        this.timestemp = timestemp;
        this.valorDaTransacao = valorDaTransacao;
        this.saldoDaConta = saldoDaConta;
    }

    public Double getValorDaTransacao() {
        return valorDaTransacao;
    }

    public Double getSaldoDaConta() {
        return saldoDaConta;
    }

    public String getTransacao() {
        return transacao;
    }

    public LocalDate getTimestemp() {
        return timestemp;
    }


    @Override
    public String toString() {
        StringBuilder extratoStringBuilder = new StringBuilder();
        extratoStringBuilder.append("=====Extrato===== \n");
        extratoStringBuilder.append("Tipo da transação: " + transacao + "\n");
        extratoStringBuilder.append("Horário da transação: " + timestemp + "\n");
        extratoStringBuilder.append("Valor: " + valorDaTransacao + "\n");
        extratoStringBuilder.append("Valor disponível em conta: " + saldoDaConta + "\n");

        return extratoStringBuilder.toString();

    }
}
