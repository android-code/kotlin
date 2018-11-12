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
var array = ArrayOf(1, 2, 3)
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

// ALIAS
typealias ShortName = VeryLongNameOfSomeCustomClass
var someObject: ShortName = ShortName()
var result = someObject is VeryLongNameOfSomeCustomClass //true