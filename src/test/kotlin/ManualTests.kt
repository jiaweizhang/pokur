import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import pokur.Card
import pokur.calculate.HandProcessor
import pokur.calculate.PlayerHand

/**
 * Created by jiaweizhang on 6/12/2017.
 *
 * 100% code coverage in HandProcessor ranking
 */

class ManualTests : StringSpec() {

    class SevenCards(one: Card, two: Card, three: Card, four: Card, five: Card, six: Card, seven: Card) {
        val communityCards = listOf(one, two, three, four, five)
        val playerHands = listOf(PlayerHand(1, Pair(six, seven), 1))
        fun process(handProcessor: HandProcessor, expected1: Int, expected2: Int, expected3: Int = 0, expected4: Int = 0, expected5: Int = 0, expected6: Int = 0) {
            handProcessor.rank(communityCards, playerHands).get(0).get(0).rank
                    .shouldBe((expected1 shl 20) + (expected2 shl 16) + (expected3 shl 12) + (expected4 shl 8) + (expected5 shl 4) + expected6)
        }
    }

    init {
        val hp = HandProcessor()

        "SF Ace High" {
            SevenCards(Card(12, 'S'), Card(11, 'S'), Card(10, 'S'), Card(9, 'S'), Card(8, 'S'), Card(2, 'S'), Card(5, 'H'))
                    .process(hp, 9, 12)
        }

        "SF King High with Straight Ace High" {
            SevenCards(Card(11, 'S'), Card(10, 'S'), Card(9, 'S'), Card(8, 'S'), Card(9, 'H'), Card(7, 'S'), Card(12, 'H'))
                    .process(hp, 9, 11)
        }

        "SF Seven High" {
            SevenCards(Card(2, 'S'), Card(3, 'S'), Card(4, 'S'), Card(1, 'S'), Card(6, 'H'), Card(0, 'S'), Card(5, 'S'))
                    .process(hp, 9, 5)
        }

        "SF Six High" {
            SevenCards(Card(2, 'S'), Card(3, 'S'), Card(4, 'S'), Card(1, 'S'), Card(4, 'H'), Card(0, 'S'), Card(5, 'H'))
                    .process(hp, 9, 4)
        }

        "SF Five High" {
            SevenCards(Card(2, 'S'), Card(3, 'S'), Card(1, 'D'), Card(1, 'S'), Card(4, 'H'), Card(0, 'S'), Card(12, 'S'))
                    .process(hp, 9, 3)
        }

        "Q 4 3" {
            SevenCards(Card(12, 'S'), Card(12, 'D'), Card(12, 'H'), Card(12, 'C'), Card(8, 'S'), Card(8, 'H'), Card(8, 'D'))
                    .process(hp, 8, 12, 8)
        }

        "Q 3 4" {
            SevenCards(Card(12, 'S'), Card(12, 'D'), Card(12, 'H'), Card(8, 'C'), Card(8, 'S'), Card(8, 'H'), Card(8, 'D'))
                    .process(hp, 8, 8, 12)
        }

        "Q 4 2 1" {
            SevenCards(Card(12, 'S'), Card(12, 'D'), Card(12, 'H'), Card(12, 'C'), Card(8, 'S'), Card(8, 'H'), Card(7, 'D'))
                    .process(hp, 8, 12, 8)
        }

        "Q 4 1 2" {
            SevenCards(Card(12, 'S'), Card(12, 'D'), Card(12, 'H'), Card(12, 'C'), Card(8, 'S'), Card(8, 'H'), Card(10, 'D'))
                    .process(hp, 8, 12, 10)
        }

        "Q 2 4 1" {
            SevenCards(Card(12, 'S'), Card(12, 'D'), Card(8, 'D'), Card(8, 'C'), Card(8, 'S'), Card(8, 'H'), Card(7, 'D'))
                    .process(hp, 8, 8, 12)
        }

        "Q 2 1 4" {
            SevenCards(Card(12, 'S'), Card(12, 'D'), Card(8, 'D'), Card(8, 'C'), Card(8, 'S'), Card(8, 'H'), Card(9, 'D'))
                    .process(hp, 8, 8, 12)
        }

        "Q 1 4 2" {
            SevenCards(Card(12, 'S'), Card(7, 'D'), Card(8, 'D'), Card(8, 'C'), Card(8, 'S'), Card(8, 'H'), Card(7, 'H'))
                    .process(hp, 8, 8, 12)
        }

        "Q 1 2 4" {
            SevenCards(Card(12, 'S'), Card(10, 'D'), Card(8, 'D'), Card(8, 'C'), Card(8, 'S'), Card(8, 'H'), Card(10, 'C'))
                    .process(hp, 8, 8, 12)
        }

        "Q 4 1 1 1" {
            SevenCards(Card(12, 'S'), Card(12, 'D'), Card(12, 'H'), Card(12, 'C'), Card(8, 'S'), Card(9, 'H'), Card(10, 'C'))
                    .process(hp, 8, 12, 10)
        }

        "Q 1 4 1 1" {
            SevenCards(Card(11, 'S'), Card(11, 'D'), Card(11, 'H'), Card(11, 'C'), Card(12, 'S'), Card(9, 'H'), Card(10, 'C'))
                    .process(hp, 8, 11, 12)
        }

        "Q 1 1 4 1" {
            SevenCards(Card(9, 'S'), Card(9, 'D'), Card(9, 'H'), Card(9, 'C'), Card(7, 'S'), Card(10, 'H'), Card(11, 'C'))
                    .process(hp, 8, 9, 11)
        }

        "Q 1 1 1 4" {
            SevenCards(Card(9, 'S'), Card(9, 'D'), Card(9, 'H'), Card(9, 'C'), Card(12, 'S'), Card(10, 'H'), Card(11, 'C'))
                    .process(hp, 8, 9, 12)
        }

        "FH 3 3 1" {
            SevenCards(Card(9, 'S'), Card(9, 'D'), Card(9, 'H'), Card(8, 'C'), Card(8, 'S'), Card(8, 'H'), Card(7, 'C'))
                    .process(hp, 7, 9, 8)
        }

        "FH 3 1 3" {
            SevenCards(Card(9, 'S'), Card(9, 'D'), Card(9, 'H'), Card(2, 'C'), Card(2, 'S'), Card(2, 'H'), Card(7, 'C'))
                    .process(hp, 7, 9, 2)
        }

        "FH 1 3 3" {
            SevenCards(Card(9, 'S'), Card(9, 'D'), Card(9, 'H'), Card(8, 'C'), Card(8, 'S'), Card(8, 'H'), Card(11, 'C'))
                    .process(hp, 7, 9, 8)
        }

        "FH 3 2 2" {
            SevenCards(Card(9, 'S'), Card(9, 'D'), Card(9, 'H'), Card(8, 'C'), Card(8, 'S'), Card(7, 'H'), Card(7, 'C'))
                    .process(hp, 7, 9, 8)
        }

        "FH 2 3 2" {
            SevenCards(Card(9, 'S'), Card(9, 'D'), Card(9, 'H'), Card(8, 'C'), Card(8, 'S'), Card(12, 'H'), Card(12, 'C'))
                    .process(hp, 7, 9, 12)
        }

        "FH 2 2 3" {
            SevenCards(Card(9, 'S'), Card(9, 'D'), Card(7, 'D'), Card(8, 'C'), Card(8, 'S'), Card(7, 'H'), Card(7, 'C'))
                    .process(hp, 7, 7, 9)
        }

        "FH 3 2 1 1" {
            SevenCards(Card(9, 'S'), Card(9, 'D'), Card(9, 'C'), Card(8, 'C'), Card(8, 'S'), Card(7, 'H'), Card(6, 'C'))
                    .process(hp, 7, 9, 8)
        }

        "FH 3 1 2 1" {
            SevenCards(Card(9, 'S'), Card(9, 'D'), Card(9, 'C'), Card(8, 'C'), Card(7, 'S'), Card(7, 'H'), Card(6, 'C'))
                    .process(hp, 7, 9, 7)
        }

        "FH 3 1 1 2" {
            SevenCards(Card(9, 'S'), Card(9, 'D'), Card(9, 'C'), Card(8, 'C'), Card(7, 'S'), Card(6, 'H'), Card(6, 'C'))
                    .process(hp, 7, 9, 6)
        }

        "FH 2 3 1 1" {
            SevenCards(Card(9, 'S'), Card(9, 'D'), Card(8, 'D'), Card(8, 'C'), Card(8, 'S'), Card(7, 'H'), Card(6, 'C'))
                    .process(hp, 7, 8, 9)
        }

        "FH 2 1 3 1" {
            SevenCards(Card(9, 'S'), Card(9, 'D'), Card(8, 'C'), Card(7, 'C'), Card(7, 'S'), Card(7, 'H'), Card(6, 'C'))
                    .process(hp, 7, 7, 9)
        }

        "FH 2 1 1 3" {
            SevenCards(Card(9, 'S'), Card(9, 'D'), Card(8, 'C'), Card(7, 'C'), Card(5, 'S'), Card(5, 'H'), Card(5, 'C'))
                    .process(hp, 7, 5, 9)
        }

        "FH 1 3 2 1" {
            SevenCards(Card(9, 'S'), Card(8, 'D'), Card(8, 'C'), Card(8, 'H'), Card(7, 'S'), Card(7, 'H'), Card(6, 'C'))
                    .process(hp, 7, 8, 7)
        }

        "FH 1 3 1 2" {
            SevenCards(Card(9, 'S'), Card(8, 'D'), Card(8, 'C'), Card(8, 'H'), Card(6, 'S'), Card(5, 'H'), Card(5, 'C'))
                    .process(hp, 7, 8, 5)
        }

        "FH 1 2 3 1" {
            SevenCards(Card(9, 'S'), Card(8, 'D'), Card(8, 'C'), Card(7, 'C'), Card(7, 'S'), Card(7, 'H'), Card(6, 'C'))
                    .process(hp, 7, 7, 8)
        }

        "FH 1 2 1 3" {
            SevenCards(Card(9, 'S'), Card(8, 'D'), Card(8, 'C'), Card(7, 'C'), Card(6, 'S'), Card(6, 'H'), Card(6, 'C'))
                    .process(hp, 7, 6, 8)
        }

        "FH 1 1 3 2" {
            SevenCards(Card(10, 'S'), Card(9, 'D'), Card(8, 'H'), Card(8, 'C'), Card(8, 'S'), Card(7, 'H'), Card(7, 'C'))
                    .process(hp, 7, 8, 7)
        }

        "FH 1 1 2 3" {
            SevenCards(Card(10, 'S'), Card(9, 'D'), Card(8, 'H'), Card(8, 'C'), Card(7, 'S'), Card(7, 'H'), Card(7, 'C'))
                    .process(hp, 7, 7, 8)
        }

        "F A K Q J 9" {
            SevenCards(Card(12, 'S'), Card(11, 'S'), Card(10, 'S'), Card(9, 'S'), Card(7, 'S'), Card(7, 'H'), Card(7, 'C'))
                    .process(hp, 6, 12, 11, 10, 9, 7)
        }

        "F 10 8 6 4 2" {
            SevenCards(Card(8, 'S'), Card(6, 'S'), Card(4, 'S'), Card(2, 'S'), Card(0, 'S'), Card(7, 'H'), Card(7, 'C'))
                    .process(hp, 6, 8, 6, 4, 2, 0)
        }

        "F 10 8 6 4 3" {
            SevenCards(Card(8, 'S'), Card(6, 'S'), Card(4, 'S'), Card(2, 'S'), Card(1, 'S'), Card(0, 'S'), Card(7, 'C'))
                    .process(hp, 6, 8, 6, 4, 2, 1)
        }

        "F 10 8 7 5 4" {
            SevenCards(Card(8, 'S'), Card(6, 'S'), Card(5, 'S'), Card(3, 'S'), Card(2, 'S'), Card(1, 'S'), Card(0, 'S'))
                    .process(hp, 6, 8, 6, 5, 3, 2)
        }

        "F 10 8 7 5 4" {
            SevenCards(Card(8, 'S'), Card(6, 'S'), Card(5, 'S'), Card(3, 'S'), Card(2, 'S'), Card(4, 'H'), Card(7, 'C'))
                    .process(hp, 6, 8, 6, 5, 3, 2)
        }

        "S A" {
            SevenCards(Card(12, 'S'), Card(11, 'S'), Card(10, 'D'), Card(9, 'S'), Card(8, 'S'), Card(1, 'D'), Card(0, 'C'))
                    .process(hp, 5, 12)
        }

        "S A with more lower straight" {
            SevenCards(Card(12, 'S'), Card(11, 'S'), Card(10, 'D'), Card(9, 'S'), Card(8, 'S'), Card(7, 'D'), Card(6, 'C'))
                    .process(hp, 5, 12)
        }

        "S 5" {
            SevenCards(Card(3, 'S'), Card(2, 'S'), Card(1, 'D'), Card(0, 'S'), Card(12, 'S'), Card(1, 'C'), Card(0, 'C'))
                    .process(hp, 5, 3)
        }

        "T 3 1 1 1 1" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(8, 'D'), Card(7, 'S'), Card(6, 'S'), Card(4, 'H'), Card(2, 'C'))
                    .process(hp, 4, 8, 7, 6)
        }

        "T 1 3 1 1 1" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(8, 'D'), Card(7, 'S'), Card(6, 'S'), Card(4, 'H'), Card(10, 'C'))
                    .process(hp, 4, 8, 10, 7)
        }

        "T 1 1 3 1 1" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(8, 'D'), Card(10, 'S'), Card(9, 'S'), Card(4, 'H'), Card(2, 'C'))
                    .process(hp, 4, 8, 10, 9)
        }

        "T 1 1 1 3 1" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(8, 'D'), Card(11, 'S'), Card(10, 'S'), Card(9, 'H'), Card(2, 'C'))
                    .process(hp, 4, 8, 11, 10)
        }

        "T 1 1 1 3 1" {
            SevenCards(Card(2, 'S'), Card(2, 'H'), Card(2, 'D'), Card(11, 'S'), Card(10, 'S'), Card(9, 'H'), Card(12, 'C'))
                    .process(hp, 4, 2, 12, 11)
        }

        "TP 2 2 2 1" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(6, 'D'), Card(6, 'S'), Card(4, 'S'), Card(4, 'H'), Card(2, 'C'))
                    .process(hp, 3, 8, 6, 4)
        }

        "TP 2 2 1 2" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(6, 'D'), Card(6, 'S'), Card(4, 'S'), Card(2, 'H'), Card(2, 'C'))
                    .process(hp, 3, 8, 6, 4)
        }

        "TP 2 1 2 2" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(6, 'D'), Card(4, 'H'), Card(4, 'S'), Card(2, 'H'), Card(2, 'C'))
                    .process(hp, 3, 8, 4, 6)
        }

        "TP 1 2 2 2" {
            SevenCards(Card(8, 'S'), Card(6, 'H'), Card(6, 'D'), Card(4, 'H'), Card(4, 'S'), Card(2, 'H'), Card(2, 'C'))
                    .process(hp, 3, 6, 4, 8)
        }

        "TP 2 2 1 1 1" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(6, 'D'), Card(6, 'S'), Card(4, 'S'), Card(3, 'H'), Card(2, 'C'))
                    .process(hp, 3, 8, 6, 4)
        }

        "TP 2 1 2 1 1" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(6, 'D'), Card(6, 'S'), Card(4, 'S'), Card(7, 'H'), Card(2, 'C'))
                    .process(hp, 3, 8, 6, 7)
        }

        "TP 2 1 1 2 1" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(6, 'D'), Card(4, 'D'), Card(4, 'S'), Card(7, 'H'), Card(2, 'C'))
                    .process(hp, 3, 8, 4, 7)
        }

        "TP 2 1 1 1 2" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(6, 'D'), Card(5, 'S'), Card(4, 'S'), Card(2, 'H'), Card(2, 'C'))
                    .process(hp, 3, 8, 2, 6)
        }

        "TP 1 2 2 1 1" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(6, 'D'), Card(6, 'S'), Card(10, 'S'), Card(3, 'H'), Card(2, 'C'))
                    .process(hp, 3, 8, 6, 10)
        }

        "TP 1 2 1 2 1" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(6, 'D'), Card(6, 'S'), Card(10, 'S'), Card(7, 'H'), Card(2, 'C'))
                    .process(hp, 3, 8, 6, 10)
        }

        "TP 1 2 1 1 2" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(6, 'D'), Card(2, 'S'), Card(10, 'S'), Card(7, 'H'), Card(2, 'C'))
                    .process(hp, 3, 8, 2, 10)
        }

        "TP 1 1 2 2 1" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(6, 'D'), Card(6, 'S'), Card(10, 'S'), Card(11, 'H'), Card(2, 'C'))
                    .process(hp, 3, 8, 6, 11)
        }

        "TP 1 1 2 1 2" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(6, 'D'), Card(6, 'S'), Card(10, 'S'), Card(11, 'H'), Card(7, 'C'))
                    .process(hp, 3, 8, 6, 11)
        }

        "TP 1 1 1 2 2" {
            SevenCards(Card(8, 'S'), Card(12, 'H'), Card(6, 'D'), Card(6, 'S'), Card(2, 'S'), Card(11, 'H'), Card(2, 'C'))
                    .process(hp, 3, 6, 2, 12)
        }

        "P 2 1 1 1 1 1" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(6, 'D'), Card(5, 'S'), Card(4, 'S'), Card(3, 'H'), Card(1, 'C'))
                    .process(hp, 2, 8, 6, 5, 4)
        }

        "P 1 2 1 1 1 1" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(9, 'D'), Card(5, 'S'), Card(4, 'S'), Card(3, 'H'), Card(1, 'C'))
                    .process(hp, 2, 8, 9, 5, 4)
        }

        "P 1 1 2 1 1 1" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(9, 'D'), Card(11, 'S'), Card(4, 'S'), Card(3, 'H'), Card(1, 'C'))
                    .process(hp, 2, 8, 11, 9, 4)
        }

        "P 1 1 1 2 1 1" {
            SevenCards(Card(8, 'S'), Card(8, 'H'), Card(9, 'D'), Card(11, 'S'), Card(12, 'S'), Card(3, 'H'), Card(1, 'C'))
                    .process(hp, 2, 8, 12, 11, 9)
        }

        "P 1 1 1 1 2 1" {
            SevenCards(Card(6, 'S'), Card(6, 'H'), Card(9, 'D'), Card(11, 'S'), Card(12, 'S'), Card(8, 'H'), Card(1, 'C'))
                    .process(hp, 2, 6, 12, 11, 9)
        }

        "P 1 1 1 1 2 1" {
            SevenCards(Card(4, 'S'), Card(4, 'H'), Card(9, 'D'), Card(11, 'S'), Card(12, 'S'), Card(8, 'H'), Card(7, 'C'))
                    .process(hp, 2, 4, 12, 11, 9)
        }

        "H 1 1 1 1 1 1 1" {
            SevenCards(Card(4, 'S'), Card(5, 'H'), Card(9, 'D'), Card(11, 'S'), Card(12, 'S'), Card(8, 'H'), Card(7, 'C'))
                    .process(hp, 1, 12, 11, 9, 8, 7)
        }

        "H 1 1 1 1 1 1 1" {
            SevenCards(Card(4, 'S'), Card(5, 'H'), Card(9, 'D'), Card(0, 'S'), Card(2, 'S'), Card(8, 'H'), Card(7, 'C'))
                    .process(hp, 1, 9, 8, 7, 5, 4)
        }
    }
}