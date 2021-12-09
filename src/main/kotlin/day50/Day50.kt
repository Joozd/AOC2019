package day50

import common.Solution
import common.extensions.grabLongs
import common.intcomputer.IntComputer

class Day50: Solution {
    override val day = 50
    private val program = readInput().grabLongs()

    override fun answer1(): Any {
        val comp = IntComputer(program){ print("${it.toInt().toChar()}")}
        comp.run()
        return "done"
    }

    override fun answer2(): Any = "two"
}