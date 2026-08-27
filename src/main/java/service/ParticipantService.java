package service;

import model.Participant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Locale;

@Service
public class ParticipantService {

    private final ArrayList<Participant> participants = new ArrayList<>();

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
            System.out.println("ID: " + p.getParticipantId()
                    + " | Name: " + p.getName()
                    + " | Event ID: " + p.getEventId());
        }
    }

    // SEARCH PARTICIPANT (console-compatible API)
    public void searchParticipant(int id) {
        Participant participant = findById(id);
        if (participant == null) {
            System.out.println("Participant Not Found.");
            return;
        }

        System.out.println("\nParticipant Found");
        System.out.println("ID: " + participant.getParticipantId());
        System.out.println("Name: " + participant.getName());
        System.out.println("Event ID: " + participant.getEventId());
    }

    // DELETE PARTICIPANT
    public void deleteParticipant(int id) {
        if (deleteParticipantAndReturn(id)) {
            System.out.println("Participant Removed Successfully.");
        } else {
            System.out.println("Participant Not Found.");
        }
    }

    public Participant findById(int id) {
        for (Participant participant : participants) {
            if (participant.getParticipantId() == id) return participant;
        }
        return null;
    }

    public boolean deleteParticipantAndReturn(int id) {
        return participants.removeIf(participant -> participant.getParticipantId() == id);
    }

    public void deleteByEventId(int eventId) {
        participants.removeIf(participant -> participant.getEventId() == eventId);
    }

    public ArrayList<Participant> findByEventId(int eventId) {
        ArrayList<Participant> matches = new ArrayList<>();
        for (Participant participant : participants) {
            if (participant.getEventId() == eventId) matches.add(participant);
        }
        return matches;
    }

    public ArrayList<Participant> searchParticipants(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>(participants);
        }

        String normalized = query.trim().toLowerCase(Locale.ROOT);
        ArrayList<Participant> matches = new ArrayList<>();
        for (Participant participant : participants) {
            if (String.valueOf(participant.getParticipantId()).contains(normalized)
                    || (participant.getName() != null
                    && participant.getName().toLowerCase(Locale.ROOT).contains(normalized))) {
                matches.add(participant);
            }
        }
        return matches;
    }

    public ArrayList<Participant> getParticipants() {
        return participants;
    }
}
