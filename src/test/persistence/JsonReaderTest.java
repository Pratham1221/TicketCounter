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
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
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
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        try {
            Organiser o = new Organiser("pratham","pratham1221");
            Event e = new Event(o,"hello hacks",20,"it is a hackathon",
                    "21-12-2022","3");
            User u = new User("pratham","pratha12");
            JsonWriter writer = new JsonWriter("./data/testReaderGeneralWorkRoom.json");
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
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}