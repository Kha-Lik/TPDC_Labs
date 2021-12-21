namespace MPI.NET.MatrixMultiplication.Collective
#light
module Program =
    open MPI
    open MPI.NET.MatrixMultiplication.Collective.Utility
    open MPI.NET.MatrixMultiplication.Collective.OneToAll
    open MPI.NET.MatrixMultiplication.Collective.AllToAll
    open System
    
    
    [<EntryPoint>]
    let main args =
        let printResult = args.Length > 0 && args[0] = "-p" || args[0] = "-pa"
        let isAllToAll = args.Length > 0 && args[0] = "-a" || args[0] = "-pa"
        let argsOffset = if printResult || isAllToAll then 1 else 0
        if args.Length > 1 then
            matrixARows <- int args[0+argsOffset]
            matrixACols <- int args[1+argsOffset]
            matrixBCols <- int args[2+argsOffset]
        let env = new MPI.Environment(ref args)
        let world = Communicator.world
        
        if not isAllToAll then
            startOneToAll (env, world, printResult)
        else
            startAllToAll (env, world)
        env.Dispose()
        0