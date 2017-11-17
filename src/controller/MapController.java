package controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;

import org.json.JSONException;

import model.SurroundingMedia;
import view.GUI;


public class MapController implements ActionListener{
	
	private GUI gui;
	private Integer zoom;
	private Float longi=0.0f;
	private Float lati = 0.0f;
	private SurroundingMedia sm;
	
	public MapController(GUI gui, SurroundingMedia r){
		this.gui = gui;
		this.sm = r;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() instanceof JButton) {
			
			String buton = ((JButton) e.getSource()).getText();
			
			if (buton.equals("up")) {
				gui.reshaper(0, this.selectLat(gui.getzoom()), 0.0f);
			}
			if (buton.equals("down")) {
				gui.reshaper(0, -this.selectLat(gui.getzoom()), 0.0f);
			}
			if (buton.equals("right")) {
				gui.reshaper(0, 0.0f, this.selectLong(gui.getzoom()));
			}
			if (buton.equals("left")) {
				gui.reshaper(0, 0.0f, -this.selectLong(gui.getzoom()));
			}
			if (buton.equals("IN")) {
				gui.reshaper(1,0.0f,0.0f);
			}
			if (buton.equals("OUT")) {
				gui.reshaper(-1,0.0f,0.0f);
			}
			if (buton.equals("Search")) {
				try {
					
					sm.getMoreInfo(gui.getTrack(),gui.getRadius(),gui.getLat(),gui.getLong());
				} catch (JSONException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		
		}
		
	}

	/**Select the scale for advancing on longitude**/
	public float selectLong(Integer Z){
		
		switch(Z){
		/**Earth level**/
		case 1: return 10.0f;
		case 2: return 10.0f;
		case 3: return 8.0f;
		/**Continent**/
		case 4: return 5.5f;
		case 5: return 5.0f;
		/**Country**/
		case 6: return 2.5f;
		case 7: return 2.0f;
		case 8: return 1.5f;
		case 9: return 0.8f;
		/**Province**/
		case 10: return 0.5f;
		case 11: return 0.2f;
		case 12: return 0.005f;
		/**City**/
		case 13: return 0.0045f;
		case 14: return 0.0040f;
		case 15: return 0.0035f;
		case 16: return 0.0030f;
		/**Street level**/
		case 17: return 0.0015f;
		case 18: return 0.0010f;
		case 19: return 0.0001f;
		case 20: return 0.0005f;
		default: return 5.0f;
		}
	}	
	/**Select the scale for advancing on latitude**/	
	public float selectLat(Integer Z){
			
			switch(Z){
			/**Earth level**/
			case 1: return 20.0f;
			case 2: return 20.0f;
			case 3: return 8.0f;
			/**Continent**/
			case 4: return 5.5f;
			case 5: return 5.0f;
			/**Country**/
			case 6: return 2.5f;
			case 7: return 2.0f;
			case 8: return 1.5f;
			case 9: return 0.8f;
			/**Province**/
			case 10: return 0.5f;
			case 11: return 0.2f;
			case 12: return 0.005f;
			/**City**/
			case 13: return 0.0045f;
			case 14: return 0.0040f;
			case 15: return 0.0035f;
			case 16: return 0.0030f;
			/**Street level**/
			case 17: return 0.0015f;
			case 18: return 0.0010f;
			case 19: return 0.0001f;
			case 20: return 0.0005f;
			default: return 5.0f; 
			}
		
	}
}
