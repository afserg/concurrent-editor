package operations

/**
 * Represents deletion operation
 */
interface Delete<T> : Operation<T> {
    /**
     * Delete start position
     */
    val start: Int
}