package common.intcomputer

import common.intcomputer.opcodes.*

/**
 * An OpCode.
 * NOTE: Do not write directly to computer from child classes. Use the functions provided:
 * [write]
 */
abstract class OpCode(private val computer: IntComputer): RunnableOpCode {
    /**
     * The amount of IntCodes that make up this OpCode
     */
    abstract val positionsUsed: Int

    private val ip = computer.instructionPointer
    private val parameterModes: List<Long> = findParameterModes()

    /**
     * Get value for any parameter
     */
    protected fun p(index: Int) = when(parameterMode(index)){
        PARAMETER_MODE_ADDRESS -> get(computer[ip + index])
        PARAMETER_MODE_DIRECT -> computer[ip + index]
        PARAMETER_MODE_RELATIVE -> getRelative(computer[ip + index])
        else -> error ("Invalid parameter mode (${parameterMode(index)})")
    }

    /**
     * Will write data to the position found from the last parameter in this OpCode
     * (uses [positionsUsed] to determine last param)
     */
    protected fun write(value: Long) {
        val t = target(positionsUsed - 1).toInt()
        computer[t] = value
    }

    /**
     * Offset Relative base by [offsetBy]
     */
    protected fun offsetRelativeBase(offsetBy: Long){
        computer.relativeBase += offsetBy.toInt()
    }

    /**
     * Jump to a specific position
     */
    protected fun jump(jumpTo: Long){
        computer.instructionPointer = jumpTo.toInt() - positionsUsed
    }

    /**
     * Send a value to computer to output
     */
    protected fun output(value: Long){
        computer.output(value)
    }

    /**
     * Get input from user
     */
    protected fun getInput(): Long? = computer.getInput()

    private fun target(index:Int) = when(parameterMode(index)){
        PARAMETER_MODE_ADDRESS, PARAMETER_MODE_DIRECT -> computer[ip + index]
        PARAMETER_MODE_RELATIVE -> computer[ip + index] + computer.relativeBase
        else -> error ("Invalid parameter mode (${parameterMode(index)})")
    }

    //get value for an address parameter
    private fun get(param: Long) =
        computer[param.toInt()]

    // get value for a relative parameter
    private fun getRelative(param: Long) =
        computer[param.toInt() + computer.relativeBase]



    private fun parameterMode(index: Int) = parameterModes[index - 1]

    private fun findParameterModes(): List<Long>{
        val opCode = computer[ip]
        val p1Mode = (opCode/100) %10
        val p2Mode = (opCode/1000) % 10
        val p3Mode = (opCode/10000) % 10
        return listOf(p1Mode,p2Mode,p3Mode)//.also{ println("modes: $it")}
    }

    companion object{
        /**
         * Make an opcode, or null if program should halt
         */
        fun make(computer: IntComputer): OpCode? {
            return when (computer[computer.instructionPointer] % 100) {
                1L -> Add(computer)
                2L -> Mul(computer)
                3L -> Input(computer)
                4L -> Output(computer)
                5L -> Jit(computer)
                6L -> Jif(computer)
                7L -> LessThan(computer)
                8L -> Equals(computer)
                9L -> OffsetPointer(computer)

                99L -> null
                else -> error("Bad opcode ${computer[computer.instructionPointer]} at ${computer.instructionPointer}")
            }
        }
        const val PARAMETER_MODE_ADDRESS = 0L
        const val PARAMETER_MODE_DIRECT = 1L
        const val PARAMETER_MODE_RELATIVE = 2L
    }
}
