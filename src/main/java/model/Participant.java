package model;

public class Participant {

    private int participantId;
    private String name;

    public Participant(int participantId, String name) {
        this.participantId = participantId;
        this.name = name;
    }

    public int getParticipantId() {
        return participantId;
    }

    public String getName() {
        return name;
    }
}