import beans.Message;
import beans.WebSocketMessage;
import com.google.gson.Gson;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.RequestDispatcher;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@ServerEndpoint("/socketmessenger")
public class MessengerServerEndPoint {

    private static final Set<Session> sessions = new HashSet<>();
    public static List <Message> chat=new ArrayList<>();

    @OnOpen
    public void onOpen(Session session){
        sessions.add(session);

        try {
            session.getBasicRemote().sendText("{\"sessionId\": \""+ session.getId() +"\"}");
            for(Session sessionItem: sessions){
                sessionItem.getBasicRemote().sendText("{\"onlineusers\": \""+ sessions.size() +"\"}");
            }

        } catch (IOException e) {
            sessions.remove(session);
            System.out.println(e.getMessage());
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {

        Message messageObj = new Gson().fromJson(message, Message.class);
        chat.add(messageObj);
        System.out.println(messageObj.getUsername() + messageObj.getMsg());

            WebSocketMessage socketMessage = new WebSocketMessage(session.getId(), messageObj.getUsername(), messageObj.getMsg());

            for (Session sessionItem : sessions) {
                try {
                    sessionItem.getBasicRemote().sendText(new Gson().toJson(socketMessage, WebSocketMessage.class));
                } catch (IOException e) {
                    System.out.println("Get In IO Exception");
                    sessions.remove(sessionItem);
                    System.out.println(e.getMessage());
                }
            }

        if (messageObj.getMsg().equals("bye bye")) {

            sessions.remove(session);

            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

//            try {
//               session.getBasicRemote().sendText("$Leaving$"); // Mark That Client Leave Chat
//                // Print IN lOg File // List Of Wordds And Occurence of  them
//                System.out.println("List Of Your words ");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }


    }

    @OnClose
    public void onClose(Session session) {


        sessions.remove(session);
        try {
            for(Session sessionItem: sessions){
                sessionItem.getBasicRemote().sendText("{\"onlineusers\": \""+ sessions.size() +"\"}");
            }
        } catch (IOException e) {
            sessions.remove(session);
            System.out.println(e.getMessage());
        }
    }
}
