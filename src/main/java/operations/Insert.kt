package operations

/**
 * Represents insertion operation
 */
interface Insert<T> : Operation<T> {
    /**
     * Insertion offset
     */
    val offset: Int
}