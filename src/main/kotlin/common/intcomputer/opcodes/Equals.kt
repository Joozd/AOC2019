package common.intcomputer.opcodes

import common.intcomputer.IntComputer
import common.intcomputer.OpCode

/**
 * Check if p(1) == p(2), write 1 if true or 0 if false
 */
class Equals(computer: IntComputer): OpCode(computer) {
    override val positionsUsed = 4
    override fun invoke() {
        write(if (p(1) == p(2)) 1L else 0L)
    }
}