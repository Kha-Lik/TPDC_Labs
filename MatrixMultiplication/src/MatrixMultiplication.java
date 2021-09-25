import Serial.SerialMultiplexer;

import java.util.Arrays;
import java.util.Random;

public class MatrixMultiplication {
    private static final int size = 1000;

    private static final double[][] firstDoubleMatrix = new double[size][size];
    private static final double[][] secondDoubleMatrix = new double[size][size];
    private static final int[][] firstIntMatrix = new int[size][size];
    private static final int[][] secondIntMatrix = new int[size][size];
    private static final Random rng = new Random();

    static {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                firstDoubleMatrix[i][j] = rng.nextDouble();
                secondDoubleMatrix[i][j] = rng.nextDouble();
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                firstIntMatrix[i][j] = rng.nextInt();
                secondIntMatrix[i][j] = rng.nextInt();
            }
        }
    }

    public static void main(String[] args) {
        var times = new long[5];
    }
}
