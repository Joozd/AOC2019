package common.intcomputer.opcodes

import common.intcomputer.IntComputer
import common.intcomputer.OpCode

/**
 * Add p(1) + p(2)
 */
class Add(computer: IntComputer): OpCode(computer) {
    override val positionsUsed = 4
    override fun invoke() {
        write(p(1) + p(2))
    }
}