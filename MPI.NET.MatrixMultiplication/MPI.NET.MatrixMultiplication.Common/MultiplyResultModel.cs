namespace MPI.NET.MatrixMultiplication.Common
{
    [Serializable]
    public class MultiplyResultModel
    {
        public int Offset { get; set; }
        public double[][] Result { get; set; }

        public MultiplyResultModel(int offset, double[][] result)
        {
            Offset = offset;
            Result = result;
        }
    }
}
