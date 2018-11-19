// DECLARATION
class Name {

    //some variables
    var a;
    val b;

    fun someFunction(a: String) {
        print(a) //parameter of function
        print(this.a) //property of class
    }

    //some more body code
}

class Empty //if class has no body than curly braces are no needed

// CONSTRUCTORS
//primary constructor can have some parameters
class Primary constructor(text: String) { }

//constructor keyword can be omitted if doesn't have any adnotations or visibility modifiers
class Primary(text: String) { }

//constructor parameters can be also mutable or read-only
class Primary(var text: String, val amount: Int) { }

//constructor keyword can't be ommited because of adnotations or visibility modifiers
class Primary public @Inject constructor(text: String) { }

//primary constructor
class Student(name: String, surname: String) {

    var name = name
    var surname = surname.toUpperCase()

    init {
        //do some init staff
        print("Student ${name} ${surname} has been created")
    }
}

//secondary constructor
//class has only secondary constructor
class Secondary {
    constructor(text: String) { // some code }
}

//class has primary and secondary constructor
class PrimarySecondary(text: String) {
    var text: String = text;
    var amount: Int
    
    init {
        //This block executes before every secondardy constructor and can be used for example by primary constructor job
        amount = 0
    }
      constructor(text: String, amount: Int) : this(text) {
        //Secondardy constructor body
        this.amount = amount
      }
    //more secondary constructors could be here
}

//objects can be created only be declared constructors
var a = PrimarySecondary("text") //okay
var b = PrimarySecondary("text", 10) //okay
var c = PrimarySecondary() //compiler error

//default constructor
class NoConstructor {
    //some body
}

//use default primary constructor to create an instance
var obj = NoConstructor()

// PROPERTIES AND FIELDS
//in Kotlin property is both field and accessors 
var name: String = "William"

//equivalent in Java
String name = "William"
public String getName() {
    return name;
}
public void setName(String name) {
    this.name = name;
}

class Person(name: String, age: Int) {

    var name: String = name
        get() = field.capitalize()
        set(value) {
            field = "Mr. " + value
        }
    
    var age: Int = age
        private set

    fun changeAge(age: Int) {
        if(this.age < age) {
            this.age = age
        }
    }
}

var person = Person("william", 15)
print("${person.name} is ${person.age} years old") //William is 15 years old
person.name = "Jim"
person.age = 20 //compiler error - he setter is private so property can not be directly changed
person.changeAge(20) //change property by specific method
print("${person.name} is ${person.age} years old") Mr. Jim is 20 years old

var person = Person("William", 15)
print("${person.name} is ${person.age} years old")
person.name = 20 //compiler error - the setter is private so property can not be directly changed
person.changeName("Jim") //name property can be changed like this

//DATA CLASSES
data class Product(val name: String) {
    var price: Float = 0f
}

var product1 = Product("Jack Daniels")
product1.price = 10f //note that price isn't a part of autogenereated equals, hashCode, toString and copy methods
print(product1.toString()) //Product(name=Jack Daniels)
var product2 = product1.copy()
print(product1.equals(product2)) //true - despite the differenet prices

// NESTED AND INNER CLASS
class Outer {
    var amount: Int = 5
    val ratio: Int = 2
    
    class Nested {
        //nested class doesn't know about Outer class
        fun work() = 20
    }

    inner class Inner {
        //inner class knows about Outer class
        fun work(): Int {
            amount = super@Outer.ratio * amount
            return amount
        }
    }
}

var outerObj = Outer()
var nestedObj = Outer.Nested()
var innerObj = outer.Inner()
print(outerObj.amount) //5
print(nestedObj.work()) //20
print(innerObj.work()) //10
print(outerObj.amount) //10

// SEALED CLASS
sealed class Status {
    data class Content(val text: String) : Status() {
        //some body
    }
    object None : Status() {
        //some body
    }
    //some members of Status class
}
class Error(code: Int) : Status() {
    //some body
}
//subclasses can be declared outside the sealed class but must be in the same file as sealed class

//get status from network event
val status: Status = Status.Content("content of the page")
when(status) {
    is Status.Content -> print(status.text)
    is Status.None -> print("No content to show")
    is Error -> print("Unexpected error")
}

// INLINE CLASSES
inline class InlineClass(val text: String) : Showable {
    val length: Int
        get() = text.length

    override fun show() {
        println("$text has $length length")
    }
}

interface Showable {
    fun show()
}

// OBJECT EXPRESSION AND DECLARATION
//object expression
var obj = object {
    var number = 10
    var text = "object expression"
}
print(obj.text) //text

//anonymous inner class
//normal declared class
class OnClickListener {
    fun onClick() {
        print("click")
    }
}

//normal object passed with default action
var listener = OnClickListener()
button.setOnClickListener(listener)

//object expression passed with modify action
button.setOnClickListener(object : OnClickListener() {
    override fun onClick() { print("click modified") }
})

//object declaration
object Singleton {
    var text = "object declaration"
    fun someFun() {}
}

//execution
Singleton.someFun()
print(Singleton.text) //object declaration

//companion object
class SomeClass1 {
    companion object SingletonCompanion {
        var text = "SingletonCompanion"
        fun someFun() {}
    }
}

class SomeClass2 {
    companion object {
        var text = "Companion"
        fun someFun() {}
    }
}

SomeClass1.SingletonCompanion.someFun() //companion name can be missed
print(SomeClass1.text) //SingletonCompanion
SomeClass2.Companion.someFun() //companion default name is Companion
print(SomeClass2.text) //Companion

// VISIBILITY MODIFIERS
class Visibility {
  
    var number: Int //public by default
    private var text: String

    public constructor(number: Int, text: String) {
        this.number = number
        this.text = text
    }

    internal fun internalFunction() { }
    protected fun protectedFunction() { }
}

//execution from the same module but another class
var obj = Visibility(1, "text")
print(obj.number) //1
print(obj.text) //compiler error - it's private
obj.internalFunction() //it's okay because it is the same module
obj.protectedFunction() //compiler error - it's protected so access only in class or in extending class