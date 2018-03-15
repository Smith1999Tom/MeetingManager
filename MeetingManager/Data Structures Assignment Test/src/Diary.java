import java.util.LinkedList;
import java.util.Stack;
import java.nio.charset.*;
import java.nio.file.*;
import java.io.*;
import java.util.Iterator;

public class Diary {

	LinkedList<Meeting> meetings;
	Stack<Meeting> undoStack;
	Charset cs = StandardCharsets.UTF_8;
	Iterator<Meeting> iterator;
	
	Diary()
	{
		meetings = new LinkedList<Meeting>();
	}
	
	boolean addEntry(Meeting addMeeting)
	{
		return meetings.add(addMeeting);
	}
	
	Diary loadDiary(String pathString)
	{
		Path diaryFile = Paths.get(pathString);
		
		try(BufferedReader br = Files.newBufferedReader(diaryFile, cs);)
		{
			Diary bufferDiary = new Diary();
			while(br.ready())
			{
				String l1 = br.readLine();
				String l2 = br.readLine();
				String l3 = br.readLine();
				String l4 = br.readLine();
				bufferDiary.addEntry(new Meeting(l1, l2, l3, l4));
			}
			
			return bufferDiary;
		}
		catch(IOException e)
		{
			System.out.println("IOEXCEPTION " + e);
			return null;
		}
		catch(NullPointerException e)
		{
			System.out.println("NULLPOINTEREXCEPTION " + e);
			return null;
		}
	}
	
	boolean saveDiary(String pathString)
	{
		Path diaryFile = Paths.get(pathString);
		
		try(BufferedWriter bw = Files.newBufferedWriter(diaryFile, cs);)
		{
			iterator = meetings.iterator();
			while(iterator.hasNext())
			{
				Meeting saveMeeting = iterator.next();
				String l1 = saveMeeting.getDateOfMeeting().toString();
				String l2 = saveMeeting.getStartTime().toString();
				String l3 = saveMeeting.getEndTime().toString();
				String l4 = saveMeeting.getDescription();
				bw.write(l1);
				bw.newLine();
				bw.write(l2);
				bw.newLine();
				bw.write(l3);
				bw.newLine();
				bw.write(l4);
				bw.newLine();
			}
			return true;
		}
		catch(IOException e)
		{
			System.out.println("IOEXCEPTION " + e);
			return false;
		}
		catch(NullPointerException e)
		{
			System.out.println("NULLPOINTEREXCEPTION " + e);
			return false;
		}
	}
	
	public void print()
	{
		iterator = meetings.iterator();
		while(iterator.hasNext())
		{
			Meeting entryToPrint = iterator.next();
			System.out.println(entryToPrint.getDateOfMeeting());
			System.out.println(entryToPrint.getStartTime());
			System.out.println(entryToPrint.getEndTime());
			System.out.println(entryToPrint.getDescription());
		}
		System.out.println();
	}
	
	
}
