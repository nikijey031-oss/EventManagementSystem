package service;

import model.Participant;

import java.util.ArrayList;

public class ParticipantService {

    ArrayList<Participant> participants = new ArrayList<>();

    // REGISTER PARTICIPANT
    public void addParticipant(Participant participant) {
        participants.add(participant);
        System.out.println("Participant Registered Successfully.");
    }

    // VIEW PARTICIPANTS
    public void viewParticipants() {

        if (participants.isEmpty()) {
            System.out.println("No participants registered.");
            return;
        }

        System.out.println("\n===== ALL PARTICIPANTS =====");

        for (Participant p : participants) {
            System.out.println(
                    "ID: " + p.getParticipantId()
                            + " | Name: " + p.getName()
            );
        }
    }

    // SEARCH PARTICIPANT
    public void searchParticipant(int id) {

        for (Participant p : participants) {

            if (p.getParticipantId() == id) {

                System.out.println("\nParticipant Found");
                System.out.println("ID: " + p.getParticipantId());
                System.out.println("Name: " + p.getName());

                return;
            }
        }

        System.out.println("Participant Not Found.");
    }

    // DELETE PARTICIPANT
    public void deleteParticipant(int id) {

        for (Participant p : participants) {

            if (p.getParticipantId() == id) {

                participants.remove(p);

                System.out.println("Participant Removed Successfully.");
                return;
            }
        }

        System.out.println("Participant Not Found.");
    }
}