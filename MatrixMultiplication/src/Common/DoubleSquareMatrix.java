package Common;

import java.util.Random;

public class DoubleSquareMatrix {
    private final double[][] _matrix;
    public final int size;

    public DoubleSquareMatrix(int size) {
        this.size = size;
        _matrix = new double[size][size];
    }

    public void setValue(int row, int col, double value){
        _matrix[row][col] = value;
    }

    public double getValue(int row, int col){
        return _matrix[row][col];
    }

    public void fillWithRandomNumber(){
        Random rng = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                _matrix[i][j] = rng.nextDouble();
            }
        }
    }
}
