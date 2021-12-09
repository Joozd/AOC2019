package day5

import common.Solution
import common.extensions.grabLongs
import common.intcomputer.IntComputer

class Day5: Solution {
    override val day = 5
    private val program = readInput().grabLongs()
    private val computer = IntComputer(program){
        println(it)
    }

    override fun answer1(): Any {
        var result = 0L
        computer.setOnOutputListener{
            result = it // yes I am well aware this is a memory leak
        }
        computer.input(1)
        computer.run()
        return result
    }

    override fun answer2(): Any {
        //val computer = IntComputer(test3)
        computer.reset()
        // computer.debug = true
        var result = 0L
        computer.setOnOutputListener{
            println(it) // yes I am well aware this is a memory leak
            result = it
        }
        computer.input(5)
        computer.run()
        return result
    }
}