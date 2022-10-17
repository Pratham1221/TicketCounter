package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrganiserTest {
    Organiser o1;

    @BeforeEach
    void runBefore() {
        o1 = new Organiser("pratham","pratham1221");
    }

    @Test
    void testCreateEvent() {
        o1.createEvent("Hello Hacks",21,
                "It is hackathon","21-12-2022","7:30");
        assertEquals(1,Event.getEventList().size());
    }

    @Test
    void testNotifyChange() {
        o1.createEvent("Hello Hacks",21,
                "It is hackathon","21-12-2022","7:30");
        User u = new User("pratham","pratham1221");
        u.buyTicket(o1.getMyShows().get(0));
        o1.notifyChange(o1.getMyShows().get(0), "name" , "hello" );
        assertEquals(1,o1.getMyShows().get(0).getAttendees().get(0).getMessages().size());
    }

    @Test
    void testCancelShow() {
        o1.createEvent("Hello Hacks",21,
                "It is hackathon","21-12-2022","7:30");
        o1.cancelShow(o1.getMyShows().get(0));
        assertEquals(0,o1.getMyShows().size());
    }

}
