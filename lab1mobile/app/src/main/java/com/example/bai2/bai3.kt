fun main() {

    // ======================
    // 1. NHÓM (SET)
    // ======================
    println("=== SET ===")

    val numbers = listOf(0, 3, 8, 4, 0, 5, 5, 8, 9, 2)
    val setOfNumbers = numbers.toSet()
    println("Set from list: $setOfNumbers")

    val set1 = setOf(1, 2, 3)
    val set2 = mutableSetOf(3, 4, 5)

    println("Intersect: ${set1.intersect(set2)}") // [3]
    println("Union: ${set1.union(set2)}")         // [1,2,3,4,5]

    // ======================
    // 2. SƠ ĐỒ (MAP)
    // ======================
    println("\n=== MAP ===")

    val peopleAges = mutableMapOf<String, Int>(
        "Fred" to 30,
        "Ann" to 23
    )

    peopleAges.put("Barbara", 42)
    peopleAges["Joe"] = 51

    // Lặp map
    peopleAges.forEach {
        print("${it.key} is ${it.value}, ")
    }
    println()

    // Chuyển đổi từng phần tử
    println(
        peopleAges.map { "${it.key} is ${it.value}" }
            .joinToString(", ")
    )

    // Lọc phần tử
    val filteredNames = peopleAges.filter { it.key.length < 4 }
    println("Filtered names: $filteredNames")

    // ======================
    // 3. PHÉP TOÁN KHÁC TRÊN COLLECTION
    // ======================
    println("\n=== COLLECTION OPERATIONS ===")

    val words = listOf("about", "acute", "balloon", "best", "brief", "class")
    val filteredWords = words
        .filter { it.startsWith("b", ignoreCase = true) }
        .shuffled()
        .take(2)
        .sorted()

    println("Filtered words: $filteredWords")

    // ======================
    // 4. HÀM PHẠM VI
    // ======================
    println("\n=== SCOPE FUNCTIONS ===")

    val arguments: Map<String, String>? = mapOf("LETTER" to "A")
    var letterId = ""

    arguments?.let {
        letterId = it["LETTER"].toString()
    }
    println("Letter ID (let): $letterId")

    val binding = StringBuilder()
    binding.apply {
        append("Hello ")
        append("Kotlin")
    }
    println("Apply result: $binding")

    // ======================
    // 5. THUỘC TÍNH DỰ PHÒNG
    // ======================
    println("\n=== BACKING PROPERTY ===")

    val game = Game()
    println("Current word: ${game.currentScrambledWord}")

    // ======================
    // 6. LỆNH GỌI AN TOÀN
    // ======================
    println("\n=== SAFE CALL ===")

    val intentExtras: Map<String, String>? = null
    val safeLetter = intentExtras?.get("letter").toString()
    println("Safe call result: $safeLetter")

    // ======================
    // 7. LAMBDA
    // ======================
    println("\n=== LAMBDA ===")

    val triple: (Int) -> Int = { a -> a * 3 }
    println("Triple 5 = ${triple(5)}")

    // ======================
    // 8. COMPANION OBJECT
    // ======================
    println("\n=== COMPANION OBJECT ===")

    println("LETTER constant: ${DetailActivity.LETTER}")

    // ======================
    // 9. KHỞI TẠO TRỄ
    // ======================
    println("\n=== LATEINIT ===")

    val wordManager = WordManager()
    wordManager.initWord()
    println("Current word: ${wordManager.currentWord}")

    // ======================
    // 10. TOÁN TỬ ELVIS
    // ======================
    println("\n=== ELVIS OPERATOR ===")

    var quantity: Int? = null
    println(quantity ?: 0)

    quantity = 4
    println(quantity ?: 0)
}

// ======================
// CLASS PHỤ TRỢ
// ======================

class Game {
    private var _currentScrambledWord = "test"
    val currentScrambledWord: String
        get() = _currentScrambledWord
}

class DetailActivity {
    companion object {
        const val LETTER = "letter"
    }
}

class WordManager {
    lateinit var currentWord: String

    fun initWord() {
        currentWord = "Kotlin"
    }
}
