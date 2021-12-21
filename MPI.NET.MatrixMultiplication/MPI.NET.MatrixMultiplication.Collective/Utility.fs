namespace MPI.NET.MatrixMultiplication.Collective

module Utility =

    let mutable matrixARows = 400
    let mutable matrixACols = 600
    let mutable matrixBCols = 400
    
    let multiply (matrixA:float[][]) (matrixB:float[][]) =
        let mutable result = Array.init matrixA.Length (fun _ -> Array.init matrixBCols (fun _ -> 0.0))
        for k in 0..matrixBCols-1 do
            for i in 0..matrixA.Length-1 do
                for j in 0..matrixACols-1 do
                    result[i][k] <- result[i][k] + matrixA[i][j] * matrixB[j][k]
        result    


    