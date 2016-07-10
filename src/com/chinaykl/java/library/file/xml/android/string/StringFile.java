package com.chinaykl.java.library.file.xml.android.string;

import java.util.ArrayList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.chinaykl.java.library.file.xml.XmlFile;

public class StringFile extends XmlFile {

	public StringFile(String path) {
		super(path);
	}

	public ArrayList<StringItem> getAllStringItems(String flag) {
		ArrayList<StringItem> result = new ArrayList<StringItem>();

		Node pNode = getPointNode(flag);
		NodeList list = pNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node cNode = list.item(i);
			if (isAvailableNode(cNode) == true) {
				String name = "";
				NamedNodeMap map = cNode.getAttributes();
				for (int j = 0; j < map.getLength(); j++) {
					Node attr = map.item(j);
					if (attr.getNodeName().equals("name") == true) {
						name = attr.getNodeValue();
						break;
					}
				}
				StringItem item = new StringItem();
				item.name = name;
				item.value = cNode.getTextContent();
				result.add(item);
			}
		}

		return result;
	}

}
