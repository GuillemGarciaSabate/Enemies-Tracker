package model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import twitter4j.Query;
import twitter4j.QueryResult;
import org.json.JSONException;
import parsers.TwitterParser;
import twitter4j.Status;
import twitter4j.Twitter;
//import twitter4j.TwitterFactory;
import twitter4j.TwitterException;
//import twitter4j.conf.ConfigurationBuilder;


public class SurroundingMedia{
	
	private TwitterParser tp;
	private Twitter twitter;
	//private ConfigurationBuilder cb;
	private InfoDigger indi;
	private ArrayList<String> landTips = new ArrayList<String>();
	private String[] landDivision = {"district", "province", "rayon", "kray", "governorate"};

	
	public SurroundingMedia() throws JSONException, IOException{

	}
	
	/**Extract the official name of the land divisions purging trash words as province or governorate
	 * you can expand this list by adding to the array landDivision which words you want to ommit**/
	private void purgefields(ArrayList<String> dirtyTips){
		
		for(int i=0; i<dirtyTips.size(); i++){
			
			for(int j=0; j<landDivision.length; j++){
				landTips.set(i,dirtyTips.get(i).toLowerCase().replaceAll(landDivision[j], ""));
			}
			landTips.set(i,landTips.get(i).trim());
			System.out.println(landTips.get(i));
		}
	}

	/**Get the search attributes and start a process for searching this atributes first in the given radius (this is done at the Request inside InfoDigger class)
	 * and later on land divisions (this is done in a method of the InfoDigger class) * **/
	public void getMoreInfo(String term, Integer rad, Float lat, Float longi) throws JSONException, IOException{
		
		indi = new InfoDigger(term, rad, lat, longi);
		landTips = indi.extractInfo();
		this.purgefields(landTips);
		
		tp = indi.getTP();
		twitter = indi.getTW();
	
		try {
			System.out.println("A");
			Query query = new Query("("+term+") AND ("
					+ "("+indi.extractInfo().get(0)+") OR "
					+ "("+indi.extractInfo().get(1)+") OR "
					+ "("+indi.extractInfo().get(2)+") OR "
					+ "("+indi.extractInfo().get(3)+") OR "
					+ "("+indi.extractInfo().get(4)+"))");
			query.setCount(100);
			System.out.println(query);
			query.resultType(Query.MIXED);
			QueryResult result = twitter.search(query);
			
			List<Status> tweets = result.getTweets();
            
			for (Status tweet : tweets) {
			    	
				tp.textToFile(tweet);

			}
			tp.closeFile();
		} catch (TwitterException e) {
			System.out.println("Problem at searching global statistics");
			e.printStackTrace();
		}

	}


}

	