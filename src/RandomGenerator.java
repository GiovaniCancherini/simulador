import java.util.Random;

public class RandomGenerator {

    public static double[] MetodoCongruenteLinear(int semente, int a, int c, int m, int tamanho) {
        double[] result = new double[tamanho];
        result[0] = (double) semente / m;
        int lastInt = semente;
        for (int i = 1; i < result.length; i++) {
            lastInt = (lastInt * a + c) % m;
            result[i] = (double) lastInt / m;
        }
        return result;
    }

    public static int[] ExemploAula(int tamanho) {
        int[] result = new int[tamanho];
        result[0] = 7;
        for (int i = 1; i < result.length; i++) {
            result[i] = (result[i - 1] * 4 + 4) % 9;
        }
        return result;
    }

    public static double[] NumericalRecips(int tamanho) {
        long seed = System.currentTimeMillis();
        return MetodoCongruenteLinear((int) seed, 1664525, 1013904223, (int) Math.pow(2, 32), tamanho);
    }

    public static double[] Glibc(int tamanho) {
        long seed = System.currentTimeMillis();
        return MetodoCongruenteLinear((int) seed, 1103515245, 12345, (int) Math.pow(2, 32), tamanho);
    }

    public static double[] MicrosoftVB(int tamanho) {
        long seed = System.currentTimeMillis();
        return MetodoCongruenteLinear((int) seed, 1140671485, 12820163, (int) Math.pow(2, 24), tamanho);
    }

    public static double[] JavaUtilRandom(int tamanho) {
        Random random = new Random();
        return MetodoCongruenteLinear(random.nextInt(), 25214903917L, 11, (int) Math.pow(2, 48), tamanho);
    }

    public static double[] JavaUtilRandomSeed(int tamanho, int semente) {
        return MetodoCongruenteLinear(semente, 25214903917L, 11, (int) Math.pow(2, 48), tamanho);
    }
}
