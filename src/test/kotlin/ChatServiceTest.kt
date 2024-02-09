import junit.framework.TestCase
import org.junit.Test
import junit.framework.TestCase.*

class ChatServiceTest {
    @Test
    fun addMessage() {
        val service = ChatService()

        val chats: MutableMap<Int, Chat> = mutableMapOf()
        chats += service.addMessage(1, Message(text = "Hello"))

        val result = service.addMessage(1, Message(text = "Hello"))

        assertEquals(chats, result)
    }

    @Test
    fun getUnreadChatsCount() {
        val service = ChatService()

        val chats: MutableMap<Int, Chat> = mutableMapOf()
        chats += service.addMessage(1, Message(text = "Hello"))

        val result = service.getUnreadChatsCount()

        assertEquals(1, result)
    }

    @Test
    fun getChats() {
        val service = ChatService()

        val chats: MutableMap<Int, Chat> = mutableMapOf()
        chats += service.addMessage(1, Message(text = "Hello"))

        val result = service.getChats()

        assertEquals(chats, result)
    }

    @Test
    fun lastMessageInChats() {
        val service = ChatService()

        val chats: MutableMap<Int, Chat> = mutableMapOf()
        chats += service.addMessage(1, Message(text = "Hello"))


        val result = service.getLastMessageInChats()

        assertEquals(chats.values.map { it.messages.lastOrNull()?.text }, result)
    }

    @Test
    fun getMessageInChat() {
        val service = ChatService()

        val chats: MutableMap<Int, Chat> = mutableMapOf()
        chats += service.addMessage(1, Message(text = "Hello"))


        val result = service.getMessageInChat(1, 1)

        assertEquals(chats[1]?.messages?.takeLast(1), result)
    }

    @Test
    fun deleteMessage() {
        val service = ChatService()

        val chats: MutableMap<Int, Chat> = mutableMapOf()
        chats += service.addMessage(1, Message(text = "Hello"))

        val result = service.deleteMessage(1, 0)

        assertEquals(Message(text = "Hello"), result)
    }

    @Test
    fun deleteChat() {
        val service = ChatService()

        val chats: MutableMap<Int, Chat> = mutableMapOf()
        chats += service.addMessage(1, Message(text = "Hello"))

        val result = service.deleteChat(1)

        val ch: MutableMap<Int, Chat> = mutableMapOf()
        ch += service.addMessage(1, Message(text = "Hello"))

        assertEquals(ch[1], result)
    }
}