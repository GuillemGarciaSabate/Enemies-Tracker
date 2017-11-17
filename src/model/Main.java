package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.SwingUtilities;

import org.json.JSONException;

import controller.MapController;
import view.GUI;

public class Main {
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				/**Here the twitter queries are launched, first the nested on a place, 
				 * On second place an analysis of the trends it's performed**/
				try {
					/**create model**/
					SurroundingMedia req = new SurroundingMedia();
					/**Creating view**/
					GUI gui = new GUI();
					/**Creating controller and assigning a view**/
					MapController mc = new MapController(gui,req);
					/**set the relation c->v**/
					gui.setController(mc);
					gui.setVisible(true);
					
			
				} catch (JSONException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
				
			
				
				
				
				
				
			}
			
		});
		
	}
}