import io.kotlintest.specs.StringSpec
import pokur.calculate.HandProcessor
import pokur.generate.CardGenerator

/**
 * Created by jiaweizhang on 6/19/2017.
 */
class ProbabilityVerifyTests : StringSpec() {

    val runs = 1000000

    init {


        val cg = CardGenerator()
        val hp = HandProcessor()

        "Probs 5" {
            var sfCount = 0
            var qCount = 0
            var fhCount = 0
            var fCount = 0
            var sCount = 0
            var tCount = 0
            var tpCount = 0
            var pCount = 0
            var hCount = 0

            for (i in 1..runs) {
                val fiveCards = cg.generateCardsBySet(5)
                val handValue = hp.findHandValue5(fiveCards)
                when (handValue shr 20) {
                    9 -> sfCount++
                    8 -> qCount++
                    7 -> fhCount++
                    6 -> fCount++
                    5 -> sCount++
                    4 -> tCount++
                    3 -> tpCount++
                    2 -> pCount++
                    1 -> hCount++
                }
            }

            println("SF: ${sfCount * 100.0 / runs}")
            println("Q : ${qCount * 100.0 / runs}")
            println("FH: ${fhCount * 100.0 / runs}")
            println("F : ${fCount * 100.0 / runs}")
            println("S : ${sCount * 100.0 / runs}")
            println("T : ${tCount * 100.0 / runs}")
            println("TP: ${tpCount * 100.0 / runs}")
            println("P : ${pCount * 100.0 / runs}")
            println("H : ${hCount * 100.0 / runs}")
            println()
        }

        "Probs 6" {
            var sfCount = 0
            var qCount = 0
            var fhCount = 0
            var fCount = 0
            var sCount = 0
            var tCount = 0
            var tpCount = 0
            var pCount = 0
            var hCount = 0

            for (i in 1..runs) {
                val sixCards = cg.generateCardsBySet(6)
                val handValue = hp.findHandValue6Using5(sixCards)
                when (handValue shr 20) {
                    9 -> sfCount++
                    8 -> qCount++
                    7 -> fhCount++
                    6 -> fCount++
                    5 -> sCount++
                    4 -> tCount++
                    3 -> tpCount++
                    2 -> pCount++
                    1 -> hCount++
                }
            }

            println("SF: ${sfCount * 100.0 / runs}")
            println("Q : ${qCount * 100.0 / runs}")
            println("FH: ${fhCount * 100.0 / runs}")
            println("F : ${fCount * 100.0 / runs}")
            println("S : ${sCount * 100.0 / runs}")
            println("T : ${tCount * 100.0 / runs}")
            println("TP: ${tpCount * 100.0 / runs}")
            println("P : ${pCount * 100.0 / runs}")
            println("H : ${hCount * 100.0 / runs}")
            println()
        }

        "Probs 7" {
            var sfCount = 0
            var qCount = 0
            var fhCount = 0
            var fCount = 0
            var sCount = 0
            var tCount = 0
            var tpCount = 0
            var pCount = 0
            var hCount = 0

            for (i in 1..runs) {
                val sevenCards = cg.generateCardsBySet(7)
                val handValue = hp.findHandValue7Complex(sevenCards)
                when (handValue shr 20) {
                    9 -> sfCount++
                    8 -> qCount++
                    7 -> fhCount++
                    6 -> fCount++
                    5 -> sCount++
                    4 -> tCount++
                    3 -> tpCount++
                    2 -> pCount++
                    1 -> hCount++
                }
            }

            println("SF: ${sfCount * 100.0 / runs}")
            println("Q : ${qCount * 100.0 / runs}")
            println("FH: ${fhCount * 100.0 / runs}")
            println("F : ${fCount * 100.0 / runs}")
            println("S : ${sCount * 100.0 / runs}")
            println("T : ${tCount * 100.0 / runs}")
            println("TP: ${tpCount * 100.0 / runs}")
            println("P : ${pCount * 100.0 / runs}")
            println("H : ${hCount * 100.0 / runs}")
            println()
        }
    }
}