package model;

import java.util.ArrayList;

public class EventList {
    private ArrayList<Event> showsList;
    public EventList() {
        showsList = new ArrayList<Event>();
    }
    public void add(Event e) {
        this.showsList.add(e);
    }
}
