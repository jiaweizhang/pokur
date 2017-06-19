import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import pokur.calculate.HandProcessor
import pokur.generate.CardGenerator

/**
 * Created by jiaweizhang on 6/19/2017.
 */
class HandValueCorrectnessTests : StringSpec() {

    fun printHandValue(handValue: Int) {
        fun generateHandValue(handValue: Int): String {
            return """1: ${handValue shr 20}
            |2: ${handValue shr 16 and 0b1111}
            |3: ${handValue shr 12 and 0b1111}
            |4: ${handValue shr 8 and 0b1111}
            |5: ${handValue shr 4 and 0b1111}
            |6: ${handValue and 0b1111}""".trimMargin()
        }
        println(generateHandValue(handValue))
    }

    init {
        val hp = HandProcessor()
        val cg = CardGenerator()

        "Compare 7 using different methods" {
            for (i in 1..10000) {
                val sevenCards = cg.generateCardsByShuffle(7)
                val complex = hp.findHandValue7Complex(sevenCards)
                val using5 = hp.findHandValue7Using5(sevenCards)
                if (complex != using5) {
                    for (card in sevenCards) {
                        print("${card.toString()}  ")
                    }
                    println()
                    printHandValue(complex)
                    println()
                    printHandValue(using5)
                    println()
                    complex shouldBe using5
                }
            }
        }
    }
}