import Common.DoubleSquareMatrix;
import Common.IMatrixMultiplier;
import Fox.FoxMultiplier;
import Serial.SerialMultiplier;
import Striped.StripedMultiplier;

public class MatrixMultiplication {
    private static final int size = 2000;

    public static void main(String[] args) {
        DoubleSquareMatrix firstMatrix = new DoubleSquareMatrix(size);
        DoubleSquareMatrix secondMatrix = new DoubleSquareMatrix(size);
        firstMatrix.fillWithRandomNumber();
        secondMatrix.fillWithRandomNumber();

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.printf("Available processors: %d", availableProcessors);
        System.out.println();

        IMatrixMultiplier stripedMultiplier = new StripedMultiplier(availableProcessors);
        IMatrixMultiplier foxMultiplier = new FoxMultiplier(availableProcessors);
        IMatrixMultiplier serialMultiplier = new SerialMultiplier();

        var currTime = System.nanoTime();
        var result = stripedMultiplier.multiplyDouble(firstMatrix, secondMatrix);
        currTime = System.nanoTime() - currTime;
        System.out.printf("Time elapsed for SA: %d ms\n", currTime / 1_000_000);

        currTime = System.nanoTime();
        result = foxMultiplier.multiplyDouble(firstMatrix, secondMatrix);
        currTime = System.nanoTime() - currTime;
        System.out.printf("Time elapsed for FA: %d ms\n", currTime / 1_000_000);

        currTime = System.nanoTime();
        result = serialMultiplier.multiplyDouble(firstMatrix, secondMatrix);
        currTime = System.nanoTime() - currTime;
        System.out.printf("Time elapsed for serial: %d ms\n", currTime / 1_000_000);
    }
}
