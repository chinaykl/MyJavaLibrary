package com.chinaykl.java.library.file.xml.android.layout;

import java.util.ArrayList;

import com.chinaykl.java.library.file.xml.XmlFile;

public class LayoutFile extends XmlFile {

	public static final int LAYOUT_OPEN_ERROR = XML_POINT_NOT_EXIST - 1;
	public static final int LAYOUT_CLOSE_ERROR = LAYOUT_OPEN_ERROR - 1;

	private static final String DEFAULT_POINT = "d";

	public LayoutFile(String path) {
		super(path);
	}

	public int open() {

		int state = super.open();
		if (state != 0) {
			return state;
		}

		if (createPoint(DEFAULT_POINT) == false) {
			return LAYOUT_OPEN_ERROR;
		}

		if (goInTo(DEFAULT_POINT, 0) != 0) {
			return LAYOUT_OPEN_ERROR;
		}

		return 0;
	}

	public LayoutItem getLayoutItem() {

		LayoutItem rootItem = new LayoutItem();

		rootItem.setParent(null);
		rootItem.setAttributes(scanAttributes(LayoutItem.getAttributekeys()));
		rootItem.setChildren(getChildLayoutItem(rootItem));

		return rootItem;

	}

	private ArrayList<LayoutItem> getChildLayoutItem(LayoutItem parent) {

		ArrayList<LayoutItem> result = new ArrayList<LayoutItem>();

		int childrenNum = getChildrenNum(DEFAULT_POINT);
		if (childrenNum == 0) {
			result = null;
		} else {
			for (int i = 0; i < childrenNum; i++) {
				goInTo(DEFAULT_POINT, i);
				LayoutItem child = new LayoutItem();
				child.setParent(parent);
				child.setAttributes(scanAttributes(LayoutItem.getAttributekeys()));
				child.setChildren(getChildLayoutItem(child));
				result.add(child);
				goBack(DEFAULT_POINT);
			}
		}
		return result;
	}

	private String[] scanAttributes(String[] attributekeys) {

		String[] attributes = new String[attributekeys.length];

		for (int i = 0; i < attributekeys.length; i++) {
			attributes[i] = getString(DEFAULT_POINT, attributekeys[i]);
		}

		return attributes;
	}

	public int close() {

		if (removePoint(DEFAULT_POINT) == false) {
			return LAYOUT_CLOSE_ERROR;
		}

		return 0;
	}
}
