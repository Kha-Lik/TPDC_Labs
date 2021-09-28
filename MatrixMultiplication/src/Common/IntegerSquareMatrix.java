package Common;

import java.util.Random;

public class IntegerSquareMatrix {
    private final int[][] _matrix;
    public final int size;

    public IntegerSquareMatrix(int size) {
        this.size = size;
        _matrix = new int[size][size];
    }

    public void setValue(int row, int col, int value){
        _matrix[row][col] = value;
    }

    public int getValue(int row, int col){
        return _matrix[row][col];
    }

    public void fillWithRandomNumbers(){
        Random rng = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                _matrix[i][j] = rng.nextInt();
            }
        }
    }
}
