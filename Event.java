import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

public class Event implements Comparable<Event>{
	
	private String title;
	private GregorianCalendar startTime;
	private GregorianCalendar endTime;

	private DateFormat fileDateFormat;
	private DateFormat fileTimeFormat;
	private GregorianCalendar s;
	public Event(String title, GregorianCalendar startTime, GregorianCalendar endTime)
	{
		this.title = title;
		this.startTime = startTime;
		this.endTime = endTime;
		
		fileDateFormat =  new SimpleDateFormat("MM/dd/yy");
		fileTimeFormat = new SimpleDateFormat("HH:mm");
	}

	@Override
	public int compareTo(Event other) 
	{
		if(startTime.before(other.getStartTime()))
			return 1;
		else if(startTime.before(other.getStartTime()))
			return -1;
		else
			return 0;
	}
	
	public boolean isOverLap(Event other)
	{
		return (startTime.getTime().before(other.getEndTime().getTime()) && other.getStartTime().getTime().before(endTime.getTime()));
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public GregorianCalendar getStartTime()
	{
		return startTime;
	}
	
	public GregorianCalendar getEndTime()
	{
		return endTime;
	}
	
	public String toString()
	{
		return title + "\n"+fileTimeFormat.format(startTime.getTime())+"-" + fileTimeFormat.format(endTime.getTime()) +"\n";
	}
}
