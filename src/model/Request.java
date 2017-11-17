package model;
import java.util.List;

import parsers.TwitterParser;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.Query.Unit;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class Request {
	

	private static final String TWITTER_CONSUMER_KEY = "xxxxxxxxxxxxxxx";
	private static final String TWITTER_SECRET_KEY = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static final String TWITTER_ACCESS_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static final String TWITTER_ACCESS_TOKEN_SECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private double radius = 10;
	private Unit unit;
	private ConfigurationBuilder cb;
	private long index=0;
	private String day;
	private long[] posid = new long[2];
	private boolean bin= true;
	protected GeoLocation location = new GeoLocation(33.510748, 36.293192);
	protected Twitter twitter;
	protected TwitterParser tp;
	protected String chain;

	
	public Request(){
		
		//cb objeto constructor del framework twitter4j usado para establecer una conexion con twiter
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		    .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
		    .setOAuthConsumerSecret(TWITTER_SECRET_KEY)
		    .setOAuthAccessToken(TWITTER_ACCESS_TOKEN)
		    .setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);
		
		//tf es un objeto constructor de una clase padre de twitter, es capaz de instanciar objetos de clases que hereden de ella
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
		tp = new TwitterParser();
			
	}
	
	/**Launching first phase of the fetch to get the most recent results and the index from the last if we need it
	 * if it reach all the results from the last two days (e.g X->today; day=X-2) it will stop to analyze data
	 * if it reach the maximum available by the API(100 results) without getting at day=today-2 it will launch a 
	 * recursive fetch until reaching X-2 at midnight**/
	public void firstFetch(){
		
		try {
			
			Query query = new Query(chain + " +exclude:retweets");
			query.setCount(100);
			this.targetLocation(query);
		    query.setSince(tp.retriveTime(2));
		    QueryResult result = twitter.search(query);	    				
		    
		    List<Status> tweets = result.getTweets();
		            
			  for (Status tweet : tweets) {
			    	
				tp.textToFile(tweet);
				day = tweet.getCreatedAt().toString().substring(8, 10);
			    index=tweet.getId();
			  }
			  System.out.println(day);
			  System.out.println(Integer.parseInt(day));
			  System.out.println(tp.retriveTime(2).substring(8,10));
			  if(Integer.parseInt(day) == Integer.parseInt(tp.retriveTime(2).substring(8,10)))
			  {
				  
				  //to implement the analysis of the results
			  }else{
				  this.recursiveFetch(index, chain);
			  }
  
		} catch (TwitterException te) {
			System.out.println("Failed to search tweets: " + te.getMessage());
			te.printStackTrace();
			System.exit(-1);
		}
		//tp.closeFile(); 
	    //System.exit(0);
	}

	/**Launching the recursive fetch to get the most recent results after the last index from the first fetch
	 * The API will be queryed for the 100 previous tweets than the index paramether, if by the way those reach
	 * day=today-2 the loop will stop, otherwise, the last obtained index will be stored and the recursiveFetch
	 * launched again and again until it reach day=today-2 or it doesn't find new tweets, then the analysis will start**/
	private void recursiveFetch(long index, String chain){
		
		try {
			
			Query query = new Query(chain + " +exclude:retweets");
			query.setCount(100);
			query.setMaxId(index); /**Return tweets with an id less than**/
			this.targetLocation(query);
		    query.setSince(tp.retriveTime(2));
		    QueryResult result = twitter.search(query);
		    List<Status> tweets = result.getTweets();
		    
		    bin=!bin;
		    if(bin==true){
		    	posid[0]=index;
		    }else{
		    	posid[1]=index;
		    }
		    
			  for (Status tweet : tweets) {
			    	
				tp.textToFile(tweet);
				index=tweet.getId();
				day = tweet.getCreatedAt().toString().substring(8, 10);
				
				if(Integer.parseInt(day) == Integer.parseInt(tp.retriveTime(2).substring(8,10)))
				{
					break;
				}
				
			  }
			  
			  if(Integer.parseInt(day) == Integer.parseInt(tp.retriveTime(2).substring(8,10)) || posid[0] == posid[1])
			  {
				  //tp.closeFile();
				  return;
				  //to implement the analysis of the results
			  }else{
				  this.recursiveFetch(index, chain);
			  }
  
		} catch (TwitterException te) {
			System.out.println("Failed to search tweets: " + te.getMessage());
			te.printStackTrace();		   
			System.exit(-1);
		}
		        
	    //System.exit(0);
	}
	
	/**We set the radius within we are going to search the new events**/
	public Query targetLocation(Query query){
		unit = Query.KILOMETERS;
		query.setGeoCode(location, radius, unit);
		return query;
	}
	
	public void setRadius(Integer rad){
		radius = rad;
	}
	
	public void setChain(String c){
		System.out.println("Request: "+c);
		chain = c;
	}
	
	public void setLocation(Float lati, Float longi){
		location = new GeoLocation(lati, longi);
	}
	

}
