import java.lang.RuntimeException

fun main(args: Array<String>) {

}

data class Chat(
    var id: Int = 0, //id самого чата
    val idInterlocutor: Int, //id собеседника
) {
    lateinit var message: Message
}

data class Message(
    var id: Int = 0, //id сообщения
    val idChat: Int, //id чата, к которому принадлежит сообщение
    val text: String, //тело сообщения
    var read: Boolean = false //прочитано сообщение или нет
)

class ChatService {
    //создаем пустой список для хранения чатов
    private val chats: MutableList<Chat> = mutableListOf()

    //создаем пустой список для хранения сообщений в чатах
    private val messages: MutableList<Message> = mutableListOf()

    //счетчик количества сообщений в чате
    var countMessage: Int = 0

    //вводим функцию для добавления чата с собеседником по его idInterlocutor
    fun addChat(chat: Chat): Int {
        chats += chat.copy() //добавляем копию (чтобы к нему не остался доступ извне по той же ссылке) чата в список всех чатов пользователя
        chat.id = chats.size //присваиваем id чата значение равное размеру списка чатов
        return chat.id
    }

    //вводим функцию добавления сообщения в чат
    fun addMessage(idChat: Int, message: Message): Int {
        for ((index, item) in chats.withIndex()) {
            if (item.id == idChat) {
                messages += message.copy(id = ++countMessage) //добавляем копию сообщения в чат с присвоением id
                ++countMessage //обновляем счетчик сообщений
                return message.id
            }
        }
        throw ChatIdNotFoundException("Чата с таким id не существует")
    }

    //введем функцию, которая будет возвращать чаты с непрочитанными сообщениями используя anonymous function
    fun getUnreadChatsCount(chats: MutableList<Chat>): List<Chat> {
        //т.е. если в свойстве чата message свойство read == false, сообщение считается непрочитанным
        return chats.filter(fun (chat: Chat) = !chat.message.read)
    }

    //введем функцию для получения списка чатов
    fun getChats(chats: MutableList<Chat>): String {
        return chats.joinToString()
    }

    //вводим функцию для получения последних сообщений из чатов в ввиде строк
    fun lastMessageInAllChats(chats: MutableList<Chat>, idChat: Int): Message {
        //перебираем все чаты циклом
        for ((index, item) in chats.withIndex()) {
            //если id чата совпадает с нужным
            if (item.id == idChat) {
                //возвращаем последнее сообщение из списка всех чатов с фильтрацией по idChat, к которому принадлежит сообщение
                return messages.last { it.idChat == idChat }
            }
        }
    }
}

class ChatIdNotFoundException(message: String) : RuntimeException(message)