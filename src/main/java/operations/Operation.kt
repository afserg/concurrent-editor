package operations

/**
 * Represents operation on a document
 */
interface Operation<T> {
    val userId: Int

    /**
     * Applies this operation to the content
     */
    fun apply(content: T)

    /**
     * Transforms the operation by this operation
     */
    fun transform(operation: Operation<T>): Operation<T>

    /**
     * Transforms this operation by the insert operation
     */
    fun transformBy(insert: Insert<T>): Operation<T>

    /**
     * Transforms this operation by the delete operation
     */
    fun transformBy(delete: Delete<T>): Operation<T>

    /**
     * Transforms this operation by the empty operation
     */
    fun transformBy(emptyOperation: EmptyOperation<T>): Operation<T>

    /**
     * Returns operation opposite to this operation
     */
    fun revert(): Operation<T>
}