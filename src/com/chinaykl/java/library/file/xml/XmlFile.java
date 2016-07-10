package com.chinaykl.java.library.file.xml;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.chinaykl.java.library.file.SimpleFile;

public class XmlFile extends SimpleFile {
	public static final int XML_OPEN_ERROR = SIMPLEFILE_ERROR_CODE - 1;
	public static final int XML_READ_ERROR = XML_OPEN_ERROR - 1;
	public static final int XML_FORMAT_ERROR = XML_READ_ERROR - 1;
	public static final int XML_POINT_NOT_EXIST = XML_FORMAT_ERROR - 1;

	private static final String XML_TEXT = "#text";
	private static final String XML_ZERO = "0";
	// private static final String XML_NODE = "node";

	private class Point {
		String flag;
		Node node;
	}

	private ArrayList<Point> mPointList;
	private NodeList mRootList;

	private int getPointIndex(String flag) {
		for (int i = 0; i < mPointList.size(); i++) {
			Point point = mPointList.get(i);
			if (flag.equals(point.flag) == true) {
				return i;
			}
		}
		return XML_POINT_NOT_EXIST;
	}

	protected Node getPointNode(String flag) {
		int index = getPointIndex(flag);
		if (index != XML_POINT_NOT_EXIST) {
			Node node = mPointList.get(index).node;
			return node;
		}
		return null;
	}

	public boolean createPoint(String flag) {
		if (getPointIndex(flag) != XML_POINT_NOT_EXIST) {
			return false;
		}
		Point point = new Point();
		point.flag = flag;
		point.node = mRootList.item(0);
		mPointList.add(point);
		return true;

	}

	public boolean removePoint(String flag) {
		int index = getPointIndex(flag);
		if (index == XML_POINT_NOT_EXIST) {
			return false;
		}
		mPointList.remove(index);
		return true;
	}

	public boolean setPoint(String origin, String target) {
		int oIndex = getPointIndex(origin);
		int tIndex = getPointIndex(target);
		if ((oIndex == XML_POINT_NOT_EXIST) || (tIndex == XML_POINT_NOT_EXIST)) {
			return false;
		}

		Point point = new Point();
		point.flag = origin;
		point.node = mPointList.get(tIndex).node.cloneNode(true);

		mPointList.set(oIndex, point);
		return true;
	}

	public boolean setPoint(String flag, Node node) {
		int index = getPointIndex(flag);
		if (index == XML_POINT_NOT_EXIST) {
			return false;
		}
		Point point = new Point();
		point.flag = flag;
		point.node = node;
		mPointList.set(index, point);
		return true;
	}

	public XmlFile(String path) {
		super(path, FILE_MODE_R);
		mPointList = new ArrayList<Point>();
	}

	public int open() {
		int fileState = super.open();
		if (fileState != 0) {
			return fileState;
		}

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			return XML_OPEN_ERROR;
		}
		Document document = null;
		try {
			document = db.parse(getPath());
		} catch (SAXException e) {
			return XML_READ_ERROR;
		} catch (IOException e) {
			return XML_READ_ERROR;
		}

		mRootList = document.getChildNodes();
		if (mRootList.getLength() != 1) {
			return XML_FORMAT_ERROR;
		}

		return 0;
	}

	protected boolean isAvailableNode(Node node) {
		if ((node.getNodeName().equals(XML_TEXT) == true) || (node.getNodeName().equals(XML_ZERO) == true)) {
			return false;
		}
		return true;
	}

	public int getChildrenNum(String flag) {
		Node node = getPointNode(flag);
		NodeList list = node.getChildNodes();
		int result = 0;
		for (int i = 0; i < list.getLength(); i++) {
			Node subNode = list.item(i);
			if (isAvailableNode(subNode) == true) {
				result++;
			}
		}
		return result;
	}

	public int goInTo(String flag, int index) {
		Node node = getPointNode(flag);
		if (node != null) {
			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node subNode = list.item(i);
				if (isAvailableNode(subNode) == true) {
					if (index == 0) {
						setPoint(flag, subNode);
						return 0;
					} else {
						index--;
					}
				}
			}
		}
		return XML_POINT_NOT_EXIST;
	}

	public int goBack(String flag) {
		Node node = getPointNode(flag);
		if (node != null) {
			node = node.getParentNode();
			setPoint(flag, node);
			return 0;
		}
		return XML_POINT_NOT_EXIST;
	}

	private String getValue(String flag, String key) {
		Node node = getPointNode(flag);
		if (node != null) {
			if (key.equals("value") == true) {
				return node.getTextContent();
			}
			NamedNodeMap map = node.getAttributes();
			for (int i = 0; i < map.getLength(); i++) {
				Node subNode = map.item(i);
				if (key.equalsIgnoreCase(subNode.getNodeName())) {
					return subNode.getNodeValue();
				}
			}
		}

		return null;
	}

	public String getString(String flag, String key) {
		String result = getValue(flag, key);
		return result != null ? result : "";
	}

	public int getInt(String flag, String key) {
		String string = getString(flag, key);
		int result = 0;
		try {
			result = Integer.valueOf(string);
		} catch (NumberFormatException e) {
			result = 0;
		}
		return result;
	}

	public float getFloat(String flag, String key) {
		String string = getString(flag, key);
		float result = 0.0f;
		try {
			result = Float.valueOf(string);
		} catch (NumberFormatException e) {
			result = 0.0f;
		}
		return result;
	}
}
