package persistence;


import model.Event;
import model.Organiser;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            //WorkRoom wr = new WorkRoom("My work room");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            //WorkRoom wr = new WorkRoom("My work room");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write();
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            reader.read();
            assertEquals(0,Event.getEventList().size());
            assertEquals(0, Organiser.getOrganisersList().size());
            assertEquals(0, User.getUsersList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            //WorkRoom wr = new WorkRoom("My work room");
//            wr.addThingy(new Thingy("saw", Category.METALWORK));
//            wr.addThingy(new Thingy("needle", Category.STITCHING));
            Organiser o = new Organiser("pratham","pratham1221");
            Event e = new Event(o,"hello hacks",20,"it is a hackathon",
                    "21-12-2022","3");
            User u = new User("pratham","pratha12");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write();
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
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
//            List<Thingy> thingies = wr.getThingies();
//            assertEquals(2, thingies.size());
//            checkThingy("saw", Category.METALWORK, thingies.get(0));
//            checkThingy("needle", Category.STITCHING, thingies.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}