#light

open System
open MPI
    
let matrixARows = 400
let matrixACols = 600
let matrixBCols = 400

[<EntryPoint>]
let main args =
    let env = new Environment(ref args)
    let world = Communicator.world

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
            
            for dest in 1..numWorkers do
                let rows = if (dest <= extraRows) then averageRows + 1 else averageRows
                let matrixToSend = Array.sub matrixA offset rows
                world.Send<int>(offset, dest, 0)
                world.Send<float[]>(matrixToSend, dest, 0)
                world.Send<float[]>(matrixB, dest, 0)
                offset <- offset + rows

            for source in 1..numWorkers do
                offset <- world.Receive<int>(source, 0)
                printfn "received offset %d from source %d" offset source
                let receivedMatrix = world.Receive<float[][]>(source, 0)
                for i in 0..receivedMatrix.Length do
                    for j in 0..matrixBCols do
                        matrixC[i+offset][j] <- receivedMatrix[i][j]
            
            printf "****\n"
            printf "Result Matrix:\n"
            for i in 0..matrixARows do
                printf "\n"
                for j in 0..matrixBCols do
                    printf "%6.2f" matrixC[i].[j]
            printf "\n********\n"
            printf "Done.\n"
            0

    else
        let offset = world.Receive<int>(0, 0)
        let receivedMatrix = world.Receive<float[][]>(0, 0)
        let matrixB = world.Receive<float[][]>(0, 0)
        let mutable matrixToSend = Array.init receivedMatrix.Length (fun _ -> Array.init matrixBCols (fun _ -> 0.0))

        for k in 0..matrixBCols do
            for i in 0..receivedMatrix.Length do
                for j in 0..matrixACols do
                    matrixToSend[i][k] <- matrixToSend[i][k] + receivedMatrix[i][j] * matrixB[j][k]
        
        world.Send<int>(offset, 0, 0)
        world.Send<float[][]>(matrixToSend, 0, 0)
        0
    |> ignore

    env.Dispose()
    0