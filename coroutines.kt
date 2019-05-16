// SUSPEND FUNCTION
suspend fun suspendingFunction() : Int  {
    //some task
    return 0
}

// CONTEXT
val handler = CoroutineExceptionHandler { context, exception ->
    //manage caught exception like logger action
}

//actually this is CombinedContext object
val context : CoroutineContext = Dispatchers.Default + Job() + handler

// DISPATCHER
//withContext is suspend functions itself so no need to declare in inside suspend function if used inside coroutine
suspend fun suspendingWithContext() =
    //allows to change contex in coroutine for given block
    withContext(Dispatchers.Main) { 
        //some work
    }

// BUILDERS
//runBlocking
//wait to finish this function to go further
fun testSuspendingWork() = runBlocking {
    val result = suspendingFunction()
    //wait here for result
    assertEquals(0, result)
}

//launch
suspend fun launchJobAndJoin() {
    //Job extends CoroutineContext to it's context itself
    val job = launch { //note that coroutines must be called on some scope 
        //some work
        val result1 = suspendingWork1()
        //wait for result1
        val result2 = suspendingWork2()
        //wait for result2
        //process results
    }
    
    //wait here until child tasks finished
    job.join() //suspending function itself
}

fun launchJobAndCancel() {
    val job = launch {
        //some work
        val result1 = suspendingWork1()
        val result2 = suspendingWork2()
        //process results
    }
    
    //cancel all cancellable jobs, so if suspendingWork is running then cancel it and pending tasks
    job.cancel() //regular function so no need to run inside coroutine or suspend function
}

//async
fun launchAsync() {
    val job = launch {
        val result = suspendingWork()
        
        val deferred1 = async {
            //some work
            return@async "result2"
        }
        
        //async can be lazy started when manual start or await called
        val deferred2 = async(start = CoroutineStart.LAZY) {
            //some work
            return@async "result3"
        }
        //note that if no start for lazy async called then behaviour is sequantial when await called
        deferred2.start()

        //if in this place deferred1 and deferred2 not finished, wait for it
        val finalResult = "$result ${deferred1.await()} ${deferred2.await()}"
    }
    //run job.cancel() to cancel parent and all childs
}

// CANCELLING
fun cancellableSuspsend() = runBlocking {
    val job = launch(Dispatchers.Default) {
        repeat(100) {
            //this computation are suspend function, so it is cancellable
            suspendingWork()
        }
    }
    
    delay(1) //allow to start coroutine before cancel
    job.cancel()
}

fun notCancellableComputation() = runBlocking {
    val job = launch(Dispatchers.Default) {
        repeat(100) { 
            //some intensive computation
            notSuspendingWork()
        }
    }

    delay(1) //allow to start coroutine before cancel
    job.cancel() //this won't work
}

fun cancellableComputation() = runBlocking {
    val job = launch(Dispatchers.Default) {
        //cancellable code throw CancellationException on cancel
        try {
            repeat(100) {
                //check periodically is scope active or has been cancelled
                if (isActive) {
                    //do some intensive computation if coroutine is still active
                    notSuspendingWork()
                }
            }
        }
        finally {
            //coroutine has been cancelled
            //run suspending function here will throw CancellationException
            withContext(NonCancellable) {
                //running suspending function is now possible
            }
        }
    }

    delay(1) //allow to start coroutine before cancel
    job.cancel()
}

//coroutines must be called on some scope so just use main scope called GlobalScope
fun cancellableByTimeout() = runBlocking {
    //cancel when coroutine couldn't complete after 1 second
    val result = withTimeoutOrNull(1000) {
        repeat(100) {
            notSuspendingWork()
        }
    }
    //use withTime to do the same but throw TimeoutCancellationException instead of return null
}

// SCOPE
class ScopeActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        //calling just launch { } is not possible
        //launch must be called on some CoroutineScope

        CoroutineScope(Dispatchers.Main).launch {
            //work
        }
        
        val job = GlobalScope.launch { 
            //work
            async {
                //coroutine nested in coroutine
            }
        }
    }
}

//extend CoroutineScope
class ScopeClassActivity : AppCompatActivity(), CoroutineScope {
    
    //can be combined with another context like contatenation with Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        //now calling launch is possible because Activity is CoroutineScope itself
        launch { 
            //work
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        cancel() //cancel on scope so all coroutines inside scope are cancelling
    }
}

//or provide CoroutineScope by delegate
class ScopeDelegateActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    
    //this class is CoroutineScope itself
}

// CHANNELS
fun runChannel() = runBlocking {
    //create channel using factory method
    //use one of RENDEZVOUS, UNLIMITED, CONFLATED optional param to specify buffer
    val channel = Channel<Int>(RENDEZVOUS) //it is unbuffered channel
    
    launch { 
        repeat(3) {
            //send some items from this coroutine
            channel.send(it)
            //suspend if no receivers
        } 
        
        //close channel to stop emission, this guarantees to send pending items
        channel.close()
    }
    
    //receive emitted values by receive function
    val value = channel.receive()

    //as alternative use channel's loop or consume by extension function of ReceiveChannel
    channel.consumeEach { 
        //do something with received values: 1, 2, 3
    }
    
    //emitted items can be consumed single time, so trying to receive again will no result
}

//BroadcastChannel
fun runBroadcastChannel() = runBlocking {
    val channel = BroadcastChannel<Int>(UNLIMITED)
    launch {
        repeat(3) { channel.send(it) } //doesn't supsend if no receivers
        channel.close()
    }
    
    //items can be consumed by multiple receivers
    channel.consumeEach {}
    channel.consumeEach {}  
}

//produce
//use it as some variable in real world
fun runProducer() = launch {
    //produce is extension function of CoroutineScope
    val producer : ReceiveChannel<Int> = produce {
        repeat(3) { send(it) }
    }

    //use instance of ReceiveChannel to receive values emitted by produce
    producer.consumeEach {
        //do something
    }   
}

//actor
//use it as some variable in real world
fun runActor() = launch {   
    //actor is extension function of CoroutineScope
    val actor : SendChannel<Int> = actor {
        for(item in channel) {
            //often used with selead class to do something
        }
    }

    repeat(3) { actor.send(it) }
}

//select
fun runSelect() = launch {
    val producer = produce {
        repeat(5) { send(it) }
    }
    //more producers

    val actor = actor<Int> {
        consumeEach {
            //do something
        }
    }
    //more actors

    //select works on some channels
    repeat(5) {
        select<Unit> {
            //do only one action for first available supsend fun
            
            //imagine the case when select must do receive action for multiple channels
            producer.onReceive { value ->
                //do something
            }
            //define more onReceive for other channels
            
            //or to send value for multiple channels
            actor.onSend(100) {}
        }
    }
}