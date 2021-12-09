package common.intcomputer

import common.intcomputer.opcodes.Input
import java.util.*

class IntComputer(private val program: List<Long>) {
    constructor(program: List<Long>, onOutput: OnOutput): this(program){
        onOutputListener = onOutput
    }

    internal var instructionPointer = 0
    internal var relativeBase = 0

    private val mem = program.toMutableList()
    private val inputBuffer = LinkedList<Long>()

    private var finished = false
    private var waitingForInput = false
    private val running get() = !finished && !waitingForInput

    private var onOutputListener: OnOutput? = null
    private var inputProvider: InputProvider? = null


    operator fun get(address: Int) = mem.getOrNull(address) ?: 0
    operator fun set(address: Int, value: Long) {
        while(address !in mem.indices)
            mem.add(0)
        mem[address] = value
    }

    /**
     * Run the program until done (opcode 99) or waiting for input.
     */
    fun run(){
        while (running)
            tick()
    }

    /**
     * Reset the computer to starting state
     */
    fun reset(){
        mem.clear()
        mem.addAll(program)
        instructionPointer = 0
        finished = false
        waitingForInput = false
    }

    /**
     * Get input from whoever provides input.
     * If computer was paused waiting for input, unpause it.
     */
    fun input(value: Long){
        inputBuffer.add(value)
        if (waitingForInput){
            waitingForInput = false
            run()
        }
    }

    /**
     * Set an output listener.
     * @param onOutput: the listener that tells the computer what to do with any output it has.
     * @see OnOutput
     */
    fun setOnOutputListener(onOutput: OnOutput){
        onOutputListener = onOutput
    }

    fun setInputProvider(inputProvider: InputProvider?){
        this.inputProvider = inputProvider
    }

    internal fun output(value: Long){
        onOutputListener?.output(value)
    }

    internal fun getInput(): Long?{
        return if (inputBuffer.isNotEmpty())
            inputBuffer.removeFirst()
        else {
            // If nothing in inputbuffer, try if InputProvider has anything
            inputProvider?.getInput()?.let{
                return it
            }
            //reset to input instruction and pause computer
            instructionPointer -= Input(this).positionsUsed
            waitingForInput = true
            null
        }
    }

    // Make and execute the next OpCode
    private fun tick(){
        OpCode.make(this)?.let{
            it()
            instructionPointer += it.positionsUsed
        } ?: run { finished = true }
    }

    /**
     * OnOutput listener. When the computer has something to output, it will run [output].
     * @see setOnOutputListener
     */
    fun interface OnOutput{
        fun output(value: Long)
    }

    fun interface InputProvider{
        fun getInput(): Long?
    }
}