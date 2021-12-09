package common.intcomputer.opcodes

import common.intcomputer.IntComputer
import common.intcomputer.OpCode

/**
 * Get input and save it.
 * If nu input available, null will be returned, and this instruction will be repeated when there is.
 */
class Input(computer: IntComputer): OpCode(computer) {
    override val positionsUsed = 2
    override fun invoke() {
        getInput()?.let {
            write(it)
        }
    }
}