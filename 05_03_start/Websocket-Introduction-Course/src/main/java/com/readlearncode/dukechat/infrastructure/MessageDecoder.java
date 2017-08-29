package com.readlearncode.dukechat.infrastructure;

import com.readlearncode.dukechat.domain.Message;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * @author Alex Theedom
 * @version 1.0
 * Editor Chinatsu  Kawakami
 */
public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(final String textMessage) throws DecodeException {
        Message message = new Message();
        //Add Json
        JsonReader jsonReader = Json.createReader(new StringReader(textMessage));
        JsonObject jsonObject = jsonReader.readObject();
        message.setContent(jsonObject.getString(name "content"));
        message.setSender(jsonObject.getString(name "sender"));
        message.setRecived(LocalTime.now().toString());
        // TODO: Implement code the decodes the text message String and returns a Message object
        return message;
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig config) {
        // not implemented
    }

    @Override
    public void destroy() {
        // not implemented
    }
}
