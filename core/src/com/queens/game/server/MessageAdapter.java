package com.queens.game.server;

import com.google.gson.*;
import com.queens.game.networking.LocationUpdate;
import com.queens.game.networking.Message;

import javax.swing.*;
import java.lang.reflect.Type;

/**
 * Created by aditisri on 1/25/18.
 */
public class MessageAdapter implements JsonSerializer, JsonDeserializer{
    private static final String TYPE = "TYPE";
    private static final String CONTENT = "CONTENT";
    @Override
    public Message deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String typeString = jsonObject.get(TYPE).getAsString();
        Message.Type type = Message.Type.valueOf(typeString);
        Class objClass = null;
        switch(type){
            case LOCATION_UPDATE:
                objClass = LocationUpdate.class;
                break;
        }

        return context.deserialize(jsonObject.get(CONTENT), objClass);
    }

    @Override
    public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        Message m = (Message) src;
        jsonObject.addProperty(TYPE, m.getType().toString());
        jsonObject.add(CONTENT, context.serialize(src));
        return jsonObject;
    }
}
