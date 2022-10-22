package Model;

import exception.CancelarContaExpetion;
import exception.SaldoInsuficienteException;

public class ContaCorrente extends ContaBancaria{

    private Double chequeEspecial = 0.0;

    public ContaCorrente(Integer numeroConta, Integer numeroAgencia, Cliente cliente) {
        super(numeroConta, numeroAgencia, cliente);
    }

    @Override
    public void sacar(Double valor) throws SaldoInsuficienteException, CancelarContaExpetion {
        Double soma = saldo + chequeEspecial;
        if(getSaldo() > valor){
            saldo -= valor;
        } else if(soma > valor) {
            Double diferenca = soma - valor;
            saldo = 0.0;
            chequeEspecial = diferenca;
        } else
        throw new SaldoInsuficienteException();
    }
}
