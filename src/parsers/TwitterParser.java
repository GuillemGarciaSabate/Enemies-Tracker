package parsers;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import twitter4j.Status;
/*import twitter4j.Query;
import twitter4j.QueryResult;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;*/

public class TwitterParser {
	
	public PrintWriter writer;
	public String timeLog;
	public String infParse;
    
	public TwitterParser(){
		
		try{
			timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		    writer = new PrintWriter(timeLog+"twitter_Output.txt", "UTF-8");
		} catch (IOException e) {
			System.out.println("Error creating the file");	  
		}
	    
    }
	
	public void textToFile(Status tweet){
        writer.println(this.parseData(tweet));	
	}
	
	/**Unpacks the data from the tweet array and safe it into a file**/
	public String parseData(Status tweet){
		
		infParse = ("NAME | "+ tweet.getUser().getName() + " | ScreenNAME | " + tweet.getUser().getScreenName() + " | Text |  " + tweet.getText() 
				+ " | Location |  " + tweet.getUser().getLocation()+ " | TimeZone |  " +  tweet.getUser().getTimeZone()  
				+ " | Created at |  " + tweet.getCreatedAt() + " | URL |  " + tweet.getUser().getURL());
		return infParse;
	}
	
	public void closeFile(){
		writer.close();	
	}
	
	
	/**We take the time of today and we substract i days**/
	public String retriveTime(int i){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -i);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String now = format1.format(cal.getTime());
		return now;
		
		
	}

}
