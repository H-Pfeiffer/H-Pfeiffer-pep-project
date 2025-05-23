package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;
// import java.lang.Integer;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    // create your constructor with a new instance of your Service 
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }


    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        // define other endpoints here 
        app.post("register", this::postAccountHandler);
        app.post("login", this::postLoginHandler);
        app.post("messages", this::postMessageHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getAMessageByIDHandler);
        app.delete("messages/{message_id}", this::deleteMessageByMessageIDHandler);
        app.patch("messages/{message_id}", this::updateMessagebyMessageIDHandler);
        app.get("accounts/{account_id}/messages", this::getAllMessagesPostedByUserHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Handler to register an account 
     * @param ctx This obj handles HTTP requests & generates responses within Javalin. It will
     *            be available to this method automatically via app.post method. 
     */
    private void postAccountHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account registeredAccount = accountService.registerAccount(account);
        if(registeredAccount != null){
            ctx.json(mapper.writeValueAsString(registeredAccount));
        } else {
            ctx.status(400);
        }
    }
    
    /**
     * Handler logs a user into their existing account
     * @param ctx 
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggedIntoAccount = accountService.loginAccount(account);
        if(loggedIntoAccount != null){
            ctx.json(mapper.writeValueAsString(loggedIntoAccount));
        } else {
            ctx.status(401);
        }
    }

    /**
     * posts a new message 
     * @param ctx 
     */
    private void postMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage != null){
            ctx.json(mapper.writeValueAsString(addedMessage));
        } else {
            ctx.status(400);
        }
    }

    /**
     * retrieves all existing messages
     * @param ctx 
     */
    private void getAllMessagesHandler(Context ctx){
        ctx.json(messageService.getAllMessages());
    }

    /**
     * retrieves a message by a message_id
     * @param ctx 
     */
    private void getAMessageByIDHandler(Context ctx){
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getAMessageByMessageID(message_id);
        if (message != null){
            ctx.json(message);
        } else {
            ctx.result("");
        }
    }

    /**
     * deletes a message by a message_id
     * @param ctx 
     */
    private void deleteMessageByMessageIDHandler(Context ctx){
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessageByMessageID(message_id);
        if (message != null){
            ctx.json(message);
        } else {
            ctx.result("");
        }
    }

    /**
     * updates a message by a message_id
     * @param ctx 
     */
    private void updateMessagebyMessageIDHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        String message_text = message.getMessage_text();

        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessageByMessageID(message_id, message_text);

        if(updatedMessage != null){
            ctx.json(updatedMessage);
        } else {
            ctx.status(400);
        }
    }

    /**
     * gets a message posted by a specific user
     * @param ctx 
     */
    private void getAllMessagesPostedByUserHandler(Context ctx){
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(messageService.getAllMessagesPostedByUser(account_id));
    }
}