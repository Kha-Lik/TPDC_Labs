import Common.DoubleSquareMatrix;
import Common.IMatrixMultiplier;
import Common.IntegerSquareMatrix;
import Fox.FoxMultiplier;
import Serial.SerialMultiplier;
import Striped.StripedMultiplier;

import java.util.Arrays;

public class MatrixMultiplication {

    public static void main(String[] args) {
        runCommonTest(false, 1000);
        //runOverallBenchmark();
    }

    private static void runCommonTest(boolean printMatrices, int size){
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.printf("Available processors: %d", availableProcessors);
        System.out.println();

        System.out.printf("Matrix size: %d", size);

        DoubleSquareMatrix firstMatrix = new DoubleSquareMatrix(size);
        DoubleSquareMatrix secondMatrix = new DoubleSquareMatrix(size);
        firstMatrix.fillWithRandomNumbers();
        secondMatrix.fillWithRandomNumbers();

        IntegerSquareMatrix firstIntMatrix = new IntegerSquareMatrix(size);
        IntegerSquareMatrix secondIntMatrix = new IntegerSquareMatrix(size);
        firstIntMatrix.fillWithRandomNumbers();
        secondIntMatrix.fillWithRandomNumbers();

        IMatrixMultiplier stripedMultiplier = new StripedMultiplier(availableProcessors);
        IMatrixMultiplier foxMultiplier = new FoxMultiplier(availableProcessors);
        IMatrixMultiplier serialMultiplier = new SerialMultiplier();

        var currTime = System.nanoTime();
        var resultDouble = serialMultiplier.multiplyDouble(firstMatrix, secondMatrix);
        currTime = System.nanoTime() - currTime;
        System.out.printf("Time elapsed for serial: %d ms\n", currTime / 1_000_000);

        currTime = System.nanoTime();
        resultDouble = stripedMultiplier.multiplyDouble(firstMatrix, secondMatrix);
        currTime = System.nanoTime() - currTime;
        System.out.printf("Time elapsed for SA: %d ms\n", currTime / 1_000_000);

        currTime = System.nanoTime();
        resultDouble = foxMultiplier.multiplyDouble(firstMatrix, secondMatrix);
        currTime = System.nanoTime() - currTime;
        System.out.printf("Time elapsed for FA: %d ms\n", currTime / 1_000_000);

        if(printMatrices){
            System.out.println("\nFirst double matrix:");
            firstMatrix.print();
            System.out.println("\nSecond double matrix:");
            secondMatrix.print();
            System.out.println("\nResult double matrix:");
            resultDouble.print();
        }

        currTime = System.nanoTime();
        var resultInt = serialMultiplier.multiplyInteger(firstIntMatrix, secondIntMatrix);
        currTime = System.nanoTime() - currTime;
        System.out.printf("Time elapsed for serial (int): %d ms\n", currTime / 1_000_000);

        currTime = System.nanoTime();
        resultInt = stripedMultiplier.multiplyInteger(firstIntMatrix, secondIntMatrix);
        currTime = System.nanoTime() - currTime;
        System.out.printf("Time elapsed for SA (int): %d ms\n", currTime / 1_000_000);

        currTime = System.nanoTime();
        resultInt = foxMultiplier.multiplyInteger(firstIntMatrix, secondIntMatrix);
        currTime = System.nanoTime() - currTime;
        System.out.printf("Time elapsed for FA (int): %d ms\n", currTime / 1_000_000);

        if(printMatrices){
            System.out.println("\nFirst int matrix:");
            firstIntMatrix.print();
            System.out.println("\nSecond int matrix:");
            secondIntMatrix.print();
            System.out.println("\nResult int matrix:");
            resultInt.print();
        }
    }

    private static void runOverallBenchmark(){
        var sizes = new int[]{500, 1000, 1500, 2000, 2500, 3000};
        var threadNums = new int[]{2, 4, 8};
        System.out.println("Size\t\t\tSerial time\t\t\tSA 2 threads\t\tFA 2 threads\t\tSA 4 threads\t\tFA 4 threads\t\tSA 8 threads\t\tFA 8 threads");
        for (var size: sizes){
            System.out.printf("%d\t\t\t\t", size);

            var firstMatrix = new DoubleSquareMatrix(size);
            var secondMatrix = new DoubleSquareMatrix(size);
            var serialMultiplier = new SerialMultiplier();

            System.out.printf("%d ms\t\t\t\t", getAverageTime(serialMultiplier, firstMatrix, secondMatrix));

            for (var nThread: threadNums){
                var stripedMultiplier = new StripedMultiplier(nThread);
                var foxMultiplier = new FoxMultiplier(nThread);

                System.out.printf("%d ms\t\t\t\t", getAverageTime(stripedMultiplier, firstMatrix, secondMatrix));
                System.out.printf("%d ms\t\t\t\t", getAverageTime(foxMultiplier, firstMatrix, secondMatrix));
            }
            System.out.println();
        }
    }

    private static long getAverageTime(IMatrixMultiplier multiplier, DoubleSquareMatrix firstMatrix, DoubleSquareMatrix secondMatrix){
        var execTimes = new long[5];
        for (int i = 0; i < 5; i++) {
            var currTime = System.nanoTime();
            multiplier.multiplyDouble(firstMatrix, secondMatrix);
            execTimes[i] = System.nanoTime() - currTime;
        }

        return Arrays.stream(execTimes).sum()/5/1_000_000;
    }
}
