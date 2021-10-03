package Common;

import java.util.Arrays;
import java.util.Random;

public class DoubleSquareMatrix {
    private double[][] _matrix;
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

    public void fillWithRandomNumbers(){
        Random rng = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                _matrix[i][j] = rng.nextDouble();
            }
        }
    }

    public void transpose(){
        double[][] newMatrix = new double[size][size];
        for (int i = 0; i < _matrix.length; i++) {
            for (int j = 0; j < _matrix.length; j++) {
                newMatrix[i][j] = _matrix[j][i];
            }
        }
        _matrix = newMatrix;
    }

    public double[] getRow(int rowIndex){
        return _matrix[rowIndex];
    }

    public double[] getCol(int colIndex){
        return Arrays.stream(_matrix).mapToDouble(nums -> nums[colIndex]).toArray();
    }

    public void print(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.printf("%10.2f", _matrix[i][j]);
            }
            System.out.println();
        }
    }

    public void plusAssign(int row, int col, double addition){
        _matrix[row][col] += addition;
    }
}
