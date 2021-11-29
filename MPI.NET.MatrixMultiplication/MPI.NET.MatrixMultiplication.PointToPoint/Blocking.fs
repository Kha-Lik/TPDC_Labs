#light 
module Blocking

open MPI
open System

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

            let stopwatch = System.Diagnostics.Stopwatch.StartNew();
            
            for dest in 1..numWorkers do
                let rows = if (dest <= extraRows) then averageRows + 1 else averageRows
                let matrixToSend = Array.sub matrixA offset rows
                world.Send<int>(offset, dest, 0)
                world.Send<float[][]>(matrixToSend, dest, 0)
                world.Send<float[][]>(matrixB, dest, 0)
                offset <- offset + rows

            for source in 1..numWorkers do
                offset <- world.Receive<int>(source, 0)
                let receivedMatrix = world.Receive<float[][]>(source, 0)
                for i in 0..receivedMatrix.Length-1 do
                    for j in 0..matrixBCols-1 do
                        matrixC[i+offset][j] <- receivedMatrix[i][j]
            
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
        let offset = world.Receive<int>(0, 0)
        let receivedMatrix = world.Receive<float[][]>(0, 0)
        let matrixB = world.Receive<float[][]>(0, 0)
        let mutable matrixToSend = Array.init receivedMatrix.Length (fun _ -> Array.init matrixBCols (fun _ -> 0.0))

        for k in 0..matrixBCols-1 do
            for i in 0..receivedMatrix.Length-1 do
                for j in 0..matrixACols-1 do
                    matrixToSend[i][k] <- matrixToSend[i][k] + receivedMatrix[i][j] * matrixB[j][k]
        
        world.Send<int>(offset, 0, 0)
        world.Send<float[][]>(matrixToSend, 0, 0)
        0
    |> ignore