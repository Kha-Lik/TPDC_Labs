package Fox;

import Common.DoubleSquareMatrix;

public class FoxThreadDouble extends Thread {
    private final DoubleSquareMatrix _firstMatrix;
    private final DoubleSquareMatrix _secondMatrix;
    private final DoubleSquareMatrix _resultMatrix;

    private final int _stepI;
    private final int _stepJ;

    public FoxThreadDouble(
            DoubleSquareMatrix firstMatrix,
            DoubleSquareMatrix secondMatrix,
            DoubleSquareMatrix resultMatrix,
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
        DoubleSquareMatrix blockRes = multiplyBlock();

        for (int i = 0; i < blockRes.size; i++) {
            for (int j = 0; j < blockRes.size; j++) {
                _resultMatrix.plusAssign(i + _stepI, j + _stepJ, blockRes.getValue(i, j));
            }
        }
    }

    private DoubleSquareMatrix multiplyBlock() {
        DoubleSquareMatrix blockRes = new DoubleSquareMatrix(_firstMatrix.size);
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
