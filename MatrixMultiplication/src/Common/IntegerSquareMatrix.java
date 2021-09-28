package Common;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class IntegerSquareMatrix {
    private int[][] _matrix;
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

    public void transpose(){
        int[][] newMatrix = new int[size][size];
        for (int i = 0; i < _matrix[0].length; i++) {
            for (int j = 0; j < _matrix.length; j++) {
                newMatrix[i][j] = _matrix[j][i];
            }
        }
        _matrix = newMatrix;
    }

    public int[] getRow(int rowIndex){
        return _matrix[rowIndex];
    }

    public int[] getCol(int colIndex){
        return Arrays.stream(_matrix).mapToInt(nums -> nums[colIndex]).toArray();
    }

    public void print(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.printf("%d", _matrix[i][j]);
            }
            System.out.println();
        }
    }

    public void plusAssign(int row, int col, int addition){
        _matrix[row][col] += addition;
    }
}
