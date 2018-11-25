// REFERENCE
var nothing = null //it's okay because type inferred from context is Nothing?
var a: String = "text"
a = null //compiler error - null can not be a value of a non-null type

var b: String? = "text"
b = null //this references is nullable type so it's okay
print(b) //print null - no NPE thrown
print(b.length) //compiler error - only safe or non-null asserted operator can be used on a nullable reference

val text: String? = "text"
if(text != null && text.length > 0) //text.length is allowed because of auto non-nullable cast
    print("String is not empty")
else 
    print("String is empty")

// SAFE CALL ?.
var text: String? = "text"
print(text?.length) //print 5
text = null
print(text.length) //compiler error - use safe operator to get value
print(text?.length) //no compile error - print null - no NPE thrown

//chain
var name: String? = building?.floor?.room?.person?.name //return null only if any property is null
building?.floor?.room?.person?.name = "Jack" //skipp assignment if any property is null

//let
var text: String? = "text"
text?.let { print("not null") } //print is okay
text = null 
text?.let { print("not null") } //print is ignore because of null

// ELVIS OPERATOR ?:
var text: String? = "text"
//if-else
var length: Int = if(text != null) text.length else -1
//instead of if-else just use Elvis
length = text?.length ?: -1

//throw and Nothing
//if true then value is null and has Nothing? type
var room = building?.floor?.room ?: throw Exception("No room provided")
var name = person?.name ?: null 

// ASSERTION OPERATOR !!
var text: String? = null
val length = text!!.length //KotlinNullPointerException because text is null!

// EXCEPTIONS
//some custom function and exception class
class CustomException(message: String) : Exception(message)
fun action(text: String) {
    if(text.length != 5 || !text.contains("PL")) { 
        throw CustomException("Passed code hasn't valid polish format")
    }
    else {
        //do some job
    }
}

//try-catch-finally
val isValid: Boolean = try {
    action("PL12")
    true
}
catch(e: CustomException) {
    print(e) //CustomException: Passed code hasn't valid polish format
    false
}
catch(e: Exception) { 
    //depends on expected exception types more than one catch block can be declared
    false
}
finally {
    print("This block is optional")
    true //this return value is ignored
}
print(isValid) //false