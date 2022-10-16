package model;

import java.util.ArrayList;

public class User {
    private ArrayList<Event> myShows;
    private String name;
    private String userName;
    private ArrayList<String> messages;

    public User(String name, String userName) {
        this.name = name;
        this.userName = userName;
        this.myShows = new ArrayList<Event>();
        this.messages = new ArrayList<String>();
    }

    public String buyTicket(Event e) {
        e.getAttendees().add(this);
        myShows.add(e);
        e.setTickets(e.getTickets() - 1);
        //e.getOrganiser().getMessages().add(getName() + "purchased a ticket");
        return "You have successfully bought a ticket for " + e.getEventName();
    }

    public ArrayList<Event> getMyShows() {
        return myShows;
    }

    public void setMyShows(ArrayList<Event> myShows) {
        this.myShows = myShows;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }
}
