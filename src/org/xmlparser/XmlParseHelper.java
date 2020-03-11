package org.xmlparser;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

//https://www.journaldev.com/901/modify-xml-file-in-java-dom-parser
//https://www.w3schools.com/xml/xml_attributes.asp
public final class XmlParseHelper {
	
	public static final void showEmployeeDetailsById(final Document doc, final String empId) {
		final NodeList employees = (NodeList) doc.getElementsByTagName("Employee");
		Element employee = null;
		NodeList empDetailsList = null;
		Node empDetailNode = null;
		Node empDetailNodeValue = null;
		
		if (employees != null) {
			for (int len = 0; len < employees.getLength(); len++) {
				employee = (Element) employees.item(len);
				if (employee != null && empId.equals(employee.getAttribute("id"))) {
					empDetailsList = employee.getChildNodes();
					for (int index = 0; index < empDetailsList.getLength(); index++) {
						empDetailNode = empDetailsList.item(index);
						if (empDetailNode != null && empDetailNode.getNodeType() == Node.ELEMENT_NODE) {
							if (empDetailNode.hasChildNodes()) {
								empDetailNodeValue = empDetailNode.getFirstChild();
							} else {
								empDetailNodeValue = null;
							}
							System.out.println(empDetailNode.getNodeName() + "  :  " + (empDetailNodeValue != null ? empDetailNodeValue.getTextContent() : null));
						}
					}
				}
			}
		}
	}
	
	public static final void addElement(final Document doc, final String empId, final String newElementName, final String newElementValue) {
		final NodeList employees = (NodeList) doc.getElementsByTagName("Employee");
		Element employee = null;
		if (null != employees) {
			for (int len = 0; len < employees.getLength(); len ++) {
				employee = (Element) employees.item(len);
				if (null != employee && empId.equals(employee.getAttribute("id"))) {
					final Element newElement = doc.createElement(newElementName);
					final Text textNode = doc.createTextNode(newElementValue);
					newElement.appendChild(textNode);
					employee.appendChild(newElement);
				}
			}
		}
	}
	
	public static final void deleteElement(final Document doc, final String empId, final String elementToBeRemoved) {
		final NodeList employees = (NodeList) doc.getElementsByTagName("Employee");
		Element employee = null;
		if (null != employees) {
			for (int len = 0; len < employees.getLength(); len ++) {
				employee = (Element) employees.item(len);
				if (null != employee && empId.equals(employee.getAttribute("id"))) {
					final Node nodeToBeRemoved = employee.getElementsByTagName(elementToBeRemoved).item(0);
					employee.removeChild(nodeToBeRemoved);
				}
			}
		}
	}
	
	public static final void updateElementValue(final Document doc, final String empId, final String elementName, final String elementValue) {
		final NodeList employees = (NodeList) doc.getElementsByTagName("Employee");
		Element employee = null;
		if (null != employees) {
			for (int len = 0; len < employees.getLength(); len ++) {
				employee = (Element) employees.item(len);
				if (null != employee && empId.equals(employee.getAttribute("id"))) {
					final Text textNodeToBeUpdated = (Text) employee.getElementsByTagName(elementName).item(0).getFirstChild();
					textNodeToBeUpdated.setNodeValue(elementValue);
				}
			}
		}
	}
	
	public static final void updateAttributeValue(final Document doc, final String empId, final String newAttrValue) {
		final NodeList employees = (NodeList) doc.getElementsByTagName("Employee");
		Element employee = null;
		if (null != employees) {
			for (int len = 0; len < employees.getLength(); len ++) {
				employee = (Element) employees.item(len);
				if (null != employee && empId.equals(employee.getAttribute("empId"))) {
					employee.setAttribute("id", newAttrValue);
				}
			}
		}
	}
	
	public static final void mergeXml(final Document doc, final File sourceFile) {
		//final NodeList employees = (NodeList) doc.getElementsByTagName("Employee");
		final Element employees = doc.getDocumentElement();
		final Document sourceDoc = newDocumentInstance(sourceFile);
		final NodeList empsFromSource = (NodeList) sourceDoc.getElementsByTagName("Employee");
		Node empToBeAppended = null;
		if (employees != null && empsFromSource != null) {
			for (int len = 0; len < empsFromSource.getLength(); len ++) {
				//empToBeAppended = empsFromSource.item(len);
				empToBeAppended = doc.importNode(empsFromSource.item(len), true);
				//employees.item(0).getParentNode().appendChild(empToBeAppended);
				employees.appendChild(empToBeAppended);
			}
		}
	}
	
	public static final Document newDocumentInstance(final File file) {
		Document document = null;
        try {
        	final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			document = docBuilder.parse(file);
		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
		} catch (final SAXException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} 
        return document;
	}

}
