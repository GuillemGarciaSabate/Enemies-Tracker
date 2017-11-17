package model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import parsers.TwitterParser;
import twitter4j.Twitter;


public class InfoDigger extends Request{
	
	private static final String googleAPIkey = "xxxxxxxxxxxxxxx";
	private URL url;
	private BufferedReader reader = null;
	private JSONObject json;
	private ArrayList<String> landTips = new ArrayList<String>();
	
	/**Builds the object and set the parameters for the Request**/
	public InfoDigger(String chain, Integer rad, Float lat, Float longi) throws JSONException, IOException{
		super.setChain(chain);
		super.setLocation(lat, longi);
		super.setRadius(rad);
		super.firstFetch();
		this.urlBuild();			
	}
	
	/**It self connects to the googleAPI. And given some coordinates through HTTPS connection it retrieves fom the connection
	 * the name of the country, region and county from this coordinates
	 * https://developers.google.com/maps/documentation/geocoding/get-api-key**/
	private void urlBuild(){
		try {
			url = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng="+location.getLatitude()+","+location.getLongitude()+"&key="+googleAPIkey);
		} catch (MalformedURLException e) {
			System.out.println("Error while building URL for google API");
			e.printStackTrace();
		}
	}
	
	/**It stores a SringBuffer into a JsonObject and parse the Json to extract the Country, region and county**/
	public ArrayList<String> extractInfo() throws JSONException, IOException {
		
		json = new JSONObject(parseJson());
		landTips.add(json.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(0).getString("long_name"));
		landTips.add(json.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(1).getString("long_name"));
		landTips.add(json.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(2).getString("long_name"));
		landTips.add(json.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(3).getString("long_name"));
		landTips.add(json.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(4).getString("long_name"));
		
		return landTips;
		
	}
	
	/**This method reads from an URL the JSON content and save it into a Buffer, later it loops around this buffer
	 * reading the characters (as an ints) that it stores and saving them in a String Buffer, which is readable as a String,
	 * when it arrives to the char (as an Int) of -1, it stops**/
	private String parseJson() throws IOException{
	
		 try {
		        reader = new BufferedReader(new InputStreamReader(url.openStream()));
		        StringBuffer buffer = new StringBuffer();
		        int read;
		        char[] chars = new char[1024];
		        while ((read = reader.read(chars)) != -1){
		            buffer.append(chars, 0, read);
		        }    
		        return buffer.toString();
		    } finally {
		        if (reader != null)
		            reader.close();
		    }
	}
	
	public Twitter getTW(){
		return super.twitter;
	}
	
	public TwitterParser getTP(){
		return super.tp;
	}


}
