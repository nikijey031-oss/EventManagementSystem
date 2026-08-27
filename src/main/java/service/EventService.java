package service;

import model.Event;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
@Service
public class EventService {

    ArrayList<Event> events = new ArrayList<>();

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
            System.out.println(
                    "ID: " + e.getEventId()
                            + " | Name: " + e.getEventName()
                            + " | Date: " + e.getEventDate()
                            + " | Venue: " + e.getVenue()
            );
        }
    }

    // SEARCH EVENT
    public void searchEvent(int id) {

        for (Event e : events) {

            if (e.getEventId() == id) {

                System.out.println("\nEvent Found");
                System.out.println("ID: " + e.getEventId());
                System.out.println("Name: " + e.getEventName());
                System.out.println("Date: " + e.getEventDate());
                System.out.println("Venue: " + e.getVenue());

                return;
            }
        }

        System.out.println("Event Not Found.");
    }

    // UPDATE EVENT
    public void updateEvent(
            int id,
            String eventName,
            String eventDate,
            String venue) {

        for (Event e : events) {

            if (e.getEventId() == id) {

                e.setEventName(eventName);
                e.setEventDate(eventDate);
                e.setVenue(venue);

                System.out.println("Event Updated Successfully.");
                return;
            }
        }

        System.out.println("Event Not Found.");
    }

    // DELETE EVENT
    public void deleteEvent(int id) {

        for (Event e : events) {

            if (e.getEventId() == id) {

                events.remove(e);

                System.out.println("Event Deleted Successfully.");
                return;
            }
        }

        System.out.println("Event Not Found.");

    }
    // GET ALL EVENTS FOR WEBSITE
    public ArrayList<Event> getEvents() {
        return events;
    }
}