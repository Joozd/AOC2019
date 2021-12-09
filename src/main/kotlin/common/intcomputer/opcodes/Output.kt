package common.intcomputer.opcodes

import common.intcomputer.IntComputer
import common.intcomputer.OpCode

/**
 * Output p(1)
 */
class Output(computer: IntComputer): OpCode(computer) {
    override val positionsUsed = 2
    override fun invoke() {
        output(p(1))
    }
}