package grupo03.mjv.exception;

public class ExercicioException {

    public static void main(String[] args) {
        Integer primeiroNumero = null;
        Integer segundoNumero = null;
        Integer resultado = null;

        resultado = ExercicioException.resultado(primeiroNumero, segundoNumero);

        System.out.println(resultado);
    }

    private static Integer resultado(Integer primeiroNumero, Integer segundoNumero) {
        int soma = 0;
        try {
            if (primeiroNumero >= 1 && segundoNumero > 10) {
                soma = primeiroNumero + segundoNumero;
            } else {
                throw  new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("O primeiro numero não pode ser zero e o segundo não pode ser maior que 10");
        }
        return soma;
    }
}
