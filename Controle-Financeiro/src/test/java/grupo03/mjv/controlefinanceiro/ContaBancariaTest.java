package grupo03.mjv.controlefinanceiro;

import exception.CancelarContaExpetion;
import exception.DataInvalidaException;
import exception.SaldoInsuficienteException;
import exception.TransferenciaDestinoNuloException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Extrato;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ContaBancariaTest {
    ContaBancaria contaBancaria;
    ContaBancaria contaBancariaDestino;
    ContaBancaria contaBancariaNula;

    ContaBancaria contaBancariaFalse;


    @BeforeEach
    public void setUP(){
        contaBancaria = new ContaBancaria(1111, 1111, "jose");
        contaBancariaDestino = new ContaBancaria(3333, 1111, "Maria");
        contaBancariaNula = null;
        contaBancariaFalse = new ContaBancaria(1111, 1111, "jose");
    }

    @DisplayName("Teste sacar com um valor maior que o saldo")
    @Test
    void quando_DeveriaDarErroAo_Sacar() {
        contaBancaria.depositar(5000.0);
        Assertions.assertThrows(SaldoInsuficienteException.class, () -> contaBancaria.sacar(5001.0));
    }

    @DisplayName("Teste sacar quando um valor é o suficiente no saldo")
    @Test
    void quando_DeveriaDarCertoAo_Sacar() {
        Double expectativa = 4950.0;
        try {
        contaBancaria.depositar(5000.0);
        contaBancaria.sacar(50.0);
        } catch (SaldoInsuficienteException e) {
            throw new RuntimeException(e);
        }
        Double resultado = contaBancaria.getSaldo();
        Assertions.assertEquals(expectativa, resultado);
    }

    @DisplayName("Teste se o valor depositar for menor ou igual a zero.")
    @Test
    void valorDepositado_DeveDarException_aoDepositarValorMenorOuIgual_a_Zero() {
        assertThrows(IllegalArgumentException.class, () -> contaBancaria.depositar(0.0));
    }

    @DisplayName("Teste se o valor depositar for maior que zero.")
    @Test
    void realizarDeposito_aoInformarValorMaiorQueZero() {
        Double resultado = 5000.0;
        contaBancaria.depositar(5000.0);
        assertEquals(resultado, contaBancaria.getSaldo());
    }

    @DisplayName("Teste transferir")
    @Test
    void realizarTransferencia_SeContaEValores_ForemCorretos() {
        Double expectativa = 5000.0;

        try {
            contaBancaria.transferir(5000.0, contaBancariaDestino);
        } catch (TransferenciaDestinoNuloException e) {
            throw new RuntimeException(e);
        } catch (SaldoInsuficienteException e) {
            throw new RuntimeException(e);
        }

        assertEquals(expectativa, contaBancariaDestino.getSaldo());
    }
    @Test
    void naoTransferir_LancarException_ContanulaEValorIgualOuInferiorZero() {
        assertThrows(TransferenciaDestinoNuloException.class, () -> contaBancaria.transferir(5000.0, contaBancariaNula));
        assertThrows(SaldoInsuficienteException.class, () -> contaBancaria.transferir(0.0, contaBancariaDestino));

    }

    @DisplayName("Teste cancelar")
    @Test
    void cancelarConta_SeOsParametrosForemCorretos() {
        try {
            assertEquals(false, contaBancaria.cancelarConta(contaBancaria, "Inatividade"));
        } catch (CancelarContaExpetion e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("Teste cancelar")
    @Test
    void naoCancelarConta_eLancarExpetion_FaltaDeJustificativa() {
        assertThrows(IllegalArgumentException.class, () -> contaBancaria.cancelarConta(contaBancaria, ""));


        try {
            contaBancariaFalse.cancelarConta(contaBancariaFalse, "teste");
        } catch (CancelarContaExpetion e) {
            throw new RuntimeException(e);
        }

        //Testa a exception caso a conta já tenha sido cancelada.
        assertThrows(CancelarContaExpetion.class, () -> contaBancariaFalse.cancelarConta(contaBancariaFalse,"Teste"));

    }

    @DisplayName("Teste consultar saldo")
    @Test
    void consultarSaldo() {

        try {
            contaBancaria.depositar(5000.0);
            contaBancaria.sacar(500.0);
            contaBancaria.depositar(500.0);
           contaBancaria.transferir(500.0, contaBancariaDestino);
        } catch (SaldoInsuficienteException e) {
            throw new RuntimeException(e);
        }
        catch (TransferenciaDestinoNuloException e) {
            throw new RuntimeException(e);
        }
    Integer expectativa = 4;
    Integer resultado = contaBancaria.getExtrato().size();
    assertEquals(expectativa, resultado);
    }

    @DisplayName("Teste consultar saldo")
    @Test
    void consultarSaldo_LancarException() {
        LocalDate dataDeHoje = LocalDate.now();
        LocalDate dataInvalida = dataDeHoje.minusMonths(1);

        assertThrows(DataInvalidaException.class, () -> contaBancaria.consultarSaldo(dataDeHoje, dataInvalida));
    }

}