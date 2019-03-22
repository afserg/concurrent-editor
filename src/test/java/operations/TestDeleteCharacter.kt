package operations

import documents.Text
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestDeleteCharacter {

    @Test
    fun testApply() {
        val text = Text("abc")
        val delete = DeleteCharacter(1, 1)
        Assert.assertEquals(1, text.perform(delete))
        Assert.assertEquals(2, text.content().length)
        Assert.assertEquals("ac", text.content())
    }

    @Test
    fun testTransformByInsertWithLesserOffset() {
        val delete = DeleteCharacter(2, 1)
        val transformedDelete = delete.transformBy(InsertCharacter(1, 'a', 2))
        Assert.assertEquals(DeleteCharacter(3, 1), transformedDelete)
    }

    @Test
    fun testTransformByInsertWithGreaterOffset() {
        val delete = DeleteCharacter(2, 1)
        val transformedDelete = delete.transformBy(InsertCharacter(3, 'a', 2))
        assertEquals(delete, transformedDelete)
    }

    @Test
    fun testTransformByInsertWithEqualOffset() {
        val delete = DeleteCharacter(2, 1)
        val transformedDelete = delete.transformBy(InsertCharacter(2, 'a', 2))
        assertEquals(DeleteCharacter(3, 1), transformedDelete)
    }

    @Test
    fun testTransformByDeleteWithLesserStart() {
        val delete = DeleteCharacter(2, 1)
        val transformedDelete = delete.transformBy(DeleteCharacter(1, 2))
        assertEquals(DeleteCharacter(1, 1), transformedDelete)
    }

    @Test
    fun testTransformByDeleteWithGreaterStart() {
        val delete = DeleteCharacter(2, 1)
        val transformedDelete = delete.transformBy(DeleteCharacter(3, 2))
        assertEquals(delete, transformedDelete)
    }

    @Test
    fun testTransformByDeleteWithEqualStart() {
        val delete = DeleteCharacter(2, 1)
        val transformedDelete = delete.transformBy(DeleteCharacter(2, 2))
        assertTrue(transformedDelete is EmptyOperation)
    }

    @Test
    fun testRevert() {
        val text = Text("abc")
        val delete = DeleteCharacter(1, 1)
        Assert.assertEquals(1, text.perform(delete))
        val reverted = delete.revert()
        assertEquals("ac", text.content())
        assertEquals(InsertCharacter(1, 'b', 1), reverted)
        assertEquals(2, text.perform(reverted))
        assertEquals(3, text.content().length)
    }
}