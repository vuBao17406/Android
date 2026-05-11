package com.example.bai1.logic

fun main() {

    // Hello World
    println("Hello, world!")
    println("This is the text to print!")

    // ======================
    // BIẾN
    // ======================
    val age = 5
    val name = "Rover"

    var roll = 6
    var rolledValue: Int = 4

    println("You are already $age!")
    println("You are already $age days old, $name!")

    // ======================
    // HÀM
    // ======================
    printHello()
    printBorder("=", 20)

    val result = rollDice()
    println("Dice rolled: $result")

    // ======================
    // IF / ELSE
    // ======================
    val num = 4
    if (num > 4) {
        println("The variable is greater than 4")
    } else if (num == 4) {
        println("The variable is equal to 4")
    } else {
        println("The variable is less than 4")
    }

    // ======================
    // WHEN
    // ======================
    val luckyNumber = 3
    val rollResult = rollDice()

    when (rollResult) {
        luckyNumber -> println("You won!")
        1 -> println("Rolled 1")
        2 -> println("Rolled 2")
        3 -> println("Rolled 3")
        4 -> println("Rolled 4")
        5 -> println("Rolled 5")
        else -> println("Rolled 6")
    }

    // ======================
    // CLASS
    // ======================
    val myFirstDice = Dice(6)
    println("Dice class roll: ${myFirstDice.roll()}")
}

// ======================
// FUNCTIONS
// ======================

fun printHello() {
    println("Hello Kotlin")
}

fun printBorder(border: String, timesToRepeat: Int) {
    repeat(timesToRepeat) {
        print(border)
    }
    println()
}

fun rollDice(): Int {
    return (1..6).random()
}

// ======================
// CLASS
// ======================

class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}
