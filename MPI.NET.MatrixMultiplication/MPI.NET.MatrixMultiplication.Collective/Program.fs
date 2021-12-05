#light

open MPI
open System

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

[<EntryPoint>]
let main args =
    let printResult = args.Length > 0 && args[0] = "-p"
    let argsOffset = if printResult then 1 else 0
    if args.Length > 1 then
        matrixARows <- int args[0+argsOffset]
        matrixACols <- int args[1+argsOffset]
        matrixBCols <- int args[2+argsOffset]
    let env = new MPI.Environment(ref args)
    let world = Communicator.world
    
    if world.Rank = 0 then
        if matrixARows % world.Size > 0 then
            printfn $"matrix of {matrixARows} rows is not supported on {world.Size} processes"
        else
            //data arrangement
            let rnd = Random()
            let matrixA = Array.init matrixARows (fun _ -> Array.init matrixACols (fun _ -> rnd.NextDouble()))
            let matrixB = Array.init matrixACols (fun _ -> Array.init matrixBCols (fun _ -> rnd.NextDouble()))
            
            let rowsPerProcess = matrixARows / world.Size
            let arrangedData = Array.chunkBySize rowsPerProcess matrixA
            
            //computations
            let stopwatch = Diagnostics.Stopwatch.StartNew();

            let receivedArray = world.Scatter(arrangedData, 0)
            world.Broadcast<float[][]>(ref matrixB, 0)
            
            let result = multiply receivedArray matrixB
            let receivedResults = world.Gather<float[][]>(result, 0)
            
            let matrixC = receivedResults |> Array.concat
            stopwatch.Stop()

            printfn $"Time elapsed to multiply matrices A(%d{matrixARows}x%d{matrixACols}) and B(%d{matrixACols}x%d{matrixBCols}) with %d{world.Size} workers: %d{stopwatch.ElapsedMilliseconds}ms"
            
            if printResult then
                printf "****\n"
                printf "Result Matrix:\n"
                for i in 0..matrixARows-1 do
                    printf "\n"
                    for j in 0..matrixBCols-1 do
                        printf $"%6.2f{matrixC[i].[j]}"
                printf "\n********\n"
                printf "Done.\n"            
    else
        let receivedMatrix = world.Scatter<float[][]>(0)
        let mutable matrixB = Array.init matrixACols (fun _ -> Array.init matrixBCols (fun _ -> 0.0))
        world.Broadcast<float[][]>(ref matrixB, 0)
        
        let result = multiply receivedMatrix matrixB
        
        world.Gather(result, 0) |> ignore
    
    env.Dispose()
    0