package Service;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    // overloaded constructor if needed for testing 
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    /**
     * This adds a new message to the database 
     *  NOTE: message must not be blank
     * 
     * @param account an message obj
     * @return persisted messsage if successful, else null
     */
    public Message addMessage(Message message){
        return message.getMessage_text() != "" ? messageDAO.insertMessage(message) : null;
    }

}
