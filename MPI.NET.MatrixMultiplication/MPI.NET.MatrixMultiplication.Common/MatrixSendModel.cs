namespace MPI.NET.MatrixMultiplication.Common
{
    [Serializable]
    public class MatrixSendModel
    {
        public int Offset { get; set; }
        public double[][] FirstMatrixRows { get; set; }
        public double[][] SecondMatrix { get; set; }

        public MatrixSendModel(int offset, double[][] firstMatrixRows, double[][] secondMatrix)
        {
            FirstMatrixRows = firstMatrixRows;
            SecondMatrix = secondMatrix;
            Offset = offset;
        }
    }
}
