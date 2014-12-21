package com.mycompany.myapp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mycompany.myapp.domain.CalendarUser;
import com.mycompany.myapp.domain.Event;
import com.mycompany.myapp.domain.EventAttendee;

public interface CalendarService {
	/* CalendarUser */
    public CalendarUser getUser(int id);

    public CalendarUser getUserByEmail(String email);

    public List<CalendarUser> getUsersByEmail(String partialEmail);
    
    public CalendarUser findUserByUserId(String userId);

    public int createUser(CalendarUser user);
    
    public void deleteAllUsers();
    
    public void updateCalendarUser(CalendarUser calendarUser);
    
 
    
    /* Event */
    public Event getEvent(int eventId);

    public List<Event> getEventForOwner(int ownerUserId);

    public List<Event> getAllEvents();

    public int createEvent(Event event);
    
    public void deleteAllEvents();
    
    public void deleteEvent(int eventId);
    
    public void udpateEvent(Event event);
    
    /* EventAttendee */
    public List<EventAttendee> getEventAttendeeByEventId(int eventId);
    
    public List<EventAttendee> getEventAttendeeByAttendeeId(int attendeeId);

    public int createEventAttendee(EventAttendee eventAttendee);

    public void deleteEventAttendee(int id);
    
    public void deleteAllEventAttendees();
    
    public List<CalendarUser> getEventUserByEventId(int eventId);

    public void deleteEventAttendeeByEventId(int eventId);
    
	/* upgradeEventLevels */
	public void upgradeEventLevels() throws Exception;

	public boolean canUpgradeEventLevel(Event event);
	
	public void upgradeEventLevel(Event event);
}