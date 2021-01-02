import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CalendarModel {

	final String[] months = {"January", "Febrary", "March", "April", "May", "June",
							"July", "August", "September", "October", "November", "December"};
	
	final String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	
	public GregorianCalendar cal;
	private File file;
	private ArrayList<Event> events;
	private ArrayList<ChangeListener> listeners;
	
//	private DateTimeFormatter fileDateFormat;
	private DateFormat fileDateFormat;
	private DateFormat fileTimeFormat;
	
	private WestPanel westPanel;
	private EastPanel eastPanel;
	
	public CalendarModel(File file) throws FileNotFoundException, ParseException
	{
		this.cal = new GregorianCalendar();
		events = new ArrayList<Event>();
		listeners = new ArrayList<ChangeListener>();
		this.file = file;
		
		westPanel = new WestPanel(this);
		eastPanel = new EastPanel(this);
		
		fileDateFormat =  new SimpleDateFormat("MM/dd/yy");
		fileTimeFormat = new SimpleDateFormat("HH mm");
		loadEventFile();
	}
	
	public void loadEventFile() throws FileNotFoundException, ParseException
	{
		String title ="";
		Scanner scan = new Scanner(file);
		
		while(scan.hasNextLine())
		{
			GregorianCalendar startTime = new GregorianCalendar();
			GregorianCalendar endTime = new GregorianCalendar();
			String strDate = scan.next();
			Date date = fileDateFormat.parse(strDate);
			startTime.setTime(date);
			endTime.setTime(date);
			
			startTime.set(startTime.HOUR_OF_DAY, Integer.parseInt(scan.next()));
			startTime.set(startTime.MINUTE, Integer.parseInt(scan.next()));
			endTime.set(endTime.HOUR_OF_DAY, Integer.parseInt(scan.next()));
			endTime.set(endTime.MINUTE, Integer.parseInt(scan.next()));
			scan.nextLine();
			
			title = scan.nextLine();
			
			eventAdded(new Event(title, startTime, endTime));
		}
		
		scan.close();
	}
	
	public void saveEventFile() throws IOException
	{
		FileWriter writer = new FileWriter(file, false);
//		DateFormat hourFormat = new SimpleDateFormat("HH");
//		DateFormat minFormat = new SimpleDateFormat("mm");
		for(int i=0; i<events.size(); i++)
		{
			String date = fileDateFormat.format(events.get(i).getStartTime().getTime());
			
			String startHour = events.get(i).getStartTime().get(events.get(i).getStartTime().HOUR_OF_DAY) +"";
			String startMin = events.get(i).getStartTime().get(events.get(i).getStartTime().MINUTE) +"";
			
			String endHour = events.get(i).getEndTime().get(events.get(i).getStartTime().HOUR_OF_DAY) +"";
			String endMin = events.get(i).getEndTime().get(events.get(i).getStartTime().MINUTE) +"";
			
//			String startHour = hourFormat.format(events.get(i).getStartTime().get(events.get(i).getStartTime().HOUR_OF_DAY)) +"";
//			String startMin = minFormat.format(events.get(i).getStartTime().get(events.get(i).getStartTime().MINUTE)) +"";
//			
//			String endHour = hourFormat.format(events.get(i).getEndTime().get(events.get(i).getStartTime().HOUR_OF_DAY)) +"";
//			String endMin = minFormat.format(events.get(i).getEndTime().get(events.get(i).getStartTime().MINUTE)) +"";
			
			String title = events.get(i).getTitle();
			
			writer.write(date + " " + startHour + " " + startMin + " " + endHour + " " + endMin + "\n" + title +"\n");
			
			
		}
		writer.close();
	}
	
	public boolean eventAdded(Event e)
	{
		
//		System.out.println(e.getStartTime().getTime());
//		System.out.println(e.getEndTime().getTime());
		
		boolean overlap = false;
		for(Event event : events)
		{
			if(event.isOverLap(e))
				overlap = true;
		}
		
		if(!overlap)
		{
			events.add(e);
			Collections.sort(events);
			
			for(ChangeListener l : listeners)
			{
				l.stateChanged(new ChangeEvent(this));
			}
			return true;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Filed to add. Time overlaps!");
			return false;
		}
	}
	
	public GregorianCalendar getCalendar()
	{
		return cal;
	}
	
	public void setYear(int year)
	{
		cal.set(year, cal.get(cal.MONTH), cal.get(cal.DATE));
	}
	public void setMonth(int month)
	{
		cal.set(cal.get(cal.YEAR), month, cal.get(cal.DATE));
	}
	
	public void setDate(int date)
	{
		cal.set(cal.get(cal.YEAR), cal.get(cal.MONTH), date);
	}
	
	public String getMonth()
	{
		return months[cal.get(cal.MONTH)];
	}
	
	public String getYear()
	{
		return cal.get(cal.YEAR)+"";
	}
	
	public String getDate()
	{
		return cal.get(cal.DATE)+"";
	}
	
	public ArrayList<Event> getEvents()
	{
		return events;
	}
	
	public ArrayList<ChangeListener> getChangeListeners()
	{
		return listeners;
	}
	
	public WestPanel getWestPanel()
	{
		return westPanel;
	}
	
	public EastPanel getEastPanel()
	{
		return eastPanel;
	}
	public void setCalendar(GregorianCalendar other)
	{
		cal = other;
	}
	
	
}
