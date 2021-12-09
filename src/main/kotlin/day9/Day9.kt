package day9

import common.Solution
import common.extensions.grabLongs
import common.intcomputer.IntComputer

class Day9: Solution {
    override val day = 9
    private val program = readInput().grabLongs()

    override fun answer1(): Any {
        val comp = IntComputer(program) { println("BOOST code: $it") }
        comp.setInputProvider{
            println("Please input which question you want answered (1 or 2)")
            readLine()?.toLong()
        }
        comp.run()
        return Unit
    }

    override fun answer2(): Any = "two"
}