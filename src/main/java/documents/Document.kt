package documents

import operations.Operation

/**
 * Represents document
 */
interface Document<T> {
    /**
     * All operations performed on this document
     */
    val operations: MutableList<Operation<T>>

    /**
     * Returns the contents of this document
     */
    fun content(): String

    /**
     * Returns current revision of this document
     */
    fun revision(): Int

    /**
     * Performs the operation on this document
     */
    fun perform(operation: Operation<T>): Int
}