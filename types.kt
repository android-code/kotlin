// VARIABLES AND CONSTANTS
var variable: Int = 1 //assignment Int
var variableInferred = 2 //type is inferred
val variableDeferred: Int //definition as Int
variableDeferred = 3 //deferred assignment - must be Int
var someObject = SomeClass(1, "Name") //instance of class - no 'new' keyword

//top-level or member class declaration
const val constant: Float = 3.14f
var a: String
println(a) //compiler error - variable must be initialized
var b //compiler error - variable must have a type or be initialized

//lateinit
lateinit var obj: SomeClass //type must be declared
print(obj) //throws UninitializedPropertyAccessException

obj = SomeClass() //setup obj from external source
print(obj) //now it's okay

// NUMERICS
var decimals = 100 //decimals (Int, Short, Byte)
var binary = 0b1100100 //binary
var hexadecimal = 0x64 //hexadecimal
var longNumber = 100L //Long
var doubleNumber = 10.0 //Double
var doubleScientific = 10.0e5 //Double in scientific notation
var floatNumber = 100F //Float

//underscore
var million = 1_000_000
var rgb = 0xFF_00_55

//unsigned
var a = 1u
var b: UInt = 1u

//conversion
var shortNumber: Short = 1
var intNumber: Int = shortNumber.toInt() //valid cast
intNumber = shortNumber //invalid cast - compile error

var longNumber = 1L + intNumber //Long + Int -> Long
var longNumber2 = intNumber + 1L //Int + Long -> Long
var validInt = longNumber.toInt() + longNumber2.toInt() //valid cast
var invalidInt = longNumber.toInt() + longNumber2 //invalid cast - compile error

// CHARACTERS
var character = 'a'
character = '1'
var tab = '\t' //special tab character
var special = '\uAABB' //special character preceded by \u

// BOOLEAN
var bool: Boolean
bool = true
bool = 1 //compiler error
var result = bool && false //false

// STRINGS 
var text = "Hello"
var letter = text[0] //this is H
var escaped = "Some line\nAnother line"
var raw = """
    Some line
    Another line
"""

//concat
var text = "text"
var concat = text + 1 //result text1
concat = 1 + text //compiler error

var number = 5
var template = "number = $number and text variable length = ${text.length}"

// ARRAYS
var array = arrayOf(1, 2, 3)
var arrayInt: IntArray = intArrayOf(1, 2, 3)
var element = array[1]
element = arrayInt.get(1)

// OPERATORS 
var a = 1
var b = 2
var result = a == b //false
result = a.equals(b) //false - translated as a == b
result = a != b //true
result = a < b //true
result = a === b //false - translated into a == b

var rect1 = Rectangle(1, 2)
var rect2 = Rectangle(1, 2)
result = rect1 === rect2 //false
rect1 = rect2
result = rec1 === rec2 //true

// CHECK TYPE AND CASTING
if(a is String) {
    var length = a.length //a is treated as String here
}
if(a is String && a.length > 0) { 
    //do something with not empty String
}

if(a !is String || a.length == 0) {
    //do something with no String or empty String
}

//as
var text = "text"
var number = 1
var a: String = text as String //cast correct
var b: Int = text as Int //ClassCastException exception thrown
var c: Int? = text as? Int //cast unsuccess - c is null but no exception throwed

// ENUMS
enum class Size {
    S, M, L, XL
}

enum class Color(val number: Int) {
    S(1), M(2), L(3), XL(4)
}

var size = Size.M
var color = Color.RED
print(color) //RED
print(color.number) //1
print(Color.GREEN.number) //2

// GENERICS
class Invariant<T> //class definition
var genString: Invariant<String> = Invariant<String>() //ok types are equals
var genInt: Invariant<Int> = Invariant<Long>() //compiler error - Invariant<Int> expected instead of Invariant<Long>

fun <T> genericFunction(x: T) {} //function definition
var a = genericFunction<String>("generic") //execution

//covariant and contravariant
class Covariant<out T>
class Contravariant<in T>	

var covA: Covariant<Number> = Covariant<Int>() //ok, Number is super class for Int
var covB: Covariant<Int> = Covariant<Number> //compiler error - type mismatch 
var contraA: Covariant<Number> = Covariant<Int>() //compiler error - type mismatch 	
var contraB: Generic<Int> = Generic<Number> //ok, Int is subtype of Number class

// COLLECTIONS
//empty
var emptyList: List<Int> = emptyList()
var emptySet = setOf<Int>() //type paremeter inferred from context

//immutable
var immutableList: List<Int> = listOf(1,2,3)
var immutableSet: Set<Int> = setOf(4,5,6)
var immutableMap: Map<Int, String>  = mapOf(1 to "a", 2 to "b", 3 to "c")

//mutable
var mutableList: MutableList<Int> = mutableListOf(3,2,1)
var mutableSet: MutableSet<Int> = mutableSetOf(6,5,4)
var mutableMap: MutableMap<Int, String> = mutableMapOf(3 to "c", 2 to "b", 1 to "c")

//cast
var immutableFromMutable: List<Int> = mutableList //ok by 
var mutableFromImmutable: MutableList<Int> = list.toMutableList() //must be directly casted by method

//covariant
var covNumbers: List<Number> = immutableList //ok because Number is supertype for Int
var covIntegers: List<Int> = covNumbers //compiler error - Int is not supertype of Number
var covMutableIntegers: MutableList<Int> = immutableList //compiler error - List<Int> is expected

//collection operations
immutableList.first() //1
immutableList.get(1) //2
immutableList.slice(0..1) //[1,2]
immutableList.filter { it % 2 == 0} //[1, 3]

println(mutableList) //[3,2,1]
mutableList.add(4) //[3,2,1,4]
mutableList.set(1, 5) //[3,5,1,4]
mutableList.removeAt(2) //[3,5,4]
mutableList.remove(4) //[3,5]
mutableList.clear() //[]

// ALIAS
typealias ShortName = VeryLongNameOfSomeCustomClass
var someObject: ShortName = ShortName()
var result = someObject is VeryLongNameOfSomeCustomClass //true