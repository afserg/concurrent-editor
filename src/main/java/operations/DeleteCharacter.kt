package operations

import java.lang.IllegalStateException

/**
 * Operation that deletes a single character
 */
data class DeleteCharacter(override val start: Int, override val userId: Int) : Delete<StringBuilder> {
    var deletedValue: Char? = null

    override fun apply(content: StringBuilder) {
        deletedValue = content.get(start)
        content.delete(start, start + 1)
    }

    override fun transform(operation: Operation<StringBuilder>): Operation<StringBuilder> =
            operation.transformBy(this)


    override fun transformBy(insert: Insert<StringBuilder>): DeleteCharacter =
            when (start < insert.offset) {
                true -> this
                false -> DeleteCharacter(start + 1, userId)
            }

    override fun transformBy(delete: Delete<StringBuilder>): Operation<StringBuilder> =
            when (start.compareTo(delete.start)) {
                -1 -> this
                1 -> DeleteCharacter(start - 1, userId)
                else -> EmptyOperation(userId)
            }

    override fun transformBy(emptyOperation: EmptyOperation<StringBuilder>): DeleteCharacter = this

    override fun revert(): InsertCharacter {
        val insertValue = deletedValue ?: throw IllegalStateException("This operation cannot be reverted")
        return InsertCharacter(start, insertValue, userId)
    }
}