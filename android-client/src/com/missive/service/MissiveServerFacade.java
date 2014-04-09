package com.missive.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.missive.model.MissiveLocation;
import com.missive.model.MissiveMessage;

public class MissiveServerFacade {

    /**
     * Returns a list of messages from the server.
     */
    public List<MissiveMessage> getMessages() {
        final List<MissiveMessage> messages = new ArrayList<MissiveMessage>();
        messages.add(new MissiveMessage("arman", "Hello World!", new MissiveLocation(49.287251, -123.141761), new Date()));
        messages.add(new MissiveMessage("david", "Hola!", new MissiveLocation(49.272679, -123.164377), new Date()));
        messages.add(new MissiveMessage("manu", "Ciao!", new MissiveLocation(49.276318, -123.134154), new Date()));
        messages.add(new MissiveMessage("micha", "O Hai!", new MissiveLocation(49.272440, -123.146878), new Date()));
        return messages;
    }
}
