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

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
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

    // EFFECTS: parses workroom from JSON object and returns it
    private void parseList(JSONObject jsonObject) {
        JSONArray e = jsonObject.getJSONArray("Events List");
        JSONArray o = jsonObject.getJSONArray("Organisers List");
        JSONArray u = jsonObject.getJSONArray("Users List");
        ArrayList<Event> eventsList = new ArrayList<Event>();
        ArrayList<Organiser> organiserList = new ArrayList<Organiser>();
        ArrayList<User> userList = new ArrayList<User>();
        for (Object json : e) {
            JSONObject nextThingy = (JSONObject) json;
            addEvent(eventsList, organiserList, nextThingy);
        }
        for (Object json : o) {
            JSONObject nextThingy = (JSONObject) json;
            addOrganiser(organiserList, nextThingy);
        }
        for (Object json : u) {
            JSONObject nextThingy = (JSONObject) json;
            addUser(userList, nextThingy);
        }

    }

    private void addUser(ArrayList<User> userList, JSONObject jsonObject) {
        JSONArray myShows = jsonObject.getJSONArray("Shows List");
        String userName = jsonObject.getString("User Name");
        String name = jsonObject.getString("name");
        User u = new User(name, userName);
        userList.add(u);
        for (Object json : myShows) {
            JSONObject nextThingy = (JSONObject) json;
            addShows(u, nextThingy);
        }
    }

    private void addShows(User u, JSONObject jsonObject) {
        String eventName = jsonObject.getString("eventName");
        for (int i = 0; i < Event.getEventList().size(); i++) {
            if (Event.getEventList().get(i).getEventName().equals(eventName)) {
                u.getMyShows().add(Event.getEventList().get(i));
            }
        }
    }

    private void addOrganiser(ArrayList<Organiser> organiserList, JSONObject jsonObject) {
        String userName = jsonObject.getString("User Name");
        String name = jsonObject.getString("name");
        Organiser org = null;
        if (organiserList.size() == 0) {
            org = new Organiser(name, userName);
            organiserList.add(org);
        }
        int count = 0;
        for (int i = 0; i < organiserList.size(); i++) {
            if (organiserList.get(i).getUserName().equals(userName)) {
                count++;
            }
        }
        if (count == 0) {
            org = new Organiser(name, userName);
            organiserList.add(org);
        }

    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void addEvent(ArrayList<Event> eventsList, ArrayList<Organiser> organiserList, JSONObject jsonObject) {
        String eventName = jsonObject.getString("EventName");
        String date = jsonObject.getString("date");
        int tickets = jsonObject.getInt("Tickets");
        String time = jsonObject.getString("time");
        String description = jsonObject.getString("Description");
        JSONObject o = jsonObject.getJSONObject("Organiser");
        String userName = o.getString("User Name");
        String name = o.getString("name");
        Organiser org = null;
        if (organiserList.size() == 0) {
            org = new Organiser(name, userName);
            organiserList.add(org);
        }
        for (int i = 0; i < organiserList.size(); i++) {
            if (organiserList.get(i).getUserName().equals(userName)) {
                org = organiserList.get(i);
                break;
            }
        }
        if (org == null) {
            org = new Organiser(name, userName);
            organiserList.add(org);
        }
        Event e = new Event(org, eventName, tickets, description, date, time);
        eventsList.add(e);
        ArrayList<Event> myShows = org.getMyShows();
        myShows.add(e);
        org.setMyShows(myShows);
    }

//    private ArrayList<Event> addMyShows(ArrayList<Event> arr, JSONObject jsonObject) {
//        String eventName = jsonObject.getString("EventName");
//        String date = jsonObject.getString("date");
//        int tickets = jsonObject.getInt("Tickets");
//        String time = jsonObject.getString("time");
//        String description = jsonObject.getString("Description");
//
//    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
//    private void addThingies(WorkRoom wr, JSONObject jsonObject) {
//        JSONArray jsonArray = jsonObject.getJSONArray("thingies");
//        for (Object json : jsonArray) {
//            JSONObject nextThingy = (JSONObject) json;
//            addThingy(wr, nextThingy);
//        }
//    }

    // EFFECTS: parses thingy from JSON object and adds it to workroom
//    private void addThingy(WorkRoom wr, JSONObject jsonObject) {
//        String name = jsonObject.getString("name");
//        Category category = Category.valueOf(jsonObject.getString("category"));
//        Thingy thingy = new Thingy(name, category);
//        wr.addThingy(thingy);
//    }
}