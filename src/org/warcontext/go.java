package org.warcontext;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
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

		img = new ImageIcon(bimg);
		label = new JLabel(img);
		p = new JPanel();
		p.add(label);
		p.add(buttonProcess);
		getContentPane().add(p);

		buttonProcess.addActionListener(this);

	}

	private void computescore(Integer x, Integer y, Integer valx, Integer valy) {
		data.setPoints(valx, x);
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
		Integer y[] = new Integer[4];
		y[0] = 262;
		y[1] = 317;
		y[2] = 374;
		y[3] = 427;
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
				String[] valsplit = val.split("/");
				if (!val.contentEquals(sNull)) {
					oldx = x;
					if (val.length() == 4) {
						x += 7;
					}
					g2d.setColor(Color.RED);
					g2d.drawString(valsplit[0], x, y[noline]);
					g2d.setColor(Color.BLACK);
					x += valsplit[0].length() * 13;
					g2d.drawString("/", x, y[noline]);
					g2d.setColor(Color.BLUE);
					x += 8;
					g2d.drawString(valsplit[1], x, y[noline]);
					computescore(noCol, noline, Integer.valueOf(valsplit[1]),
							Integer.valueOf(valsplit[0]));
					

					x = oldx;
				}
				noCol++;
				x += depx;
			}
		}
		// Draw total team points and bar chart
		Integer xpos[] = new Integer[4];
		xpos[0] = 55;
		xpos[1] = 169;
		xpos[2] = 270;
		xpos[3] = 383;
		for (int i = 0; i < data.points.length; i++) {
			g2d.drawString(String.valueOf(data.getPoints(i)), 432, y[i]);
			g2d.fillRect(xpos[i],730-pointtopixel(data.getPoints(i)),  54, pointtopixel(data.getPoints(i)));
			
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