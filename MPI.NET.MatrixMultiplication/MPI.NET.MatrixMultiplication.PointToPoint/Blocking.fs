#light 
module Blocking

open MPI
open System
open MPI.NET.MatrixMultiplication.Common

let startBlocking (env: MPI.Environment, world: Intracommunicator, matrixARows, matrixACols, matrixBCols, printResult) =
    if world.Rank = 0 then
        if world.Size < 2 then
            printfn "You need to run this program on more than one processor"
            0
        else
            let rnd = Random()
            let numWorkers = world.Size - 1
            let matrixA = Array.init matrixARows (fun _ -> Array.init matrixACols (fun _ -> rnd.NextDouble()))
            let matrixB = Array.init matrixACols (fun _ -> Array.init matrixBCols (fun _ -> rnd.NextDouble()))
            let averageRows = matrixARows / numWorkers
            let extraRows = matrixARows % numWorkers

            let mutable matrixC = Array.init matrixARows (fun _ -> Array.init matrixBCols (fun _ -> 0.0))
            let mutable offset = 0

            let stopwatch = Diagnostics.Stopwatch.StartNew();
            
            for dest in 1..numWorkers do
                let rows = if (dest <= extraRows) then averageRows + 1 else averageRows
                let matrixToSend = Array.sub matrixA offset rows 
                let sendModel = MatrixSendModel(offset, matrixToSend, matrixB)
                world.Send(sendModel, dest, 0)
                offset <- offset + rows

            for source in 1..numWorkers do
                let resultModel = world.Receive<MultiplyResultModel>(source, 0)
                for i in 0..resultModel.Result.Length-1 do
                        matrixC[i+resultModel.Offset] <- resultModel.Result[i]
            
            stopwatch.Stop()

            printfn "Time elapsed to multiply matrices A(%dx%d) and B(%dx%d) with %d workers: %dms" matrixARows matrixACols matrixACols matrixBCols numWorkers stopwatch.ElapsedMilliseconds
            
            if printResult then
                printf "****\n"
                printf "Result Matrix:\n"
                for i in 0..matrixARows-1 do
                    printf "\n"
                    for j in 0..matrixBCols-1 do
                        printf "%6.2f" matrixC[i].[j]
                printf "\n********\n"
                printf "Done.\n"
            0

    else
        try
            let sendModel = world.Receive<MatrixSendModel>(0, 0)
            let mutable matrixToSend = Array.init sendModel.FirstMatrixRows.Length (fun _ -> Array.init matrixBCols (fun _ -> 0.0))

            for k in 0..matrixBCols-1 do
                for i in 0..sendModel.FirstMatrixRows.Length-1 do
                    for j in 0..matrixACols-1 do
                        matrixToSend[i][k] <- matrixToSend[i][k] + sendModel.FirstMatrixRows[i][j] * sendModel.SecondMatrix[j][k]

            let resultModel = MultiplyResultModel(sendModel.Offset, matrixToSend)
            world.Send(resultModel, 0, 0)
        with
            | :? (Exception) as ex ->printfn "%s" ex.Message 
        0
    |> ignore