package operations

import documents.Text
import java.lang.StringBuilder

/**
 * Operation that inserts a single character
 */
data class InsertCharacter(override val offset: Int, val value: Char, override val userId: Int) : Insert<StringBuilder> {

    override fun apply(content: StringBuilder) {
        content.insert(offset, value)
    }

    override fun transform(operation: Operation<StringBuilder>): Operation<StringBuilder> =
            operation.transformBy(this)

    override fun transformBy(insert: Insert<StringBuilder>): Operation<StringBuilder> =
            when (offset.compareTo(insert.offset)) {
                -1 -> this
                1 -> InsertCharacter(offset + 1, value, userId)
                else -> if (userId > insert.userId) this
                        else InsertCharacter(offset + 1, value, userId)
            }

    override fun transformBy(delete: Delete<StringBuilder>): InsertCharacter =
            when (offset <= delete.start) {
                true -> this
                false -> InsertCharacter(offset - 1, value, userId)
            }

    override fun transformBy(emptyOperation: EmptyOperation<StringBuilder>): InsertCharacter = this

    override fun revert(): DeleteCharacter = DeleteCharacter(offset, userId)
}