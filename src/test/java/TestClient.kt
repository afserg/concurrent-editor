import documents.Text
import operations.DeleteCharacter
import operations.InsertCharacter
import operations.Operation

class TestClient(val server: TextEditor, val userId: Int) {
    val text = Text()

    fun perform(operation: Operation<StringBuilder>) {
        text.perform(operation)
        server.edit(operation, text.revision()).forEach { text.perform(it) }
    }

    fun refresh() = server.refresh(text.revision()).forEach { text.perform(it) }

    fun insertRandomCharacter() {
        val offset = randomPosition()
        val value = ('a'..'z').random()
        perform(InsertCharacter(offset, value, userId))
    }

    fun deleteRandomCharacter() {
        val start = randomPosition()
        perform(DeleteCharacter(start, userId))
    }

    fun twoStepsForwardOneStepBack() {
        insertRandomCharacter()
        insertRandomCharacter()
        deleteRandomCharacter()
    }

    fun undo() {
        val ops = server.undo(userId)
        ops.forEach { text.perform(it) }
    }

    private fun randomPosition() = if (text.content().isEmpty()) 0 else (0 .. (text.content().length - 1)).random()

}