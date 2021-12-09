package day2

import common.Solution
import common.extensions.grabLongs
import common.intcomputer.IntComputer

class Day2: Solution {
    override val day = 2
    private val program = readInput().grabLongs()
    private val intComputer = IntComputer(program)

    override fun answer1(): Any {
        intComputer[1] = 12L
        intComputer[2] = 2
        intComputer.run()
        return intComputer[0]
    }

    override fun answer2(): Any {
        (0L..99L).forEach { noun ->
            (0L..99L).forEach{ verb ->
                intComputer.reset()
                intComputer[1] = noun
                intComputer[2] = verb
                intComputer.run()
                if (intComputer[0] == WANTED_RESULT_Q2)
                    return 100* noun + verb
            }
        }
        return Unit
    }

    companion object{
        private const val WANTED_RESULT_Q2 = 19690720L
    }
}