import java.lang.RuntimeException

fun main(args: Array<String>) {

}

data class Chat(
    var id: Int = 0, //id самого чата
    val idInterlocutor: Int, //id собеседника
    var countMessage: Int = 0,
    var messages: MutableList<Message> //пустой список для хранения сообщений
)

data class Message(
    var id: Int = 0, //id сообщения
    val idChat: Int = 0, //id чата, к которому принадлежит сообщение
    val text: String, //тело сообщения
    var read: Boolean = false //прочитано сообщение или нет
)

class ChatService {
    //создаем пустой список для хранения чатов
    private val chats: MutableList<Chat> = mutableListOf()
    //счетчик количества чатов
    var countChat: Int = 0

    //создаем пустой список для хранения всех сообщений в чатах
    private val messagesAll: MutableList<Message> = mutableListOf()

    //счетчик количества сообщений в чате
    var countMessage: Int = 0

    //вводим функцию для добавления чата с собеседником по его idInterlocutor
    fun addChat(idInterlocutor: Int, message: Message): MutableList<Chat> {
        //увеличиваем счетчик чатов на 1
        countChat += 1
        //добавляем сообщение в список всех сообщений
        messagesAll += message.copy(idChat = countChat, id = ++countMessage)

        chats += Chat(
            id = countChat,
            idInterlocutor = idInterlocutor,
            countMessage = countMessage + 1,
            //берем из списка всех сообщений нужное нам
            messages = messagesAll.filter { it.idChat == countChat }.toMutableList()
        )
        return chats
    }

    //вводим функцию добавления сообщения в чат
    fun addMessage(idChat: Int, message: Message): Int {
        for ((index, item) in chats.withIndex()) {
            if (item.id == idChat) {
                countMessage += 1 //обновляем счетчик сообщений
                item.messages += message.copy(id = countMessage) //добавляем копию сообщения в чат с присвоением id
                return countMessage
            }
        }
        throw ChatIdNotFoundException("Чата с таким id не существует")
    }

    fun hasRead(chat: Chat): Boolean {
        for (item in chats) {
            for ((index, value) in item.messages.withIndex()) {
                return value.read
            }
        }
        throw ChatIdNotFoundException("Чата с таким id не найдено!")
    }


    //введем функцию, которая будет возвращать чаты с непрочитанными сообщениями используя anonymous function
    fun getUnreadChatsCount(): MutableList<Chat> {
        val mes: MutableList<Chat> = mutableListOf()

        for (item in chats) {
            for ((index, value) in item.messages.withIndex()) {
                if (!value.read) {
                    mes += item.copy()
                    return mes
                }
            }
        }
        //если сообщений нет или они все прочитаны возвращаем пустой список
        return mes
    }

    //введем функцию для получения списка чатов
    fun getChats(): String {
        return chats.joinToString()
    }

    //вводим функцию для получения последних сообщений из чата в ввиде строк
    fun getLastMessageInChat(chats: MutableList<Chat>, idChat: Int): Message {
        //используем функцию filter
        return messagesAll.filter { it.idChat == idChat }.last()

        //перебираем все чаты циклом
        /*for ((index, item) in chats.withIndex()) {
            //если id чата совпадает с нужным
            if (item.id == idChat) {
                //возвращаем последнее сообщение из списка всех чатов с фильтрацией по idChat, к которому принадлежит сообщение
                return item.messages.last { it.idChat == idChat }
            }
        }*/
        return Message(text = "Сообщений еще нет или они были удалены")
    }


    //вводим функцию для вывода всех сообщений с определенным собеседником
    fun getMessageInChat(idInterlocutor: Int, countMessage: Int) : List<Message> {
        for ((index, item) in chats.withIndex()) {
            if (item.idInterlocutor == idInterlocutor) {
                //возвращает элементы из списка messages, idChat которые равны заданному id
                return item.messages.filter(fun (message: Message) = message.idChat == item.id)
            }
        }
        throw InterlocutorNotFoundException("Собеседника с таким id не существует")
    }

    //вводим функцию для удаления сообщения по id
    fun deleteMessage(idChat: Int, idMessage: Int): Boolean {
        for ((index, item) in chats.withIndex()) {
            if (item.id == idChat) {
                for ((count, value) in item.messages) {
                    item.messages.removeAt(count-1)
                    return true
                }
            }
        }
        throw MessageIdNotFoundException("Сообщение с таким id не существует")
    }

    //вводим функцию для удаления chat вместе с сообщениями, если они есть
    fun deleteChat(idChat: Int): Boolean {
        for ((index, item) in chats.withIndex()) {
            if (item.id == idChat) {
                chats.removeAt(index)
                return true
            }
        }
        throw ChatIdNotFoundException("Чата с таким id не существует")
    }

}

class ChatIdNotFoundException(message: String) : RuntimeException(message)
class InterlocutorNotFoundException(message: String) : RuntimeException(message)
class MessageIdNotFoundException(message: String) : RuntimeException(message)