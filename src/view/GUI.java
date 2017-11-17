package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.MapController;

import java.io.IOException;
import java.net.URL;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;


public class GUI extends JFrame{

private Integer zoom = 1;
private Float longi = 0.0f;
private Float lati = 0.0f;
private JPanel mainBox;
private JPanel jpConditions;
private JLabel jlTrack;
private JTextArea jtaTrack;
private JLabel jlRadius;
private JTextArea jtaRadius;
private JButton search;
private BufferedImage image = null;
private ImageIcon im;
private JLabel jlimage;
private JPanel jpimage;
private JPanel jptools;
private JButton jbup;
private JButton jbdown;
private JButton jbleft;
private JButton jbright;
private JButton jbin;
private JButton jbout;

	/**This is the view of the tool**/
	public GUI(){
		
		/**First row elements**/
		jpConditions = new JPanel(new FlowLayout(FlowLayout.LEFT) );
		jpConditions.setBorder(BorderFactory.createTitledBorder(""));
		
		jlTrack = new JLabel("Key word to Track: ");
		jlTrack.setFont (jlTrack.getFont ().deriveFont (16.0f));
		
		jtaTrack = new JTextArea(1,10);
		jtaRadius = new JTextArea(1,5);
		
		jlRadius = new JLabel("Radius for your your search: ");
		jlRadius.setFont (jlTrack.getFont ().deriveFont (16.0f));
		
		search = new JButton("Search");
				
		jpConditions.add(jlTrack);
		jpConditions.add(jtaTrack);
		jpConditions.add(Box.createHorizontalStrut(10));
		jpConditions.add(jlRadius);
		jpConditions.add(jtaRadius);
		jpConditions.add(Box.createHorizontalStrut(10));
		jpConditions.add(search);
		
		/**Map**/
		this.prepareMap(0,40.714728f,-73.998672f);
		jlimage = new JLabel(im, JLabel.LEFT);
		jpimage = new JPanel(new BorderLayout());
		
		/**Tools**/
		jptools = new JPanel(new GridLayout(3,5));
		jbup = new JButton("up");
		jbdown = new JButton("down");
		jbleft = new JButton("left");
		jbright = new JButton("right");
		jbin = new JButton("IN");
		jbout = new JButton("OUT");
		
		jptools.add(new JPanel());
		jptools.add(new JPanel());
		jptools.add(jbup);
		jptools.add(new JPanel());
		jptools.add(new JPanel());
		jptools.add(jbleft);
		jptools.add(jbin);
		jptools.add(new JPanel());
		jptools.add(jbout);
		jptools.add(jbright);
		jptools.add(new JPanel());
		jptools.add(new JPanel());
		jptools.add(jbdown);
		jptools.add(new JPanel());
		jptools.add(new JPanel());

		jpimage.add(jptools, BorderLayout.EAST);
		jpimage.add(jlimage, BorderLayout.WEST);

		mainBox = new JPanel(new BorderLayout());
		mainBox.add(jpConditions, BorderLayout.NORTH);
		mainBox.add(jpimage, BorderLayout.SOUTH);
		mainBox.setBorder(BorderFactory.createTitledBorder("The Tracker"));

		this.getContentPane().add(mainBox);
		this.setSize(980, 500);
		this.setLocationRelativeTo(null);
	
	}
	
	/**Adjust the size of the map, load a new image and displays it on the GUI**/
	public void reshaper(Integer z, Float a, Float b){
		
		this.prepareMap(z,a,b);
		jlimage.setIcon(im);
		jpimage.add(jlimage, BorderLayout.WEST);
		mainBox.add(jpimage);

	}
	
	/**Adjust the size of the map**/
	public void prepareMap(Integer z, Float a, Float b){
		
		zoom = zoom + z;
		lati = lati +a;
		if(Math.abs(lati)>90){
			lati = 0.0f;
		}
		longi = longi + b;
		if(Math.abs(longi)>180){
			longi = 0.0f;
		}
		
		try {
		    URL url = new URL("https://maps.googleapis.com/maps/api/staticmap?center="+lati+","+longi+"&zoom="+zoom+"&size=800x400&markers=color:blue%7Clabel:S%7C11211%7C11206%7C11222&key=xxxxxxxxxxxxxxx");
		    image = ImageIO.read(url);
		    im = new ImageIcon(image);
		} catch (IOException e) {
			e.printStackTrace();
            System.exit(1);
		}
		
	}
	
	public String getTrack(){
		return jtaTrack.getText();
	}
	
	public Integer getRadius(){
		return Integer.parseInt(jtaRadius.getText());
	}
	
	public float getLat(){
		return lati;
	}
	
	public float getLong(){
		return longi;
	}
	
	public void setLong(Float b){
		longi = b;
	}
	
	public void setLati(Float a){
		lati = a;
	}
	
	public int getzoom(){
		return zoom;
	}

	public void setController(MapController mc){
		jbup.addActionListener(mc);
		jbdown.addActionListener(mc);
		jbleft.addActionListener(mc);
		jbright.addActionListener(mc);
		jbin.addActionListener(mc);
		jbout.addActionListener(mc);
		search.addActionListener(mc);
	}
}
