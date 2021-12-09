package common.intcomputer.opcodes

import common.intcomputer.IntComputer
import common.intcomputer.OpCode

/**
 * Jump If False
 */
class Jif(computer: IntComputer): OpCode(computer) {
    override val positionsUsed = 3
    override fun invoke() {
        if (p(1) == 0L) jump(p(2))
    }
}