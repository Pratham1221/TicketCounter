package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    Event e1;

    @BeforeEach
    void runBefore() {
        e1 = new Event(new Organiser("Pratham", "pratham1221"), "Hello Hacks", 21,
                "It is hackathon", "21-12-2022", "7:30");
    }

    @Test
    void testDisplayDetailed() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        assertEquals(String.format("Event name: %-20s %s %s%nTickets Available: %d%nDescription %n%s%n",
                e1.getEventName(), String.valueOf(sdf.parse(e1.getDate())).substring(0, 10), e1.getTime(),
                e1.getTickets(), e1.getDescription()), e1.displayDetailed());
        e1.setDescription("It is a hackathon");
        assertEquals(String.format("Event name: %-20s %s %s%nTickets Available: %d%nDescription %n%s%n",
                e1.getEventName(), String.valueOf(sdf.parse(e1.getDate())).substring(0, 10), e1.getTime(),
                e1.getTickets(), e1.getDescription()), e1.displayDetailed());
        e1.setTickets(0);
        assertEquals(String.format("Event name: %-20s %s %s%nSold Out%nDescription %n%s%n", e1.getEventName(),
                String.valueOf(sdf.parse(e1.getDate())).substring(0, 10), e1.getTime(), e1.getDescription()), e1.displayDetailed());
        e1.setDate("21/12/2022");
        assertEquals("Error", e1.displayDetailed());
        e1.setTickets(1);
        e1.setDate("21-12/2022");
        assertEquals("Error", e1.displayDetailed());
        Event.getEventList().remove(e1);
    }


    @Test
    void testDisplay() {
        assertEquals(String.format("Event name: Hello Hacks          Wed Dec 21%n"), e1.toString());
        e1.setEventName("hello hecks");
        assertEquals(String.format("Event name: hello hecks          Wed Dec 21%n"), e1.toString());
        e1.setTime("7pm");
        e1.setDate("12-12/2022");
        assertEquals("Error", e1.toString());
        Event.getEventList().remove(e1);
    }

    @Test
    void testCreateTicket() throws Exception {
        assertTrue(e1.createTicket("pratham"));
        Event.getEventList().remove(e1);
    }

    @Test
    void testGetOrganiser() {
        String username = e1.getOrganiser().getUserName();
        assertEquals("pratham1221",username);
        Event.getEventList().remove(e1);
    }


    @Test
    void testShowEvents() {
        assertEquals("1 " + Event.getEventList().get(0).toString() + "\n", Event.showEvent());
        Event.getEventList().remove(e1);
        assertEquals("There are no events\n", Event.showEvent());
    }




}