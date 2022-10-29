package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import model.Event;
import model.Organiser;
import model.User;
import org.json.*;

// Represents a reader that reads TicketCounter from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads TicketCounter from file and returns it;
    // throws IOException if an error occurs reading data from file
    public void read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        if (jsonObject.length() != 0) {
            parseList(jsonObject);
        }
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses TicketCounter from JSON object and returns it
    private void parseList(JSONObject jsonObject) {
        JSONArray e = jsonObject.getJSONArray("Events List");
        JSONArray o = jsonObject.getJSONArray("Organisers List");
        JSONArray u = jsonObject.getJSONArray("Users List");
//        ArrayList<Event> eventsList = new ArrayList<Event>();
//        ArrayList<Organiser> organiserList = new ArrayList<Organiser>();
//        ArrayList<User> userList = new ArrayList<User>();
        for (Object json : e) {
            JSONObject nextThingy = (JSONObject) json;
            addEvent(nextThingy);
        }
        for (Object json : o) {
            JSONObject nextThingy = (JSONObject) json;
            addOrganiser(nextThingy);
        }
        for (Object json : u) {
            JSONObject nextThingy = (JSONObject) json;
            addUser(nextThingy);
        }

    }

    //Effects: Gets users as json objects and puts them in the usersList
    private void addUser(JSONObject jsonObject) {
        JSONArray myShows = jsonObject.getJSONArray("Shows List");
        String userName = jsonObject.getString("User Name");
        String name = jsonObject.getString("name");
        User u = new User(name, userName);
        //userList.add(u);
        for (Object json : myShows) {
            JSONObject nextThingy = (JSONObject) json;
            addShows(u, nextThingy);
        }
    }

    //Effects: Gets users shows as json objects and puts them in the users myShows
    private void addShows(User u, JSONObject jsonObject) {
        String eventName = jsonObject.getString("EventName");
        for (int i = 0; i < Event.getEventList().size(); i++) {
            if (Event.getEventList().get(i).getEventName().equals(eventName)) {
                u.getMyShows().add(Event.getEventList().get(i));
            }
        }
    }

    //Effects: Gets organisers as json objects and puts them in the organisersList
    private void addOrganiser(JSONObject jsonObject) {
        String userName = jsonObject.getString("User Name");
        String name = jsonObject.getString("name");
        if (Organiser.getOrganisersList().size() == 0) {
            new Organiser(name, userName);
        }
        int count = 0;
        for (int i = 0; i < Organiser.getOrganisersList().size(); i++) {
            if (Organiser.getOrganisersList().get(i).getUserName().equals(userName)) {
                count++;
            }
        }
        if (count == 0) {
            new Organiser(name, userName);
        }
    }

    //Effects: Gets events as json objects and puts them in the eventList
    private void addEvent(JSONObject jsonObject) {
        String eventName = jsonObject.getString("EventName");
        String date = jsonObject.getString("date");
        int tickets = jsonObject.getInt("Tickets");
        String time = jsonObject.getString("time");
        String description = jsonObject.getString("Description");
        JSONObject o = jsonObject.getJSONObject("Organiser");
        String userName = o.getString("User Name");
        String name = o.getString("name");
        Organiser org = null;
        if (Organiser.getOrganisersList().size() == 0) {
            org = new Organiser(name, userName);
        }
        for (int i = 0; i < Organiser.getOrganisersList().size(); i++) {
            if (Organiser.getOrganisersList().get(i).getUserName().equals(userName)) {
                org = Organiser.getOrganisersList().get(i);
                break;
            }
        }
        if (org == null) {
            org = new Organiser(name, userName);
        }
        Event e = new Event(org, eventName, tickets, description, date, time);
        org.getMyShows().add(e);
    }

}