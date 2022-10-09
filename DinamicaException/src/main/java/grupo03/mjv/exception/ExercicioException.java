package grupo03.mjv.exception;

public class ExercicioException {

    public static void main(String[] args) {

        try {
            System.out.println( ExercicioException.resultado(5, 8));
        }catch (IllegalArgumentException e ){
            System.out.println("O primeiro número não pode ser maior");
        }

    }

    private static Integer resultado(Integer primeiroNumero, Integer segundoNumero) {
            if (primeiroNumero == 0) {
                throw  new IllegalArgumentException();
            } if(segundoNumero > 10){
                throw new IllegalArgumentException();
            }
        return Math.max(primeiroNumero,segundoNumero) - Math.min(primeiroNumero,segundoNumero);
    }
}
