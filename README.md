# ConcurrentEditor
 
 The task is to create a server-side model of the online concurrent editing tool, something like Google Docs.
 
 This model must support following features:
 * Live updates - when one user enters text, the other users see it, maybe with a little delay but without reloading the page.
 * Concurrent editing - multiple users can make edits at the same time. Concurrent edit should always merge and never result in a conflict.
 
 For this task we assume that everything happens in memory, so there's no database or other storage. The format is plain text (no formatting) and the
 language is know to be English.
 
## Operational transformation

 This tool uses Operational Transformations. Each client has its own copy of the document. Clients operate on their local copies in a lock-free,
 non blocking manner, and the changes are then propagated to the server. When the server receives the changes from a client, it transforms them by\
 changes made by other clients. Then it responds with the transformed changes made by other clients. The transformations ensures that document
 consistency are maintained by all sites.
 More information on Operational Transformation can be found at https://en.wikipedia.org/wiki/Operational_transformation.
 
## Supported operations

 1. `InsertCharacter` - inserts a single character into the text document
 2. `DeleteCharacter` - deletes a single character from the text document
 3. `Undo` - undoes last user operation. Redoes it when called once again.
 4. `Refresh` - updates clients copy of the document to the state of the server copy.
 
## Usage example

### Insert character
    
    //client 1 inserts a character 'a' with 0 offset in the 5 revision of the document
    //and receives the list of transformed operations made by other clients
    
    val transformedOperations = server.edit(InsertCharacter(0, 'a', 1), 5)
    
### Delete character
    
    //client 1 deletes a character 0 positon in the 5 revision of the document
    //and receives the list of transformed operations made by other clients
    
    val transformedOperations = server.edit(DeleteCharacter(0, 1), 5)
    
### Undo
    
    //client 1 undoes its last operation
    
    val transformedOperations = server.undo(1)
    
### Refresh
    
    //returns the list of operations made from 5 revision of the document
    
    val transformedOperations = server.refresh(5)