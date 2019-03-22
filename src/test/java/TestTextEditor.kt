import documents.Text
import operations.DeleteCharacter
import operations.InsertCharacter
import org.junit.Test
import kotlin.test.assertEquals

class TestTextEditor {
    var text: Text = Text()
    var textEditor: TextEditor = TextEditor(text)

    @Test
    fun testEdit() {
        textEditor.edit(InsertCharacter(0, 'a', 2), 1)
        textEditor.edit(InsertCharacter(0, 'b', 2), 1)
        textEditor.edit(DeleteCharacter(0, 2), 2)
        val transformedOperations = textEditor.edit(InsertCharacter(0, 'c', 1), 1)
        assertEquals(3, transformedOperations.size)
        assertEquals("bc", text.content())
    }

    @Test
    fun testConcurrentEditing() {
        val client1 = TestClient(textEditor, 1)
        val client2 = TestClient(textEditor, 2)
        val agent1 = Runnable { repeat(500) { client1.twoStepsForwardOneStepBack() } }
        val agent2 = Runnable { repeat(500) { client2.twoStepsForwardOneStepBack() } }
        val thread1 = Thread(agent1)
        val thread2 = Thread(agent2)
        thread1.start()
        thread2.start()
        thread1.join()
        thread2.join()
        client1.refresh()
        client2.refresh()
        assertEquals(text.content(), client1.text.content())
        assertEquals(text.content(), client2.text.content())
    }

    @Test
    fun testUndo() {
        val client1 = TestClient(textEditor, 1)
        client1.perform(InsertCharacter(0, 'a', 1))             //a
        textEditor.edit(InsertCharacter(0, 'b', 2), 1)  //ba
        client1.undo()                                                              //b
        assertEquals("b", text.content())
        assertEquals(text.content(), client1.text.content())
        client1.undo()                                                              //ba
        assertEquals("ba", text.content())
        assertEquals(text.content(), client1.text.content())
    }
}