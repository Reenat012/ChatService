import junit.framework.TestCase
import org.junit.Test
import junit.framework.TestCase.*

class ChatServiceTest {

    @Test
    fun addChat() {
        val service = ChatService()

        val result = service.addChat(1, Message(text = "Hello"))

        val messagesAll: MutableList<Message> = mutableListOf()
        messagesAll += Message(id = 1, idChat = 1, text = "Hello")
        val list = mutableListOf(
            Chat(
                id = 1,
                idInterlocutor = 1,
                countMessage = 2,
                messages = messagesAll.filter { it.idChat == 1 }.toMutableList()
            )
        )

        assertEquals(list, result)
    }

    @Test
    fun addMessage() {
        val service = ChatService()

        service.addChat(1, Message(text = "Hello"))


        val result = service.addMessage(idChat = 1, Message(text = "World"))

        assertEquals(2, result)
    }

    @Test
    fun getUnreadChatsCount() {
        val service = ChatService()

        service.addChat(1, Message(text = "Hello"))

        val messagesAll: MutableList<Message> = mutableListOf()
        messagesAll += Message(id = 1, idChat = 1, text = "Hello")
        val list = mutableListOf(
            Chat(
                id = 1,
                idInterlocutor = 1,
                countMessage = 2,
                messages = messagesAll.filter { it.idChat == 1 }.toMutableList()
            )
        )

        val result = service.getUnreadChatsCount()

        assertEquals(list, result)
    }

    @Test
    fun getChats() {
        val service = ChatService()

        service.addChat(1, Message(text = "Hello"))

        val messagesAll: MutableList<Message> = mutableListOf()
        messagesAll += Message(id = 1, idChat = 1, text = "Hello")
        val list = mutableListOf(
            Chat(
                id = 1,
                idInterlocutor = 1,
                countMessage = 2,
                messages = messagesAll.filter { it.idChat == 1 }.toMutableList()
            )
        )

        val result = service.getChats()

        assertEquals(list.joinToString(), result)
    }

    @Test
    fun lastMessageInChat() {
        val service = ChatService()

        val chats: MutableList<Chat> = mutableListOf()
        chats += service.addChat(1, Message(text = "Hello"))

        val messagesAll: MutableList<Message> = mutableListOf()
        messagesAll += Message(id = 1, idChat = 1, text = "Hello")
        val list = mutableListOf(
            Chat(
                id = 1,
                idInterlocutor = 1,
                countMessage = 2,
                messages = messagesAll.filter { it.idChat == 1 }.toMutableList()
            )
        )

        val result = service.getLastMessageInChat(chats, idChat = 1)

        assertEquals(Message(id = 1, idChat = 1, text = "Hello"), result)
    }

    @Test
    fun getMessageInChat() {
        val service = ChatService()

        val chats: MutableList<Chat> = mutableListOf()
        chats += service.addChat(1, Message(text = "Hello"))

        val messagesAll: MutableList<Message> = mutableListOf()
        messagesAll += Message(id = 1, idChat = 1, text = "Hello")
        val list = mutableListOf(
            Chat(
                id = 1,
                idInterlocutor = 1,
                countMessage = 2,
                messages = messagesAll.filter { it.idChat == 1 }.toMutableList()
            )
        )

        val result = service.getMessageInChat(1, 2)

        assertEquals(Message(id = 1, idChat = 1, text = "Hello"), result.joinToString())
    }

    @Test
    fun deleteMessage() {
        val service = ChatService()

        val chats: MutableList<Chat> = mutableListOf()
        chats += service.addChat(1, Message(text = "Hello"))

        val messagesAll: MutableList<Message> = mutableListOf()
        messagesAll += Message(id = 1, idChat = 1, text = "Hello")
        val list = mutableListOf(
            Chat(
                id = 1,
                idInterlocutor = 1,
                countMessage = 2,
                messages = messagesAll.filter { it.idChat == 1 }.toMutableList()
            )
        )

        val result = service.deleteMessage(1, 1)

        assertEquals(true, result)
    }

    @Test
    fun deleteChat() {
        val service = ChatService()

        val chats: MutableList<Chat> = mutableListOf()
        chats += service.addChat(1, Message(text = "Hello"))

        val messagesAll: MutableList<Message> = mutableListOf()
        messagesAll += Message(id = 1, idChat = 1, text = "Hello")
        val list = mutableListOf(
            Chat(
                id = 1,
                idInterlocutor = 1,
                countMessage = 2,
                messages = messagesAll.filter { it.idChat == 1 }.toMutableList()
            )
        )

        val result = service.deleteChat(1)

        assertEquals(true, result)
    }
}