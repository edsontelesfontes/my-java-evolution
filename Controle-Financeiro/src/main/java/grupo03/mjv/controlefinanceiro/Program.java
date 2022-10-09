package grupo03.mjv.controlefinanceiro;

import exception.DataInvalidaException;
import exception.SaldoInsuficienteException;
import exception.TransferenciaDestinoNuloException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Program {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataDeNascimento;
        //  ContaBancaria cb = new ContaBancaria(1111, 1111, "jose", dataDeNascimento = sdf.parse("10/03/1990"));
        ContaBancaria cb = new ContaBancaria(1111, 1111, "jose");
        ContaBancaria c1 = new ContaBancaria(3333, 1111, "Maria");
        ContaBancaria b = new ContaBancaria();
        //realizando um deposito

        LocalDate periodoInicial = LocalDate.now();
        LocalDate periodoFinal = periodoInicial.plusMonths(1);


        try {
            cb.depositar(5000.0);
            cb.sacar(0.0);
            // cb.transferir(500.0, c1);

            //System.out.println(sdf.format(cb.getDataDeNascimento()));
            // cb.transferir(500.0, b);
            cb.consultarSaldo(periodoInicial, periodoFinal);
        }

//        } catch (TransferenciaDestinoNuloException e) {
//            e.printStackTrace();
//        }

        catch (SaldoInsuficienteException e) {
            e.printStackTrace();
        } catch (DataInvalidaException e) {
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
