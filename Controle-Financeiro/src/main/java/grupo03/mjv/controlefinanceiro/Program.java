package grupo03.mjv.controlefinanceiro;

import Enums.EnumJustificativa;
import Model.Cliente;
import Model.ContaBancaria;
import Model.ContaCorrente;
import Model.ContaPoupanca;
import exception.*;
import utils.Operacao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataDeNascimento;
        //  ContaBancaria cb = new ContaBancaria(1111, 1111, "jose", dataDeNascimento = sdf.parse("10/03/1990"));

        Cliente cliente = new Cliente("jose", "444.333.555-66");
        ContaCorrente cb = new ContaCorrente(1111, 1111, cliente);
        ContaPoupanca c1 = new ContaPoupanca(3333, 1111, cliente);
        //realizando um deposito

        LocalDate periodoInicial = LocalDate.now();
        LocalDate periodoFinal = periodoInicial.plusMonths(1);


        try {
            cb.depositar(5000.0);
            cb.sacar(50.0);
            // cb.transferir(500.0, c1);

            //System.out.println(sdf.format(cb.getDataDeNascimento()));
            // cb.transferir(500.0, b);
            cb.consultarSaldo(periodoInicial, periodoFinal);
            cb.cancelarConta(cb, EnumJustificativa.INSATISFEITO, "Estou bravo com o banco !");
            cb.cancelarConta(cb, EnumJustificativa.INSATISFEITO, "Estou bravo com o banco !");

        }

//        } catch (TransferenciaDestinoNuloException e) {
//            e.printStackTrace();
//        }

        catch (SaldoInsuficienteException e) {
            e.printStackTrace();
        } catch (DataInvalidaException e) {
            throw new RuntimeException(e);
        } catch (CancelarContaExpetion e) {
            throw new RuntimeException(e);
        } catch (ValorInsuficienteException e) {
            throw new RuntimeException(e);
        }
    }
}
//transferindo para a mesma conta
//cb.transferir(500.0, cb);
//caminho feliz
// cb.transferir(500.1, c1);

//realizando um saque maior que o em conta
// cb.sacar(5001.0);
