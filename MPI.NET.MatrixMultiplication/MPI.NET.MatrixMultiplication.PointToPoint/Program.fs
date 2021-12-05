#light

open MPI
open Blocking
open NonBlocking
    
let mutable matrixARows = 400
let mutable matrixACols = 600
let mutable matrixBCols = 400

[<EntryPoint>]
let main args =
    let printResult = args.Length > 0 && args[0] = "-p" || args[0] = "-pb"
    let isBlocking = args.Length > 0 && args[0] = "-b" || args[0] = "-pb"
    let argsOffset = if printResult || isBlocking then 1 else 0
    if args.Length > 1 then
        matrixARows <- int args[0+argsOffset]
        matrixACols <- int args[1+argsOffset]
        matrixBCols <- int args[2+argsOffset]
    let env = new Environment(ref args)
    let world = Communicator.world
    
    if world.Size < 2 then
        printfn "You need to run this program on more than one processor"
    else
        if isBlocking then
            startBlocking (env, world, matrixARows, matrixACols, matrixBCols, printResult)
        else
            startNonBlocking (env, world, matrixARows, matrixACols, matrixBCols, printResult)

    env.Dispose()
    0