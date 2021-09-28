package Serial;

import Common.DoubleSquareMatrix;
import Common.IMatrixMultiplier;
import Common.IntegerSquareMatrix;

public class SerialMultiplier implements IMatrixMultiplier {
    public DoubleSquareMatrix multiplyDouble(DoubleSquareMatrix firstMatrix, DoubleSquareMatrix secondMatrix){
        if (firstMatrix.size != secondMatrix.size)
            throw new IllegalArgumentException("Matrices must be same size");

        DoubleSquareMatrix resultMatrix = new DoubleSquareMatrix(firstMatrix.size);
        for (int row = 0; row < firstMatrix.size; row++) {
            for (int col = 0; col < secondMatrix.size; col++) {
                var value = computeDoubleMatrixCell(firstMatrix, secondMatrix, row, col);
                resultMatrix.setValue(row, col, value);
            }
        }
        return resultMatrix;
    }

    private static double computeDoubleMatrixCell(DoubleSquareMatrix firstMatrix, DoubleSquareMatrix secondMatrix, int row, int col){
        double cell = 0;
        for (int i = 0; i < secondMatrix.size; i++) {
            cell += firstMatrix.getValue(row, i) * secondMatrix.getValue(i, col);
        }
        return cell;
    }

    public IntegerSquareMatrix multiplyInteger(IntegerSquareMatrix firstMatrix, IntegerSquareMatrix secondMatrix){
        if (firstMatrix.size != secondMatrix.size)
            throw new IllegalArgumentException("Matrices must be same size");

        IntegerSquareMatrix resultMatrix = new IntegerSquareMatrix(firstMatrix.size);
        for (int row = 0; row < firstMatrix.size; row++) {
            for (int col = 0; col < secondMatrix.size; col++) {
                var value = computeIntegerMatrixCell(firstMatrix, secondMatrix, row, col);
                resultMatrix.setValue(row, col, value);
            }
        }
        return resultMatrix;
    }

    private static int computeIntegerMatrixCell(IntegerSquareMatrix firstMatrix, IntegerSquareMatrix secondMatrix, int row, int col){
        int cell = 0;
        for (int i = 0; i < secondMatrix.size; i++) {
            cell += firstMatrix.getValue(row, i) * secondMatrix.getValue(i, col);
        }
        return cell;
    }
}
