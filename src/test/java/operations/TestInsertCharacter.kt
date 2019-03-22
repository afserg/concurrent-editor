package operations

import documents.Text
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals

class TestInsertCharacter {

    @Test
    fun testApply() {
        val text = Text("abc")
        val insert = InsertCharacter(1, 'f', 1)
        assertEquals(1, text.perform(insert))
        assertEquals(4, text.content().length)
        assertEquals("afbc", text.content())
    }

    @Test
    fun testTransformByInsertWithLesserOffset() {
        val insert = InsertCharacter(2, 'c', 1)
        val transformedInsert = insert.transformBy(InsertCharacter(1, 'a', 2))
        assertEquals(InsertCharacter(3, 'c', 1), transformedInsert)
    }

    @Test
    fun testTransformByInsertWithGreaterOffset() {
        val insert = InsertCharacter(1, 'c', 1)
        val transformedInsert = insert.transformBy(InsertCharacter(2, 'a', 2))
        assertEquals(insert, transformedInsert)
    }

    @Test
    fun testTransformByInsertWithEqualOffset() {
        val insert = InsertCharacter(1, 'c', 2)
        val transformedInsert = insert.transformBy(InsertCharacter(1, 'a', 1))
        assertEquals(insert, transformedInsert)
    }

    @Test
    fun testTransformByDeleteWithLesserStart() {
        val insert = InsertCharacter(2, 'c', 1)
        val transformedInsert = insert.transformBy(DeleteCharacter(1, 2))
        assertEquals(InsertCharacter(1, 'c', 1), transformedInsert)
    }

    @Test
    fun testTransformByDeleteWithGreaterStart() {
        val insert = InsertCharacter(2, 'c', 1)
        val transformedInsert = insert.transformBy(DeleteCharacter(3, 2))
        assertEquals(insert, transformedInsert)
    }

    @Test
    fun testTransformByDeleteWithEqualStart() {
        val insert = InsertCharacter(2, 'c', 1)
        val transformedInsert = insert.transformBy(DeleteCharacter(2, 2))
        assertEquals(insert, transformedInsert)
    }

    @Test
    fun testRevert() {
        val text = Text("abc")
        val insert = InsertCharacter(1, 'f', 1)
        val reverted = insert.revert()
        assertEquals(1, text.perform(insert))
        assertEquals(DeleteCharacter(1, 1), reverted)
        assertEquals(2, text.perform(reverted))
        assertEquals(3, text.content().length)
        assertEquals("abc", text.content())
    }
}