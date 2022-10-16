package model;

import java.text.ParseException;
import java.util.ArrayList;

public class Organiser {
    private ArrayList<Event> myShows;
    //private ArrayList<String> messages;
    private String name;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Organiser(String name, String userName) {
        this.userName = userName;
        this.name = name;
        this.myShows = new ArrayList<Event>();
        //this.messages = new ArrayList<String>();
    }

    public ArrayList<Event> getMyShows() {
        return myShows;
    }

    public void setMyShows(ArrayList<Event> myShows) {
        this.myShows = myShows;
    }

//    public ArrayList<String> getMessages() {
//        return messages;
//    }

//    public void setMessages(ArrayList<String> messages) {
//        this.messages = messages;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void createEvent(String eventName, int tickets, String description, String date, String time) {
        Event e = new Event(this, eventName, tickets, description, date, time);
        myShows.add(e);
    }

    public String myEvents() {
        if (myShows.size() == 0) {
            return null;
        } else {
            int i = 0;
            do {
                Event e = myShows.get(i);
                i++;
                return i + " " + e.display();
            } while (i < myShows.size());
        }
    }

    public void modifyDate(Event e, String date) {
        e.setDate(date);
    }

    public void modifyTime(Event e, String time) {
        e.setTime(time);
    }

    public void modifyName(Event e, String name) {
        e.setEventName(name);
    }

    public void modifyTickets(Event e, int tickets) {
        e.setTickets(tickets);
    }

    public void modifyDesciption(Event e, String description) {
        e.setDescription(description);
    }

    public void cancelShow(Event e) {
        myShows.remove(e);
        Event.getEventList().remove(e);
        for (int i = 0; i < e.getAttendees().size(); i++) {
            int index = e.getAttendees().get(i).getMyShows().indexOf(e);
            e.getAttendees().get(i).getMyShows().get(index).setDescription("Cancelled");
        }
    }

    //public void create


}
