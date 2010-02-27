package org.warcontext;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Config {
	private Document Document;
	private String URIsourceimage;
	private String fontname;
	private int fontsize;

	public Config() {
		// TODO Auto-generated constructor stub
		this.Document = parseXmlFile("config.xml", false);
		readDocument(Document.getDocumentElement());
	}

	public static Document parseXmlFile(String filename, boolean validating) {
		try {
			// Create a builder factory
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setValidating(validating);

			// Create the builder and parse the file
			Document doc = factory.newDocumentBuilder().parse(
					new File(filename));
			return doc;
		} catch (SAXException e) {
			// A parsing error occurred; the xml input is not valid
		} catch (ParserConfigurationException e) {
		} catch (IOException e) {
		}
		return null;
	}

	public void readDocument(Node node) {
		// TODO Auto-generated method stub
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			if (node.getNodeName() == "URIsourceimage") {
				this.URIsourceimage = node.getTextContent();
			}
			if (node.getNodeName() == "font") {
				this.fontname = node.getTextContent();
			}
			if (node.getNodeName() == "size") {
				this.fontsize = Integer.parseInt(node.getTextContent()) ;
			}

		}

		Node nextChild = node.getFirstChild();
		while (nextChild != null) {
			readDocument(nextChild);
			nextChild = nextChild.getNextSibling();
		}

	}
	public String getURIsourceimage() {
		return URIsourceimage;
	}
	public int getFontsize() {
		return fontsize;
	}

	public String getFontname() {
		return fontname;
	}

}
