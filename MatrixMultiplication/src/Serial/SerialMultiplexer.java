package Serial;

public class SerialMultiplexer {
    public static double[][] multiplyDouble(double[][] firstMatrix, double[][] secondMatrix){
        double[][] resultMatrix = new double[firstMatrix.length][secondMatrix[0].length];
        for (int row = 0; row < firstMatrix.length; row++) {
            for (int col = 0; col < secondMatrix[0].length; col++) {
                resultMatrix[row][col] = computeDoubleMatrixCell(firstMatrix, secondMatrix, row, col);
            }
        }
        return resultMatrix;
    }

    private static double computeDoubleMatrixCell(double[][] firstMatrix, double[][] secondMatrix, int row, int col){
        double cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }

    public static int[][] multiplyInteger(int[][] firstMatrix, int[][] secondMatrix){
        int[][] resultMatrix = new int[firstMatrix.length][secondMatrix[0].length];
        for (int row = 0; row < firstMatrix.length; row++) {
            for (int col = 0; col < secondMatrix[0].length; col++) {
                resultMatrix[row][col] = computeIntegerMatrixCell(firstMatrix, secondMatrix, row, col);
            }
        }
        return resultMatrix;
    }

    private static int computeIntegerMatrixCell(int[][] firstMatrix, int[][] secondMatrix, int row, int col){
        int cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }
}
