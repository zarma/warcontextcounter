package org.warcontext;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.*;

public class go extends JFrame implements ActionListener {
	String s;
	ImageIcon img;
	Image image;
	JPanel p;
	JLabel label;
	JLabel text;
	JButton buttonProcess = new JButton("Process");
	Config config = new Config();
	BufferedImage bimg;
	int imageWidth;
	int imageheight;
	Graphics2D g2d;
	Data data;
	int colorb, coloropfor, colorblue;
	Font font2 = new Font("Arial", Font.PLAIN, 12);

	public int getWidth() {
		return imageWidth;
	}

	public int getHeight() {
		return imageheight;
	}

	public go() {

		Font f = new Font("Arial", Font.PLAIN, 24);
		text = new JLabel("Hi");
		text.setFont(f);

		MediaTracker mt = new MediaTracker(this);
		image = Toolkit.getDefaultToolkit().createImage(
				config.getURIsourceimage());
		mt.addImage(image, 0);
		try {
			mt.waitForID(0);
		} catch (InterruptedException ie) {
		}
		imageWidth = image.getWidth(null);
		imageheight = image.getHeight(null);
		this.bimg = new BufferedImage(imageWidth, imageheight,
				BufferedImage.TYPE_INT_ARGB);
		g2d = bimg.createGraphics();
		g2d.drawImage(image, 0, 0, this);

		g2d.setFont(f);
		// g2d.drawString(s, 165, 265);
		colorb = bimg.getRGB(176, 246); 
		coloropfor = bimg.getRGB(382, 246);
		colorblue = bimg.getRGB(198, 120);

		img = new ImageIcon(bimg);
		label = new JLabel(img);
		p = new JPanel();
		p.add(label);
		p.add(buttonProcess);
		getContentPane().add(p);

		buttonProcess.addActionListener(this);

	}

	private void computescore( Integer y, Integer valy) {
		
		data.setPoints(valy, y);
	}

	private Integer pointtopixel(Integer point) {
		float pixel = (float) (point * 0.3);
		return Math.round(pixel) ;
	}

	private void drawdata() {

		data = new Data("data.csv");
		// Compute score

	
		Integer noline;
		Integer noCol = 0;
		Integer x = 0;
		Integer depx = 0;
		Integer oldx = 0;
		Integer y[] = new Integer[3];
		y[0] = 262;
		y[1] = 317;
		y[2] = 374;
		
		//
		// Tableau
		//
		for (noline = 0; noline < data.getLines().length; noline++) {

			StringTokenizer st = new StringTokenizer(data.getLines()[noline],
					";");
			String val = "";

			String sNull = "null";
			x = 159;
			depx = 67;
			noCol = 0;
			while (st.hasMoreElements()) {

				val = st.nextToken();
				if (!val.contentEquals(sNull)) {
					oldx = x;
					if (val.length() == 3) {
						x += 7;
					}
					g2d.setColor(Color.RED);
					g2d.drawString(val, x, y[noline]);
					computescore( noline, Integer.valueOf(val));
					

					x = oldx;
				}
				noCol++;
				x += depx;
			}
		}
		// Draw total team points and bar chart
		Integer xpos[] = new Integer[3];
		Integer maxpoint = 200;
		
		xpos[0] = 72;
		xpos[1] = 186;
		xpos[2] = 300;
		
		int colorb = bimg.getRGB(176, 246); 
		int coloropfor = bimg.getRGB(382, 246);
		int colorblue = bimg.getRGB(198, 120);
		for (int i = 0; i < data.points.length; i++) {
			g2d.setColor(new Color(colorb));
			g2d.fillRect(xpos[i],658-maxpoint,  54, maxpoint);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(xpos[i]-1,658-maxpoint-1,  55, maxpoint+1);
			
			g2d.setColor(new Color(colorblue));
			g2d.drawString(String.valueOf(data.getPoints(i)), 362, y[i]);
			g2d.setColor(new Color(coloropfor));
			g2d.fillRect(xpos[i],658-maxpoint,  54, data.getPoints(i));
			
			
			
			
		}
		
		g2d.setColor(Color.BLACK);
		g2d.setFont(font2);
		for (Integer i = 0; i < data.points.length; i++) {		
			g2d.drawLine(xpos[i], 658-(maxpoint/2), xpos[i]+54, 658-(maxpoint/2));
			Integer temp = i+1;
			g2d.drawString("F".concat(temp.toString()), xpos[i] + 18, 658-maxpoint-6);
			g2d.drawString("200", xpos[i] - 30, 658-maxpoint+6);
			g2d.drawString("100", xpos[i] - 30, 658-(maxpoint/2)+6);
						
		}
		
	


		

		p.repaint();

		File outputFile = new File("image.png");
		try {
			ImageIO.write(bimg, "PNG", outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();

		if (source == buttonProcess) {
			drawdata();
		}
	}

	public static void main(String args[]) {
		// Lire les paramètres

		// Lire les données

		// Lancer la modification de l'image
		go tt = new go();
		tt.setDefaultCloseOperation(EXIT_ON_CLOSE);
		tt.setSize(750, 900);
		tt.setVisible(true);
	}
}