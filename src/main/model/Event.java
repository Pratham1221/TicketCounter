package model;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/*Represents an Event having Event Name, event organiser, attendees, no. of tickets,
  event description, date and time of event and a list of all events.
 */
public class Event implements Writable {
    private static ArrayList<Event> eventList = new ArrayList<Event>();

    public Organiser getOrganiser() {
        return organiser;
    }

    //private ArrayList<User> attendees;
    private Organiser organiser;
    private String eventName;
    private int tickets;
    private String description;
    private String date;
    private String time;

    //Requires: Organiser object, Event Name, No. of Tickets, Event Description, Event Date, Event Time
    //Modifies: this
    //Effects: Instantiates an Event object
    public Event(Organiser organiser,String eventName, int tickets, String description, String date, String time) {
        this.organiser = organiser;
        this.eventName = eventName;
        this.tickets = tickets;
        this.description = description;
        this.date = date;
        this.time = time;
        //attendees = new ArrayList<User>();
        eventList.add(this);
    }

    //Effects: Returns the detailed description of the event
    public String displayDetailed() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        if (getTickets() == 0) {
            try {
                return String.format("Event name: %-20s %s %s%nSold Out%nDescription %n%s%n", getEventName(),
                        String.valueOf(sdf.parse(getDate())).substring(0, 10), getTime(), getDescription());
            } catch (ParseException e) {
                return  "Error";
            }
        } else {
            try {
                return String.format("Event name: %-20s %s %s%nTickets Available: %d%nDescription %n%s%n",
                        getEventName(), String.valueOf(sdf.parse(getDate())).substring(0, 10), getTime(),
                        getTickets(), getDescription());
            } catch (ParseException e) {
                return "Error";
            }
        }
    }

    //Effects: Returns a brief description of the event
    public String display() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return String.format("Event name: %-20s %s%n", getEventName(),
                    String.valueOf(sdf.parse(getDate())).substring(0, 10));
        } catch (ParseException e) {
            return "Error";
        }

    }

    //Requires: A string with a name is entered
    //Effects: Creates an event ticket pdf format and stores it in the project directory
    public boolean createTicket(String name) throws Exception {
        Document document = new Document();
        Rectangle rec =  new Rectangle(PageSize.A4);
        document.setPageSize(rec);
        PdfWriter.getInstance(document, new FileOutputStream(this.getEventName() + ".pdf"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        document.open();
        Paragraph p = new Paragraph(getEventName() + "!",FontFactory.getFont(FontFactory.TIMES_BOLD,20,Font.BOLD));
        p.setSpacingAfter(30f);
        document.add(p);
        this.addThings(document,name);
//        document.add(new Paragraph("Registration Confirmation"));
//        document.add(new Paragraph("Name: " + name));
//        document.add(new Paragraph("Date/time of event: "
//                + String.valueOf(sdf.parse(getDate())).substring(0, 10) + " " + getTime()));
//        document.add(new Paragraph("Brief description of the event " + "\n" + getDescription()));
//        document.add(Chunk.NEWLINE);
//        document.add(new Paragraph("Thank you so much for attending!\n\nWe will meet you at the event."));
        document.close();
        return true;
    }

    //Effects: Edits the pdf tickets
    public void addThings(Document document,String name) throws DocumentException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        document.add(new Paragraph("Registration Confirmation"));
        document.add(new Paragraph("Name: " + name));
        document.add(new Paragraph("Date/time of event: "
                + String.valueOf(sdf.parse(getDate())).substring(0, 10) + " " + getTime()));
        document.add(new Paragraph("Brief description of the event " + "\n" + getDescription()));
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Thank you so much for attending!\n\nWe will meet you at the event."));
    }

    //Effects: Returns a String of all events created
    //         or returns "There are no events" otherwise.
    public static String showEvent() {
        String s = "";
        if (getEventList().size() == 0) {
            s = "There are no events\n";
        } else {
            int[] arr = new int[getEventList().size()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = i + 1;
            }
            for (int i = 0; i < getEventList().size(); i++) {
                s =  s + arr[i] + " " + getEventList().get(i).display() + "\n";
            }
        }
        return s;
    }

    public static ArrayList<Event> getEventList() {
        return eventList;
    }

//    public ArrayList<User> getAttendees() {
//        return attendees;
//    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTickets() {
        return tickets;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    // EFFECTS: returns events in the eventsList as a JSON array
    public static JSONArray eventListToJson(ArrayList<Event> arr) {
        JSONArray jsonArray = new JSONArray();

        for (Event t : arr) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }


    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        //json.put("Attendees List", attendees);
        json.put("Organiser", organiser.toJson());
        json.put("EventName", eventName);
        json.put("Tickets",tickets);
        json.put("Description", description);
        json.put("date", date);
        json.put("time", time);
        return json;
    }

//    public JSONArray attendeesToJson() {
//        JSONArray jsonArray = new JSONArray();
//
//        for (User t : attendees) {
//            jsonArray.put(t.toJson());
//        }
//
//        return jsonArray;
//    }
}
