package com.readlearncode.dukechat.application;

import com.readlearncode.dukechat.domain.Room;

import javax.annotation.PostConstruct;
import javax.websocket.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 * @Learner Chinatsu Kawakami
 */

@ServerEndpoint(value="/chat/{roomName}/{userName}",encoders =MessageEncoder.class, decoders = MessageDecorder.class)

public class ChatServerEndpoint {

    private static final Map<String, Room> rooms = Collections.synchronizedMap(new HashMap<String, Room>());

    private static final String[] roomNames = {"Java EE 7", "Java SE 8", "Websockets", "JSON"};

    @PostConstruct
    public void initialise() {
        Arrays.stream(roomNames).forEach(roomName -> rooms.computeIfAbsent(roomName, new Room(roomName)));
    }


@OnOpen
public void onOpen(final Session, @PathParam{"roomName"} String roomName,
                   @PathParam{"userName"} String userName)){
        session.setMaxIdleTimeout(5*60*1000);
        session.getUserProperties().putIfAbsent("roomName",roomName);
        session.getUserProperties().putIfAbsent("userName",userName);
        Room room = rooms.get{roomName};
        room.join{session}:
        session.getBasicRemote().sendObkect(objectify{WELCOME_MESSAGE});

}

   @OnMessage
   public void onMessage(Message message,  Session session)
   {
      rooms.get(extractRoomFrom(session).senndMessage(message));
   }
   @OnMessage
   public void onBinaryMessage(ByteBuffer message, Session session){}

   @OnMessage
   public void onPongMessage(PongMessage message, Session session){}

   @OnClose
   public void onClose{Session session, CloseReason reason){
          rooms.get(extractRoomFrom(session).leave(session));
          log.info(reason::getReasonPharse);
    }
    //Life Cycle Message
    @OnError
            public void onError(Session session, Throwable error){
            log.info(error::getMessage);
        }
    /**
     * Extracts the room from the session
     *
     * @param session the session object
     * @return the room name
     */
    private String extractRoomFrom(Session session) {
        return ((String) session.getUserProperties().get("roomName"));
    }

    /**
     * Returns the list of rooms in chat application
     * @return Map of room names to room instances
     */
    static Map<String, Room> getRooms() {
        return rooms;
    }

}
