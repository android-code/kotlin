// PATTERN
interface Base {
    val text: String
    fun show()
    fun showFull()
}

class BaseImpl(text: String) : Base {
    override val text = "Delegate implementation: $text"
    override fun show() { print(text) }
    override fun showFull() { print("Delegate implementation full info: + $text") }
}

class Derived(delegate: Base) : Base by delegate

val base = BaseImpl("value")
val derived = Derived(base)
derived.show() //Delegate implementation: value

// OVERRIDE
class Derived(delegate: Base) : Base by delegate {
    override val text = "Overriden implementation: $text"
    override fun showFull() { print("Overriden implementation full info: $text") }
}

val base = BaseImpl("value")
val derived = Derived(base)
derived.show() //Delegate implementation: value
print(derived.text) //Overriden implementation: value
derived.showFull() //Overriden implementation full info: value

// PROPERTIES
class DelegatedProperty {
    var value: String by Delegate()
}

class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "get '${property.name}' has been delegated"
    }
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
    	print("set '${property.name}' = $value has been delegated")
    }
}

val delegated = DelegatedProperty()
print(delegated.value) //get 'value' has been delegated
delegated.value = "new one" //set 'value' = new one has been delegated

// LAZY PROPERTY
val lazyValue: String by lazy {
    print("evaluated ")
    "value"
}
print(lazyValue) //evaluted value - at first time is computed
print(lazyValue) //value - is computed already

// OBSERVABLE PROPERTY
class Person {
    var name: String by Delegates.observable("No name ") {
        property, old, new ->
        print("$old changed to $new")
    }
}

val person = Person()
person.name = "Johnnie" //No name changed to Johnnie
person.name = "Jack" //Johnnie changed to Jack

// STORING IN MAP
class Worker(var map: MutableMap<String, Any?>) {
    var name: String by map
    var salary: Int by map
}

//execute constructor with init values
var map: MutableMap<String, Any?> = mutableMapOf(
    "name" to "William",
    "salary" to 2000
)
val worker = Worker(map)

print(worker.name) //William
print(worker.salary) //2000
map.set("salary", 3000)
println(worker.salary) //3000