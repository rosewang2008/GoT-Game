package com.queens.game.networking;

import com.queens.game.client.Client;


/**
 * Created by aditisri on 1/26/18.
 */
public class NewPlayerRequest implements Request{
    private Message.Type type;
    private int id;
    public NewPlayerRequest(){
        this.type = Type.NEW_PLAYER;
        this.id = Client.getUniqueId();

    }
    @Override
    public Type getType() {
        return this.type;
    }


    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int getPlayerId() {
        return 0;
    }


}
