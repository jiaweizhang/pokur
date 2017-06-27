package pokur.calculate

import pokur.Card

/**
 * Created by jiaweizhang on 6/12/2017.
 */

class HandProcessor {
    private fun getSevenCards(communityCards: List<Card>, holeCards: PlayerHand): List<Card> {
        return communityCards.plus(holeCards.holeCards.first).plus(holeCards.holeCards.second)
    }

    fun rank(communityCards: List<Card>, holeCards: List<PlayerHand>): List<List<RankingUnit>> {
        val handRanking = mutableListOf<List<RankingUnit>>()

        val temporaryRanking = mutableListOf<RankingUnit>()
        holeCards.forEach {
            val rank = findHandValue7Complex(getSevenCards(communityCards, it))
            temporaryRanking.add(RankingUnit(it, rank))
        }

        val rankingGroups = temporaryRanking.groupBy { it.rank }

        rankingGroups.keys.toSortedSet().reversed().mapTo(handRanking) { rankingGroups.getValue(it) }
        return handRanking
    }

    fun findHandValue7Using5(sevenCards: List<Card>): Int {
        val fiveCardsList = listOf(sevenCards.subList(0, 5),
                listOf(sevenCards[0], sevenCards[1], sevenCards[2], sevenCards[3], sevenCards[5]),
                listOf(sevenCards[0], sevenCards[1], sevenCards[2], sevenCards[3], sevenCards[6]),
                listOf(sevenCards[0], sevenCards[1], sevenCards[2], sevenCards[4], sevenCards[5]),
                listOf(sevenCards[0], sevenCards[1], sevenCards[2], sevenCards[4], sevenCards[6]),

                listOf(sevenCards[0], sevenCards[1], sevenCards[2], sevenCards[5], sevenCards[6]),
                listOf(sevenCards[0], sevenCards[1], sevenCards[3], sevenCards[4], sevenCards[5]),
                listOf(sevenCards[0], sevenCards[1], sevenCards[3], sevenCards[4], sevenCards[6]),
                listOf(sevenCards[0], sevenCards[1], sevenCards[3], sevenCards[5], sevenCards[6]),
                listOf(sevenCards[0], sevenCards[1], sevenCards[4], sevenCards[5], sevenCards[6]),

                listOf(sevenCards[0], sevenCards[2], sevenCards[3], sevenCards[4], sevenCards[5]),
                listOf(sevenCards[0], sevenCards[2], sevenCards[3], sevenCards[4], sevenCards[6]),
                listOf(sevenCards[0], sevenCards[2], sevenCards[3], sevenCards[5], sevenCards[6]),
                listOf(sevenCards[0], sevenCards[2], sevenCards[4], sevenCards[5], sevenCards[6]),
                listOf(sevenCards[0], sevenCards[3], sevenCards[4], sevenCards[5], sevenCards[6]),

                listOf(sevenCards[1], sevenCards[2], sevenCards[3], sevenCards[4], sevenCards[5]),
                listOf(sevenCards[1], sevenCards[2], sevenCards[3], sevenCards[4], sevenCards[6]),
                listOf(sevenCards[1], sevenCards[2], sevenCards[3], sevenCards[5], sevenCards[6]),
                listOf(sevenCards[1], sevenCards[2], sevenCards[4], sevenCards[5], sevenCards[6]),
                listOf(sevenCards[1], sevenCards[3], sevenCards[4], sevenCards[5], sevenCards[6]),

                listOf(sevenCards[2], sevenCards[3], sevenCards[4], sevenCards[5], sevenCards[6]))

        return fiveCardsList.map {
            findHandValue5(it)
        }.max()!!.toInt()
    }

    fun findHandValue6Using5(sixCards: List<Card>): Int {
        val fiveCardsList = listOf(sixCards.subList(0, 5),
                listOf(sixCards[0], sixCards[2], sixCards[3], sixCards[4], sixCards[5]),
                listOf(sixCards[0], sixCards[1], sixCards[3], sixCards[4], sixCards[5]),
                listOf(sixCards[0], sixCards[1], sixCards[2], sixCards[4], sixCards[5]),
                listOf(sixCards[0], sixCards[1], sixCards[2], sixCards[3], sixCards[5]),
                listOf(sixCards[1], sixCards[2], sixCards[3], sixCards[4], sixCards[5]))

        return fiveCardsList.map {
            findHandValue5(it)
        }.max()!!.toInt()
    }

    fun findHandValue5(fiveCards: List<Card>): Int {
        // find handValue using grouping
        var pair1 = -1
        var pair2 = -1

        var set = -1

        var high2 = -1
        var high3 = -1
        var high4 = -1

        val one = fiveCards[0]
        val two = fiveCards[1]
        val three = fiveCards[2]
        val four = fiveCards[3]
        val five = fiveCards[4]

        // process first card
        var high1 = one.face

        // process second card
        if (two.face == high1) {
            // 2
            pair1 = high1
            high1 = -1
        } else {
            // 1 1
            high2 = two.face
        }

        // process third card
        if (three.face == pair1) {
            // 3
            set = pair1
            pair1 = -1
        } else if (pair1 != -1) {
            // 2 1
            high1 = three.face
        } else {
            if (three.face == high1) {
                // 2 1
                pair1 = high1
                high1 = high2
                high2 = -1
            } else if (three.face == high2) {
                // 2 1
                pair1 = high2
                high2 = -1
            } else {
                // 1 1 1
                high3 = three.face
            }
        }

        // process fourth card
        if (four.face == set) {
            // 4 - can return quad
            return (8 shl 20) + (set shl 16) + (five.face shl 12)
        } else if (set != -1) {
            // 3 1
            high1 = four.face
        } else {
            if (four.face == pair1) {
                // 3 1
                set = pair1
                pair1 = -1
            } else if ((pair1 != -1) and (four.face == high1)) {
                // 2 2
                pair2 = high1
                high1 = -1
            } else if ((pair1 != -1)) {
                high2 = four.face
            } else if (four.face == high1) {
                // 2 1 1
                pair1 = high1
                high1 = high2
                high2 = high3
                high3 = -1
            } else if (four.face == high2) {
                // 2 1 1
                pair1 = high2
                high2 = high3
                high3 = -1
            } else if (four.face == high3) {
                // 2 1 1
                pair1 = high3
                high3 = -1
            } else {
                // 1 1 1 1
                high4 = four.face
            }
        }

        // process fifth card
        if (five.face == set) {
            // 4 - return quad
            return (8 shl 20) + (set shl 16) + (high1 shl 12)
        } else {
            if ((set != -1) and (five.face == high1)) {
                // 3 2 - return FH
                return (7 shl 20) + (set shl 16) + (high1 shl 12)
            } else if (set != -1) {
                // 3 1 1 - return set
                return (4 shl 20) + (set shl 16) + ((if (high1 > five.face) high1 else five.face) shl 12) + ((if (high1 > five.face) five.face else high1) shl 8)
            } else if (five.face == pair2) {
                // 3 2 - return FH
                return (7 shl 20) + (pair2 shl 16) + (pair1 shl 12)
            } else if ((five.face == pair1) and (pair2 != -1)) {
                // 3 2 - return FH
                return (7 shl 20) + (pair1 shl 16) + (pair2 shl 12)
            } else if ((pair1 != -1) and (pair2 != -1)) {
                // 2 2 1
                return (3 shl 20) + ((if (pair1 > pair2) pair1 else pair2) shl 16) + ((if (pair1 > pair2) pair2 else pair1) shl 12) + (five.face shl 8)
            } else if (five.face == pair1) {
                // 3 1 1
                return (4 shl 20) + (pair1 shl 16) + ((if (high1 > high2) high1 else high2) shl 12) + ((if (high1 > high2) high2 else high1) shl 8)
            } else if ((pair1 != -1) and (five.face == high1)) {
                // 2 2 1
                return (3 shl 20) + ((if (pair1 > high1) pair1 else high1) shl 16) + ((if (pair1 > high1) high1 else pair1) shl 12) + (high2 shl 8)
            } else if ((pair1 != -1) and (five.face == high2)) {
                // 2 2 1
                return (3 shl 20) + ((if (pair1 > high2) pair1 else high2) shl 16) + ((if (pair1 > high2) high2 else pair1) shl 12) + (high1 shl 8)
            } else if ((pair1 != -1)) {
                // 2 1 1 1
                return (2 shl 20) + (pair1 shl 16) + (order3(high1, high2, five.face) shl 4)
            } else if (five.face == high1) {
                // 2 1 1 1
                return (2 shl 20) + (high1 shl 16) + (order3(high2, high3, high4) shl 4)
            } else if (five.face == high2) {
                // 2 1 1 1
                return (2 shl 20) + (high2 shl 16) + (order3(high1, high3, high4) shl 4)
            } else if (five.face == high3) {
                // 2 1 1 1
                return (2 shl 20) + (high3 shl 16) + (order3(high1, high2, high4) shl 4)
            } else if (five.face == high4) {
                // 2 1 1 1
                return (2 shl 20) + (high4 shl 16) + (order3(high1, high2, high3) shl 4)
            } else {
                // 1 1 1 1 1
                val flush = ((one.suit == two.suit) and (one.suit == three.suit) and (one.suit == four.suit) and (one.suit == five.suit))
                val order5 = order5(one.face, two.face, three.face, four.face, five.face)
                val straight = order5 and 0b1111
                if (flush and (straight != 0)) {
                    return (9 shl 20) + (straight shl 16)
                }
                if (flush) {
                    return (6 shl 20) + (order5 shr 4)
                }
                if (straight != 0) {
                    return (5 shl 20) + (straight shl 16)
                }
                return (1 shl 20) + (order5 shr 4)
            }
        }
    }

    fun order3(one: Int, two: Int, three: Int): Int {
        var tmp: Int
        var a = one
        var b = two
        var c = three
        if (b < c) {
            tmp = b
            b = c
            c = tmp
        }
        if (a < c) {
            tmp = a
            a = c
            c = tmp
        }
        if (a < b) {
            tmp = a
            a = b
            b = tmp
        }
        return (a shl 8) + (b shl 4) + c
    }

    fun order5(one: Int, two: Int, three: Int, four: Int, five: Int): Int {
        var tmp: Int
        var a = one
        var b = two
        var c = three
        var d = four
        var e = five
        if (a < b) {
            tmp = a
            a = b
            b = tmp
        }
        if (d < e) {
            tmp = d
            d = e
            e = tmp
        }
        if (c < e) {
            tmp = c
            c = e
            e = tmp
        }
        if (c < d) {
            tmp = c
            c = d
            d = tmp
        }
        if (b < e) {
            tmp = b
            b = e
            e = tmp
        }
        if (a < d) {
            tmp = a
            a = d
            d = tmp
        }
        if (a < c) {
            tmp = a
            a = c
            c = tmp
        }
        if (b < d) {
            tmp = b
            b = d
            d = tmp
        }
        if (b < c) {
            tmp = b
            b = c
            c = tmp
        }
        val straight = if ((a == b + 1) and (a == c + 2) and (a == d + 3) and (a == e + 4)) {
            a
        } else if ((a == 12) and (b == 3) and (c == 2) and (d == 1) and (e == 0)) {
            3
        } else {
            0
        }
        return (a shl 20) + (b shl 16) + (c shl 12) + (d shl 8) + (e shl 4) + straight
    }

    fun findHandValue7Complex(sevenCards: List<Card>): Int {
        var type: Int

        val sortedCards = sevenCards.sortedByDescending {
            it.face
        }

        val groupedCards = sevenCards.groupBy {
            it.face
        }

        val first = sortedCards.get(0).face
        val second = sortedCards.get(1).face
        val third = sortedCards.get(2).face
        val fourth = sortedCards.get(3).face
        val fifth = sortedCards.get(4).face
        val sixth = sortedCards.get(5).face
        val seventh = sortedCards.get(6).face

        var twoPairTopPair = -1
        var twoPairBottomPair = -1
        var twoPairKicker = -1

        var triple = -1
        var tripleKicker = -1
        var tripleKicker2 = -1

        var pair = -1
        var pairKicker = -1
        var pairKicker2 = -1
        var pairKicker3 = -1

        var straightHigh = -1
        var spadesCount = 0
        var heartsCount = 0
        var clubsCount = 0
        var diamondsCount = 0

        when (groupedCards.size) {
            2 -> {
                // 4 3 - Q
                // if count quad, cannot possibly be SF
                if (first == fourth) {
                    return (8 shl 20) + (first shl 16) + (fifth shl 12)
                } else {
                    return (8 shl 20) + (fourth shl 16) + (first shl 12)
                }
            }
            3 -> {
                // 4 2 1 - Q
                if (groupedCards.any { it.value.size == 4 }) {
                    if (first == second) {
                        if (second == third) {
                            // quad is top
                            return (8 shl 20) + (first shl 16) + (fifth shl 12)
                        } else {
                            // pair is top
                            if (third == fourth) {
                                // quad is after top
                                return (8 shl 20) + (third shl 16) + (first shl 12)
                            } else {
                                // quad is last
                                return (8 shl 20) + (fourth shl 16) + (first shl 12)
                            }
                        }
                    } else {
                        if (second == fourth) {
                            // 1 4 2
                            return (8 shl 20) + (second shl 16) + (first shl 12)
                        } else {
                            // 1 2 4
                            return (8 shl 20) + (fourth shl 16) + (first shl 12)
                        }
                    }
                }

                // 3 3 1 - FH
                else if (groupedCards.any { it.value.size == 1 }) {
                    if (first == second) {
                        // 3 3 1 or 3 1 3
                        if (fourth == fifth) {
                            // 3 3 1
                            return (7 shl 20) + (first shl 16) + (fourth shl 12)
                        } else {
                            // 3 1 3
                            return (7 shl 20) + (first shl 16) + (fifth shl 12)
                        }
                    } else {
                        // 1 3 3
                        return (7 shl 20) + (second shl 16) + (fifth shl 12)
                    }
                }

                // 3 2 2 - FH
                else {
                    if (second == third) {
                        // 3 2 2
                        return (7 shl 20) + (first shl 16) + (fourth shl 12)
                    } else if (fourth == fifth) {
                        // 2 3 2
                        return (7 shl 20) + (third shl 16) + (first shl 12)
                    } else {
                        // 2 2 3
                        return (7 shl 20) + (fifth shl 16) + (first shl 12)
                    }
                }
            }
            4 -> {
                // 4 1 1 1
                if (groupedCards.any { it.value.size == 4 }) {
                    if (first == second) {
                        // 4 1 1 1
                        return (8 shl 20) + (first shl 16) + (fifth shl 12)
                    } else if (second == third) {
                        // 1 4 1 1
                        return (8 shl 20) + (second shl 16) + (first shl 12)
                    } else if (third == fourth) {
                        // 1 1 4 1
                        return (8 shl 20) + (third shl 16) + (first shl 12)
                    } else {
                        // 1 1 1 4
                        return (8 shl 20) + (fourth shl 16) + (first shl 12)
                    }
                }

                // 3 2 1 1
                else if (groupedCards.any { it.value.size == 3 }) {
                    val advancedGroupedCards = sevenCards.groupingBy({
                        it.face
                    }).eachCount()

                    // triple and pair
                    var fhTriple = -1
                    var fhPair = -1

                    advancedGroupedCards.forEach {
                        if (it.value == 3) {
                            fhTriple = it.key
                        } else if (it.value == 2) {
                            fhPair = it.key
                        }
                    }
                    return (7 shl 20) + (fhTriple shl 16) + (fhPair shl 12)
                }

                // 2 2 2 1
                else {
                    type = 3
                    if (first != second) {
                        // 1 2 2 2
                        twoPairTopPair = second
                        twoPairBottomPair = fourth
                        twoPairKicker = first
                    } else if (sixth != seventh) {
                        // 2 2 2 1
                        twoPairTopPair = first
                        twoPairBottomPair = third
                        twoPairKicker = fifth
                    } else if (fourth != fifth) {
                        // 2 2 1 2
                        twoPairTopPair = first
                        twoPairBottomPair = third
                        twoPairKicker = fifth
                    } else {
                        // 2 1 2 2
                        twoPairTopPair = first
                        twoPairBottomPair = fourth
                        twoPairKicker = third
                    }
                }
            }
            5 -> {
                // 3 1 1 1 1
                if (groupedCards.any { it.value.size == 3 }) {
                    type = 4
                    if (first == second) {
                        // 3 1 1 1 1
                        triple = first
                        tripleKicker = fourth
                        tripleKicker2 = fifth
                    } else if (second == third) {
                        // 1 3 1 1 1
                        triple = second
                        tripleKicker = first
                        tripleKicker2 = fifth
                    } else if (third == fourth) {
                        // 1 1 3 1 1
                        triple = third
                        tripleKicker = first
                        tripleKicker2 = second
                    } else if (fourth == fifth) {
                        // 1 1 1 3 1
                        triple = fourth
                        tripleKicker = first
                        tripleKicker2 = second
                    } else {
                        // 1 1 1 1 3
                        triple = fifth
                        tripleKicker = first
                        tripleKicker2 = second
                    }
                }
                // 2 2 1 1 1

                else {
                    type = 3
                    if (first == second) {
                        twoPairTopPair = first
                        if (third == fourth) {
                            // 2 2 1 1 1
                            twoPairBottomPair = third
                            twoPairKicker = fifth
                        } else if (fourth == fifth) {
                            // 2 1 2 1 1
                            twoPairBottomPair = fourth
                            twoPairKicker = third
                        } else if (fifth == sixth) {
                            // 2 1 1 2 1
                            twoPairBottomPair = fifth
                            twoPairKicker = third
                        } else {
                            // 2 1 1 1 2
                            twoPairBottomPair = sixth
                            twoPairKicker = third
                        }
                    } else {
                        twoPairKicker = first
                        if (second == third) {
                            twoPairTopPair = second
                            if (fourth == fifth) {
                                // 1 2 2 1 1
                                twoPairBottomPair = fourth
                            } else if (fifth == sixth) {
                                // 1 2 1 2 1
                                twoPairBottomPair = fifth
                            } else {
                                // 1 2 1 1 2
                                twoPairBottomPair = sixth
                            }
                        } else if (third == fourth) {
                            twoPairTopPair = third
                            if (fifth == sixth) {
                                // 1 1 2 2 1
                                twoPairBottomPair = fifth
                            } else {
                                // 1 1 2 1 2
                                twoPairBottomPair = sixth
                            }
                        } else {
                            // 1 1 1 2 2
                            twoPairTopPair = fourth
                            twoPairBottomPair = sixth
                        }
                    }
                }
            }
            6 -> {
                type = 2
                if (first == second) {
                    // 2 1 1 1 1 1
                    pair = first
                    pairKicker = third
                    pairKicker2 = fourth
                    pairKicker3 = fifth
                } else if (second == third) {
                    // 1 2 1 1 1 1
                    pair = second
                    pairKicker = first
                    pairKicker2 = fourth
                    pairKicker3 = fifth
                } else if (third == fourth) {
                    // 1 1 2 1 1 1
                    pair = third
                    pairKicker = first
                    pairKicker2 = second
                    pairKicker3 = fifth
                } else if (fourth == fifth) {
                    // 1 1 1 2 1 1
                    pair = fourth
                    pairKicker = first
                    pairKicker2 = second
                    pairKicker3 = third
                } else if (fifth == sixth) {
                    // 1 1 1 1 2 1
                    pair = fifth
                    pairKicker = first
                    pairKicker2 = second
                    pairKicker3 = third
                } else {
                    // 1 1 1 1 1 2
                    pair = sixth
                    pairKicker = first
                    pairKicker2 = second
                    pairKicker3 = third
                }
            }
            else -> {
                type = 1
            }
        }

        sevenCards.forEach {
            // fill suits - technically don't need to iterate all 7
            val suit = it.suit
            if (suit == 'S') {
                spadesCount++
            } else if (suit == 'H') {
                heartsCount++
            } else if (suit == 'C') {
                clubsCount++
            } else if (suit == 'D') {
                diamondsCount++
            }
        }
        // is flush
        val flushSuit = when {
            spadesCount >= 5 -> 'S'
            heartsCount >= 5 -> 'H'
            clubsCount >= 5 -> 'C'
            diamondsCount >= 5 -> 'D'
            else -> 'N'
        }

        // look for straight
        val distinctCards = sortedCards.distinctBy {
            it.face
        }


        if (distinctCards.size == 5) {
            if (distinctCards.get(0).face == distinctCards.get(4).face + 4) {
                // straight
                type = 5
                straightHigh = distinctCards.get(0).face
                if (findStraightFlush(flushSuit, straightHigh, groupedCards)) {
                    return (9 shl 20) + (straightHigh shl 16)
                }
            }
            if ((distinctCards.get(1).face == 3)
                    and (distinctCards.get(4).face == 0)
                    and (distinctCards.get(0).face == 12)) {
                // 5-high straight
                type = 5
                if (straightHigh < 3) {
                    straightHigh = 3
                }
                if (findFiveHighStraightFlush(flushSuit, groupedCards)) {
                    return (9 shl 20) + (3 shl 16)
                }
            }
        } else if (distinctCards.size == 6) {
            if (distinctCards.get(0).face == distinctCards.get(4).face + 4) {
                // straight
                type = 5
                straightHigh = distinctCards.get(0).face
                if (findStraightFlush(flushSuit, straightHigh, groupedCards)) {
                    return (9 shl 20) + (straightHigh shl 16)
                }
            }
            if (distinctCards.get(1).face == distinctCards.get(5).face + 4) {
                type = 5
                if (straightHigh < distinctCards.get(1).face) {
                    straightHigh = distinctCards.get(1).face
                }
                if (findStraightFlush(flushSuit, distinctCards.get(1).face, groupedCards)) {
                    return (9 shl 20) + (distinctCards.get(1).face shl 16)
                }
            }
            if ((distinctCards.get(2).face == 3)
                    and (distinctCards.get(5).face == 0)
                    and (distinctCards.get(0).face == 12)) {
                // 5-high straight
                type = 5
                if (straightHigh < 3) {
                    straightHigh = 3
                }
                if (findFiveHighStraightFlush(flushSuit, groupedCards)) {
                    return (9 shl 20) + (3 shl 16)
                }
            }
        } else if (distinctCards.size == 7) {
            // size 7 - all different digits
            if (distinctCards.get(0).face == distinctCards.get(4).face + 4) {
                type = 5
                straightHigh = distinctCards.get(0).face
                if (findStraightFlush(flushSuit, straightHigh, groupedCards)) {
                    return (9 shl 20) + (straightHigh shl 16)
                }
            }
            if (distinctCards.get(1).face == distinctCards.get(5).face + 4) {
                type = 5
                if (straightHigh < distinctCards.get(1).face) {
                    straightHigh = distinctCards.get(1).face
                }
                if (findStraightFlush(flushSuit, distinctCards.get(1).face, groupedCards)) {
                    return (9 shl 20) + (distinctCards.get(1).face shl 16)
                }
            }
            if (distinctCards.get(2).face == distinctCards.get(6).face + 4) {
                type = 5
                if (straightHigh < distinctCards.get(2).face) {
                    straightHigh = distinctCards.get(2).face
                }
                if (findStraightFlush(flushSuit, distinctCards.get(2).face, groupedCards)) {
                    return (9 shl 20) + (distinctCards.get(2).face shl 16)
                }
            }
            if ((distinctCards.get(3).face == 3)
                    and (distinctCards.get(6).face == 0)
                    and (distinctCards.get(0).face == 12)) {
                // 5-high straight
                type = 5
                if (straightHigh < 3) {
                    straightHigh = 3
                }
                if (findFiveHighStraightFlush(flushSuit, groupedCards)) {
                    return (9 shl 20) + (3 shl 16)
                }
            }

        }

        // SF, Q, and FH have returned
        // return flush
        if (flushSuit != 'N') {
            // get five highest of suit
            val sortedFlush = sortedCards.filter {
                it.suit == flushSuit
            }
            return (6 shl 20) + (sortedFlush.get(0).face shl 16) + (sortedFlush.get(1).face shl 12) + (sortedFlush.get(2).face shl 8) + (sortedFlush.get(3).face shl 4) + (sortedFlush.get(4).face)
        }

        // return straight
        if (type == 5) {
            return (5 shl 20) + (straightHigh shl 16)
        }

        // return triple
        if (type == 4) {
            return (4 shl 20) + (triple shl 16) + (tripleKicker shl 12) + (tripleKicker2 shl 8)
        }

        // two pair
        if (type == 3) {
            return (3 shl 20) + (twoPairTopPair shl 16) + (twoPairBottomPair shl 12) + (twoPairKicker shl 8)
        }

        // pair
        if (type == 2) {
            return (2 shl 20) + (pair shl 16) + (pairKicker shl 12) + (pairKicker2 shl 8) + (pairKicker3 shl 4)
        }

        if (type == 1) {
            return (1 shl 20) + (first shl 16) + (second shl 12) + (third shl 8) + (fourth shl 4) + fifth
        }

        throw Exception("Impossible type")
    }

    fun findStraightFlush(flushSuit: Char, straightHigh: Int, groupedCards: Map<Int, List<Card>>): Boolean {
        if (flushSuit != 'N') {
            // check series of 5 for flush

            var isStraightFlush = true
            for (i in straightHigh.downTo(straightHigh - 4)) {
                var suitFoundInCurrent = false
                for (j in groupedCards.getValue(i)) {
                    if (j.suit == flushSuit) {
                        suitFoundInCurrent = true
                        break
                    }
                }
                if (!suitFoundInCurrent) {
                    isStraightFlush = false
                }
            }
            if (isStraightFlush) {
                return true
            }
        }
        return false
    }

    fun findFiveHighStraightFlush(flushSuit: Char, groupedCards: Map<Int, List<Card>>): Boolean {
        if (flushSuit != 'N') {
            var isStraightFlush = true
            for (i in 0..3) {
                var suitFoundInCurrent = false
                for (j in groupedCards.getValue(i)) {
                    if (j.suit == flushSuit) {
                        suitFoundInCurrent = true
                        break
                    }
                }
                if (!suitFoundInCurrent) {
                    isStraightFlush = false
                }
            }
            var aceSuitCorrect = false
            for (i in groupedCards.getValue(12)) {
                if (i.suit == flushSuit) {
                    aceSuitCorrect = true
                }
            }
            if (isStraightFlush and aceSuitCorrect) {
                return true
            }
        }
        return false
    }
}