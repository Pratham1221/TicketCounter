package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    User u1;
    Event e1;

    @BeforeEach
    void runBefore() {
        u1 = new User("Pratham", "pratham1221");
        e1 = new Event(new Organiser("Pratham", "pratham1221"), "Hello Hacks", 21,
                "It is hackathon", "21-12-2022", "7:30");
    }

    @Test
    void testBuyTicket() throws Exception {
        assertEquals("You have successfully bought a ticket for " + e1.getEventName()
                + " \nYour ticket has been generated! "
                + "Please find it in " + System.getProperty("user.dir") + "\\" +
                e1.getEventName() + ".pdf", u1.buyTicket(e1));
        assertEquals("You have already purchased a ticket for this show!",u1.buyTicket(e1));
        Iterator<Events> i = EventLog.getInstance().iterator();
        assertEquals(u1.getName() + " purchased a ticket for " + e1.getEventName(),i.next().getDescription());

    }

    @Test
    void testAllEvents() throws Exception {
        u1.buyTicket(e1);
        assertEquals("1 " + u1.getMyShows().get(0).toString() + "\n", u1.allEvents());
        u1.getMyShows().remove(e1);
        assertEquals("There are no events\n", u1.allEvents());
    }

    @Test
    void testGetUserName() {
        assertEquals("pratham1221",u1.getUserName());
    }

    @Test
    void testGetName() {
        assertEquals("Pratham",u1.getName());
    }

}
