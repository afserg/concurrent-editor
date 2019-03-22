import documents.Document
import documents.Text
import operations.Operation
import operations.transform
import operations.transformBy

/**
 * Editor for a text document
 */
class TextEditor(override val document: Document<StringBuilder> = Text()) : Editor<StringBuilder> {
    override fun edit(operation: Operation<StringBuilder>, revision: Int): List<Operation<StringBuilder>> {
        val newOperations = synchronized(document) {
            val newOperations = (revision..document.revision()).map { document.operations[it - 1] }
            val transformedOperation = newOperations.transform(operation)
            document.perform(transformedOperation)
            newOperations
        }

        return newOperations.transformBy(operation)
    }

    override fun refresh(revision: Int): List<Operation<StringBuilder>> =
            (revision until document.revision()).map { document.operations[it] }

    override fun undo(userId: Int): List<Operation<java.lang.StringBuilder>> {
        val lastUserOperationIndex = document.operations.indexOfLast { it.userId == userId }
        val undoOperation = document.operations[lastUserOperationIndex].revert()
        val transformedOperations = edit(undoOperation, lastUserOperationIndex + 2)
        return listOf(undoOperation).plus(transformedOperations)
    }
}