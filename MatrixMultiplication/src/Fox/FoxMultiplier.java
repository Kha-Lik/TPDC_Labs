package Fox;

import Common.DoubleSquareMatrix;
import Common.IMatrixMultiplier;
import Common.IntegerSquareMatrix;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FoxMultiplier implements IMatrixMultiplier {
    private int _threadNumber;

    public FoxMultiplier(int threadNumber) {
        _threadNumber = threadNumber;
    }

    @Override
    public IntegerSquareMatrix multiplyInteger(IntegerSquareMatrix firstMatrix, IntegerSquareMatrix secondMatrix) {
        return null;
    }

    @Override
    public DoubleSquareMatrix multiplyDouble(DoubleSquareMatrix firstMatrix, DoubleSquareMatrix secondMatrix) {
        if (firstMatrix.size != secondMatrix.size)
            throw new IllegalArgumentException("Matrices must be same size");

        DoubleSquareMatrix resultMatrix = new DoubleSquareMatrix(firstMatrix.size);

        _threadNumber = Math.min(_threadNumber, firstMatrix.size);
        _threadNumber = findNearestDivider(_threadNumber, firstMatrix.size);
        int step = firstMatrix.size / _threadNumber;

        ExecutorService exec = Executors.newFixedThreadPool(_threadNumber);
        ArrayList<Future> threads = new ArrayList<>();

        int[][] matrixOfSizesI = new int[_threadNumber][_threadNumber];
        int[][] matrixOfSizesJ = new int[_threadNumber][_threadNumber];

        int stepI = 0;
        for (int i = 0; i < _threadNumber; i++) {
            int stepJ = 0;
            for (int j = 0; j < _threadNumber; j++) {
                matrixOfSizesI[i][j] = stepI;
                matrixOfSizesJ[i][j] = stepJ;
                stepJ += step;
            }
            stepI += step;
        }

        for (int l = 0; l < _threadNumber; l++) {
            for (int i = 0; i < _threadNumber; i++) {
                for (int j = 0; j < _threadNumber; j++) {
                    int stepI0 = matrixOfSizesI[i][j];
                    int stepJ0 = matrixOfSizesJ[i][j];

                    int stepI1 = matrixOfSizesI[i][(i + l) % _threadNumber];
                    int stepJ1 = matrixOfSizesJ[i][(i + l) % _threadNumber];

                    int stepI2 = matrixOfSizesI[(i + l) % _threadNumber][j];
                    int stepJ2 = matrixOfSizesJ[(i + l) % _threadNumber][j];

                    FoxThreadDouble t = new FoxThreadDouble(
                            copyDoubleBlock(firstMatrix, stepI1, stepJ1, step),
                            copyDoubleBlock(secondMatrix, stepI2, stepJ2, step),
                            resultMatrix,
                            stepI0,
                            stepJ0);
                    threads.add(exec.submit(t));
                }
            }
        }

        for (Future mapFuture : threads) {
            try {
                mapFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        exec.shutdown();

        return resultMatrix;
    }

    private DoubleSquareMatrix copyDoubleBlock(DoubleSquareMatrix matrix, int i, int j, int size) {
        DoubleSquareMatrix block = new DoubleSquareMatrix(size);
        for (int k = 0; k < size; k++) {
            System.arraycopy(matrix.getRow(k + i), j, block.getRow(k), 0, size);
        }
        return block;
    }

    private int findNearestDivider(int s, int p) {
    /*
    https://ru.stackoverflow.com/questions/434403/%D0%9F%D0%BE%D0%B8%D1%81%D0%BA-%D0%B1%D0%BB%D0%B8%D0%B6%D0%B0%D0%B9%D1%88%D0%B5%D0%B3%D0%BE-%D0%B4%D0%B5%D0%BB%D0%B8%D1%82%D0%B5%D0%BB%D1%8F
     */
        int i = s;
        while (i > 1) {
            if (p % i == 0) break;
            if (i >= s) {
                i++;
            } else {
                i--;
            }
            if (i > Math.sqrt(p)) i = Math.min(s, p / s) - 1;
        }

        return i >= s ? i : i != 0 ? p / i : p;
    }
}
