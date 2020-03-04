package org.xmlparser;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlParser {
	
	private final static String FILE = "C:\\workspace_2\\XmlParserTest\\src\\org\\xmlparser\\TestFile.xml"; 
	
	public static void main(final String args[]) {
		DocumentBuilderFactory docBuilderFactory = null;
		DocumentBuilder docBuilder = null;
		Document doc = null;
		
		try {
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(new File(FILE));
			
			//Node node = doc.getFirstChild();
			NodeList nodeList = doc.getChildNodes();
			System.out.println(nodeList.getLength());
			for (int len = 0; len < nodeList.getLength(); len ++) {
				Node node = nodeList.item(len);
				System.out.println(node.getNodeName() + "   " + node.getNodeValue() + "   " + node.getTextContent());
			}
			
		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
		} catch (final SAXException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}
