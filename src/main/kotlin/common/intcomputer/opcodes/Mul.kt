package common.intcomputer.opcodes

import common.intcomputer.IntComputer
import common.intcomputer.OpCode

/**
 * Multiply p(1) * p(2)
 */
class Mul(computer: IntComputer): OpCode(computer) {
    override val positionsUsed = 4
    override fun invoke() {
        write(p(1) * p(2))
    }
}