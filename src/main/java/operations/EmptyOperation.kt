package operations

/**
 * Operation that does nothing. It is needed to resolve
 * a tie between two equal delete operations
 */
data class EmptyOperation<T>(override val userId: Int) : Operation<T> {
    override fun apply(content: T) {}

    override fun transform(operation: Operation<T>): Operation<T> = operation.transformBy(this)

    override fun transformBy(insert: Insert<T>): Operation<T> = this

    override fun transformBy(delete: Delete<T>): Operation<T> = this

    override fun transformBy(emptyOperation: EmptyOperation<T>): Operation<T> = emptyOperation

    override fun revert(): Operation<T> = this
}