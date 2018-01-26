package com.queens.game.networking;

import java.io.OutputStreamWriter;

/**
 * Created by aditisri on 1/24/18.
 */
public interface Message {
    enum Children{
        REQUEST, RESPONSE
    }

    enum Type{
        LOCATION_UPDATE, NEW_PLAYER,
    }

    Type getType();


}
