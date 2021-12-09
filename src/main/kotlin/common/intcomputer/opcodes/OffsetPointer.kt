package common.intcomputer.opcodes

import common.intcomputer.IntComputer
import common.intcomputer.OpCode

/**
 * Offset relative pointer
 */
class OffsetPointer(computer: IntComputer): OpCode(computer) {
    override val positionsUsed = 2

    override fun invoke() {
        offsetRelativeBase(p(1))
    }
}