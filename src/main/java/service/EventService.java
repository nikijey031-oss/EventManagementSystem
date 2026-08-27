package service;

import model.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Locale;

@Service
public class EventService {

    private final ArrayList<Event> events = new ArrayList<>();

    // ADD EVENT
    public void addEvent(Event event) {
        events.add(event);
        System.out.println("Event Added Successfully.");
    }

    // VIEW ALL EVENTS
    public void viewEvents() {
        if (events.isEmpty()) {
            System.out.println("No events available.");
            return;
        }

        System.out.println("\n===== ALL EVENTS =====");
        for (Event e : events) {
            System.out.println("ID: " + e.getEventId()
                    + " | Name: " + e.getEventName()
                    + " | Date: " + e.getEventDate()
                    + " | Venue: " + e.getVenue());
        }
    }

    // SEARCH EVENT (console-compatible API)
    public void searchEvent(int id) {
        Event event = findById(id);
        if (event == null) {
            System.out.println("Event Not Found.");
            return;
        }

        System.out.println("\nEvent Found");
        System.out.println("ID: " + event.getEventId());
        System.out.println("Name: " + event.getEventName());
        System.out.println("Date: " + event.getEventDate());
        System.out.println("Venue: " + event.getVenue());
    }

    // UPDATE EVENT
    public void updateEvent(int id, String eventName, String eventDate, String venue) {
        Event event = findById(id);
        if (event == null) {
            System.out.println("Event Not Found.");
            return;
        }

        event.setEventName(eventName);
        event.setEventDate(eventDate);
        event.setVenue(venue);
        System.out.println("Event Updated Successfully.");
    }

    // DELETE EVENT
    public void deleteEvent(int id) {
        if (deleteEventAndReturn(id)) {
            System.out.println("Event Deleted Successfully.");
        } else {
            System.out.println("Event Not Found.");
        }
    }

    public Event findById(int id) {
        for (Event event : events) {
            if (event.getEventId() == id) return event;
        }
        return null;
    }

    public boolean deleteEventAndReturn(int id) {
        return events.removeIf(event -> event.getEventId() == id);
    }

    public ArrayList<Event> searchEvents(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>(events);
        }

        String normalized = query.trim().toLowerCase(Locale.ROOT);
        ArrayList<Event> matches = new ArrayList<>();
        for (Event event : events) {
            if (String.valueOf(event.getEventId()).contains(normalized)
                    || contains(event.getEventName(), normalized)
                    || contains(event.getEventDate(), normalized)
                    || contains(event.getVenue(), normalized)) {
                matches.add(event);
            }
        }
        return matches;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    private boolean contains(String value, String query) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(query);
    }
}
