package com.mycompany.myapp.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.myapp.domain.CalendarUser;
import com.mycompany.myapp.domain.Event;
import com.mycompany.myapp.domain.EventAttendee;
import com.mycompany.myapp.domain.EventForm;
import com.mycompany.myapp.domain.EventInfo;
import com.mycompany.myapp.domain.EventLevel;
import com.mycompany.myapp.domain.EventsForm;
import com.mycompany.myapp.domain.UserInfo;
import com.mycompany.myapp.service.CalendarService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/events")
public class EventController {
	@Autowired
	private CalendarService calendarService;	
	
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String createEvent(@ModelAttribute("userInfo") UserInfo userInfo, Model model) {
		EventForm eventForm = new EventForm();

		model.addAttribute("eventForm", eventForm);
		model.addAttribute("message", "event페이지 입니다.");
		return "/events/createEvent";
	}
	
	@RequestMapping(value = "/createSuccess", method = RequestMethod.POST)
	public String processEvent(@ModelAttribute("userInfo") UserInfo userInfo,
			@ModelAttribute("eventForm") EventForm eventForm, Model model) {
		Event newevent = new Event();
		CalendarUser owner = calendarService.findUserByUserId(eventForm.getOwner());
		Calendar cal = Calendar.getInstance();

		if (eventForm.getWhen().equals("")) {
			eventForm.setWhen(cal.getTime().toString());
		} else {
			String split[] = eventForm.getWhen().split(" "); // date, time, am
																// or pm
			String split2[] = split[0].split("/"); // month, day, year
			String split3[] = split[1].split(":"); // hour, minute

			String year = split2[2];
			String month = split2[0];
			String date = split2[1];
			String hour = split3[0];
			String minute = split3[1];
			String noon = split[2];

			if (noon.equals("PM"))
				if (hour.equals("12")) {
				} else
					hour = "" + (Integer.parseInt(hour) + 12);

			cal.set(Integer.parseInt(year) - 1, Integer.parseInt(month) - 1,
					Integer.parseInt(date), Integer.parseInt(hour),
					Integer.parseInt(minute));
			cal.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		}
		
		newevent.setWhen(cal);
		newevent.setSummary(eventForm.getSummary());
		newevent.setDescription(eventForm.getDescription());
		newevent.setOwner(owner);
		newevent.setNumLikes(0);
		newevent.setEventLevel(EventLevel.NORMAL);
		
		return "/events/createSuccess";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView viewEvent(
	 ModelAndView mav) {
		EventsForm eventsForm = new EventsForm();
		List<Event> allEvents = new ArrayList<Event>();
		List<EventInfo> eventInfoes = new ArrayList<EventInfo>();

		allEvents = calendarService.getAllEvents();
		for (int i = 0; i < allEvents.size(); i++) {
			Event e = allEvents.get(i);
			if (!eventInfoes.contains(e)) {
				EventInfo ei = new EventInfo(e);
				ei.setAttendees(calendarService.getEventUserByEventId(e.getId()));
				eventInfoes.add(ei);
			}
		}
		mav.addObject("eventForm", eventsForm);
		mav.addObject("eventInfoes", eventInfoes);
		mav.setViewName("/events/allevents");
		return mav;
	}
	@RequestMapping(value = "/my", method = RequestMethod.POST)
	public ModelAndView myEvent(@ModelAttribute("userInfo") UserInfo userInfo,
			ModelAndView mav) {

		EventForm eventForm = new EventForm();

		CalendarUser currentUser = calendarService.findUserByUserId(userInfo
				.getName());

		List<Event> events = calendarService.getEventForOwner(currentUser
				.getId());

		List<EventAttendee> eas = calendarService
				.getEventAttendeeByAttendeeId(currentUser.getId());

		List<Event> joinEvents = new ArrayList<Event>();

		for (int i = 0; i < eas.size(); i++) {
			joinEvents.add(eas.get(i).getEvent());
		}

		mav.addObject("eventForm", eventForm);
		mav.addObject("events", events);
		mav.addObject("joinEvents", joinEvents);
		mav.setViewName("/events/my");
		return mav;
	}
	
	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public ModelAndView viewEvent(
			@ModelAttribute("userInfo") UserInfo userInfo, ModelAndView mav,
			@ModelAttribute("eventsForm") EventsForm eventsForm) {

		List<Integer> eventList = new ArrayList<Integer>();
		List<String> nameList = new ArrayList<String>();

		String val = eventsForm.getEventList();

		String temp[] = val.split(":");

		for (int i = 1; i < temp.length; i++) {
			eventList.add(Integer.parseInt(temp[i]));
		}

		for (int i = 0; i < eventList.size(); i++) {
			nameList.add(calendarService.getEvent(eventList.get(i))
					.getSummary());
		}

		if (eventsForm.getFlag().equals("join")) {
			CalendarUser currentUser = calendarService
					.findUserByUserId(eventsForm.getUserId());

			for (int i = 0; i < eventList.size(); i++) {
				EventAttendee addEventAttendee = new EventAttendee();
				addEventAttendee.setAttendee(currentUser);
				addEventAttendee.setEvent(calendarService.getEvent(eventList
						.get(i)));

				calendarService.createEventAttendee(addEventAttendee);
			}

			mav.setViewName("/events/joinResult");
			mav.addObject("msg", "event가 성공적으로 등록되었습니다.");
			mav.addObject("nameList", nameList);
		} else if (eventsForm.getFlag().equals("delete")) {
			for (int i = 0; i < eventList.size(); i++) {
				calendarService.deleteEventAttendeeByEventId(eventList.get(i));
				calendarService.deleteEvent(eventList.get(i));
			}

			mav.setViewName("/events/deleteResult");
			mav.addObject("msg", "event가 성공적으로 삭제되었습니다.");
			mav.addObject("nameList", nameList);
		}

		return mav;
	}
}
