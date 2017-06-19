import io.kotlintest.specs.StringSpec
import pokur.calculate.HandProcessor
import pokur.generate.CardGenerator

/**
 * Created by jiaweizhang on 6/19/2017.
 */
class ProbabilityVerifyTests : StringSpec() {
    init {


        val cg = CardGenerator()
        val hp = HandProcessor()

        "Counts 5" {
            var sfCount = 0
            var qCount = 0
            var fhCount = 0
            var fCount = 0
            var sCount = 0
            var tCount = 0
            var tpCount = 0
            var pCount = 0
            var hCount = 0

            for (i in 1..1000000) {
                val fiveCards = cg.generateCardsBySet(5)
                val handValue = hp.findInt5(fiveCards)
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

            println("SF: $sfCount")
            println("Q : $qCount")
            println("FH: $fhCount")
            println("F : $fCount")
            println("S : $sCount")
            println("T : $tCount")
            println("TP: $tpCount")
            println("P : $pCount")
            println("H : $hCount")
            println()
        }

        "Counts 7" {
            var sfCount = 0
            var qCount = 0
            var fhCount = 0
            var fCount = 0
            var sCount = 0
            var tCount = 0
            var tpCount = 0
            var pCount = 0
            var hCount = 0

            for (i in 1..100000) {
                val sevenCards = cg.generateCardsBySet(7)
                val handValue = hp.findInt7(sevenCards)
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

            println("SF: $sfCount")
            println("Q : $qCount")
            println("FH: $fhCount")
            println("F : $fCount")
            println("S : $sCount")
            println("T : $tCount")
            println("TP: $tpCount")
            println("P : $pCount")
            println("H : $hCount")
        }
    }
}