package com.queens.game.networking;

import com.google.gson.*;
import com.queens.game.networking.LocationUpdateRequest;
import com.queens.game.networking.Message;

import java.lang.reflect.Type;

/**
 * Created by aditisri on 1/25/18.
 */
public class MessageAdapter implements JsonSerializer, JsonDeserializer{
    private static final String CHILD_TYPE = "CHILD_TYPE";
    private static final String TYPE = "TYPE";
    private static final String CONTENT = "CONTENT";
    @Override
    public Message deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String typeString = jsonObject.get(TYPE).getAsString();
        Message.Type type = Message.Type.valueOf(typeString);
        String childTypeString = jsonObject.get(CHILD_TYPE).getAsString();
        Message.Children child_type = Message.Children.valueOf(childTypeString);
        Class objClass = getClassFromInfo(child_type, type);
        return context.deserialize(jsonObject.get(CONTENT), objClass);
    }

    @Override
    public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        Message m = (Message) src;
        if(m instanceof Request){
            jsonObject.addProperty(CHILD_TYPE, Message.Children.REQUEST.toString());
        }
        else if(m instanceof Response){
            jsonObject.addProperty(CHILD_TYPE, Message.Children.RESPONSE.toString());
        }
        jsonObject.addProperty(TYPE, m.getType().toString());
        jsonObject.add(CONTENT, context.serialize(src));
        return jsonObject;
    }

    public Class getClassFromInfo(Message.Children childType, Message.Type type){
        switch(type){
            case LOCATION_UPDATE:
                switch(childType){
                    case REQUEST:
                        return LocationUpdateRequest.class;
                    case RESPONSE:
                        return LocationUpdateResponse.class;
                }
                break;
            case NEW_PLAYER:
                switch(childType){
                    case REQUEST:
                        return NewPlayerRequest.class;
                    case RESPONSE:
                        return NewPlayerResponse.class;
                }
                break;
            case SCOUTING:
                switch(childType){
                    case REQUEST:
                        return ScoutingRequest.class;
                    case RESPONSE:
                        return ScoutingResponse.class;
                }
                break;
            case ENVIRONMENT_SWITCH:
                switch(childType){
                    case REQUEST:
                        return EnvironmentSwitchRequest.class;
                    case RESPONSE:
                        return EnvironmentSwitchResponse.class;
                }
                break;
            case COLLISION_VERIFICATION:
                switch (childType){
                    case REQUEST:
                        return CollisionVerificationRequest.class;
                    case RESPONSE:
                        return CollisionVerificationResponse.class;
                }
                break;
        }
        return null;
    }
}
