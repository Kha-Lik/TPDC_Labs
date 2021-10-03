package Fox;

import Common.IntegerSquareMatrix;

public class FoxThreadInteger extends Thread {
    private final IntegerSquareMatrix _firstMatrix;
    private final IntegerSquareMatrix _secondMatrix;
    private final IntegerSquareMatrix _resultMatrix;

    private final int _stepI;
    private final int _stepJ;

    public FoxThreadInteger(
            IntegerSquareMatrix firstMatrix,
            IntegerSquareMatrix secondMatrix,
            IntegerSquareMatrix resultMatrix,
            int stepI,
            int stepJ) {
        _firstMatrix = firstMatrix;
        _secondMatrix = secondMatrix;
        _resultMatrix = resultMatrix;
        _stepI = stepI;
        _stepJ = stepJ;
    }

    @Override
    public void run() {
        IntegerSquareMatrix blockRes = multiplyBlock();

        for (int i = 0; i < blockRes.size; i++) {
            for (int j = 0; j < blockRes.size; j++) {
                _resultMatrix.plusAssign(i + _stepI, j + _stepJ, blockRes.getValue(i, j));
            }
        }
    }

    private IntegerSquareMatrix multiplyBlock() {
        IntegerSquareMatrix blockRes = new IntegerSquareMatrix(_firstMatrix.size);
        for (int i = 0; i < _firstMatrix.size; i++) {
            for (int j = 0; j < _secondMatrix.size; j++) {
                for (int k = 0; k < _firstMatrix.size; k++) {
                    blockRes.plusAssign(i, j, _firstMatrix.getValue(i, k) * _secondMatrix.getValue(k, j));
                }
            }
        }
        return blockRes;
    }
}
