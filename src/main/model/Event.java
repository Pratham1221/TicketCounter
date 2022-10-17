package model;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class Event {
    private static ArrayList<Event> eventList = new ArrayList<Event>();
    private ArrayList<User> attendees;
    private Organiser organiser;
    private String eventName;
    private int tickets;
    private String description;
    private String date;
    private String time;

    public Event(Organiser organiser, String eventName, int tickets, String description, String date, String time) {
        this.organiser = organiser;
        this.eventName = eventName;
        this.tickets = tickets;
        this.description = description;
        this.date = date;
        this.time = time;
        attendees = new ArrayList<User>();
        eventList.add(this);
    }

    public static ArrayList<Event> getEventList() {
        return eventList;
    }

//    public static void setEventList(ArrayList<Event> eventList) {
//        Event.eventList = eventList;
//    }

    public ArrayList<User> getAttendees() {
        return attendees;
    }

//    public void setAttendees(ArrayList<User> attendees) {
//        this.attendees = attendees;
//    }

//    public Organiser getOrganiser() {
//        return organiser;
//    }

//    public void setOrganiser(Organiser organiser) {
//        this.organiser = organiser;
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

    public String display() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return String.format("Event name: %-20s %s%n", getEventName(),
                    String.valueOf(sdf.parse(getDate())).substring(0, 10));
        } catch (ParseException e) {
            return "Error";
        }

    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
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
        document.add(new Paragraph("Registration Confirmation"));
        document.add(new Paragraph("Name: " + name));
        document.add(new Paragraph("Date/time of event: "
                + String.valueOf(sdf.parse(getDate())).substring(0, 10) + " " + getTime()));
        document.add(new Paragraph("Brief description of the event " + "\n" + getDescription()));
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Thank you so much for attending!\n\nWe will meet you at the event."));
        document.close();
        return true;
    }

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

//    public static void main(String[] args) {
//        Event e = new Event(new Organiser("Pratham","pratham1221"),"Hello Hacks",21,
//                "It is hackathon","21-12-2022","7:30");
//
//        System.out.println(Event.getEventList().size());
//    }
}
