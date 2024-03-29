package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

/*Represents an Organiser of an event having name, username and
  a list of all the shows organised by the organiser.
 */
public class Organiser implements Writable {
    private static ArrayList<Organiser> organisersList = new ArrayList<Organiser>();
    private ArrayList<Event> myShows;
    private String name;
    private String userName;

    public static ArrayList<Organiser> getOrganisersList() {
        return organisersList;
    }


    //Effects : Instantiates an organiser object with name and username
    public Organiser(String name, String userName) {
        this.userName = userName;
        this.name = name;
        this.myShows = new ArrayList<Event>();
        organisersList.add(this);
    }

    public String getUserName() {
        return userName;
    }

    //Requires: A date String in the format (dd-mm-yyyy)
    //Modifies: myShows list
    //Effects: Creates an event with the following inputs and add the event to the organiser's myShows
    public void createEvent(String eventName, int tickets, String description, String date, String time) {
        Event e = new Event(this,eventName, tickets, description, date, time);
        EventLog.getInstance().logEvent(new Events(this.getName() + " created an event named: " + eventName));
        myShows.add(e);
    }

    //Modifies: myShows, Event.eventList, event.description, event.description, user.messages
    //Effects: Removes the show from Organisers events and Events List.
    //         Informs this to the attendees and changes the event description to cancelled
    public void cancelShow(Event e) {
        String message = "Organiser of " + e.getEventName() + " has cancelled the event\nIt has been removed "
                + "from your events list";
        myShows.remove(e);
        Event.getEventList().remove(e);
//        for (int i = 0; i < e.getAttendees().size(); i++) {
//            int index = e.getAttendees().get(i).getMyShows().indexOf(e);
//            e.getAttendees().get(i).getMyShows().get(index).setDescription("Cancelled");
//        }
//        for (int i = 0; i < e.getAttendees().size(); i++) {
//            e.getAttendees().get(i).getMessages().add(message);
//        }
    }

    //Effects: Returns a String of all organiser's created events
    //         or returns "There are no events" otherwise.
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
                s =  s + arr[i] + " " + this.getMyShows().get(i).toString() + "\n";
            }
        }
        return s;
    }

    //Modifies : user.messages
    //Effects: Adds an information message regarding the change in the event

    public String getName() {
        return name;
    }
    //         in all the attendees messages list
//    public void notifyChange(Event e, String changeType, String change) {
//        String message = "Organiser of " + e.getEventName() + " changed the " + changeType
//                + " to " + change;
//        for (int i = 0; i < e.getAttendees().size(); i++) {
//            e.getAttendees().get(i).getMessages().add(message);
//        }
//    }

    public ArrayList<Event> getMyShows() {
        return myShows;
    }

    // EFFECTS: returns organisers in the organisersList as a JSON array
    public static JSONArray organisersListToJson(ArrayList<Organiser> arr) {
        JSONArray jsonArray = new JSONArray();

        for (Organiser t : arr) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        //json.put("Shows List", this.myShows);
        json.put("User Name", this.userName);
        return json;
    }

//    public JSONArray myShowsToJson() {
//        JSONArray jsonArray = new JSONArray();
//        for (Event t : getMyShows()) {
//            jsonArray.put(t.toJson());
//        }
//
//        return jsonArray;
//    }




}
