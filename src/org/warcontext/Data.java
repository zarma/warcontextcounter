package org.warcontext;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Data {

	BufferedReader br;
	FileReader fr;
	private int size = 4;
	String[] lines = new String[size];
	Integer[] points = new Integer[size];

	public Data(String datafilename) {
		try {
			this.fr = new FileReader(datafilename);
			this.br = new BufferedReader(fr);
			String strline = "";
			int ind = 0;
			try {
				while ((strline = br.readLine()) != null) {
					lines[ind] = strline;
					points[ind]=0;
					ind++;
					

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String[] getLines() {
		return lines;
	}

	public Integer getPoints(Integer teamnumber) {
		return points[teamnumber];
	}

	public void setPoints(Integer point,Integer teamnumber) {
		this.points[teamnumber] += point;
	}

}
