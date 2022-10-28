package persistence;

import model.Event;
import model.Organiser;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTicketCounter() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTicketCounter.json");
        try {
            reader.read();
            assertEquals(0, Event.getEventList().size());
            assertEquals(0, Organiser.getOrganisersList().size());
            assertEquals(0, User.getUsersList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTicketCounter() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTicketCounter.json");
        try {
            Organiser o = new Organiser("pratham","pratham1221");
            Event e = new Event(o,"hello hacks",20,"it is a hackathon",
                    "21-12-2022","3");
            Organiser o1 = new Organiser("pratham","pratha12");
            Event e1 = new Event(o1,"hello camp",20,"it is a hackathon",
                    "21-12-2022","3");
            User u = new User("pratham","pratha12");
            JsonWriter writer = new JsonWriter("./data/testReaderGeneralTicketCounter.json");
            writer.open();
            writer.write();
            writer.close();

            reader.read();
            assertEquals("hello hacks", Event.getEventList().get(0).getEventName());
            assertEquals(20, Event.getEventList().get(0).getTickets());
            assertEquals("21-12-2022", Event.getEventList().get(0).getDate());
            assertEquals("3", Event.getEventList().get(0).getTime());
            assertEquals("it is a hackathon", Event.getEventList().get(0).getDescription());
            assertEquals("pratham1221", Event.getEventList().get(0).getOrganiser().getUserName());
            assertEquals("pratham", Event.getEventList().get(0).getOrganiser().getName());
            assertEquals("pratham", Organiser.getOrganisersList().get(0).getName());
            assertEquals("pratham1221", Organiser.getOrganisersList().get(0).getUserName());
            assertEquals("pratha12", User.getUsersList().get(0).getUserName());
            assertEquals("pratham", User.getUsersList().get(0).getName());
            assertEquals(0,User.getUsersList().get(0).getMyShows().size());
            assertEquals("pratham", Organiser.getOrganisersList().get(1).getName());
            assertEquals("pratha12", Organiser.getOrganisersList().get(1).getUserName());
            assertEquals("hello camp", Event.getEventList().get(1).getEventName());
            assertEquals(20, Event.getEventList().get(1).getTickets());
            assertEquals("21-12-2022", Event.getEventList().get(1).getDate());
            assertEquals("3", Event.getEventList().get(1).getTime());
            assertEquals("it is a hackathon", Event.getEventList().get(1).getDescription());
            assertEquals("pratha12", Event.getEventList().get(1).getOrganiser().getUserName());
            assertEquals("pratham", Event.getEventList().get(1).getOrganiser().getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
        Event.getEventList().clear();
        Organiser.getOrganisersList().clear();
        User.getUsersList().clear();
    }

    @Test
    void testReaderOrganiserNoEvents() {
        JsonReader reader = new JsonReader("./data/testReaderOrganiserNoEvents.json");
        try {
            Organiser o = new Organiser("pratham","pratham1221");
            JsonWriter writer = new JsonWriter("./data/testReaderOrganiserNoEvents.json");
            writer.open();
            writer.write();
            writer.close();
            reader.read();
            assertEquals("pratham", Organiser.getOrganisersList().get(0).getName());
            assertEquals("pratham1221", Organiser.getOrganisersList().get(0).getUserName());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
        Organiser.getOrganisersList().clear();
    }

    @Test
    void testReaderMultipleOrganiserNoEvents() {
        JsonReader reader = new JsonReader("./data/testReaderMultipleOrganiserNoEvents.json");
        try {
            Organiser o = new Organiser("pratham","pratham1221");
            Organiser o1 = new Organiser("pratham","pratha12");
            JsonWriter writer = new JsonWriter("./data/testReaderMultipleOrganiserNoEvents.json");
            writer.open();
            writer.write();
            writer.close();
            reader.read();
            assertEquals("pratham", Organiser.getOrganisersList().get(0).getName());
            assertEquals("pratham1221", Organiser.getOrganisersList().get(0).getUserName());
            assertEquals("pratham", Organiser.getOrganisersList().get(1).getName());
            assertEquals("pratha12", Organiser.getOrganisersList().get(1).getUserName());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
        Organiser.getOrganisersList().clear();
    }
}