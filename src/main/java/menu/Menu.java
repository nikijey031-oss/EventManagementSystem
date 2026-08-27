package menu;

import model.Event;
import model.Participant;
import service.EventService;
import service.ParticipantService;

import java.util.Scanner;

public class Menu {

    Scanner sc = new Scanner(System.in);

    EventService service;
    ParticipantService participantService;

    public Menu(EventService service,
                ParticipantService participantService) {

        this.service = service;
        this.participantService = participantService;
    }

    public void start() {

        int choice;

        do {

            System.out.println("\n===== Event Management System =====");
            System.out.println("1. Add Event");
            System.out.println("2. View Events");
            System.out.println("3. Search Event");
            System.out.println("4. Update Event");
            System.out.println("5. Delete Event");
            System.out.println("6. Register Participant");
            System.out.println("7. View Participants");
            System.out.println("8. Search Participant");
            System.out.println("9. Delete Participant");
            System.out.println("10. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Event ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Event Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Event Date: ");
                    String date = sc.nextLine();

                    System.out.print("Enter Venue: ");
                    String venue = sc.nextLine();

                    Event event = new Event(id, name, date, venue);

                    service.addEvent(event);
                    break;

                case 2:
                    service.viewEvents();
                    break;

                case 3:
                    System.out.print("Enter Event ID to search: ");
                    int searchId = sc.nextInt();

                    service.searchEvent(searchId);
                    break;

                case 4:
                    System.out.print("Enter Event ID to update: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter new Event Name: ");
                    String newName = sc.nextLine();

                    System.out.print("Enter new Event Date: ");
                    String newDate = sc.nextLine();

                    System.out.print("Enter new Venue: ");
                    String newVenue = sc.nextLine();

                    service.updateEvent(
                            updateId,
                            newName,
                            newDate,
                            newVenue
                    );
                    break;

                case 5:
                    System.out.print("Enter Event ID to delete: ");
                    int deleteId = sc.nextInt();

                    service.deleteEvent(deleteId);
                    break;

                case 6:
                    System.out.print("Enter Participant ID: ");
                    int participantId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Participant Name: ");
                    String participantName = sc.nextLine();

                    Participant participant =
                            new Participant(
                                    participantId,
                                    participantName
                            );

                    participantService.addParticipant(participant);
                    break;

                case 7:
                    participantService.viewParticipants();
                    break;

                case 8:
                    System.out.print("Enter Participant ID to search: ");
                    int searchParticipantId = sc.nextInt();

                    participantService.searchParticipant(
                            searchParticipantId
                    );
                    break;

                case 9:
                    System.out.print("Enter Participant ID to delete: ");
                    int deleteParticipantId = sc.nextInt();

                    participantService.deleteParticipant(
                            deleteParticipantId
                    );
                    break;

                case 10:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 10);
    }
}