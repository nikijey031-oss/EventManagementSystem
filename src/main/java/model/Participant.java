package model;

public class Participant {

    private int participantId;
    private String name;
    private int eventId;

    public Participant() {
    }

    public Participant(int participantId, String name) {
        this(participantId, name, 0);
    }

    public Participant(int participantId, String name, int eventId) {
        this.participantId = participantId;
        this.name = name;
        this.eventId = eventId;
    }

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
