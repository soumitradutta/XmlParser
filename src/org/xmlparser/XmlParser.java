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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlParser {
	
    private final static String FILE_LOCATION = "C:\\workspace_2\\XmlParserTest\\XmlParser\\src\\org\\xmlparser\\TestFile.xml";
    private final static String SOURCE_FILE = "C:\\workspace_2\\XmlParserTest\\XmlParser\\src\\org\\xmlparser\\source.xml";

    private void printValuesLikeMap(final Document doc) {
        final Element element = doc.getDocumentElement();
        final NodeList nodeList = element.getChildNodes();
        Node node = null;
        NodeList nList = null;
        Node innerNode = null;
        Node childNode = null;
        System.out.println(nodeList.getLength());

        for (int len = 0; len < nodeList.getLength(); len++) {
            node = nodeList.item(len);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                System.out.println(node.getNodeValue() + "	" + node.getNodeName() + "   " + node.getNodeType());
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
        try {
        	final File xmlFile = new File(FILE_LOCATION);
            final Document doc = XmlParseHelper.newDocumentInstance(xmlFile);
            //printValuesLikeMap(doc);
            //m1(doc);
            //XmlParseHelper.showEmployeeDetailsById(doc, "2");
            //XmlParseHelper.addElement(doc, "2", "salary", "121212");
            //XmlParseHelper.deleteElement(doc, "2", "salary");
            //XmlParseHelper.updateElementValue(doc, "2", "role", "Java Developer");
            //XmlParseHelper.updateAttributeValue(doc, "1", "5");
            XmlParseHelper.mergeXml(doc, new File(SOURCE_FILE));
            
            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();
            final DOMSource source = new DOMSource(doc);
            final StreamResult result = new StreamResult(xmlFile);
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.transform(source, result);

        } catch (final TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (final TransformerException e) {
			e.printStackTrace();
		}
    }

	public static void main(final String args[]) {
        new XmlParser().processXml();
    }

}
