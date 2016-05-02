package com.chinaykl.java.library.file.xml;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlSimpleOperations extends XmlBase {

	Node mCurrentNode = null;

	public XmlSimpleOperations(String path) {
		super(path);
	}

	static final public int XML_OPEN_ERROR = -1;
	static final public int XML_READ_ERROR = -2;
	static final public int XML_FORMAT_ERROR = -3;

	public int read() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.out.println("Xml open failed");
			return XML_OPEN_ERROR;
		}
		Document document = null;
		try {
			document = db.parse(mPath);
		} catch (SAXException | IOException e) {
			System.out.println("Xml read failed");
			return XML_READ_ERROR;
		}

		NodeList rootList = document.getChildNodes();
		if (rootList.getLength() != 1) {
			return XML_FORMAT_ERROR;
		}

		mCurrentNode = rootList.item(0);
		return 0;
	}

	static final private String XML_TEXT = "#text";

	public void goInTo(int index) {
		NodeList list = mCurrentNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			if (!list.item(i).getNodeName().equalsIgnoreCase(XML_TEXT)) {
				if (index == 0) {
					mCurrentNode = list.item(i);
				} else {
					index--;
				}
			}
		}

	}

	public void goBack() {
		mCurrentNode = mCurrentNode.getParentNode();
	}

	public String getValue(String key) {
		NamedNodeMap map = mCurrentNode.getAttributes();
		for (int i = 0; i < map.getLength(); i++) {
			Node node = map.item(i);
			if (key.equalsIgnoreCase(node.getNodeName())) {
				return node.getNodeValue();
			}
		}
		return null;
	}
}
