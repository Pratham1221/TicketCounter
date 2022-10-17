package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    Event e1;
    Event e2;
    Event e3;
    @BeforeEach
    void runBefore() {
        e1 = new Event(new Organiser("Pratham","pratham1221"),"Hello Hacks",21,
                "It is hackathon","21-12-2022","7:30");
        //e2 = new Event(new Organiser("Pratham","pratham1221"),"Hello Hecks",21,
         //       "It is hackathon","21-12-2022","7:30");
    }

//    @Test
//    void testShowEvents() {
//        assertEquals("1 " + Event.getEventList().get(0).display() + "\n",Event.showEvent());
//    }

    @Test
    void testDisplayDetailed() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        assertEquals(String.format("Event name: %-20s %s %s%nTickets Available: %d%nDescription %n%s%n",
                e1.getEventName(), String.valueOf(sdf.parse(e1.getDate())).substring(0, 10), e1.getTime(),
                e1.getTickets(), e1.getDescription()), e1.displayDetailed());
        e1.setTickets(0);
        assertEquals(String.format("Event name: %-20s %s %s%nSold Out%nDescription %n%s%n", e1.getEventName(),
                String.valueOf(sdf.parse(e1.getDate())).substring(0, 10), e1.getTime(), e1.getDescription()), e1.displayDetailed());
    }

    @Test
    void testDisplay() {
        assertEquals(String.format("Event name: Hello Hacks          Wed Dec 21%n"), e1.display());
    }

    @Test
    void testCreateTicket() {
        assertTrue(e1.createTicket("pratham"));
    }

}