package Common;

public interface IMatrixMultiplier {
    IntegerSquareMatrix multiplyInteger(IntegerSquareMatrix firstMatrix, IntegerSquareMatrix secondMatrix);

    DoubleSquareMatrix multiplyDouble(DoubleSquareMatrix firstMatrix, DoubleSquareMatrix secondMatrix);
}
