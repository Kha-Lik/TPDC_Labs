package Matrices;

import Matrices.Common.Matrix;
import Matrices.Serial.SerialAlgorithm;
import Matrices.Fox.FoxAlgorithm;
import java.util.concurrent.ForkJoinPool;

public class Matrices {
  public static void main(String[] args) {
    simpleRun(false);
  }

  public static void simpleRun(boolean printMatrices) {
    int sizeAxis0 = 2000;
    int sizeAxis1 = 2000;

    Matrix A = new Matrix(sizeAxis0, sizeAxis1);
    Matrix B = new Matrix(sizeAxis0, sizeAxis1);

    A.generateRandomMatrix();
    B.generateRandomMatrix();

    int nThread = Runtime.getRuntime().availableProcessors();

    SerialAlgorithm ba = new SerialAlgorithm(A, B);

    long currTimeBasic = System.nanoTime();
    Matrix C = ba.multiply();
    currTimeBasic = System.nanoTime() - currTimeBasic;

    if (printMatrices) C.print();

    System.out.println("Time for Basic Algorithm: " + currTimeBasic / 1_000_000);

    long currTimeFox = System.nanoTime();
    ForkJoinPool forkJoinPool = new ForkJoinPool(nThread);
    C = forkJoinPool.invoke(new FoxAlgorithm(A, B, 6));
    currTimeFox = System.nanoTime() - currTimeFox;

    if (printMatrices) C.print();

    System.out.println("Time for Fox Algorithm: " + currTimeFox / 1_000_000);
    System.out.println();
    System.out.println(
        "SpeedUp (timeBasic / timeFoxForkJoin): " + (double) currTimeBasic / currTimeFox);

    /*
    5550 - this is average execution time for Fox algorithm (from assignment 2)
     */
    System.out.println(
        "SpeedUp (timeFox / timeFoxForkJoin): " + (double) 5550 / (currTimeFox / 1_000_000));
  }
}
