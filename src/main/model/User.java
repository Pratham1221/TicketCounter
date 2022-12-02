package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

/*Represents the User panel having the name of the user,
  username, shows list and messages list
 */
public class User implements Writable {
    private static ArrayList<User> usersList = new ArrayList<User>();
    private ArrayList<Event> myShows;
    private String name;
    private String userName;
    //private ArrayList<String> messages;

    public static ArrayList<User> getUsersList() {
        return usersList;
    }


    //Effects : Instantiates a user object with name and username
    public User(String name, String userName) {
        this.name = name;
        this.userName = userName;
        this.myShows = new ArrayList<Event>();
        //this.messages = new ArrayList<String>();
        usersList.add(this);
    }

    public String getName() {
        return name;
    }

    //Modifies: event.attendees, myShows, event.tickets
    /*Effects: Returns a String informing the user that he/she
      bought the ticket and the directory of the pdf ticket or
      returns "you have already purchased a ticket" if the user
      owns a ticket.
    */
    public String buyTicket(Event e) throws Exception {
        if (this.getMyShows().contains(e)) {
            return "You have already purchased a ticket for this show!";
        } else {
//            e.getAttendees().add(this);
            myShows.add(e);
            e.setTickets(e.getTickets() - 1);
            e.createTicket(this.name);
            EventLog.getInstance().logEvent(new Events(this.getName() + " purchased a ticket for "
                    + e.getEventName()));
            //e.getOrganiser().getMessages().add(getName() + "purchased a ticket");
            return "You have successfully bought a ticket for " + e.getEventName()
                    + " \nYour ticket has been generated! "
                    + "Please find it in " + System.getProperty("user.dir") + "\\" + e.getEventName() + ".pdf";
            //return "Thanks for buying";
        }
    }

    public ArrayList<Event> getMyShows() {
        return myShows;
    }

    public String getUserName() {
        return userName;
    }

//    public ArrayList<String> getMessages() {
//        return messages;
//    }

    //Effects: Returns a String of all user's purchased events
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

    // EFFECTS: returns events in the eventsList as a JSON array
    public static JSONArray userListToJson(ArrayList<User> arr) {
        JSONArray jsonArray = new JSONArray();

        for (User t : arr) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Shows List", Event.eventListToJson(myShows));
        json.put("name", name);
        json.put("User Name", userName);
        //json.put("messages", messages);
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
