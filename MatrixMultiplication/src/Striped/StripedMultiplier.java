package Striped;

import Common.DoubleSquareMatrix;
import Common.IMatrixMultiplier;
import Common.IntegerSquareMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class StripedMultiplier implements IMatrixMultiplier {
    
    private final int _nThread;

    public StripedMultiplier(int nThread) {
        _nThread = nThread;
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

        resultMatrix = privateMultiplyDouble(firstMatrix, secondMatrix, resultMatrix);

        return resultMatrix;
    }

    private DoubleSquareMatrix privateMultiplyDouble(DoubleSquareMatrix firstMatrix, DoubleSquareMatrix secondMatrix, DoubleSquareMatrix resultMatrix) {
        secondMatrix.transpose();

        ExecutorService executor = Executors.newFixedThreadPool(this._nThread);

        List<Future<Map<String, Number>>> list = new ArrayList<>();

        for (int j = 0; j < secondMatrix.size; j++) {
            for (int i = 0; i < firstMatrix.size; i++) {
                Callable<Map<String, Number>> worker =
                        new StripedCallable(firstMatrix.getRow(i), i, secondMatrix.getRow(j), j);
                Future<Map<String, Number>> submit = executor.submit(worker);
                list.add(submit);
            }
        }

        for (Future<Map<String, Number>> mapFuture : list) {
            try {
                Map<String, Number> res = mapFuture.get();
                var row = (int) res.get("rowIndex");
                var col = (int) res.get("columnIndex");
                var value = (double) res.get("value");
                resultMatrix.setValue(row, col, value);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        secondMatrix.transpose();

        return resultMatrix;
    }
}
