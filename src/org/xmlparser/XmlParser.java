package org.xmlparser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlParser {
	
    private final static String FILE = "D:\\RFPT-Upgrade\\XmlParser\\src\\org\\xmlparser\\TestFile.xml";

    private void printValuesLikeMap(final Document doc) {
        Element element = doc.getDocumentElement();
        NodeList nodeList = element.getChildNodes();
        Node node = null;
        NodeList nList = null;
        Node innerNode = null;
        Node childNode = null;
        System.out.println(nodeList.getLength());

        for (int len = 0; len < nodeList.getLength(); len++) {
            node = nodeList.item(len);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                System.out.println(node.getNodeName() + "   " + node.getNodeType());
                System.out.println("----------------------------------------------------");

                nList = node.getChildNodes();
                for (int x = 0; x < nList.getLength(); x++) {
                    innerNode = nList.item(x);
                    if (Node.ELEMENT_NODE == innerNode.getNodeType()) {
                        if (innerNode.hasChildNodes()) {
                            childNode = innerNode.getFirstChild();
                        }
                        System.out.println(innerNode.getNodeName() + " : " + ((childNode != null) ? childNode.getTextContent() : null));
                    }
                }
                System.out.println("==========================================================\n\n");
            }
        }
    }

    private void m1(final Document doc) {
        NodeList nodeList = doc.getChildNodes();
        System.out.println(nodeList.getLength());
        for (int len = 0; len < nodeList.getLength(); len++) {
            Node node = nodeList.item(len);
            System.out.println("   " + node.getNodeValue() + "   " + node.getTextContent());
        }
    }

    private void processXml() {
        DocumentBuilderFactory docBuilderFactory = null;
        DocumentBuilder docBuilder = null;
        Document doc = null;

        try {
            docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docBuilderFactory.newDocumentBuilder();
            doc = docBuilder.parse(new File(FILE));
            // printValuesLikeMap(doc);
            m1(doc);

        } catch (final ParserConfigurationException e) {
            e.printStackTrace();
        } catch (final SAXException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

	public static void main(final String args[]) {
        new XmlParser().processXml();
    }

}
