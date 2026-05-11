package com.example.coroutinesample

import kotlinx.coroutines.*
import kotlin.random.Random

// =======================
// OBJECT
// =======================
object DataProviderManager {
    fun getRandomValue(): Double {
        return Random.nextDouble(1.0, 100.0)
    }
}

// =======================
// ENUM
// =======================
enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

// =======================
// SUSPEND FUNCTION
// =======================
suspend fun getValue(): Double {
    delay(1000) // giả lập tác vụ lâu
    return DataProviderManager.getRandomValue()
}

// =======================
// GỌI SUSPEND TỪ SUSPEND
// =======================
suspend fun processValue(): Double {
    val value = getValue()
    return value * 2
}

// =======================
// MAIN
// =======================
fun main() {

    println("=== BÀI 4: COROUTINE ===")

    // -----------------------
    // runBlocking
    // -----------------------
    runBlocking {
        val output = getValue()
        println("runBlocking output: $output")
    }

    // -----------------------
    // GlobalScope.launch
    // -----------------------
    val job: Job = GlobalScope.launch {
        val output = getValue()
        println("GlobalScope.launch output: $output")
    }

    // Hủy coroutine
    job.cancel()

    // -----------------------
    // async / await
    // -----------------------
    runBlocking {
        val deferred = async {
            processValue()
        }

        println("Async result: ${deferred.await()}")
    }

    // -----------------------
    // TRY / CATCH
    // -----------------------
    try {
        val number = 10 / 0
        println(number)
    } catch (exception: Exception) {
        println("Caught exception: ${exception.message}")
    }

    // -----------------------
    // ENUM
    // -----------------------
    val direction = Direction.NORTH

    when (direction) {
        Direction.NORTH -> println("Going North")
        Direction.SOUTH -> println("Going South")
        Direction.WEST  -> println("Going West")
        Direction.EAST  -> println("Going East")
    }

    println("=== KẾT THÚC BÀI 4 ===")
}
