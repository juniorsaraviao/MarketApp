package com.mitocode.marketapp


/*data class -> when we manipulate things - at least 1 attribute
used to unstructured*/

class Person (var name: String = "", var surname: String = "") {

    // static values and fun
    companion object {
        const val hello = "Hello"
    }

    //constructor(name: String) : this(name, "")
    //constructor() : this("", "")
    /*init {
        var name = name
        var surname = surname
    }*/

    /*constructor(name: String, surname: String){
        this.name = name
        this.surname = surname
    }*/


}