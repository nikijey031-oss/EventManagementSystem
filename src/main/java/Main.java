import menu.Menu;
import model.Admin;
import service.EventService;
import service.ParticipantService;

public class Main {

    public static void main(String[] args) {

        Admin admin = new Admin("Admin");
        admin.displayRole();

        EventService eventService = new EventService();

        ParticipantService participantService =
                new ParticipantService();

        Menu menu = new Menu(
                eventService,
                participantService
        );

        menu.start();
    }
}