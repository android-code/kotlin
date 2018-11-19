// INHERITANCE
//with primary constructor
open class ParentPrimary(number: Int)
class ChildPrimary(number: Int) : Parent(number)

//with secondary constructor
open class ParentSecondary {
    var number: Int
    constructor(number: Int) {
        this.number = number
    }
}

class ChildSecondary : ParentSecondary {
    var text: String
    constructor(number: Int) : super(number) {
        this.text = ""
    }
    constructor(number: Int, text: String) : super(number) {
        this.text = text
    }
}

// CLASS MEMBERS
//classes derived from Parent class can override text property and action1, action2 functions
open class Parent {
    open val text: String = "parent"
        get() = field.capitalize()
    open fun action1() { print("base action1") }
    open fun action2() { print("base action2") }
    fun showText() { print(text) }
}

//overriding properties can be part of the primary constructor
//any class can derived from Child class because it's not open
//but if Child class will be open then classes derived can override only action1
class Child(final override var text: String) : Parent() {
    override fun action1() { print ("overriden action1") }
    final override fun action2() { print ("final overriden action2") }
}

//execution
var parent = Parent()
var child = Child("child")
parent.showText() //Parent
child.showText() //child
parent.action1() //base action1
child.action1() //overriden action1

// MULTI INHERITANCE
open class A {
    open fun action1() { print("action1 A") }
}

open class B {
    open fun action1() { print("action1 B") }
    open fun action2() { print("action2 B") }
}

class C() : A(), B() {
    override fun action1() { //action1 fun must be overriden
        super<A>.action1()
        print(" from C")
    }
    fun action3() { print("action3 C") }
}

//execution
var a = A()
var b = B()
var c = C()
a.action1() //action1 A
b.action1() //action1 B
c.action1() //action1 A from C
c.action2() //action2 B
c.action3() //action3 C

// ABSTRACT CLASS
abstract class Abstraction {
    fun action1() { print("abstract action1") }
    abstract fun action2() //must be overriden
}
open class Concrete : Abstraction() {
    override fun action2() { print("action2 implementation") }
}

// INTERFACES
interface SomeInterface {
    val text: String //is abstract so must be overriden
    fun action1() //must be implement
    fun action2() {
      print(text + " from interface implementation")
    }
}

class SomeClass : SomeInterface {
    override val text: String = "value"
    override fun action1() {
        print("action1 implementation")
    }
}

var obj: SomeInterface = SomeClass()
obj.action1() //action1 implementation
obj.action2() //value from interface implementation

// INTERFACE INHERITANCE
interface IA {
    fun action1() //this is abstract
    fun action2() { print("action2 IA") }
}

interface IB : IA {
    fun action3() { print("action3 IB") }
}

interface IC {
    fun action2() { print("action2 IC") }
}

class ClassInterface : IA, IC {
    override fun action1() { print("action1 ClassInterface") }
    override fun action2() { 
        super<IC>.action1()
        print(" from ClassInterface") 
    }
}

var obj: IB = ClassInterface()
obj.action1() //action1 ClassInterface
obj.action2() //action2 IA from ClassInterface
obj.action3() //action3 IB

// EXTENSIONS
fun Int?.multiply(number: Int): Int {
    if(this == null) return 0
    return this*number //autocast to non-null
}
val String.even: Boolean
    get() = length%2 == 0

var number: Int = 5
var text: String = "text"
print(number.multiply(3)) //15
print(text.even) //true

open class A
class B: A()
class C {
    fun show() = print("member")
}
fun A.show() = print("A")
fun B.show() = print("B")
fun C.show() = print("extension")
fun showInfo(obj: A) = obj.show()

showInfo(B()) //A printed
C().show() //member printed

// EXTENSIONS AS MEMBERS
class ExtensionReceiver {
    fun work() { print("some work") }
}
open class DispatchReceiver {
    fun action() { print("ExtensionReceiver.action() from dispatch receiver class") }
    open fun ExtensionReceiver.action() {
        work() //from ExtensionReceiver class
        println("")
        this@DispatchReceiver.action()
    }
    fun caller(obj: ExtensionReceiver) {
        obj.action()
    }
}
class DerivedClass: DispatchReceiver() {
    override fun ExtensionReceiver.action() {
        print("ExtensionReceiver.action() function from derived class")
    }
}

var extension = ExtensionReceiver()
var dispatch = DispatchReceiver()
var derived = DerivedClass()
dispatch.caller(extension) //some work ExtensionReceiver.action() from dispatch receiver class
derived.caller(extension) //ExtensionReceiver.action() function from derived class