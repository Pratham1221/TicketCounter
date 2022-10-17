package model;

import java.text.ParseException;
import java.util.ArrayList;

public class Organiser {
    private ArrayList<Event> myShows;
    private String name;
    private String userName;

    public Organiser(String name, String userName) {
        this.userName = userName;
        this.name = name;
        this.myShows = new ArrayList<Event>();
        //this.messages = new ArrayList<String>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Event> getMyShows() {
        return myShows;
    }

    public void setMyShows(ArrayList<Event> myShows) {
        this.myShows = myShows;
    }

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
        String message = "Organiser of " + e.getEventName() + " has cancelled the event\nIt has been removed "
                + "from your events list";
        myShows.remove(e);
        Event.getEventList().remove(e);
        for (int i = 0; i < e.getAttendees().size(); i++) {
            int index = e.getAttendees().get(i).getMyShows().indexOf(e);
            e.getAttendees().get(i).getMyShows().get(index).setDescription("Cancelled");
        }
        for (int i = 0; i < e.getAttendees().size(); i++) {
            e.getAttendees().get(i).getMessages().add(message);
        }
    }

    public String allEvents() {
        String s = "";
        if (this.getMyShows().size() == 0) {
            s = "There are no events\n";
        } else {
            int[] arr = new int[this.getMyShows().size()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = i + 1;
            }
            for (int i = 0; i < this.getMyShows().size(); i++) {
                s =  s + arr[i] + " " + this.getMyShows().get(i).display() + "\n";
            }
        }
        return s;
    }

    public void notifyChange(Event e, String changeType, String change) {
        String message = "Organiser of " + e.getEventName() + " changed the " + changeType
                + " to " + change;
        for (int i = 0; i < e.getAttendees().size(); i++) {
            e.getAttendees().get(i).getMessages().add(message);
        }
    }


}
