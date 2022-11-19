package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrganiserTest {
    Organiser o1;
    User u1;
    @BeforeEach
    void runBefore() {
        o1 = new Organiser("pratham","pratham1221");
        u1 = new User("pranjal","pranjal1221");
    }

    @Test
    void testCreateEvent() {
        o1.createEvent("Hello Hacks",21,
                "It is hackathon","21-12-2022","7:30");
        assertEquals(1,Event.getEventList().size());
    }

    //@Test
//    void testNotifyChange() throws Exception {
//        o1.createEvent("Hello Hacks",21,
//                "It is hackathon","21-12-2022","7:30");
//        User u = new User("pratham","pratham1221");
//        u.buyTicket(o1.getMyShows().get(0));
//        o1.notifyChange(o1.getMyShows().get(0), "name" , "hello" );
//        assertEquals(1,o1.getMyShows().get(0).getAttendees().get(0).getMessages().size());
//    }

    @Test
    void testCancelShow() throws Exception {
        o1.createEvent("Hello Hacks",21,
                "It is hackathon","21-12-2022","7:30");
        u1.buyTicket(o1.getMyShows().get(0));
        o1.cancelShow(o1.getMyShows().get(0));
        assertEquals(0,o1.getMyShows().size());
        //assertEquals("Cancelled",u1.getMyShows().get(0).getDescription());
        //assertEquals(1,u1.getMessages().size());
    }

    @Test
    void testAllEvents() {
        assertEquals("There are no events\n", o1.allEvents());
        o1.createEvent("Hello Hacks",21,
                "It is hackathon","21-12-2022","7:30");
        assertEquals("1 " + o1.getMyShows().get(0).toString() + "\n", o1.allEvents());
    }

    @Test
    void testGetUsername() {
        assertEquals("pratham1221",o1.getUserName());
    }

}
