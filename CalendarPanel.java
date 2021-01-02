import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CalendarPanel extends JPanel{

	private ArrayList<NumberPanel> panels;
	private ArrayList<JLabel> labels;
	private ArrayList<MyListener> listeners;
	
	private int[] lastDay = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	private CalendarModel model;
	
	public CalendarPanel(CalendarModel model)
	{
		this.model = model;
		
		panels = new ArrayList<NumberPanel>();
		labels = new ArrayList<JLabel>();
		listeners = new ArrayList<MyListener>();
		
		this.setLayout(new GridLayout(7,7));
		
		JPanel sun = new JPanel();
		sun.setBackground(Color.WHITE);
		JLabel sunLabel = new JLabel("S");
		sun.add(sunLabel);
		
		JPanel mon = new JPanel();
		mon.setBackground(Color.WHITE);
		JLabel monLabel = new JLabel("M");
		mon.add(monLabel);

		JPanel tues = new JPanel();
		tues.setBackground(Color.WHITE);
		JLabel tuesLabel = new JLabel("T");
		tues.add(tuesLabel);
		
		JPanel wed = new JPanel();
		wed.setBackground(Color.WHITE);
		JLabel wedLabel = new JLabel("W");
		wed.add(wedLabel);
		
		JPanel thurs = new JPanel();
		thurs.setBackground(Color.WHITE);
		JLabel thursLabel = new JLabel("T");
		thurs.add(thursLabel);
		
		JPanel fri = new JPanel();
		fri.setBackground(Color.WHITE);
		JLabel friLabel = new JLabel("F");
		fri.add(friLabel);

		JPanel sat = new JPanel();
		sat.setBackground(Color.WHITE);
		JLabel satLabel = new JLabel("S");
		sat.add(satLabel);
		
		this.add(sun);
		this.add(mon);
		this.add(tues);
		this.add(wed);
		this.add(thurs);
		this.add(fri);
		this.add(sat);
		

		//add panels and labels to the arraylists.
		//attach all labels to the panel
		for(int i=0; i<42; i++)
		{
			panels.add(new NumberPanel());
			labels.add(new JLabel("-"));
			labels.get(i).setName("-");
			panels.get(i).add(labels.get(i));
			this.add(panels.get(i));
			panels.get(i).setBackground(Color.WHITE);
			panels.get(i).setBorder(new LineBorder(Color.WHITE));
			
		}
		
		this.paintCalendar(model.getCalendar());
		this.setListeners();
		
	}
	
	public void setListeners()
	{
		for(int i=0; i<labels.size(); i++)
		{
			listeners.add(new MyListener(i));
			panels.get(i).addMouseListener(listeners.get(i));
		}
		
	}
	
	public void paintCalendar(GregorianCalendar cal)
	{
		if(cal.isLeapYear(cal.get(cal.YEAR)))
			lastDay[2] = 29;
		
		GregorianCalendar firstDate = new GregorianCalendar(cal.get(cal.YEAR), cal.get(cal.MONTH), 1);
		int firstDayValue = firstDate.get(firstDate.DAY_OF_WEEK)-1;
		int monthValue = firstDate.get(firstDate.MONTH);
		int day = 1;
		
		for(int i=0; i<42; i++)
		{
			if(i < firstDayValue)
			{
				labels.get(i).setText("-");
				labels.get(i).setName("-");
			}
			else if(i >=firstDayValue && i <lastDay[monthValue]+firstDayValue)
			{
				labels.get(i).setText(day+"");
				labels.get(i).setName(day+"");
				day++;
			}
			else
			{
				labels.get(i).setText("-");
				labels.get(i).setName("-");
				day++;
			}
		}
		
		this.setSelected(cal.get(cal.DATE)+firstDayValue-1);
	}
	
	public void moveLeft()
	{
		System.out.println("Move Left");
		if(model.getCalendar().get(model.getCalendar().DATE)==1)
		{
			GregorianCalendar temp;
			if(model.getCalendar().get(model.getCalendar().MONTH)==0)
			{
			temp = new GregorianCalendar(model.getCalendar().get(model.getCalendar().YEAR)-1, 11, 31);
			}
			else
			{
				temp = new GregorianCalendar(model.getCalendar().get(model.getCalendar().YEAR), 
						model.getCalendar().get(model.getCalendar().MONTH)-1, lastDay[model.getCalendar().get(model.getCalendar().MONTH)-1]);
			}
			System.out.println(temp.getTime());
			model.setCalendar(temp);
			paintCalendar(model.getCalendar());
		}
		else
		{
			model.setDate(model.getCalendar().get(model.getCalendar().DATE)-1);
			paintCalendar(model.getCalendar());
		}
		System.out.println(model.getCalendar().getTime());
	}
	
	public void moveRight()
	{
		
		System.out.println("Move Rgiht");
		
		if(model.getCalendar().get(model.getCalendar().DATE)==lastDay[model.getCalendar().get(model.getCalendar().MONTH)])
		{
			GregorianCalendar temp;
			if(model.getCalendar().get(model.getCalendar().MONTH)==11)
			{
				temp = new GregorianCalendar(model.getCalendar().get(Calendar.YEAR)+1, 
						0, 1);
			}
			else 
			{
				temp = new GregorianCalendar(model.getCalendar().get(Calendar.YEAR), 
						model.getCalendar().get(Calendar.MONTH)+1, 1);
			}
			model.setCalendar(temp);
			paintCalendar(model.getCalendar());
		}
		else
		{
			model.setDate(model.getCalendar().get(model.getCalendar().DATE)+1);
			paintCalendar(model.getCalendar());
		}
		System.out.println(model.getCalendar().getTime());
	}
	
	public void setSelected(int index)
	{
		for(int i=0; i<panels.size(); i++)
		{
			if(i==index)
			{
				panels.get(i).setSelected(true);
				panels.get(i).setBorder(new LineBorder(Color.BLACK));
			}
			else
			{
				panels.get(i).setSelected(false);
				panels.get(i).setBorder(new LineBorder(Color.WHITE));
			}
		}
		for(ChangeListener l : model.getChangeListeners())
		{
			l.stateChanged(new ChangeEvent(this));
		}
		
	}
	
	private class MyListener extends MouseAdaptor
	{
		private boolean selected;
		int index;
		private MyListener(int i)
		{
			index = i;
			this.selected = panels.get(i).getSelected();
		}
		
		public void changeSelected(int i)
		{
			if(!selected)
			{
				for(JPanel p : panels)
				{
					if(!selected)
					{
						p.setBorder(new LineBorder(Color.WHITE));
					}
				}
				for(MyListener l : listeners)
				{
					l.setNotSelected();
				}
				panels.get(i).setBorder(new LineBorder(Color.BLACK));
				selected = true;
			}
			else
			{
				panels.get(i).setBorder(new LineBorder(Color.WHITE));
				selected = false;
			}
		}
		

		public void setNotSelected()
		{
			selected = false;
		}
		@Override
		public void mousePressed(MouseEvent e) 
		{
			if(!labels.get(index).getName().equals("-"))
			{
				System.out.println("MousePressed");
				
				this.changeSelected(index);
				model.setDate(Integer.parseInt(labels.get(index).getName()));
				System.out.println("SelectedDay: " + model.getCalendar().getTime());
				
				for(ChangeListener l : model.getChangeListeners())
				{
					l.stateChanged(new ChangeEvent(this));
				}
				
			}
		}
		
	}
	private class MouseAdaptor implements MouseListener
	{
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
}
