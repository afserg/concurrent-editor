package operations

/**
 * Transforms the operation by this list of operations
 */
fun <V> List<Operation<V>>.transform(operation: Operation<V>): Operation<V> =
        this.fold(operation) { acc, oper -> oper.transform(acc) }

/**
 * Transforms this list of operations by the operation
 */
fun <V> List<Operation<V>>.transformBy(operation: Operation<V>): List<Operation<V>> {
    var bridge = operation
    return this.map {
        val transformedOperation = bridge.transform(it)
        if (transformedOperation == it || transformedOperation is EmptyOperation) bridge = it.transform(bridge)
        transformedOperation
    }
}
