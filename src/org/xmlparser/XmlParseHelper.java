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
	
	public static final void updateElementValue(final Document doc, final String empId, final String elementName, 
			final String elementValue) {
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
	
	public static final void appendElementValueFromOtherSorce(final Document doc, final String empId, final String elementName, 
			final File srcFile) {
		final NodeList employees = (NodeList) doc.getElementsByTagName("Employee");
		final Document sourceDoc = newDocumentInstance(srcFile);
		final NodeList empsFromSource = (NodeList) sourceDoc.getElementsByTagName("Employee");
		Element destElement = null;
		Element srcElement = null;
		
		for (int dIndex = 0; dIndex < employees.getLength(); dIndex ++) {
			destElement = (Element) employees.item(dIndex);
			for (int sIndex = 0; sIndex < empsFromSource.getLength(); sIndex ++) {
				srcElement = (Element) empsFromSource.item(sIndex);
				if (empId.equals(destElement.getAttribute("id")) 
						&& destElement.getAttribute("id").equals(srcElement.getAttribute("id"))) {
					if (destElement.hasChildNodes() && srcElement.hasChildNodes()) {
						final NodeList dList = destElement.getChildNodes();
						final NodeList sList = srcElement.getChildNodes();
						for (int dLen = 0; dLen < dList.getLength(); dLen ++ ) {
							final Node dNode = dList.item(dLen);
							for (int sLen = 0; sLen < sList.getLength(); sLen ++) {
								final Node sNode = sList.item(sLen);
								if (Node.ELEMENT_NODE == dNode.getNodeType() && Node.ELEMENT_NODE == sNode.getNodeType() 
										&& dNode.getNodeName().equals(sNode.getNodeName()) && elementName.equals(dNode.getNodeName())) {
									final StringBuilder nodeContent = new StringBuilder();
									final Text dTextNode = (Text) dNode.getFirstChild();
									final Text sTextNode = (Text) sNode.getFirstChild();
									nodeContent.append(dTextNode.getTextContent()).append(" ").append(sTextNode.getTextContent());
									dTextNode.setTextContent(nodeContent.toString());
								}
							}
						}
					}
					
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
	
	public static final void addChildNodeToParentElement(final Document doc, final String empId, final File sourceFile) {
		final NodeList employees = (NodeList) doc.getElementsByTagName("Employee");
		final Document sourceDoc = newDocumentInstance(sourceFile);
		final NodeList empsFromSource = (NodeList) sourceDoc.getElementsByTagName("Employee");
		Node nodeToBeAppended = null;
		Element destElement = null;
		Element srcElement = null;
		
		for (int dIndex = 0; dIndex < employees.getLength(); dIndex ++) {
			destElement = (Element) employees.item(dIndex);
			for (int sIndex = 0; sIndex < empsFromSource.getLength(); sIndex ++) {
				srcElement = (Element) empsFromSource.item(sIndex);
				if (srcElement.getAttribute("id").equals(destElement.getAttribute("id"))) {
					if (srcElement.hasChildNodes()) {
						final NodeList nList = srcElement.getChildNodes();
						for (int len = 0; len < nList.getLength(); len ++) {
							nodeToBeAppended = doc.importNode(nList.item(len), true);
							destElement.appendChild(nodeToBeAppended);
						}
					}
				}
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
