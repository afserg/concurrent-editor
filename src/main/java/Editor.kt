import documents.Document
import operations.Operation

/**
 * Represents editor for a document
 */
interface Editor<T> {
    val document: Document<T>

    /**
     * Edits a document with the operation and returns a list of operations
     * transformed by the operation to synchronize changes on a client side
     */
    fun edit(operation: Operation<T>, revision: Int): List<Operation<T>>

    /**
     * Returns the list of operations since the revision
     */
    fun refresh(revision: Int): List<Operation<T>>

    /**
     * Undoes the last user operation. Redoes it when called again.
     */
    fun undo(userId: Int): List<Operation<T>>
}