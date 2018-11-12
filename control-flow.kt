// IF-ELSE
var a = 1
var b = 2
var c = 0

//standard way
if(a < b) 
    c = a
else if(a > b) 
    c = b
else {
    //do something
    c = a + b
}

//as expression
c = if(a < b) a 
    else if(a > b) b 
    else { //else branch is required when if is using as expression
        //do something
        a+b //note that last expression must be returning value
    }

// RANGE
var a = 3
if(a in 1..5) print("a is in 1-5 range") //1<=a && a<=5
if(a !in 0..9) print("a is not a single number") //0>a || a>9

// WHEN
var a = 3
when(a) {
    1, 2, 3 -> print("a is in small range")
    in 4..10 -> print("a is in big range")
    0 -> print("a is zero")
    else -> { 
        print("a is not in range")
    }
}

// FOR
var array = ArrayOf(1, 2, 3)
for(item: Int in array) {
    print(item)
}

//or just
for(item in array) print(item)

//or iterate by indices
for(i in array.indices) print(array[i])

for(i in 1..3) print(i) //1,2,3
for(i in 1 until 3) print(i) //1,2,3
for(i in 5..3) print(i) //5,4,3
for(i in 5 downTo 3) print(i) //5,4,3
for(i in 1..10 step 2) print(i) //1,3,5,7,9

// WHILE
var i = 1
while(i <= 5) {
    print(i)
    i++
}

var j = 0
do {
    print(j)
    j++
} while(j < 0)

// JUMP INSTRUCTIONS
var a = 9
for(i in 1..10) {
    if(i == a) break
    if(i % 2 == 0) continue
    print(i)
}

loop@ for(i in 1..10) {
    for(j in 1..10) {
        if (i*j % 2 == 0) continue@loop
        print(" " + i*j)
    }
}

listOf(1, 2, 3, 4, 5).forEach {
    if(it %2 == 0) return // non-local return directly to the caller of foo()
    print(" " + it)
}
//returned from entire caller function body so this point is unreachable

listOf(1, 2, 3, 4, 5).forEach loop@{
    if(it %2 == 0) return@loop 
    print(" " + it)
}
//returned only from the local caller - foreach loop, not from caller function so this point is reachable