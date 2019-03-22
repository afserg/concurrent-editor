package documents

import operations.Operation

/**
 * Represents simple text document
 */
class Text() : Document<StringBuilder> {
    override val operations: MutableList<Operation<StringBuilder>> = mutableListOf()
    private val content: StringBuilder = StringBuilder()

    constructor(text: String) : this() {
        content.append(text)
    }

    override fun content(): String = content.toString()

    override fun revision(): Int = operations.size

    override fun perform(operation: Operation<StringBuilder>): Int = synchronized(this) {
        operation.apply(content)
        operations.add(operation)
        return revision()
    }
}