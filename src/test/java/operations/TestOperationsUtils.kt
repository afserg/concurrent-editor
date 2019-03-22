package operations

import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals

class TestOperationsUtils {

    @Test
    fun testTransform() {
        val operations = listOf(
                InsertCharacter(1, 'a', 1),
                InsertCharacter(2, 'b', 2),
                InsertCharacter(3, 'c', 2),
                DeleteCharacter(3, 1)
        )
        val transformedOperation = operations.transform(InsertCharacter(1, 'f', 1))
        assertEquals(InsertCharacter(3, 'f', 1), transformedOperation)
    }

    @Test
    fun testTransformBy() {
        val operations = listOf(
                InsertCharacter(1, 'a', 1),
                DeleteCharacter(1, 2),
                EmptyOperation(1)
        )
        val transformedOperations = operations.transformBy(InsertCharacter(1, 'f', 2))
        assertEquals(InsertCharacter(2, 'a', 1), transformedOperations[0])
        assertEquals(DeleteCharacter(2, 2), transformedOperations[1])
        assertEquals(EmptyOperation<StringBuilder>(1), transformedOperations[2])
    }
}