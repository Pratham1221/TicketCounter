package model;

import java.util.ArrayList;

public class User {
    private static ArrayList<Event> myShows = new ArrayList<Event>();
    private String name;

    public String buyTicket(Event e) {
        e.getAttendees().add(this);
        e.setTickets(e.getTickets() - 1);
        //e.getOrganiser().getMessages().add(getName() + "purchased a ticket");
        return "You have successfully bought a ticket for " + e.getEventName();
    }

    public static ArrayList<Event> getMyShows() {
        return myShows;
    }

    public static void setMyShows(ArrayList<Event> myShows) {
        User.myShows = myShows;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
