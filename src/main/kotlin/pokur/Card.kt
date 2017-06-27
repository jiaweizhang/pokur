package pokur

/**
 * Created by jiaweizhang on 6/12/2017.
 */

class Card(val face: Int, val suit: Char) {

    fun toInt(): Int {
        val base = when (suit) {
            'S' -> 0
            'H' -> 13
            'C' -> 26
            else -> 39
        }
        return base + face
    }

    override fun toString(): String {
        val faceChar = when (face) {
            0 -> '2'
            1 -> '3'
            2 -> '4'
            3 -> '5'
            4 -> '6'
            5 -> '7'
            6 -> '8'
            7 -> '9'
            8 -> 'T'
            9 -> 'J'
            10 -> 'Q'
            11 -> 'K'
            else -> 'A'
        }
        return "$faceChar-$suit"
    }
}