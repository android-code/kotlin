// DECLARATION
//declaration in standard way
fun tripple(): Int {
    return 3*10
}

//return Unit type
fun printText() {
    print("Hello world")
}

//declaration as expression
fun tripple() = 3*10 //note that return type declaration is not needed here

//infix
//usage
var result = tripple()
SomeClass().tripple() //if function is a member class

//some A class body
infix fun function(arg: Int) {} //declared as member function

//usage
var object = A()
A function 5 //infix function
A.function(5) //the same result as infix function above

// DECLARATION POSITION
//top-level declaration
//some class declaration { ... }
fun function() {
    //do something
}

//local function declaration
fun calculate(): Int {
    fun local1(): Int {
        return 3*10
    }
    fun local2(): Int {
        return 2*10
    }
    return local1() * local2()
}

// PARAMETERS
fun functionWithArgs(arg1: Int, arg2: String) {
    //some body
    print(arg2 + " " + arg1)
}

fun functionWithDefaultArgs(arg1: Int = 5, arg2: String) {
    //some body
    print(arg2 + " " + arg1)
}

//execution
functionWithArgs(10, "text") //text 10
functionWithDefaultArgs(10, "text") //text 10
functionWithDefaultArgs("text") //text 5

// parameters
//some A class
fun function(arg1: Int = 5, arg2: String) {}

//other B class extending A class
override fun function(arg1: Int = 10, arg2: String) {} //compiler error
override fun function(arg1: Int, arg2: String) {} //correct overriding

//declaration
fun <T> functionGenericType(item: T): List<T> { 
    var list: List<T> = listOf(item, item, item)
    return list
}

//execution
var someList = functionGenericType<Int>(10)
someList = functionGenericType(10) //type can be missing if known from context

// NAMED ARGUMENTS
fun function(arg1: Int = 10, arg2: String) {}
function("text") //compile error
function(arg2 = "text") //correct executing using named arg

fun functionWithManyArgs(id: Int, number: Int, name: String, citizen: Boolean, adult: Boolean)
functionWithManyArgs(100, 1, "Jack", true, false) //proper but not easy to understand
functionWithManyArgs(id=100, number=1, name="Jack", citizen=true, adult=false) //more cleaner way

fun function(arg1: Int, arg2: String) {}
function(10, arg2="text") //correct
function(arg2="text", 10) //compile error

//lambda
fun function(arg1: Int = 10, arg2: Int = 5, lambda: () -> Unit) {}
function(3) { print("lambda action") } //arg1=3, arg2=5
function() { print("lambda action") } //arg1=10, arg2=5

// vararg
fun function(vararg args: Int, text: String) {} //vararg should be the last one arg
function(1,2,3, "text") //compile error
function(1,2,3, text="text") //correct execution with named arg

//vararg could be passed as list with * modifier
var array = arrayOf(1,2,3)
function(*array, text="text")

// high order
//take function as argument
fun functionWithFunArg(arg: Int = 10, lambda: (a: Int) -> Int) {
    //do some work and use lambda function
    lambda(arg)
}

//execute function with function as argument
var lambdaArg = { a: Int -> a*a }
functionWithFunArg(3, lambdaArg) 
functionWithFunArg(3) { a: Int -> a*a } 

//return function from function
fun functionWithFunReturn(arg: Int) : (a: Int) -> String {
    return { a -> "value=" + (arg*a) }
}

//execute function with function type returned
var lambdaReturn = functionWithFunReturn(10)
lambdaReturn(2)

// inline
@PublishedApi
internal var internalMember = "internal"
private var privateMember = "private"

inline functionInline(arg: Int = 10, lambda: (a: Int) -> Unit) {
    privateMember.toString() //compile error - not allowed
    internalMember.toString() //it's okay
    lambda(arg)
    print("This is unreachable") //because of return statement in executing
}

//execution
functionInline {
    //do something
    return //it is possible because function is inline
}

// tail recursion
tailrec fun fibonacci(n: Int, a: Long, b: Long): Long {
    return if (n == 0) b else fibonacci(n-1, a+b, a)
}

//instead of loop
fun fibonacciLoop(n: Int, a: Long, b: Long): Long {
    var counter = 1
    while(counter <= n) {
        var sum=a+b
        a=b
        b=sum
        counter++
    }
}

// SCOPE FUNCTIONS
//this vs it
var employee = Employee().apply {
    name = "Jack"
    salary = 3000
}

var employee = Employee().also { 
    newEmployee ->
    newEmployee.name = "Jack"
    newEmployee.salary = 3000
}

//instead of
var employee = Employee()
employee.name = "Jack"
employee.salary = 3000

//return self or other type
var employee = Employee("Jack", 3000)
var tax = employee.let {
    print(it)
    it
}.let {
    it.salary
}.let {
    it * 0.2
}

var tax = employee.also {
    print(it)
    it //don't need to be here
}.also {
    it.salary //it skips, employee object is passed
}.also {
    it * 0.2 //compiler error - Employee instances is here, not Int
}

//instead of
print(employee)
var tax = employee.salary * 0.2

//normal vs extension function
var employee: Employee? = Employee("Jack", 3000)
with(employee) {
    print(this?.name)
    this?.salary = salary + 1000
}

employee?.run {
    print(name)
    salary = salary + 1000
}

//instead of
if(employee != null) {
    print(employee.name)
    employee.salary = employee.salary + 1000
}