package com.example.eventmanagement;

import model.Event;
import model.Participant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.EventService;
import service.ParticipantService;

@Controller
public class HomeController {

    private final EventService eventService;
    private final ParticipantService participantService;

    public HomeController(EventService eventService, ParticipantService participantService) {
        this.eventService = eventService;
        this.participantService = participantService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("events", eventService.getEvents());
        model.addAttribute("eventCount", eventService.getEvents().size());
        model.addAttribute("participantCount", participantService.getParticipants().size());
        return "index";
    }

    @GetMapping("/events")
    public String events(@RequestParam(required = false) String query, Model model) {
        model.addAttribute("events", eventService.searchEvents(query));
        model.addAttribute("query", query == null ? "" : query);
        return "events/list";
    }

    @GetMapping("/events/new")
    public String newEvent(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("pageTitle", "Add event");
        model.addAttribute("formAction", "/events");
        return "events/form";
    }

    @PostMapping("/events")
    public String addEvent(@ModelAttribute Event event, RedirectAttributes redirectAttributes) {
        String error = validateEvent(event);
        if (error != null) {
            redirectAttributes.addFlashAttribute("error", error);
            return "redirect:/events/new";
        }
        if (eventService.findById(event.getEventId()) != null) {
            redirectAttributes.addFlashAttribute("error", "An event with that ID already exists.");
            return "redirect:/events/new";
        }
        eventService.addEvent(event);
        redirectAttributes.addFlashAttribute("success", "Event added successfully.");
        return "redirect:/events";
    }

    @GetMapping("/events/{id}/edit")
    public String editEvent(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Event event = eventService.findById(id);
        if (event == null) {
            redirectAttributes.addFlashAttribute("error", "Event not found.");
            return "redirect:/events";
        }
        model.addAttribute("event", event);
        model.addAttribute("pageTitle", "Update event");
        model.addAttribute("formAction", "/events/" + id);
        return "events/form";
    }

    @PostMapping("/events/{id}")
    public String updateEvent(@PathVariable int id, @ModelAttribute Event event,
                              RedirectAttributes redirectAttributes) {
        String error = validateEvent(event);
        if (error != null) {
            redirectAttributes.addFlashAttribute("error", error);
            return "redirect:/events/" + id + "/edit";
        }
        if (eventService.findById(id) == null) {
            redirectAttributes.addFlashAttribute("error", "Event not found.");
            return "redirect:/events";
        }
        eventService.updateEvent(id, event.getEventName(), event.getEventDate(), event.getVenue());
        redirectAttributes.addFlashAttribute("success", "Event updated successfully.");
        return "redirect:/events";
    }

    @PostMapping("/events/{id}/delete")
    public String deleteEvent(@PathVariable int id, RedirectAttributes redirectAttributes) {
        if (eventService.deleteEventAndReturn(id)) {
            participantService.deleteByEventId(id);
            redirectAttributes.addFlashAttribute("success", "Event deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Event not found.");
        }
        return "redirect:/events";
    }

    @GetMapping("/events/{id}/participants")
    public String eventParticipants(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Event event = eventService.findById(id);
        if (event == null) {
            redirectAttributes.addFlashAttribute("error", "Event not found.");
            return "redirect:/events";
        }
        model.addAttribute("event", event);
        model.addAttribute("participants", participantService.findByEventId(id));
        return "events/participants";
    }

    @GetMapping("/participants")
    public String participants(@RequestParam(required = false) String query, Model model) {
        model.addAttribute("participants", participantService.searchParticipants(query));
        model.addAttribute("events", eventService.getEvents());
        model.addAttribute("query", query == null ? "" : query);
        return "participants/list";
    }

    @GetMapping("/participants/new")
    public String newParticipant(@RequestParam(required = false) Integer eventId, Model model) {
        Participant participant = new Participant();
        if (eventId != null) {
            participant.setEventId(eventId);
        }
        model.addAttribute("participant", participant);
        model.addAttribute("events", eventService.getEvents());
        return "participants/form";
    }

    @PostMapping("/participants")
    public String addParticipant(@ModelAttribute Participant participant,
                                 RedirectAttributes redirectAttributes) {
        String error = validateParticipant(participant);
        if (error != null) {
            redirectAttributes.addFlashAttribute("error", error);
            return "redirect:/participants/new";
        }
        if (participantService.findById(participant.getParticipantId()) != null) {
            redirectAttributes.addFlashAttribute("error", "A participant with that ID already exists.");
            return "redirect:/participants/new";
        }
        participantService.addParticipant(participant);
        redirectAttributes.addFlashAttribute("success", "Participant registered successfully.");
        if (participant.getEventId() > 0) {
            return "redirect:/events/" + participant.getEventId() + "/participants";
        }
        return "redirect:/participants";
    }

    @PostMapping("/participants/{id}/delete")
    public String deleteParticipant(@PathVariable int id, RedirectAttributes redirectAttributes) {
        if (participantService.deleteParticipantAndReturn(id)) {
            redirectAttributes.addFlashAttribute("success", "Participant removed successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Participant not found.");
        }
        return "redirect:/participants";
    }

    private String validateEvent(Event event) {
        if (event.getEventId() <= 0) return "Event ID must be a positive number.";
        if (isBlank(event.getEventName())) return "Event name is required.";
        if (isBlank(event.getEventDate())) return "Event date is required.";
        if (isBlank(event.getVenue())) return "Venue is required.";
        return null;
    }

    private String validateParticipant(Participant participant) {
        if (participant.getParticipantId() <= 0) return "Participant ID must be a positive number.";
        if (isBlank(participant.getName())) return "Participant name is required.";
        if (participant.getEventId() > 0 && eventService.findById(participant.getEventId()) == null) {
            return "Please select an existing event.";
        }
        return null;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
