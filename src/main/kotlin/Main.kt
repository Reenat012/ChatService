import java.lang.RuntimeException

fun main(args: Array<String>) {
    val service = ChatService()

    val chats: MutableMap<Int, Chat> = mutableMapOf()
    chats += service.addMessage(1, Message(text = "Hello"))
    println(chats)
}

data class Chat(
    val idUser: Int = 0, //id собеседника
    var countMessage: Int = 0,
    var messages: MutableList<Message> = mutableListOf() //пустой список для хранения сообщений
)

data class Message(
    var id: Int = 0, //id сообщения
    val idChat: Int = 0, //id чата, к которому принадлежит сообщение
    val text: String = "", //тело сообщения
    var read: Boolean = false //прочитано сообщение или нет
)

class ChatService {
    //создаем пустой список для хранения чатов
    private val chats = mutableMapOf<Int, Chat>()

    //счетчик количества чатов
    var countChat: Int = 0

    //создаем пустой список для хранения всех сообщений в чатах
    private val messagesAll: MutableList<Message> = mutableListOf()

    //счетчик количества сообщений в чате
    var countMessage: Int = 0

    //вводим функцию для добавления чата с собеседником по его idInterlocutor
    fun addMessage(idUser: Int, message: Message): MutableMap<Int, Chat> {
        //получаем либо существующий чат, либо (если чата еще нет) чат запишется
        chats.getOrPut(idUser) { Chat() }.messages += message.copy()
        return chats
    }

    //введем функцию, которая будет возвращать чаты с непрочитанными сообщениями используя anonymous function
    fun getUnreadChatsCount(): Int {
        //values - обращаемся ко всем элементам коллекции
        //count - возвращает количество элементов, которое удовлевтворяет придакату {..}
        //чат считается непрочитанным, если есть хотя бы одно непрочитанное сообщение
        //any - вовзвращает true, если хотя бы один элемент удовлетворяет предикате
        return chats.values
            .count { chat -> chat.messages.any { !it.read } }
    }

    //введем функцию для получения списка чатов
    fun getChats(): MutableMap<Int, Chat> {
        return chats
    }

    //вводим функцию для получения последних сообщений из чатов в ввиде строк
    fun getLastMessageInChats(): List<String> {
        //value - своего рода цикл, пробегается по всем элементам коллекции
        //map - функция траснформации, собирает новую коллекцию по указанным параметрам
        //т.е. собирает коллекцию из последних сообщений всех чатов
        //lastOrNull - возвращает последний элемент в списке, а если его нет возвращает null
        //в нашем случае с помощью оператора элвиса ?: возвращаем сообщения "No messages"
        return chats.values
            .asSequence()
            .map { it.messages.lastOrNull()?.text ?: "No messages" }
            .toList()
    }


    //вводим функцию для вывода всех сообщений с определенным собеседником
    fun getMessageInChat(idUser: Int, countMessage: Int): List<Message> {
        val chat = chats[idUser] ?: throw ChatIdNotFoundException("Такого чата нет")
        //takeLast (функция расширения) - возвращает список, который содержит последнии n элементов (в нашем случае количество последних сообщений)
        //oneEach (функция расширения) - выполняет лямбду на каждом элементе и возвращает коллекцию
        //onEach { it.read = true } - автоматически читает выведенные сообщения
        return chat.messages
            .asSequence()
            .take(countMessage)
            .ifEmpty { throw MessageIdNotFoundException("Messages not") }
            .onEach { it.read = true }
            .toList()

    }

    //вводим функцию для удаления сообщения по id
    fun deleteMessage(idUser: Int, idMessage: Int): Message {
        val chat = chats[idUser] ?: throw ChatIdNotFoundException("Такого чата нет")
        return chat.messages.removeAt(idMessage)
    }

    fun deleteChat(idUser: Int): Chat {
        return chats.remove(idUser) ?: throw ChatIdNotFoundException("Такого чата нет")
    }

    class ChatIdNotFoundException(message: String) : RuntimeException(message)
    class InterlocutorNotFoundException(message: String) : RuntimeException(message)
    class MessageIdNotFoundException(message: String) : RuntimeException(message)
}