package com.queens.game.networking;

import java.io.OutputStreamWriter;

/**
 * Created by aditisri on 1/24/18.
 */
public interface Message {
    enum Type{
        LOCATION_UPDATE
    }

    Type getType();

    void write(OutputStreamWriter out);

}
