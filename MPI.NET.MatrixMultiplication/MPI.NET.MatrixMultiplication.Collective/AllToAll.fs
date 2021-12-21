namespace MPI.NET.MatrixMultiplication.Collective

module AllToAll =
    
    open MPI
    open System
    open MPI.NET.MatrixMultiplication.Collective.Utility
    
    let startAllToAll (env: MPI.Environment, world: Intracommunicator) =
        let rnd = Random()
        let stopwatch = Diagnostics.Stopwatch()
        let rowsPerProcess = matrixARows / world.Size
        let firstSubMatrix = Array.init rowsPerProcess (fun _ -> Array.init matrixACols (fun _ -> rnd.NextDouble()))
        let secondSubMatrix = Array.init rowsPerProcess (fun _ -> Array.init matrixBCols (fun _ -> rnd.NextDouble()))
        
        if world.Rank = 0 then stopwatch.Start()
        
        let matrixB = world.AllgatherFlattened(secondSubMatrix, secondSubMatrix.Length)
        let resultBlock = multiply firstSubMatrix matrixB
        let result = world.GatherFlattened(resultBlock, 0)
        
        if world.Rank = 0 then
            stopwatch.Stop()
            printfn $"Time elapsed to multiply matrices A(%d{matrixARows}x%d{matrixACols}) and B(%d{matrixACols}x%d{matrixBCols}) with %d{world.Size} workers: %d{stopwatch.ElapsedMilliseconds}ms"          

