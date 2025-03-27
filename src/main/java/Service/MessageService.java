package Service;

import DAO.MessageDAO;
import java.util.List;
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
     * @param account a Message obj
     * @return persisted messsage if successful, else null
     */
    public Message addMessage(Message message){
        return message.getMessage_text() != "" ? messageDAO.insertMessage(message) : null;
    }

    /**
     * retrieves all persisted messages from database 
     * 
     * @param account an Message obj
     * @return a list of all messages (i.e. List<Message>)
     */
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    /**
     * retrieve a single message from database by its message_id
     * 
     * @param int messsage_id
     * @return a persisted Message obj from database
     */
    public Message getAMessageByMessageID(int message_id){
        return messageDAO.getAMessageByMessageID(message_id);
    }

    /**
     * deletes a single message from database by its message_id
     * 
     * @param int messsage_id
     * @return deleted Message obj from database
     */
    public Message deleteMessageByMessageID(int message_id){
        return messageDAO.deleteMessageByMessageID(message_id);
    }

    /**
     * updates a single message from database by its message_id
     * 
     * @param int messsage_id, String message_text
     * @return updated message from database
     */
    public Message updateMessageByMessageID(int message_id, String message_text){
        Message message = getAMessageByMessageID(message_id);

        if(message == null || message_text.isEmpty() || message_text.length() > 255){
            return null;
        } else {
            return messageDAO.updateMessageByMessageID(message_id, message_text);
        }
    }

    /**
     * gets all messages posted by a single user
     * 
     * @param int posted_by
     * @return a list of all messages posted by a single user (i.e. List<Message>)
     */
    public List<Message> getAllMessagesPostedByUser(int posted_by){
        return messageDAO.getAllMessagesPostedByUser(posted_by);
    }

}
